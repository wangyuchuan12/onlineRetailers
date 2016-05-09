<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
<div class="container">
	<div class="address">
		<div class="Address_list">
			<ul>
				<c:forEach items="${addresses}" var="address">
					<li  id="li-${address.id}">
						<div class="Address_list_botton" onclick="javascript:addressItemOnClick('${address.id}','${prepareRedirect}','${token.id}');">
							<input id="radio-${address.id}" name="默认" type="radio" <c:if test="${address.isDefault}">checked="checked"</c:if>/>
						</div>

						<div class="Address_list_information" onclick="javascript:addressItemOnClick('${address.id}','${prepareRedirect}','${token.id}');">
							<ul>
								<li>${address.name}
								<li>${address.phone}
								<li>${address.province}${address.city}${address.address}
								<li>${address.content}
							</ul>
						</div>
						<div class="address_mark">
							<div class="address_mark_default"><c:if test="${address.isDefault}">默认</c:if></div>
							
							<div class="address_mark_type">
								<c:if test="${address.type=='1'}">
									家庭
								</c:if>
								<c:if test="${address.type=='2'}">
									公司
								</c:if>
							
							</div>
							<div class="fa fa-pencil-square Address_edit" onclick="javascript:addressEditOnClick('${address.id}','${token.id}');"></div>
						</div>
						
					</li>
				</c:forEach>
					
			   </ul>
		 </div>
				
		
      </div>  
       	
  </div>
  <div class="foot4" onclick="javascript:toAddAddress('${prepareRedirect}','${token.id}');"><a>新增地址</a></div>
  <script type="text/javascript">
	  $(document).ready(function(){
		setUserToken("${token.id}");
		footActive("foot_personal_center_list");
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