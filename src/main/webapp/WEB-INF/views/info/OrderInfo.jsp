<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
    <div class="container" >
       <div class="order_info">
       	<div class="order_info_flow">
       		<ul>
	       		<li>
	       			<div class="order_info_flow_block <c:if test='${order.status==1||order.status==2}'>order_info_flow_active</c:if>">
		       			<div class="fa fa-building"></div>
		       			<div class="order_info_flow_font">提交订单</div>
		       			<div class="order_info_flow_line"></div>
	       			</div>
	       			 
	       		</li>
	       		<li>
	       			<div class="order_info_flow_block <c:if test='${order.status==3}'>order_info_flow_active</c:if>">
		       			<div class="fa fa-paper-plane"></div>
		       			<div class="order_info_flow_font">配送中</div>
		       			<div class="order_info_flow_line"></div>
	       			</div>
	       		</li>
	       		<li>
	       			<div class="order_info_flow_block <c:if test='${order.status==4||order.status==5||order.status==6||order.status==7}'>order_info_flow_active</c:if>">
		       			<div class="fa fa-truck"></div>
		       			<div class="order_info_flow_font">签收成功</div>
	       			</div>
	       		</li>
       		</ul>
       	</div>
       	<div class="order_info_detail">
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">订单状态：</div>
       			<div class="order_info_detail_content_b">
       				<c:if test='${order.status==1}'>未付款</c:if>
       				<c:if test='${order.status==2}'>已付款 未发货</c:if>
       				<c:if test='${order.status==3}'>已发货 未签收</c:if>
       				<c:if test='${order.status==4}'>已签收</c:if>
       				<c:if test='${order.status==5}'>退款未处理</c:if>
       				<c:if test='${order.status==6}'>退款已处理</c:if>
       				<c:if test='${order.status==7}'>已取消</c:if>
       			</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">总额：</div>
       			<div class="order_info_detail_content_b">${order.cost}</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">送至：</div>
       			<div class="order_info_detail_content">${order.area}</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">收货人：</div>
       			<div class="order_info_detail_content">${order.recipient} ${order.phonenumber}</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">订单编号：</div>
       			<div class="order_info_detail_content">${order.code}</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">下单时间：</div>
       			<div class="order_info_detail_content">${order.createTime}</div>
       		</div>
       		<!--  <div class="order_info_detail_block">
       			<div class="order_info_detail_header">配送方法：</div>
       			<div class="order_info_detail_content">圆通快递</div>
       			 <div class="order_info_detail_number">802965705849</div>
       		</div>
       		-->
       	</div>
       </div>
       <div class="order_info_good_info">
       	<div class="order_info_good_info_header">商品信息</div>
       	<div class="order_info_good_info_detail">
       		<img src="${order.goodImg}"/>
       		<div class="order_info_good_info_name">${order.goodName}</div>
       		<div class="order_info_good_info_num">数量：1</div>
       		<div class="order_info_good_info_price">￥${order.goodPrice}/件</div>
       	</div>
       	<c:if test="${order.type==0 || order.type==2}">
	       	<div class="order_info_good_info_btns" onclick="javascript:skipToGroupInfo('${order.groupId}');">
	       		<div class="order_info_good_info_btn_group_detail">
	       			查看团详情
	       		</div>
	       	</div>
       	</c:if>
       	
       	
       </div>
     </div>
      <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_order_list");
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>
