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
					
					<form action="goods/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="goods_id" id="goods_id" value="${pd.goods_id}"/>
						<input type="hidden" name="DICTIONARIES_ID" id="DICTIONARIES_ID" value="${dictionaries.DICTIONARIES_ID}"/>
						<input type="hidden" name="pd_cat_id1" id="pd_cat_id1" value="${pd.cat_id1}"/>
						<input type="hidden" name="pd_cat_id2" id="pd_cat_id2" value="${pd.cat_id2}"/>
						<input type="hidden" name="pd_cat_id3" id="pd_cat_id3" value="${pd.cat_id3}"/>
						<input type="hidden" name="msg" id="msg" value="${msg}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td colspan="2"><input  type="text" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" maxlength="100" placeholder="这里输入商品编码" onblur="hasencode()" title="商品编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品条码:</td>
								<td colspan="2"><input  type="text" name="goods_barcode" id="goods_barcode" value="${pd.goods_barcode}" maxlength="13" placeholder="这里输入商品条码" onblur="hasbarcode()" title="商品条码" style="width:98%;"/></td>
							</tr>
							<tr >
							    <td style="width:75px;text-align: right;padding-top: 13px;">商品分类:</td>
								<td style="style="width:10px;" colspan="3">
								 	<select id="cat_id1" name="cat_id1" value="${pd.cat_id1}" onchange="change2(this.value)" style="width:32%;">
                      				    <c:if test="${not empty pd.cat_id1s}">
											<c:forEach items="${pd.cat_id1s}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${'selected'==var.selected}">
													    <option selected="selected" value="${var.DICTIONARIES_ID}">${var.NAME}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
                      				</select>
								 	<select id="cat_id2" name="cat_id2" value="${pd.cat_id2}" onchange="change3(this.value)" style="width:32%;" >
                      				    <c:if test="${not empty pd.cat_id2s}">
											<c:forEach items="${pd.cat_id2s}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${'selected'==var.selected}">
													    <option selected="selected" value="${var.DICTIONARIES_ID}">${var.NAME}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
                      				</select>
									<select id="cat_id3" name="cat_id3" value="${pd.cat_id3}" style="width:32%;" >
										<c:if test="${not empty pd.cat_id3s}">
											<c:forEach items="${pd.cat_id3s}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${'selected'==var.selected}">
													    <option selected="selected" value="${var.DICTIONARIES_ID}">${var.NAME}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">建议零售价:</td>
								<td><input  type="number" name="goods_price" id="goods_price" value="${pd.goods_price}" maxlength="100"  placeholder="这里输入建议零售价" title="建议零售价" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td><input type="text" name="goods_name" id="goods_name" value="${pd.goods_name}" maxlength="100" placeholder="这里输入商品名称" title="商品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品单位:</td>
								<td>
									<select name="unit_id" id="unit_id" style="width:98%;">
										<c:forEach items="${utils}" var="unit">
											<option id="${unit.unit_id}" value="${unit.unit_id }" <c:if test="${pd.unit_id == unit.unit_id }">selected</c:if>>${unit.unit_name }</option>
										</c:forEach>
									</select>
								    <input type="hidden" name="unit_name" id="unit_name" value="${pd.unit_name}" />
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商:</td>
								<td>
									<input type="hidden" name="supplier_id" id="supplier_id" value="${pd.supplier_id}"/> 
									<input type="text" name="supplier_name" id="supplier_name" readonly="readonly" value="${pd.supplier_name}" onclick="selectSupplierMethod();" maxlength="20" placeholder="这里输入供应商" title="供应商" style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否批次管理:</td>
								<td>
									<select id="isopen_batch" name="isopen_batch" style="width:98%;">
										<option id="0" value="0" <c:if test="${pd.isopen_batch == 0 }">selected</c:if>>否</option>
										<option id="1" value="1" <c:if test="${pd.isopen_batch == 1 }">selected</c:if> >是</option>
									</select>
								</td>	
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格:</td>
								<td><input type="text" name="spec" id="spec" value="${pd.spec}" maxlength="100" placeholder="这里输入规格" title="规格" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">属性分类:</td>
								<td>
									<select id="attr_cate" name="attr_cate" style="width:98%;">
										<option id="0" value="0" <c:if test="${pd.attr_cate == 0 }">selected</c:if>>原材料</option>
										<option id="1" value="1" <c:if test="${pd.attr_cate == 1 }">selected</c:if> >包材</option>
										<option id="2" value="2" <c:if test="${pd.attr_cate == 2 }">selected</c:if>>商品</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">税率:</td>
								<td><input type="number" name="tax_rate" id="tax_rate" value="${pd.tax_rate}" onblur="this.value=this.value.replace(/[^\d\.]/g, '')" maxlength="32" placeholder="这里输入税率" title="税率" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">重量(kg):</td>
								<td><input type="number" name="weight" id="weight" value="${pd.weight}"  maxlength="32" placeholder="这里输入重量 默认0.000" title="重量" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">保质期（天）:</td>
								<td><input type="number" name="quality_period" id="quality_period" onblur="this.value=this.value.replace(/\D/g, '')" value="${pd.quality_period}" maxlength="11" placeholder="这里输入保质期" title="保质期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否预售:</td>
								<td>
									<select name="is_pre_sales" id="is_pre_sales" style="width:98%;">
											<option  value="0" <c:if test="${pd.is_pre_sales ==0 }">selected</c:if>>非预售商品</option>
											<option  value="1" <c:if test="${pd.is_pre_sales == 1 }">selected</c:if>>预售商品</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">经营类型:</td>
								<td>
									<select name="is_self_goods" id="is_self_goods" style="width:98%;">
											<option  value="0" <c:if test="${pd.is_self_goods ==0 }">selected</c:if>>自营商品</option>
											<option  value="1" <c:if test="${pd.is_self_goods == 1 }">selected</c:if>>非自营商品</option>
									        <option  value="2" <c:if test="${pd.is_self_goods == 2 }">selected</c:if>>联营商品</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">是否组合商品:</td>
								<td>
									<select name="is_combination_goods" id="is_combination_goods" style="width:98%;">
											<option  value="0" <c:if test="${pd.is_combination_goods ==0 }">selected</c:if>>非组合</option>
											<option  value="1" <c:if test="${pd.is_combination_goods ==1 }">selected</c:if>>组合</option>
									</select>
								</td>
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


	<!-- 页面底部-->
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
            if($("#sku_encode").val()==""){
				$("#sku_encode").tips({
					side:3,
		            msg:'请输入商品编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_encode").focus();
			return false;
			}
            
			if($("#goods_barcode").val()=="" || $("#goods_barcode").val().length != 13){
				$("#goods_barcode").tips({
					side:3,
		            msg:'请输入13位有效商品条码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_barcode").focus();
			return false;
			}
			if($("#cat_id3").val()==""){
				$("#cat_id3").tips({
					side:3,
		            msg:'请选择商品分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cat_id3").focus();
			return false;
			}
			if($("#goods_price").val()==""){
				$("#goods_price").tips({
					side:3,
		            msg:'请输入建议零售价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_price").focus();
			return false;
			}
			if(parseInt($("#goods_price").val()) <= 0){
				$("#goods_price").tips({
					side:3,
		            msg:'建议零售价不能小于0！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_price").focus();
			return false;
			}
			if($("#goods_name").val()==""){
				$("#goods_name").tips({
					side:3,
		            msg:'请输入商品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_name").focus();
			return false;
			}
            if($("#supplier_name").val()==""){
				$("#supplier_name").tips({
					side:3,
		            msg:'请输入供应商',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_name").focus();
			return false;
			}
			if($("#quality_period").val()==""){
				$("#quality_period").tips({
					side:3,
		            msg:'请输入保质期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quality_period").focus();
			return false;
			}
			if(parseInt($("#quality_period").val()) < 0){
				$("#quality_period").tips({
					side:3,
		            msg:'保质期不能小于0！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quality_period").focus();
			return false;
			}
			if($("#goods_price").val()==""){
				$("#goods_price").tips({
					side:3,
		            msg:'请输入商品单价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_name").focus();
			return false;
			}
			if($("#cat_id").val()==""){
				$("#cat_id").tips({
					side:3,
		            msg:'请输入商品分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cat_id").focus();
			return false;
			}
			if($("#unit_id").val()==""){
				$("#unit_id").tips({
					side:3,
		            msg:'请输入商品单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit_id").focus();
			return false;
			}
			
			if($("#isopen_batch").val()==""){
				$("#isopen_batch").tips({
					side:3,
		            msg:'请输入是否批次管理',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#isopen_batch").focus();
			return false;
			}
			if($("#spec").val()==""){
				$("#spec").tips({
					side:3,
		            msg:'请输入规格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#spec").focus();
			return false;
			}
			if($("#attr_cate").val()==""){
				$("#attr_cate").tips({
					side:3,
		            msg:'请输入属性分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#attr_cate").focus();
			return false;
			}
			if($("#tax_rate").val()==""){
				$("#tax_rate").tips({
					side:3,
		            msg:'请输入税率  格式 0.00',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tax_rate").focus();
			return false;
			}
            var reg = /^0{1}\.{1}(\d{2})$/;
            var str = $("#tax_rate").val();
            //alert(reg.test(str));
            if(!reg.test(str)){
                $("#tax_rate").tips({
					side:3,
		            msg:'请输入税率  税率范围在0.00到0.99之间  格式 0.00',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tax_rate").focus();
			    return false;
            }
			var unit_name = $("#unit_id option:selected").text();  
			$('#unit_name').val(unit_name);
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectSupplier = null;
		//选择供应商
		function selectSupplierMethod(){
			 top.jzts();
			 selectSupplier = new top.Dialog();
			 selectSupplier.Drag=true;
			 selectSupplier.Title ="选择供应商";
			 selectSupplier.URL = '<%=basePath%>supplier/listForSelect.do';
			 selectSupplier.Width = 1250;
			 selectSupplier.Height = 755;
			 selectSupplier.Modal = true;				//有无遮罩窗口
			 selectSupplier. ShowMaxButton = true;	//最大化按钮
			 selectSupplier.ShowMinButton = true;		//最小化按钮
			 selectSupplier.CancelEvent = function(){ //关闭事件
				 selectSupplier.close();
			 };
			 selectSupplier.show();
			 
		}
        function hasencode(){
             var sku_encode = $.trim($("#sku_encode").val());
             var goods_id = $.trim($("#goods_id").val()); 
               $.ajax({
                     type: "POST",
                     url: '<%=basePath%>goods/hasencode.do?sku_encode='+sku_encode+'&goods_id='+goods_id,
                     
                     dataType:'json',
                     cache: false,
                     success: function(data){
                      if("success" != data.result){
                              $("#sku_encode").tips({
                                 side:3,
                                 msg:'商品编码['+sku_encode+'] 不能重复',
                                 bg:'#AE81FF',
                                 time:3
                              });
                     $("#sku_encode").val('');
                    }
                 }
            });
        }
       
        function hasbarcode(){
             var goods_barcode = $.trim($("#goods_barcode").val());
             var goods_id = $.trim($("#goods_id").val()); 
               $.ajax({
                     type: "POST",
                     url: '<%=basePath%>goods/hasbarcode.do?goods_barcode='+goods_barcode+'&goods_id='+goods_id,
                     
                     dataType:'json',
                     cache: false,
                     success: function(data){
                      if("success" != data.result){
                              $("#goods_barcode").tips({
                                 side:3,
                                 msg:'商品条码['+goods_barcode+'] 不能重复',
                                 bg:'#AE81FF',
                                 time:3
                              });
                     $("#goods_barcode").val('');
                    }
                 }
            });
        }
		//第一级值改变事件(初始第二级)
		function change1(value){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>linkage/getLevels.do?tm='+new Date().getTime(),
		    	data: {DICTIONARIES_ID:value},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#cat_id1").html("<option value=''>请选择</option>");
					$("#cat_id2").html("<option value=''>请选择</option>");
					$("#cat_id3").html("<option value=''>请选择</option>");
					 $.each(data.list, function(i, dvar){
							$("#cat_id1").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
					 });
				}
			});
		}
		
		//第二级值改变事件(初始第三级)
		function change2(value){
			if(null == value || value.length <= 0){
				 $("#cat_id2").removeAttr("disabled");
				 $("#cat_id3").removeAttr("disabled");
				 $("#cat_id2").html("<option value=''>请选择</option>");
				 $("#cat_id3").html("<option value=''>请选择</option>");
			}else{
				$.ajax({
					type: "POST",
					url: '<%=basePath%>linkage/getLevels.do?tm='+new Date().getTime(),
			    	data: {DICTIONARIES_ID:value},
					dataType:'json',
					cache: false,
					success: function(data){
					    $("#cat_id2").removeAttr("disabled");
						$("#cat_id2").html("<option value=''>请选择</option>");
						$("#cat_id3").html("<option value=''>请选择</option>");
						 $.each(data.list, function(i, dvar){
								$("#cat_id2").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
						 });
					}
				});
			}
		}
		
		//第三级值改变事件(初始第四级)
		function change3(value){
			if(null == value || value.length <= 0){
				 $("#cat_id3").removeAttr("disabled");
				 $("#cat_id3").html("<option value=''>请选择</option>");
			}else{
				$.ajax({
					type: "POST",
					url: '<%=basePath%>linkage/getLevels.do?tm='+new Date().getTime(),
			    	data: {DICTIONARIES_ID:value},
					dataType:'json',
					cache: false,
					success: function(data){
					    $("#cat_id3").removeAttr("disabled");
						$("#cat_id3").html("<option value=''>请选择</option>");
						 $.each(data.list, function(i, dvar){
								$("#cat_id3").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
						 });
					}
				});
			}
		}
		
		//关闭选择商品页面
		function closeselectSupplier(supplier_sn,supplier_name,supplier_id ){
			$("#supplier_id").val(supplier_id);
			$("#supplier_name").val(supplier_name);
			selectSupplier.close();
			selectSupplier = null;
		}

		$(function() {
		    if($("#msg").val() == "save"){
		    	change1($("#DICTIONARIES_ID").val());
		    }
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>