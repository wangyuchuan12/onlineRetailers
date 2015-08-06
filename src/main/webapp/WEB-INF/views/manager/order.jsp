<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!doctype html>
<html lang="en">
	<head>
		<link rel="stylesheet" href="/css/order.css" />
        <link rel="stylesheet" href="/css/mystyle.css" />
        <link rel="stylesheet" href="/css/core.css" />
    </head>
	<body>
		<div class="container">
			<div class="order">
			<div class="order_head">
				<ul>
					<li>全部订单</li>
					<li>等待买家付款</li>
					<li>待发货	</li>
					<li>已发货	</li>
					<li>已退款	</li>
					<li>交易成功</li>
					<li>交易关闭</li>
				</ul>
			</div>
			<div class="order_title">
				<ul>
					<li><div class="order_title_goodsname">宝贝</div></li>
					<li><div class="order_title_price">单价（元）</div> </li>
					<li><div class="order_title_amount">数量</div></li>
					<li><div class="order_title_id">买家</div></li>
					<li><div class="order_title_state">交易状态</div></li>
					<li><div class="order_title_income">实收款(元)</div></li>
				</ul>
			</div>
			<div class="order_list">
				<div class="order_list_top">
					<ul>
						<li>订单编号：30000</li>
						<li>付款时间：2015-3-29 15:00</li>
						<li></li>
					</ul>
				</div>
				<div class="order_list_information">
					<ul>
						<li><div class="order_list_img"><img src="/img/apple.jpg"></div></li>
						<li><div class="order_list_goods">花果山水蜜桃20元1斤</div></li>
						<li><div class="order_list_price">3.59</div></li>
						<li><div class="order_list_howmany">8</div></li>
						<li><div class="order_list_id">我买单你付款</div></li>			
						<li><div class="order_list_state"></div>
							<ul>
								<li>组团成功</li>
								<li>详情</li>
								<li>发货</li>
							</ul>
						</li>
						<li><div class="order_list_income"></div>
							<ul>
								<li>39.77</li>
								<li></li>
							</ul>
						</li>	
					</ul>
				</div>	
			</div>
			<div class="order_list">
				<div class="order_list_top">
					<ul>
						<li>订单编号：30001</li>
						<li>付款时间：2015-3-29 15:00</li>
						<li></li>
					</ul>
				</div>
				<div class="order_list_information">
					<ul>
						<li><div class="order_list_img"><img src="/img/apple.jpg"></div></li>
						<li><div class="order_list_goods">花果山水蜜桃20元1斤</div></li>
						<li><div class="order_list_price">3.59</div></li>
						<li><div class="order_list_howmany">8</div></li>
						<li><div class="order_list_id">神经质234</div></li>			
						<li><div class="order_list_state"></div>
							<ul>
								<li>组团成功</li>
								<li>详情</li>
								<li>买家已发货</li>
							</ul>
						</li>
						<li><div class="order_list_income"></div>
							<ul>
								<li>39.77</li>
								<li>查看物流</li>
							</ul>
						</li>	
					</ul>
				</div>	
			</div>
			<div class="order_list">
				<div class="order_list_top">
					<ul>
						<li>订单编号：30002</li>
						<li>付款时间：2015-3-29 15:00</li>
						<li>退款时间：2015-3-30 15:00</li>
					</ul>
				</div>
				<div class="order_list_information">
					<ul>
						<li><div class="order_list_img"><img src="/img/apple.jpg"></div></li>
						<li><div class="order_list_goods">花果山水蜜桃20元1斤</div></li>
						<li><div class="order_list_price">3.59</div></li>
						<li><div class="order_list_howmany">8</div></li>
						<li><div class="order_list_id">不喜欢吃的吃货</div></li>			
						<li><div class="order_list_state"></div>
							<ul>
								<li>组团失败</li>
								<li>详情</li>
								<li></li>
							</ul>
						</li>
						<li><div class="order_list_income"></div>
							<ul>
								<li>39.77</li>
								<li></li>
							</ul>
						</li>	
					</ul>
				</div>	
			</div>
			<div class="order_list">
				<div class="order_list_top">
					<ul>
						<li>订单编号：30003</li>
						<li>下单时间：2015-3-29 15:00</li>
						<li></li>
					</ul>
				</div>
				<div class="order_list_information">
					<ul>
						<li><div class="order_list_img"><img src="/img/apple.jpg"></div></li>
						<li><div class="order_list_goods">花果山水蜜桃20元1斤</div></li>
						<li><div class="order_list_price">3.59</div></li>
						<li><div class="order_list_howmany">8</div></li>
						<li><div class="order_list_id">天气热了买点水果</div></li>			
						<li><div class="order_list_state"></div>
							<ul>
								<li>交易关闭</li>
								<li>详情</li>
								<li></li>
							</ul>
						</li>
						<li><div class="order_list_income"></div>
							<ul>
								<li>39.77</li>
								<li></li>
							</ul>
						</li>	
					</ul>
				</div>	
			</div>
			<div class="order_list">
				<div class="order_list_top">
					<ul>
						<li>订单编号：30004</li>
						<li>付款时间：2015-3-29 15:00</li>
						<li>收货时间：2015-4-03 15:00</li>
					</ul>
				</div>
				<div class="order_list_information">
					<ul>
						<li><div class="order_list_img"><img src="/img/apple.jpg"></div></li>
						<li><div class="order_list_goods">花果山水蜜桃20元1斤</div></li>
						<li><div class="order_list_price">3.59</div></li>
						<li><div class="order_list_howmany">8</div></li>
						<li><div class="order_list_id">我是shary</div></li>			
						<li><div class="order_list_state"></div>
							<ul>
								<li>交易成功</li>
								<li>详情</li>
								<li>买家已签收</li>
							</ul>
						</li>
						<li><div class="order_list_income"></div>
							<ul>
								<li>39.77</li>
								<li>查看物流</li>
							</ul>
						</li>	
					</ul>
				</div>	
			</div>
			
			</div>
		</div>
	</body>
	</body>
	</body>
</html>
	