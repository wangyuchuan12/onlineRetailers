<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
    <div class="container" >
       <img src="/img/help.jpg" width="100%">
     </div>
      <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_personal_center_list");
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>
