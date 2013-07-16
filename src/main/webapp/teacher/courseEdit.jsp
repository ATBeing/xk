<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
</script>

<div>
	<form id="t_courseAdd_form" method="post">
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
				<th>locked待定</th>
				<td><input name="locked" />
				</td>
			</tr>
			<tr>
				<th>实验目的，内容及要求</th>
				<td><textarea name="appRecord"></textarea>
				</td>
			</tr>
			<tr>
				<th>老师(组长)编号</th>
				<td><input name="tid" />
				</td>
			</tr>
			<tr>
				<th>开放形式</th>
				<td><input name="opentype" />
				</td>
			</tr>
			<tr>
				<th>实验室名称、地点</th>
				<td><input name="exname" />
				</td>
			</tr>
			<tr>
				<th>联系电话</th>
				<td><input name="telphone" />
				</td>
			</tr>
			<tr>
				<th>开放实验项目类型</th>
				<td><input name="extype" />
				</td>
			</tr>
			<tr>
				<th>实验学时</th>
				<td><input name="extime" />
				</td>
			</tr>
			<tr>
				<th>计划指导学生人数</th>
				<td><input name="exnum" />
				</td>
			</tr>
			<tr>
				<th>每组人数</th>
				<td><input name="expernum" />
				</td>
			</tr>
			<tr>
				<th>实验成果形式</th>
				<td><input name="resultType" />
				</td>
			</tr>
			<tr>
				<th>材料消耗量</th>
				<td><input name="materFare" />
				</td>
			</tr>
			<tr>
				<th>工作量补贴</th>
				<td><input name="workFare" />
				</td>
			</tr>
			<tr>
				<th></th>
				<td><input name="" />
				</td>
			</tr>



			<tr>
				<th></th>
				<td><input name="" />
				</td>
			</tr>

		</table>

	</form>
</div>