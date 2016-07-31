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

<title>商家</title>
</head>
<body>
<%--   <jsp:include page="/StoreServlet"> --%>
<%--   	<jsp:param name="action" value="getAll"/> --%>
<%--   </jsp:include> --%>
  
<%--   <c:if test="${param.action=='update'}"> --%>
<%--   	<c:forEach items="${storeList}" var="vo"> --%>
<%--   		<c:if test="${vo.storeId==param.storeId}"> --%>
<%--   		<c:set var="store" value="${vo}" scope="request"/> --%>
<%--   		<jsp:forward page="/store/update.jsp"/> --%>
<%--   		</c:if> --%>
<%--   	</c:forEach> --%>
<%--   </c:if> --%>
  <form action="/jersey/StoreServlet" method="POST">
    <c:import url="/WEB-INF/pages/header.jsp"/><span style="display: inline-block; width: 100px"></span>
  	<a href="/jersey/StoreServlet?action=getOne"><button type="button" class="btn btn-success" data-toggle="modal">新增</button></a>
  	<button type="submit" name="action" value="delete" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th></th>
      <th>商家編號/商家名稱</th>
      <th>商家類型</th>
    </tr>
    </thead>
      	
  	<c:forEach items="${storeList}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="storeIds" value="${vo.storeId}">
  	  </td>
  	  <td>
		<a href="/jersey/StoreServlet?action=getOne&storeId=${vo.storeId}"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
  	  <td>${vo.storeId} - <c:out value="${vo.name}" /></td>
  	  <c:if test="${vo.type=='store'}"><td>商店</td></c:if>
  	  <c:if test="${vo.type=='shippingCompany'}"><td>託運公司</td></c:if>
  	</tr>
  	</c:forEach>
  </table>
  </form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>