<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="title">商品列表</tiles:putAttribute>
<tiles:putAttribute name="body">
	<div class="container">
        <div class="groups">
				<ul>
					<li >
						<a class="group activityStyle">
							<div class="group_header">
								<img src="http://img3.3lian.com/2014/c2/61/d/19.jpg"/>
								<div class="group_good_info">
									<div class="group_good_info_title"><b>你好哈哈哈哈哈哈哈哈哈哈哈哈哈哈</b></div>
									<div class="group_good_info_price">
										成团价：<b>16.9元2222222</b>
									</div>
								</div>
							</div>
							<div class="group_footer">
								<span class="group_result">团购失败</span>
								<span class="group_details">查看团详情</span>
								<span class="group_details">查看订单详情</span>
							</div>
						</a>
					</li>
					
					
					<li>
						<a class="group activityStyle">
							<div class="group_header">
								<img src="http://img3.3lian.com/2014/c2/61/d/19.jpg"/>
								<div class="group_good_info">
									<div class="group_good_info_title"><b>你好哈哈哈哈哈哈哈哈哈哈哈哈哈哈</b></div>
									<div class="group_good_info_price">
										成团价：<b>16.9元2222222</b>
									</div>
								</div>
							</div>
							<div class="group_footer">
								<span class="group_result">团购失败</span>
								<span class="group_details">查看团详情</span>
								<span class="group_details">查看订单详情</span>
							</div>

						</a>
					</li>
				</ul>
			</div>
    </div>
</tiles:putAttribute>
</tiles:insertDefinition>