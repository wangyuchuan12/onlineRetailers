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
					<li>
						<div class="coupon_list_goods">哈密瓜</div>
						<div class="coupon_list_limit">
							<ul>
								<li>免单团券
								<li>有效期:2015-8-10至2015-8-15
								<li>团长免费开团
								<li><b style="color:red">已使用</b>
						    </ul>
						</div>
					</li>
					<li>
						<div class="coupon_list_goods">￥25</div>
						<div class="coupon_list_limit">
							<ul>
								<li>满减券
								<li>有效期:2015-8-10至2015-8-15
								<li>单独购买满66元使用
								<li><b style="color:red">已到期</b></li>
							</ul>
						</div>
					</li>
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