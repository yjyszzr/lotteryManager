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
					
					<form action="activitybonus/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="bonus_id" id="bonus_id" value="${pd.bonus_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券金额：</label>
                                	</td>
                                	<td style="text-align: left;" colspan="10">
	                                	<div class="col-sm-4">
	                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="0元"    value="${pd.bonus_amount}"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width:204px;border-radius:5px !important"/>元
	                               		</div>
	                                <div class="col-sm-5">
	                                </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券条件：</label>
                                	</td>
                                	<td style="text-align: left;" colspan="10">
	                                		<label style="float:left;padding-left: 8px;padding-top:7px;">
												<input  name="min_amount" type="radio" <c:if test="${pd.min_amount==0}">checked="checked"</c:if>  checked   value = "0" class="ace" id="min_amount0" />
												<span class="lbl">无门槛优惠券</span>
											</label>
											<label style="float:left;padding-left: 5px;padding-top:7px;">
												<input name="min_amount" type="radio"  <c:if test="${pd.min_amount==1}">checked="checked"</c:if>  value = "1" class="ace" id="min_amount1" />
												<span class="lbl">满减优惠券</span>
											</label>
											<input type="text" id="min_goods_amount"  name="min_goods_amount" placeholder="最低消费金额"    value="${pd.min_goods_amount}"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width:100px;border-radius:5px !important;display:none;"/>
	                                <div class="col-sm-5">
	                                </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券数量：</label>
                                	</td>
                                	<td style="text-align: left;" colspan="10">
	                                		<label style="float:left;padding-left: 8px;padding-top:7px;">
												<input  name="bonus_number_type" type="radio" <c:if test="${pd.bonus_number_type==0}">checked="checked"</c:if> checked    value = "0" class="ace" id="bonus_number_type1" />
												<span class="lbl">无上限</span>
											</label>
											<label style="float:left;padding-left: 5px;padding-top:7px;">
												<input name="bonus_number_type" type="radio"  <c:if test="${pd.bonus_number_type==1}">checked="checked"</c:if>  value = "1" class="ace" id="bonus_number_type2" />
												<span class="lbl">有上限</span>
											</label>
											<input type="text" id="bonus_number"  name="bonus_number" placeholder="数量上限"    value="${pd.bonus_number}"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width:100px;border-radius:5px !important;display:none;"/>
	                                <div class="col-sm-5">
	                                </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券使用范围：</label>
                                	</td>
                                		<td style="text-align: left;" colspan="10">
	                                		<label style="float:left;padding-left: 8px;padding-top:7px;">
												<input  name="use_range" type="radio" <c:if test="${pd.use_range==0}">checked="checked"</c:if> checked  value = "0" class="ace" id="use_range" />
												<span class="lbl">通用</span>
											</label>
	                                	<div class="col-sm-5"> </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券类型：</label>
                                	</td>
                                		<td style="text-align: left;" colspan="10">
	                                		 <div class="col-sm-4">
									    <select  name="bonus_type" id="bonus_type" value="${pd.bonus_type}" style="width:204px;border-radius:5px !important">
									        <option value="1" <c:if test="${pd.bonus_type==1}">selected</c:if>>注册送红包</option>
									    </select>
									</div>
	                                	<div class="col-sm-5"> </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券生效日期：</label>
                                	</td>
                                	<td style="text-align: left;" colspan="10">
	                                	<div class="col-sm-2">
	                                  	 	领取之日起<input type="text" id="start_time"  name="start_time" placeholder="0天"    value="${pd.start_time}"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width:40px;border-radius:5px !important"/>天开始生效
	                               		</div>
	                                <div class="col-sm-5"> 
	                                </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" colspan="10">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">优惠券有效日期：</label>
                                	</td>
                                	<td style="text-align: left;" colspan="10">
	                                	<div class="col-sm-2">
	                                  	 	生效之日起<input type="text" id="end_time"  name="end_time" placeholder="0天"    value="${pd.end_time}"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width:40px;border-radius:5px !important"/>天后失效
	                               		</div>
	                                <div class="col-sm-5"> 
	                                </div>
	                                </td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="20">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
 
		$(document).ready(function() {
		    $('input[type=radio][name=min_amount]').change(function() {
		        if (this.value == '0') {
		        	$("#min_goods_amount").val('0');
		        	$("#min_goods_amount").hide();
		        }else if (this.value == '1') {
		        	$("#min_goods_amount").show();
		        } 
		    });
		    $('input[type=radio][name=bonus_number_type]').change(function() {
		        if (this.value == '0') {
		        	$("#bonus_number").val('0');
		        	$("#bonus_number").hide();
		        }else if (this.value == '1') {
		        	$("#bonus_number").show();
		        } 
		    });
			var bonusNumber ="${pd.bonus_number}";
			if(bonusNumber!=''){
				if(bonusNumber > 0 ){
			        	$("#bonus_number").show();
				}
			}
			var minGoodsAmount = '${pd.min_goods_amount}';
			if(minGoodsAmount!=''){
				if(minGoodsAmount > 0 ){
			        	$("#min_goods_amount").show();
				}
			}
		});
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>