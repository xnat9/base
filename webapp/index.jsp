<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>base web</title>
<base href="/base/">
<link rel="stylesheet" href="css/index.css" type="text/css"/>
<script type="text/javascript" src="jquery/jquery-2.1.1.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<style type="text/css">
body { text-align: center }
p { background-color: gray; }
</style>
</head>
<body>
<input id="btn1" type="button" value="点击"/>
<form action="test/m5" method="POST" enctype="multipart/form-data">
	<input type="file" name="file"/>
	<input type="submit" value="提交">
</form>
</body>
</html>
