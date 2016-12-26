var webPath = "";
var cityObject = new Object();
var currentCity;
var currentAddress;
var currentProvince;

function checkMobile(mobile){
	if(!/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.test(mobile))
	{
	  return false;
	}else{
		return true;
	}
}

function checkStr(str){
	 var pattern = /^[\w\u4e00-\u9fa5]+$/gi;
     if(pattern.test(str))
     {
         return true;
     }
     return false;
}

function checkAddress(value){
	var myreg=/^([\"\”\“\’\'\'\&lt;\&gt;]).*$/;
	if(myreg.test(value)){
		return false;
	}else{
		return true;
	}
}

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
	if(!addressName){
		alert("收件人不能为空");
		return false;
	}
	
	if(!addressPhonenumber){
		alert("电话号码不能为空");
		return false;
	}
	
	if(!checkMobile(addressPhonenumber)){
		alert("手机号码格式错误");
		return false;
	}
	
	if(!addressProvince){
		alert("省份不能为空");
		return false;
	}
	
	if(!addressCity){
		alert("城市不能为空");
		return false;
	}
	
	if(!addressAddress){
		alert("区/县不能为空");
		return false;
	}
	
	if(!addressType){
		alert("地址类别不能为空");
		return false;
	}
	
	if(!addressContent){
		alert("详细地址不能为空");
		return false;
	}
	
	if(!checkAddress(addressContent)){
		alert("地址不能包含特殊字符");
		return false;
	}
	
	if(!checkStr(addressName)){
		alert("收件人不能包含特殊字符");
		return false;
	}
	
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
				skipToUrl(prepareRedirect);
			}
			else{
				skipToUrl("/info/address");
			}
		}
	});
}

function goodInfoPayAddressOnClick(payType,state,goodId,groupId){
	var obj = new Object();
	obj.prepare_redirect = "/info/good_info_pay?pay_type="+payType+"&state="+state+"&good_id="+goodId+"&group_id="+groupId;
	skipToUrl("/info/address", null,obj);
}

function initGroupInvalidDate(startTime2,timeLong,id){
	if(!id){
		id="";
	}else{
		id="_"+id;
	}
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
				$("#group_info_hour"+id).html(hour+"");
			}else{
				$("#group_info_hour"+id).html("0"+hour+"");
			}
			
			if(min>=10){
				$("#group_info_min"+id).html(min+"");
			}else{
				$("#group_info_min"+id).html("0"+min+"");
			}
			
			if(second>=10){
				$("#group_info_second"+id).html(second+"");
			}else{
				$("#group_info_second"+id).html("0"+second+"");
			}
		}else{
			$("#group_info_hour"+id).html("00");
			$("#group_info_min"+id).html("00");
			$("#group_info_second"+id).html("00");
			$(".footer3").attr("onclick","javascript:skipToGoodList();");
			$(".footer3 a").html("再开一个团");
			$("#group_head").attr("class","group_head_failure").html("组团超时");
			$("#groupinfo_situation_title").html("该团已结束，请再开团").css("color","red");
		}
		
	}, 1000); 
}

function good_info_group_info_time(startTime2,timeLong){
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
			$("#group_info_hour").html("00");
			$("#group_info_min").html("00");
			$("#group_info_second").html("00");
		}
		
	}, 1000); 
}

function toTakepartGroup(id,role){
	doTakepartGroup(id,role);
}

function skipToChatList(){
	var url = "/info/chat_list";
	skipToUrl(url);
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
			$("#address_city").append("<option value=''>请选择</option>");
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
			$("#address_address").append("<option value=''>请选择</option>");
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

function setGoodType(goodType){
	if(goodType){
		window.localStorage.setItem("goodType",goodType);

	}
}

function skipToRedirectUrl(url,token,quickId){
	if(url.indexOf("?")>0){
		url=url+"&quick_id="+quickId;
	}else{
		url=url+"?quick_id"+quickId;
	}
	
	if(token!=null&&url.indexOf("?")>0){
		url=url+"&token="+token;
	}else if (token!=null&&url.indexOf("?")<0) {
		url=url+"?token"+token;
	}
	skipToUrl(url,token,null,true)
}

function skipToUrl(url,token,params,flag){
	var temp = document.createElement("form");
	if(!flag){
		temp.action = webPath+url;
	}else{
		temp.action = url;
	}
    temp.method = "post";        
    temp.style.display = "none";        
    var opt = document.createElement("textarea");        
    opt.name = "token";
	if(!token&&window.localStorage.getItem("userToken")){
		token = window.localStorage.getItem("userToken");
	}
	opt.value = token;
    temp.appendChild(opt);              
    
    if(params){
	    for(var name in params){
	    	var node = document.createElement("textarea");
	    	node.value=params[name];
	    	node.name=name;
	    	temp.appendChild(node);
	    }
    }
    
    document.body.appendChild(temp);
    temp.submit();
	
}

function skipToChat(adminId,type,goodId,orderId,groupId,token){
	var params = new Object();
	params.admin_id = adminId;
	params.type = type;
	params.goodId = goodId;
	params.orderId = orderId;
	params.groupId = groupId;
	skipToUrl("/info/chat_view",token,params)
}


function request(url,token,callback){
	if(!token&&window.sessionStorage.getItem("userToken")){
		token = window.sessionStorage.getItem("userToken");
	}
	var params = new Object();
	if(window.sessionStorage.getItem("goodTpe")){
		params.good_type = window.sessionStorage.getItem("goodType");
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
		params:params,
		success:function(resp){
			if(callback){
				callback.success(resp);
			}
		},
		failure:function(){
			callback.failure(resp);
		}
	});
}

function skipToLastestGroupInfo(token){
	skipToUrl("/info/lastest_group_info",token);
}

function skipToLastestOrderInfo(token){
	skipToUrl("/info/last_order",token);
}



function skipToGroupInfo(id , token){
	skipToUrl("/info/group_info2?id="+id,token);
}

function skipToGoodInfo(id , token){
	skipToUrl("/info/good_info?id="+id,token);
}

function skipToLuckDraw(luckDrawRecordId,token){
	skipToUrl("/game/receive_good?luckDrawRecordId="+luckDrawRecordId,token);
}

function skipToGoodList(goodType,token){
	if(goodType){
		skipToUrl("/main/good_list?type=1&good_type="+goodType,token);
	}else{
		skipToUrl("/main/good_list?type=1",token);
	}
	
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

function skipToGoodPay(id,pay_type,token,groupId,cost,params){
	var url = "/info/good_info_pay?good_id="+id+"&pay_type="+pay_type+"&group_id="+groupId+"&cost="+cost;
	
	skipToUrl(url,token,params);
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

function skipToGoodType(){
	skipToUrl("/info/good_type");
}

function paySuccess(goodId,payType,status,token,callback){
	var random = Math.random()*Math.random();
	request("/api/pay_success?pay_type="+payType+"&good_id="+goodId+"&status="+status+"&random="+random,token,callback);
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

function skipToHelp(){
	skipToUrl("/info/help");
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

function toAddAddress(prepareRedirect,token){
	var obj = new Object();
	obj.prepareRedirect = prepareRedirect;
	skipToUrl("/info/address_add",token,obj);
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
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:appId, // 必填，公众号的唯一标识
	    timestamp:t, // 必填，生成签名的时间戳
	    nonceStr:noncestr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: [
	                'checkJsApi',
	                'onMenuShareTimeline',
	                'onMenuShareAppMessage',
	                'onMenuShareQQ',
	                'onMenuShareWeibo',
	                'hideMenuItems',
	                'showMenuItems',
	                'hideAllNonBaseMenuItem',
	                'showAllNonBaseMenuItem',
	                'translateVoice',
	                'startRecord',
	                'stopRecord',
	                'onRecordEnd',
	                'playVoice',
	                'pauseVoice',
	                'stopVoice',
	                'uploadVoice',
	                'downloadVoice',
	                'chooseImage',
	                'previewImage',
	                'uploadImage',
	                'downloadImage',
	                'getNetworkType',
	                'openLocation',
	                'getLocation',
	                'hideOptionMenu',
	                'showOptionMenu',
	                'closeWindow',
	                'scanQRCode',
	                'chooseWXPay',
	                'openProductSpecificView',
	                'addCard',
	                'chooseCard',
	                'openCard'
	              ]
	});
//	wx.ready(function(){
//		wx.showMenuItems({
//            menuList: [
//                'menuItem:profile', // 添加查看公众号
//                'menuItem:addContact'
//            ]
//        });
//		
//	});
}

function wxOnMenuShareAppMessage(title,desc,link,imgUrl,type,dataUrl){
	var obj = new Object();
	obj.title = title;
	obj.desc = desc;
	obj.link = link;
	obj.imgUrl = imgUrl;
	obj.type = type;
	obj.dataUrl = dataUrl;
	obj.success = function(){
		
	}
	obj.cancel = function(){
		
	}
	wx.onMenuShareAppMessage(obj);
	wx.onMenuShareTimeline(obj);
}

function onChooseWXPay(appid,pack,nonceStr,paySign,signType,timestamp,goodId,payType,status,token,address,personName,phonenumber,outTradeNo){
	$("#good_info_pay_button_href").text("正在支付").css("background-color:yellow");
	var receiveAddress = "收件人姓名："+personName+"-收件人地址："+address+"-联系号码："+phonenumber;
	var setAddressCallback = new Object();
	request("/api/set_temporary_data?key="+outTradeNo+"&name=address"+"&value="+receiveAddress,token,setAddressCallback);
		setAddressCallback.success = function(){
			if(payType!=2){
				wx.ready(function(){
					wx.chooseWXPay({
						timestamp:timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
					    nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
					    package: pack, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
					    signType:signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
					    paySign: paySign, // 支付签名
					    success: function (res) {
					    	if(payType=="0"||payType=="3"){
					    		skipToLastestGroupInfo();
					    	}else{
					    		var getOrderIdCallback = new Object();
					    		skipToLastestOrderInfo();
//					    		getOrderIdCallback.success = function(resp){
//					    			
//					    			skipToLastestOrderInfo();
//					    		}
					    	//	request("/api/get_temporary_data?key="+outTradeNo+"&name=orderId",token,getOrderIdCallback);
					    	}
					    	
					    },
					    
					    cancel:function(res){
					    	var callback = new Object();
					    	callback.success = function(resp){
					    		var obj = eval("("+resp+")");
					    		skipToOrderInfo(obj.orderId);
					    	};
					    	callback.failure = function(resp){
					    		alert("失败："+resp);
					    	};
					    	request("/api/pay_failure?good_id="+goodId+"&pay_type="+payType,token,callback);
					    },
					    
					    fail:function(res){
					    	var callback = new Object();
					    	callback.success = function(resp){
					    		
					    	};
					    	callback.failure = function(resp){
					    		
					    	};
					    	request("/api/pay_failure?good_id="+goodId+"&pay_type="+payType,token,callback);
					    }
					});
					
				});
			}else{
				var callback = new Object();
				callback.success = function(a){
					skipToLastestGroupInfo();
				}
				request("/api/wx/pay_success?pay_type=2&outTradeNo="+outTradeNo,token,callback);
			}
			
		}
}



function handleSpanItems(item,callback){
	
	if(item.attr("type")=="image"){
		var children = item.children(".good_info_check_detail_item_img");
		
		children.each(
				function(){
					$(this).click(function(){
						selectImage(children,$(this));
						var value = $(this).attr("value");
						callback.call($(this),value);
					});
				}		
			);;
	}else{
		var children = item.children(".good_info_check_detail_item_apan");
		children.each(
			function(){
				$(this).click(function(){
					selectSpan(children,$(this));
					var value = $(this).attr("value");
					callback.call($(this),value);
				});
			}		
		);;
	}
	
}

function selectImage(children,image){
	children.each(function(){
		$(this).css("background-color","white");
		$(this).attr("isSelected",false);
	});
	if(image){
		image.css("background-color","red");
		image.attr("isSelected",true);
	}
}

function selectSpan(children,span){
	children.each(function(){
		$(this).css("background-color","white");
		$(this).attr("isSelected",false);
	});
	span.css("background-color","red");
	span.attr("isSelected",true);
}

function getItemValue(item){
	var children = item.children(".good_info_check_detail_item_apan");
	var value = null;
	children.each(function(){
		if($(this).attr("isSelected")&&$(this).attr("isSelected")=="true"){
			value = $(this).attr("value");
		}
	});
	return value;
}
