-- 计算某字符或字符串出现的次数
DROP FUNCTION IF EXISTS fn_subStrCount;
CREATE FUNCTION fn_subStrCount(str VARCHAR(500), subStr VARCHAR(100))
RETURNS INT
RETURN (LENGTH(str)-LENGTH(REPLACE(str, subStr, '')))/LENGTH(subStr);


-- 分割一个字符串
DROP FUNCTION IF EXISTS fn_getSplitStr;
DELIMITER //
CREATE FUNCTION fn_getSplitStr(str VARCHAR(2000), delimiter VARCHAR(5), seq INT)
RETURNS VARCHAR(250)
BEGIN
	DECLARE result VARCHAR(250) DEFAULT '';
	set result = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(str,delimiter,seq)),delimiter,1));
	return result;
END
//
DELIMITER ;

-- 分割一个字符串v2
DROP FUNCTION IF EXISTS fn_getSplitStr_v2;
DELIMITER //
CREATE FUNCTION fn_getSplitStr_v2(str VARCHAR(2000), delimiter VARCHAR(10), seq INT)
RETURNS VARCHAR(250)
BEGIN
	DECLARE result VARCHAR(250) DEFAULT NULL;
	set result = replace(substring(substring_index(str, delimiter, seq), length(substring_index(str, delimiter, seq-1)) + 1), delimiter, '');
	return result;
END
//
DELIMITER ;



-- 得到分割一个字符串的个数
DROP FUNCTION IF EXISTS `fn_get_split_string_total`;
DELIMITER $$
CREATE FUNCTION `fn_get_split_string_total`(f_string varchar(1000),f_delimiter varchar(5)) RETURNS int(11)  
BEGIN
	declare returnInt int(11);  
	if length(f_string) = 0 then  
    	return 0;
    elseif length(f_delimiter)=2 then
        return 1+(length(f_string) - length(replace(f_string,f_delimiter,'')))/2;
	else      
    	return 1+(length(f_string) - length(replace(f_string,f_delimiter,'')));  
	end if;  
END
$$  
DELIMITER ; 

