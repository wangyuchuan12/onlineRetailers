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
		<link rel="stylesheet" href="/css/chat.css">
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
	    <div class="container" >
	    		<div class="chat_businessHead">商家名称</div>
	    		<div class="chat_content">
	    			
	    			<c:forEach items="${dialogSessionItems}" var="dialogSessionItem">
	    				<c:if test="${dialogSessionItem.role==0}">
	    					<div class="chat_content_request_dialog">
			    				<div class="chat_content_time"><joda:format value="${dialogSessionItem.dateTime}" pattern="yyyy-MM-dd HH:mm"/></div>
			    				<div class="chat_content_session">
			    					<div class="chat_content_text">${dialogSessionItem.content}</div>
			    					<img src="${dialogSessionItem.headImg}">
			    				</div>

	    					</div>
	    				
	    				</c:if>
	    				
	    				
	    				<c:if test="${dialogSessionItem.role==1}">
	    					<div class="chat_content_response_dialog">
	    						<div class="chat_content_time"><joda:format value="${dialogSessionItem.dateTime}" pattern="yyyy-MM-dd HH:mm"/></div>
			    				<div class="chat_content_session">
			    					<div class="chat_content_text">${dialogSessionItem.content}</div>
			    					<img src="${dialogSessionItem.headImg}">
			    				</div>
	    					</div>
	    				</c:if>
	    			</c:forEach>
	    			
	    			
	    			
	    			
	    		</div>
	    		<div class="chat_input chat_footer">
	    			<input type="text" class="chat_input_text"></input>
	    			<div class="chat_input_btn">发送 </div>
	    		</div>
	    		<input id="type"  type = "hidden" name="type" value="${type}"/>
	    		<input id="goodId" type = "hidden" name="goodId" value="${goodId}"/>
	    		<input id="orderId" type = "hidden" name="orderId" value="${orderId}"/>
	    		<input id="adminId" type = "hidden" name="adminId" value="${adminId}"/>
	    </div>
	</body>
	<script type="text/javascript">
	 $(document).ready(function(){
		$(".chat_content").animate({scrollTop: 100000}, 300);
		$(".chat_input_btn").on("click",function(){
			var content = $(".chat_input_text").val();
			
			var goodId = $("#goodId").val();
			var type = $("#type").val();
			var adminId = $("#adminId").val();
			var orderId = $("#orderId").val();
			var obj = new Object();
			obj.goodId = goodId;
			obj.type = type;
			obj.orderId = orderId;
			htmlobj=$.ajax({url:"/api/chat/send_message?token=${token.id}&good_id="+goodId+"&type="+type+"&order_id="+orderId+"&content="+content+"&admin_id="+adminId+"&role=0",async:false});
			htmlobj = eval("("+htmlobj.responseText+")");
			var dialogDiv = $("<div></div>");
			if(htmlobj.role==0){
				dialogDiv.addClass("chat_content_request_dialog");
			}else{
				dialogDiv.addClass("chat_content_response_dialog");
			}
			
			var timeDiv = $("<div class='chat_content_time'>"+htmlobj.dateTime+"</div>");
			
			var chatContentSessionDiv = $("<div class='chat_content_session'><div class='chat_content_text'>"+htmlobj.content+"</div><img src='"+htmlobj.headImg+"'></div>");
			dialogDiv.append(timeDiv);
			dialogDiv.append(chatContentSessionDiv);
			$(".chat_content").append(dialogDiv);
			$(".chat_content").animate({scrollTop: 100000}, 300);
			$(".chat_input_text").val("");
			
		});
	 });
	</script>
</html>