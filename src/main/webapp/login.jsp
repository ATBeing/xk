<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html dir="ltr" lang="zh-CN">
<head>
<title>欢迎使用选课管理系统</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#user_login_loginForm').form({
			url : '${pageContext.request.contextPath}/loginAction!doNotNeedSession_login.action',
			success : function(r) {
				var json = jQuery.parseJSON(r);
				if (json.success) {
					window.location.href = "${pageContext.request.contextPath}/layout/index.jsp";
				} else {
					$.messager.show({
						title : '提示',
						msg : json.msg
					});
				}
			}
		});
		$('#user_login_loginForm input').bind('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				$('#user_login_loginForm').submit();
			}
		});

		window.setTimeout(function() {
			$('#user_login_loginForm input[name=uname]').focus();
		}, 0);
	});
</script>
</head>
<body>
	<div class="login">
		<div class="box png">
			<form id="user_login_loginForm" method="post">
				<div class="header">
					<h2 class="logo png">
						<a href="#http://dolphin.com" target="_blank"></a>
					</h2>
					<span class="alt">管理员登录</span>
				</div>
				<ul>
					<li><label>用户名</label><input name="uname" type="text" id="text" class="easyui-validatebox" data-options="required:'true',missingMessage:'登陆名称必填'" /></li>
					<li><label>密 码</label><input name="pwd" type="password" id="text" class="easyui-validatebox" data-options="required:'true',missingMessage:'密码必填'" /></li>
					<li class="submits"><input class="submit" type="button" value="登录" onclick="$('#user_login_loginForm').submit();"/></li>
				</ul>
				<div class="copyright">
					&copy; 2012 - 2015 | <a href="http://localhost:8081/OpenKM/" target="_blank" title="OpenKM">KMS.</a> | <a title="腾讯微博" href="#http://t.qq.com/dolphin" target="_blank" class="weibo tencent">腾讯微博</a>
				</div>
			</form>
		</div>
		<div class="air-balloon ab-1 png"></div>
		<div class="air-balloon ab-2 png"></div>
		<div class="footer"></div>
	</div>

	<script type="text/javascript" src="js/fun.base.js"></script>
	<script type="text/javascript" src="js/login.js"></script>

	<!--[if lt IE 8]>
<script src="jslib/PIE.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    if (window.PIE && ( $.browser.version >= 6 && $.browser.version < 10 )){
        $('input.text,input.submit').each(function(){
            PIE.attach(this);
        });
    }
});
</script>
<![endif]-->

	<!--[if IE 6]>
<script src="jslib/DD_belatedPNG.js" type="text/javascript"></script>
<script>DD_belatedPNG.fix('.png')</script>
<![endif]-->

</body>
</html>