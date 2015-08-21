<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
<div class="container">
	<div class="address">
		<div class="Address_list">
			<ul>
				<c:forEach items="${addresses}" var="address">
					<li>
						<div class="Address_list_botton">
							<input name="默认" type="radio" <c:if test="${address.isDefault}">checked="checked"</c:if>/>
						</div>

						<div class="Address_list_information">
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
							<div class="fa fa-pencil-square Address_edit"></div>
						</div>
						
					</li>
				</c:forEach>
					
			   </ul>
		 </div>
				
		
      </div>  
       	
  </div>
  <div class="foot4"><a href="/info/address_add">新增地址</a></div>
  <script type="text/javascript">
	  $(document).ready(function(){
		setUserToken("${token.id}");
	  });
  </script>
</tiles:putAttribute>
</tiles:insertDefinition>