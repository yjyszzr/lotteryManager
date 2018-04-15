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
					
					<form action="purchasedetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="detail_id" id="detail_id" value="${pd.detail_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">生产单编码:</td>
								<td><input type="text" name="purchase_code" readonly="readonly" id="purchase_code" value="${pd.purchase_code}" maxlength="50" placeholder="这里输入生产单编码" title="生产单编码" style="width:98%;"/></td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">sku_id:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入sku_id" title="sku_id" style="width:98%;"/></td>
							</tr> --%>
							
									   <td style="width:75px;text-align: right;padding-top: 13px;">商品:</td>
									
									<td>
									<input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}" />
									<input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}"  onclick="selectPurchaseDetailSku();" maxlength="50" readonly="readonly" placeholder="请选择商品" style="width:98%;"/>
									</td>
									
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>
								<td><input type="number" name="quantity" id="quantity" value="${pd.quantity}" maxlength="32" placeholder="这里输入数量" title="数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位:</td>
								<td><input type="text" name="unit" id="unit" value="${pd.unit}" maxlength="50" placeholder="这里输入单位" title="单位" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">可发货数量:</td>
								<td><input type="number" name="total_send_quantity" id="total_send_quantity" value="${pd.total_send_quantity}" maxlength="32" placeholder="这里输入可发货数量" title="可发货数量" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">已发货数量:</td>
								<td><input type="number" name="total_sended_quantity" id="total_sended_quantity" value="${pd.total_sended_quantity}" maxlength="32" placeholder="这里输入已发货数量" title="已发货数量" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">良品数量:</td>
								<td><input type="number" name="good_quantity" id="good_quantity" value="${pd.good_quantity}" maxlength="32" placeholder="这里输入良品数量" title="良品数量" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }' >
								<td style="width:75px;text-align: right;padding-top: 13px;">不良品数量:</td>
								<td><input type="number" name="bad_quantity" id="bad_quantity" value="${pd.bad_quantity}" maxlength="32" placeholder="这里输入不良品数量" title="不良品数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单价:</td>
								<td><input type="text" name="price" id="price" value="${pd.price}" maxlength="20" placeholder="这里输入单价" title="单价" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">含税单价:</td>
								<td><input type="text" name="tax_price" id="tax_price" value="${pd.tax_price}" maxlength="20" placeholder="这里输入含税单价" title="含税单价" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">税率:</td>
								<td><input type="text" name="tax_rate" id="tax_rate" value="${pd.tax_rate}" maxlength="5" placeholder="这里输入税率" title="税率" style="width:98%;"/></td>
							</tr>
							<tr style='display:none'>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计到货时间:</td>
								<td><input class="span10 date-picker" name="pre_arrival_time" id="pre_arrival_time" value="${pd.pre_arrival_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预计到货时间" title="预计到货时间" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建人:</td>
								<td><input type="text" name="createby" id="createby" readonly="readonly"  value="${pd.createby}" maxlength="50" placeholder="这里输入创建人" title="创建人" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input class="span10 date-picker" name="create_time" readonly="readonly" id="create_time" value="${pd.create_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新人:</td>
								<td><input type="text" name="updateby" id="updateby" readonly="readonly" value="${pd.updateby}" maxlength="50" placeholder="这里输入更新人" title="更新人" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新时间:</td>
								<td><input class="span10 date-picker" name="update_time" readonly="readonly" id="update_time" value="${pd.update_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="更新时间" title="更新时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
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
			if($("#purchase_code").val()==""){
				$("#purchase_code").tips({
					side:3,
		            msg:'请输入生产单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入sku_id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#quantity").val()==""){
				$("#quantity").tips({
					side:3,
		            msg:'请输入数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
			return false;
			}
			if($("#unit").val()==""){
				$("#unit").tips({
					side:3,
		            msg:'请输入单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit").focus();
			return false;
			}
			if($("#total_send_quantity").val()==""){
				$("#total_send_quantity").tips({
					side:3,
		            msg:'请输入可发货数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#total_send_quantity").focus();
			return false;
			}
			if($("#total_sended_quantity").val()==""){
				$("#total_sended_quantity").tips({
					side:3,
		            msg:'请输入已发货数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#total_sended_quantity").focus();
			return false;
			}
			if($("#good_quantity").val()==""){
				$("#good_quantity").tips({
					side:3,
		            msg:'请输入良品数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
			return false;
			}
			if($("#bad_quantity").val()==""){
				$("#bad_quantity").tips({
					side:3,
		            msg:'请输入不良品数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bad_quantity").focus();
			return false;
			}
			if($("#price").val()==""){
				$("#price").tips({
					side:3,
		            msg:'请输入单价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#price").focus();
			return false;
			}
			if($("#tax_price").val()==""){
				$("#tax_price").tips({
					side:3,
		            msg:'请输入含税单价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tax_price").focus();
			return false;
			}
			if($("#tax_rate").val()==""){
				$("#tax_rate").tips({
					side:3,
		            msg:'请输入税率',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tax_rate").focus();
			return false;
			}
			if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
			if($("#pre_arrival_time").val()==""){
				$("#pre_arrival_time").tips({
					side:3,
		            msg:'请输入预计到货时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_arrival_time").focus();
			return false;
			}
			/* if($("#createby").val()==""){
				$("#createby").tips({
					side:3,
		            msg:'请输入创建人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#createby").focus();
			return false;
			}
			if($("#create_time").val()==""){
				$("#create_time").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#create_time").focus();
			return false;
			}
			if($("#updateby").val()==""){
				$("#updateby").tips({
					side:3,
		            msg:'请输入更新人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#updateby").focus();
			return false;
			}
			if($("#update_time").val()==""){
				$("#update_time").tips({
					side:3,
		            msg:'请输入更新时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#update_time").focus();
			return false;
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		//选择商品
		function selectPurchaseDetailSku(){
			 top.jzts();
			 selectPurchaseDetailSkuDiag = new top.Dialog();
			 selectPurchaseDetailSkuDiag.Drag=true;
			 selectPurchaseDetailSkuDiag.Title ="选择商品";
			 selectPurchaseDetailSkuDiag.URL = '<%=basePath%>goodssku/list.do';
			 selectPurchaseDetailSkuDiag.Width = 1250;
			 selectPurchaseDetailSkuDiag.Height = 755;
			 selectPurchaseDetailSkuDiag.Modal = true;				//有无遮罩窗口
			 selectPurchaseDetailSkuDiag. ShowMaxButton = true;	//最大化按钮
			 selectPurchaseDetailSkuDiag.ShowMinButton = true;		//最小化按钮
			 selectPurchaseDetailSkuDiag.CancelEvent = function(){ //关闭事件
				 selectPurchaseDetailSkuDiag.close();
			 };
			 selectPurchaseDetailSkuDiag.show();
			 
		}
		
		//关闭选择商品页面
		function closeSelectPurchaseDetailSkuDiag(Id,name){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			selectPurchaseDetailSkuDiag.close();
		}
		</script>
</body>
</html>