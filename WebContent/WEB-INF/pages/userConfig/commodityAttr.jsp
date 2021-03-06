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
		
		//商品種類和權限對應的陣列
		var commodityTypeToAuthority = {};
		<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
			commodityTypeToAuthority['${commodityAttr.key.id}'] = '${commodityAttr.key.authority}';
		</c:forEach>
		
		function init () {
			//讓所有select恢復預設值
			$("select option:nth-child(1)").prop("selected", true);
			//隱藏確認修改的按鈕
			$("#updateCommodityType").add("button[name=updateCommodityAttr]").hide();
			//隱藏按下修改後才會顯示的東西
			$(".updateCommodityType").add(".updateCommodityAttr").hide();
			//顯示該顯示的
			$("#prepareUpdateCommodityType").add("button[name=prepareUpdateCommodityAttr]").show();
			$(".prepareUpdateCommodityType").add(".prepareUpdateCommodityAttr").show();
			//清空input
			$("input").val("");
			//因為filter變回全部, 所以秀出所有tr
			$("tr").show();
		}
		
		$(function(){
			init();
			//ajax結束後將按鈕恢復
			$(document).ajaxComplete(function(){
				var select = $("select[name=commodityTypeId]");
				var selected = select.find(":selected").val();
				init();
				//新增商品屬性的下拉式選單值不要變, 這樣比較直覺
				select.find("option[value="+ selected +"]").prop("selected", true);
			});
			
			//重新整理頁面後要跳出alert
			if(sessionStorage.isCreateCommodityType){
				alertify.success("新增商品種類成功");
				sessionStorage.removeItem("isCreateCommodityType");
			} else if (sessionStorage.isUpdateCommodityType) {
				alertify.success("商品種類修改成功");
				sessionStorage.removeItem("isUpdateCommodityType");
			} else if (sessionStorage.isDeleteCommodityType) {
				alertify.success("刪除商品種類成功");
				sessionStorage.removeItem("isDeleteCommodityType");
			} else if (sessionStorage.isCreateFirstCommodityAttr) {
				alertify.success("新增商品屬性成功");
				sessionStorage.removeItem("isCreateFirstCommodityAttr");
			}
			
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
// 							alertify.success("新增商品種類成功");
// 							var option = $("<option>").val(data.commodityTypeId).text(commodityAttrVO.commodityType);
// 							$("select[name=commodityTypeId]").add(".prepareUpdateCommodityType").append(option);
							//重新整理，這樣子上面的商品下拉式選單裡面商品才會長出來
							sessionStorage.isCreateCommodityType = true;
							location.reload();
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
							var tr = $("tr").has("td");
							if(tr.length==0){
								sessionStorage.isCreateFirstCommodityAttr = true;
								location.reload();
							}
							alertify.success("新增商品屬性成功");
							tr = tr.eq(0).clone();
							tr.removeClass().addClass(commodityAttrVO.commodityTypeId);
							tr.find("td:nth-child(1) button[name=removeCommodityAttr]").val(data.commodityAttrId);
							tr.find("td:nth-child(2)").text(data.commodityType);
							tr.find("td:nth-child(3) div").text(data.commodityAttr);
							tr.find("td:nth-child(3) input").val(data.commodityAttr);
							tr.find("td:nth-child(4) div").text(data.commodityAttrAuthorityShowName);
							tr.find("td:nth-child(4) option[value="+data.commodityAttrAuthority+"]").prop("selected", true);
							tr.find("td:nth-child(5) button[name=updateCommodityAttr]").val(data.commodityAttrId);
							var beforeTr = $("tr").has("td:contains("+ data.commodityType +")").filter(":last");
							if(beforeTr.length==0){
								$("table").append(tr);
							} else {
								beforeTr.after(tr);
							}
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
				if ($(".prepareUpdateCommodityType").val()=="all") {
					return;
				}
				$(this).add(".prepareUpdateCommodityType").hide();
				$("#updateCommodityType").add(".updateCommodityType").show();
				$("input.updateCommodityType").val($(".prepareUpdateCommodityType option:selected").text());
				$("select.updateCommodityType").val(commodityTypeToAuthority[$(".prepareUpdateCommodityType option:selected").val()]);
			});
			
			//商品種類修改
			$("#updateCommodityType").click(function(){
				var option = $(this).parents("div.row").find("option:selected");
				var commodityTypeId = option.val();
				var oldCommodityType = option.text();
				var pushData = [];
				pushData.push($(this).parents("div.row").find("input").val());
				pushData.push($(this).parents("div.row").find("select[name=authority]").val());
				$.ajax("/jersey/userConfig/commodityType/"+commodityTypeId, {
					type : "PUT",
					data : JSON.stringify(pushData),
					contentType : "application/json;charset=UTF-8",
					dataType : "json",
					success : function(data) {
						if (data.result=="success") {
// 							alertify.success("商品種類修改成功");
// 							$("select[name=commodityTypeId]").add(".prepareUpdateCommodityType").find("option[value="+commodityTypeId+"]").text(pushData[0]);
// 							$("td").filter(function(){
// 								return $(this).text() == oldCommodityType;
// 							}).text(pushData[0]);
							//重新整理，這樣子上面的商品下拉式選單裡面商品才會長出來
							sessionStorage.isUpdateCommodityType = true;
							location.reload();
						} else {
							alertify.error(data.msg);
						}
					},
					error : function(){
						alertify.error("商品種類修改失敗");
					}
				});
			});
			
			//商品屬性切換為修改模式
			$("table").on("click", "button[name=prepareUpdateCommodityAttr]", function(){
				var tr = $(this).parents("tr");
				$(this).add(tr.find(".prepareUpdateCommodityAttr")).hide();
				tr.find("button[name=updateCommodityAttr], .updateCommodityAttr").show();
				tr.find("input").val(tr.find("td:nth-child(3)>.prepareUpdateCommodityAttr").text());
				tr.find("option").filter(function(){
					return $(this).text() == tr.find("td:nth-child(4)>.prepareUpdateCommodityAttr").text();
				}).prop("selected", true);
			});
			
			//商品屬性修改
			$("table").on("click", "button[name=updateCommodityAttr]", function(){
				var commodityAttrId = $(this).val();
				var commodityAttr = [];
				var tr = $(this).parents("tr");
				commodityAttr.push(tr.find("input.updateCommodityAttr").val());
				commodityAttr.push(tr.find("select.updateCommodityAttr").val());
				$.ajax("/jersey/userConfig/commodityAttr/"+commodityAttrId, {
					type : "PUT",
					data : JSON.stringify(commodityAttr),
					contentType : "application/json;charset=UTF-8",
					dataType : "json",
					success : function(data) {
						if (data.result=="success") {
							alertify.success("商品屬性修改成功");
							tr.find("input.updateCommodityAttr").prev().text(commodityAttr[0]);
							tr.find("select.updateCommodityAttr").prev().text(data.commodityAttrAuthorityShowName);
						} else {
							alertify.error(data.msg);
						}
					},
					error : function(){
						alertify.error("商品屬性修改失敗");
					}
				});
			});
			
			//移除商品屬性
			$("table").on("click", "button[name=removeCommodityAttr]", function(){
				var commodityAttrId = $(this).val();
				var tr = $(this).parents("tr");
				alertify.confirm("警告:刪除商品屬性也會刪除已存在商品的對應屬性值, 請確認是否刪除", function(confirm){
					if (confirm) {
						$.ajax("/jersey/userConfig/commodityAttr/"+commodityAttrId, {
							type : "DELETE",
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if (data.result=="success") {
									alertify.success("刪除商品屬性成功");
									tr.remove();
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
			$("select.prepareUpdateCommodityType").change(function(){
				var commodityTypeId = $(this).val();
				$("tr").show();
				if(commodityTypeId!="all"){
					$("tr").not("."+commodityTypeId).hide();
					$("tr").eq(0).show();
					$("tr").eq(1).show();
				}
			});
			
			//移除商品種類
			$("#removeCommodityType").click(function(){
				if ($(".prepareUpdateCommodityType").val()=="all") {
					return;
				}
				var option = $(this).parents("tr").find("option:selected");
				var commodityTypeId = option.val();
				var commodtiyType = option.text();
				alertify.confirm("警告:刪除商品種類也會刪除所有此種類的商品和商品屬性, 請確認是否刪除", function(confirm){
					if (confirm) {
						$.ajax("/jersey/userConfig/commodityType/"+commodityTypeId, {
							type : "DELETE",
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if (data.result=="success") {
// 									alertify.success("刪除商品種類成功");
// 									$("select[name=commodityTypeId]").add(".prepareUpdateCommodityType").find("option[value="+commodityTypeId+"]").remove();
// 									$("td").filter(function(){
// 										return $(this).text() == commodtiyType;
// 									}).parent().remove();
									//重新整理，這樣子上面的商品下拉式選單裡面商品才會長出來
									sessionStorage.isDeleteCommodityType = true;
									location.reload();
								} else {
									alertify.error(data.msg);
								}
							},
							error : function(){
								alertify.error("刪除商品種類失敗");
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
				<c:forEach items="${applicationScope.authority}" var="authority">
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
					<option value="${commodityAttr.key.id}">${commodityAttr.key.commodityType}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<input class="form-control" type="text" name="commodityAttr">
		</div>
		<div class="col-sm-1">
			<select class="form-control" name="commodityAttrAuthority">
				<c:forEach items="${applicationScope.commodityAttrAuthority}" var="commodityAttrAuthority">
					<option value="${commodityAttrAuthority}">${commodityAttrAuthority.showName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-sm-1">
			<button id="createCommodityAttr" class="btn btn-warning">新增</button>
		</div>
	</div>
	<br />
	<br />
	
	<!-- 根據篩選器列出符合的商品屬性 -->
	<table border=1 class="table table-hover">
		<thead>
			<tr>
				<th colspan="5">
					<div class="row">
						<div class="col-sm-1">
							<select class="form-control prepareUpdateCommodityType">
								<option value="all">全部</option>
								<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
									<option value="${commodityAttr.key.id}">${commodityAttr.key.commodityType}</option>
								</c:forEach>
							</select>
							<input class="form-control updateCommodityType" type="text" name="commodityType">
						</div>
						<div class="col-sm-1">
							<select class="form-control updateCommodityType" name="authority">
								<c:forEach items="${applicationScope.authority}" var="authority">
									<option value="${authority}">${authority.showName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-1">
							<button id="prepareUpdateCommodityType" class="btn btn-warning">修改商品種類名稱</button>
						</div>
						<div class="col-sm-1">
							<button id="updateCommodityType" class="btn btn-success">確認修改</button>
						</div>
						<div class="col-sm-1">
							<button id="removeCommodityType" class="btn btn-danger">移除商品種類</button>
						</div>
					</div>
				</th>
			</tr>
			<tr>
				<th></th>
				<th>商品類別</th>
				<th>商品屬性</th>
				<th>屬性權限</th>
				<th></th>
			</tr>
		</thead>
		<c:forEach items="${requestScope.commodityAttrMap}" var="commodityAttr">
			<c:forEach items="${commodityAttr.value}" var="commodityAttrVO">
				<tr class="${commodityAttrVO.commodityTypeVO.id}">
					<td><button name="removeCommodityAttr" class="btn btn-danger" value="${commodityAttrVO.id}">刪除</button></td>
					<td>${commodityAttrVO.commodityTypeVO.commodityType}</td>
					<td>
						<div class="prepareUpdateCommodityAttr">${commodityAttrVO.commodityAttr}</div>
						<input class="form-control updateCommodityAttr" type="text" name="commodityAttr" value="${commodityAttrVO.commodityAttr}">
					</td>
					<td>
						<div class="prepareUpdateCommodityAttr">${commodityAttrVO.commodityAttrAuthority.showName}</div>
						<select class="form-control updateCommodityAttr">
						<c:forEach items="${applicationScope.commodityAttrAuthority}" var="commodityAttrAuthority">
							<option value="${commodityAttrAuthority}" ${commodityAttrVO.commodityAttrAuthority==commodityAttrAuthority?"selected":""}>${commodityAttrAuthority.showName}</option>
						</c:forEach>
						</select>
					</td>
					<td>
						<button name="prepareUpdateCommodityAttr" class="btn btn-warning">修改商品屬性</button>
						<button name="updateCommodityAttr" class="btn btn-success" value="${commodityAttrVO.id}">確認修改</button>
					</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>

</body>
</html>