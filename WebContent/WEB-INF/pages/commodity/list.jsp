<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
.checkboxDiv {
	position: absolute;
	display: none;
	background-color: #00A800;
	border: solid;
	min-width: 90px;
}

</style>
<title>商品</title>
</head>
<body>
		<c:import url="/WEB-INF/pages/header.jsp" />
		<script type="text/javascript">

// 	var commodityAttr = ["itemName", "player", "team", "style", "brand",
// 			"size", "level", "condition", "tag", "owner", "authority",
// 			"isStored" ];

// 	function filter() {
// 		$("tr").show();
// 		for ( var index in commodityAttr) {
// 			var filterName = commodityAttr[index];
			<%-- 從篩選條件中取得要保留的資料 --%>
// 			var keep = $("input:checked[name='"+filterName+"']").map(function(){
// 				return $(this).val();
// 			}).toArray();
			<%-- 只要有任何條件不符合就隱藏資料 --%>
// 			$("."+filterName).not(".checkboxDiv").each(function(){
// 				if($.inArray($(this).text(), keep)==-1){
// 					$(this).closest("tr").hide();
// 				}
// 			});
// 		}
// 	}

	$(function(){
		<%--把checkbox清空--%>
		$("input[name=commodityIds]:checked").prop("checked", false);
		
		<%--生成篩選條件的下拉式選單內容--%>
		var commodity = ["itemName", "player", "team", "style", "brand",
		          			"size", "condition", "owner", "authority"];
		$.each(commodity, function(){
			var name = this;
			var inputValue = [];
			$("."+name).not(".checkboxDiv").each(function(){
				if($.inArray($(this).text(), inputValue)==-1){
					inputValue.push($(this).text());
					var input = $("<input>").attr("type", "checkbox").attr("name", name)
						.attr("value", $(this).text()).prop("checked", true);
					$("."+name+".checkboxDiv").append(input).append($(this).text()+"<br>");
				}
			});
		});
		
		<%--顯示下拉式篩選條件的按鈕們--%>
		$(".checkboxDiv").prev().click(function(){
			$(this).next().slideToggle("fast").css("padding-right","10px");
		});
		
		<%--全選按鈕, 弄掉的話為全不選 --%>
		$("input[name=all]").change(function(){
			var checked = $(this).prop("checked");
			$(this).siblings().each(function(){
				$(this).prop("checked", checked);
			});
		});
		
		<%--篩選條件發生變化時進行篩選--%>
		$("table tr:first").on("change", "[name!=commodityIds]", function(){
			filter();
		});
// 		$("input[name!=commodityIds]").change(function(){
// 			filter();
// 		});
		
		<%--新增--%>
		$("#create").click(function(){
			location.href = "/jersey/commodity/${requestScope.commodityTypeId}";
		});
		
		<%--修改--%>
		$("table").on("click", "button[name=update]", function(){
			//first是圖片張數, 有button的是上架按鈕
			var button = $(this);
			var updateTd = button.parent().nextAll().not(":first, :has(button)");
			var updateTdDiv = updateTd.children("div");
			var updateTdInput = updateTd.children("input");
			var updateTdSelect = updateTd.children("select");
			if (button.hasClass("btn-warning")) {
				updateTdDiv.hide();
				updateTdSelect.show();
				updateTdInput.attr("type", "text");
				updateTdInput.filter("[name=cost]").attr("type", "number");
				updateTdInput.filter("[name=sellPrice]").attr("type", "number");
				button.removeClass("btn-warning").addClass("btn-success").text("確認修改");
			} else if(button.hasClass("btn-success")){
				var body = {};
				var commodityAttr = {};
				//商品自定屬性
				$.each(updateTdInput.filter("[class=commodityAttrMapping]").serializeArray(), function(){
					commodityAttr[this.name] = this.value;
				});
				//商品資料
				$.each(updateTdInput.filter("[class!=commodityAttrMapping]").serializeArray(), function(){
					body[this.name] = this.value;
				});
				//select不知道為什麼不能serialize, 懶的研究
				body["isStored"] = updateTdSelect.eq(0).val();
				body["commodityAttr"] = commodityAttr;
				delete body["commodityIds"];
				
				var commodityId = button.closest("tr").find("input[name=commodityIds]").val();
				$.ajax("/jersey/commodity/"+commodityId, {
					type : "PUT",
					data : JSON.stringify(body),
					contentType : "application/json;charset=UTF-8",
					dataType : "json",
					success : function(data) {
						if (data.result=="success") {
							updateTdDiv.each(function(){
								$(this).text($(this).next().val());
							});
							updateTdDiv.show();
							updateTdInput.attr("type", "hidden");
							updateTdSelect.not("input").hide();
							button.removeClass("btn-success").addClass("btn-warning").text("修改");		
							alertify.success("修改商品屬性成功");
						} else {
							updateTdDiv.show();
							updateTdInput.attr("type", "hidden");
							updateTdSelect.not("input").hide();
							button.removeClass("btn-success").addClass("btn-warning").text("修改");		
							alertify.error(data.msg);
						}
					},
					error : function(){
						updateTdDiv.show();
						updateTdInput.attr("type", "hidden");
						updateTdSelect.not("input").hide();
						button.removeClass("btn-success").addClass("btn-warning").text("修改");		
						alertify.error("修改商品屬性失敗");
					}
				});
		
			}
		});
// 		$("table").on("click", "button[name=update]", function(){
// 			location.href = "/jersey/commodity/${requestScope.commodityTypeId}/"+$(this).val();
// 		});

		<%--商品上下架--%>
		$(".commodityAuthority").click(function(){
			var button = $(this);
			var isUp;
			var body = {};
			var img = $("<img>").attr("src", "/jersey/pic/circle_load.gif");
			if(button.hasClass("btn-warning")){
				//未上架, 開始上架
				button.prop("disabled", true).text(" 上架中").prepend(img);
				body["commodityAuthority"] = "customer";
				isUp = true;
			}else if(button.hasClass("btn-success")){
				//已上架, 開始下架
				button.prop("disabled", true).text(" 下架中").prepend(img);
				body["commodityAuthority"] = "admin";
				isUp = false;
			}
			
			var commodityId = button.closest("tr").find("input[name=commodityIds]").val();
			$.ajax("/jersey/commodity/switchStatus/"+commodityId, {
				type : "PUT",
				data : JSON.stringify(body),
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					if (data.result=="success") {
						if (isUp) {
							button.prop("disabled", false)
								.removeClass("btn-warning")
								.addClass("btn-success")
								.text("已上架");
						} else {
							button.prop("disabled", false)
								.removeClass("btn-success")
								.addClass("btn-warning")
								.text("未上架");
						}
					} else {
						if (isUp) {
							button.prop("disabled", false).text("未上架");
						} else {
							button.prop("disabled", false).text("已上架");
						}
						alertify.error(data.msg);
					}
				},
				error : function(){
					if (isUp) {
						button.prop("disabled", false).text("未上架");
					} else {
						button.prop("disabled", false).text("已上架");
					}
					alertify.error("商品上下架切換失敗");
				}
			});
		});
		
		<%--刪除--%>
		$("#delete").click(function() {
			alertify.confirm("確定要刪除?", function(confirm){
				if(confirm){
					var checked = $("input[name=commodityIds]:checked");
					if (checked.size==0) {
						return;
					}
					var commodityIds = checked.map(function(){
						return $(this).val();
					}).get();

					$.ajax("/jersey/commodity", {
						type : "PUT",
						data : JSON.stringify(commodityIds),
						contentType : "application/json",
						dataType : "json",
						success : function(data) {
							if (data.result=="success") {
								alertify.success("刪除成功");
								checked.parent().parent().remove();
							} else {
								alertify.error(data.msg);
							}
						},
						error : function(){
							alertify.error("刪除失敗");
						}
					});
				}
			});
		});
		
		<%--複製(ㄧ次只能複製一筆)--%>
		$("#clone").click(function(){
			var checked = $("input[name=commodityIds]:checked");
			if (checked.size()==0) {
				return;
			} else if (checked.size()!=1) {
				alertify.error("幹你媽的只能勾一筆啦");
				return;
			}
			$.ajax("/jersey/commodity/clone", {
				type : "POST",
				data : checked.eq(0),
				success : function(){
					alertify.success("複製成功");
					location.reload();
				},
				error : function(){
					alertify.error("複製失敗");
				}
			});
		});
		
		<%--標示出分頁標籤的當前分頁--%>
		var pagePos = location.search.indexOf("page=")+"page=".length;
		var page = location.search.substr(pagePos);
		if(location.search==""){
			$(".page-item").eq(0).addClass("active");
		}
		$(".page-item").each(function(){
			if($(this).val()==page) {
				$(this).addClass("active");
			}
		});

	});
		</script>
		<span style="display: inline-block; width: 100px"></span>
		<button id="create" type="button" class="btn btn-success" data-toggle="modal">新增</button>
		<button id="delete" type="button" class="btn btn-danger" data-toggle="modal">刪除</button>
		<button id="clone" type="button" class="btn btn-warning" data-toggle="modal">複製</button>
		
		<table border=1 width="1500px" class="table table-hover">
			<thead>
				<tr>
						<th></th>
						<th></th>
						<th>圖片</th>
						<th>
							<button type="button" class="btn btn-warning" data-toggle="modal">商品名稱</button>
							<div class="itemName checkboxDiv">
								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/>
<%-- 								<c:forEach items="${requestScope.itemNames}" var="itemName"> --%>
<%-- 									<input type="checkbox" name="itemName" value='<c:out value="${itemName}"/>' checked="checked">${itemName}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
							</div>
						</th>
						
						<c:forEach items="${requestScope.commodityAttrList}" var="commodityAttrVO">
							<th>${commodityAttrVO.commodityAttr}</th>
						</c:forEach>
<!-- 						<th>Qty</th> -->
<!-- 						<th> -->
<!-- 							<button type="button" class="btn btn-warning" data-toggle="modal">player</button> -->
<!-- 							<div class="player checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.players}" var="player"> --%>
<!-- 									<input type="checkbox" name="player" -->
<%-- 										value='<c:out value="${player}"/>' checked="checked">${player}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th>number</th> -->
<!-- 						<th>season</th> -->
<!-- 						<th>							 -->
<!-- 							<button type="button" class="btn btn-warning" data-toggle="modal">team</button> -->
<!-- 							<div class="team checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.teams}" var="team"> --%>
<!-- 									<input type="checkbox" name="team" -->
<%-- 										value='<c:out value="${team}"/>' checked="checked">${team}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th> -->
<!-- 						<button type="button" class="btn btn-warning" data-toggle="modal">style</button> -->
<!-- 							<div class="style checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.styles}" var="style"> --%>
<!-- 									<input type="checkbox" name="style" -->
<%-- 										value='<c:out value="${style}"/>' checked="checked">${style}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th>color</th> -->
<!-- 						<th>							 -->
<!-- 						<button type="button" class="btn btn-warning" data-toggle="modal">brand</button> -->
<!-- 							<div class="brand checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.brands}" var="brand"> --%>
<!-- 									<input type="checkbox" name="brand" -->
<%-- 										value='<c:out value="${brand}"/>' checked="checked">${brand}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th>						 -->
<!-- 						<button type="button" class="btn btn-warning" data-toggle="modal">size</button> -->
<!-- 							<div class="size checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.sizes}" var="size"> --%>
<!-- 									<input type="checkbox" name=size -->
<%-- 										value='<c:out value="${size}"/>' checked="checked">${size}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th>					 -->
<!-- 						<button type="button" class="btn btn-info" data-toggle="modal">level</button> -->
<!-- 							<div class="checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="" checked="checked">&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Replica" checked="checked">Replica&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Swingman" checked="checked">Swingman&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Authentic" checked="checked">Authentic&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Team Authentic" checked="checked">Team Authentic&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Pro Cut" checked="checked">Pro Cut&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Team Issued" checked="checked">Team Issued&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Game Issued" checked="checked">Game Issued&nbsp<br/> -->
<!-- 								<input type="checkbox" name=level value="Game Used" checked="checked">Game Used&nbsp<br/> -->
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th>						 -->
<!-- 						<button type="button" class="btn btn-warning" data-toggle="modal">condition</button> -->
<!-- 							<div class="condition checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.conditions}" var="condition"> --%>
<!-- 									<input type="checkbox" name=condition -->
<%-- 										value='<c:out value="${condition}"/>' checked="checked">${condition}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th> -->
<!-- 							<button type="button" class="btn btn-info" data-toggle="modal">tag</button> -->
<!-- 							<div class="checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<!-- 								<input type="checkbox" name=tag value="" checked="checked">&nbsp<br/> -->
<!-- 								<input type="checkbox" name=tag value="--" checked="checked">--&nbsp<br/> -->
<!-- 								<input type="checkbox" name=tag value="Yes" checked="checked">Yes&nbsp<br/> -->
<!-- 								<input type="checkbox" name=tag value="No" checked="checked">No&nbsp<br/> -->
<!-- 							</div> -->
<!-- 						</th> -->
<!-- 						<th>Patch/Certificate</th> -->
<!-- 						<th>serial</th> -->
<!-- 						<th>						 -->
<!-- 						<button type="button" class="btn btn-warning" data-toggle="modal">owner</button> -->
<!-- 							<div class="owner checkboxDiv"> -->
<!-- 								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/> -->
<%-- 								<c:forEach items="${requestScope.owners}" var="owner"> --%>
<!-- 									<input type="checkbox" name=owner -->
<%-- 										value='<c:out value="${owner}"/>' checked="checked">${owner}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
<!-- 						</th> -->
						<th>售價</th>
					<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
						<th>成本</th>
						<th>						
							<button type="button" class="btn btn-info" data-toggle="modal">銷售平台</button>
							<div class="authority checkboxDiv">
								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/>
<%-- 								<c:forEach items="${requestScope.authoritys}" var="authority"> --%>
<!-- 									<input type="checkbox" name=authority -->
<%-- 										value='<c:out value="${authority}"/>' checked="checked">${authority}&nbsp<br/> --%>
<%-- 								</c:forEach> --%>
							</div>
						</th>
						<th>
							<button type="button" class="btn btn-info" data-toggle="modal">是否仍在庫</button>
							<div class="checkboxDiv">
								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/>
								<input type="checkbox" name=isStored value="" checked="checked">&nbsp<br/>
								<input type="checkbox" name=isStored value="是" checked="checked">是&nbsp<br/>
								<input type="checkbox" name=isStored value="否" checked="checked">否&nbsp<br/>
							</div>
						</th>
					</c:if>
				</tr>
			</thead>


			<c:forEach items="${commodityList}" var="vo">
				<tr>
					<td><input type="checkbox" name="commodityIds"
						value="${vo.commodityId}"></td>
					<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
					<td>
						<button name="update" type="button" value="${vo.commodityId}" class="btn btn-warning">修改</button>
					</td>
					</c:if>
					<c:if test="${vo.pictureCount!=0}">
						<td><a
							href="/jersey/picture/${vo.commodityId}"><button
									type="button" class="btn btn-success" data-toggle="modal">${vo.pictureCount}</button></a></td>
					</c:if>
					<c:if test="${vo.pictureCount==0}">
						<td><a
							href="/jersey/picture/${vo.commodityId}"><button
									type="button" class="btn btn-danger" data-toggle="modal">0</button></a></td>
					</c:if>

					<td>
						<a href="/jersey/triple/commodity/${vo.commodityId}"><div class="itemName"><c:out value="${vo.itemName}" /></div></a>
						<input type="hidden" name="itemName" value="<c:out value="${vo.itemName}" />">
						
						<a href="${vo.link}" target="_blank" ${empty vo.link? 'style="display:none"':''}>連結</a>
						<input type="hidden" name="link" value="${vo.link}">
					</td>
					
					<c:forEach items="${vo.commodityAttrMappings}" var="commodityAttrMappingVO">
						<td>
							<div><c:out value="${commodityAttrMappingVO.commodityAttrValue}" /></div>
							<input type="hidden" class="commodityAttrMapping" name="${commodityAttrMappingVO.commodityAttrMappingId}" value="${commodityAttrMappingVO.commodityAttrValue}">
						</td>
					</c:forEach>
<%-- 					<td><div class=""><c:out value="${vo.qty}" /></div></td> --%>
<%-- 					<td><div class="player"><c:out value="${vo.player}" /></div></td> --%>
<%-- 					<td><div class=""><c:out value="${vo.number}" /></div></td> --%>
<%-- 					<td><div class=""><c:out value="${vo.season}" /></div></td> --%>
<%-- 					<td><div class="team"><c:out value="${vo.team}" /></div></td> --%>
<%-- 					<td><div class="style"><c:out value="${vo.style}" /></div></td> --%>
<%-- 					<td><div class=""><c:out value="${vo.color}" /></div></td> --%>
<%-- 					<td><div class="brand"><c:out value="${vo.brand}" /></div></td> --%>
<%-- 					<td><div class="size"><c:out value="${vo.size}" /></div></td> --%>
<%-- 					<td><div class="level"><c:out value="${vo.level}" /></div></td> --%>
<%-- 					<td><div class="condition"><c:out value="${vo.condition}" /></div></td> --%>
<%-- 					<td><div class="tag"><c:out value="${vo.tag}" /></div></td> --%>
<%-- 					<td><div class=""><c:out value="${vo.patchAndCertificate}" /></div></td> --%>
<%-- 					<td><div class=""><c:out value="${vo.serial}" /></div></td> --%>
<%-- 					<td><div class="owner"><c:out value="${vo.owner}" /></div></td> --%>
					<td>
						<div><c:out value="${vo.sellPrice}" /></div>
						<input type="hidden" name="sellPrice" value="${vo.sellPrice}">
					</td>
					<td>
						<div><c:out value="${vo.cost}" /></div>
						<input type="hidden" name="cost" value="${vo.cost}">
					</td>
				<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
					<td>
						<c:choose>
							<c:when test="${vo.authority=='admin'}">
								<button class="btn btn-warning commodityAuthority">未上架</button>
							</c:when>
							<c:when test="${vo.authority=='customer'}">
								<button class="btn btn-success commodityAuthority">已上架</button>
							</c:when>
						</c:choose>
					</td>
					<td>
						<div class="isStored">${vo.isStored ? '是':'否'}</div>
						<select style="display:none">
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</td>
				</c:if>
				</tr>
			</c:forEach>
		</table>

	<div align="center">
		<ul class="pagination">
			<c:forEach begin="1" end="${requestScope.pages}" var="page">
				<li class="page-item" value="${page}"><a class="page-link" href="?page=${page}">${page}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>