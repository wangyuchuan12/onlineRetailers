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
                    <div class="good_info_introduce_name"><b>浦江葡萄</b></div>
                    <div class="good_info_introduce_cx">这种葡萄非常甜，非常好吃</div>
                    <div class="good_info_introduce_info">
                        <div class="good_info_introduce_price">市场价：<b id="market_price">100</b> <span>已售：<i id="sold_quantity">10</i>件</span></div>
                        <div class="good_info_introduce_num">支付开团并邀请<span id="tuan_more_need_number"></span>人参团，人数不足自动退款，详见下方拼团玩法</div>
                    </div>
                </div>
            </div>
            <div class="step">
                <div class="trade_flow">
			<ul>
				<li>
					<span class="step_panel">
						<i class="trade_flow_choose_colorful"></i><b class="trade_flow_choose_font_colorful">选择商品></b>
					</span>
				</li>

				<li>
					<span class="step_panel">
						<i class="trade_flow_pay_gray"></i><b class="trade_flow_font_gray">开团支付></b>
					</span>
				</li>

				<li>
					<span class="step_panel">
						<i class="trade_flow_group_gray"></i><b class="trade_flow_font_gray">正在组团></b>
					</span>
				</li>

				<li>
					<span class="step_panel">
						<i class="trade_flow_success_gray"></i><b class="trade_flow_font_gray">团购成功></b>
					</span>
				</li>
			</ul>
		</div>
            </div>
			
	<div  class="details">
	<h1>商品简介</h1>
	<p>就是这么任性，就是这么甜。不用泡盐水，直接削着吃。更没有所谓的涩，自然成熟，8，9成熟采摘，然后直接空运到京城。预计下周到货。大家可以预定了，数量有限，预购从速。
	<div class="images">
	<img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-477820d09689d655.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-2e3b34201d00768f.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-08eea6fa41112aa8.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-e83e8cbe95160d57.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-174187a08958982b.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-c6a36b3dd5a01755.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-ec5f73c6c50aa65b.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-0043c1d91d242ef1.jpg" alt=""><img class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-931b701e04b75674.jpg" alt=""><img id="__mcenew" class="img-responsive" src="http://img1.ifindu.cn/photo/2015-5/group-commodity-09efa87ff3533058.jpg" alt=""></p></div>
	</div>
        </section>
    </div>
    <div class="btn-group-justified footer2">
                    <a class="btn btn-success">立即购买</a>
                    <a class="btn btn-success">加入购物车</a>
     </div>
</body>
</html>
