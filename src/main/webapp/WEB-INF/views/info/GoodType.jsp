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
				<c:forEach items="${goodTypes}" var="goodType">
					<li onclick="skipToGoodList('${goodType.id}')">

						<div class="good_type_name">${goodType.name}</div>
						<div class="good_type_mark">
							<c:if test="${goodType.isDefault}"><div class="good_type_mark_default">默认</div></c:if>
						</div>
					</li>
				</c:forEach>	
					
			

					
			   </ul>
		 </div>
				
		
      </div>  
       	
  </div>
  <script type="text/javascript">
	  $(document).ready(function(){
		setUserToken("${token.id}");
		footActive("foot_personal_center_list");
		wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
		wx.ready(function(){
			wxOnMenuShareAppMessage("${typeName}","${typeTitle}",webPath+"/main/good_list?good_type=${goodType}","${typeImg}","link");
		});
	  });
  </script>
</tiles:putAttribute>
</tiles:insertDefinition>