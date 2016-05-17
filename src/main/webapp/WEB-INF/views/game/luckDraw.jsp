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
			<div id="lottery"> 
	<table border="0" cellpadding="0" cellspacing="0" style=""> 
		
		<tr>
			<td class="lottery-unit lottery-unit-0"><img src="/images/1.png"  style="background-color: RGBA(134,112,255 ,.1)"></td>
			
			<td class="lottery-unit lottery-unit-1" ><img src="/images/2.png" style="background-color: RGBA(223,136,207 ,.1)"></td>
			<td class="lottery-unit lottery-unit-2"><img src="/images/4.png"  style="background-color: RGBA(142,217,249 ,.1)"></td>
            <td class="lottery-unit lottery-unit-3" ><img src="/images/3.png" style="background-color: RGBA(254,249,185,.1)"></td>
		</tr>
		<tr>
			<td class="lottery-unit lottery-unit-11"><img src="/images/7.png" style="background-color: RGBA(253,222,46 , .1)"></td>
			<td colspan="2" rowspan="2"><div class="start"><div>开始<br/>抽奖</div></div></td>
			<td class="lottery-unit lottery-unit-4"><img src="/images/5.png" style="background-color: RGBA(33,217,207 , .1)"></td>
		</tr>
		<tr>
			<td class="lottery-unit lottery-unit-10"><img src="/images/1.png" style="background-color: RGBA(253,250,207 , .1)"></td>
			<td class="lottery-unit lottery-unit-5"><img src="/images/6.png" style="background-color: RGBA(255,247,154 , .1)"></td>
		</tr>
        <tr>
			<td class="lottery-unit lottery-unit-9"><img src="/images/3.png"  style="background-color: RGBA(255,177,25,.1)"></td>
			<td class="lottery-unit lottery-unit-8"><img src="/images/6.png" style="background-color: RGBA(255,214,232,.2)"></td>
			<td class="lottery-unit lottery-unit-7"><img src="/images/8.png" style="background-color: RGBA(159,249,247 ,.2)"></td>
            	<td class="lottery-unit lottery-unit-6" ><img src="/images/7.png" style="background-color: RGBA(159,249,247,.1)"></td>
		</tr>
	</table>
</div>

<script type="text/javascript" src="jquery-1.8.3.min.js"></script>
<script type="text/javascript">
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
		return false;
	},
	stop:function(index){
		this.prize=index;
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
	}else{
		if (lottery.times<lottery.cycle) {
			lottery.speed -= 10;
		}else if(lottery.times==lottery.cycle) {
			var index = Math.random()*(lottery.count)|0;
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
	lottery.init('lottery');
	$("#lottery").click(function(){
		if (click) {
			return false;
		}else{
			lottery.speed=100;
			roll();
			click=true;
			return false;
		}
	});
};
</script>
			
			
			
			
     	</div>

     	<script type="text/javascript">
     		$(document).ready(function(){
    				
     		});
    			
   		 </script>
	</body>
</html>
