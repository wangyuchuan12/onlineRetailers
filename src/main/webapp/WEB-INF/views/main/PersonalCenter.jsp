<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div class="container">
        <div class="personal_center">
        	<div class="personal_center_header">
        		<div class="personal_center_header_img">
        			<img src="/img/good.jpg"/>
        		</div>
        		<div class="personal_center_userinfo">
        			<div class="personal_center_userinfo_name">川川</div>
        			<div class="personal_center_userinfo_integral">积分：50</div>
        		</div>
        	</div>
        	<div class="personal_center_entry">
        		<ul>
        			<li>
        				<div class="personal_center_item activityStyle">
        					<div class="personal_center_item_icon" style="background-color:blue;padding-left:3px;padding-top:5px;">
        						<i class="fa fa-money"></i>
        					</div>
        					<div class="personal_center_item_content">我的开团劵</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        			
        			<li>
        				<div class="personal_center_item activityStyle">
        					<div class="personal_center_item_icon" style="background-color:red;padding-top:5px;padding-left:3px;">
        						<i class="fa fa-shopping-cart"></i>
        					</div>
        					<div class="personal_center_item_content" onclick="skipToAddress();">我的地址</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        			
        			
        			<li>
        				<div class="personal_center_item activityStyle">
        					<div class="personal_center_item_icon" style="background-color:green;padding-top:3px;padding-left:5px;">
        						<i class="fa fa-question-circle"></i>
        					</div>
        					<div class="personal_center_item_content">帮助</div>
        					<div class="personal_center_item_footer">&gt</div>
        				</div>
        			</li>
        		</ul>
        	</div>
        </div>
    </div>
    <script type="text/javascript">
    		$(document).ready(function(){
    			footActive("foot_personal_center_list");
    			setUserToken("${token.id}");
    		});
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>