<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <title></title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <link href="http://static.paipaiimg.com/c/=/fd/qqpai/base/css/gb.min.css,/fd/qqpai/tuan/css/index_v3.min.css?t=1415376382" rel="stylesheet">
</head>
<body>
    <div class="help">
        <div id="common" class="help_tit" style="display: block;">拼团流程：</div>
        <div id="common_step" style="display: block;" class="help_step">
            <span class="help_i">1</span>
            <span class="help_t">选择心仪商品</span>
            <i></i>
            <span class="help_i">2</span>
            <span class="help_t">支付开团或参团</span>
            <i></i>
            <span class="help_i">3</span>
            <span class="help_t">等待好友参团</span>
            <i></i>
            <span class="help_i">4</span>
            <span class="help_t">达到人数团购成功</span>
        </div>
    </div>
    <div style="display: block;" id="common_rule" class="rule">
        <p class="rule_row"><span class="rule_num">(1)</span>选择商品开团：选择拼团商品下单，团长完成支付后，团即刻开启。在24小时内参团人数达到规定人数，此团方能成功。若24小时内参团人数不到规定人数，则该团失败，系统自动为您退款。</p>
        <p class="rule_row"><span class="rule_num">(2)</span>团长：开团且该团第一位支付成功的人；</p>
        <p class="rule_row"><span class="rule_num">(3)</span>参团成员：通过团长邀请购买该商品的成员即为参团成员，参团成员也可邀请更多的成员参团。</p>
        <p class="rule_row"><span class="rule_num">(4)</span>团购成功：从团长开团24小时内找到相应开团人数的好友参团，则该团成功，卖家发货；</p>
        <p class="rule_row"><span class="rule_num">(5)</span>团购失败：从团长开团24小时内未能找到相应开团人数的好友参团，则该团失败。系统会在1-2个工作日内提交微信处理，微信审核后自动原路退款到您的支付账户。</p>
        <p class="rule_row"><span class="rule_num">(6)</span>拼团，是基于好友的组团购买，获取团购优惠，为了保证广大消费者的权益，商城有权将判定为黄牛倒货的团解散并取消订单。</p>
    </div>
</body>
</html>
