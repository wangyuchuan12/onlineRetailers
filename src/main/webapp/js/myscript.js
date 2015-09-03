var webPath = "";
var cityObject = new Object();
var currentCity;
var currentAddress;
var currentProvince;
function addressAddSbumit(prepareRedirect,token,id){
	if(!token&&window.localStorage.getItem("userToken")){
		token = window.localStorage.getItem("userToken");
	}
	var addressName = $("#address_name").val();
	var addressPhonenumber = $("#address_phonenumber").val();
	var addressProvince = $("#address_province").val();
	var addressCity = $("#address_city").val();
	var addressAddress = $("#address_address").val();
	var addressType = $("#address_type").val();
	var addressContent = $("#address_content").val();
	if(!prepareRedirect){
		if(!id){
			skipToUrl("/action/do_address_save?city_id="+addressAddress+"&type="+addressType+"&content="+addressContent+"&phonenumber="+addressPhonenumber+"&name="+addressName);
		}else{
			skipToUrl("/action/do_address_save?city_id="+addressAddress+"&type="+addressType+"&content="+addressContent+"&phonenumber="+addressPhonenumber+"&name="+addressName+"&id="+id);
		}
	}else{
		if(!id){
			$.ajax({
				url:"/api/add_address?city_id="+addressAddress+"&type="+addressType+"&content="+addressContent+"&phonenumber="+addressPhonenumber+"&name="+addressName+"&token="+token,
				success:function(resp){
					skipToUrl(prepareRedirect);
				}
			});
		}else{
			$.ajax({
				url:"/api/add_address?city_id="+addressAddress+"&type="+addressType+"&content="+addressContent+"&phonenumber="+addressPhonenumber+"&name="+addressName+"&token="+token+"&id="+id,
				success:function(resp){
					skipToUrl(prepareRedirect);
				}
			});
		}
		
	}
	
}

function initData(id,name,phonenumber,type,province,city,address,content){
	currentProvince = province;
	currentCity = city;
	currentAddress = address;
	var addressPhonenumber = $("#address_phonenumber").val(phonenumber);
	var addressProvince = $("#address_province").val(province);
	var addressCity = $("#address_city").val(city);
	$("#address_name").val(name);
	var addressAddress = $("#address_address").val(address);
	var addressType = $("#address_type").val(type);
	var addressContent = $("#address_content").val(content);
}


function addressEditOnClick(id,token){
	var obj = new Object();
	obj.address_id = id;
	skipToUrl("/info/address_save", token,obj);
}

function addressItemOnClick(id,prepareRedirect,token){
	if(!token&&window.localStorage.getItem("userToken")){
		token = window.localStorage.getItem("userToken");
	}
	var status =Math.random()*Math.random();
	$.ajax({
		url:"/api/set_default_address?address_id="+id+"&token="+token+"&status="+status,
		success:function(resp){
			if(prepareRedirect){
				alert("prepareRedirect:{}"+prepareRedirect);
				skipToUrl(prepareRedirect);
			}
			else{
				alert("addresses");
				skipToUrl("/info/address");
			}
		}
	});
}

function goodInfoPayAddressOnClick(payType,state,goodId){
	var obj = new Object();
	obj.prepare_redirect = "/info/good_info_pay?pay_type="+payType+"&state="+state+"&good_id="+goodId;
	skipToUrl("/info/address", null,obj);
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
			$("#address_city").val(currentCity);
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
			$("#address_address").val(currentAddress);
			
		}
	});
}

function setUserToken(userToken){
	if(userToken){
		window.localStorage.setItem("userToken",userToken);

	}
}

function skipToUrl(url,token,params){
	var temp = document.createElement("form");        
    temp.action = webPath+url;        
    temp.method = "post";        
    temp.style.display = "none";        
    var opt = document.createElement("textarea");        
    opt.name = "token";
	if(!token&&window.localStorage.getItem("userToken")){
		token = window.localStorage.getItem("userToken");
	}
	opt.value = token;
    temp.appendChild(opt);              
    
    
    for(var name in params){
    	var node = document.createElement("textarea");
    	node.value=params[name];
    	node.name=name;
    	temp.appendChild(node);
    }
    
    document.body.appendChild(temp);
    temp.submit();
	
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
	skipToUrl("/main/order_list?status=0",token);
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

function skipToOrderInfo(id){
	skipToUrl("/info/order_info?id="+id);
}

function skipToCoupons(){
	skipToUrl("/info/coupon");
}

function paySuccess(goodId,payType,status,token){
	var callback = new Object();
	callback.call=function(response){
		alert(response);
	};
	request("/api/pay_success?pay_type="+payType+"&good_id="+goodId+"&status="+status,token);
}

function orderActive(status){
	if(status=="0"){
		$("#all_orders").css("color","red");
		$("#prepare_pay_orders").css("color","RGB( 39,131,241)");
		$("#prepare_over_orders").css("color","RGB( 39,131,241)");
	}else if (status=="1") {
		$("#all_orders").css("color","RGB( 39,131,241)");
		$("#prepare_pay_orders").css("color","red");
		$("#prepare_over_orders").css("color","RGB( 39,131,241)");
	}else if (status=="2") {
		$("#all_orders").css("color","RGB( 39,131,241)");
		$("#prepare_pay_orders").css("color","RGB( 39,131,241)");
		$("#prepare_over_orders").css("color","red");
	}
	
}

function onOrderClick(){
	$("#all_orders").on("click",function(){
		skipToUrl("/main/order_list?status=0");
	});
	
	$("#prepare_pay_orders").on("click",function(){
		skipToUrl("/main/order_list?status=1");
	});
	
	$("#prepare_over_orders").on("click",function(){
		skipToUrl("/main/order_list?status=2");
	});
}

function toAddAddress(){
	skipToUrl("/info/address_add");
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



function wxConfig(appId,signature,noncestr,t){
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:appId, // 必填，公众号的唯一标识
	    timestamp:t, // 必填，生成签名的时间戳
	    nonceStr:noncestr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: ['onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
}