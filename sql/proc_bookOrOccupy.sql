
-- 预订  入住程序(添加)
DROP PROCEDURE IF EXISTS sp_bookOrOccupy_add;
DELIMITER //
CREATE PROCEDURE sp_bookOrOccupy_add(name VARCHAR(100), sex VARCHAR(5), IDType SMALLINT UNSIGNED, IDNum VARCHAR(100), conName VARCHAR(50), conNum VARCHAR(100), roomsNum VARCHAR(500), operator INT UNSIGNED, startTime DATETIME, endTime DATETIME, cash INT, roomState SMALLINT UNSIGNED, comment TEXT)
label1: BEGIN
	DECLARE result VARCHAR(50) DEFAULT '';
	DECLARE roomCount INT DEFAULT 0;                          -- 定义传入的房间编号的个数
    
	-- 1. 判断客户是否存在, 如果存在：就更新updateClient;如果不存在就createClient
	SET @sp_bookOrOccupy_add_pId = NULL;
	call sp_personExist(IDType, IDNum, @sp_bookOrOccupy_add_pId);
	-- a. 如果客户存在
	IF @sp_bookOrOccupy_add_pId > 0 THEN
	    -- 得到客户的id(clientId)
	    SET @sp_bookOrOccupy_add_clientId = NULL;
	    PREPARE sp_bookOrOccupy_add_q1 FROM 'SELECT id INTO @sp_bookOrOccupy_add_clientId FROM client WHERE personId=?';
	    EXECUTE sp_bookOrOccupy_add_q1 USING @sp_bookOrOccupy_add_pId;
	    -- 更新客户信息
	    call sp_updateClient(@sp_bookOrOccupy_add_clientId, name, sex, NULL, '', IDType, IDNum, conName, conNum, '');
	-- b. 如果客户不存在
	ELSE
        -- 创建一个新客户
	    call sp_createClient(name, sex, '', '', IDType, IDNum, conName, conNum, '');
	    call sp_personExist(IDType, IDNum, @sp_bookOrOccupy_add_pId);
	END IF;
	
	-- 2. 保存入住或预定的信息
    SELECT fn_subStrCount(roomsNum, ',') INTO roomCount;      -- 获取传入的房间编号的个数
    -- 依次插入房间号
    WHILE roomCount >= 0 DO
        -- 对传入的房间编号(roomsNum)字符串处理
        SET @sp_bookOrOccupy_add_roomNum = NULL;
        SELECT SUBSTRING_INDEX(roomsNum, ',', 1) INTO @sp_bookOrOccupy_add_roomNum;         -- 获取一个房间编号
        SET @sp_bookOrOccupy_add_a = NULL;
        SELECT LOCATE(',', roomsNum) INTO @sp_bookOrOccupy_add_a;              -- 获取分割符下标
        IF @sp_bookOrOccupy_add_a > 0 THEN
             SELECT SUBSTRING(roomsNum, @sp_bookOrOccupy_add_a+1) INTO roomsNum;
        END IF;
        
        -- 获取此roomNum的roomId
        SET @sp_bookOrOccupy_add_roomId = NULL;
        PREPARE sp_bookOrOccupy_add_q2 FROM 'SELECT id INTO @sp_bookOrOccupy_add_roomId FROM room WHERE num=? LIMIT 1';
        EXECUTE sp_bookOrOccupy_add_q2 USING @sp_bookOrOccupy_add_roomNum;
        
        -- roomNum是否存在dynamicRoomState表中
        IF @sp_bookOrOccupy_add_roomId > 0 THEN
            SET @sp_bookOrOccupy_add_b = NULL;
            PREPARE sp_bookOrOccupy_add_q3 FROM 'SELECT COUNT(*) INTO @sp_bookOrOccupy_add_b FROM dynamicRoomState WHERE roomId=?';
            EXECUTE sp_bookOrOccupy_add_q3 USING @sp_bookOrOccupy_add_roomId;
        END IF;
        -- 如果roomNum不存在dynamicRoomState表中  执行下面的语句
        IF @sp_bookOrOccupy_add_b = 0 THEN         
            -- 向dynamicRoomState表插入数据
            INSERT INTO dynamicRoomState(roomId, personId, stateId, startTime, endTime, cash, comment) VALUES (@sp_bookOrOccupy_add_roomId, @sp_bookOrOccupy_add_pId, roomState, startTime, endTime, cash, comment);
            SET result = 'success';
        -- 如果roomNum存在dynamicRoomState表中  执行下面的语句
        ELSEIF  @sp_bookOrOccupy_add_b > 0 THEN    
            -- 查可用状态的房间 id
            SET @sp_bookOrOccupy_add_dId = NULL;
            PREPARE sp_bookOrOccupy_add_q4 FROM  'SELECT id INTO @sp_bookOrOccupy_add_dId FROM dynamicRoomState WHERE stateId=1 AND roomId=? LIMIT 1';
            EXECUTE sp_bookOrOccupy_add_q4 USING @sp_bookOrOccupy_add_roomId;
        
            SET @sp_bookOrOccupy_add_roomState = roomState;
            SET @sp_bookOrOccupy_add_startTime = startTime;
            SET @sp_bookOrOccupy_add_endTime = endTime;
            SET @sp_bookOrOccupy_add_cash = cash;
            SET @sp_bookOrOccupy_add_comment = comment;
            IF @sp_bookOrOccupy_add_dId > 0 THEN
                PREPARE sp_bookOrOccupy_add_q5 FROM 'UPDATE dynamicRoomState SET personId=?, stateId=?, startTime=?, endTime=?, cash=?, comment=? WHERE id=?';
                EXECUTE sp_bookOrOccupy_add_q5 USING @sp_bookOrOccupy_add_pId, @sp_bookOrOccupy_add_roomState, @sp_bookOrOccupy_add_startTime, @sp_bookOrOccupy_add_endTime, @sp_bookOrOccupy_add_cash, @sp_bookOrOccupy_add_comment, @sp_bookOrOccupy_add_dId;
                SET result = 'success';
            ELSE
                -- 如果要设置的状态为预定的情况
                IF @sp_bookOrOccupy_add_roomState = 2 THEN
                    INSERT INTO dynamicRoomState(roomId, personId, stateId, startTime, endTime, cash, comment) VALUES (@sp_bookOrOccupy_add_roomId, @sp_bookOrOccupy_add_pId, roomState, startTime, endTime, cash, comment);
                    SET result = 'success';                 
                ELSE
                    SET result = 'failure';
                END IF;
            END IF;
        END IF;
        IF result = 'success' THEN
            -- 添加历史信息到roomStateHistoricalInfo表
            INSERT INTO roomStateHistoricalInfo(operator, storeTime, roomId, personId, stateId, startTime, endTime, cash, comment) VALUES (operator, now(), @sp_bookOrOccupy_add_roomId, @sp_bookOrOccupy_add_pId, roomState, startTime, endTime, cash, comment);
        END IF;
        SET roomCount = roomCount - 1;
   END WHILE;
   SELECT result;
END label1
//
DELIMITER ;