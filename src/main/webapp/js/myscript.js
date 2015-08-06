var webPath = "http://www.chengxi.pub";
window.localStorage.setItem("userToken","28cf76cc-9410-447e-a529-baa108e231fa");
function goodItemOnClick(id,token){
	var url;
	
	if(!token&&!localStorage.getItem("userToken")){
		url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=wx7e3ed2dc655c0145&redirect_uri="+webPath+"/info/good_info?id="+id+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
	}else{
		if(token){
			url = webPath+"/info/good_info?id="+id+"&token="+token;
			window.localStorage.setItem("userToken",token);
		}else{
			url = webPath+"/info/good_info?id="+id+"&token="+window.localStorage.getItem("userToken");
		}
	}
	alert(userToken);
	window.location.href=url;
}

function toPayOnClick(goodId , payType){
	var url;
	if(window.localStorage.getItem("userToken")){
		url = webPath+"/info/good_info_pay?pay_type="+payType+"&good_id="+goodId+"&token="+window.localStorage.getItem("userToken");
	}else{
		url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
		"appid=wx7e3ed2dc655c0145&redirect_uri="+webPath+"/info/good_info_pay?good_id="+goodId+"&pay_type="+payType+"&response_type=code&scope=snsapi_userinfo&state="+payType+"&connect_redirect=1#wechat_redirect";
	}
	window.location.href=url;
}

function setUserToken(userToken){
	window.localStorage.setItem("userToken",userToken);
}

