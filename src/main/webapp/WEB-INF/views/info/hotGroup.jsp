<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!doctype html>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="body">
	<div class="container">
			<section>	
	<!-- header start -->
	<header class="top-fixed header">
		<a href="http://m.ipinbb.com/" class="fn-left iconfont back-home"></a><h1 class="top-h1">正在热拼</h1>
	</header>
	<!-- / header end -->

	<!-- article start -->
	<article class="pad-top44 ui-refresh">
		
		<!-- list start -->
		<section class="hot-group">
				<c:forEach items="${groups}" var="group">
				<div class="hot-group-item" data-href="/ipbb/goods/group?groupId=10805" onclick="javascript:skipToGroupInfo('${group.group_id}');">
							<div class="hot-group-top">
								<span class="fn-left hot-group-img" style="background: none;"> 
									<img class="imglazyload" src="${group.head_img}">
								</span>
	
								<div class="hot-group-content">
									<h3>${group.name}</h3>
									<p class="text-color font14 margin-btm12">${group.group_num}人团 ¥${group.total_price}</p>
									<p class="font12">
										<span class="fn-right text-color1">剩余时间 <em class="ct-time conduct" data-end="4956000"><b id="group_info_hour">01</b> : <b id = "group_info_min">22</b> : <b id = "group_info_second">17</b></em></span> <span>已拼 <i class="text-color">${group.partake_num}</i>人
										</span>
									</p>
								</div>
								<script type="text/javascript">
		     						initGroupInvalidDate("${group.startTime}","${group.timeLong}");
		     					</script>
							</div>
							
							<div class="hot-group-btm">
									<a  class="fn-right hot-group-btn">参与</a>
									<div class="fn-clear">
									<c:forEach items="${group.members}" var = "member">
										<span class="fn-left hot-group-user" style="background-image: url(${member.headImg});"></span>
									</c:forEach>
									</div>
								</div>
							</div>
						</c:forEach>
					</section>
	</article>
</section>
     	</div>
     	<script type="text/javascript">
     		$(document).ready(function(){
	     			wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
	    			wx.ready(function(){
	    				wxOnMenuShareAppMessage("${title}","${instruction}","${domainName}/info/hot_group","${img}","link");
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
    			
   		 </script>
</tiles:putAttribute>
</tiles:insertDefinition>
