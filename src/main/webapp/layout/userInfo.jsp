<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var pwd;
	var pwdd;
	$(function() {
		pwd = $('#pwd').validatebox({
			required : true,
			delay : 10,
			deltaX : 10
		});

	});
</script>
<div id="userInfo">
	<form method="post">
		<input name="id" type="hidden" value="${sessionInfo.userId}" />
		<table class="tableForm">
			<tr>
				<th style="width: 130px;">登录名</th>
				<td><input readonly="readonly" value="${sessionInfo.loginName}" />
				</td>
			</tr>
			<tr>
				<th>修改密码<br>
				<span style="color:red">(不想修改可以不填)</span>
				</th>
				<td><input id="pwd" name="pwd" type="password" />
				</td>
			</tr>
			<tr>
				<th><span style="color:red">再输入一次修改的密码</span>
				</th>
				<td><input type="password" class="easyui-validatebox"
					data-options="required:'true',missingMessage:'请再次填写密码',validType:'eqPwd[\'#userInfo input[name=pwd]\']'" />
				</td>
			</tr>
			<tr>
				<th>所属角色</th>
				<td><input readonly="readonly" style="height: 80px;"
					value="${sessionInfo.role}" />
				</td>
			</tr>
			<tr>
				<th>用户号</th>
				<td><input name="idd" value="${sessionInfo.idd}" />
				</td>
			</tr>
			<tr>
				<th>手机号码</th>
				<td><input name="phone" value="${sessionInfo.phone}" class="easyui-numberbox" />
				</td>
			</tr>
			<tr>
				<th>邮箱号码</th>
				<td><input name="email" value="${sessionInfo.email}" class="easyui-validatebox"
					data-options="validType:'email'" />
				</td>
			</tr>
			<tr>
				<th>帐号创建时间</th>
				<td><input readonly="readonly" value="${sessionInfo.createtime}" />
				</td>
			</tr>
			<tr>
				<th>帐号上次修改时间时间</th>
				<td><input readonly="readonly" value="${sessionInfo.modifytime}" />
				</td>
			</tr>
		</table>
	</form>
</div>