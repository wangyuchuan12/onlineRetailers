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
	if(!token&&window.localStorage.getItem("userToken")){
		token = window.localStorage.getItem("userToken");
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
