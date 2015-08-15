<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<footer class="footer">
		<nav>
			<ul>
				<li><a href="javascript:skipToGoodList()" class="nav-controller active" id="foot_good_list"><i
						class="fa fa-home"></i>首页</a></li>
				<li><a href="javascript:skipToGroupList()" class="nav-controller" id="foot_group_list"><i
						class="fa fa-group"></i>我的团</a></li>
				<li><a href="javascript:skipToOrderList()" class="nav-controller" id="foot_order_list"><i
						class="fa fa-list"></i>我的订单</a></li>
				<li><a href="javascript:skipToPersonCenter()" class="nav-controller" id="foot_personal_center_list"><i
						class="fa fa-user"></i>个人中心</a></li>
			</ul>
		</nav>
</footer>