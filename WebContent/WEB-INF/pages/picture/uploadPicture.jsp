<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/jersey/lib/jquery-colorbox/colorbox.css" rel="stylesheet">
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
	<script src="/jersey/lib/jquery-colorbox/jquery.colorbox.js" type="text/javascript"></script>
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
		
		<%--刪除--%>
		$("#delete").click(function() {
			alertify.confirm("確定要刪除?", function(confirm){
				if(confirm){
					var checked = $("input[name=pictureIds]:checked");
					if (checked.size==0) {
						return;
					}
					var pictureIds = checked.map(function(){
						return $(this).val();
					}).get();

					$.ajax("/jersey/picture/${requestScope.commodity.commodityId}", {
						type : "PUT",
						data : JSON.stringify(pictureIds),
						success : function(data) {
							var result = $.parseJSON(data);
							if (result.result=="success") {
								alertify.success("刪除成功");
								checked.parent().parent().remove();
							} else {
								alertify.error(data.msg);
							}
						},
						error : function(){
							alertify.error("刪除失敗");
						}
					});
				}
			});
		});

		
		<%--下載圖片--%>
		$("#download").click(function(){
			var form = $(this).parent();
			form.attr("action", form.attr("action")+"/download").attr("method", "GET").submit();
		});
		
		<%--下載全部圖片--%>
		$("#downloadAll").click(function(){
			var form = $(this).parent();
			form.attr("action", form.attr("action")+"/downloadAll").attr("method", "GET").submit();
		});
		
		
	});
	</script>
	<br><br>
	
	<c:forEach items="${requestScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
    
  <table border=1 width="1500px" id="commodity" class="table table-hover">
    <thead>
    <tr>
      <th>商品編號/商品名稱</th>
      <th>成本</th>
      <th>售價</th>
      <th>是否仍在庫</th>
    </tr>
    </thead>
	<tr>
  	  <td><a href="/jersey/triple/commodity/${requestScope.commodity.commodityId}">${requestScope.commodity.commodityId} - <c:out value="${requestScope.commodity.itemName}" /></a>
  	  		<c:if test="${!empty requestScope.commodity.link}"><a href="${requestScope.commodity.link}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty requestScope.commodity.link}"></c:if></td>
  	  <td><c:out value="${requestScope.commodity.cost}" /></td>
  	  <td><c:out value="${requestScope.commodity.sellPrice}" /></td>
  	  <td>${requestScope.commodity.isStored ? '是':'否'}</td>
  	</tr>
  	</table>
  	<%-- 在url和input裡面都要放commodityId, 不然吃不到 --%>
	<form action="/jersey/picture/${requestScope.commodity.commodityId}/uploadPicture" method="POST" enctype="multipart/form-data" class="form-horizontal">
    <input type="hidden" name="commodityId" value="${requestScope.commodity.commodityId}">
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

	<button id="delete" type="button" class="btn btn-danger">刪除</button>
	<button id="download" type="button" class="btn btn-normal">下載</button>
	<button id="downloadAll" type="button" class="btn btn-normal">全部下載</button>
	<span>選擇圖片請在圖片上按滑鼠右鍵</span>
	
	<table id="pictures" class="table table-hover">
		<c:forEach items="${requestScope.pictureIds}" var="pictureId" varStatus="status">
			<c:if test="${status.index%4 == 0}"><tr></c:if>
			<td>
			<input type="checkbox" name="pictureIds" value="${pictureId}" id="${pictureId}" style="margin-left:200px"><br>
			<a href="/jersey/picture/${requestScope.commodity.commodityId}/getOne/${pictureId}" class="lightbox">
				<img src="/jersey/picture/${requestScope.commodity.commodityId}/getOne/${pictureId}" class="picture">
			</a>
			</td>
			<c:if test="${status.index%4 == 3}"></tr></c:if>
		</c:forEach>
	</table>
	
</body>
</html>