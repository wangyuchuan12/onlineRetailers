<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
	<div class="container">
        <div class="personal_center">
        	<div class="personal_center_header">
        		<div class="personal_center_header_img">
        			<img src="${userInfo.headImgurl}"/>
        		</div>
        		<div class="personal_center_userinfo">
        			<div class="personal_center_userinfo_name">${userInfo.nickname}</div>
        			<%-- <div class="personal_center_userinfo_integral">积分：${userInfo.integral}</div> --%>
        		</div>
        	</div>
        	<div class="personal_center_entry">
        		<ul>
        			<li>
        				<div class="personal_center_item activityStyle"  onclick="skipToCoupons();">
        					<div class="personal_center_item_icon" style="background-color:blue;padding-left:3px;padding-top:5px;">
        						<i class="fa fa-money"></i>
        					</div>
        					<div class="personal_center_item_content">我的开团劵</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        			
        			<li>
        				<div class="personal_center_item activityStyle"  onclick="skipToAddress();">
        					<div class="personal_center_item_icon" style="background-color:red;padding-top:5px;padding-left:3px;">
        						<i class="fa fa-shopping-cart"></i>
        					</div>
        					<div class="personal_center_item_content">我的地址</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        			
        			
        			
        			
        			
        			<li>
        				<div class="personal_center_item activityStyle"  onclick="skipToGoodType();">
        					<div class="personal_center_item_icon" style="background-color:orange;padding-top:3px;padding-left:5px;">
        						<i class="fa fa-book"></i>
        					</div>
        					<div class="personal_center_item_content">商品类别</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        			
        			
        			<li>
        				<div class="personal_center_item activityStyle"  onclick="skipToChatList();">
        					<div class="personal_center_item_icon" style="background-color:black;padding-top:3px;padding-left:5px;">
        						<i class="fa fa-comments-o"></i>
        					</div>
        					<div class="personal_center_item_content">我的消息</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        			
        			<li>
        				<div class="personal_center_item activityStyle"  onclick="skipToHelp();">
        					<div class="personal_center_item_icon" style="background-color:green;padding-top:3px;padding-left:5px;">
        						<i class="fa fa-question-circle"></i>
        					</div>
        					<div class="personal_center_item_content">帮助</div>
        					<div class="personal_center_item_footer">&gt</div>
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
    				wxOnMenuShareAppMessage("${typeName}","${typeTitle}","${domainName}/main/good_list?good_type=${goodType}","${typeImg}","link");
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