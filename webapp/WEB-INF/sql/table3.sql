-- 表地图:person{connect(contactWay), certificate(IDType), personRole{employee{empRole{dept, position}, empState, }, client}}
-- room(roomType, roomState)
-- 房间动态信息表: dynamicRoomState(room, roomState, person)
-- 房间动态信息表(dynamicRoomState)的历史记录 : roomStateHistoricalInfo
-- 员工职位历史信息记录表: empRoleHistoricalInfo
-- 公司员工历史信息记录表: employeeHistoricalInfo

-- person表
DROP TABLE IF EXISTS person;
CREATE TABLE person(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	sex ENUM('m', 'f') NOT NULL,           -- m:男   f:女
	birth DATE,
	address VARCHAR(200),
	comment TEXT
);
INSERT INTO person(name, sex) VALUES ('张三', 'm');


-- 人角色表(person的子表)  用以分别公司员工和客户  一对多(可以有多个角色)
DROP TABLE IF EXISTS personRole;
CREATE TABLE personRole(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	personId INT UNSIGNED NOT NULL,
	roleTableName VARCHAR(100) NOT NULL,              -- 此列记录的是表名 (每种角色用一张表来描述——可以动态添加角色)
	comment TEXT,
	FOREIGN KEY(personId) REFERENCES person(id)
);

-- 证件表(person的子表)   一对多(可以有多个证件)
DROP TABLE IF EXISTS certificate;
CREATE TABLE certificate(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	personId INT UNSIGNED NOT NULL, 
	IDType SMALLINT UNSIGNED NOT NULL,
	IDNum VARCHAR(100) NOT NULL,
	comment TEXT,
	FOREIGN KEY(personId) REFERENCES person(id),
	FOREIGN KEY(IDType) REFERENCES IDType(id)
);

-- 可用证件列表
DROP TABLE IF EXISTS IDType;
CREATE TABLE IDType(
    id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    comment TEXT
);
INSERT INTO IDType(name) VALUES ('身份证');


-- 联系表(person的子表)     一对多(可以有多种联系方式)
DROP TABLE IF EXISTS `connect`;
CREATE TABLE `connect`(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	personId INT UNSIGNED NOT NULL,
	method SMALLINT UNSIGNED NOT NULL,                       
	num VARCHAR(100) NOT NULL,                      -- 联系方式内容(电话号码， qq号码，msn, email, 网站注册名...)
	storeTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 表示储存此条记录的时间(用以判断此联系为式是否可能过期)
	comment TEXT,
	FOREIGN KEY(personId) REFERENCES person(id),
	FOREIGN KEY(method) REFERENCES contactWay(id)
);

-- 联系方式列表
-- 联系方式名称(电话, qq，msn, email, 网址...)
DROP TABLE IF EXISTS contactWay;
CREATE TABLE contactWay(
   id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100) UNIQUE NOT NULL,
   comment TEXT
);
INSERT INTO contactWay(name) VALUES ('手机'), ('QQ'), ('msn'), ('email'), ('座机');

-- 公司员工表(personRole的子表)
DROP TABLE IF EXISTS employee;
CREATE TABLE employee(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	personId INT UNSIGNED NOT NULL,
	num VARCHAR(20) NOT NULL,                   -- 员工编号(workNum=deptNum+positionNum+empNum)
	hireDate DATE,   	                        -- 员工入职日期
	state SMALLINT UNSIGNED DEFAULT 1,          -- 员工状态——可查看员工状态表
	cardNum VARCHAR(100),                       -- 银行卡号
	comment TEXT,                    -- 备注
	FOREIGN KEY(state) REFERENCES empState(id),
	FOREIGN KEY(personId) REFERENCES person(id)
);

-- 员工状态表
DROP TABLE IF EXISTS empState;
CREATE TABLE empState(
	id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,    -- 状态名称
	comment TEXT                  -- 状态描述
);
INSERT INTO empState(name) VALUES ('在职'), ('休假');

-- 员工角色(employee的子表)
DROP TABLE IF EXISTS empRole;
CREATE TABLE empRole(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	empId INT UNSIGNED NOT NULL,
	deptId MEDIUMINT UNSIGNED,
	positionId MEDIUMINT UNSIGNED,
	password VARCHAR(100),
	comment TEXT,
	FOREIGN KEY(empId) REFERENCES employee(id),
	FOREIGN KEY(deptId) REFERENCES dept(id),
	FOREIGN KEY(positionId) REFERENCES `position`(id)
);

-- 部门表 
DROP TABLE IF EXISTS dept;
CREATE TABLE dept(
	id MEDIUMINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,                  -- 部门名称
	num VARCHAR(20) NOT NULL UNIQUE,            -- 部门编号
	parentDeptId MEDIUMINT UNSIGNED,            -- 上级部门编号——如果没有上级部门默认就设置parentDeptId 为空字符
	comment TEXT,                               -- 部门说明
	FOREIGN KEY(parentDeptId) REFERENCES dept(id)
);
INSERT INTO dept(num, name, parentDeptId) VALUES
('001D', '前厅部', 0),('002D', '工程部', 0),('003D', '餐饮部', 0),('004D', '客房部', 0),('005D', '财务部', 0),
('006D', '人事部', 0),('007D', '销售部', 0),('008D', '预定部', 1),('009D', '总机领班', 1),('010D', '接待部', 1),
('011D', '服务领班', 1);

-- 职位表
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`(
	id MEDIUMINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,                  -- 职位名称
	num VARCHAR(20) NOT NULL UNIQUE,            -- 职位编号
	comment TEXT                                -- 职位说明
);
INSERT INTO `position`(num, name) VALUES
('001P', '酒店经理'),('002P', '前厅部经理'),('003P', '话务员'),('004P', '接待员'),
('005P', '收银员'),('006P', '行李员'), ('007P', '预定员'), ('008P', '清洁员');


-- 公司员工历史信息记录表: employeeHistoricalInfo
DROP TABLE IF EXISTS empHistoricalInfo;
CREATE TABLE employeeHistoricalInfo(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	storeTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,       -- 此条记录更改的时间
	empId INT UNSIGNED,
	personId INT UNSIGNED,
	num VARCHAR(20),           -- 员工编号
	hireDate DATETIME,	       -- 员工入职日期
	cardNum VARCHAR(100),      -- 银行卡号
	comment TEXT               -- 备注
);

-- 员工角色历史信息记录表
DROP TABLE IF EXISTS empRoleHistoricalInfo;
CREATE TABLE empRoleHistoricalInfo(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	empRoleId INT UNSIGNED,
	empId INT UNSIGNED,
	personId INT UNSIGNED,
	deptId MEDIUMINT UNSIGNED,
	positionId MEDIUMINT UNSIGNED,
	password VARCHAR(100),
	comment TEXT
);


-- 客户表
DROP TABLE IF EXISTS client;
CREATE TABLE client(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	personId INT UNSIGNED NOT NULL,
	storeTime DATETIME,
	updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	comment TEXT,
	FOREIGN KEY(personId) REFERENCES person(id)
);

-- 房间表
DROP TABLE IF EXISTS room;
CREATE TABLE room(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	num VARCHAR(20) UNIQUE,                          -- 房间编号
	typeId SMALLINT UNSIGNED NOT NULL,               -- 房间类型
	comment TEXT,
	FOREIGN KEY(typeId) REFERENCES roomType(id)
);
INSERT INTO room(num, typeId) VALUES
('001F001', 1), ('001F002', 1), ('001F003', 1), ('001F004', 1), ('001F005', 1),
('002F001', 2), ('002F002', 2), ('002F003', 2), ('002F004', 2), ('002F005', 2),
('003F001', 3), ('003F002', 3), ('003F003', 3), ('003F004', 3), ('003F005', 3),
('004F001', 4), ('004F002', 4), ('004F003', 4), ('004F004', 4), ('004F005', 4),
('005F001', 5), ('005F002', 5), ('005F003', 5), ('005F004', 5), ('005F005', 5);

-- 房间类型表
DROP TABLE IF EXISTS roomType;
CREATE TABLE roomType(
	id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) UNIQUE NOT NULL,      -- 房间类型名
	unitCost INT DEFAULT 0,                -- 单价
	picShow VARCHAR(200),				       -- 房间图片展示
	comment TEXT                -- 房间类型介绍
);
INSERT INTO roomType(name, unitCost) VALUES ('单人间', 50), ('双人间', 80);

-- 房间状态表
DROP TABLE IF EXISTS roomState;
CREATE TABLE roomState(
	id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) UNIQUE NOT NULL,      -- 房间状态名
	comment TEXT                -- 房间状态描述
);
INSERT INTO roomState(name, comment) VALUES
('可用', '空闲可用'), ('预定', '已经被预定'), ('入住', '已有客人入住'), ('维修', '房间维修'), ('清洁', '房间正在或未被清洁');

-- 动态房间状态表
-- 用以表示：personId 在 startTime到endTime这个时间段使房间roomId处于stateId这个状态
DROP TABLE IF EXISTS dynamicRoomState;
CREATE TABLE dynamicRoomState(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	operator INT UNSIGNED NOT NULL,                 -- 操作人员
	roomId INT UNSIGNED NOT NULL,                   -- 房间表id
	personId INT UNSIGNED NOT NULL,                 -- 员工id 或 客户id
	stateId SMALLINT UNSIGNED NOT NULL,				-- 房间状态
	startTime DATETIME,                             -- 状态持续开始时间
	endTime DATETIME,                               -- 状态持续结束时间
	cash INT DEFAULT 0,                             -- 
	comment TEXT,
	FOREIGN KEY(operator) REFERENCES employee(id),
	FOREIGN KEY(roomId) REFERENCES room(id),
	FOREIGN KEY(personId) REFERENCES personId(id),
	FOREIGN KEY(stateId) REFERENCES roomState(id)
);

-- dynamicRoomState表的历史记录
DROP TABLE IF EXISTS roomStateHistoricalInfo;
CREATE TABLE roomStateHistoricalInfo(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	storeTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 此条记录更改的时间
	dId INT UNSIGNED,                               -- dynamicRoomState 表的id
	operator INT UNSIGNED,                          -- 操作人员
	roomId INT UNSIGNED,                            -- 房间表id
	personId INT UNSIGNED,                          -- 员工id 或 客户id
	stateId SMALLINT UNSIGNED,				        -- 房间状态
	startTime DATETIME,                             -- 状态持续开始时间
	endTime DATETIME,                               -- 状态持续结束时间
	cash INT,                                       -- 
	comment TEXT
);