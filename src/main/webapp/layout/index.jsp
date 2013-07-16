<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<html>
<head>
<title>选课管理系统</title>
<jsp:include page="../inc.jsp"></jsp:include></head>
<body class="easyui-layout">

	<!-- 北部面板  -->
	<div data-options="border:false,region:'north',href:'../layout/north.jsp'"
		style="height: 60px;overflow: hidden;" class="logo"></div>

	<!-- 西部面板 -->
	<div data-options="region:'west',title:'系统菜单'" style="width:165px;">
		<jsp:include page="../layout/west.jsp"></jsp:include></div>

	<!-- 中部面板 -->
	<div data-options="region:'center',border:false,title:'欢迎使用选课管理系统'" style="overflow: hidden;">
		<jsp:include page="../layout/center.jsp"></jsp:include></div>
</body>
</html>