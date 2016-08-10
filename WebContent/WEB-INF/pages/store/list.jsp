<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>商家</title>
</head>
<body>

    <c:import url="/WEB-INF/pages/header.jsp"/><span style="display: inline-block; width: 100px"></span>
  	<script type="text/javascript">
    	$(function(){
    		<%--把checkbox清空--%>
    		$("input[name=commodityIds]:checked").prop("checked", false);
    		
    		<%--新增--%>
    		$("#create").click(function(){
    			location.href = "/jersey/store";
    		});
    		
    		<%--修改--%>
    		$("table").on("click", "button[name=update]", function(){
    			location.href = "/jersey/store/"+$(this).val();
    		});
    		
    		<%--刪除--%>
    		$("#delete").click(function() {
    			alertify.confirm("確定要刪除?", function(confirm){
    				if(confirm){
    					var checked = $("input[name=storeIds]:checked");
    					if (checked.size==0) {
    						return;
    					}
    					var storeIds = checked.map(function(){
    						return $(this).val();
    					}).get();

    					$.ajax("/jersey/store", {
    						type : "PUT",
    						data : JSON.stringify(storeIds),
    						contentType : "application/json",
    						dataType : "json",
    						success : function(data) {
    							if (data.result=="success") {
    								alertify.success("刪除成功");
    								checked.parent().parent().remove();
    							} else {
    								alertify.error("刪除失敗");
    							}
    						},
    						error : function(){
    							alertify.error("刪除失敗");
    						}
    					});
    				}
    			});
    		});

    		
    	});
    </script>
	<button id="create" type="button" class="btn btn-success" data-toggle="modal">新增</button>
	<button id="delete" type="button" class="btn btn-danger" data-toggle="modal">刪除</button>
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
		<button name="update" type="button" class="btn btn-warning" data-toggle="modal">修改</button>
  	  </td>
  	  <td>${vo.storeId} - <c:out value="${vo.name}" /></td>
  	  <c:if test="${vo.type=='store'}"><td>商店</td></c:if>
  	  <c:if test="${vo.type=='shippingCompany'}"><td>託運公司</td></c:if>
  	</tr>
  	</c:forEach>
  </table>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>