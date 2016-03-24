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
					<li onclick="javascript:skipToGoodInfo('${coupon.goodId}')">
						<div class="coupon_list_goods">${coupon.goodName}</div>
						<div class="coupon_list_limit">
							<ul>
								<li>免单团券
								<li>有效期:<br/>${coupon.beginTime}至${coupon.endTime}
								<li>团长免费开团
								<li>
								<b style="color:red">
									<c:if test="${coupon.status==0}">
										已使用
									</c:if>
									<c:if test="${coupon.status==1}">
										未使用
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
    			});
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>