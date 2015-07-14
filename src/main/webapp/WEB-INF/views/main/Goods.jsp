<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div class="container">
        <div class="goods">
        	<c:forEach items="${goods}" var="good">
            <div class="good activityStyle">
           	 <div class="good_list_groupinfo">
                	<div class="good_list_groupinfo_discount">2.5折</div>
                	<div class="good_list_groupinfo_groupnum">5人团</div>
                </div>
                <div class="good-img">
                    <a href="">
                        <img src="${good.head_img}" />
                        <div class="good-title">
                          ${good.instruction}
                        </div>
                    </a>
                </div>
                <div class="tuan_g_core">
                    <div class="tuan_g_price">
                    	${good.group_num}人团
                        <b>
                         	￥${good.group_original_cost*good.group_discount}
                        </b>
                    </div>
                    <div class="tuan_g_btn">
                        	去开团
                    </div>
                </div>
                
            </div>
            </c:forEach>
        </div>
    </div>
</tiles:putAttribute>
</tiles:insertDefinition>