
$(document).ready(
	function(){
		
	}
);

function goodItemOnClick(id,token){
	var url;
	if(!token){
		url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=wx7e3ed2dc655c0145&redirect_uri=http://www.chengxi.pub/info/good_info?id="+id+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
	}else{
		url = "http://www.chengxi.pub/good_info?id="+id+"&token="+token;
	}
	window.location.href=url;
	
}

