<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!doctype html>
<html>
    
    <head>
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <meta name="format-detection" content="telephone=no">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Expires" content="0">
        <meta name="description" content="" />
        <link rel="stylesheet" href="/css/address.css" />
        <link rel="stylesheet" href="/css/mystyle.css" />
        <link rel="stylesheet" href="/css/core.css" />
        <script type="text/javascript" src="/js/myscript.js"></script>
        <script type="text/javascript" src="/js/jquery-2.1.4.min.js"></script>
        <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js">
        </script>
    </head>
    
    <body>
        <div class="container">
        <div class="address">
            <div class="address_head">
              	 收货地址
            </div>
            
	                <div class="address_item">
	                    <div class="address_label">收货人</div>
	                    <input name="" type="text" value="" id="address_name"/>
	                </div>
                    <div class="address_item">
                    	<div class="address_label">手机号码</div>
                    	<input name="" type="text" value="" class="address_phone_text"  id="address_phonenumber"/></div>
                    <div class="address_item">
                    	<div class="address_label">省</div>
                        <select name="省" size="1" id="address_province">
                        		<c:forEach items="${cities}" var="province">
                        			<option value="${province.id}">${province.name}</option>
                        		</c:forEach>
                        </select>
                    </div>
                    <div class="address_item">
                    	<div class="address_label">市</div>
                        <select name="市" size="1" id="address_city">
                        
                        </select>
                    </div>
                    <div class="address_item">
                       	 <div class="address_label">区/县</div>
                        <select name="区/县" size="1" id="address_address">
                            
                        </select>
                    </div>
                    <div class="address_item">
                    	<div class="address_label">地址类别</div>
                        <select name="地址类别" size="1" id="address_type">
                            <option value="1" selected>家</option>
                            <option value="2">公司</option>
                        </select>
                    </div>
                    <div class="address_item">
                    	<div class="address_label">详细地址</div>
                        <input name="详细地址" type="text" id="address_content"/>
                    </div>
                    <div class="address_submit" onclick="addressAddSbumit('${prepareRedirect}','${token.id}','${id}')">确认</div>
                </div>
            </div>
               <script type="text/javascript">
	               $(document).ready(function(){
	            	   onProvinceSelect();
	            	   initData('${id}','${name}','${phonenumber}','${type}','${province}','${city}','${address}','${content}');
	            	   onCitySelect();
	            	   getCities();
	            	   setUserToken("${token.id}");
	            	   wxConfig("${appId}","${signature}","${noncestr}","${datetime}");
	            	   wx.ready(function(){
	       				wxOnMenuShareAppMessage("${typeName}","${typeTitle}",webPath+"/main/good_list?good_type=${goodType}","${typeImg}","link");
	       			});
	               });
    			</script>
    </body>

</html>