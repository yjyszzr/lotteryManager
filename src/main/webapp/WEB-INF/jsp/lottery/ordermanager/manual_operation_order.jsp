<%@page import="com.fh.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<!-- 检索  -->
						<form action="ordermanager/moOrder.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<th>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												订单编号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="订单编号" class="nav-search-input" id="order_sn" autocomplete="off" name="order_sn" value="${pd.order_sn }"   onkeyup="value=value.replace(/[^\d]/g,'')"  />
											</span>
										</div>
									</th>
										<th>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													订单状态:
												</span>
										 	<select  name="order_status" id="order_status" data-placeholder="请选择" value="${pd.order_status }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
													<option value="0" <c:if test="${pd.order_status!=NULL && pd.order_status!='' && pd.order_status == 0}">selected</c:if>>待付款</option>
													<option value="1" <c:if test="${pd.order_status == 1}">selected</c:if>>待出票</option>
													<option value="2" <c:if test="${pd.order_status == 2}">selected</c:if>>出票失败</option>
													<option value="3" <c:if test="${pd.order_status == 3}">selected</c:if>>待开奖</option>
													<option value="4" <c:if test="${pd.order_status == 4}">selected</c:if>>未中奖</option>
													<option value="5" <c:if test="${pd.order_status == 5}">selected</c:if>>已中奖</option>
													<option value="6" <c:if test="${pd.order_status == 6}">selected</c:if>>派奖中</option>
													<option value="7" <c:if test="${pd.order_status == 7}">selected</c:if>>审核中</option>
													<option value="8" <c:if test="${pd.order_status == 8}">selected</c:if>>支付失败</option>
										  	</select>
										  	</div>
									</th>
										<c:if test="${QX.cha == 1 }">
										<th style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:80px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
											</span>
											<span class="input-icon" style="width:43px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
											</span>
										</th>
									</c:if>
									<c:if test="${QX.toExcel == 1 }">
										<th style="vertical-align:top;padding-left:2px">
										<span class="input-icon" style="width:80px;"> </span>
											<span>
												<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"  style="border-radius:5px;color:blue !important; width:150px">导出到EXCEL </a>
											</span>
										</th>
									</c:if>
									</tr>
							</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover get_set_time" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">订单编号</th>
									<th class="center">用户名称</th>
									<th class="center">手机号</th>
									<th class="center">投注金额</th>
									<th class="center">中奖金额</th>
									<th class="center">购彩时间</th>
									<th class="center">支付倒计时</th>
									<th class="center">订单状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
<!-- 							开始循环	 -->
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'><a onclick="toDetail('${var.order_id}');" style=" cursor:pointer;">${var.order_sn}</a></td>
											<td class='center'>${var.user_name}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.ticket_amount}</td>
											<td class='center'>${var.winning_money}</td>
											<td class='center get_time'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center set_time'><span style="font-weight:bold;color:red">00:00:00</span></td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.order_status == 0}">待付款</c:when>
													<c:when test="${var.order_status == 1}">待出票</c:when>
													<c:when test="${var.order_status == 2}">出票失败</c:when>
													<c:when test="${var.order_status == 3}">待开奖</c:when>
													<c:when test="${var.order_status == 4}">未中奖</c:when>
													<c:when test="${var.order_status == 5}">已中奖</c:when>
													<c:when test="${var.order_status == 6}">派奖中</c:when>
													<c:when test="${var.order_status == 7}">审核中</c:when>
													<c:when test="${var.order_status == 8}"><lable style ="color:red">支付失败</lable></c:when>
													<c:when test="${var.order_status == 9}">已派奖</c:when>
												</c:choose>
											</td>
											<td class='center'>  
												<c:choose>
													<c:when test="${var.order_status == 0}">
														<c:choose>
															<c:when test="${var.pay_status == 0}">
																<a class="btn btn-xs btn-success" title="确认付款" style="border-radius: 5px;" onclick="operationOrder('${var.order_sn}',1,'${var.ticket_amount}');"> 确认付款</a>
																<a class="btn btn-xs btn-default" title="取消" style="border-radius: 5px;  color:#yellow" onclick="operationOrder('${var.order_sn}',2,'${var.ticket_amount}');"> 取消</a>
															</c:when>
															<c:otherwise>--</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test="${var.order_status == 5}">
														<a class="btn btn-xs btn-primary" title="派奖" style="border-radius: 5px;" onclick="operationOrder('${var.order_sn}',9,'${var.winning_money}');"> 派奖</a>
													</c:when>
													<c:when test="${var.order_status == 6}">
														<span>大额订单先审核</span>
													</c:when>
													<c:otherwise>--</c:otherwise>
												</c:choose>
											 </td>
										</tr>
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<th>
											<td colspan="100" class="center">您无权查看</td>
										</th>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%; ">
							<tr>
								<th style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></th>
							</tr>
						</table>
						</div>
						</form>
					
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="http://code.jquery.com/jquery-1.4.1.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
			function tosearch(status){
			if(status==0){
				$("#user_name").val("");
				$("#mobile").val("");
				$("#order_sn").val("");
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#amountStart").val("");
				$("#amountEnd").val("");
				$("#order_status").empty();
				$("#lottery_classify_id").empty();
			}
			top.jzts();
			$("#Form").submit();
		}
			//订单详情页
			function toDetail(orderId){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="订单详情";
				 diag.URL = '<%=basePath%>ordermanager/toDetail.do?order_id='+orderId;
				 diag.Width = 1300;
				 diag.Height = 320;
				 diag.Modal = true;				//有无遮罩窗口
				 diag. ShowMaxButton = false;	//最大化按钮
			     diag.ShowMinButton = false;		//最小化按钮
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 tosearch();
						 }else{
							 tosearch();
						 }
					}
					diag.close();
				 };
				 diag.show();
			}
			
			//删除或上架
			function operationOrder(Id,status,money){
				var str = "<h4  style='color:green'>温馨提示</h4><hr>&nbsp;&nbsp;&nbsp;";
				if(status==1 ){
					
// 					$.ajax({
// 						type: "POST",
<%-- 						url: '<%=basePath%>ordermanager/checkOrderStatus.do?order_sn='+Id, --%>
// 						dataType:'json',
// 						cache: false,
// 						success: function(data){
					 
// 						}
// 					});
					
// 					return;
						str+="请确认:&nbsp;订单号:"+Id+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;付款金额:<span style='font-weight:bold;color:red'>"+money+"</span>元"
					}else if(status==2 ){
// 					alert("该订单已取消!");
// 					return;
						str+="确定要取消编号为:"+Id+"的订单吗?"
					}else if(status==9 ){
// 					alert("该订单已派奖!");
// 					return;
						str+="请确认:&nbsp;订单号:"+Id+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中奖金额:<span style='font-weight:bold;color:red'>"+money+"</span>元"
					}else{
						return;
					}
				bootbox.confirm(str, function(result) {
					if(result) {
						top.jzts();
						var url = "<%=basePath%>ordermanager/updatePayStatus.do?id="+Id+"&pay_status="+status;
						$.get(url,function(data){
							tosearch();
						});
					}
				});
			}
			
			$(document).ready(function (){ 
			            function show() {  
							var getTime = $(".get_time");
			                for (var i = 0; i < getTime.length; i++) {  
						  	var timeStart =getTime[i].innerText.replace(/-/g,':').replace(/\s+|&nbsp;/ig, ':');
							timeStart = timeStart.split(':');
							var startTimeInt = new Date(timeStart[0],(timeStart[1]-1),timeStart[2],timeStart[3],timeStart[4],timeStart[5]).getTime();
							var currentTime = Date.parse(new Date());
		                   	var overtime = 20*60*1000 - (currentTime - startTimeInt)  ;  
		                    var fz = parseInt(overtime / 60000);  
								if(fz<10){
									fz = "0"+fz;
								}
		                    var mz = (overtime - (fz * 60000)) / 1000;  
								if(mz<10){
									mz = "0"+mz;
								}
		                    var str ='<span style="font-weight:bold;color:red">00:00:00</span>';  
		                    var str1 ='<span style="font-weight:bold;color:red">00:'+fz+':'+mz+'</span>';  
		                    if (overtime<= 0) {  
		                        $('.get_set_time tbody  tr').eq(i).find('td').eq(7).html( str); 
								var bjstr =$('.get_set_time tbody  tr').eq(i).find('td').eq(8).text() ;
								bjstr=Trim(bjstr,'g');
								if(bjstr!='已中奖'){
			                        $('.get_set_time tbody  tr').eq(i).find('td').eq(9).html( "--");  
								}
		                    }else {  
		                        $('.get_set_time tbody tr').eq(i).find('td').eq(7).html( str1);  
		                    }  
							var str1 =$.trim($('.get_set_time tbody  tr').eq(i).find('td').eq(9).text()) ;
							if (str1 == '--') {
		                        $('.get_set_time tbody  tr').eq(i).find('td').eq(7).html( str);  
							}
// 							console.log(i+"============================="+str);  
			                }  
			            }  
			            setInterval(show, 1000); // 注意函数名没有引号和括弧！  
			            // 使用setInterval("show()",3000);会报“缺少对象”  
			        });  
			
		 
		     function Trim(str,is_global)
	        {
	            var result;
	            result = str.replace(/(^\s+)|(\s+$)/g,"");
	            if(is_global.toLowerCase()=="g")
	            {
	                result = result.replace(/\s/g,"");
	             }
	            return result;
	}
			
			//导出excel
			function toExcel(){
// 				var lastStart = $("#lastStart").val();
// 				var lastEnd = $("#lastEnd").val();
// 				var orderStatus = $("#order_status").val();
// 				if(lastStart =="" && lastEnd == ""  && orderStatus == "" ){
					bootbox.confirm("<h4><strong>温馨提示</strong> </h4><hr><h5>&nbsp&nbsp默认导出今天的数据。</h5><br><h5>&nbsp&nbsp您可以按照<span style='color:red'>时间</span>筛选导出！</h5><br> &nbsp&nbsp<input id=\"selectionTime\"   type=\"text\" onfocus=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})\" readonly=\"readonly\" style=\"width:186px;border-radius:5px !important\" placeholder=\"时间筛选\"/><br><br>", function(result) {
						if(result) {
							var selectionTime = $("#selectionTime").val();
						    var items = selectionTime.split("-");
						    var newStr = items.join("");
							window.location.href='<%=basePath%>ordermanager/excelForMO.do?selectionTime='+newStr;
						}
					});
// 				}else{
<%-- 				window.location.href='<%=basePath%>ordermanager/excel.do?lastStart='+lastStart+'&lastEnd='+lastEnd+'&order_status='+orderStatus; --%>
// 				}
			}
			
	</script>
</body>
 
</html> 
 
