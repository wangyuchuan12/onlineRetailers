<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<tiles:insertAttribute name="resource"/>
</head>
<body>
<div id="wrapper">
   <tiles:insertAttribute name="header" />
   <tiles:insertAttribute name="body" />
   <tiles:insertAttribute name="footer" />
</div>
<!-- /#wrapper -->

</body>
</html>
