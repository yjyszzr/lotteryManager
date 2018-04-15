<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="inboundnoticedetail/${msg}.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="notice_detail_id" id="notice_detail_id" value="${pd.notice_detail_id}"/>
						<input type="hidden" name="purchase_code" id="purchase_code" value="${pd.purchase_code}"/>
						<input type="hidden" name="purchase_detail_id" id="purchase_detail_id" value="${pd.purchase_detail_id}" />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">质检类型:</td>
								<td>
									<select id="type" name="type" required="required" >
										<option id="0" value="0" <c:if test="${pd.type==0 }">selected</c:if>>到货质检</option>
										<option id="1" value="1" <c:if test="${pd.type==1 }">selected</c:if>>生产质检</option>
										<%-- <option id="2" value="2" <c:if test="${pd.type==2 }">selected</c:if>>报损质检</option> --%>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预到货数量:</td>
								<td><input type="number" name="pre_arrival_quantity" id="pre_arrival_quantity" value="${pd.pre_arrival_quantity}"  maxlength="32" readonly="readonly" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">良品数量:</td>
								<td><input type="number" name="good_quantity" id="good_quantity" value="${pd.good_quantity}"  onchange="goodQuantityChange()"  maxlength="32" placeholder="这里输入入库数量" title="入库数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">不良品数量:</td>
								<td><input type="number" name="bad_quantity" id="bad_quantity" value="${pd.bad_quantity}"  maxlength="32" readonly="readonly" placeholder="这里输入不良品数量" title="不良品数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">不良品处理类型:</td>
								<td>
									<select id="bad_deal_type" name="bad_deal_type" required="required" >
										<option id="0" value="0" <c:if test="${pd.bad_deal_type==0 }">selected</c:if>>退货</option>
										<option id="1" value="1" <c:if test="${pd.bad_deal_type==1 }">selected</c:if>>入不良品</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">质检文件:</td>
								<td id="fileTd">
								<input type="file" name="file" id="file" value="${pd.url}" maxlength="500" placeholder="这里输入质检文件" title="质检文件" style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									<a class="btn btn-mini btn-danger" onclick="addfile();">+</a>
									<a class="btn btn-mini btn-danger" onclick="removefile();">-</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#good_quantity").val()=="" ){
				$("#good_quantity").tips({
					side:3,
		            msg:'请输入良品数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
			return false;
			}
			if($("#bad_quantity").val()=="" ){
				$("#bad_quantity").tips({
					side:3,
		            msg:'请输入不良品数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bad_quantity").focus();
			return false;
			}
			var gq_ = $("#good_quantity").val();
			var bq_ = $("#bad_quantity").val();
			var paq_ = $("#pre_arrival_quantity").val();
			if( parseInt(gq_)<0 || parseInt(gq_)>parseInt(paq_)){
				$("#good_quantity").tips({
					side:3,
		            msg:'良品数量不能大于'+paq_,
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
			return false;
			}
			
			if( (parseInt(gq_)+parseInt(bq_)) != parseInt(paq_)){
				$("#good_quantity").tips({
					side:3,
		            msg:'良品数量与不良品数量不等于预到货数量 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		function goodQuantityChange(){
			var gq_ = $("#good_quantity").val();
			var paq_ = $("#pre_arrival_quantity").val();
			if(parseInt(gq_)>parseInt(paq_)){
				$("#good_quantity").tips({
					side:3,
		            msg:'良品数量不能大于'+paq_,
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
			    return false;
			}
		if(parseInt(gq_)<0){
				$("#good_quantity").tips({
					side:3,
		            msg:'良品数量不能小于0',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
			    return false;
			}
			var bq_ = parseInt(paq_) -  parseInt(gq_);
			$("#bad_quantity").val(bq_);
		}

		function removefile(){
			var len = $('#fileTd input').length;
			if(len > 1){
				$('#fileTd input:last').remove();
			}
		}

		function addfile(){
			var len = $('#fileTd input').length;
			$('#fileTd').append('<input type="file" name="file'+len+'" style="width:98%;"/>');
		}

		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
        $(function(){
          $('#good_quantity').bind('input propertychange', function() {
           goodQuantityChange();
         });
         });
		</script>
</body>
</html>