<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.fh.common.ScrapConstants" %>
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
					<form action="scrapdetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="scrap_detail_id" id="scrap_detail_id" value="${pd.scrap_detail_id}"/>
						<input type="hidden" name="status" id="status" value="${pd.status}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">报损编码:</td>
								<td><input readonly="readonly" type="text" name="scrap_code" id="scrap_code" value="${pd.scrap_code}" maxlength="32" placeholder="这里输入物料号" title="物料号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td>
								    <input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="50" readonly="readonly" placeholder="请选择商品" style="width:98%;" <c:if test="${msg == 'save' }"> onclick="selectPurchaseDetailSku( ${pd.store_id });"</c:if>/>
								    <input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}" />
								    <input type="hidden" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" />
								    <input type="hidden" name="spec" id="spec" value="${pd.spec}" />
								    <input type="hidden" name="unit" id="unit" value="${pd.unit}" />
								    <input type="hidden" name="batch_code" id="batch_code" value="${pd.batch_code}" />
								    <input type="hidden" name="quantity" id="quantity" value="${pd.quantity}" />
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">报损数量:</td>
								<td><input <c:if test="${msg == 'check' }">readonly="readonly"</c:if> type="number" name="scrap_quantity" id="scrap_quantity" value="${pd.scrap_quantity}" maxlength="32" placeholder="这里输入报损数量 " title="报损数量 " style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">报损说明:</td>
								<td><input <c:if test="${msg == 'check' }">readonly="readonly"</c:if> type="text" name="remark" id="remark" value="${pd.remark}" maxlength="500" placeholder="这里输入报损说明" title="报损说明" style="width:98%;"/></td>
							</tr>
							<c:if test="${msg == 'check' }">
							<input type="hidden" id="status" name="status" value="1"/>
								<%-- <tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">核准状态:</td>
									<td>
										<select id="status" name="status" required="required" style="width:98%;" >
											<option id="0" value="0" <c:if test="${pd.status==0 }">selected</c:if>>待审</option>
											<option id="1" value="1" <c:if test="${pd.status==1 }">selected</c:if>>核准完成</option>
										</select>
									</td>
								</tr> --%>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">核准数量:</td>
									<td><input <c:if test="${msg != 'check' }">readonly="readonly"</c:if> type="number"  min="0"  max="${pd.scrap_quantity}" name="audit_quantity" id="audit_quantity" value="${pd.audit_quantity}" maxlength="32" placeholder="这里输入核准数量" title="核准数量" style="width:98%;"/></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">处理建议类型:</td>
									<td>
									<select id="deal_suggest_type" name="deal_suggest_type" required="required" style="width:98%;" <c:if test="${msg != 'check' }">disabled="disabled"</c:if>>
											<option id="0" value="0" <c:if test="${pd.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_0 }">selected</c:if>>可用</option>
											<option id="1" value="1" <c:if test="${pd.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_1 }">selected</c:if>>不可用</option>
											<option id="2" value="2" <c:if test="${pd.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_2 }">selected</c:if>>其它</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">处理建议说明:</td>
									<td><input <c:if test="${msg != 'check' }">readonly="readonly"</c:if> type="text" name="deal_suggest_des" id="deal_suggest_des" value="${pd.deal_suggest_des}" maxlength="500" placeholder="这里输入处理建议说明" title="处理建议说明" style="width:98%;"/></td>
								</tr>
							</c:if>
							
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save(${msg == 'check' ? 1: 0});">保存</a>
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
		function save(flag){
		if(flag == 0){
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
			
			if($("#scrap_quantity").val()==""){
				$("#scrap_quantity").tips({
					side:3,
		            msg:'请输入报损数量 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#scrap_quantity").focus();
			return false;
			} else {
				var sq_val_ = $("#scrap_quantity").val();
				if(sq_val_ <= 0){
					$("#scrap_quantity").tips({
						side:3,
		            	msg:'报损数量不能为0 ',
		           		 bg:'#AE81FF',
		           	 	time:2
		       	 	});
					$("#scrap_quantity").focus();
					return false;
				}
			}
 		}else{
			if($("#audit_quantity").val()==""){
				$("#audit_quantity").tips({
					side:3,
		            msg:'请输入核准数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#audit_quantity").focus();
			return false;
			} else {
				var aq_ = $("#audit_quantity").val();
				var max_ = $("#audit_quantity").attr('max');
				var min_ = $("#audit_quantity").attr('min');
				if(parseInt(aq_) > parseInt(max_) || parseInt(aq_) < parseInt(min_)){
					$("#audit_quantity").tips({
						side:3,
		           		 msg:'核准数量小于'+max_+'且大于'+min_,
		            	bg:'#AE81FF',
		           		 time:2
		       		 });
					$("#audit_quantity").focus();
					return false;
				}
			}


		/*	if($("#remark").val()==""){
				$("#remark").tips({
					side:3,
		            msg:'请输入报损说明',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#remark").focus();
			return false;
			}
			
			if($("#deal_suggest_des").val()==""){
				$("#deal_suggest_des").tips({
					side:3,
		            msg:'请输入处理建议说明',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#deal_suggest_des").focus();
			return false;
			}  */
		}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectPurchaseDetailSkuDiag = null;
		var selectSkuDiag = null;
		
		//选择商品
		function selectPurchaseDetailSku(store_id){
			 top.jzts();
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
				var scrap_code = $('#scrap_code').val();		
				$.ajax({
						  type: 'POST',
						  url: '<%=basePath%>scrapdetail/preSave.do',
						  data: {'sku_id':Id,'batch_code':batch_code,'scrap_code':scrap_code},
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