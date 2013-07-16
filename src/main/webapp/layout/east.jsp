<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	var onlineDatagrid;
	var onlinePanel;
	var calendar;
	var userOnlineInfoDialog;
	var userOnlineInfoDataGrid;
	$(function() {

		calendar = $('#calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" style="height:180px;overflow: hidden;">
		<div id="calendar"></div>
	</div>
</div>