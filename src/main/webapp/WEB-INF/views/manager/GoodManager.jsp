<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<html>
	<head>
		<link href="/ext-3.4.0/resources/css/ext-all.css"  type="text/css" rel="stylesheet"/>
		<script src="/ext-3.4.0/adapter/ext/ext-base.js"></script>
		<script src="/ext-3.4.0/ext-all.js"></script>
		<script src="/js/goodManager/goods.js"></script>
		<script src="/js/goodManager/goodInfo.js"></script>
		<script src="/js/goodManager/goodController.js"></script>	
		<script src="/js/goodManager/goodInfoForm.js"></script>
		<script src="/js/goodManager/imageManager.js"></script>
		<script src="/js/goodManager/fiewUploadWin.js"></script>
		<script src="/js/goodManager/sourceForm.js"></script>
		<script src="/js/orderManager/orders.js"></script>
	</head>
	<body>
		<script>
		Ext.onReady(function(){

			var goodMainGrid = new GoodMainGrid();
			var goodAddForm = new GoodInfo();
			var goodUpdateForm = new GoodInfo();
			var imageManager = new ImageManager();
			var sourceForm = new SourceForm();
			goodMainGrid.render(Ext.getBody());
			var controller = new GoodController(goodMainGrid,goodAddForm,goodUpdateForm,imageManager,sourceForm);

			/*
			var orderMainGrid = new OrderMainGrid();
			orderMainGrid.render(Ext.getBody());
			
			orderMainGrid.on("sendClick",function(record){alert("sendClick"+record);});
			*/
			
		});
	</script>
	</body>
</html>
