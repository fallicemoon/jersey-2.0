<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="lib/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>請選擇時間</title>
</head>
<body>
	<c:import url="/WEB-INF/pages/header.jsp"/>
	<script type="text/javascript" src="lib/moment-with-locales.min.js"></script>
	<script src="lib/bootstrap/bootstrap-datetimepicker.min.js"></script>
	<br><br><br>
	<c:forEach items="${requestScope.errors}" var="error">
		<p style="color: red">${error}</p><br>
	</c:forEach>

	
	<form action="/jersey/AccountingServlet" method="POST">
	<div style="length:100%; bottom:9px; text-align:center;">
	<div class="container">
	    <div class='col-md-5'>
	        <div class="form-group">
	            <div class='input-group date' id='datetimepicker6'>
	                <input type='text' class="form-control" name="start" />
	                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
	        </div>
	    </div>
	    <div class='col-md-5'>
	        <div class="form-group">
	            <div class='input-group date' id='datetimepicker7'>
	                <input type='text' class="form-control" name="end"/>
	                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
	        </div>
	    </div>
	</div>
	</div>
	<script type="text/javascript">
	    $(function () {
	        $('#datetimepicker6').datetimepicker();
	        $('#datetimepicker7').datetimepicker();
	        $("#datetimepicker6").on("dp.change",function (e) {
	            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
	        });
	        $("#datetimepicker7").on("dp.change",function (e) {
	            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
	        });
	    });
	</script>
	
	<br><br><br><br>
	
	<div style="length:100%; bottom:9px; text-align:center;">
		<div>
  		<button type="submit" name="action" value="accounting" class="btn btn-primary btn-lg">計算利潤</button>
		</div>
	</div>
	</form>

</body>
</html>