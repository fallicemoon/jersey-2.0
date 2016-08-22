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
			//隱藏確認修改的按鈕
			$("#updateCommodityType").add("button[name=updateCommodityAttr]").hide();
			//隱藏按下修改後才會顯示的東西
			$("#commodityTypeSelect").add
			
			<%--新增商品種類--%>
			$("#createCommodityType").click(function(){
				var row = $(this).parents(".row");
				var commodityAttrVO = {
						commodityType : row.find("input[name=commodityType]").val(), 
						authority: row.find("select[name=authority]").val()
					};
				$.ajax("/jersey/userConfig/commodityType", {
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
						commodityTypeId : row.find("select[name=commodityTypeId]").val(), 
						commodityAttr : row.find("input[name=commodityAttr]").val(), 
						commodityAttrAuthority: row.find("select[name=commodityAttrAuthority]").val()
					};
				$.ajax("/jersey/userConfig/commodityAttr/"+commodityAttrVO.commodityTypeId, {
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
			
			//商品種類切換為修改模式
			$("#prepareUpdateCommodityType").click(function(){
				$(this).
			});
			
			//商品屬性切換為修改模式
			$("button[name=prepareUpdateCommodityAttr]").click(function(){
				
			});
			
			//移除商品屬性
			$("button[name=removeCommodityAttr]").click(function(){
				alertify.confirm("警告:刪除商品屬性也會刪除已存在商品的對應屬性值, 請確認是否刪除", function(confirm){
					if (confirm) {
						$.ajax("/jersey/userConfig/commodityAttr/"+$(this).val(), {
							type : "DELETE",
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if (data.result=="success") {
									alertify.success("刪除商品屬性成功");
								} else {
									alertify.error(data.msg);
								}
							},
							error : function(){
								alertify.error("刪除商品屬性失敗");
							}
						});
					}
				});
			});
			
			//依照商品種類篩選出商品屬性
			$("#commodityTypeSelect").chnage(function(){
				var commodityTypeId = $(this).val();
				$("tr").show();
				$("tr").not("."+commodityTypeId).hide();
			});
			
			//移除商品種類
			$("#removeCommodityType").click(function(){
				alertify.confirm("警告:刪除商品種類也會刪除所有此種類的商品和商品屬性, 請確認是否刪除", function(confirm){
					if (confirm) {
						var commodityTypeId = $(this).parent().prev().children().val();
						$.ajax("/jersey/userConfig/commodityType/"+commodityTypeId, {
							type : "DELETE",
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if (data.result=="success") {
									alertify.success("刪除商品屬性成功");
								} else {
									alertify.error(data.msg);
								}
							},
							error : function(){
								alertify.error("刪除商品屬性失敗");
							}
						});
					}
				});
			});

			
			
			
		});
	</script>
	<br/><br/>
	<!-- 新增商品種類 -->
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">新增商品種類:</label>
		<div class="col-sm-1">
			<input class="form-control" type="text" name="commodityType">
		</div>
		<div class="col-sm-1">
			<select class="form-control" name="authority">
				<c:forEach items="${authorityList}" var="authority">
					<option value="${authority}">${authority.showName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<button id="createCommodityType" class="btn btn-success">新增</button>
		</div>
	</div>
	<br />
	
	<!-- 新增商品屬性 -->
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">新增商品屬性:</label>
		<div class="col-sm-1">
			<select class="form-control" name="commodityTypeId">
				<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
					<option value="${commodityAttr.key.commodityTypeId}">${commodityAttr.key.commodityType}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<input class="form-control" type="text" name="commodityAttr">
		</div>
		<div class="col-sm-1">
			<select class="form-control" name="commodityAttrAuthority">
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
	
	<!-- 篩選商品種類、修改商品種類、移除商品種類 -->
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">商品種類:</label>
		<div class="col-sm-1">
			<div id="commodityTypeText">${commodityAttr.key.commodityType}</div>
			<select class="form-control" id="commodityTypeSelect">
				<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
					<option value="${commodityAttr.key.commodityTypeId}">${commodityAttr.key.commodityType}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<button id="prepareUpdateCommodityType" class="btn btn-warning">修改商品種類</button>
			<button id="updateCommodityType" class="btn btn-success">確認修改</button>
		</div>
		<div class="col-sm-1">
			<button id="removeCommodityType" class="btn btn-danger">移除商品種類</button>
		</div>
	</div>
	<br />
	
	<!-- 根據上面的篩選器列出符合的商品屬性 -->
	<table border=1 class="table table-hover">
		<thead>
			<tr>
				<th></th>
				<th>商品類別</th>
				<th>商品屬性</th>
				<th>屬性權限</th>
				<th></th>
		</thead>
		<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
			<c:forEach items="${commodityAttr.value}" var="commodityAttrVO">
				<tr class="${commodityAttrVO.commodityTypeVO.commodityTypeId}">
					<td><button name="removeCommodityAttr" class="btn btn-danger" value="${commodityAttrVO.commodityAttrId}">刪除</button></td>
					<td>${commodityAttrVO.commodityTypeVO.commodityType}</td>
					<td class="commodityAttrText">${commodityAttrVO.commodityAttr}</td>
					<td class="commodityAttrText">${commodityAttrVO.commodityAttr}</td>
					<td><select class="form-control">
						<c:forEach items="${requestScope.commodityAttrAuthorityList}" var="commodityAttrAuthority">
							<option value="${commodityAttrAuthority}" ${commodityAttrVO.commodityAttrAuthority==commodityAttrAuthority?"selected":""}>${commodityAttrAuthority.showName}</option>
						</c:forEach>
					</select></td>
					<td>${commodityAttrVO.commodityAttrAuthority}</td>
					<td>
						<button name="prepareUpdateCommodityAttr" class="btn btn-warning">修改商品屬性</button>
						<button name="updateCommodityAttr" class="btn btn-success">確認修改</button>
					</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>

</body>
</html>