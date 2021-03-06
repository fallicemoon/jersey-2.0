<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>修改進貨中之商品</title>
</head>
<body>
  <c:import url="/WEB-INF/pages/header.jsp"/>
  
  <form action="/jersey/purchaseCase/addCommodity" method="POST">
  <input type="hidden" name="_method" value="PUT">
  <input type="hidden" name="purchaseCaseId" value="${requestScope.purchaseCaseId}">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th>商品編號/商品名稱</th>
      <td>販售平台</td>
      <td>是否仍在庫</td>
    </tr>
    </thead>
      	
  	<c:forEach items="${commodityListNotInPurchaseCase}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="commodityIds" value="${vo.id}">
  	  </td>
  	  <td>${vo.id} - <c:out value="${vo.itemName}" /><c:if test="${vo.link != null}"><a href="${vo.link}" target="_blank">連結</a></c:if>
  	  		<c:if test="${vo.link == null}"></c:if></td>
  	  <td>${vo.authority=='ADMIN' ? '未上架':'已上架'}</td>
  	  <td>${vo.isStored ? '是':'否'}</td>
  	</tr>
  	</c:forEach>
  </table>
  <button type="submit" class="btn btn-warning" data-toggle="modal">確定</button>
  </form>
  <br><br><br>

  <c:if test="${param.purchaseCaseId != 0}">
  <form action="/jersey/purchaseCase/removeCommodity" method="POST">
  <input type="hidden" name="_method" value="PUT">
  <input type="hidden" name="purchaseCaseId" value="${requestScope.purchaseCaseId}">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th>商品編號/商品名稱</th>
      <td>販售平台</td>
      <td>是否仍在庫</td>
    </tr>
    </thead>
      	
  	<c:forEach items="${commodityListInPurchaseCase}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="commodityIds" value="${vo.id}">
  	  </td>
  	  <td>${vo.id} - <c:out value="${vo.itemName}" /><c:if test="${vo.link != null}"><a href="${vo.link}" target="_blank">連結</a></c:if>
  	  		<c:if test="${vo.link == null}"></c:if></td>
  	  <td>${vo.authority=='ADMIN' ? '未上架':'已上架'}</td>
  	  <td>${vo.isStored ? '是':'否'}</td>
  	</tr>
  	</c:forEach>
  </table>
  <button type="submit" class="btn btn-danger" data-toggle="modal">刪除</button>
  </form>
  </c:if>
  
<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>