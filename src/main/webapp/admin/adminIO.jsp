<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('#adminIO_fileform').form({
			url : '${pageContext.request.contextPath}/admin/adminAction!importUserExcel.action',
			success : function(r) {
				var json = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : json.msg
				});
			}
		});
	});
</script>
<style>
<!--
.excel dd dt dl span {
	font-size: 16px;
}

#export_excel {
	float: left;
	width: 300px;
	margin: 10px;
	padding: 15px;
	padding-left: 100px;
	border-right: #000 dashed 1px;
}

#import_excel {
	float: left;
	width: 300px;
	margin: 10px;
	padding: 15px;
	padding-left: 100px;
	border: #000 dashed 0px;
}

#clear {
	clear: both;
}

#description_importAndExport {
	width: 100%;
	position: relative;
	margin: 5px auto;
	display: block;
}
-->
</style>
<div>
	<div id="export_excel">
		<p class="excel">导出所有数据</p>
		<a id="btn" href="/xk/admin/adminAction!exportUserToExcela.action" class="easyui-linkbutton"
			data-options="iconCls:'icon-redo'">导出</a> &nbsp;&nbsp;&nbsp;&nbsp; 
		<a id="btn1" href="/xk/admin/adminAction!exportUserModela.action" class="easyui-linkbutton"
			data-options="iconCls:'icon-redo'">导出模版</a>
	</div>
	<div id="import_excel">
		<form id="adminIO_fileform" method="post" enctype="multipart/form-data">
			<label for="adminIO_file" class="excel">选择要导入的Excel文件:</label> <br> <br> <input
				type="file" name="file" id="adminIO_file" size="8" /> <br> <br> <input
				type="button" value="导入数据" onclick="$('#adminIO_fileform').submit(); " />
		</form>
	</div>
	<div id="clear"></div>
</div>