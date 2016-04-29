<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
    <div class="container" >
        <section class="main-view">
            <div id="focus" class="focus goods-focus ect-padding-lr ect-margin-tb">
                <div class="hd">
                    
                </div>
                <div class="bd">
                    <ul>
						
                    </ul>
                </div>
            </div>
            <div class="good_info">
            	<div class="good_info_head_img"><img src="${good.head_img}"></img></div>
                <div class="good_info_introduce">
                    <div class="good_info_introduce_name"><b>${good.name}</b></div>
                    <div class="good_info_title">${good.title}</div>
                    <div class="good_info_introduce_cx"></div>
                    <div class="good_info_introduce_info">
                        <div class="good_info_introduce_price">市场价：<b id="market_price">${good.market_price}</b> 
                        <span>已售：<i id="sold_quantity">${good.salesVolume}</i>件</span>
                        <div class="good_info_good_chat" onclick="javascript:skipToChat('${good.adminId}','1','${good.id}','','','${token.id}')">
                		<img src="http://script.suning.cn/project/pdsWeb/images/online.gif"/>
               	     </div>
                        
                        </div>
                        <div class="good_info_introduce_num">支付开团并邀请<span id="tuan_more_need_number">${good.group_num}</span>人参团，人数不足自动退款，详见下方拼团玩法</div>
                    </div>
                    
				<div class="good_info_btns">
					<ul>
						<li>
							<div class="good_info_btn activityStyle"
								onclick="skipToGoodPay('${good.id}',0)">
								<div class="good_info_btn_price_group">
									￥<fmt:formatNumber type="number" value="${good.group_original_cost*good.group_discount}" maxFractionDigits="3"/>/件
								</div>
								<div class="good_info_btn_type">${good.group_num}人团</div>
							</div>
						</li>

						<li>
							<div class="good_info_btn activityStyle"
								onclick="skipToGoodPay('${good.id}',1)">
								<div class="good_info_btn_price_alone">￥<fmt:formatNumber type="number" value="${good.alone_discount*good.alone_original_cost}" maxFractionDigits="3"/>/件</div>
								<div class="good_info_btn_type">单独买</div>
							</div>
						</li>

						<li>
							<div class="good_info_btn activityStyle"
								<c:if test="${couponCount>0}">
				    					onclick="skipToGoodPay('${good.id}',2)"
				    				</c:if>>
								<c:if test="${couponCount>0}">
									<div class="good_info_btn_price_integral">${good.coupon_cost}张/件</div>
									<div class="good_info_btn_type">开团劵开团(${couponCount})</div>
								</c:if>
								<c:if test="${couponCount==0}">
									<div class="good_info_btn_price_disable">${good.coupon_cost}张/件</div>
									<div class="good_info_btn_type_disable">开团劵开团(${couponCount})</div>
								</c:if>
							</div>
						</li>
					</ul>
				</div>
				</div>
				
				
            </div>
            <div class="trade_flow">
            	<div class="trade_flow_details" onclick="window.location.href='/info/trade_flow_info'">查看详情&gt;</div>
            	<ul>
            		<li>
            			<div class="trade_flow_block trade_flow_block_activity">
            				<i class="fa fa-search"></i><span>选择商品</span>
            				<div class="trade_flow_num">1</div>
            			</div>
            			
            		</li>
            		<li>
            			<div class="trade_flow_block">
            				<i class="fa fa-shopping-cart"></i><span>开团支付</span>
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
			
		<div class="good_info_details">
			<h1>商品简介</h1>
			<p>${good.instruction}</p>
			<div class="images">
				<c:forEach items="${imgs}" var="img">
					<img class="img-responsive" src="${img.url}" alt="">
				</c:forEach>
			</div>
		</div>
        </section>
    </div>
    
    <script type="text/javascript">
    		$(document).ready(function(){
    			setUserToken("${token.id}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("${good.name}","${good.title}","www.chengxihome.com/info/good_info?id=${good.id}&good_type=${goodType}","${good.head_img}","link");
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
</tiles:putAttribute>
</tiles:insertDefinition>
