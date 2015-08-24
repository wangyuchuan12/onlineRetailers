var webPath = "";
var cityObject = new Object();
function addressAddSbumit(){
	var addressName = $("#address_name").val();
	var addressPhonenumber = $("#address_phonenumber").val();
	var addressProvince = $("#address_province").val();
	var addressCity = $("#address_city").val();
	var addressAddress = $("#address_address").val();
	var addressType = $("#address_type").val();
	var addressContent = $("#address_content").val();
	skipToUrl("/action/do_address_save?city_id="+addressAddress+"&type="+addressType+"&content="+addressContent+"&phonenumber="+addressPhonenumber+"&name="+addressName);
}

function initGroupInvalidDate(startTime2,timeLong){
	window.setInterval(function(){
		var startTime = startTime2.replace(/-/g,"/");
		startTime = new Date(startTime);
		var timestamp = startTime.valueOf()+parseFloat(timeLong)*3600000;
		var now = new Date();
		var nowTimestamp = now.valueOf();
		if(timestamp>nowTimestamp){
			var second = new Date(timestamp-nowTimestamp).getSeconds();
			var min = new Date(timestamp-nowTimestamp).getMinutes();
			
			var date = new Date(timestamp-nowTimestamp).getDate()-1;
			var hour = new Date(timestamp-nowTimestamp).getHours()-8+date*24;
			if(hour>=10){
				$("#group_info_hour").html(hour+"");
			}else{
				$("#group_info_hour").html("0"+hour+"");
			}
			
			if(min>=10){
				$("#group_info_min").html(min+"");
			}else{
				$("#group_info_min").html("0"+min+"");
			}
			
			if(second>=10){
				$("#group_info_second").html(second+"");
			}else{
				$("#group_info_second").html("0"+second+"");
			}
		}else{
			$("#groupinfo_situation_title").html("该团已结束，请再开团").css("color","red");
			$("#group_info_hour").html("00");
			$("#group_info_min").html("00");
			$("#group_info_second").html("00");
			$(".footer3").attr("onclick","javascript:skipToGoodList();");
			$(".footer3 a").html("再开一个团");
			$("#group_head").attr("class","group_head_failure").html("组团超时");
		}
		
	}, 1000); 
}

function toTakepartGroup(id,role){
	doTakepartGroup(id,role);
}

function doTakepartGroup(id,role){
	var url = "/info/takepart_group?id="+id+"&role="+role;
	skipToUrl(url);
}
function displayLinkGuid(){
	$("#linkGuid").css("display","block");
}

function hideLinkGuid(){
	$("#linkGuid").css("display","none");
}

function onProvinceSelect(){
	$("#address_province").on("change",function(){
		getCities();
	});
}

function onCitySelect(){
	$("#address_city").on("change",function(){
		getAddresses();
	});
}

function getCities(){
	$.ajax({
		url:"/api/get_city_by_parentid?parent_id="+$("#address_province").val(),
		success:function(resp){
			$("#address_city").empty();
			for(var i = 0 ;i<resp.length;i++){
				$("#address_city").append("<option value='"+resp[i].id+"'>"+resp[i].name+"</option>");
			}
			getAddresses();
			
		}
	});
}

function getAddresses(){
	$.ajax({
		url:"/api/get_city_by_parentid?parent_id="+$("#address_city").val(),
		success:function(resp){
			$("#address_address").empty();
			for(var i = 0 ;i<resp.length;i++){
				$("#address_address").append("<option value='"+resp[i].id+"'>"+resp[i].name+"</option>");
			}
			
		}
	});
}

function setUserToken(userToken){
	if(userToken){
		window.localStorage.setItem("userToken",userToken);

	}
}

function skipToUrl(url,token){
	if(!token&&window.localStorage.getItem("userToken")){
		token = window.localStorage.getItem("userToken");
	}
	if(url.indexOf("?")>0){
		if(token){
			window.location.href=webPath+url+"&token="+token;
		}else{
			window.location.href=webPath+url;
		}
	}else{
		if(token){
			window.location.href=webPath+url+"?token="+token;
		}else{
			window.location.href=webPath+url;
		}
	}
	
}

function request(url,token,callback){
	if(!token&&window.sessionStorage.getItem("userToken")){
		token = window.sessionStorage.getItem("userToken");
	}
	if(url.indexOf("?")>0){
		if(token){
			url=webPath+url+"&token="+token;
		}else{
			url=webPath+url;
		}
	}else{
		if(token){
			url=webPath+url+"?token="+token;
		}else{
			url=webPath+url;
		}
	}
	$.ajax({
		url:url,
		method:"POST",
		success:function(resp){
			var content = resp.responseText;
			if(callback){
				callback.call(content);
			}
		}
	});
}

function skipToGroupInfo(id , token){
	skipToUrl("/info/group_info2?id="+id,token);
}

function skipToGoodInfo(id , token){
	skipToUrl("/info/good_info?id="+id,token);
}

function skipToGoodList(){
	skipToUrl("/main/good_list");
}

function skipToGroupList(token){
	skipToUrl("/main/group_list",token);
}

function skipToOrderList(token){
	skipToUrl("/main/order_list",token);
}

function skipToPersonCenter(token){
	skipToUrl("/main/personal_center",token);
}

function skipToGoodPay(id,pay_type,token){
	skipToUrl("/info/good_info_pay?good_id="+id+"&pay_type="+pay_type,token);
}

function skipToPersonCenter(){
	skipToUrl("/main/personal_center");
}

function skipToAddress(){
	skipToUrl("/info/address");
}

function paySuccess(goodId,payType,status,token){
	var callback = new Object();
	callback.call=function(response){
		alert(response);
	};
	request("/api/pay_success?pay_type="+payType+"&good_id="+goodId+"&status="+status,token);
}

function footActive(id){
	var footGoodList = $("#foot_good_list");	
	var footGroupList = $("#foot_group_List");
	var footOrderList = $("#foot_order_list");
	var foot_personal_center_list = $("#foot_personal_center_list");
	footGoodList.removeClass("active");
	footGroupList.removeClass("active");
	footOrderList.removeClass("active");
	
	foot_personal_center_list.removeClass("active");
	
	$("#"+id).addClass("active");
}
