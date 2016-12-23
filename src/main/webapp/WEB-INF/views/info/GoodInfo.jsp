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
                     <!--     <span>已售：<i id="sold_quantity">${good.salesVolume}</i>件</span>   -->
                        <div class="good_info_good_chat" onclick="javascript:skipToChat('${good.adminId}','1','${good.id}','','','${token.id}')">
                		<img src="http://script.suning.cn/project/pdsWeb/images/online.gif"/>
               	     </div>
                        
                        </div>
                        <div class="good_info_introduce_num">
                        <c:if test="${good.status==1}">
                           支付开团并邀请<span id="tuan_more_need_number">${good.group_num}</span>人参团，人数不足自动退款，详见下方拼团玩法
                        </c:if>
                        <c:if test="${good.status==0}">
                        	对不起，该商品已经下架
						</c:if>
						
						<c:if test="${good.status==2}">
							对不起，该商品已售完
						</c:if>
                        </div>
                    </div>
                    
				<div class="good_info_btns">
					<ul>
						<li>
							<div class="good_info_btn activityStyle <c:if test='${good.status==0||good.status==2}'> good_info_btn_disalbe</c:if>"
								<c:if test='${good.status==1}'>
								onclick="showCheckDetail('${good.group_original_cost*good.group_discount}');"
								</c:if>
								>
								<div class="good_info_btn_price_group">
									￥<fmt:formatNumber type="number" value="${good.group_original_cost*good.group_discount-good.forceInsteadOfRelief}" maxFractionDigits="3"/>/件
								</div>
								<div class="good_info_btn_type">${good.group_num}人团</div>
							</div>
						</li>

						<li>
							<div class="good_info_btn activityStyle <c:if test='${good.status==0||good.status==2}'> good_info_btn_disalbe</c:if>" 
								<c:if test='${good.status==1}'>
								onclick="skipToGoodPay('${good.id}',1)"
								</c:if>
								>
								<div class="good_info_btn_price_alone">￥<fmt:formatNumber type="number" value="${good.alone_discount*good.alone_original_cost}" maxFractionDigits="3"/>/件</div>
								<div class="good_info_btn_type">单独买</div>
							</div>
						</li>
					<c:if test="${couponCount>0}">
						<li>
							<div class="good_info_btn activityStyle <c:if test='${good.status==0||good.status==2||couponCount<1}'> good_info_btn_disalbe</c:if>"
								<c:if test="${couponCount>0&&good.status==1}">
				    					onclick="skipToGoodPay('${good.id}',2)"
				    				</c:if>>
								<div class="good_info_btn_price_integral">${good.coupon_cost}张/件</div>
								<div class="good_info_btn_type">开团劵开团(${couponCount})</div>
							</div>
						</li>
					</c:if>
					</ul>
				</div>
				</div>
				<c:if test="${groupInfo!=null}">
					<div class="good_info_groups" onclick="javascript:skipToGroupInfo('${groupInfo.id}' , '${token.id}')">
						<div class="good_info_groups_head">以下小伙伴正在发起团购，您可以直接参与</div>
						<div class="good_info_group">
							<div class="good_info_group_head"><img src="${groupInfo.headImg}"/></div>
							<div class="good_info_group_info">
								<div class="good_info_group_info1">
									<span class="good_info_group_info_nickname">${groupInfo.nickname}</span>
									<span class="good_info_group_info_num">还差${groupInfo.num}个</span>
								</div>
								<div class="good_info_group_info2">
									<span class="good_info_group_info_city">${groupInfo.city}</span>
									<span class="good_info_group_info_time">剩余
										<b id = "group_info_hour">00</b>
										<b id = "group_info_min">00</b>
										<b id = "group_info_second">00</b>结束
									</span>
									<script type="text/javascript">
										good_info_group_info_time("${groupInfo.startTime}","${groupInfo.timeLong}");
			     					</script>
								</div>
							</div>
						</div>
						<div class="good_info_group_footer">去参团</div>
					</div>
				</c:if>
				
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
        
            <button id="test_button">你在哪里</button>
    </div>

    
    <div class="good_info_check_detail">
    	<div class="good_info_check_detail_head">
    		<div class="good_info_check_detail_head_img">
    			<img alt="" src="http://7xlw44.com1.z0.glb.clouddn.com/6d5341fc-1bc2-48d7-a8c4-b7c61056ba51">
    		</div>
    		
    		<div class="good_info_check_detail_head_content">
    			<div class="good_info_check_detail_head_content_tile">${good.name}</div>
    			<div class="good_info_check_detail_head_content_price">￥45.9</div>
    		</div>
    		
    	</div>
    	
    	<div class="good_info_check_detail_items">
    		<div class="good_info_check_detail_items_i" name="name">
	    		<div class="good_info_check_detail_items_head">是否允许代他人收货（他人可以暂时将货寄放在我这里）</div>
	    		<div class="good_info_check_detail_item" id="good_info_check_detail_item_relief">
	    			<div class="good_info_check_detail_item_apan" value="1" relief="${good.allowInsteadOfRelief}" id="allow">允许
	    			<c:if test="${good.allowInsteadOfRelief!=null}">
	    				（减免${good.allowInsteadOfRelief}元）
	    			</c:if>
	    			
	    			
	    			</div>
	    			
	    			<div class="good_info_check_detail_item_apan" value="0" relief="0" id="unallow">不允许</div>
	    			
	    			<div class="good_info_check_detail_item_apan" value="2" relief="${good.forceInsteadOfRelief}" id="force">强制统一收货
	    			<c:if test="${good.forceInsteadOfRelief!=null}">
	    				（所有人减免${good.forceInsteadOfRelief}元）
	    			</c:if>
	    			
	    			</div>
	    
	    		</div>
	    	</div>
	    
	    	
	    	<!--  <div class="good_info_check_detail_items_i">
	    		<div class="good_info_check_detail_items_head">颜色</div>
	    		<div class="good_info_check_detail_item">
	    			<div class="good_info_check_detail_item_img">
	    				<img src="http://7xlw44.com1.z0.glb.clouddn.com/6d5341fc-1bc2-48d7-a8c4-b7c61056ba51">
	    			</div>
	    			<div class="good_info_check_detail_item_img">
	    				<img src="http://7xlw44.com1.z0.glb.clouddn.com/6d5341fc-1bc2-48d7-a8c4-b7c61056ba51">
	    			</div>
	    			<div class="good_info_check_detail_item_img">
	    				<img src="http://7xlw44.com1.z0.glb.clouddn.com/6d5341fc-1bc2-48d7-a8c4-b7c61056ba51">
	    			</div>
	    			<div class="good_info_check_detail_item_img">
	    				<img src="http://7xlw44.com1.z0.glb.clouddn.com/6d5341fc-1bc2-48d7-a8c4-b7c61056ba51">
	    			</div>
	    		</div>
	    	
	    	</div>
	    -->
    	</div>
    	
    	<div class="good_info_check_detail_button">确定</div>
    </div>
    <script type="text/javascript">
    
    		function showCheckDetail(price){
    			
    			$(".good_info_check_detail").animate({
					bottom:0
				},300);
    			var reliefItem = $("#good_info_check_detail_item_relief");
    			countPrice(price,2);
    		//	alert(reliefItem.children(".good_info_check_detail_item_apan").length);
    			selectSpan(reliefItem.children(".good_info_check_detail_item_apan"),$("#force"));
    			handleSpanItems(reliefItem,function(spanValue){
    				
    				countPrice(price,spanValue)
    			});
    		}
    		
    		
    		function countPrice(price,spanValue){
    			price = parseFloat(price);
    			price = price.toFixed(2);
    			var reliefValue = 0;
    			var reliefItem = $("#good_info_check_detail_item_relief");
    			
    			if(spanValue=="1"){
    				reliefValue = $("#allow").attr("relief");
    			}else if(spanValue=="0"){
    				reliefValue = $("#unallow").attr("relief");
    			}else if(spanValue=="2"){
    				reliefValue = $("#force").attr("relief");
    			}
    			reliefValue = parseFloat(reliefValue);
    			reliefValue = reliefValue.toFixed(2);
    			var result = price-reliefValue;
    			result = result.toFixed(2);
    			$(".good_info_check_detail_head_content_price").text("￥"+result);
    		}
    		$(document).ready(function(){
    			var reliefItem = $("#good_info_check_detail_item_relief");
    			var button = $(".good_info_check_detail_button")
    			button.click(function(){
    				var reliefItemValue = getItemValue($("#good_info_check_detail_item_relief"));
    				var params = new Object();
    				params.isFindOtherInsteadOfReceiving = 0;
    				if(reliefItemValue=="2"){
    					params.isReceiveGoodsTogether = 1;
    					params.isInsteadOfReceiving = 1;
    				}else if(reliefItemValue=="0"){
    					params.isInsteadOfReceiving = 0;
    					params.isReceiveGoodsTogether = 0;
    				}else{
    					params.isInsteadOfReceiving = 1;
    					params.isReceiveGoodsTogether = 0;
    				}
        			skipToGoodPay('${good.id}',0,'${token.id}',null,null,params);
    			});
    			setUserToken("${token.id}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("${good.name}","${good.title}","${domainName}/info/good_info?id=${good.id}&good_type=${goodType}","${good.head_img}","link");
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
