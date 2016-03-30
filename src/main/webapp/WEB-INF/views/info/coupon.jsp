<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
	<div class="container">
		<div class="coupon">
			<div class="coupon_title">
				我的优惠券
			</div>
			
			<div class="coupon_list">
				<ul>
				<c:forEach items="${coupons}" var="coupon">
					<li  <c:if test="${coupon.status==1}"> onclick="javascript:skipToGoodInfo('${coupon.goodId}')" </c:if>  class="activityStyle"  <c:if test="${coupon.status==0 || coupon.status==2}">style="background-color:RGBA(130,132,129,.4);"</c:if>
					<c:if test="${coupon.status==1}">style="background-color:RGBA(212,75,64,0.03);"</c:if>>
						<div class="coupon_list_good_img"><img src="${coupon.goodImg}"></div>
						<div class="coupon_list_limit">
							<ul>
								<li>免单团券<font color="red">(团长免费开团)</font></li>
								<li>有效期:<br/>${coupon.beginTime}至${coupon.endTime}
								<li>${coupon.goodName}
								<li>
								<b style="color:red">
									<c:if test="${coupon.status==0}">
										已使用
									</c:if>
									<c:if test="${coupon.status==1}">
										<font style="color:black">未使用</font>
									</c:if>
									
									<c:if test="${coupon.status==2}">
										已过期
									</c:if>
								</b>
						    </ul>
						</div>
					</li>
				</c:forEach>
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