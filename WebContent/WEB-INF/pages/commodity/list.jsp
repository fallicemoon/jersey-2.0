<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
.checkboxDiv {
	position: absolute;
	display: none;
	background-color: #00A800;
	border: solid;
	min-width: 90px;
}

</style>
<title>商品</title>
</head>
<body>
		
		<c:import url="/WEB-INF/pages/header.jsp" />
		<script type="text/javascript">
		
	function init () {
		<%--把checkbox清空--%>
		$("input[name=commodityIds]:checked").prop("checked", false);
		
		<%--生成篩選條件的下拉式選單內容, 上架和是否在庫寫死不用動態生成--%>
		$(".filter:not([name=commodityAuthority], [name=isStored])").each(function(){
			//生成空白的div塞到button後面
			var button = $(this);
			var div = $("<div>").addClass("checkboxDiv").addClass(button.attr("name"));
			button.after(div);
			//生成checkbox塞到div,先塞全選
			var all = $("<input>").attr("type","checkbox")
					.attr("name","all").prop("checked", true);
			div.append(all.clone()).append("全選<br>");
			var checkboxText = [];
			$("div."+button.attr("name")+":not(.checkboxDiv)").each(function(){
				if ($.inArray($.trim($(this).text()), checkboxText)==-1) {
					var checkbox = $("<input>").attr("type","checkbox")
					.attr("name",button.attr("name")).prop("checked", true).val($.trim($(this).text()));
					div.append(checkbox).append($.trim($(this).text())).append("<br>");
					checkboxText.push($.trim($(this).text()));
				}
			});
		});
		
		<%--上架和是否在庫的checkBox都勾起來--%>
		$(".filter[name=commodityAuthority], .filter[name=isStored]").each(function(){
			$(this).next().children().prop("checked", true);
		});
	}

	function filter() {
		$("tr").show();
		$("button.filter").each(function(){
			<%-- 從篩選條件中取得要保留的資料 --%>
			var checkboxDiv = $(this).next();
			var keep = checkboxDiv.find("input:checked").map(function(){
				return $(this).val();
			}).toArray();
			<%-- 只要有任何條件不符合就隱藏資料 --%>
			$("div."+$(this).attr("name")+":not(.checkboxDiv)").each(function(){
				if($.inArray($.trim($(this).text()), keep)==-1){
					$(this).closest("tr").hide();
				}
			});
		});
	}

	$(function(){
		init();
		<%--顯示下拉式篩選條件的按鈕們--%>
		$(".checkboxDiv").prev().click(function(){
			$(this).next().slideToggle("fast").css("padding-right","10px");
		});
		
		<%--全選按鈕, 弄掉的話為全不選 --%>
		$("tr:first").on("change", "input[name=all]", function(){
			var checked = $(this).prop("checked");
			$(this).siblings().each(function(){
				$(this).prop("checked", checked);
			});
		});
		
		<%--篩選條件發生變化時進行篩選--%>
		$("table tr:first").on("change", "[name!=commodityIds]", function(){
			filter();
		});
		
		<%--重置篩選條件--%>
		$("button[name=reset]").click(function(){
			$("tr").show();
			$("input[type=checkbox]:not([name=commodityIds])").prop("checked", true);
			$("div.checkboxDiv").slideUp();
		});
		
		<%--按背景網頁後把篩選條件收起來--%>
		$("html").on("click", function(e){
			if(!$(e.target).is("button.filter, .checkboxDiv, .checkboxDiv input")){
				$(".checkboxDiv").slideUp();
			}
		});
		
		
		
		<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
		<%--新增--%>
		$("#create").click(function(){
			location.href = "/jersey/commodity/${requestScope.commodityTypeId}";
		});
		
		<%--修改--%>
		$("table").on("click", "button[name=update]", function(){
			var button = $(this);
			//把篩選收起來
			$("div.checkboxDiv").hide();
			//有button的是上架按鈕跟圖片張數
			var updateTd = button.parent().nextAll().not(":has(button)");
			//hide
			var updateTdDiv = updateTd.find("div");
			var updateTdHref = updateTd.children("a");
			//show
			var updateTdInput = updateTd.children("input");
			var updateTdSelect = updateTd.children("select");
			if (button.hasClass("btn-warning")) {
				updateTdDiv.hide();
				updateTdHref.hide();
				updateTdSelect.show();
				updateTdInput.each(function(){
					//商品名稱跟超連結的屬性因為TD格式不同,另外處理
					if ($(this).attr("name")=="itemName") {
						$(this).attr("type", "text").val($(this).prev().children().text());
					} else if ($(this).attr("name")=="link") {
						$(this).attr("type", "text").val($(this).prev().children().attr("href"));
					} else {
						$(this).attr("type", "text").val($(this).prev().text());	
					}
				});
				updateTdInput.filter("[name=cost]").attr("type", "number");
				updateTdInput.filter("[name=sellPrice]").attr("type", "number");
				button.removeClass("btn-warning").addClass("btn-success").text("確認修改");
			} else if(button.hasClass("btn-success")){
				var body = {};
				var commodityAttr = {};
				//商品自定屬性
				$.each(updateTdInput.filter("[class=commodityAttrMapping]").serializeArray(), function(){
					commodityAttr[this.name] = this.value;
				});
				//商品資料
				$.each(updateTdInput.filter("[class!=commodityAttrMapping]").serializeArray(), function(){
					body[this.name] = this.value;
				});
				//select不知道為什麼不能serialize, 懶的研究
				body["isStored"] = updateTdSelect.eq(0).val();
				body["commodityAttr"] = commodityAttr;
				delete body["commodityIds"];
				
				var commodityId = button.closest("tr").find("input[name=commodityIds]").val();
				$.ajax("/jersey/commodity/"+commodityId, {
					type : "PUT",
					data : JSON.stringify(body),
					contentType : "application/json;charset=UTF-8",
					dataType : "json",
					success : function(data) {
						if (data.result=="success") {
							updateTdDiv.each(function(){
								//item的div外面有包一層超連結, 要另外處理
								if ($(this).hasClass("itemName")) {
									$(this).text($.trim($(this).parent().next().val()));
								} else if ($(this).hasClass("link")) {
									var link = $(this).next().val();
									$(this).children().attr("href", link);
									if ($.trim(link)) {
										$(this).children().show();
									} else {
										$(this).children().hide();
									}
								} else {
									$(this).text($.trim($(this).next().val()));	
								}
							});
							init();
							alertify.success("修改商品屬性成功");
						} else {
							alertify.error(data.msg);
						}
						updateTdHref.show();
						updateTdDiv.show();
						updateTdInput.attr("type", "hidden");
						updateTdSelect.hide();
						button.removeClass("btn-success").addClass("btn-warning").text("修改");		
					},
					error : function(){
						updateTdHref.show();
						updateTdDiv.show();
						updateTdInput.attr("type", "hidden");
						updateTdSelect.hide();
						button.removeClass("btn-success").addClass("btn-warning").text("修改");		
						alertify.error("修改商品屬性失敗");
					}
				});
		
			}
		});

		<%--商品上下架--%>
		$(".authority").click(function(){
			var button = $(this);
			var isUp;
			var body = {};
			var img = $("<img>").attr("src", "/jersey/pic/circle_load.gif");
			if(button.hasClass("btn-warning")){
				//未上架, 開始上架
				button.prop("disabled", true).text(" 上架中").prepend(img);
				body["authority"] = "customer";
				isUp = true;
			}else if(button.hasClass("btn-success")){
				//已上架, 開始下架
				button.prop("disabled", true).text(" 下架中").prepend(img);
				body["authority"] = "admin";
				isUp = false;
			}
			
			var commodityId = button.closest("tr").find("input[name=commodityIds]").val();
			var div = $("<div>").addClass("commodityAuthority");
			$.ajax("/jersey/commodity/switchStatus/"+commodityId, {
				type : "PUT",
				data : JSON.stringify(body),
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					button.prop("disabled", false).empty();
					if (data.result=="success") {
						if (isUp) {
							div.text("已上架");
							button.removeClass("btn-warning").addClass("btn-success");
						} else {
							div.text("未上架");
							button.removeClass("btn-success").addClass("btn-warning");
						}
					} else {
						if (isUp) {
							div.text("未上架");
						} else {
							div.text("已上架");
						}
						alertify.error(data.msg);
					}
					button.append(div);
					init();
				},
				error : function(){
					if (isUp) {
						div.text("未上架");
					} else {
						div.text("已上架");
					}
					button.prop("disabled", false).append(div);
					alertify.error("商品上下架切換失敗");
					init();
				}
			});
		});
		
		<%--刪除--%>
		$("#delete").click(function() {
			alertify.confirm("確定要刪除?", function(confirm){
				if(confirm){
					var checked = $("input[name=commodityIds]:checked");
					if (checked.size==0) {
						return;
					}
					var commodityIds = checked.map(function(){
						return $(this).val();
					}).get();

					$.ajax("/jersey/commodity", {
						type : "PUT",
						data : JSON.stringify(commodityIds),
						contentType : "application/json",
						dataType : "json",
						success : function(data) {
							if (data.result=="success") {
								alertify.success("刪除成功");
								checked.parent().parent().remove();
								init();
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
		
		<%--複製(ㄧ次只能複製一筆)--%>
		$("#clone").click(function(){
			var checked = $("input[name=commodityIds]:checked");
			if (checked.size()==0) {
				return;
			} else if (checked.size()!=1) {
				alertify.error("幹你媽的只能勾一筆啦");
				return;
			}
			var tr = checked.eq(0).parents("tr").clone();
			$.ajax("/jersey/commodity/clone", {
				type : "POST",
				data : checked.eq(0),
				success : function(data){
					if(data.result=="success"){
						tr.find("input").val(data.commodityId);
						$("table").append(tr);
						init();
						alertify.success("複製商品成功");
					} else {
						alertify.error(data.msg);
					}
				},
				error : function(){
					alertify.error("複製失敗");
				}
			});
		});
		</c:if>
		
		<%--標示出分頁標籤的當前分頁--%>
		var pagePos = location.search.indexOf("page=")+"page=".length;
		var page = location.search.substr(pagePos);
		if(location.search==""){
			$(".page-item").eq(0).addClass("active");
		}
		$(".page-item").each(function(){
			if($(this).val()==page) {
				$(this).addClass("active");
			}
		});

	});
		</script>
		<span style="display: inline-block; width: 100px"></span>
		<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
		<button id="create" type="button" class="btn btn-success" data-toggle="modal">新增</button>
		<button id="delete" type="button" class="btn btn-danger" data-toggle="modal">刪除</button>
		<button id="clone" type="button" class="btn btn-warning" data-toggle="modal">複製</button>
		</c:if>
		
		<table border=1 width="1500px" class="table table-hover">
			<thead>
				<tr>
						<th></th>
						<th>
							<button type="button" class="btn btn-info" name="reset" data-toggle="modal">重置篩選條件</button>
						</th>
						<th>圖片</th>
						<th>
							<button type="button" class="btn btn-info filter" name="itemName" data-toggle="modal">商品名稱</button>
						</th>
						
						<c:forEach items="${requestScope.commodityAttrList}" var="commodityAttrVO">
							<th>
								<button type="button" class="btn btn-info filter" name="${commodityAttrVO.commodityAttr}" data-toggle="modal">${commodityAttrVO.commodityAttr}</button>
							</th>
						</c:forEach>
						<th>售價</th>
					<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
						<th>成本</th>
						<th>
							<button type="button" class="btn btn-info filter" name="commodityAuthority" data-toggle="modal">銷售平台</button>
							<div class="commodityAuthority checkboxDiv">
								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/>
								<input type="checkbox" name="commodityAuthority" value="未上架" checked="checked">未上架&nbsp<br/>
								<input type="checkbox" name="commodityAuthority" value="已上架" checked="checked">已上架&nbsp<br/>
							</div>
						</th>
						<th>
							<button type="button" class="btn btn-info filter" name="isStored" data-toggle="modal">是否仍在庫</button>
							<div class="checkboxDiv">
								<input type="checkbox" name="all" checked="checked">全選&nbsp<br/>
								<input type="checkbox" name="isStored" value="是" checked="checked">是&nbsp<br/>
								<input type="checkbox" name="isStored" value="否" checked="checked">否&nbsp<br/>
							</div>
						</th>
					</c:if>
				</tr>
			</thead>

			<c:forEach items="${commodityList}" var="vo">
				<tr>
					<td><input type="checkbox" name="commodityIds" value="${vo.commodityId}"></td>
					<td>
						<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
						<button name="update" type="button" value="${vo.commodityId}" class="btn btn-warning">修改</button>
						</c:if>
					</td>
					<td>
						<a href="/jersey/picture/${vo.commodityId}">
							<button type="button" class="btn ${vo.pictureCount!=0 ? 'btn-success':'btn-danger'}" data-toggle="modal">${vo.pictureCount}</button>
						</a>
					</td>
					<td>
						<a href="/jersey/triple/commodity/${vo.commodityId}"><div class="itemName"><c:out value="${vo.itemName}" /></div></a>
						<input type="hidden" name="itemName" value="<c:out value="${vo.itemName}" />">
						
						<div class="link"><a href="${vo.link}" target="_blank" ${empty vo.link? 'style="display:none"':''}>連結</a></div>
						<input type="hidden" name="link" value="${vo.link}">
					</td>
					
					<c:forEach items="${vo.commodityAttrMappings}" var="commodityAttrMappingVO">
						<td>
							<div class="${commodityAttrMappingVO.commodityAttrVO.commodityAttr}"><c:out value="${commodityAttrMappingVO.commodityAttrValue}" /></div>
							<input type="hidden" class="commodityAttrMapping" name="${commodityAttrMappingVO.commodityAttrMappingId}" value="${commodityAttrMappingVO.commodityAttrValue}">
						</td>
					</c:forEach>
					<td>
						<div><c:out value="${vo.sellPrice}" /></div>
						<input type="hidden" name="sellPrice" value="${vo.sellPrice}">
					</td>
					
				<c:if test="${sessionScope['scopedTarget.userSession'].admin}">
					<td>
						<div><c:out value="${vo.cost}" /></div>
						<input type="hidden" name="cost" value="${vo.cost}">
					</td>
					<td>
						<c:choose>
							<c:when test="${vo.authority=='admin'}">
								<button class="btn btn-warning authority"><div class="commodityAuthority">未上架</div></button>
							</c:when>
							<c:when test="${vo.authority=='customer'}">
								<button class="btn btn-success authority"><div class="commodityAuthority">已上架</div></button>
							</c:when>
						</c:choose>
					</td>
					<td>
						<div class="isStored">${vo.isStored ? '是':'否'}</div>
						<select style="display:none">
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</td>
				</c:if>
				</tr>
			</c:forEach>
		</table>

	<div align="center">
		<ul class="pagination">
			<c:forEach begin="1" end="${requestScope.pages}" var="page">
				<li class="page-item" value="${page}"><a class="page-link" href="?page=${page}">${page}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>