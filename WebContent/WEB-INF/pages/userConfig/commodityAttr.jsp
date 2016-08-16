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
	<script type="text/javascript">
		$(function(){
			<%--新增商品種類--%>
			$("#createCommodityType").click(function(){
				var row = $(this).parents(".row");
				var commodityAttrVO = {
						commodityType : row.find("input[name=commodityType]").val(), 
						commodityAttr : row.find("input[name=commodityAttr]").val(), 
						commodityAttrAuthority: row.find("select").val()
					};
				$.ajax("/jersey/userConfig/commodityAttr/", {
					type : "POST",
					data : JSON.stringify(commodityAttrVO),
					contentType : "application/json",
					dataType : "json",
					success : function(data) {
						if (data.result=="success") {
							alertify.success("新增商品種類成功");
						} else {
							alertify.error(data.msg);
						}
					},
					error : function(){
						alertify.error("新增商品種類失敗");
					}
				});
			});
			
			<%--新增商品屬性--%>
			$("#createCommodityAttr").click(function(){
				var row = $(this).parents(".row");
				var commodityAttrVO = {
						commodityType : row.find("input[name=commodityType]").val(), 
						commodityAttr : row.find("input[name=commodityAttr]").val(), 
						commodityAttrAuthority: row.find("select").val()
					};
				$.ajax("/jersey/userConfig/commodityAttr/"+commodityAttrVO.commodityType, {
					type : "POST",
					data : JSON.stringify(commodityAttrVO),
					contentType : "application/json",
					dataType : "json",
					success : function(data) {
						if (data.result=="success") {
							alertify.success("新增商品屬性成功");
						} else {
							alertify.error(data.msg);
						}
					},
					error : function(){
						alertify.error("新增商品屬性失敗");
					}
				});
			});
			
			//
			
		});
	</script>
	<br/><br/>
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">新增商品種類:</label>
		<div class="col-sm-1">
			<input type="text" name="commodityType">
		</div>
		<div class="col-sm-1">
			<input type="text" name="commodityAtt">
		</div>
		<div class="col-sm-1">
			<select>
				<c:forEach items="${commodityAttrAuthorityList}" var="commodityAttrAuthority">
					<option value="${commodityAttrAuthority}">${commodityAttrAuthority.showName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<button id="createCommodityType" class="btn btn-success">新增</button>
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
			<input type="text" name="commodityAttr">
		</div>
		<div class="col-sm-1">
			<select>
				<c:forEach items="${commodityAttrAuthorityList}" var="commodityAttrAuthority">
					<option value="${commodityAttrAuthority}">${commodityAttrAuthority.showName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<button id="createCommodityAttr" class="btn btn-warning">新增</button>
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
					<td><select>
						<c:forEach items="${requestScope.commodityAttrAuthorityList}" var="commodityAttrAuthority">
							<option value="${commodityAttrAuthority}" ${commodityAttrVO.commodityAttrAuthority==commodityAttrAuthority?"selected":""}>${commodityAttrAuthority.showName}</option>
						</c:forEach>
					</select></td>
					<td>${commodityAttrVO.commodityAttrAuthority}</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>

</body>
</html>