<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
  <a href="/jersey/commodity/getAll">
  <button type="button" class="btn btn-info dropdown-toggle">
    商品
  </button>
  </a>
</div>

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

<a href="/jersey/MemberServlet?action=logout">登出</a>

</body>
</html>