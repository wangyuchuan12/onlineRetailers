<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<html>
<head>
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <meta name="format-detection" content="telephone=no">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Expires" content="0">
        <meta name="description" content="" />
        <link rel="stylesheet" href="/css/coupon.css" />
        <link rel="stylesheet" href="/css/mystyle.css" />
        <link rel="stylesheet" href="/css/core.css" />
        <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js">
        </script>
        <link rel="stylesheet"
	href="/css/font-awesome.min.css">
    </head>
<body>
	<div class="container">
		<div class="coupon">
			<div class="coupon_title">
				我的优惠券
			</div>
			
			<div class="coupon_list">
				<ul>
				<c:forEach items="${coupons}" var="coupon">
					<li>
						<div class="coupon_list_goods">${coupon.goodName}</div>
						<div class="coupon_list_limit">
							<ul>
								<li>免单团券
								<li>有效期:<br/>${coupon.beginTime}至${coupon.endTime}
								<li>团长免费开团
								<li>
								<b style="color:red">
									<c:if test="${coupon.status==0}">
										已使用
									</c:if>
									<c:if test="${coupon.status==1}">
										未使用
									</c:if>
									
									<c:if test="${coupon.status==2}">
										已过期
									</c:if>
								</b>
						    </ul>
						</div>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</div>
<footer class="footer">
		<nav>
			<ul>
				<li><a href="#" class="nav-controller active"><i
						class="fa fa-home"></i>首页</a></li>
				<li><a href="groups.html" class="nav-controller"><i
						class="fa fa-group"></i>我的团</a></li>
				<li><a href="#" class="nav-controller"><i
						class="fa fa-list"></i>我的订单</a></li>
				<li><a href="personal.html" class="nav-controller"><i
						class="fa fa-user"></i>个人中心</a></li>
			</ul>
		</nav>
</footer>


</body>
</html>