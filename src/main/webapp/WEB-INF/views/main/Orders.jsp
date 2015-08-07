<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div class="container">
	        <div class="orders">
	        		<div class="order_header">
	        			<ul>
	        				<li>
	        					<div class="order_header_item activityStyle">全部订单</div>
	        				</li>
	        				<li>
	        					<div class="order_header_item activityStyle">待付款</div>
	        				</li>
	        				<li>
	        					<div class="order_header_item activityStyle">待收货</div>
	        				</li>
	        			</ul>
	        		</div>
	        		
	        		<div class="order_goods">
	        			<ul>
	        				<li>
	        					<div class="order_good activityStyle">
	        						<div class="order_good_time">2015-07-04 10:45:44</div>
	        						<div class="order_good_p1">
	        							<div class="order_good_detail">
	        								<img src="/img/good.jpg"/>
	        								<div class="order_good_name">广西小台芒2斤9.9</div>
	        								<div class="order_good_num">数量1</div>
	        							</div>
	        							<div class="order_good_price">￥9.9/件</div>
	        						</div>
	        						<div class="order_good_p2">
	        							<div class="order_good_logistics">共1件商品，免运费</div>
	        							<div class="order_good_payment">实付：<b>￥29.90</b></div>
	        						</div>
	        						<div class="order_good_p3">
	        							<div class="order_good_logistics_status">未发货退款成功</div>
	        						</div>
	        					</div>
	        				</li>
	        				
	        				
	        			</ul>
	        		</div>
	        </div>
    </div>
    <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_order_list");
    			setUserToken("${token.id}");
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>