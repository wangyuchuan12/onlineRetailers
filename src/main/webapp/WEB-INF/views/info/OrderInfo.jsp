<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
    <div class="container" >
       <div class="order_info">
       	<div class="order_info_flow">
       		<ul>
	       		<li>
	       			<div class="order_info_flow_block order_info_flow_active">
		       			<div class="fa fa-building"></div>
		       			<div class="order_info_flow_font">提交订单</div>
		       			<div class="order_info_flow_line"></div>
	       			</div>
	       		</li>
	       		<li>
	       			<div class="order_info_flow_block">
		       			<div class="fa fa-paper-plane"></div>
		       			<div class="order_info_flow_font">配送中</div>
		       			<div class="order_info_flow_line"></div>
	       			</div>
	       		</li>
	       		<li>
	       			<div class="order_info_flow_block">
		       			<div class="fa fa-truck"></div>
		       			<div class="order_info_flow_font">签收成功</div>
	       			</div>
	       		</li>
       		</ul>
       	</div>
       	<div class="order_info_detail">
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">订单状态：</div>
       			<div class="order_info_detail_content_b">已签收</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">总额：</div>
       			<div class="order_info_detail_content_b">9.90</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">送至：</div>
       			<div class="order_info_detail_content">杭州西湖区 西斗门路天堂软件园A幢F座微纳科技有限公司</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">收货人：</div>
       			<div class="order_info_detail_content">王煜川 13738139702</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">订单编号：</div>
       			<div class="order_info_detail_content">20150602-330184792</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">下单时间：</div>
       			<div class="order_info_detail_content">2015-06-02 19:51:17</div>
       		</div>
       		<div class="order_info_detail_block">
       			<div class="order_info_detail_header">配送方法：</div>
       			<div class="order_info_detail_content">圆通快递</div>
       			<div class="order_info_detail_number">802965705849</div>
       		</div>
       	</div>
       </div>
       <div class="order_info_good_info">
       	<div class="order_info_good_info_header">商品信息</div>
       	<div class="order_info_good_info_detail">
       		<img src="/img/good.jpg"/>
       		<div class="order_info_good_info_name">新鲜荔枝2斤9.9元</div>
       		<div class="order_info_good_info_num">数量：1</div>
       		<div class="order_info_good_info_price">￥9.90/件</div>
       	</div>
       	<div class="order_info_good_info_btns">
       		<div class="order_info_good_info_btn_group_detail">
       			查看团详情
       		</div>
       	</div>
       </div>
     </div>
</tiles:putAttribute>
</tiles:insertDefinition>
