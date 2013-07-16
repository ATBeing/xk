<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var role;
	$(function() {
		role = $('#role').combobox({
			panelHeight : 100,
			valueField : 'role',
			textField : 'roleName',
			data : [ {
				role : 'admin',
				roleName : '管理员'
			}, {
				role : 'teacher',
				roleName : '老师'
			}, {
				role : 'student',
				roleName : '学生'
			} ]
		});
	});
</script>
<div>
	<form id="admin_userEdit_form" method="post">
		<input name="id" readonly="readonly" hidden="true" style="display:none" />
		<table>
			<tr>
				<th>用户名称</th>
				<td><input name="uname" class="easyui-validatebox" data-options="required:'true'" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password" class="easyui-validatebox"
					data-options="required:'true'" /></td>
			</tr>
			<tr>
				<th>用户号</th>
				<td><input name="idd" class="easyui-validatebox"
					data-options="required:'true'"/></td>
			</tr>
			<tr>
				<th>手机</th>
				<td><input name="phone" class="easyui-numberbox" /></td>
			</tr>
			<tr>
				<th>邮箱</th>
				<td><input name="email" class="easyui-validatebox"
					data-options="validType:'email'" /></td>
			</tr>
			<tr>
				<th>角色</th>
				<td><input id="role" name="role">
				</td>
			</tr>
		</table>
	</form>
</div>
