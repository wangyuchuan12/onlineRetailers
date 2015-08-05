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
 <meta name="format-detection" content="telephone=no">
 <meta http-equiv="Pragma" content="no-cache">   
 <meta http-equiv="Cache-Control" content="no-store">
 <meta http-equiv="Expires" content="0">
	</head>
	<body>
		<div class="container">
			<div class="group_goodinfo">
			      
					<div class="group_goodinfo_img">
						<img src="${groupInfo.headImg}"></img>
					</div>
					<div class="group_goodinfo_detail">
						<div class="group_goodinfo_detail_title">${groupInfo.name}【产地定制】新疆哈密瓜1个14.9元</div>
						<div class="group_goodinfo_detail_price">${groupInfo.groupNum}人团：<span class="group_goodinfo_detail_price2">￥${groupInfo.totalPrice}/件</span></div>
					
					</div>
			
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
					<c:forEach begin="1" end="${groupInfo.groupNum-fn:length(groupInfo.groupPartake)}">
						<li>
							<div class="member">
								<img src="/img/question_mark.jpg"/>
							</div>
						</li>
					</c:forEach>
	
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
			<div class="groupinfo_situation">
				<div class="groupinfo_situation_title">还差<b>4</b>人，盼你如南方人盼暖气</div>
				<div class="groupinfo_situation_time">剩余<b>05</b><b>19</b><b>20</b>结束</div>
			</div>
			<div class="trade_flow">
            	<div class="trade_flow_details">查看详情&gt;</div>
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
            			<div class="trade_flow_block trade_flow_block_activity"> 
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
		<div class="footer3">
                <a class="goto_gootlist">我也开个团，点此回商品列表</a>
     	</div>
     	</div>
	</body>
</html>
