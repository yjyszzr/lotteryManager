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

	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<form action="ordermanager/moOrderQDD.do" method="post" name="Form" id="Form">
						 <input  type = "hidden" id="app_code_name"  value="10"/>
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<th>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												订单编号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="订单编号" style="width:265px;" class="nav-search-input" id="order_sn" autocomplete="off" name="order_sn" value="${pd.order_sn }"   onkeyup="value=value.replace(/[^\d]/g,'')"  />
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
													<option value="3" <c:if test="${pd.order_status == 3}">selected</c:if>>已付款</option>
													<option value="4" <c:if test="${pd.order_status == 4}">selected</c:if>>未中奖</option>
													<option value="5" <c:if test="${pd.order_status == 5}">selected</c:if>>已中奖</option>
													<option value="9" <c:if test="${pd.order_status == 9}">selected</c:if>>已派奖</option>
													<option value="10" <c:if test="${pd.order_status == 10}">selected</c:if>>已退款</option>
										  	</select>
										  	</div>
									</th>
									<th>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													出票状态:
												</span>
										 	<select  name="mo_status" id="mo_status" data-placeholder="请选择" value="${pd.mo_status }" style="width:154px;border-radius:5px !important"  >
													<option value="" selected="selected">全部</option>
													<option value="0" <c:if test="${pd.mo_status!=NULL && pd.mo_status!='' && pd.mo_status == 0}">selected</c:if>>待出票</option>
													<option value="1" <c:if test="${pd.mo_status == 1}">selected</c:if>>出票成功</option>
													<option value="2" <c:if test="${pd.mo_status == 2}">selected</c:if>>出票失败</option>
													<option value="4" <c:if test="${pd.mo_status == 4}">selected</c:if>>--- ---</option>
										  	</select>
										  	</div>
									</th>
										<th>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													彩种:
												</span>
										 	<select  name="lottery_classify_id" id="lottery_classify_id" data-placeholder="请选择" value="${pd.lottery_classify_id }" style="width:154px;border-radius:5px !important"  >
													<option value="" selected="selected">全部</option>
													<option value="1" <c:if test="${pd.lottery_classify_id == 1}">selected</c:if>>竞彩足球</option>
													<option value="2" <c:if test="${pd.lottery_classify_id == 2}">selected</c:if>>大乐透</option>
													<option value="3" <c:if test="${pd.lottery_classify_id == 3}">selected</c:if>>竞彩篮球</option>
										  	</select>
										  	</div>
									</th>
										</tr>
										<tr>
									<th  >
											<span class="input-icon" style="width:80px;text-align:right;">
													购彩时间:
												</span>
												<span  >
													<input name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="readonly" style="width:130px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
													<input name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="readonly" style="width:130px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
												</span>
										
											</th>
											<th>
										<div class="nav-search">
												<span class="input-icon" style="width:80px;text-align:right;">
													手机号:
												</span>
												<span class="input-icon">
													<input type="text" placeholder="手机号" style="width:154px;" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"   onkeyup="value=value.replace(/[^\d]/g,'')"  />
												</span>
											</div>
											<c:if test="${QX.cha == 1 }">
											<td >
												<span class="input-icon" style="width:80px;"> </span>
												<span>
														<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
												</span>
												<span class="input-icon" style="width:44px;"> </span>
												<span>
														<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
												</span>
												</td>
										</c:if>
										</th>
											
									
										<th style="vertical-align:top;padding-left:2px">
										<span class="input-icon" style="width:80px;"> </span>
											<span>
												<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"  style="border-radius:5px;color:blue !important; width:150px">导出到EXCEL </a>
											</span>
										</th>
									</tr>
									<tr>
<!-- 										<th> -->
<!-- 										<div class="nav-search"> -->
<!-- 											<span class="input-icon" style="width:80px;text-align:right;"> -->
<!-- 													平台来源: -->
<!-- 												</span> -->
<%-- 										 	<select  name="app_code_name" id="app_code_name" data-placeholder="请选择" value="${pd.app_code_name }" style="width:154px;border-radius:5px !important"  > --%>
<!-- 													<option value="" selected="selected">全部来源</option> -->
<%-- 													<option value="10" <c:if test="${pd.app_code_name == 10}">selected</c:if>>球多多</option> --%>
<%-- 													<option value="11" <c:if test="${pd.app_code_name == 11}">selected</c:if>>圣和APP</option> --%>
<!-- 										  	</select> -->
<!-- 										  	</div> -->
<!-- 									</th> -->
									</tr>
							</table>
					
						<table id="simple-table" class="table table-striped table-bordered table-hover get_set_time" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">订单编号</th>
									<th class="center">手机号</th>
									<th class="center">购买彩种</th>
									<th class="center">平台来源</th>
									<th class="center">支付方式</th>
									<th class="center">投注金额</th>
									<th class="center">中奖金额</th>
									<th class="center">购彩时间</th>
									<th class="center">支付倒计时</th>
									<th class="center">订单状态</th>
									<th class="center">店铺名称</th>
									<th class="center">手动出票状态</th>
									<th class="center">手动出票时间</th>
									<th class="center">代金卷金额</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
										<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.order_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
<%-- 											<td class='center'><a onclick="toDetail('${var.order_id}');" style=" cursor:pointer;">${var.order_sn}</a></td> --%>
											<td class='center'>${var.order_sn}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.lottery_name}</td>
											<td class='center'>
											<c:choose>
												<c:when test="${var.app_code_name==10}">球多多</c:when>
												<c:when test="${var.app_code_name==11}">圣和APP</c:when>
												<c:otherwise>球多多</c:otherwise>
											</c:choose>
											
											</td>
										
											<c:choose>
												<c:when test="${var.order_status == 0 }"><th class='center'>--</th></c:when>
												<c:otherwise>
													<th class='center'>
														<c:if test="${var.surplus !='0.00' }">
																余额：	${var.surplus }
														</c:if>
														<c:choose>
															<c:when test="${var.third_party_paid !='0.00' }">
																	<c:if test="${!empty var.pay_name }">
																		${var.pay_name}：${var.third_party_paid }
																	</c:if>
																	<c:if test="${empty var.pay_name }">
																		第三方：${var.third_party_paid }
																	</c:if>
															 </c:when>
															<c:otherwise>
																<c:if test="${var.surplus == '0.00' }">
																		模拟支付：${var.money_paid }
																</c:if>
															 </c:otherwise>
														</c:choose>
														<c:if test="${var.bonus !='0.00' }">
															代金券：	${var.bonus }
														</c:if>
													</th>
												</c:otherwise>
											</c:choose>
											<td class='center'>${var.ticket_amount}</td>
											<td class='center'>${var.winning_money}</td>
											<td class='center get_time'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center set_time'><span style="font-weight:bold;color:red">00:00:00</span></td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.order_status == 0}">待付款</c:when>
													<c:when test="${var.order_status == 1}">待出票</c:when>
													<c:when test="${var.order_status == 2}">出票失败</c:when>
													<c:when test="${var.order_status == 3}">已付款</c:when>
													<c:when test="${var.order_status == 4}">未中奖</c:when>
													<c:when test="${var.order_status == 5}">已中奖</c:when>
													<c:when test="${var.order_status == 6}">派奖中</c:when>
													<c:when test="${var.order_status == 7}">审核中</c:when>
													<c:when test="${var.order_status == 8}"><lable style ="color:red">支付失败</lable></c:when>
													<c:when test="${var.order_status == 9}">已派奖</c:when>
													<c:when test="${var.order_status == 10}">已退款</c:when>
												</c:choose>
											</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>   
												<c:choose>
															<c:when test="${var.order_status == 8}">--- ---</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${var.mo_status == 0}">待出票</c:when>
															<c:when test="${var.mo_status == 1}">出票成功</c:when>
															<c:when test="${var.mo_status == 2}">出票失败</c:when>
															<c:otherwise>--- ---</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											
											
											</td>
													<td class='center'>
																	<c:choose>
					 										<c:when test="${var.order_status == 8}">--- ---</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${var.mo_add_time  == null or var.mo_add_time == ''}">--- ---</c:when>
															<c:otherwise>${DateUtil.toSDFTime(var.mo_add_time*1000)}</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
														
													</td>
													
											<td class='center'> 
												${var.bonus}
											</td>		
											<td class='center'>  
											
												<c:choose>
													<c:when test="${var.order_status == 0 && var.pay_status == 0}"> 
														<c:choose>
															<c:when test="${var.app_code_name==10}">
																	<a class="btn btn-xs btn-success" title="确认付款" style="border-radius: 5px;" onclick="operationOrder('${var.order_sn}',1,'${var.ticket_amount}');"> 确认付款</a>
															</c:when>
														</c:choose>
													</c:when>
												</c:choose>
												<c:choose>  
													   <c:when test="${ var.surplus <= 0 && var.order_status == 5 }">
													   	<c:choose>
															<c:when test="${var.app_code_name==10}">
													   		<a class="btn btn-xs btn-primary" title="派奖" style="border-radius: 5px;" onclick="operationOrder('${var.order_sn}',9,'${var.winning_money}');"> 派奖</a> 
															</c:when>
														</c:choose>
													   </c:when>  
													   
											    </c:choose>
											    
												 <c:choose>
												 	<c:when test="${var.order_status == 4 && (var.mo_status == 0 || var.mo_status == 2)}">
															<a class="btn btn-xs btn-primary" title="退款" style="border-radius: 5px;" onclick="refundOperation('${var.order_sn}');"> 退款</a>
													</c:when>
													 
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
							<td style="vertical-align:top;">
								总计：昨日已付款票数：${pd.payNum }张，昨日已出票数：${pd.printNum }张。
								</td>
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
				$("#order_sn").val("");
				$("#lastEnd").val("");
				$("#lastStart").val("");
				$("#order_status").empty();
				$("#mo_status").empty();
				$("#mobile").val("");
				$("#app_code_name").empty();
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
				 diag.URL = '<%=basePath%>ordermanager/toDetail.do?order_id='+orderId+'&moStatus_type=1';
				 diag.Width = 1300;
				 diag.Height = 700;
				 diag.Modal = true;				//有无遮罩窗口
				 diag. ShowMaxButton = true;	//最大化按钮
			     diag.ShowMinButton = true;		//最小化按钮
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
						str+="请确认:&nbsp;订单号:"+Id+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;付款金额:<span style='font-weight:bold;color:red'>"+money+"</span>元";
					}else if(status==2 ){
// 					alert("该订单已取消!");
// 					return;
						str+="确定要取消编号为:"+Id+"的订单吗?";
					}else if(status==9 ){
// 					alert("该订单已派奖!");
// 					return;
						str+="请确认:&nbsp;订单号:"+Id+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中奖金额:<span style='font-weight:bold;color:red'>"+money+"</span>元";
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
			
			//退款
			function refundOperation(orderSn){
				var str = "<h4  style='color:green'>退款备注</h4><hr>&nbsp;&nbsp;&nbsp;";
			 
						str+="<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows='4'   id=\"refundRemark\"   type=\"text\"  style=\"width:450px;border-radius:5px !important\" placeholder=\"退款备注\"></textarea>";
				 
				bootbox.confirm(str, function(result) {
					if(result) {
						top.jzts();
						var refundRemark = $("#refundRemark").val();
						var url = "<%=basePath%>ordermanager/addRefundRemark.do?&order_sn="+orderSn+"&fail_msg="+refundRemark;
						$.get(url,function(data){
							tosearch();
						});
					}
				});
			}
			$(document).ready(function (){ show();});
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
		                        $('.get_set_time tbody  tr').eq(i).find('td').eq(9).html( str); 
								var bjstr =$('.get_set_time tbody  tr').eq(i).find('td').eq(11).text() ;
								bjstr=Trim(bjstr,'g');
// 									console.log("bjstr============================"+bjstr);  
// 									console.log("状态============================"+!((bjstr=='已中奖' ) || ( bjstr=='未中奖' )));  
								if(!((bjstr=='已中奖' ) || ( bjstr=='未中奖' ))){

			                        $('.get_set_time tbody  tr').eq(i).find('td').eq(14)
// 											.html( "--")
											;  

								}
		                    }else {  
		                        $('.get_set_time tbody tr').eq(i).find('td').eq(9).html( str1);  
		                    }  
							var str1 =$.trim($('.get_set_time tbody  tr').eq(i).find('td').eq(14).text()) ;
							if (str1 == '--') {
		                        $('.get_set_time tbody  tr').eq(i).find('td').eq(9).html( str);  
							}
// 							console.log(i+"============================="+str);  
			                }  
			            }  
			$(document).ready(function (){ 
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
				var orderSn = $("#order_sn").val();
				var moStatus = $("#mo_status").val();
				var orderStatus = $("#order_status").val();
				var lastStart = $("#lastStart").val();
				var lastEnd = $("#lastEnd").val();
				var app_code_name = $("#app_code_name").val();
				var lottery_classify_id = $("#lottery_classify_id").val();
				var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
				if(lastStart =='' && lastEnd =='' && orderSn =='' && moStatus == ''  && orderStatus == '' &&  str == '' && app_code_name == '' && lottery_classify_id == ''){
					alert("请选择要导出的数据。");
				}else{
				window.location.href='<%=basePath%>ordermanager/excelForMO.do?lastStart='+lastStart+'&lastEnd='+lastEnd+'&orderSn='+orderSn+'&moStatus='+moStatus+'&orderStatus='+orderStatus+'&idsStr='+str+'&app_code_name='+app_code_name+'&lottery_classify_id='+lottery_classify_id;
				}
			}
			
			
			$(function() {
				//复选框全选控制
				var active_class = 'active';
				$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).click( function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					$(this).closest('table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).attr('checked', true);
						else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).attr('checked', false);
					});
				});
			});	
	</script>
</body>
 
</html> 
 
