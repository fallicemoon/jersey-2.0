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
<title>修改商品項目</title>
</head>
<c:import url="/WEB-INF/pages/header.jsp"/>
<body>
	<br/><br/>
	<br/><br/>
	<c:forEach items="${requestScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	
	<form action="/jersey/CommodityServlet" method="post" class="form-horizontal">
	<input type="hidden" name="action" value="update">
	
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商品編號：</label>
    	<div class="col-sm-10">
    	<input type="hidden" name="commodityId" value="${commodity.commodityId}">${commodity.commodityId}
    	</div>
    </div>
	
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商品名稱：</label>
    	<div class="col-sm-10">
    	<input type="text" name="itemName" value='<c:out value="${commodity.itemName}"/>'>
    	</div>
    </div>
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Qty：</label>
    	<div class="col-sm-10">
    	<input type="text" name="qty" value='${commodity.qty}'> 請輸入數字!
    	</div>
    </div>      
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">link：</label>
    	<div class="col-sm-10">
    	<input type="text" name="link" value="${commodity.link}">
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">player：</label>
    	<div class="col-sm-10">
    	<input type="text" name="player" value='<c:out value="${commodity.player}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="number" value='<c:out value="${commodity.number}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">season：</label>
    	<div class="col-sm-10">
    	<input type="text" name="season" value='<c:out value="${commodity.season}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">team：</label>
    	<div class="col-sm-10">
    	<input type="text" name="team" value='<c:out value="${commodity.team}"/>'>
    	</div>
    </div>
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">style：</label>
    	<div class="col-sm-10">
    	<input type="text" name="style" value='<c:out value="${commodity.style}"/>'>
    	</div>
    </div>        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">color：</label>
    	<div class="col-sm-10">
    	<input type="text" name="color" value='<c:out value="${commodity.color}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">brand：</label>
    	<div class="col-sm-10">
    	<input type="text" name="brand" value='<c:out value="${commodity.brand}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">size：</label>
    	<div class="col-sm-10">
    	<input type="text" name="size" value='<c:out value="${commodity.size}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">level：</label>
    	<div class="col-sm-10">
    	<select name="level">
    		<c:if test="${empty commodity.level}"><option selected="selected"></option></c:if>
    		<c:if test="${not empty commodity.level}"><option></option></c:if>    		
    		<c:if test="${commodity.level == 'Replica'}"><option selected="selected">Replica</option></c:if>
    		<c:if test="${commodity.level != 'Replica'}"><option>Replica</option></c:if>
    		<c:if test="${commodity.level == 'Swingman'}"><option selected="selected">Swingman</option></c:if>
    		<c:if test="${commodity.level != 'Swingman'}"><option>Swingman</option></c:if>
    		<c:if test="${commodity.level == 'Authentic'}"><option selected="selected">Authentic</option></c:if>
    		<c:if test="${commodity.level != 'Authentic'}"><option>Authentic</option></c:if>
    		<c:if test="${commodity.level == 'Team Authentic'}"><option selected="selected">Team Authentic</option></c:if>
    		<c:if test="${commodity.level != 'Team Authentic'}"><option>Team Authentic</option></c:if>
    		<c:if test="${commodity.level == 'Pro Cut'}"><option selected="selected">Pro Cut</option></c:if>
    		<c:if test="${commodity.level != 'Pro Cut'}"><option>Pro Cut</option></c:if>
    		<c:if test="${commodity.level == 'Team Issued'}"><option selected="selected">Team Issued</option></c:if>
    		<c:if test="${commodity.level != 'Team Issued'}"><option>Team Issued</option></c:if>
    		<c:if test="${commodity.level == 'Game Issued'}"><option selected="selected">Game Issued</option></c:if>
    		<c:if test="${commodity.level != 'Game Issued'}"><option>Game Issued</option></c:if>
    		<c:if test="${commodity.level == 'Game Used'}"><option selected="selected">Game Used</option></c:if>
    		<c:if test="${commodity.level != 'Game Used'}"><option>Game Used</option></c:if>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">condition：</label>
    	<div class="col-sm-10">
    	<input type="text" name="condition" value='<c:out value="${commodity.condition}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">tag：</label>
    	<div class="col-sm-10">
    	<select name="tag">
    		<c:choose>
    			<c:when test="${commodity.tag != '--'}"><option>--</option></c:when>
    			<c:otherwise><option selected="selected">--</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    			<c:when test="${commodity.tag != 'Yes'}"><option>Yes</option></c:when>
    			<c:otherwise><option selected="selected">Yes</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    			<c:when test="${commodity.tag != 'No'}"><option>No</option></c:when>
    			<c:otherwise><option selected="selected">No</option></c:otherwise>
    		</c:choose>
    	</select>
    	</div>
    </div>
    
        <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Patch/Certificate：</label>
    	<div class="col-sm-10">
    	<input type="text" name="patchAndCertificate" value='<c:out value="${commodity.patchAndCertificate}"/>'>
    	</div>
    </div>
    
        <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">serial：</label>
    	<div class="col-sm-10">
    	<input type="text" name="serial" value='<c:out value="${commodity.serial}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">owner：</label>
    	<div class="col-sm-10">
    	<input type="text" name="owner" value='<c:out value="${commodity.owner}"/>'>
    	</div>
    </div> 
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">成本：</label>
    	<div class="col-sm-10">
    	<input type="text" name="cost" value="${commodity.cost}"> 請輸入數字!
    	</div>
    </div>
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">售價：</label>
    	<div class="col-sm-10">
    	<input type="text" name="sellPrice" value="${commodity.sellPrice}"> 請輸入數字!
    	</div>
    </div>      
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">販售平台：</label>
    	<div class="col-sm-10">
    	<input type="text" name="sellPlatform" value='<c:out value="${commodity.sellPlatform}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <c:choose>
          <c:when test="${commodity.isStored}"><input type="checkbox" name="isStored" value="true" checked="checked"></c:when>
          <c:otherwise><input type="checkbox" name="isStored" value="true"></c:otherwise>
          </c:choose>
                         是否仍在倉庫
        </label>
      </div>
    </div>
    </div>

	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-warning" >修改</button>
		</label>
	</div>
	</form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>
</body>
</html>