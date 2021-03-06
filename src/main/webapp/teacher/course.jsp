<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var t_course_datagrid;
	$(function() {
		t_course_datagrid = $('#t_course_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/course/courseAction!datagrid.action',
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
				field : 'reviewed',
				title : '审核情况',
				width : 100
			}, {
				field : 'bdesc',
				title : '课程简介',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="showCdesc(' + rowIndex + ');">查看详细</a>';
				}
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					appendCourse();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					removeCourse();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editCourse();
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

	function editCourse() {//动态加载节点，最后要销毁节点
		var rows = t_course_datagrid.datagrid('getChecked');//记录选中的行的信息
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				width : 500,
				height : 600,
				href : '${pageContext.request.contextPath}/course/courseAction!courseEdit.action',
				modal : true,
				title : '编辑用户',
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/course/courseAction!edit.action',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									t_course_datagrid.datagrid('reload');
									p.dialog('close');
								}
								$.messager.show({
									title : '提示',
									msg : json.msg
								});
							}
						});
					}
				} ],
				onLoad : function() {//用动态加载时，用onLoad事件做数据填充
					var f = p.find('form');
					f.form('load', rows[0]);
				}
			});
		} else if (rows.length > 1) {
			$.messager.alert('提示', '请选择一条记录进行编辑！', 'error');
		} else {
			$.messager.alert('提示', '请选择要编辑的记录！', 'error');
		}
	}

	function searchCourseFun() {
		//序列化查询参数
		t_course_datagrid.datagrid('load', serializeObject($('#t_course_searchForm')));
	}
	function clearCourseFun() {
		$('#t_course_layout').find('input[name=cname]').val('');
		t_course_datagrid.datagrid('load', {});
	}

	function appendCourse() {
		var p = parent.sy.dialog({
			title : '添加用户',
			href : '${pageContext.request.contextPath}/course/courseAction!courseAdd.action',
			width : 500,
			height : 600,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/course/courseAction!add.action',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								t_course_datagrid.datagrid('reload');
								p.dialog('close');
							}
							parent.sy.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			} ]
		});
	}

	function removeCourse() {
		var rows = t_course_datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/course/courseAction!remove.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(r) {
							t_course_datagrid.datagrid('load');
							t_course_datagrid.datagrid('unselectAll');
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

	function showCdesc(rowIndex) {
		var rows = t_course_datagrid.datagrid('getRows');
		var row = rows[rowIndex];

		var p = parent.sy.dialog({
			title : '课程名称[' + row.cname + ']',
			modal : true,
			maximizable : true,
			width : 800,
			height : 600,
			content : '<iframe src="${pageContext.request.contextPath}/course/courseAction!showCdesc.action?id=' + row.id + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'
		});

		t_course_datagrid.datagrid('unselectAll');
	}
</script>

<div id="t_course_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px;">
		<form id="t_course_searchForm">
			检索用户名称(可模糊查询)：<input name="cname" /> <a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchCourseFun();">查询</a> <a
				href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="clearCourseFun();">清空</a>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="t_course_datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="appendCourse();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="removeCourse();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="editCourse();" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
</div>
