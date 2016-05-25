<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!doctype html>
<html>
	<head>
		<link rel="stylesheet" href="/css/group_info.css" />
		<link rel="stylesheet" href="/css/game.css" />
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
			<div class="game_receive_good">
				<img src="${imgUrl}">
				<div class="game_info">
					<div class="game_info_type">开团劵</div>
					<div class="game_info_name">名称：${name}</div>
					<div class="game_info_validity">有效期：<joda:format value="${endDate}" pattern="yyyy-MM-dd HH:mm"/></div>
				</div>
				<div class="game_receive_btn" onclick="skipToGoodInfo('${goodId}')">立即使用</div>
			</div>
     	</div>

     	<script type="text/javascript">
     		$(document).ready(function(){
    				
     		});
    			
   		 </script>
	</body>
</html>
