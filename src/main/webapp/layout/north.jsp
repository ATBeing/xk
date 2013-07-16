<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	function logout(b) {
		$('#sessionInfoDiv').html('');
		$.post('${pageContext.request.contextPath}/loginAction!doNotNeedSession_logout.action', function() {
			window.location.href = "${pageContext.request.contextPath}/login.jsp";
		});
	}

	function showUserInfo() {
		var p = parent.sy.dialog({
			title : '用户信息',
			href : '${pageContext.request.contextPath}/loginAction!doNotNeedAuth_userInfo.action',
			width : 490,
			height : 400,
			buttons : [ {
				text : '修改密码',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/loginAction!doNotNeedAuth_editUserInfo.action',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								p.dialog('close');
							}
							parent.sy.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			} ],
			onLoad : function() {
			}
		});
	}
	
</script>

<div id="weather" style="margin-left: 10px; height: 300px; width: 80%; border: 0px solid #c00; position: relative;">
	<img src="../images/logo.gif" style="margin-top: 5px" />
</div>
<div id="sessionInfoDiv" style="position: absolute; right: 5px; top: 10px;">
	<c:if test="${sessionInfo.userId != null}">[<strong>${sessionInfo.loginName}</strong>]，欢迎你！您使用[<strong>${sessionInfo.ip}</strong>]IP登录！</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	 <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="showUserInfo();">个人信息</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div onclick="logout(true);">退出系统</div>
</div>
