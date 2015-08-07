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
					<li>
						<div class="Address_list_botton">
							<input name="默认" type="radio" />
						</div>

						<div class="Address_list_information">
							<ul>
								<li>王二
								<li>13709876543
								<li>江苏南京
								<li>解放路10号光明小区3幢3单元2002室
							</ul>
						</div>
						<div class="address_mark">
							<div class="address_mark_default">默认</div>
							<div class="address_mark_type">家庭</div>
						</div>
						<div class="Address_edit">编辑</div>
					</li>
					<li>
						<div class="Address_list_botton">
							<input name="默认" type="radio" />
						</div>

						<div class="Address_list_information">
							<ul>
								<li>王二
								<li>13709876543
								<li>江苏南京
								<li>解放路10号光明小区3幢3单元2002室
							</ul>
						</div>
						<div class="address_mark">
							<div class="address_mark_default">默认</div>
							<div class="address_mark_type">公司</div>
						</div>
						<div class="Address_edit">编辑</div>
					</li>
			   </ul>
		 </div>
				
		
      </div>  
       	
  </div>
  <div class="foot4"><a href="">新增地址</a></div>
</tiles:putAttribute>
</tiles:insertDefinition>