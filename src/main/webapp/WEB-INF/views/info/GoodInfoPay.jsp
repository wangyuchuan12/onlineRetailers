<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
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
    <link rel="stylesheet" href="/css/mystyle.css"/>
</head>
<body>
    <div class="container">
        <div class="good_info_pay">

        	<div class="good_info_pay_recipients">
        		
        		<div class="good_info_pay_recipients_prefix">送至</div>
        		<div class="good_info_pay_recipients_address">西斗门路天堂软件园A幢F座微纳科技有限公司</div>
        		<div class="good_info_pay_recipients_name"><b>王煜川</b>13738139702</div>
        		<div class="good_info_pay_recipients_suffix">&gt</div>
        		
        	</div>
        	<div class="good_info_pay_goodinfo">
        		<img src="good.jpg"/>
        		<div class="good_info_pay_goodinfo_detail">
        			<div class="good_info_pay_goodinfo_detail_title">广西小台芒2斤9.9元</div>
        			<div class="good_info_pay_goodinfo_detail_num">数量：1</div>
        			<div class="good_info_pay_goodinfo_detail_stock">库存：2000000件</div>
        		</div>
        		<div class="good_info_pay_goodinfo_price">9.90/件</div>
        	</div>
        	<div class="good_info_pay_price">
        		<div class="good_info_pay_price_content">快递：￥0.00 总价：<b>￥9.90</b></div>
        	</div>
        	
        	<div class="good_info_pay_type">
        		<div class="good_info_pay_type_head">请选择支付方式</div>
        		<div class="good_info_pay_type_list">
        			<ul>
        				<li>
        					<div class="good_info_pay_type_item">
        						<div class="good_info_pay_type_item_prefix">√</div>
        						<div class="good_info_pay_type_item_img">
        							<img src="/img/weixin.jpg"/>
        						</div>
        						<div class="good_info_pay_type_item_font">微信支付</div>
        					</div>
        				</li>
        			</ul>
        		</div>
        	</div>

	        	<div class="good_info_pay_button">
	        		<a>立即支付</a>
	        	</div>
	        	<div class="good_info_pay_trade_flow">
                <div class="trade_flow">
				<ul>
					<li>
						<span class="step_panel">
							<i class="trade_flow_choose_colorful"></i><b class="trade_flow_choose_font_colorful">选择商品></b>
						</span>
					</li>
	
					<li>
						<span class="step_panel">
							<i class="trade_flow_pay_gray"></i><b class="trade_flow_font_gray">开团支付></b>
						</span>
					</li>
	
					<li>
						<span class="step_panel">
							<i class="trade_flow_group_gray"></i><b class="trade_flow_font_gray">正在组团></b>
						</span>
					</li>
	
					<li>
						<span class="step_panel">
							<i class="trade_flow_success_gray"></i><b class="trade_flow_font_gray">团购成功></b>
						</span>
					</li>
				</ul>
			</div>
			</div>
        </div>
    </div>
</body>

</html>