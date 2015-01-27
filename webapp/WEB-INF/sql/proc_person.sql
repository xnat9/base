-- 查询person是否存在  存在返回此person 的id; 不存在返回null
DROP PROCEDURE IF EXISTS sp_personExist;
DELIMITER //
CREATE PROCEDURE sp_personExist(IDType SMALLINT UNSIGNED, IDNum VARCHAR(100), OUT pId INT UNSIGNED)
BEGIN
	SET @sp_personExist_IDType = IDType;
	SET @sp_personExist_IDNum = IDNum;
	SET @sp_personExist_result = NULL;
	PREPARE query FROM 'SELECT personId INTO @sp_personExist_result FROM certificate WHERE name=? AND IDNum=? LIMIT 1';
	EXECUTE query USING @sp_personExist_IDType, @sp_personExist_IDNum;
	SELECT @sp_personExist_result INTO pId;
END 
//
DELIMITER ;


-- 更新一个客户(client)
DROP PROCEDURE IF EXISTS sp_updateClient;
DELIMITER //
CREATE PROCEDURE sp_updateClient(clientId INT UNSIGNED, name VARCHAR(100), sex VARCHAR(5), birth DATE, addr VARCHAR(200), IDType SMALLINT UNSIGNED, IDNum VARCHAR(100), contactWay VARCHAR(50), contactNum VARCHAR(100), comment TEXT)
BEGIN
	-- 查询personId
	SET @sp_updateClient_clientId = clientId;
	SET @sp_updateClient_pId = NULL;     
	PREPARE sp_updateClient_q1 FROM 'SELECT personId INTO @sp_updateClient_pId FROM client WHERE id=?';
	EXECUTE sp_updateClient_q1 USING @sp_updateClient_clientId;
	
	-- 客户存在
	IF @sp_updateClient_pId > 0 THEN
	    -- 1. person表
	    SET @sp_updateClient_b = name;
	    SET @sp_updateClient_c = sex;
	    SET @sp_updateClient_d = birth;
	    SET @sp_updateClient_e = addr;
	    PREPARE sp_updateClient_q2 FROM 'UPDATE person SET name=?, sex=?, birth=?, address=? WHERE id=?';
	    EXECUTE sp_updateClient_q2 USING @sp_updateClient_b, @sp_updateClient_c, @sp_updateClient_d, @sp_updateClient_e, @sp_updateClient_pId;
	    -- 2. certificate表
        -- 查找此客户是否存在一个证件类型和传入的证件类型相同，如果有：就只是更新证件号码;如果没就添加一条certificate记录;	    
	    SET @sp_updateClient_f = NULL;
	    SET @sp_updateClient_g = IDType;
	    PREPARE query FROM 'SELECT id INTO @sp_updateClient_f FROM certificate WHERE IDType=? AND personId=?';
	    EXECUTE query USING @sp_updateClient_g, @sp_updateClient_pId;
	    IF @sp_updateClient_f > 0 THEN  -- 此证件类型存在
            SET @sp_updateClient_h = IDNum;
            PREPARE sp_updateClient_q3  FROM 'UPDATE certificate SET IDNum=? WHERE id=?';
            EXECUTE sp_updateClient_q3 USING @sp_updateClient_h, @sp_updateClient_f;
	    ELSE
	        INSERT INTO certificate(personId, IDType, IDNum) VALUES (@sp_updateClient_pId, IDType, IDNum);
	    END IF;
	    -- 3. client表
	    SET @sp_updateClient_i = comment;
	    PREPARE sp_updateClient_q4 FROM 'UPDATE client SET comment=? WHERE id=?';
	    EXECUTE sp_updateClient_q4 USING @sp_updateClient_i, @sp_updateClient_clientId;
	    -- 4. connect表
	    -- 查找此客户是否存在一个联系方式和传入的联系方式相同，如果有：就只是更新联系号码;如果没就添加一条connect记录;	
	    SET @sp_updateClient_j = NULL;
	    SET @sp_updateClient_k = contactWay;
	    PREPARE query1 FROM 'SELECT id INTO @sp_updateClient_j FROM `connect` WHERE method=? AND personId=?';
	    EXECUTE query1 USING @sp_updateClient_k, @sp_updateClient_pId;
	    IF @sp_updateClient_j > 0 THEN   -- 此联系方式存在
            SET @sp_updateClient_l = contactNum;
            PREPARE sp_updateClient_q5 FROM 'UPDATE `connect` SET num=? WHERE id=?';
            EXECUTE sp_updateClient_q5 USING @sp_updateClient_l, @sp_updateClient_j;
	    ELSE
	        INSERT INTO `connect`(personId, method, num) VALUES (sp_updateClient_pId, contactWay, contactNum);
	    END IF;
	END IF;
END
//
DELIMITER ;




