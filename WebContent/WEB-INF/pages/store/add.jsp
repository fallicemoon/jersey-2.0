<%@page import="sellCase.model.SellCaseVO"%>
<%@page import="sellCase.model.SellCaseDAO"%>
<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="purchaseCase.model.PurchaseCaseDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>新增商家</title>
</head>
<body>
<c:import url="/WEB-INF/pages/header.jsp"/>
<%-- 	<c:if test="${param.action=='create'}"> --%>
<%--     	<jsp:useBean id="store" class="store.model.StoreVO" scope="request"/> --%>
<%--     	<jsp:setProperty property="*" name="store"/> --%>
<%--     	<jsp:forward page="/StoreServlet"/> --%>
<%--     </c:if> --%>
	<br/><br/>
	<form action="/jersey/StoreServlet" method="post" class="form-horizontal">
	<input type="hidden" name="action" value="create">
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家類型：</label>
    	<div class="col-sm-10">
    	<select name="type" class="form-control" style="width: 14%">
    		<option value="store" selected="selected">商店</option>
    		<option value="shippingCompany">託運公司</option>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家名稱：</label>
    	<div class="col-sm-10">
    	<input type="text" name="name">
    	</div>
    </div>

	<input type="hidden" name="action" value="create">
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-success" >新增</button>
		</label>
	</div>
	</form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>
</body>
</html>