<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.fh.common.DefetiveConstants" %>
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
					
					<input type="hidden" name="store_id" id="store_id" value="${pd.store_id }">
					<form action="defectivegoodsdetail/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="defective_detail_id" id="defective_detail_id" value="${pd.defective_detail_id}"/>
						<input type="hidden" name="status" id="status" value="${pd.status}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">不良品单编码:</td>
								<td><input readonly="readonly" type="text" name="defective_code" id="defective_code" value="${pd.defective_code}" maxlength="32" placeholder="这里输入物料号" title="物料号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td>
								    <input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="50" readonly="readonly" placeholder="请选择商品" style="width:82%;"/>
								    <input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}" />
								    <input type="hidden" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" />
								    <input type="hidden" name="spec" id="spec" value="${pd.spec}" />
								    <input type="hidden" name="unit" id="unit" value="${pd.unit}" />
								    <input type="hidden" name="batch_code" id="batch_code" value="${pd.batch_code}" />
								    <input type="hidden" name="quantity" id="quantity" value="${pd.quantity}" />
								    <a class="btn btn-mini btn-primary" onclick="selectPurchaseDetailSku( ${pd.store_id });">选择商品</a>
								    
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">不良品数量 :</td>
								<td><input  type="number" name="defective_quantity" id="defective_quantity" value="${pd.defective_quantity}" maxlength="32" placeholder="这里输入不良品数量 " title="不良品数量 " style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">不良品说明:</td>
								<td><input type="hidden" id="status" name="status" value="1"/>
								    <input  type="text" name="remark" id="remark" value="${pd.remark}" maxlength="500" placeholder="这里输入不良品说明" title="不良品说明" style="width:98%;"/></td>
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
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入物料号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入物料名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
			if($("#defective_quantity").val()==""){
				$("#defective_quantity").tips({
					side:3,
		            msg:'请输入不良品数量 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#defective_quantity").focus();
			return false;
			} 
		    var reg = /^[0-9]*[1-9][0-9]*$/;
            var str = $("#defective_quantity").val();
            //alert(reg.test(str));
            if(!reg.test(str)){
                $("#defective_quantity").tips({
					side:3,
		            msg:'不良品数量必须为大于0的整数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#defective_quantity").focus();
			    return false;
            }
            
            var quantity = $("#quantity").val();
            //alert(parseInt(quantity));
            //alert(parseInt(str));
            if(quantity!="" && parseInt(quantity)<parseInt(str)){
                $("#defective_quantity").tips({
					side:3,
		            msg:'不良品数量必须为小于库存数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#defective_quantity").focus();
			    return false;
            }
			if($("#remark").val()==""){
				$("#remark").tips({
					side:3,
		            msg:'请输入不良品说明 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#remark").focus();
			return false;
			} 
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectPurchaseDetailSkuDiag = null;
		var selectSkuDiag = null;
		
		//选择商品
		function selectPurchaseDetailSku(store_id){
			 selectPurchaseDetailSkuDiag = new top.Dialog();
			 selectPurchaseDetailSkuDiag.Drag=true;
			 selectPurchaseDetailSkuDiag.Title ="选择商品";
			 selectPurchaseDetailSkuDiag.URL = '<%=basePath%>goodssku/list2Scap.do?store_id='+store_id;
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
		function closeSelectPurchaseDetailSkuDiag(Id,name, encode,barcode,  unit, spec, isopenBatch,attrCate, taxRate,batch_code, quantity){
				var defective_code = $('#defective_code').val();		
				$.ajax({
						  type: 'POST',
						  url: '<%=basePath%>defectivegoodsdetail/preSave.do',
						  data: {'sku_id':Id,'batch_code':batch_code,'defective_code':defective_code},
 						  dataType:'json',
                          cache: false,
						  success: function(data){
							  if(data.msg=="success")
								{
									$("#sku_id").val(Id);
									$("#sku_name").val(name);
									$("#sku_encode").val(encode);
									$("#unit").val(unit);
									$("#spec").val(spec);
									$("#batch_code").val(batch_code);
									$("#quantity").val(quantity);
									selectPurchaseDetailSkuDiag.close();
								}
							else
								{
								  alert("该批次已选择，请另外选择！");
								  
								}
						  }
						}); 
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>