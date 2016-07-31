<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.connector.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>修改出貨中之進貨</title>
</head>
<body>
  <c:import url="/WEB-INF/pages/header.jsp"/>

  <form action="/jersey/SellCaseServlet" method="POST">
  <input type="hidden" name="action" value="addSellCaseId">
  <input type="hidden" name="sellCaseId" value="${param.sellCaseId}">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th>進貨編號/商家名稱</th>
	  <th>商品編號/商品名稱</th>
      <th>進度</th>
      <th>託運公司</th>
      <th>Tracking number</th>
      <th>代運人</th>
      <th>代運Tracking number</th>
      <th>是否為代購</th>
      <th>成本</th>
      <th>國際運費</th>
      <th>備註</th>
      <th>進貨時間</th>
    </tr>
    </thead>
      	
  	<c:forEach items="${purchaseCaseListNotInSellCase}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="purchaseCaseIds" value="${vo.purchaseCaseId}">
  	  </td>
<%--   	  <jsp:include page="/StoreServlet"> --%>
<%--   	  	<jsp:param value="getOne" name="action"/> --%>
<%--   	  	<jsp:param value="${vo.store}" name="storeId"/> --%>
<%--   	  </jsp:include> --%>
  	  <td>${vo.purchaseCaseId} - <c:out value="${vo.store.name}" /></td>
  	  <td><c:forEach items="${vo.commoditys}" var="commodity">${commodity.commodityId}-${commodity.itemName}<br></c:forEach></td>
  	  <td>${vo.progress}</td>
<%--   	  <jsp:include page="/StoreServlet"> --%>
<%--   	  	<jsp:param value="getOne" name="action"/> --%>
<%--   	  	<jsp:param value="${vo.shippingCompany}" name="storeId"/> --%>
<%--   	  </jsp:include> --%>
  	  <td><c:out value="${vo.store.name}" /></td>
  	  <c:if test="${!empty vo.trackingNumberLink}"><td><c:out value="${vo.trackingNumber}" /><a href="${vo.trackingNumberLink}" target="_blank">連結</a></c:if>
  	  <c:if test="${empty vo.trackingNumberLink}"><td><c:out value="${vo.trackingNumber}" /></td></c:if>
  	  <td><c:out value="${vo.agent}" /></td>
  	  <c:if test="${!empty vo.agentTrackingNumberLink}"><td><c:out value="${vo.agentTrackingNumber}" /><a href="${vo.agentTrackingNumberLink}" target="_blank">連結</a></c:if>
  	  <c:if test="${empty vo.agentTrackingNumberLink}"><td><c:out value="${vo.agentTrackingNumber}" /></td></c:if>
  	  <c:if test="${vo.isAbroad}"><td>是</td></c:if>
  	  <c:if test="${!vo.isAbroad}"><td>否</td></c:if>
  	  <td>${vo.cost}</td>
  	  <td>${vo.agentCost}</td>
  	  <td><c:out value="${vo.description}" /></td>
  	  <td>${vo.time}</td>
  	</tr>
  	</c:forEach>
  </table>
  <button type="submit" class="btn btn-warning" data-toggle="modal">確定</button>
  </form>
  <br><br><br>

  <c:if test="${param.sellCaseId != 0}">
  <form action="/jersey/SellCaseServlet" method="POST">
  <input type="hidden" name="action" value="deleteSellCaseId">
  <input type="hidden" name="sellCaseId" value="${param.sellCaseId}">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th>進貨編號/商家名稱</th>
	  <th>商品編號/商品名稱</th>
      <th>進度</th>
      <th>託運公司</th>
      <th>Tracking number</th>
      <th>代運人</th>
      <th>代運Tracking number</th>
      <th>是否為代購</th>
      <th>成本</th>
      <th>國際運費</th>
      <th>備註</th>
      <th>進貨時間</th>
    </tr>
    </thead>
      	
  	<c:forEach items="${purchaseCaseListInSellCase}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="purchaseCaseIds" value="${vo.purchaseCaseId}">
  	  </td>
<%--   	  <jsp:include page="/StoreServlet"> --%>
<%--   	  	<jsp:param value="getOne" name="action"/> --%>
<%--   	  	<jsp:param value="${vo.store}" name="storeId"/> --%>
<%--   	  </jsp:include> --%>
  	  <td>${vo.purchaseCaseId} - <c:out value="${vo.store.name}" /></td>
  	  <td><c:forEach items="${vo.commoditys}" var="commodity"> ${commodity.commodityId}-${commodity.itemName}<br></c:forEach></td>
  	  <td>${vo.progress}</td>
<%--   	  <jsp:include page="/StoreServlet"> --%>
<%--   	  	<jsp:param value="getOne" name="action"/> --%>
<%--   	  	<jsp:param value="${vo.shippingCompany}" name="storeId"/> --%>
<%--   	  </jsp:include> --%>
  	  <td><c:out value="${vo.store.name}" /></td>
  	  <c:if test="${!empty vo.trackingNumberLink}"><td><c:out value="${vo.trackingNumber}" /><a href="${vo.trackingNumberLink}" target="_blank"> 連結</a></c:if>
  	  <c:if test="${empty vo.trackingNumberLink}"><td><c:out value="${vo.trackingNumber}" /></td></c:if>
  	  <td><c:out value="${vo.agent}" /></td>
  	  <c:if test="${!empty vo.agentTrackingNumberLink}"><td><c:out value="${vo.agentTrackingNumber}" /><a href="${vo.agentTrackingNumberLink}" target="_blank"> 連結</a></c:if>
  	  <c:if test="${empty vo.agentTrackingNumberLink}"><td><c:out value="${vo.agentTrackingNumber}" /></td></c:if>
  	  <c:if test="${vo.isAbroad}"><td>是</td></c:if>
  	  <c:if test="${!vo.isAbroad}"><td>否</td></c:if>
  	  <td>${vo.cost}</td>
  	  <td>${vo.agentCost}</td>
  	  <td><c:out value="${vo.description}" /></td>
  	  <td>${vo.time}</td>
  	</tr>
  	</c:forEach>
  </table>
  <button type="submit" class="btn btn-danger" data-toggle="modal">刪除</button>
  </form>
  </c:if>
  
<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>