<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會計</title>
</head>
<body>
	<c:import url="/WEB-INF/pages/header.jsp"/>
	<br>
	<h2>${requestScope.start} ~ ${requestScope.end}</h2>
	<br>	
	<table border=1 width="1500px" class="table table-hover">
		<thead>
		<tr>
			<th>出貨編號 - 收件人</th>
			<th>實際利潤</th>
		</tr>
		</thead>
		<c:forEach items="${requestScope.sellCaseList}" var="sellCase">
		<tr>
		<td>${sellCase.sellCaseId}  ${sellCase.addressee}</td>
<%-- 		<jsp:include page="/SellCaseServlet"> --%>
<%--   	  		<jsp:param value="getOne" name="action"/> --%>
<%--   	  		<jsp:param value="${sellCase.sellCaseId}" name="sellCaseId"/> --%>
<%--   	  	</jsp:include> --%>
  	  	<td>已收額${sellCase.collected} - 成本${sellCase.costs} - 國際運費${sellCase.agentCosts} - 國內運費${sellCase.transportCost} = 
  	  	  <c:if test="${sellCase.benefit < 0}"><span style="color: red">${sellCase.benefit}</span></c:if>
		  <c:if test="${sellCase.benefit >= 0}"><span style="color: blue">${sellCase.benefit}</span></c:if>
	  	</td>
		</tr>
		</c:forEach>
	</table>
	
  	<div style="length:100%; bottom:9px; text-align:center;">
	<div>
	<h2>總利潤 : ${requestScope.totalBenefit}元</h2>
  	</div>
  	</div>
  	
  <br><br><br>
  <div style="length:100%; bottom:9px; text-align:center;">
		<div>
  		<a href="/jersey/AccountingServlet"> <button type="button" class="btn btn-primary btn-lg">時間選單</button></a>
  		</div>
  </div>

</body>
</html>