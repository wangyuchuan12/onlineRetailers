<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport"
		content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	 <meta name="format-detection" content="telephone=no">
	 <meta http-equiv="Pragma" content="no-cache">   
	 <meta http-equiv="Cache-Control" content="no-store">
	 <meta http-equiv="Expires" content="0">
	 <title>晨曦拼货商城</title>
<tiles:insertAttribute name="resource"/>
</head>
<body>
<div id="wrapper">
	<c:if test="${chat!=null && chat.notReadCount!=0}">	
		<div class="common_chat" onclick="skipToChatList();">
	   		<i class="fa fa-comments-o" aria-hidden="true"></i>
	   		<font>${chat.notReadCount}</font>
   		</div>
	</c:if>
   
   <tiles:insertAttribute name="header" />
   <tiles:insertAttribute name="body" />
   <div style="height:20px;"></div>
   <tiles:insertAttribute name="footer" />
</div>
</body>
</html>
