<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.connector.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="lib/jquery-2.1.3.min.js"></script>

<style type="text/css">
	.checkboxDiv{
		position: absolute;
		display: none;
		background-color: #00A800;
		border: solid;
		min-width: 90px;
	}
</style>

<script type="text/javascript">

	var commodityAttr = [ "itemName", "player", "team", "style", "brand",
			"size", "level", "condition", "tag", "owner", "sellPlatform",
			"isStored" ];

	function filter() {
		$("tr").show();
		for ( var index in commodityAttr) {
			var filterName = commodityAttr[index];
			<%-- 從篩選條件中取得要保留的資料 --%>
			var keep = $("input:checked[name='"+filterName+"']").map(function(){
				return $(this).val();
			}).toArray();
			<%-- 只要有任何條件不符合就隱藏資料 --%>
			$("."+filterName).each(function(){
				if($.inArray($(this).text(), keep)==-1){
					$(this).closest("tr").hide();
				}
			});	
		}
	}

	$(function(){
		<%--顯示下拉式篩選條件的按鈕們--%>
		$(".checkboxDiv").prev().click(function(){
			$(this).next().slideToggle("fast");
		});
		
		<%--全選按鈕, 弄掉的話為全不選 --%>
		$("input[name=all]").change(function(){
			var checked = $(this).prop("checked");
			$(this).parent().siblings().children().each(function(){
				$(this).prop("checked", checked);
			});
		});
		
		<%--篩選條件發生變化時進行篩選--%>
		$("input[name]").change(function(){
			filter();
		});
	});
</script>

<title>商品</title>
</head>
<body>
	<form action="/jersey/CommodityServlet" method="POST">
		<c:import url="/WEB-INF/pages/header.jsp" />
		<span style="display: inline-block; width: 100px"></span> <a
			href="/jersey/CommodityServlet?action=getOne"><button
				type="button" class="btn btn-success" data-toggle="modal">新增</button></a>
		<button type="submit" name="action" value="delete"
			class="btn btn-danger" data-toggle="modal"
			onclick="return confirm('確認刪除?')">刪除</button>
<!-- 			改成用javaScript篩選 -->
<!-- 		<button type="submit" name="action" value="getByRule" -->
<!-- 			class="btn btn-warning" data-toggle="modal">篩選</button> -->
		<button type="submit" name="action" value="copy"
			class="btn btn-warning" data-toggle="modal">複製</button>
		<table border=1 width="1500px" class="table table-hover">
			<thead>
				<tr>
					<c:if test="${!requestScope.showRule}">
						<th></th>
						<th></th>
						<th>圖片</th>
						<th>商品名稱
						<th>Qty</th>
						<th>player</th>
						<th>number</th>
						<th>season</th>
						<th>team</th>
						<th>style</th>
						<th>color</th>
						<th>brand</th>
						<th>size</th>
						<th>level</th>
						<th>condition</th>
						<th>tag</th>
						<th>Patch/Certificate</th>
						<th>serial</th>
						<th>owner</th>
						<th>成本</th>
						<th>售價</th>
						<th>販售平台</th>
						<th>是否仍在庫</th>
					</c:if>
					<c:if test="${requestScope.showRule}">
						<th></th>
						<th></th>
						<th>圖片</th>
<!-- 						<th>商品編號/商品名稱 <select name="itemName"> -->
<!-- 								<option selected="selected">無</option> -->
<%-- 								<c:forEach items="${requestScope.itemNames}" var="itemName"> --%>
<%-- 									<option value="${itemName}">${itemName}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 						</select> -->
<!-- 						</th> -->
						<th>
							<button type="button" class="btn btn-warning" data-toggle="modal">商品名稱</button>				
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.itemNames}" var="itemName">
									<label><input type="checkbox" name="itemName" value='<c:out value="${itemName}"/>' checked="checked">${itemName}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>Qty</th>
						<th>
							<button type="button" class="btn btn-warning" data-toggle="modal">player</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.players}" var="player">
									<label><input type="checkbox" name="player"
										value='<c:out value="${player}"/>' checked="checked">${player}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>number</th>
						<th>season</th>
						<th>							
							<button type="button" class="btn btn-warning" data-toggle="modal">team</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.teams}" var="team">
									<label><input type="checkbox" name="team"
										value='<c:out value="${team}"/>' checked="checked">${team}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>
						<button type="button" class="btn btn-warning" data-toggle="modal">style</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.styles}" var="style">
									<label><input type="checkbox" name="style"
										value='<c:out value="${style}"/>' checked="checked">${style}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>color</th>
						<th>							
						<button type="button" class="btn btn-warning" data-toggle="modal">brand</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.brands}" var="brand">
									<label><input type="checkbox" name="brand"
										value='<c:out value="${brand}"/>' checked="checked">${brand}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>						
						<button type="button" class="btn btn-warning" data-toggle="modal">size</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.sizes}" var="size">
									<label><input type="checkbox" name=size
										value='<c:out value="${size}"/>' checked="checked">${size}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>					
						<button type="button" class="btn btn-info" data-toggle="modal">level</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<label><input type="checkbox" name=level value="" checked="checked">&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Replica" checked="checked">Replica&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Swingman" checked="checked">Swingman&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Authentic" checked="checked">Authentic&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Team Authentic" checked="checked">Team Authentic&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Pro Cut" checked="checked">Pro Cut&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Team Issued" checked="checked">Team Issued&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Game Issued" checked="checked">Game Issued&nbsp</label><br/>
								<label><input type="checkbox" name=level value="Game Used" checked="checked">Game Used&nbsp</label><br/>
							</div>
						</th>
						<th>						
						<button type="button" class="btn btn-warning" data-toggle="modal">condition</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.conditions}" var="condition">
									<label><input type="checkbox" name=condition
										value='<c:out value="${condition}"/>' checked="checked">${condition}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>
							<button type="button" class="btn btn-info" data-toggle="modal">tag</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<label><input type="checkbox" name=tag value="" checked="checked">&nbsp</label><br/>
								<label><input type="checkbox" name=tag value="--" checked="checked">--&nbsp</label><br/>
								<label><input type="checkbox" name=tag value="Yes" checked="checked">Yes&nbsp</label><br/>
								<label><input type="checkbox" name=tag value="No" checked="checked">No&nbsp</label><br/>
							</div>
						</th>
						<th>Patch/Certificate</th>
						<th>serial</th>
						<th>						
						<button type="button" class="btn btn-warning" data-toggle="modal">owner</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.owners}" var="owner">
									<label><input type="checkbox" name=owner
										value='<c:out value="${owner}"/>' checked="checked">${owner}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>成本</th>
						<th>售價</th>
						<th>						
							<button type="button" class="btn btn-warning" data-toggle="modal">販售平台</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<c:forEach items="${requestScope.sellPlatforms}" var="sellPlatform">
									<label><input type="checkbox" name=sellPlatform
										value='<c:out value="${sellPlatform}"/>' checked="checked">${sellPlatform}&nbsp</label><br/>
								</c:forEach>
							</div>
						</th>
						<th>
							<button type="button" class="btn btn-info" data-toggle="modal">是否仍在庫</button>
							<div class="checkboxDiv">
								<label><input type="checkbox" name="all" checked="checked">全選&nbsp</label><br/>
								<label><input type="checkbox" name=isStored value="" checked="checked">&nbsp</label><br/>
								<label><input type="checkbox" name=isStored value="是" checked="checked">是&nbsp</label><br/>
								<label><input type="checkbox" name=isStored value="否" checked="checked">否&nbsp</label><br/>
							</div>
						</th>
					</c:if>
				</tr>
			</thead>


			<c:forEach items="${commodityList}" var="vo">
				<tr>
					<td><input type="checkbox" name="commodityIds"
						value="${vo.commodityId}"></td>
					<td><a
						href="/jersey/CommodityServlet?action=getOne&commodityId=${vo.commodityId}"><button
								type="button" class="btn btn-warning">修改</button></a></td>
					<c:if test="${vo.pictureCount!=0}">
						<td><a
							href="/jersey/PictureServlet?commodityId=${vo.commodityId}"><button
									type="button" class="btn btn-success" data-toggle="modal">${vo.pictureCount}</button></a></td>
					</c:if>
					<c:if test="${vo.pictureCount==0}">
						<td><a
							href="/jersey/PictureServlet?commodityId=${vo.commodityId}"><button
									type="button" class="btn btn-danger" data-toggle="modal">0</button></a></td>
					</c:if>

					<td><a href="/jersey/TripleServlet?action=commodity&commodityId=${vo.commodityId}">
						<div class="itemName"><c:out value="${vo.itemName}" /></div></a>
					<c:if test="${!empty vo.link}">
							<a href="${vo.link}" target="_blank"> 連結</a>
						</c:if> <c:if test="${empty vo.link}"></c:if></td>
					<td><div class=""><c:out value="${vo.qty}" /></div></td>
					<td><div class="player"><c:out value="${vo.player}" /></div></td>
					<td><div class=""><c:out value="${vo.number}" /></div></td>
					<td><div class=""><c:out value="${vo.season}" /></div></td>
					<td><div class="team"><c:out value="${vo.team}" /></div></td>
					<td><div class="style"><c:out value="${vo.style}" /></div></td>
					<td><div class=""><c:out value="${vo.color}" /></div></td>
					<td><div class="brand"><c:out value="${vo.brand}" /></div></td>
					<td><div class="size"><c:out value="${vo.size}" /></div></td>
					<td><div class="level"><c:out value="${vo.level}" /></div></td>
					<td><div class="condition"><c:out value="${vo.condition}" /></div></td>
					<td><div class="tag"><c:out value="${vo.tag}" /></div></td>
					<td><div class=""><c:out value="${vo.patchAndCertificate}" /></div></td>
					<td><div class=""><c:out value="${vo.serial}" /></div></td>
					<td><div class="owner"><c:out value="${vo.owner}" /></div></td>
					<td><div class=""><c:out value="${vo.cost}" /></div></td>
					<td><div class=""><c:out value="${vo.sellPrice}" /></div></td>
					<td><div class="sellPlatform"><c:out value="${vo.sellPlatform}" /></div></td>
					<td><div class="isStored"><c:if test="${vo.isStored ? '是':'否'}"></c:if></div></td>
				</tr>
			</c:forEach>
		</table>
	</form>
	<c:import url="/WEB-INF/pages/footer.jsp"></c:import>
</body>
</html>