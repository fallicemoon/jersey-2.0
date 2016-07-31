<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="lib/jquery-colorbox/colorbox.css" rel="stylesheet">
<style type="text/css">
.picture {
	width: 60%;
	height: 60%;
}
#commodity { 
	width: 100% ; 
}
#pictures {
 border: 0px ;
 width: 100% ;
}
span {
	color: red ;
}
</style>
<title>上傳圖片</title>
</head>
<body>
	<c:import url="/WEB-INF/pages/header.jsp"/>
	<script src="lib/jquery-colorbox/jquery.colorbox.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		<%--彈出式照片--%>
		$(".lightbox").colorbox({rel:'lightbox', photo:true, width:"75%", height:"75%"});
		
		<%--點兩下選取照片--%>
		$("input[type='checkbox']").hide();
		$(".picture").contextmenu(function(e){
			e.preventDefault();
			var checkbox = $(this).parent().prevAll("input[type='checkbox']");
			if(!checkbox.prop("checked")){
				$(this).css("border", "solid 4px");
				checkbox.prop("checked", true);
			}else{
				$(this).css("border", "");
				checkbox.prop("checked", false);
			}
		});
		
		<%--刪除確認視窗--%>
		$("button[name='action'][value='delete']").click(function(){
			confirm("確認要刪除?");
		});
		
	});
	</script>
	<br><br>
	
	<c:forEach items="${requestScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
    
  <table id="commodity" class="table table-hover">
    <thead>
    <tr>
      <th>商品編號/商品名稱</th>
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
    </tr>
    </thead>
	<tr>
  	  <td><a href="/jersey/TripleServlet?action=commodity&commodityId=${requestScope.commodity.commodityId}">${requestScope.commodity.commodityId} - <c:out value="${requestScope.commodity.itemName}" /></a>
  	  		<c:if test="${!empty requestScope.commodity.link}"><a href="${requestScope.commodity.link}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty requestScope.commodity.link}"></c:if></td>
  	  <td><c:out value="${requestScope.commodity.qty}" /></td>
  	  <td><c:out value="${requestScope.commodity.player}" /></td>
  	  <td><c:out value="${requestScope.commodity.number}" /></td>
  	  <td><c:out value="${requestScope.commodity.season}" /></td>
  	  <td><c:out value="${requestScope.commodity.team}" /></td>
	  <td><c:out value="${requestScope.commodity.style}" /></td>
  	  <td><c:out value="${requestScope.commodity.color}" /></td>
  	  <td><c:out value="${requestScope.commodity.brand}" /></td>
  	  <td><c:out value="${requestScope.commodity.size}" /></td>
  	  <td><c:out value="${requestScope.commodity.level}" /></td>
  	  <td><c:out value="${requestScope.commodity.condition}" /></td>
  	  <td><c:out value="${requestScope.commodity.tag}" /></td>
  	  <td><c:out value="${requestScope.commodity.patchAndCertificate}" /></td>
  	  <td><c:out value="${requestScope.commodity.serial}" /></td>
  	  <td><c:out value="${requestScope.commodity.owner}" /></td>
  	  <td><c:out value="${requestScope.commodity.cost}" /></td>
  	  <td><c:out value="${requestScope.commodity.sellPrice}" /></td>
  	  <td><c:out value="${requestScope.commodity.sellPlatform}" /></td>
  	  <c:if test="${requestScope.commodity.isStored}"><td>是</td></c:if>
  	  <c:if test="${!requestScope.commodity.isStored}"><td>否</td></c:if>
  	</tr>
  	</table>
  	<%-- 在url和input裡面都要放commodityId, 不然吃不到 --%>
	<form action="/jersey/PictureServlet?commodityId=${param.commodityId}" method="POST" enctype="multipart/form-data" class="form-horizontal">
	<input type="hidden" name="commodityId" value="${param.commodityId}">
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">圖片(須為jpg / gif / png)：</label>
  		<div class="col-lg-6">
    		<div class="input-group">
      		<input type="file" class="form-control" placeholder="請選擇圖片" name="picture" multiple>檔案須小於30MB!
    		</div>
  		</div>
    </div>

	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-success">新增</button>
		</label>
	</div>
	</form>

	<form action="/jersey/PictureServlet" method="POST">
	<input type="hidden" name="commodityId" value="${param.commodityId}">
	<button type="submit" class="btn btn-danger" name="action" value="delete">刪除</button>
	<button type="submit" class="btn btn-normal" name="action" value="download">下載</button>
	<button type="submit" class="btn btn-normal" name="action" value="downloadAll">全部下載</button>
	<span>選擇圖片請在圖片上按滑鼠右鍵</span>
	
	<table id="pictures" class="table table-hover">
		<c:forEach items="${requestScope.pictureIds}" var="pictureId" varStatus="status">
			<c:if test="${status.index%4 == 0}"><tr></c:if>
			<td>
			<input type="checkbox" name="pictureId" value="${pictureId}" id="${pictureId}" style="margin-left:200px"><br>
			<a href="/jersey/PictureServlet?action=getPicture&pictureId=${pictureId}" class="lightbox">
				<img src="/jersey/PictureServlet?action=getPicture&pictureId=${pictureId}" class="picture">
			</a>
			</td>
			<c:if test="${status.index%4 == 3}"></tr></c:if>
		</c:forEach>
	</table>
	</form>
	
</body>
</html>