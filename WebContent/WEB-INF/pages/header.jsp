<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/jersey/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/jersey/lib/jquery-alertify/alertify.bootstrap.css" rel="stylesheet">
<link href="/jersey/lib/jquery-alertify/alertify.core.css" rel="stylesheet">
<link href="/jersey/lib/jquery-alertify/alertify.default.css" rel="stylesheet">

<script src="/jersey/lib/jquery-2.1.3.min.js"></script>
<script src="/jersey/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="/jersey/lib/jquery-alertify/alertify.min.js"></script>

</head>
<body>

<div class="btn-group">
<div class="btn-group">
 <div class="dropdown">
  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">商品
  <span class="caret"></span></button>
  <ul class="dropdown-menu">
  	<c:forEach items="${sessionScope['scopedTarget.userSession'].commodityTypeAttrMap}" var="commodityTypeAttr">
  		<li>
  			<a href="/jersey/commodity/${commodityTypeAttr.key.id}/getAll" ${commodityTypeAttr.key.authority=='ADMIN' ? 'style="color:red;"':'' }>${commodityTypeAttr.key.commodityType}</a>
  		</li>
  	</c:forEach>
  </ul>
</div>
</div>
</div>

<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
<div class="btn-group">
  <a href="/jersey/purchaseCase/getAll">
  <button type="button" class="btn btn-primary dropdown-toggle">
    進貨 
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/sellCase/getAll">
  <button type="button" class="btn btn-success dropdown-toggle">
    出貨
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/store/getAll">
  <button type="button" class="btn btn-danger dropdown-toggle">
    商家
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/accounting/datePicker">
  <button type="button" class="btn btn-warning dropdown-toggle">
    會計
  </button>
  </a>
</div>

<div class="btn-group">
 <div class="dropdown">
  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">設定
  <span class="caret"></span></button>
  <ul class="dropdown-menu">
    <li><a href="/jersey/userConfig/commodityAttr">商品種類與商品屬性</a></li>
    <li><a href="/jersey/userConfig/systemParam">系統參數</a></li>
  </ul>
</div>
</div>

<a href="/jersey/logout">登出</a>
</c:if>

</body>
</html>