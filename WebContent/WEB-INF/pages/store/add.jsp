<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>新增商家</title>
</head>
<body>
<c:import url="/WEB-INF/pages/header.jsp"/>

	<br/><br/>
	<form action="/jersey/store/" method="post" class="form-horizontal">
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家類型：</label>
    	<div class="col-sm-10">
    	<select name="type" class="form-control" style="width: 14%">
    		<option value="STORE" selected="selected">商店</option>
    		<option value="SHIPPING_COMPANY">託運公司</option>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家名稱：</label>
    	<div class="col-sm-10">
    	<input type="text" name="name">
    	</div>
    </div>

	<input type="hidden" name="action" value="create">
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-success" >新增</button>
		</label>
	</div>
	</form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>
</body>
</html>