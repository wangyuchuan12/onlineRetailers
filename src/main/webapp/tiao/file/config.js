﻿function play68_init() {
	updateShare(0);
}
function play68_submitscore(score) {
	updateSharescore(score);
	Play68.shareFriend(); 
}
function updateShare(score) {
	var descContent = "超级虐跳——看似简单，过关很难~";
		switch(true){
		case score > 20 :
			shareTitle = "我过了" + score + "分，你牛逼玩20分给我看看~";
			break;
		case score > 10 :
			shareTitle = "我玩了" + score + "分，玩5分不容易，你能你上";
			break;
		case score > 0 :
			shareTitle = "我玩了" + score + "分，你牛逼玩5分给我看看";
			break;
		default: shareTitle = "想得5分不容易~~";
	}
	appid = '';
	Play68.setShareInfo(shareTitle,descContent);
}

function updateSharescore(score) {
	updateShare(score);
}