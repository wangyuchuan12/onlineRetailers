<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>









<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	
	<div id="loading" style="position:fixed;z-index:10000;width:100%;height:100%;">


    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"> 
    <meta http-equiv="Pragma" content="no-cache"> 
    <meta http-equiv="Expires" content="0">

    <title> </title>
    <style type="text/css">
    body{
        overflow:hidden;
    }
    .spinner {
        width: 34px;
        height: 27px;
        position: relative;
        margin: 0 auto;
        top: 150px;
    }
    
    .container1 > div,
    .container2 > div,
    .container3 > div {
        width: 10px;
        height: 10px;
        background-color: #DF2127;
        border-radius: 100%;
        position: absolute;
        -webkit-animation: bouncedelay 1.2s infinite ease-in-out;
        animation: bouncedelay 1.2s infinite ease-in-out;
        -webkit-animation-fill-mode: both;
        animation-fill-mode: both;
    }
    
    .spinner .spinner-container {
        position: absolute;
        width: 100%;
        height: 100%;
    }
    
    .container2 {
        -webkit-transform: rotateZ(45deg);
        transform: rotateZ(45deg);
    }
    
    .container3 {
        -webkit-transform: rotateZ(90deg);
        transform: rotateZ(90deg);
    }
    
    .circle1 {
        top: 0;
        left: 0;
    }
    
    .circle2 {
        top: 0;
        right: 0;
    }
    
    .circle3 {
        right: 0;
        bottom: 0;
    }
    
    .circle4 {
        left: 0;
        bottom: 0;
    }
    
    .container2 .circle1 {
        -webkit-animation-delay: -1.1s;
        animation-delay: -1.1s;
    }
    
    .container3 .circle1 {
        -webkit-animation-delay: -1.0s;
        animation-delay: -1.0s;
    }
    
    .container1 .circle2 {
        -webkit-animation-delay: -0.9s;
        animation-delay: -0.9s;
    }
    
    .container2 .circle2 {
        -webkit-animation-delay: -0.8s;
        animation-delay: -0.8s;
    }
    
    .container3 .circle2 {
        -webkit-animation-delay: -0.7s;
        animation-delay: -0.7s;
    }
    
    .container1 .circle3 {
        -webkit-animation-delay: -0.6s;
        animation-delay: -0.6s;
    }
    
    .container2 .circle3 {
        -webkit-animation-delay: -0.5s;
        animation-delay: -0.5s;
    }
    
    .container3 .circle3 {
        -webkit-animation-delay: -0.4s;
        animation-delay: -0.4s;
    }
    
    .container1 .circle4 {
        -webkit-animation-delay: -0.3s;
        animation-delay: -0.3s;
    }
    
    .container2 .circle4 {
        -webkit-animation-delay: -0.2s;
        animation-delay: -0.2s;
    }
    
    .container3 .circle4 {
        -webkit-animation-delay: -0.1s;
        animation-delay: -0.1s;
    }
    
    @-webkit-keyframes bouncedelay {
        0%, 80%, 100% {
            -webkit-transform: scale(0.0)
        }
        40% {
            -webkit-transform: scale(1.0)
        }
    }
    
    @keyframes bouncedelay {
        0%, 80%, 100% {
            transform: scale(0.0);
            -webkit-transform: scale(0.0);
        }
        40% {
            transform: scale(1.0);
            -webkit-transform: scale(1.0);
        }
    }
    </style>

    
        <div class="spinner">
            <div class="spinner-container container1">
                <div class="circle1"></div>
                <div class="circle2"></div>
                <div class="circle3"></div>
                <div class="circle4"></div>
            </div>
            <div class="spinner-container container2">
                <div class="circle1"></div>
                <div class="circle2"></div>
                <div class="circle3"></div>
                <div class="circle4"></div>
            </div>
            <div class="spinner-container container3">
                <div class="circle1"></div>
                <div class="circle2"></div>
                <div class="circle3"></div>
                <div class="circle4"></div>
            </div>
        </div>
    

</div>

	
	
	
	
	<div id="container" class="container" style="display: none;">
		
			
		
		
		<c:if test="${fn:length(adGoodHeaderImgs)>0}">
			<div class="swiper-container swiper-container-horizontal">
		        <div class="swiper-wrapper" style="transition: 0ms; -webkit-transition: 0ms; -webkit-transform: translate3d(0px, 0px, 0px);">
		             <c:forEach items="${adGoodHeaderImgs}" var="adGoodHeaderImg">
			             <div class="swiper-slide swiper-slide-active" style="width: 98%;margin-left: 0px;margin-right: 0px;">
							<a href="javascript:skipToRedirectUrl('${adGoodHeaderImg.url}','${token.id}')"><img data-original="${adGoodHeaderImg.imgUrl}" src="/img/loading.gif" style="height:170px;width:100%;margin: 0px auto;" class="lazy"></a>
			             </div>
		             </c:forEach>
		            
		        </div>
		        <div class="swiper-pagination swiper-pagination-clickable"><span class="swiper-pagination-bullet swiper-pagination-bullet-active"></span><span class="swiper-pagination-bullet"></span><span class="swiper-pagination-bullet"></span><span class="swiper-pagination-bullet"></span></div>
			</div>
		</c:if>
		<c:if test="${fn:length(quickEntrances)>0}">
		    <div class="quick_entrance">
		        <ul>
		        	  <c:forEach items="${quickEntrances}" var="quickEntrance">
		            	<li><a href="javascript:skipToRedirectUrl('${quickEntrance.url}','${token.id}')"><img src="${quickEntrance.imgUrl}"><em></em><span>${quickEntrance.name}</span></a></li>                       
		        	  </c:forEach>
		        </ul>
		    </div>
  		</c:if>
        <div class="goods">
        	<c:forEach items="${goods}" var="good">
            <div class="good" >
            
           	 <div class="good_list_groupinfo" onclick="skipToGoodInfo('${good.id}')"<c:if test="${token!=null}">,'${token}'</c:if>)">
                	<div class="good_list_groupinfo_discount"><fmt:formatNumber type="number" value="${good.group_discount*10}" maxFractionDigits="2"/>折</div>
                	<div class="good_list_groupinfo_groupnum">${good.group_num}人团</div>
                </div>
                <div class="good-img" onclick="skipToGoodInfo('${good.id}')"<c:if test="${token!=null}">,'${token}'</c:if>)">

                        <img data-original="${good.head_img}" src="/img/loading.gif" class="lazy"/>
                        
      
                </div>
                
                <div class="good-title" onclick="skipToGoodInfo('${good.id}')"<c:if test="${token!=null}">,'${token}'</c:if>)">
                          ${good.title}
                    <c:if test="${good.notice!=null}">
                    	<br/><b style="color:black;">${good.notice}</b>
                    </c:if>
                   
                </div>
                
                <div class="tuan_g_core"  onclick="skipToGoodInfo('${good.id}')"<c:if test="${token!=null}">,'${token}'</c:if>)">
                	<img class="tuan_g_pin" src="/img/pin.png"></img>
                    <div class="tuan_g_price">
                    	${good.group_num}人团
                        <b>
                        		￥<fmt:formatNumber type="number" value="${good.group_original_cost*good.group_discount}" maxFractionDigits="3"/>
                        </b>
                    </div>
                    <div class="tuan_g_btn">
                        	去开团  >
                    </div>
                    
                </div>
                <div style="position: relative;padding-left:10px;top:-60px;" >
	                <div class="good_name">${good.name}</div>
	                <!--  <div class="good_market_price">市场价：￥${good.market_price}</div> -->
	                <div class="good_chat" onclick="javascript:skipToChat('${good.adminId}','1','${good.id}','','','${token.id}')">
                		<img src="http://script.suning.cn/project/pdsWeb/images/online.gif"/>
               	 </div>
                </div>
                
            </div>
            </c:forEach>
        </div>
    </div>
    <script type="text/javascript">
    		$(document).ready(function(){
    			$("img.lazy").lazyload({
                    effect: "fadeIn",
                    threshold : 200
            	});
            	$("img.lazy:eq(0)").attr('src',$("img.lazy:eq(0)").attr('data-original'));
    			
    			
    			
    			
    			$(function(){
    				window.onload=function(){
    				document.getElementById('loading').style.display='none';
    				document.getElementById('container').style.display='';
    				var swiper = new Swiper('.swiper-container', {
		    		        pagination: '.swiper-pagination',
		    		        paginationClickable: true,
		    		        speed:1000,
		    		        spaceBetween:1,
		    		        centeredSlides: true,
		    		        autoplay: 5000,
		    		        autoplayDisableOnInteraction: false
		    		    });
    				
    				
    			      $('.flexslider').flexslider({
    			        animation: "slide",
    			        start: function(slider){
    			        	
    			          $('body').removeClass('loading');
    			        }
    			      });
    			    };
    			
    			
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
    				wxOnMenuShareAppMessage("${typeName}","${typeTitle}","${domainName}/main/good_list?good_type=${goodType}","${typeImg}","link");
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
    		
    		
	});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>