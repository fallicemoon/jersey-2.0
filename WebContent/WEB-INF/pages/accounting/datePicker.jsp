<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="/jersey/lib/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>請選擇時間</title>
</head>
<body>
	<c:import url="/WEB-INF/pages/header.jsp"/>
	<script type="text/javascript" src="/jersey/lib/moment-with-locales.min.js"></script>
	<script src="/jersey/lib/bootstrap/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript">
		function parseTimeToString (time) {
			var status = time.getHours()>11 ? 'PM':'AM';
			//月從0開始
			var month = time.getMonth()+1;
			if (month.toString().length==1) {
				month = "0"+month;
			}
			
			var date = time.getDate();
			if (date.toString().length==1) {
				date = "0"+date;
			}
			
			var hour = time.getHours();
			if (status=='PM' && hour!=12) {
				hour -= 12;
			}
			
			var minutes = time.getMinutes();
			if (minutes.toString().length==1) {
				minutes = "0"+minutes;
			}
			
			return month+"/"+date+"/"+time.getFullYear()
					+" "+hour+":"+minutes+" "+status;
		}
		
		$(function(){
			var now = new Date();
			$("#year").on("click", function(){
				//月從0開始
				var start = new Date(now.getFullYear(), 0, 1);
				$("input[name=start]").val(parseTimeToString(start));
				$("input[name=end]").val(parseTimeToString(now));
			})
			
			$("#month").on("click", function(){
				var start = new Date(now.getFullYear(), now.getMonth(), 1);
				$("input[name=start]").val(parseTimeToString(start));
				$("input[name=end]").val(parseTimeToString(now));
			})
			
			$("#week").on("click", function(){
				var start = new Date(now.getFullYear(), now.getMonth(), now.getDate()-now.getDay());
				$("input[name=start]").val(parseTimeToString(start));
				$("input[name=end]").val(parseTimeToString(now));
			})
			
			$("#day").on("click", function(){
				var start = new Date(now.getFullYear(), now.getMonth(), now.getDate());
				$("input[name=start]").val(parseTimeToString(start));
				$("input[name=end]").val(parseTimeToString(now));
			})
			
			<%--禁止手動輸入時間--%>
			$("input").on("keydown", function(){
				return false;
			})
		});
	</script>
	
	<br><br><br>
	<c:forEach items="${requestScope.errors}" var="error">
		<p style="color: red">${error}</p><br>
	</c:forEach>

	<div style="text-align: center;">
		<div class="btn-group">
			<button type="submit" id="year" class="btn btn-primary btn-lg" style="margin: 30px">本年</button>
		</div>
		<div class="btn-group">
			<button type="submit" id="month" class="btn btn-primary btn-lg" style="margin: 30px">本月</button>
		</div>
		<div class="btn-group">
			<button type="submit" id="week" class="btn btn-primary btn-lg" style="margin: 30px">本周</button>
		</div>
		<div class="btn-group">
			<button type="submit" id="day" class="btn btn-primary btn-lg" style="margin: 30px">本日</button>
		</div>
	</div>
	<br><br>

	<form action="/jersey/accounting" method="GET">
	<div style="length:100%; bottom:9px; text-align:center;">
	<div class="container">
	    <div class='col-md-5'>
	        <div class="form-group">
	            <div class='input-group date' id='datetimepicker6'>
	                <input type='text' class="form-control" name="start" value="${requestScope.start}"/>
	                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
	        </div>
	    </div>
	    <div class='col-md-5'>
	        <div class="form-group">
	            <div class='input-group date' id='datetimepicker7'>
	                <input type='text' class="form-control" name="end" value="${requestScope.end}"/>
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
  		<button type="submit" class="btn btn-primary btn-lg">計算利潤</button>
		</div>
	</div>
	</form>

</body>
</html>