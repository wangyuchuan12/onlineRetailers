<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
	<div class="container">
        <div class="groups">
				<ul>
				<c:forEach items="${groups}" var="group">
				
					<li >
							<a class="group">
								<div class="group_header">
									<img src="${group.head_img}"/>
									<div class="group_good_info">
										<div class="group_good_info_title"><b>${group.name}</b></div>
										<div class="group_good_info_price">
											成团价：<b>${group.total_price}元</b>
										</div>
									</div>
								</div>
								<div class="group_footer">
									<span class="group_result">
										<c:if test="${group.result==0}">组团失败</c:if>
										<c:if test="${group.result==1}">正在组团</c:if>
										<c:if test="${group.result==2}">组团成功</c:if>
									</span>
									
									<div class="group_details activityStyle" onclick="javascript:skipToGroupInfo('${group.group_id}');">查看团详情</div>
									<div class="group_details activityStyle" onclick="javascript:skipToOrderInfo('${group.order_id}');">查看订单详情</div>
								</div>
							</a>
					</li>
				</c:forEach>
					
				</ul>
			</div>
    </div>
    <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_group_list");
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