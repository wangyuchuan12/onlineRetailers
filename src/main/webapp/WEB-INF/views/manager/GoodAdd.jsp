<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<html>
	<form action="/manager/api/add_good" method="post" enctype="multipart/form-data">
		商品名称：<input type="text" name="name"/>
		单卖折扣：<input type="text" name="alone_discount"/>
		单卖原价：<input type="text" name="alone_original_cost"/>
		物流费用：<input type="text" name="flow_price"/>
		团购折扣：<input type="text" name="group_discount"/>
		团购原价：<input type="text" name="group_original_cost"/>
		简介：<input type="text" name="instruction"/>
		市场价格：<input type="text" name="market_price"/>
		<br/>
		主图片：<input type="file" name="head_img"/>
		<input type="submit" value="提交"/>
	</form>
</html>