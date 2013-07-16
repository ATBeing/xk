<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var locked;
	var reviewed;
	$(function() {
		locked = $('#locked').combobox({
			valueField : 'locked',
			textField : 'lockedName',
			panelHeight : 50,
			data : [ {
				locked : '1',
				lockedName : '是'
			}, {
				locked : '0',
				lockedName : '否'
			} ]
		});
		
		reviewed = $('#reviewed').combobox({
			valueField : 'reviewed',
			textField : 'reviewedName',
			panelHeight : 50,
			data : [ {
				reviewed : '1',
				reviewedName : '是'
			}, {
				reviewed : '0',
				reviewedName : '否'
			} ]
		});
	});
</script>
<div>
	<form id="admin_all_editStateform" method="post">
		<input name="id" readonly="readonly" hidden="true" style="display:none" />
		<table>
			<tr>
				<th>课程编号</th>
				<td><input name="cid" class="easyui-validatebox"
					data-options="required:'true',missingMessage:'课程编号必填'" /></td>
			</tr>
			<tr>
				<th>课程名称</th>
				<td><input name="cname" class="easyui-validatebox"
					data-options="required:'true',missingMessage:'课程名称必填'" />
				</td>
			</tr>
			<tr>
				<th>课程简介</th>
				<td><textarea name="cdesc"></textarea>
				</td>
			</tr>
			<tr>
				<th>截止时间</th>
				<td><input name="ctime" />
				</td>
			</tr>
			<tr>
				<th>最大选课人数</th>
				<td><input name="maxnum" />
				</td>
			</tr>
			<tr>
				<th>已选人数</th>
				<td><input name="exitnum" />
				</td>
			</tr>
			<tr>
				<th>是否锁定</th>
				<td><input id="locked" name="locked" /></td>
			</tr>
			<tr>
				<th>是否审阅</th>
				<td><input id="reviewed" name="reviewed" /></td>
			</tr>
		</table>
	</form>
</div>