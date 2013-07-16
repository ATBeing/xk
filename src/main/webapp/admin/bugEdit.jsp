<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="margin: 5px;">
	<form method="post">
		<input type="hidden" name="id" />
		<table class="tableForm">
			<tr>
				<th style="width:80px;">上报BUG名称</th>
				<td><input name="bname" class="easyui-validatebox" data-options="required:'true',missingMessage:'请填写上报BUG名称'" style="width: 97%;" />
				</td>
			</tr>
			<tr>
				<th>BUG描述</th>
				<td><textarea name="bdesc" style="height: 260px;width: 98%;">${desc}</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>