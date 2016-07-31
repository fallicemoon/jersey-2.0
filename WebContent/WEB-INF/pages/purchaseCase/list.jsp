<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>進貨</title>
</head>
<body>  
  <form action="/jersey/PurchaseCaseServlet" method="POST">
    <c:import url="/WEB-INF/pages/header.jsp"/><span style="display: inline-block; width: 100px"></span>
    <script type="text/javascript">
    	$(function(){
    		$("#getProgressNotComplete").click(function(){
    			var button = $(this).text();
    			$("tr").show();
    			if(button=="進貨未完成"){
        			$(".progress").each(function(){
        				if($(this).text()=="進貨完成"){
        					$(this).parent().hide();
        				}
        			});
        			$(this).text("列出全部");
    			} else {
    				$(this).text("進貨未完成");
    			}    			
    		});
    		
    		
    	});
    </script>
    <a href="/jersey/PurchaseCaseServlet?action=getOne"><button type="button" class="btn btn-success" data-toggle="modal">新增</button></a>
  	<button type="submit" name="action" value="delete" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  	<button type="button" class="btn btn-default btn-lg" id="getProgressNotComplete">進貨未完成</button>
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
      	
  	<c:forEach items="${purchaseCaseList}" var="vo">
  	<tr>
	  <td>
  	    <input type="checkbox" name="purchaseCaseIds" value="${vo.purchaseCaseId}">
  	  </td>
  	  <td>
		<a href="/jersey/PurchaseCaseServlet?action=getOne&purchaseCaseId=${vo.purchaseCaseId}"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
	  <td>
	    <a href="/jersey/PurchaseCaseServlet?action=getCommodityList&purchaseCaseId=${vo.purchaseCaseId}"><button type="button" class="btn btn-success">匯入商品</button></a>
	  </td>
  	  <td><a href="/jersey/TripleServlet?action=purchaseCase&purchaseCaseId=${vo.purchaseCaseId}">${vo.purchaseCaseId} - <c:out value="${vo.store.name}" /></a></td>
  	  <td><c:forEach items="${vo.commoditys}" var="commodity">${commodity.commodityId}-${commodity.itemName}<br></c:forEach></td>
  	  <td class="progress">${vo.progress}</td>
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
  	  <td><fmt:formatDate value="${vo.time}" pattern="yyyy/MM/dd hh:mm:ss"/></td>
  	</tr>
  	</c:forEach>
  </table>
  </form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>