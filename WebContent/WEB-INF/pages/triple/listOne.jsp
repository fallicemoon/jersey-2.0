<%@page import="org.apache.catalina.connector.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>關聯條目</title>
</head>
<body>
  <c:import url="/WEB-INF/pages/header.jsp"/>
  <br>
  <h1>${requestScope.title}</h1>
	<h3>商品</h3>
	<form action="/jersey/commodity" method="POST">
  	<table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th>圖片</th>
      <th>商品編號/商品名稱</th>
      <td>販售平台</td>
      <td>是否仍在庫</td>
    </tr>
    </thead>
      	
  	<c:forEach items="${requestScope.commodityList}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="commodityIds" value="${vo.commodityId}">
  	  </td>
  	  <td><a href="/jersey/picture/${vo.commodityId}/getAll"><button type="button" class="btn ${vo.pictureCount!=0 ? 'btn-success':'btn-danger'}" data-toggle="modal">${vo.pictureCount}</button></a></td>
  	  
  	  <td>${vo.commodityId} - <c:out value="${vo.itemName}" />
  	  		<c:if test="${!empty vo.link}"><a href="${vo.link}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty vo.link}"></c:if></td>
  	  <td>${vo.authority=='ADMIN' ? '未上架':'已上架'}</td>
  	  <c:if test="${vo.isStored}"><td>是</td></c:if>
  	  <c:if test="${!vo.isStored}"><td>否</td></c:if>
  	</tr>
  	</c:forEach>
  </table>
  	<button type="submit" name="_method" value="PUT" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  </form>

<h3>進貨</h3>
  <form action="/jersey/purchaseCase" method="POST">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th></th>
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
      	
  	<c:forEach items="${requestScope.purchaseCaseList}" var="vo">
  	<tr>
	  <td>
  	    <input type="checkbox" name="purchaseCaseIds" value="${vo.purchaseCaseId}">
  	  </td>
  	  <td>
		<a href="/jersey/purchaseCase/${vo.purchaseCaseId}"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
  	  <td>
	    <a href="/jersey/purchaseCase/getCommodityList/${vo.purchaseCaseId}"><button type="button" class="btn btn-success">匯入商品</button></a>
	  </td>
  	  <td>${vo.purchaseCaseId} - <c:out value="${vo.store.name}" /></td>
  	  <td><c:forEach items="${vo.commoditys}" var="commodity">${commodity.commodityId}-${commodity.itemName}<br></c:forEach></td>
  	  <td>${vo.progress}</td>
  	  <td><c:out value="${vo.shippingCompany.name}" /></td>
  	  <td><c:out value="${vo.trackingNumber}" /><c:if test="${!empty vo.trackingNumberLink}"><a href="${vo.trackingNumberLink}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty vo.trackingNumberLink}"></c:if></td>
  	  <td><c:out value="${vo.agent}" /></td>
  	  <td><c:out value="${vo.agentTrackingNumber}" /><c:if test="${!empty vo.agentTrackingNumberLink}"><a href="${vo.agentTrackingNumberLink}" target="_blank"> 連結</a></c:if>
  	  	  <c:if test="${empty vo.agentTrackingNumberLink}"></c:if></td>
  	  <c:if test="${vo.isAbroad}"><td>是</td></c:if>
  	  <c:if test="${!vo.isAbroad}"><td>否</td></c:if>
  	  <td>${vo.cost}</td>
  	  <td>${vo.agentCost}</td>
  	  <td><c:out value="${vo.description}" /></td>
  	  <td>${vo.createTime}</td>
  	</tr>
  	</c:forEach>
  </table>
  	<button type="submit" name="_method" value="PUT" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  </form>	

  <h3>出貨</h3>
  <form action="/jersey/sellCase" method="POST">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th></th>
      <th></th>
      <th>出貨編號/收件人</th>
      <th>總價</th>
      <th>是否已出貨</th>
      <th>是否以查收</th>
      <th>未收額</th>
      <th>是否已結案</th>
      <th>實際利潤/預期利潤</th>
      <th>進貨編號/商家名稱</th>
      <th>運送方式</th>
      <th>手機</th>
      <th>地址/店名</th>
      <th>Tracking number</th>
      <th>運費</th>
      <th>出貨時間</th>
      <th>備註</th>
      <th>已收額</th>
      <th>結案時間</th>
    </tr>
    </thead>
  	<c:forEach items="${sellCaseList}" var="vo">
  	<tr>
  	  <td>
  	  	<input type="checkbox" name="sellCaseIds" value="${vo.sellCaseId}">
  	  </td>
  	  <td>
  	  	<a href="/jersey/sellCase/${vo.sellCaseId}"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
  	  <td><a href="/jersey/sellCase/getPurchaseCaseList/${vo.sellCaseId}"><button type="button" class="btn btn-success">匯入進貨</button></a></td>
  	  <td>${vo.sellCaseId} - <c:out value="${vo.addressee}" /></td>
  	  <td>${vo.income}</td>
  	  <c:if test="${vo.isShipping}"><td>是</td></c:if>
  	  <c:if test="${!vo.isShipping}"><td>否</td></c:if>
  	  <c:if test="${vo.isChecked}"><td>是</td></c:if>
  	  <c:if test="${!vo.isChecked}"><td>否</td></c:if>
  	  <td>${vo.uncollected}</td>
  	  <td><c:if test="${vo.uncollected==0 && vo.isChecked}">是</c:if>
  	      <c:if test="${!vo.isChecked || vo.uncollected!=0}">否</c:if></td>
  	  <td>已收額${vo.collected} (總價${vo.income}) - 成本${vo.costs} - 國際運費${vo.agentCosts} - 國內運費${vo.transportCost} =   	  
  	  	  <c:if test="${vo.benefit < 0}"><span style="color: red">${vo.benefit} / </span></c:if>
		  <c:if test="${vo.benefit >= 0}"><span style="color: blue">${vo.benefit} / </span></c:if>
  	  	  <c:if test="${vo.estimateBenefit < 0}"><span style="color: red">${vo.estimateBenefit}</span></c:if>
		  <c:if test="${vo.estimateBenefit >= 0}"><span style="color: blue">${vo.estimateBenefit}</span></c:if>
	  </td>
  	  <td><c:forEach items="${vo.purchaseCases}" var="purchaseCase">
  	  	${purchaseCase.purchaseCaseId}-${purchaseCase.store.name}<br>
  	  </c:forEach></td>
	  <td>${vo.transportMethod}</td>
	  <td><c:out value="${vo.phone}" /></td>
  	  <td><c:out value="${vo.address}" /></td>
  	  <td><c:out value="${vo.trackingNumber}" /></td>
  	  <td>${vo.transportCost}</td>
  	  <td>${vo.shippingTime}</td>
  	  <td><c:out value="${vo.description}" /></td>
  	  <td>${vo.collected}</td>
  	  <td>${vo.closeTime}</td>
  	</tr>
  	</c:forEach>
  </table>
  	<button type="submit" name="_method" value="PUT" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  </form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>