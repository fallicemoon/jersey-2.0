<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/jersey/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="/jersey/lib/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/jersey/lib/bootstrap/js/bootstrap.min.js"></script>
<title>登入</title>
</head>
<body>

<div style="width:500px; height: 500px; position: absolute; left:50%; top: 50%; margin-top: -200px; margin-left: -250px">
	<form class="form-horizontal" action="/jersey/secretLogin" method="POST">
	  <div class="form-group">
    	<label for="inputEmail3" class="col-sm-2 control-label">帳號：</label>
    	<div class="col-sm-10">
    	<input type="text" name="userName">
    	</div>
      </div>
	  
	  <div class="form-group">
    	<label for="inputEmail3" class="col-sm-2 control-label">密碼：</label>
    	<div class="col-sm-10">
    	<input type="password" name="password" autocomplete="off">
    	</div>
      </div>
	  
<!-- 	  <div class="form-group"> -->
<!-- 	    <div class="col-sm-offset-2 col-sm-10"> -->
<!-- 	      <div class="checkbox"> -->
<!-- 	        <label> -->
<!-- 	          <input type="checkbox"> Remember me -->
<!-- 	        </label> -->
<!-- 	      </div> -->
<!-- 	    </div> -->
<!-- 	  </div> -->
	  
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button type="submit" class="btn btn-default" name="action" value="login">登入</button>
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
			<p style="color: red">${requestScope.errorMessage}</p>
	    </div>
	  </div>
	  

</form>
</div>
	

</body>
</html>