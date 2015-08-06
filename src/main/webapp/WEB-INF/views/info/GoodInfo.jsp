<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!doctype html>
<html>

<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Pragma" content="no-cache">   
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="Expires" content="0">
    <link rel="stylesheet"
	href="/css/font-awesome.min.css">
	<link rel="stylesheet" href="/css/mystyle.css">
	<link rel="stylesheet" href="/css/core.css">
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="/js/myscript.js"></script>
</head>

<body >
    <div class="container" >
        <section class="main-view">
            <div id="focus" class="focus goods-focus ect-padding-lr ect-margin-tb">
                <div class="hd">
                    
                </div>
                <div class="bd">
                    <ul>
						
                    </ul>
                </div>
            </div>
            <div class="good_info">
                <div class="good_info_introduce">
                    <div class="good_info_introduce_name"><b>${good.name}</b></div>
                    <div class="good_info_introduce_cx"></div>
                    <div class="good_info_introduce_info">
                        <div class="good_info_introduce_price">市场价：<b id="market_price">${good.market_price}</b> <span>已售：<i id="sold_quantity">10</i>件</span></div>
                        <div class="good_info_introduce_num">支付开团并邀请<span id="tuan_more_need_number"></span>人参团，人数不足自动退款，详见下方拼团玩法</div>
                    </div>
                </div>
            </div>
            <div class="trade_flow">
            	<div class="trade_flow_details" onclick="window.location.href='/info/trade_flow_info'">查看详情&gt;</div>
            	<ul>
            		<li>
            			<div class="trade_flow_block trade_flow_block_activity">
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
            			<div class="trade_flow_block">
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
			
		<div class="good_info_details">
			<h1>商品简介</h1>
			<p>${good.instruction}
			<div class="images">
			<img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-477820d09689d655.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-2e3b34201d00768f.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-08eea6fa41112aa8.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-e83e8cbe95160d57.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-174187a08958982b.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-c6a36b3dd5a01755.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-ec5f73c6c50aa65b.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-0043c1d91d242ef1.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-931b701e04b75674.jpg" alt=""><img id="__mcenew" class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-09efa87ff3533058.jpg" alt=""></p></div>
		</div>
        </section>
    </div>
    <div class="good_info_btns footer2">
    		<ul>
    			<li>
    				<div class="good_info_btn activityStyle" onclick="toPayOnClick('${good.id}',0)">
    					<div class="good_info_btn_price_group">￥${good.group_cost}/件</div>
    					<div class="good_info_btn_type">${good.group_num}人团</div>
    				</div>
    			</li>
    			
    			<li>
    				<div class="good_info_btn activityStyle" onclick="toPayOnClick('${good.id}',1)">
    					<div class="good_info_btn_price_alone">￥${good.alone_cost}/件</div>
    					<div class="good_info_btn_type">单独买</div>
    				</div>
    			</li>
    			
    			<li>
    				<div class="good_info_btn activityStyle" onclick="toPayOnClick('${good.id}',2)">
    					<div class="good_info_btn_price_integral">${good.coupon_cost}张/件</div>
    					<div class="good_info_btn_type">开团劵开团</div>
    				</div>
    			</li>
    		</ul>
    </div>
    <script type="text/javascript">
    		setUserToken("${token}");
    </script>
</body>
</html>
