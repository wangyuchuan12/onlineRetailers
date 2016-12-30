<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
   <meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
 <meta name="format-detection" content="telephone=no">
 <meta http-equiv="Pragma" content="no-cache">   
 <meta http-equiv="Cache-Control" content="no-store">
 <meta http-equiv="Expires" content="0">
    <meta name="description" content="" />
    <link rel="stylesheet" href="/css/core.css"/>
    <link rel="stylesheet" href="/m/css/manager.css"/>
    <link rel="stylesheet"
	href="/font-awesome-4.7.0/css/font-awesome.min.css">
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="/js/jquery-2.1.4.min.js"></script>
</head>
	<body>
	<div class="container" >
		<div class="manager_goods_list">
			<div class="manager_goods_item">
				<div class="manager_goods_item_detail">
					<div class="manager_goods_item_detail_img">
						<img src="http://wx.qlogo.cn/mmopen/xPXrGtX7mxzVLiacuLGmhCugiccm8EcaPiaFYOaQ74EPBGFvkV0U7Mgy35raJRJfHfY3RF5XQwvscTDCzZuicTe5HA/0">
					</div>
					<div class="manager_goods_item_detail_content">
						<div class="manager_goods_item_detail_name">苹果</div>
						
						<div class="manager_goods_item_detail_price">团购价 ￥30</div>
						<div class="manager_goods_item_detail_price">单买价格 ￥50</div>
						<div class="manager_goods_item_detail_price">市场价格 ￥50</div>
					</div>
					
					<div class="manager_goods_item_detail_gt">&gt;</div>
				</div>
				
				<div class="manager_goods_item_tool">
					<ul>
						
						<li>
							<em class="fa fa-pencil-square-o"></em>
							<div class="manager_goods_item_tool_name">修改</div>
						</li>
						
						<li>
							<em class="fa fa-times"></em>
							<div class="manager_goods_item_tool_name">删除</div>
						</li>
						
						<li>
							<em class="fa fa-product-hunt"></em>
							<div class="manager_goods_item_tool_name">发布</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="manager_goods_tool">
			<div class="manager_goods_tool_item">
				<em class="fa fa-plus"></em>
				<div class="manager_goods_tool_name">添加商品</div>
			</div>
			
			
			<div class="manager_goods_tool_item">
				<em class="fa fa-dropbox"></em>
				<div class="manager_goods_tool_name">分类管理</div>
			</div>
		</div>
	</div>
	</body>

</html>