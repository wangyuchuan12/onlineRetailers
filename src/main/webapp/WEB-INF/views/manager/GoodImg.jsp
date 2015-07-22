<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<html lang="en">
<head>
<title>图片管理</title>
<link href="/css/manager.css" rel="stylesheet">
</head>
<body>

	<div class="image_manager">
		<div class="image_manager_header">
			<a href="#"> <img src="ImgManger.png">
			</a>
		</div>
		<div class="image_mmanager_buttons">
			<div class="image_mmanager_buttons_file">
				<input type="file">
			</div>
			<div class="image_mmanager_buttons_add">
				<input type="submit" value="新增">
			</div>
			<div class="image_mmanager_buttons_delete">
				<input type="button" value="删除">
			</div>
		</div>
	<div class="good_img_imagelist">
		<table>
			<tr>
				<td><img src=""/></td>
				<td><img src=""/></td>
				<td><img src=""/></td>
				<td><img src=""/></td>
			</tr>
			
			<tr>
				<td><img src=""/></td>
				<td><img src=""/></td>
				<td><img src=""/></td>
				<td><img src=""/></td>
			</tr>

		</table>
	</div>

	</div>

</body>
</html>
