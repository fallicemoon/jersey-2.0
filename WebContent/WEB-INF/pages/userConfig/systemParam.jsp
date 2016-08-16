<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>系統參數</title>
</head>
<body>
	<c:import url="/WEB-INF/pages/header.jsp" />
	<script type="text/javascript">
		$(function() {
			$("#pageSize").on("click", "button", function() {
				var img = $("<img>").attr("src", "../pic/circle_load.gif");
				var self = $(this);
				console.log(img);
				console.log(self);
				self.prop("disabled", true).text(" 更新中").prepend(img);
				var value = [];
				value.push(self.parent().prev().children().val());
				$.ajax("/jersey/userConfig/systemParam/pageSize/"+self.parent().prev().children().attr("name"), {
					type : "PUT",
					data : JSON.stringify(value),
					contentType : "application/json",
					dataType : "json",
					success : function(data) {
						if (data.result == "success") {
							alertify.success("更新成功");
							self.parent().prev().prev().text(value[0]);
							self.prop("disabled", false).text("更新");
						} else {
							alertify.error(data.msg);
							self.prop("disabled", false).text("更新");
						}
					},
					error : function() {
						alertify.error("更新失敗");
						self.prop("disabled", false).text("更新");
					}
				});
			});

		});
	</script>
	<br /><br />
	<div id="pageSize">
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">商品分頁筆數(1~100)：</label>
		<div class="col-sm-1">
			${requestScope.commodityPageSize}
		</div>
		<div class="col-sm-1">
			<input type="number" max="100" min="0" name="commodityPageSize" value="${requestScope.commodityPageSize}">
		</div>
		<div class="col-sm-1">
			<button type="button" class="btn btn-warning">更新</button>
		</div>
	</div>
	
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">進貨分頁筆數(1~100)：</label>
		<div class="col-sm-1">
			${requestScope.purchaseCasePageSize}
		</div>
		<div class="col-sm-1">
			<input type="number" max="100" min="0" name="purchaseCasePageSize" value="${requestScope.purchaseCasePageSize}">
		</div>
		<div class="col-sm-1">
			<button type="button" class="btn btn-warning">更新</button>
		</div>
	</div>
	
		<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">出貨分頁筆數(1~100)：</label>
		<div class="col-sm-1">
			${requestScope.sellCasePageSize}
		</div>
		<div class="col-sm-1">
			<input type="number" max="100" min="0" name="sellCasePageSize" value="${requestScope.sellCasePageSize}">
		</div>
		<div class="col-sm-1">
			<button type="button" class="btn btn-warning">更新</button>
		</div>
	</div>
	
	<div class="row">
		<label for="inputEmail3" class="col-sm-1 control-label">商店分頁筆數(1~100)：</label>
		<div class="col-sm-1">
			${requestScope.storePageSize}
		</div>
		<div class="col-sm-1">
			<input type="number" max="100" min="0" name="storePageSize" value="${requestScope.storePageSize}">
		</div>
		<div class="col-sm-1">
			<button type="button" class="btn btn-warning">更新</button>
		</div>
	</div>
	</div>

	<c:import url="/WEB-INF/pages/footer.jsp"></c:import>
</body>
</html>