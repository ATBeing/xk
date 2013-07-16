<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="南邮 经济与管理学院 经管院 选课系统 开放项目">
<meta http-equiv="description" content="选课系统">
<!-- jquery库 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/jquery-1.8.2.min.js"></script>

<!-- easyui相关库 -->
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/syUtil.js" charset="utf-8"></script>

<!-- xhEditor插件库 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js" charset="utf-8"></script>