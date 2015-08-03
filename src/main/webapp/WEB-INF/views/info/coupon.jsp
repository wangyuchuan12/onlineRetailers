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
    </head>
<body>
	<div class="container">
		<div class="coupon">
			<div class="coupon_title">
				我的优惠券
			</div>
			
			<div class="coupon_rule"><a href="#">使用规则</a></div>
			
			<div class="coupon_list">
				<div class="coupon_list_goods">哈密瓜</div>
				<div class="coupon_list_limit">
					<ul>
						<li>免单团券
						<li>有效期:2015-8-10至2015-8-15
						<li>团长免费开团
				    </ul>
				</div>
			</div>
			
			<div class="coupon_list">
				<div class="coupon_list_goods">￥25</div>
				<div class="coupon_list_limit">
					<ul>
						<li>满减券
						<li>有效期:2015-8-10至2015-8-15
						<li>单独购买满66元使用
				    </ul>
				</div>
			</div>
			<div class="coupon_footer">
			<ul>
						<li>没有更多优惠券了,
						<li class="coupon_footer_check"><a href="#">查看过期券</a>
				    </ul>
			</div>
		</div>
	</div>



</body>
</html>