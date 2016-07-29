<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
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
		 <title>晨曦拼货商城</title>
	</head>
	<body>
		<div class="container">
			<div class="linkGuid" style="display:none" id="linkGuid"  onclick="javascript:hideLinkGuid();">
				<img src="/img/arrow2.jpg"></img>
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
						<div class="group_goodinfo_detail_price">${groupInfo.groupNum}人团：<span class="group_goodinfo_detail_price2">￥${groupInfo.totalPrice}</span></div>
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
			<div class="members">
				<ul>
					<c:forEach items="${groupInfo.groupPartake}" var="member">
						<li>
							<div class="member">
								<img src="${member.headImg}"/>
								<c:if test="${member.role==1}">
									<span class="member_obvious">团 </span>
									<span class="member_kind">长</span>
								</c:if>
								
								<c:if test="${member.role==2}">
									<span class="member_sofa">沙</span>
									<span class="member_kind">发</span>
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
							<div class="member_item">
								<img src="${member.headImg}"/>
								<div class="member_item_name"><b>${member.name}</b></div>
								<div class="member_item_time"><b>${member.datetime}<c:if test="${member.role!=1}">参团</c:if><c:if test="${member.role==1}">开团</c:if></b></div>
							</div>
						</li>
							
						
					</c:forEach>
				</ul>
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
		                <a class="goto_gootlist" href="javascript:skipToGoodPay('${groupInfo.goodId}','3','${token.id}','${groupInfo.id}','${groupInfo.totalPrice}')">点击参团</a>
		     	</div>
	     	</c:if>
     	  </c:if>
     	</div>
     	<script type="text/javascript">
     		$(document).ready(function(){
     			<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
     			<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     			<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
     			<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
     			<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
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
     					 <title>晨曦拼货商城</title>
     				</head>
     				<body>
     					<div class="container">
     						<div class="linkGuid" style="display:none" id="linkGuid"  onclick="javascript:hideLinkGuid();">
     							<img src="/img/arrow2.jpg"></img>
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
     									<div class="group_goodinfo_detail_price">${groupInfo.groupNum}人团：<span class="group_goodinfo_detail_price2">￥${groupInfo.totalPrice}</span></div>
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
     						<div class="members">
     							<ul>
     								<c:forEach items="${groupInfo.groupPartake}" var="member">
     									<li>
     										<div class="member">
     											<img src="${member.headImg}"/>
     											<c:if test="${member.role==1}">
     												<span class="member_obvious">团 </span>
     												<span class="member_kind">长</span>
     											</c:if>
     											
     											<c:if test="${member.role==2}">
     												<span class="member_sofa">沙</span>
     												<span class="member_kind">发</span>
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
     										<div class="member_item">
     											<img src="${member.headImg}"/>
     											<div class="member_item_name"><b>${member.name}</b></div>
     											<div class="member_item_time"><b>${member.datetime}<c:if test="${member.role!=1}">参团</c:if><c:if test="${member.role==1}">开团</c:if></b></div>
     										</div>
     									</li>
     										
     									
     								</c:forEach>
     							</ul>
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
     					                <a class="goto_gootlist" href="javascript:skipToGoodPay('${groupInfo.goodId}','3','${token.id}','${groupInfo.id}','${groupInfo.totalPrice}')">点击参团</a>
     					     	</div>
     				     	</c:if>
     			     	  </c:if>
     			     	</div>
     			     	<script type="text/javascript">
     			     		$(document).ready(function(){
     			    				setUserToken("${token.id}");
     			    				wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
     			    				var groupInfoImg = $("#group_info_img").val();
     			    				var groupInfoGoodName = $("#group_info_good_name").val();
     			    				var groupInfoGoodTitle = $("#group_info_good_title").val();
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

     		});
    			
   		 </script>
	</body>
</html>
