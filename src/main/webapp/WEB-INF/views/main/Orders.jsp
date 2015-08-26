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
	        					<div class="order_header_item activityStyle" id="all_orders">全部订单</div>
	        				</li>
	        				<li>
	        					<div class="order_header_item activityStyle" id="prepare_pay_orders">待付款</div>
	        				</li>
	        				<li>
	        					<div class="order_header_item activityStyle" id="prepare_over_orders">待收货</div>
	        				</li>
	        			</ul>
	        		</div>
	        		
	        		<div class="order_goods">
	        			<ul>
	        			<c:forEach items="${orders}" var="order">
	        				<li>
	        					<div class="order_good activityStyle">
	        						<div class="order_good_time">${order.createTime}</div>
	        						<div class="order_good_p1">
	        							<div class="order_good_detail">
	        								<img src="${order.goodImg}"/>
	        								<div class="order_good_name">${order.goodName}</div>
	        							</div>
	        							<!--  <div class="order_good_price">￥9.9/件</div> -->
	        						</div>
	        						<div class="order_good_p2">
	        							<div class="order_good_logistics">共${order.num}件商品，免运费</div>
	        							
	        							<div class="order_good_payment">实付：<b>￥${order.cost}</b></div>
	        						</div>
	        						<div class="order_good_p3">
	        						<!--1表示未付款 2表示已付款 未发货 3表示已发货但未签收 4已签收 5退款未处理6退款已处理 -->
	        							
	        							<div class="order_good_logistics_status">
	        								<c:if test="${order.status==1}">未付款</c:if>
	        								<c:if test="${order.status==2}">已付款，未发货</c:if>
	        								<c:if test="${order.status==3}">已发货，未签收</c:if>
	        								<c:if test="${order.status==4}">已签收</c:if>
	        								<c:if test="${order.status==5}">退款中</c:if>
	        								<c:if test="${order.status==6}">退款完成</c:if>
	        							</div>
	        						</div>
	        					</div>
	        				</li>
	        			</c:forEach>
	        				
	        			</ul>
	        		</div>
	        </div>
    </div>
    <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_order_list");
    			setUserToken("${token.id}");
    			orderActive("${status}");
    			onOrderClick();
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>