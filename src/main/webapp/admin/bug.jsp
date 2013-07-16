<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var admin_bug_datagrid;
	$(function() {
		admin_bug_datagrid = $('#admin_bug_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/admin/bugAction!datagrid.action',
			title : 'BUG列表',
			fit : true,
			fitColumns : true,//使得列之间相互充满，当列比较多的时候不要使用
			cache : false,
			nowrap : false,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'bcreatetime',
			sortOrder : 'asc',
			checkOnSelect : true,//选为false时，点击行不会选中复选框
			selectOnCheck : false,//为false时，点击复选框，不让选中行
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 10,
				checkbox : true
			}, {
				field : 'bname',
				title : 'Bug名称',
				width : 50
			}, {
				field : 'bcreatetime',
				title : 'Bug创建时间',
				width : 100,
				sortable : true
			}, {
				field : 'bdesc',
				title : 'BUG描述',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="showBdesc(' + rowIndex + ');">查看详细</a>';
				}
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					appendBug();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					removeBug();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editBug();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					admin_bug_datagrid.datagrid('unselectAll');
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

	function editBug() {
		var rows = admin_bug_datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				title : '修改上报BUG',
				href : '${pageContext.request.contextPath}/admin/bugAction!bugEdit.action?id=' + rows[0].id,
				width : 500,
				height : 450,
				buttons : [ {
					text : '修改',
					handler : function() {
						var f = p.find('form');
						f.form({
							url : '${pageContext.request.contextPath}/admin/bugAction!edit.action',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									admin_bug_datagrid.datagrid('reload');
									p.dialog('close');
								}
								parent.sy.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
						f.submit();
					}
				} ],
				onLoad : function() { //实现xheditor编辑，上传文件
					var f = p.find('form');
					f.find('input[name=id]').val(rows[0].id);
					f.find('input[name=bname]').val(rows[0].bname);
					var editor = f.find('textarea[name=bdesc]').xheditor({
						tools : 'full',
						html5Upload : true,
						upMultiple : 4,
						upLinkUrl : '${pageContext.request.contextPath}/admin/bugAction!upload.action',
						upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
						upImgUrl : '${pageContext.request.contextPath}/admin/bugAction!upload.action',
						upImgExt : 'jpg,jpeg,gif,png',
						upMediaUrl : '${pageContext.request.contextPath}/admin/bugAction!upload.action',
						upMediaExt : 'wmv,avi,wma,mp3,mid'
					});
				}
			});
		} else if (rows.length > 1) {
			parent.sy.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.sy.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function appendBug() {
		var p = parent.sy.dialog({
			title : '上报BUG',
			href : '${pageContext.request.contextPath}/admin/bugAction!bugAdd.action',
			width : 800,
			height : 600,
			buttons : [ {
				text : '上报',
				handler : function() {
					var f = p.find('form');
					f.form({
						url : '${pageContext.request.contextPath}/admin/bugAction!add.action',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								admin_bug_datagrid.datagrid('reload');
								p.dialog('close');
							}
							parent.sy.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
					f.submit();
				}
			} ],
			onLoad : function() {
				var f = p.find('form');
				/*找到textarea，并且用xheditor初始化textarea
				下面规定上传文件的类型与上传图片的类型
				 */
				var editor = f.find('textarea[name=bdesc]').xheditor({
					tools : 'full',
					html5Upload : true,
					upMultiple : 4,
					upLinkUrl : '${pageContext.request.contextPath}/admin/bugAction!upload.action',
					upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
					upImgUrl : '${pageContext.request.contextPath}/admin/bugAction!upload.action',
					upImgExt : 'jpg,jpeg,gif,png',
					upMediaUrl : '${pageContext.request.contextPath}/admin/bugAction!upload.action',
					upMediaExt : 'wmv,avi,wma,mp3,mid'
				});
			}
		});
	}
	
	function removeBug() {
		var rows = admin_bug_datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.sy.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/admin/bugAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							admin_bug_datagrid.datagrid('load');
							admin_bug_datagrid.datagrid('unselectAll');
							parent.sy.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.sy.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	
	function showBdesc(rowIndex) {
		var rows = admin_bug_datagrid.datagrid('getRows');
		var row = rows[rowIndex];

		var p = parent.sy.dialog({
			title : 'BUG名称[' + row.bname + ']',
			modal : true,
			maximizable : true,
			width : 800,
			height : 600,
			content : '<iframe src="${pageContext.request.contextPath}/admin/bugAction!showCdesc.action?id=' + row.id + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'
		});

		admin_bug_datagrid.datagrid('unselectAll');
	}
	
	function searchBugFun() {
		//序列化查询参数
		admin_bug_datagrid.datagrid('load', serializeObject($('#admin_bug_searchForm')));
	}
	function clearBugFun() {
		$('#admin_bug_layout').find('input[name=bname]').val('');
		admin_bug_datagrid.datagrid('load', {});
	}
	
</script>

<div id="admin_bug_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px;">
		<form id="admin_bug_searchForm">
			检索Bug名称(可模糊查询)：<input name="bname" /> <a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchBugFun();">查询</a> <a
				href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="clearBugFun();">清空</a>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="admin_bug_datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="appendBug();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="removeBug();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="editBug();" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
</div>