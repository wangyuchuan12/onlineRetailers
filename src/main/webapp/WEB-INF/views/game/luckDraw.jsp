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
			<div class="games_head">
				<img  src="${good0.imgUrl}"> 
			</div>
			<div id="lottery"> 
	<table border="0" cellpadding="0" cellspacing="0" style=""> 
		
		<tr>
			<td class="lottery-unit lottery-unit-0"><img src="${good0.imgUrl}"  style="background-color: RGBA(134,112,255 ,.1)"></td>
			
			<td class="lottery-unit lottery-unit-1" ><img src="${good1.imgUrl}" style="background-color: RGBA(223,136,207 ,.1)"></td>
			<td class="lottery-unit lottery-unit-2"><img src="${good2.imgUrl}"  style="background-color: RGBA(142,217,249 ,.1)"></td>
            <td class="lottery-unit lottery-unit-3" ><img src="${good3.imgUrl}" style="background-color: RGBA(254,249,185,.1)"></td>
		</tr>
		<tr>
			<td class="lottery-unit lottery-unit-11"><img src="${good11.imgUrl}" style="background-color: RGBA(253,222,46 , .1)"></td>
			<td colspan="2" rowspan="2"><div class="start activityStyle"><div id = "start">开始<br/>抽奖</div></div></td>
			<td class="lottery-unit lottery-unit-4"><img src="${good4.imgUrl}" style="background-color: RGBA(33,217,207 , .1)"></td>
		</tr>
		<tr>
			<td class="lottery-unit lottery-unit-10"><img src="${good10.imgUrl}" style="background-color: RGBA(253,250,207 , .1)"></td>
			<td class="lottery-unit lottery-unit-5"><img src="${good5.imgUrl}" style="background-color: RGBA(255,247,154 , .1)"></td>
		</tr>
        <tr>
			<td class="lottery-unit lottery-unit-9"><img src="${good9.imgUrl}"  style="background-color: RGBA(255,177,25,.1)"></td>
			<td class="lottery-unit lottery-unit-8"><img src="${good8.imgUrl}" style="background-color: RGBA(255,214,232,.2)"></td>
			<td class="lottery-unit lottery-unit-7"><img src="${good7.imgUrl}" style="background-color: RGBA(159,249,247 ,.2)"></td>
            	<td class="lottery-unit lottery-unit-6" ><img src="${good6.imgUrl}" style="background-color: RGBA(159,249,247,.1)"></td>
		</tr>
	</table>
</div>

<script type="text/javascript" src="jquery-1.8.3.min.js"></script>
<script type="text/javascript">
var goods = eval('(${goods})');
var selectGood;
var htmlobj;
var lottery={
	index:-1,	//当前转动到哪个位置，起点位置
	count:0,	//总共有多少个位置
	timer:0,	//setTimeout的ID，用clearTimeout清除
	speed:20,	//初始转动速度
	times:0,	//转动次数
	cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize:-1,	//中奖位置
	init:function(id){
		if ($("#"+id).find(".lottery-unit").length>0) {
			$lottery = $("#"+id);
			$units = $lottery.find(".lottery-unit");
			this.obj = $lottery;
			this.count = $units.length;
			$lottery.find(".lottery-unit-"+this.index).addClass("active");
		};
	},
	roll:function(){
		var index = this.index;
		var count = this.count;
		var lottery = this.obj;
		$(lottery).find(".lottery-unit-"+index).removeClass("active");
		index += 1;
		if (index>count-1) {
			index = 0;
		};
		$(lottery).find(".lottery-unit-"+index).addClass("active");
		this.index=index;
		
		for(var i = 0;i<goods.length;i++){
			if(goods[i].recordIndex==index){
				$(".games_head img").attr("src",goods[i].imgUrl);
			}
		}
		
		
		return false;
	},
	stop:function(index){
		this.prize=index;
		if(htmlobj){
			if(htmlobj.type!=0){
				$("#start").html("立即<br/>领取");
				$(".start").css("background-color","RGBA(255,0,0,1)"); 
				$("#start").click(function(){
					skipToLuckDraw(htmlobj.luckDrawRecordId,"${token.id}");
				});
			}else{
				$(".start").css("background-color","RGBA(222,18,122,.1)");
			}
			$("#draw_footer_content").html(htmlobj.prompt);
		}else{
			$("#draw_footer_content").html("对不起，网络连接失败");
			$(".start").css("background-color","RGBA(222,18,122,.1)");
		}
		 
		 
		
		 
		return false;
	}
};

function roll(){
	lottery.times += 1;
	lottery.roll();
	if (lottery.times > lottery.cycle+10 && lottery.prize==lottery.index) {
		clearTimeout(lottery.timer);
		lottery.prize=-1;
		lottery.times=0;
		click=false;
		lottery.stop(lottery.index);
	}else{
		if (lottery.times<lottery.cycle) {
			lottery.speed -= 10;
			
		}else if(lottery.times==lottery.cycle) {
			var index = Math.random()*(lottery.count)|0;
			var flag = true;
			while(flag){
				for(var i = 0;i<goods.length;i++){
					if(goods[i].recordIndex==index){
						if(goods[i].allow){
							flag = false;
							selectGood = goods[i];
							break;
						}
					}
				}
				if(flag){
					index = Math.random()*(lottery.count)|0;
				}
			}
			
			$('#start').unbind();
			var luckNo = "${luckNo}";
			$.ajax({
				url:"/game/draw_handler?luckNo="+luckNo+"&index="+index+"&token=${token.id}",
				success:function(resp){
					htmlobj = resp;
					 
					
				}
			});
			
			lottery.prize = index;		
		}else{
			if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {
				lottery.speed += 110;
			}else{
				lottery.speed += 20;
			}
		}
		if (lottery.speed<40) {
			lottery.speed=40;
		};
		//console.log(lottery.times+'^^^^^^'+lottery.speed+'^^^^^^^'+lottery.prize);
		lottery.timer = setTimeout(roll,lottery.speed);
	}
	return false;
}

var click=false;

window.onload=function(){
	var isAllow = "${isAllow}";
	lottery.init('lottery');
	if(isAllow=="true"){
		$("#start").click(function(){
			if (click) {
				return false;
			}else{
				lottery.speed=100;
				roll();
				click=true;
				return false;
			}
		});
	}else{
		$(".start").css("background-color","RGBA(222,18,122,.1)");
	}
	
	
};
</script>
     	</div>
		<div class="draw_footer">
			<div id="draw_footer_content">
				<c:if test="${isAllow==true}">
					点击开始抽奖吧
				</c:if>
				
				<c:if test="${!isAllow}">
					对不起，你今天的机会已经用完
				</c:if>
			</div>
			<div style="color:black">规则：<br/>1.每天允许抽奖一次<br/>2.抽中开团劵，可以用此开团劵免费开团<br/>3.抽中红包，系统自动将红包发到你的微信零钱中<br/>4.抽到感谢参与，那么请明天继续吧</div>
		</div>
		<a href="http://${domainName}/main/good_list?token=${token.id}" style="color:red;padding-left:10px;font-size:20px;">返回商城</a>
     	<script type="text/javascript">
     		$(document).ready(function(){
     			setUserToken("${token.id}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("每日抽奖","抽中领大奖！现金红包，免单团劵，好礼分享给大家","${domainName}/game/luck_draw","http://7xugu1.com1.z0.glb.clouddn.com/%E6%8A%BD%E5%A5%96.jpg","link");
    				wx.hideMenuItems({
    				    menuList: ["menuItem:copyUrl","menuItem:exposeArticle","menuItem:setFont","menuItem:readMode","menuItem:originPage","menuItem:share:email","menuItem:openWithQQBrowser","menuItem:openWithSafari"] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
    				});
    				wx.showMenuItems({
    		            menuList: [
    		                "menuItem:profile",// 添加查看公众号
    		                "menuItem:addContact"
    		            ]
    		        	});
    			});
     		});
    			
   		 </script>
	</body>
</html>
