<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>系統參數設定</title>
</head>
<body>
	<c:import url="/WEB-INF/pages/header.jsp" />
	<br/><br/>
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">新增商品種類:</label>
		<div class="col-sm-1">
			<input type="text" name="commodityType">
		</div>
		<div class="col-sm-1">
			<input type="text" name="commodityAttribute">
		</div>
		<div class="col-sm-1">
			<button class="btn btn-success">新增</button>
		</div>
	</div>
	<br />
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">新增商品屬性:</label>
		<div class="col-sm-1">
			<select class="form-control">
				<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
					<option value="${commodityAttr.key}">${commodityAttr.key}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<input type="text" name="commodityAttribute">
		</div>
		<div class="col-sm-1">
			<button class="btn btn-warning">新增</button>
		</div>
	</div>
	<br />
	<table border=1 class="table table-hover">
		<thead>
			<tr>
				<th></th>
				<th></th>
				<th>商品類別</th>
				<th>商品屬性</th>
				<th>屬性權限</th>
		</thead>
		<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
			<c:forEach items="${commodityAttr.value}" var="commodityAttrVO">
				<tr>
					<td><button class="btn btn-warning">修改</button></td>
					<td><button class="btn btn-danger">刪除</button></td>
					<td>${commodityAttrVO.commodityType}</td>
					<td>${commodityAttrVO.commodityAttr}</td>
					<td>${commodityAttrVO.commodityAttrAuthority}</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>

</body>
</html>