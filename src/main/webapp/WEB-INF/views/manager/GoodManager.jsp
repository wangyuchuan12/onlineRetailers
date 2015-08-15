<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<html>
	<head>
		<link href="/ext-3.4.0/resources/css/ext-all.css"  type="text/css" rel="stylesheet"/>
		<script src="/ext-3.4.0/adapter/ext/ext-base.js"></script>
		<script src="/ext-3.4.0/ext-all.js"></script>
		<script src="/js/extjs/goods.js"></script>
		<script src="/js/extjs/goodInfo.js"></script>
		
		
		<script src="/js/extjs/goodController.js"></script>
		
		<script src="/js/extjs/goodInfoForm.js"></script>
		<script src="/js/extjs/imageManager.js"></script>
		<script src="/js/extjs/fiewUploadWin.js"></script>
	</head>
	<body>
		<script>
		Ext.onReady(function(){
	
			var goodMainGrid = new GoodMainGrid();
			var goodAddForm = new GoodInfo();
			var goodUpdateForm = new GoodInfo();
			var imageManager = new ImageManager();
			goodMainGrid.render(Ext.getBody());
			var controller = new GoodController(goodMainGrid,goodAddForm,goodUpdateForm,imageManager);
	
			/*
			var imageManager = new ImageManager();
			
			imageManager.render(Ext.getBody());
			imageManager.addItem("https://www.baidu.com/img/bd_logo1.png","123");
			imageManager.doLayout();
			imageManager.on("imageItemOnClick",function(id,panel){alert("imageItemOnClick");});
			imageManager.on("uploadClick",function(id,panel){alert("uploadClick"+" id:"+id+" item:"+panel);});
			imageManager.on("updateClick",function(id,panel){alert("updateClick");});
			imageManager.on("removeClick",function(id,panel){alert("removeClick");});
			*/
			
		});
	</script>
	</body>
</html>
