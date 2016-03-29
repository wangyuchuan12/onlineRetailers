<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div class="container">
		
		<div class="swiper-container swiper-container-horizontal">
	        <div class="swiper-wrapper" style="transition: 0ms; -webkit-transition: 0ms; -webkit-transform: translate3d(0px, 0px, 0px);">
	             <c:forEach items="${adGoodHeaderImgs}" var="adGoodHeaderImg">
		             <div class="swiper-slide swiper-slide-active" style="width: 98%;">
						<a href="javascript:skipToRedirectUrl('${adGoodHeaderImg.url}')"><img src="${adGoodHeaderImg.imgUrl}" style="height:250px;width:100%;"></a>
		             </div>
	             </c:forEach>
	            
	        </div>
	        <div class="swiper-pagination swiper-pagination-clickable"><span class="swiper-pagination-bullet swiper-pagination-bullet-active"></span><span class="swiper-pagination-bullet"></span><span class="swiper-pagination-bullet"></span><span class="swiper-pagination-bullet"></span></div>
		</div>
    <div class="quick_entrance">
        <ul>
        	  <c:forEach items="${quickEntrances}" var="quickEntrance">
            	<li><a href="${quickEntrance.url}"><img src="${quickEntrance.imgUrl}"><em></em><span>${quickEntrance.name}</span></a></li>                       
        	  </c:forEach>
        </ul>
    </div>
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
                    <c:if test="${good.notice!=null}">
                    	<br/><b style="color:black;">${good.notice}</b>
                    </c:if>
                   
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
    			
    			$(function(){
    			      $('.flexslider').flexslider({
    			        animation: "slide",
    			        start: function(slider){
    			          $('body').removeClass('loading');
    			        }
    			      });
    			    });
    			
    			
    			$(function(){
  			      $('.test').flexslider({
  			        animation: "slide",
  			        start: function(slider){
  			          $('body').removeClass('loading');
  			        }
  			      });
  			    });
    		
    			
    			
    			
    			footActive("foot_good_list");
    			setUserToken("${token.id}");
    			setGoodType("${goodType}");
    			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
    			wx.ready(function(){
    				wxOnMenuShareAppMessage("${typeName}","${typeTitle}",webPath+"/main/good_list?good_type=${goodType}","${typeImg}","link");
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
    		
    		window.onload=function(){		
				var swiper = new Swiper('.swiper-container', {
	        pagination: '.swiper-pagination',
	        paginationClickable: true,
	        spaceBetween: 30,
	        centeredSlides: true,
	        autoplay: 2500,
	        autoplayDisableOnInteraction: false
	    });
	}
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>