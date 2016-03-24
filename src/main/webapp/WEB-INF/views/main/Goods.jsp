<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div class="container">
        <div class="goods">
        	<c:forEach items="${goods}" var="good">
            <div class="good activityStyle" onclick="skipToGoodInfo('${good.id}')"<c:if test="${token!=null}">,'${token}'</c:if>)">
            
           	 <div class="good_list_groupinfo">
                	<div class="good_list_groupinfo_discount"><fmt:formatNumber type="number" value="${good.group_discount*10}" maxFractionDigits="2"/>折</div>
                	<div class="good_list_groupinfo_groupnum">${good.group_num}人团</div>
                </div>
                <div class="good-img">

                        <img src="${good.head_img}" />
                        
      
                </div>
                
                <div class="good-title">
                          ${good.title}
                    
                   <br/><b>活动：开团成功赠送一张开团劵 </b>
                </div>
                
                <div class="tuan_g_core">
                    <div class="tuan_g_price">
                    	${good.group_num}人团
                        <b>
                        		￥<fmt:formatNumber type="number" value="${good.group_original_cost*good.group_discount}" maxFractionDigits="3"/>
                        </b>
                    </div>
                    <div class="tuan_g_btn">
                        	去开团
                    </div>
                </div>
                <div style="position: relative;padding-left:10px;top:-50px;">
	                <div class="good_name">${good.name}</div>
	                <div class="good_market_price">市场价：￥${good.market_price}</div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_good_list");
    			setUserToken("${token.id}");
    			setGoodType("${goodType}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("${typeName}","${typeTitle}",webPath+"/main/good_list?good_type=${goodType}","${typeImg}","link");
    			});
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>