<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.connector.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#changePage{
	background-color: #FFDD55;
}
</style>
<title>出貨</title>
</head>
<body>
	<form action="/jersey/SellCaseServlet" method="POST">
		<c:import url="/WEB-INF/pages/header.jsp" />
		<script type="text/javascript">
			$(function(){
				var page = "before";
				var listType = "getAll";
				var closeListTypeButton = function(){
					$(".listType").css("color", "");
					$("#"+listType).css("color", "red");
				};
				
				<%--列出全部和到第一頁--%>
				$("#getAll").css("color", "red");
				$(".after").hide();
				

				<%--列出未收額不為0的資料--%>
				$("#getUncollectedNotZero").click(function(){
					listType = "getUncollectedNotZero";
					$("tr").show();
					$(".uncollected").each(function(){
						if($(this).text()=="0"){
							$(this).parent().hide();
						}
					});
					closeListTypeButton();
				});
				
				<%--列出已結案的資料--%>
				$("#getIsClosed").click(function(){
					listType = "getIsClosed";
					$("tr").show();
					$(".isClosed").each(function(){
						if($(this).text()=="否"){
							$(this).parent().hide();
						}
					});
					closeListTypeButton();
				});
				
				<%--列出未結案的資料--%>
				$("#getNotClosed").click(function(){
					listType = "getNotClosed";
					$("tr").show();
					$(".isClosed").each(function(){
						if($(this).text()=="是"){
							$(this).parent().hide();
						}
					});
					closeListTypeButton();
				});
				
				<%--列出全部的資料--%>
				$("#getAll").click(function(){
					listType = "getAll";
					$("tr").show();
					closeListTypeButton();
				});
				
				<%--切換到到第二頁--%>
				$("#changePage").click(function(){
					$("th").show();
					$("td").show();
					$("."+page).each(function(){
						$(this).hide();
					});					
					page = page=="before"?"after":"before";
					$(this).text(page=="before"?"到第二頁":"到第一頁");
				});
				
			});
		</script>
		<span style="display: inline-block; width: 100px"></span> <a
			href="/jersey/SellCaseServlet?action=getOne"><button
				type="button" class="btn btn-success" data-toggle="modal">新增</button></a>
		<button type="submit" name="action" value="delete"
			class="btn btn-danger" data-toggle="modal"
			onclick="return confirm('確認刪除?')">刪除</button>

		<button type="button" class="btn btn-default btn-lg listType" id="getUncollectedNotZero">尚有未收額</button>
		<button type="button" class="btn btn-default btn-lg listType" id="getIsClosed">已結案</button>
		<button type="button" class="btn btn-default btn-lg listType" id="getNotClosed">未結案</button>
		<button type="button" class="btn btn-default btn-lg listType" id="getAll">列出全部</button>
		<button type="button" class="btn btn-default btn-lg" id="changePage">到第二頁</button>
		
			
			<table border=1 width="1500px" class="table table-hover">
				<thead>
					<tr>
						<th class="before"></th>
						<th class="before"></th>
						<th class="before"></th>
						<th class="before">出貨編號/收件人</th>
						<th class="before">總價</th>
						<th class="before">是否已出貨</th>
						<th class="before">是否以查收</th>
						<th class="before">未收額</th>
						<th class="before">是否已結案</th>
						<th class="before">實際利潤 / 預期利潤</th>
						
						<th class="after"></th>
						<th class="after"></th>
						<th class="after"></th>						
						<th class="after">進貨編號/商家名稱</th>
						<th class="after">運送方式</th>
						<th class="after">手機</th>
						<th class="after">地址/店名</th>
						<th class="after">Tracking number</th>
						<th class="after">運費</th>
						<th class="after">時間</th>
						<th class="after">備註</th>
						<th class="after">已收額</th>
						<th class="after">結案時間</th>
					</tr>
				</thead>
				<c:forEach items="${sellCaseList}" var="vo">
					<tr>
						<td><input type="checkbox" name="sellCaseIds"
							value="${vo.sellCaseId}"></td>
						<td><a
							href="/jersey/SellCaseServlet?action=getOne&sellCaseId=${vo.sellCaseId}"><button
									type="button" class="btn btn-warning">修改</button></a></td>
						<td><a
							href="/jersey/SellCaseServlet?action=getPurchaseCaseList&sellCaseId=${vo.sellCaseId}"><button
									type="button" class="btn btn-success">匯入進貨</button></a></td>
						<td class="before"><a
							href="/jersey/TripleServlet?action=sellCase&sellCaseId=${vo.sellCaseId}">${vo.sellCaseId}
								- <c:out value="${vo.addressee}" />
						</a></td>
						<td class="before">${vo.income}</td>
 						<td class="before">${vo.isShipping ? '是':'否'}</td>
 						<td class="before">${vo.isChecked ? '是':'否'}</td>
						<td class="uncollected before">${vo.uncollected}</td>
						<td class="isClosed before">${(vo.uncollected==0&&vo.isChecked)?'是':'否'}</td>
						<td class="before">已收額${vo.collected} (總價${vo.income}) - 成本${vo.costs} -
							國際運費${vo.agentCosts} - 國內運費${vo.transportCost} = <c:if
								test="${vo.benefit < 0}">
								<span style="color: red">${vo.benefit} / </span>
							</c:if> <c:if test="${vo.benefit >= 0}">
								<span style="color: blue">${vo.benefit} / </span>
							</c:if> <c:if test="${vo.estimateBenefit < 0}">
								<span style="color: red">${vo.estimateBenefit}</span>
							</c:if> <c:if test="${vo.estimateBenefit >= 0}">
								<span style="color: blue">${vo.estimateBenefit}</span>
							</c:if>
						</td>
						<td class="after"><c:forEach items="${vo.purchaseCases}" var="purchaseCase">
							<a href="/jersey/TripleServlet?action=purchaseCase&purchaseCaseId=${purchaseCase.purchaseCaseId}">${purchaseCase.purchaseCaseId}-${purchaseCase.store.name}</a>
							<br>
						</c:forEach></td>
						<td class="after">${vo.transportMethod}</td>
						<td class="after"><c:out value="${vo.phone}" /></td>
						<td class="after"><c:out value="${vo.address}" /></td>
						<td class="after"><c:out value="${vo.trackingNumber}" /></td>
						<td class="after">${vo.transportCost}</td>
						<td class="after"><fmt:formatDate value="${vo.shippingTime}"
								pattern="yyyy/MM/dd hh:mm:ss" /></td>
						<td class="after"><c:out value="${vo.description}" /></td>
						<td class="after">${vo.collected}</td>
						<td class="after"><fmt:formatDate value="${vo.closeTime}"
								pattern="yyyy/MM/dd hh:mm:ss" /></td>
					</tr>
				</c:forEach>
			</table>
	</form>

	<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>