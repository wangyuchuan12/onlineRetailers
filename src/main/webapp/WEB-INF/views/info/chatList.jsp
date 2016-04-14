<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
	<div class="container">
		<div class="chat_container">
			<div class="coupon_title">
				我的消息
			</div>
			
			<div class="chat_list">
				<ul>
				
					<li>
						<div class="chat_img"> <img src="http://wx.qlogo.cn/mmopen/xPXrGtX7mxzz6NLDbwDETgAp79xQLWnHV10FBSeqefOyjqjXJHXWxYQ5vtMO6J6qqdOTXW1ZAJiad79rtXLf6s1LxoEfA6N6b/0"></div>
						<div class="chat_num">1</div>
						<div class="chat_list_limit">
							<ul>
								<li class="chat_list_limit_name"><b>美丽家纺</b>
								<li class="chat_list_limit_content">这是一家相对来说比较成熟的企业，我们有300多员工
								
						    </ul>
						</div>
					</li>
					<li>
						<div class="chat_img"> <img src="http://wx.qlogo.cn/mmopen/xPXrGtX7mxzz6NLDbwDETgAp79xQLWnHV10FBSeqefOyjqjXJHXWxYQ5vtMO6J6qqdOTXW1ZAJiad79rtXLf6s1LxoEfA6N6b/0"></div>
						<div class="chat_num">1</div>
						<div class="chat_list_limit">
							<ul>
								<li class="chat_list_limit_name"><b>美丽家纺</b>
								<li class="chat_list_limit_content">这是一家相对来说比较成熟的企业，我们有300多员工
								
						    </ul>
						</div>
					</li>
					
				</ul>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_personal_center_list");
    			setUserToken("${token.id}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("${typeName}","${typeTitle}",webPath+"/main/good_list?good_type=${goodType}","${typeImg}","link");
    				wx.hideMenuItems({
    				    menuList: ["menuItem:copyUrl","menuItem:exposeArticle","menuItem:setFont","menuItem:readMode","menuItem:originPage","menuItem:share:email","menuItem:openWithQQBrowser","menuItem:openWithSafari"] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
    				});
    				wx.showMenuItems({
    		            menuList: [
    		                "menuItem:profile",// 添加查看公众号
    		                "menuItem:addContact"
    		            ]
    		        	});
    			});
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>