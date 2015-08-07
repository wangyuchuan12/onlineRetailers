var webPath = "http://www.chengxi.pub";
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
			window.location.href=url+"&token="+token;
		}else{
			window.location.href=url;
		}
	}else{
		if(token){
			window.location.href=url+"?token="+token;
		}else{
			window.location.href=url;
		}
	}
	
}

function skipToGoodInfo(id , token){
	skipToUrl("/info/good_info?id='"+id+"'",token);
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
