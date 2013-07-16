<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var s_course_datagrid;
	$(function() {
		s_course_datagrid = $('#s_course_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/student/studentAction!datagrid.action',
			fit : true,
			fitColumns : true,//使得列之间相互充满，当列比较多的时候不要使用
			cache : false,
			nowrap : false,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'ctime',
			sortOrder : 'asc',
			checkOnSelect : true,//选为false时，点击行不会选中复选框
			selectOnCheck : false,//为false时，点击复选框，不让选中行
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 10,
				checkbox : true
			}, {
				field : 'cid',
				title : '课程号',
				width : 80,
				sortable : true,
			}, {
				field : 'cname',
				title : '课程名称',
				width : 80,
				sortable : true,
			} ] ],
			columns : [ [ {
				field : 'ctime',
				title : '截止时间',
				width : 100
			}, {
				field : 'maxnum',
				title : '最大选课人数',
				width : 100
			}, {
				field : 'exitnum',
				title : '已选人数',
				width : 100
			}, {
				field : 'xkstate',
				title : '是否已选该课',
				width : 100
			}, {
				field : 'cdesc',
				title : '课程简介',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="showSdesc(' + rowIndex + ');">查看详细</a>';
				}
			} ] ],
			toolbar : [ {
				text : '选课',
				iconCls : 'icon-add',
				handler : function() {
					chooseCourse();
				}
			}, '-',{
				text : '签到',
				iconCls : 'icon-add',
				handler : function() {
					signCourse();
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

	function searchSCourseFun() {
		//序列化查询参数
		s_course_datagrid.datagrid('load', serializeObject($('#s_course_searchForm')));
	}
	function clearSCourseFun() {
		$('#s_course_layout').find('input[name=cname]').val('');
		s_course_datagrid.datagrid('load', {});
	}
	
	function chooseCourse() {
		var rows = s_course_datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				if (rows[i].xkstate=="已选") {
					$.messager.alert('警告', '不能选择已选择的[-' + rows[i].cname + '-]课程', 'error');
					return;
				}
			}
		} else {
			$.messager.alert('提示', '请选择要编辑的记录！', 'error');
			return;
		}
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要选修当前选中的课程？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/student/studentAction!choose.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(r) {
							s_course_datagrid.datagrid('load');
							s_course_datagrid.datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}

	function signCourse(){
		var rows = s_course_datagrid.datagrid('getChecked');
		if (rows.length == 1) {		
			$.messager.confirm('确认', '签到当前选中的课程？', function(r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/checkin/checkinAction!sign.action',
						data : {
							cid : rows[0].id,
						},
						dataType : 'json',
						success : function(r) {
							s_course_datagrid.datagrid('load');
							s_course_datagrid.datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
			});
		}  else if (rows.length > 1) {
			$.messager.alert('提示', '请选择一条记录进行敲到！', 'error');
		} else {
			$.messager.alert('提示', '请选择要签到的课程！', 'error');
		}
	}
	
	function showSdesc(rowIndex) {
		var rows = s_course_datagrid.datagrid('getRows');
		var row = rows[rowIndex];

		var p = parent.sy.dialog({
			title : '课程名称[' + row.cname + ']',
			modal : true,
			maximizable : true,
			width : 800,
			height : 600,
			content : '<iframe src="${pageContext.request.contextPath}/student/studentAction!showSdesc.action?id=' + row.id + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'
		});

		s_course_datagrid.datagrid('unselectAll');
	}
</script>

<div id="s_course_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px;">
		<form id="s_course_searchForm">
			检索用户名称(可模糊查询)：<input name="cname" /> <a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchSCourseFun();">查询</a> <a
				href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="clearSCourseFun();">清空</a>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="s_course_datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="chooseCourse();" data-options="iconCls:'icon-remove'">选课</div>
	</div>
</div>
