<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var admin_user_datagrid;

	$(function() {
		admin_user_datagrid = $('#admin_user_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/user/userAction!datagrid.action',
			fit : true,
			fitColumns : true,//使得列之间相互充满，当列比较多的时候不要使用
			cache : false,
			nowrap : false,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'idd',
			sortOrder : 'asc',
			checkOnSelect : true,//选为false时，点击行不会选中复选框
			selectOnCheck : false,//为false时，点击复选框，不让选中行
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 10,
				checkbox : true
			}, {
				field : 'idd',
				title : '用户号',
				width : 80,
				sortable : true,
			},{
				field : 'uname',
				title : '登录名称',
				width : 80,
				sortable : true,
			} ] ],
			columns : [ [ {
				field : 'pwd',
				title : '密码',
				width : 50,
				formatter : function(value, row, index) {
					return '******';
				}
			}, {
				field : 'createtime',
				title : '创建时间',
				width : 100,
				sortable : true
			}, {
				field : 'modifytime',
				title : '最后修改时间',
				width : 100,
				sortable : true
			}, {
				field : 'phone',
				title : '手机',
				width : 100
			}, {
				field : 'email',
				title : '邮箱',
				width : 100
			}, {
				title : '所属角色',
				field : 'role',
				width : 100
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					appendUser();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					removeUser();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editUser();
				}
			}, '-' ],
			loadFilter : function(data) {
				if (data !=null ) {
					return data;
				}else{
					$.messager.alert('提示', '查询数据不存在，请确认查询条件后再查询', 'error');
					data={"rows":[],"total":0};
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

	function editUser() {//动态加载节点，最后要销毁节点
		var rows = admin_user_datagrid.datagrid('getChecked');//记录选中的行的信息
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				width : 400,
				height : 300,
				href : '${pageContext.request.contextPath}/user/userAction!userEdit.action',
				modal : true,
				title : '编辑用户',
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/user/userAction!edit.action',
							onSubmit : function() {

							},
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									admin_user_datagrid.datagrid('reload');
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

	function searchFun() {
		//序列化查询参数
		admin_user_datagrid.datagrid('load', serializeObject($('#admin_user_searchForm')));
	}
	function clearFun() {
		$('#admin_user_layout').find('input[name=uname]').val('');
		admin_user_datagrid.datagrid('load', {});
	}
	function appendUser() {
		var p = parent.sy.dialog({
			title : '添加用户',
			href : '${pageContext.request.contextPath}/user/userAction!userAdd.action',
			width : 400,
			height : 300,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/user/userAction!add.action',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								admin_user_datagrid.datagrid('reload');
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

	function removeUser() {
		var rows = admin_user_datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/user/userAction!remove.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(r) {
							admin_user_datagrid.datagrid('load');
							admin_user_datagrid.datagrid('unselectAll');
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
</script>
<div id="admin_user_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px;">
		<form id="admin_user_searchForm">
			检索用户名称(可模糊查询)：<input name="uname" /> <a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a> <a
				href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="clearFun();">清空</a>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="admin_user_datagrid"></table>
	</div>
	
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="appendUser();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="removeUser();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="editUser();" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
</div>