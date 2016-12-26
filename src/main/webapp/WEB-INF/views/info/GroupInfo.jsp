<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!doctype html>
<html>
	<head>
		<link rel="stylesheet" href="/css/group_info.css" />
		<link rel="stylesheet" href="/css/core.css" />
		<link rel="stylesheet" href="/css/mystyle.css" />
		<link rel="stylesheet" href="/css/font-awesome.min.css">
		<meta charset="utf-8">
		<meta name="viewport"
			content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		 <script src="/js/myscript.js"></script>
		 <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		 <script type="text/javascript" src="/js/jquery-2.1.4.min.js"></script>
		 <script type="text/javascript" src="/layer/layer.js"></script>
		
		 <title>晨曦拼货商城</title>
	</head>
	<body>
		<div class="container">
			<div class="linkGuid" style="display:none" id="linkGuid"  onclick="javascript:hideLinkGuid();">
						<div class="linkGuid_img">
							<img src="/img/arrow.png"></img>
							<div class="linkGuid_img_content">分享至朋友圈<br/>咻的一下就成团了</div>
						</div>
						<div class="linkGuid_content">
							<c:if test="${groupInfo.role==1}">
								团长大人都是厉害的人物！<br/>
								还差${groupInfo.groupNum-fn:length(groupInfo.groupPartake)}人就能组团成功啦~<br/>快去呼唤小伙伴吧
							</c:if>
							
							<c:if test="${groupInfo.role!=1}">
								小可爱，欢迎你成为本团光荣的一员<br/>
								还差${groupInfo.groupNum-fn:length(groupInfo.groupPartake)}人就能组团成功啦~<br/>快去呼唤小伙伴吧
							</c:if>
						</div>
					</div>
			<c:if test="${groupInfo.result==1}">
				<div class="group_info_head">
		            	<div class="group_info_head_status_progress"><i class="fa fa-check-circle"></i>正在组团</div>
		            	<div class="group_info_head_info_progress">快去邀请好友加入吧</div>
	            	</div>
            	</c:if>
            	
            	
            	<c:if test="${groupInfo.result==2}">
				<div class="group_info_head">
		            	<div class="group_info_head_status_success"><i class="fa  fa-thumbs-up"></i>组团成功</div>
		            	<div class="group_info_head_info_success">恭喜你，组团成功拉</div>
	            	</div>
            	</c:if>
            	
            	<c:if test="${groupInfo.result==0}">
				<div class="group_info_head">
		            	<div class="group_info_head_status_failure"><i class="fa fa-meh-o"></i></div>
		            	<div class="group_info_head_info_failure">团购失败</div>
	            	</div>
            	</c:if>
			<input hidden="true" id = "group_info_img" value="${groupInfo.headImg}">
			<input hidden="true" id = "group_info_good_name" value="${groupInfo.goodName}">
			<input hidden="true" id = "group_info_good_title" value="${groupInfo.title}">
			<div class="group_goodinfo activityStyle" onclick="skipToGoodInfo('${groupInfo.goodId}','${token.id}')" style="width:99.9%">
			      	
					<div class="group_goodinfo_img">
						<img src="${groupInfo.headImg}"></img>
					</div>
					<div class="group_goodinfo_detail">
						<div class="group_goodinfo_detail_title">${groupInfo.goodName}</div>
						<div class="group_goodinfo_footer">&gt</div>
						<div class="group_goodinfo_detail_price">${groupInfo.groupNum}人团：原价 <span class="group_goodinfo_detail_price2" style="TEXT-DECORATION: line-through">￥${groupInfo.totalPrice}</span>
						<br/>
						<c:if test="${groupInfo.isReceiveGoodsTogether==1}">
							<b style="font-size: 15px;">团长统一收货</b>
						</c:if>优惠价<span class="group_goodinfo_detail_price2" id="discountPrice">￥${groupInfo.goodPrice}</span></div>
						<div class="group_goodinfo_detail_notice" style="color: black;">${groupInfo.notice} </div>
						
					</div>
					<!--
						<c:if test="${groupInfo.result==2}">
							<div class="group_head_success" id="group_head">组团成功</div>
						</c:if>
						
						
						<c:if test="${groupInfo.result==1}">
							<div class="group_head_in"  id="group_head">正在组团</div>
						</c:if>
						
						
						<c:if test="${groupInfo.result==0}">
							<div class="group_head_failure"  id="group_head">组团失败</div>
						</c:if>
						
					-->
			</div>
			
			<div class="group_info_good_chat" onclick="javascript:skipToChat('${groupInfo.adminId}','3','','','${groupInfo.id}','${token.id}')">
                				<img src="http://script.suning.cn/project/pdsWeb/images/online.gif"/>
               </div>
               
           <c:if test="${groupInfo.result==1}">
				<div class="groupinfo_situation" id="groupinfo_in">
					<div class="groupinfo_situation_title" id="groupinfo_situation_title">还差<b>${groupInfo.groupNum-fn:length(groupInfo.groupPartake)}</b>人，盼你如南方人盼暖气</div>
					<div class="groupinfo_situation_time">剩余<b id="group_info_hour">00</b><b id="group_info_min">00</b><b id="group_info_second">00</b>结束</div>
				</div>
				<script type="text/javascript">
		     		initGroupInvalidDate("${groupInfo.startTime}","${groupInfo.timeLong}");
		     	</script>
			</c:if>
			<div class="members">
				<ul>
					<c:forEach items="${groupInfo.groupPartake}" var="member">
						<li>
							<div class="member" onclick="scrollToItem('${member.groupPartakeId}');" id="member_object_${member.groupPartakeId}" phonenumber="${member.phonenumber}">
								<img src="${member.headImg}"/>
								<c:if test="${member.role==1}">
									<div class="member_role">
									<span class="member_obvious">团 </span>
									<span class="member_kind">长</span>
									</div>
								</c:if>
								
								<c:if test="${member.role==2}">
									<div class="member_role">
									<span class="member_sofa">沙</span>
									<span class="member_kind">发</span>
									</div>
								</c:if>
								
							</div>
						</li>
					</c:forEach>
					<c:if test="${groupInfo.groupNum-fn:length(groupInfo.groupPartake)>0}">
					<c:forEach begin="1" end="${groupInfo.groupNum-fn:length(groupInfo.groupPartake)}">
						<li>
							<div class="member">
								<img src="/img/question_mark.jpg"/>
							</div>
						</li>
					</c:forEach>
					</c:if>
	
				</ul>
			</div>
			<div class="member_list">
				<ul>
					<c:forEach items="${groupInfo.groupPartake}" var="member">
						<li>
							<div class="member_item" onclick="memberItemOnClick('${member.groupPartakeId}');" id="member_item_${member.groupPartakeId}">
								<img src="${member.headImg}"/>
								<div class="member_item_name"><b>${member.name}</b></div>
								<div class="member_item_time"><b>${member.datetime}<c:if test="${member.role!=1}">参团</c:if><c:if test="${member.role==1}">开团</c:if></b></div>
								<em class="vcon vclose"></em>
								<div id="member_item_log_${member.groupPartakeId}" style="display: none;" flag="1" class="member_item_log">
									<ul>
										<c:forEach items="${member.logs}" var="log" varStatus="status">
											<li>
												<div class="member_item_log_index">${status.index+1}</div>
												<div class="member_item_log_time">${log.happenTime}</div>
												<div class="member_item_log_content">${log.content}</div>
												
											
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
				
				
			</div>
			
			
			<c:if test="${groupInfo.result==0}">
				<div style="color:red;margin: 0 auto;position:relative;text-align: center;">很遗憾，组团失败了</div>
			</c:if>
			<div class="trade_flow">
            	<div class="trade_flow_details" onclick="window.location.href='/info/trade_flow_info'">查看详情&gt;</div>
            	<ul>
            		<li>
            			<div class="trade_flow_block ">
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
            			<div class="trade_flow_block <c:if test='${groupInfo.result==1}'>trade_flow_block_activity</c:if>"> 
            				<i class="fa fa-users"></i><span>等待组团</span>
            				<div class="trade_flow_num">3</div>
            			</div>
            		</li>
            		<li>
            			<div class="trade_flow_block <c:if test='${groupInfo.result==2}'>trade_flow_block_activity</c:if>">
            				<i class="fa fa-flag"></i><span>购买完成</span>
            				<div class="trade_flow_num">4</div>
            			</div>
            		</li>
            	</ul>
            </div>
            <c:if test="${groupInfo.result==2}">
			<div class="footer3" onclick="javascript:skipToGoodList();">
	                <a class="goto_gootlist">我也开个团，点此回商品列表</a>
	     	</div>
     	  </c:if>
     	  
     	  <c:if test="${groupInfo.result==0}">
			<div class="footer3" onclick="javascript:skipToGoodList();">
	                <a class="goto_gootlist">我也开个团，点此回商品列表</a>
	     	</div>
     	  </c:if>
     	  
     	  <c:if test="${groupInfo.result==1}">
     	  	<c:if test="${groupInfo.role==1||groupInfo.role==2||groupInfo.role==3}">
				<div class="footer3" >
					<i class="fa fa-home" onclick="javascript:skipToGoodList();"></i>
		                <a href="javascript:displayLinkGuid();" class="">还差${groupInfo.groupNum-fn:length(groupInfo.groupPartake)}个人，发送链接</a>
		     	</div>
		     	
	     	</c:if>
	     	
	     	
	     	<c:if test="${groupInfo.role==0}">
				<div class="footer3">
		                <i class="fa fa-home" onclick="javascript:skipToGoodList();"></i>
		                <a class="goto_gootlist" href="javascript:takepart();">点击参团</a>
		     	</div>
	     	</c:if>
     	  </c:if>
     	  
     	  
     	  <div class="good_info_check_detail">
    	<div class="good_info_check_detail_head">
    		<div class="good_info_check_detail_head_img">
    			<img alt="" src="${groupInfo.headImg}">
    		</div>
    		
    		<div class="good_info_check_detail_head_content">
    			<div class="good_info_check_detail_head_content_tile">${groupInfo.goodName}</div>
    			<div class="good_info_check_detail_head_content_price">￥0</div>
    		</div>
    		
    	</div>
    	
    	<div class="good_info_check_detail_items">
    		<div class="good_info_check_detail_items_i">
	    		<div class="good_info_check_detail_items_head">找人代收还是自己收货</div>
	    		<div class="good_info_check_detail_item" id="isInstead">
	    			<div class="good_info_check_detail_item_apan" value="1" id="instead">找人代收(减免<b>${groupInfo.insteadOfRelief}</b>)元</div>
	    			
	    			<div class="good_info_check_detail_item_apan" value="0" id="unInstead">自己收货</div>
	    
	    		</div>
	    	</div>
	    	
	    	<div class="good_info_check_detail_items_i" style="display: none;" id="insteadOfReceivingMember">
	    		<div class="good_info_check_detail_items_head">请选择代收人</div>
	    		<div class="good_info_check_detail_item" id="member" type="image">
		    		<c:forEach items="${groupInfo.groupPartake}" var="member">
		    			<c:if test="${member.isInsteadOfReceiving==1}">
			    			<div class="good_info_check_detail_item_img" value="${member.groupPartakeId}">
			    				<img src="${member.headImg}">
			    				<div class="good_info_check_detail_item_img_name">${member.name}</div>
			    			</div>
		    			</c:if>
		    			
		    		</c:forEach>
	    		</div>
	    	
	    	</div>
    	
    	
    		<div class="good_info_check_detail_items_i">
	    		<div class="good_info_check_detail_items_head">是否允许代他人收货（他人可以暂时将货寄放在我这里）</div>
	    		<div class="good_info_check_detail_item" id="good_info_check_detail_item_relief">
	    			<div class="good_info_check_detail_item_apan" id="allowInstead" value="1">允许（减免40元）</div>
	    			
	    			<div class="good_info_check_detail_item_apan" id="unAllowInstead" value="0">不允许</div>
	    
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
    	
    	<div class="good_info_check_detail_button" onclick="orderSubmit();">确定</div>
    </div>
     	  
     	  
     	  
     	</div>
     	
     	
     	 
     	
     	
     	
     	
     	<input type = "hidden" value="${prompt}" name="prompt"></input>
     	<input type = "hidden" value="${groupInfo.goodId}" id="goodId"></input>
     	<input type = "hidden" value="${token.id}" id="tokenId"></input>
     	<input type = "hidden" value="${groupInfo.id}" id="groupId"></input>
     	<input type = "hidden" value="${groupInfo.totalPrice}" id=totalPrice></input>
     	<input type = "hidden" value="${groupInfo.goodPrice}" id=goodPrice></input>
     	<input type = "hidden" value="${groupInfo.allowInsteadOfRelief}" id=allowInsteadOfRelief></input>
     	<input type = "hidden" value="${groupInfo.isReceiveGoodsTogether}" id=isReceiveGoodsTogether></input>
     	<input type = "hidden" value="${groupInfo.insteadOfRelief}" id=insteadOfRelief></input>
     	<input type="hidden" value="${groupInfo.myGroupPartakeId}" id="myGroupPartakeId"></input>
     	<input type="hidden" value="${groupInfo.headGroupPartakeId}" id="headGroupPartakeId"></input>
     	<input type="hidden" value="${groupInfo.hreadGroupPhonenumber}" id="hreadGroupPhonenumber"></input>
     	
     	<input type="hidden" value="${groupInfo.agentGroupPartakeId}" id="apentGroupPartakeId"></input>
     	<input type="hidden" value="${groupInfo.agentGroupPhonenumber}" id="agentGroupPhonenumber"></input>
     	<input type="hidden" value="${groupInfo.forceInsteadOfRelief}" id="forceInsteadOfRelief"/>
     	<script type="text/javascript">
     	
     	function scrollToItem(groupPartakeId){
     		
     		if(!groupPartakeId){
     			return;
     		}else if(!$("#member_item_"+groupPartakeId)){
     			return;
     		}else if(!$("#member_item_"+groupPartakeId).position()){
     			return;
     		}
     		var height = $("#member_item_"+groupPartakeId).position().top-$(".member_list").position().top

     		$(".member_list").animate({scrollTop:height},"slow");
     		memberItemOnClick(groupPartakeId);
     	}
     	
     	function memberItemOnClick(partakeId){
     		var memberItemLog = $("#member_item_log_"+partakeId);
     		if(memberItemLog.attr("flag")=="1"){
     			memberItemLog.slideDown();
     			memberItemLog.attr("flag","0");
     			$("#member_item_"+partakeId+" em").addClass("vopen");
     			$("#member_item_"+partakeId+" em").removeClass("vclose");
     		}else{
     			memberItemLog.slideUp();
     			memberItemLog.attr("flag","1");
     			$("#member_item_"+partakeId+" em").addClass("vclose");
     			$("#member_item_"+partakeId+" em").removeClass("vopen");
     		}
     	}
     	var groupPartakeId;
     	var isCheckGroupPartake = false;
     	function orderSubmit(){
     		var goodId = $("#goodId").val();
 			var tokenId = $("#tokenId").val();
 			var groupId = $("#groupId").val();
 			var totalPrice = $("#totalPrice").val();
 			var goodPrice = $("#goodPrice").val();
 			
 			
 			var insteadOfRelief = $("#insteadOfRelief").val();
 			var insteadItem = $("#isInstead");
 			var isInstead = getItemValue(insteadItem);
 			var allowItem = $("#good_info_check_detail_item_relief");
 			var isAllow = getItemValue(allowItem);
 			var isForce = 0;
 			var params = new Object();
 			params.isInsteadOfReceiving = isAllow;
 			params.isFindOtherInsteadOfReceiving = isInstead;
 			params.insteadPartakeId = groupPartakeId;
 			params.isReceiveGoodsTogether = isForce;
 			if(isCheckGroupPartake||isInstead=="0"){
 				layer.prompt({title:"请输入团长的手机号码，并确认",formType:1},function(phonenumber,index){
 					
 					alert(phonenumber);
 					alert(index);
 				});
 				//skipToGoodPay(goodId,3,tokenId,groupId,totalPrice,params);
 			}else{
 				layer.alert("请选择代收人，如果无可选代收人则选择自己收货");
 			}
 			
     	}
     	function takepart(){
     			var goodId = $("#goodId").val();
     			var tokenId = $("#tokenId").val();
     			var groupId = $("#groupId").val();
     			var totalPrice = $("#totalPrice").val();
     			var goodPrice = $("#goodPrice").val();
     			var allowInsteadOfRelief = $("#allowInsteadOfRelief").val();
     			var isReceiveGoodsTogether = $("#isReceiveGoodsTogether").val();
     			var insteadOfRelief = $("#insteadOfRelief").val();
     			var insteadItem = $("#isInstead");
     			var isInstead = getItemValue(insteadItem);
     			var allowItem = $("#good_info_check_detail_item_relief");
     			var isAllow = getItemValue(allowItem);
     			var myGroupPartakeId = $("#myGroupPartakeId").val();
     			var params = new Object();
     			params.isInsteadOfReceiving = isAllow;
     			params.isFindOtherInsteadOfReceiving = isInstead;

     			params.isReceiveGoodsTogether = isReceiveGoodsTogether;
				if(isReceiveGoodsTogether=="1"){
					//skipToGoodPay(goodId,3,tokenId,groupId,totalPrice,params);
					layer.prompt({title:"亲，请输入团长的手机号码，并确认😊",formType:0},function(phonenumber,index){
	 					layer.close(index);
	 					var hreadGroupPhonenumber = $("#hreadGroupPhonenumber").val();
	 					if(phonenumber==hreadGroupPhonenumber){
	 						//是否指定代收人，为了避免自动跳转
		 					params.isMakeAgent = "1";
		 					params.insteadPartakeId = $("#headGroupPartakeId").val();
		 					alert(params.insteadPartakeId);
		 					skipToGoodPay(goodId,3,tokenId,groupId,totalPrice,params);
	 					}else{
	 						layer.alert("亲，验证手机号码失败哦，请联系团长😊");
	 					}
	 					
	 				});
				}else{
					$(".good_info_check_detail").animate({
						bottom:0
					},300);
				}
			}
     		$(document).ready(function(){
     			
     				$(".dropdown").click(function(){
     					var menu = $(this).children(".dropdown-menu");
     					
     					if(!menu.hasClass('show')) {
     						menu.addClass('show');
     						menu.removeClass('hide');
     						arrow.addClass('open');
     						arrow.removeClass('close');
     						event.preventDefault();
     					}
     					else {
     						menu.removeClass('show');
     						menu.addClass('hide');
     						arrow.removeClass('open');
     						arrow.addClass('close');
     						event.preventDefault();
     					}
     				});
     				scrollToItem($("#myGroupPartakeId").val());
     				var goodPrice = ${groupInfo.goodPrice};
     			
     				function countPrice(){
     					var cost;
     					var totalPrice = $("#totalPrice").val();
     					totalPrice = parseFloat(totalPrice);
     					totalPrice = totalPrice.toFixed(2);
     					var insteadOfRelief = $("#insteadOfRelief").val();
     					insteadOfRelief = parseFloat(insteadOfRelief);
     					insteadOfRelief = insteadOfRelief.toFixed(2);
     					var insteadItem = $("#isInstead");
     					var isInstead = getItemValue(insteadItem);
     					var isReceiveGoodsTogether = $("#isReceiveGoodsTogether").val();
     					if(isReceiveGoodsTogether=="1"){
     						var forceInsteadOfRelief = $("#forceInsteadOfRelief").val();
     						forceInsteadOfRelief = parseFloat(forceInsteadOfRelief);
     						forceInsteadOfRelief = forceInsteadOfRelief.toFixed(2);
     						cost = totalPrice - forceInsteadOfRelief;
     					}else if(isInstead==1){
     						cost = totalPrice - insteadOfRelief;
     					}else{
     						var allowItem = $("#good_info_check_detail_item_relief");
     						var isAllow = getItemValue(allowItem);
     						if(isAllow ==1){
     							var allowInsteadOfRelief = $("#allowInsteadOfRelief").val();
     							allowInsteadOfRelief = parseFloat(allowInsteadOfRelief);
     							allowInsteadOfRelief = allowInsteadOfRelief.toFixed(2);
     							cost = totalPrice - allowInsteadOfRelief;
     						}else{
     							cost = totalPrice;
     						}
     					}
     					
     					cost = parseFloat(cost);
     					cost = cost.toFixed(2);
     					
     					$(".good_info_check_detail_head_content_price").text("￥"+cost);
     					$("#discountPrice").text("￥"+cost);
     					
     				}
     				
     				
     				var memberItem = $("#member");
     				
					handleSpanItems(memberItem,function(spanValue){
						groupPartakeId = spanValue;
						isCheckGroupPartake = true;
	    			});
     				
     				
     				setIsInsteadOfReceiving(1);
     				//设置是否找人代收
     				function setIsInsteadOfReceiving(isInstead){
     					
     					
     					var insteadItem = $("#isInstead");
     					var insteadItemChildren = insteadItem.children(".good_info_check_detail_item_apan");
     					
     					if(isInstead==1){
     						//找人代收
     						$("#insteadOfReceivingMember").css("display","block");
     						
     						selectSpan(insteadItemChildren,$("#instead"));
     						setAllowInstead(0);
     					}else{
     						//自己收货
     						$("#insteadOfReceivingMember").css("display","none");
     						selectSpan(insteadItemChildren,$("#unInstead"));
     					}
     					countPrice();
     				}
     				
     				
     				//是否允许替他人代收
     				function setAllowInstead(isAllow){
     					var allowItem = $("#good_info_check_detail_item_relief");
     					var allowChildren = allowItem.children(".good_info_check_detail_item_apan");
     					
     					var allowInstead = $("#allowInstead");
     					var unAllowInstead = $("#unAllowInstead");
     					
     					var insteadItem = $("#isInstead");
     					var insteadItemChildren = insteadItem.children(".good_info_check_detail_item_apan");
     					
     					if(isAllow==1){
     						selectSpan(allowChildren,allowInstead);
     						if(getItemValue(insteadItem)==1){
     							layer.alert("你已经选择找人代收，自己不能再代收货");
     							selectSpan(allowChildren,unAllowInstead);
     						}
     						
     					}else{
     						selectSpan(allowChildren,unAllowInstead);
     					}
     					countPrice();
     				}
		   			
     			
		   			
     			
		   			var insteadItem = $("#isInstead");
		   			handleSpanItems(insteadItem,function(spanValue){
	    				
		   				setIsInsteadOfReceiving(spanValue);
	    			});
		   			
		   			var allowItem = $("#good_info_check_detail_item_relief");	
					handleSpanItems(allowItem,function(spanValue){
	    				
						setAllowInstead(spanValue);
	    			});
		   			
    				setUserToken("${token.id}");
    				wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    				var groupInfoImg = $("#group_info_img").val();
    				var groupInfoGoodName = $("#group_info_good_name").val();
    				var groupInfoGoodTitle = $("#group_info_good_title").val();
    				
    				var prompt = $("input[name=prompt]").val();
    				if(prompt){
	    					displayLinkGuid();
	    				}
    				wx.ready(function(){
        				wxOnMenuShareAppMessage(groupInfoGoodName,groupInfoGoodTitle,"${domainName}/info/group_info2?id=${groupInfo.id}&good_type=${goodType}",groupInfoImg,"link",null);
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
	</body>
</html>
