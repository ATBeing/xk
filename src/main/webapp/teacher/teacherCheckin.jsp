<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var t_tcheckin_datagrid;
	$(function() {
		t_tcheckin_datagrid = $('#t_tcheckin_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/checkin/checkinAction!tDatagrid.action',
			fit : true,
			fitColumns : true,//使得列之间相互充满，当列比较多的时候不要使用
			cache : false,
			nowrap : false,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'classSeq',
			sortOrder : 'asc',
			checkOnSelect : true,//选为false时，点击行不会选中复选框
			selectOnCheck : false,//为false时，点击复选框，不让选中行
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 10,
				checkbox : true
			}, {
				field : 'cname',
				title : '课程名称',
				width : 80,
				sortable : true,
			}, {
				field : 'stuname',
				title : '学生姓名',
				width : 80,
				sortable : true,
			} ] ],
			columns : [ [ {
				field : 'classSeq',
				title : '第几次签到',
				width : 100
			}, {
				field : 'checkintime',
				title : '签到时间',
				width : 100
			}, {
				field : 'state',
				title : '是否代签到',
				width : 100
			}  ] ],
			toolbar : [ {
				text : '待开发功能',
				iconCls : 'icon-add',
				handler : function() {
					
				}
			}, '-' ],
			loadFilter : function(data) {
				if (data != null) {
					return data;
				} else {
					$.messager.alert('提示', '查询数据不存在，请确认查询条件后再查询', 'error');
					data = {
						"rows" : [],
						"total" : 0
					};
					return data;
				}
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});

	function searchTcheckinFun() {
		//序列化查询参数
		t_tcheckin_datagrid.datagrid('load', serializeObject($('#t_tcheckin_searchForm')));
	}
	function clearTcheckinFun() {
		$('#t_tcheckin_layout').find('input[name=cname]').val('');
		t_tcheckin_datagrid.datagrid('load', {});
	}
	
</script>

<div id="t_tcheckin_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px;">
		<form id="t_tcheckin_searchForm">
			检索签到课程名称(可模糊查询)：<input name="cname" /> <a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchTcheckinFun();">查询</a> <a
				href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="clearTcheckinFun();">清空</a>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="t_tcheckin_datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="chooseCourse();" data-options="iconCls:'icon-remove'">选课</div>
	</div>
</div>