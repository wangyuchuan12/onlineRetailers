var webPath = "http://localhost";
function goodItemOnClick(id,token){
	var url;
	if(!token&&!localStorage.token){
		url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=wx7e3ed2dc655c0145&redirect_uri="+webPath+"/info/good_info?id="+id+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
	}else{
		if(token){
			url = webPath+"/info/good_info?id="+id+"&token="+token;
		}else{
			url = webPath+"/info/good_info?id="+id+"&token="+window.localStorage.getItem("userToken");
		}
		
		if(window.localStorage){
			window.localStorage.setItem("userToken",token);
		}
	}
	window.location.href=url;
}

function toPayOnClick(goodId , payType){
	var url;
	if(window.localStorage.getItem("userToken")){
		url = webPath+"/info/good_info_pay?pay_type="+payType+"&good_id="+goodId+"&token="+window.localStorage.getItem("userToken");
	}else{
		url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
		"appid=wx7e3ed2dc655c0145&redirect_uri="+webPath+"/info/good_info_pay?pay_type="+payType+"&good_id="+goodId+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
	}
	window.location.href=url;
}

