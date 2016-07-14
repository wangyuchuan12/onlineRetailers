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
    <link rel="stylesheet" href="/css/mystyle.css"/>
    <link rel="stylesheet"
	href="/css/font-awesome.min.css">
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="/js/myscript.js"></script>
	<script type="text/javascript" src="/js/jquery-2.1.4.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="good_info_pay">

        	<div class="good_info_pay_recipients" onclick="javascript:goodInfoPayAddressOnClick('${payGoodInfo.pay_type}','${payGoodInfo.pay_type}','${payGoodInfo.good_id}','${payGoodInfo.groupId}');">
        		
        		<div class="good_info_pay_recipients_prefix">送至</div>
        		<div class="good_info_pay_recipients_address">${payGoodInfo.address}</div>
        		<div class="good_info_pay_recipients_name"><b>${payGoodInfo.person_name}</b>${payGoodInfo.phonenumber}</div>
        		<div class="good_info_pay_recipients_suffix">&gt</div>
        		
        	</div>
        	<div class="good_info_pay_goodinfo">
        		<img src="${payGoodInfo.head_img}"/>
        		<div class="good_info_pay_goodinfo_detail">
        			<div class="good_info_pay_goodinfo_detail_title">${payGoodInfo.name}</div>
        			<div class="good_info_pay_goodinfo_detail_num">数量：1</div>
        			<div class="good_info_pay_goodinfo_detail_stock">库存：${payGoodInfo.stock}件</div>
        		</div>
        		<div class="good_info_pay_goodinfo_price">	
        			<c:if test="${payGoodInfo.pay_type==0}">
        				￥<fmt:formatNumber type="number" value="${payGoodInfo.group_cost}" maxFractionDigits="3"/>
        			</c:if>
        			
        			<c:if test="${payGoodInfo.pay_type==1}">
        				￥<fmt:formatNumber type="number" value="${payGoodInfo.alone_cost}" maxFractionDigits="3"/>
        			</c:if>
        			
        			<c:if test="${payGoodInfo.pay_type==2}">
        				${payGoodInfo.coupon_cost}张开团劵
        			</c:if>
        			
        			<c:if test="${payGoodInfo.pay_type==3}">
        				￥<fmt:formatNumber type="number" value="${payGoodInfo.group_cost}" maxFractionDigits="3"/>
        			</c:if>
        			/件
        		</div>
        	</div>
        	<div class="good_info_pay_price">
        		<div class="good_info_pay_price_content">
        			<c:if test="${payGoodInfo.pay_type=='0'}">${payGoodInfo.group_num}人团  &nbsp; &nbsp; </c:if>
        			<c:if test="${payGoodInfo.pay_type==1}">单买 &nbsp; &nbsp; </c:if>
        			<c:if test="${payGoodInfo.pay_type==2}">使用开团劵 &nbsp; &nbsp; </c:if>
        			 快递：￥${payGoodInfo.flow_price} &nbsp;总价：<b>￥<fmt:formatNumber type="number" value="${payGoodInfo.cost}" maxFractionDigits="3"/></b></div>
        	</div>
        	<c:if test="${fn:length(goodStyles)>0}">
	        	<div class="good_info_pay_good_style">
	        		<ul>
	        			<c:forEach items="${goodStyles}" var="goodStyle">
	        				
	        					<li>
	        						<div class="good_info_pay_good_style_item" onclick="javascript:selectStyle('${goodStyle.id}')">
	        							<img src="${goodStyle.imgUrl}"  id="select_${goodStyle.id}">
	        							<div class="good_info_pay_good_style_item_name">${goodStyle.name}</div>
	        						</div>
	        						
	        					</li>
	        			</c:forEach>
	        		</ul>
	        	</div>
        	</c:if>
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
	        	<div class="good_info_pay_button" onclick="onChooseWXPay('${appId}','${pack}','${nonceStr}','${paySign}','${signType}','${timestamp}','${payGoodInfo.good_id}','${payGoodInfo.pay_type}','${payGoodInfo.pay_type}','${token.id}','${payGoodInfo.address}','${payGoodInfo.person_name}','${payGoodInfo.phonenumber}','${outTradeNo}');">
	        		<a id=“good_info_pay_button_href”>立即支付</a>
	        	</div>
	        	 <div class="trade_flow">
            	<div class="trade_flow_details" onclick="window.location.href='/info/trade_flow_info'">查看详情&gt;</div>
            	<ul>
            		<li>
            			<div class="trade_flow_block">
            				<i class="fa fa-search"></i><span>选择商品</span>
            				<div class="trade_flow_num">1</div>
            			</div>
            			
            		</li>
            		<li>
            			<div class="trade_flow_block trade_flow_block_activity">
            				<i class="fa fa-shopping-cart"></i><span >开团支付</span>
            				<div class="trade_flow_num">2</div>
            			</div>
            		</li>
            		<li>
            			<div class="trade_flow_block">
            				<i class="fa fa-users"></i><span>等待组团</span>
            				<div class="trade_flow_num">3</div>
            			</div>
            		</li>
            		<li>
            			<div class="trade_flow_block">
            				<i class="fa fa-flag"></i><span>购买完成</span>
            				<div class="trade_flow_num">4</div>
            			</div>
            		</li>
            	</ul>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
		function selectStyle(styleId){
			var htmlobj=$.ajax({url:"/api/set_good_style?temp_group_order_id=${tempGroupOrderId}&good_style_id="+styleId+"&token=${token.id}",async:false});
			htmlobj = eval("("+htmlobj.responseText+")");
			if(htmlobj.result){
				$(".good_info_pay_good_style_select").removeClass("good_info_pay_good_style_select");
				$("#select_"+styleId).addClass("good_info_pay_good_style_select");
				
			}
		}
    		$(document).ready(function(){
    			setUserToken("${token.id}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("${typeName}","${typeTitle}","${domainName}/main/good_list?good_type=${goodType}","${typeImg}","link");
    				wx.hideMenuItems({
    				    menuList: ["menuItem:copyUrl","menuItem:exposeArticle","menuItem:setFont","menuItem:readMode","menuItem:originPage","menuItem:share:email","menuItem:openWithQQBrowser","menuItem:openWithSafari"] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
    				});
    				wx.showMenuItems({
    		            menuList: [
    		                "menuItem:profile",// 添加查看公众号
    		                "menuItem:addContact"
    		            ]
    		        	});
    			});
    		});
    </script>
</html>