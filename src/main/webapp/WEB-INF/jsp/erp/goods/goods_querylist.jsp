<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
							
						<!-- 检索  -->
						<form action="goods/querylist.do" method="post" name="Form" id="Form">
						<input type="hidden" name="DICTIONARIES_ID" id="DICTIONARIES_ID" value="${dictionaries.DICTIONARIES_ID}"/>
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									物料编码：
											<input type="text" placeholder="这里输入物料编码" class="nav-search-input" id="sku_encode" autocomplete="off" name="sku_encode" value="${pd.sku_encode}" placeholder="这里输入物料编码"/>
								</td>
								<!--
								<td style="padding-left:10px;">
								            物料条码：
											<input type="text" placeholder="这里输入物料条码" class="nav-search-input" id="sku_barcode" autocomplete="off" name="sku_barcode" value="${pd.sku_barcode }" placeholder="这里输入物料条码"/>
								</td>
								-->
								<td class="pgt-query-form-td">
								            物料名称：
											<input type="text" placeholder="这里输入物料名称" class="nav-search-input" id="sku_name" autocomplete="off" name="sku_name" value="${pd.sku_name }" placeholder="这里输入物料名称"/>
								</td>
								<td class="pgt-query-form-td">
								            属性分类：
								 	<select name="attr_cate" id="attr_cate" data-placeholder="请选择属性分类">
										<option value="">请选择属性分类</option>
										<option value="0" ${pd.attr_cate=="0"?'selected':''}>原材料</option>
										<option value="1" ${pd.attr_cate=="1"?'selected':''}>包材</option>
										<option value="2" ${pd.attr_cate=="2"?'selected':''}>商品</option>
								  	</select>
								</td>
								<td class="pgt-query-form-td">
								           是否预售：
								 	<select name="is_pre_sales" id="is_pre_sales" data-placeholder="请选择是否预售">
										<option value="">请选择是否预售</option>
										<option value="0" ${pd.is_pre_sales=="0"?'selected':''}>非预售商品</option>
										<option value="1" ${pd.is_pre_sales=="1"?'selected':''}>预售商品</option>
								  	</select>
								</td>
								<td class="pgt-query-form-td">
								            经营方式：
								 	<select style="width:158px;" name="is_self_goods" id="is_self_goods" data-placeholder="请选择经营方式">
										<option value="">请选择经营方式</option>
										<option value="0" ${pd.is_self_goods=="0"?'selected':''}>自营商品</option>
										<option value="1" ${pd.is_self_goods=="1"?'selected':''}>非自营商品</option>
										<option value="2" ${pd.is_self_goods=="2"?'selected':''}>联营商品</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">清空</a></td>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- <th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">物料编码</th>
									<th class="center">物料条码</th>
									<th class="center">物料名称</th>
									<th class="center">单位</th>
									<th class="center">规格</th>
									<th class="center">重量(kg)</th>
									<th class="center">税率</th>
									<th class="center">属性分类</th>
									<th class="center">是否预售</th>
									<th class="center">经营方式</th>
									<th class="center">操作</th>
									<!-- <th class="center">供应商</th> -->
									<!-- <th class="center">添加时间</th> -->
									<!-- <th class="center">是否批次管理</th>
									<th class="center">是否组合商品</th>
									<th class="center">查看组合商品</th>
									<th class="center">同步商城</th> -->
									
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<%-- <td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.goods_id}" class="ace" /><span class="lbl"></span></label>
											</td> --%>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											
											<td class='center'>${var.sku_encode}</td>
											<td class='center'>${var.goods_barcode}</td>
											<td class='center'>${var.goods_name}</td>
											<td class='center'>${var.unit_name}</td>
											<td class='center'>${var.spec}</td>
											<td class='center'>${var.weight}</td>
											<td class='center'>${var.tax_rate}</td>
											<td class='center'>
												<c:if test="${var.attr_cate == 0}">原材料</c:if>
												<c:if test="${var.attr_cate == 1}">包材</c:if>
												<c:if test="${var.attr_cate == 2}">商品</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.is_pre_sales == 0}">非预售商品</c:if>
												<c:if test="${var.is_pre_sales != 0}">预售商品</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.is_self_goods == 0}">自营商品</c:if>
												<c:if test="${var.is_self_goods == 1}">非自营商品</c:if>
												<c:if test="${var.is_self_goods == 2}">联营商品</c:if>
											</td>
											<td class='center'>
												<a class="btn btn-xs btn-success" title="库存详情" onclick="stockinfo('${var}');">库存详情</a>
											</td>
											<%-- <td class='center'>${var.supplier_id}</td> --%>
											<%-- <td class='center'>${var.add_time}</td> --%>
											<%-- <td class='center'>
												<c:if test="${var.isopen_batch == 0}">否</c:if>
												<c:if test="${var.isopen_batch == 1}">是</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.is_combination_goods == 0}">非组合</c:if>
												<c:if test="${var.is_combination_goods != 0}">组合</c:if>
											</td>
											<td class='center'>
													<c:if test="${var.is_combination_goods != 0}">
														<a class="btn btn-xs btn-success" title="组合" onclick="combination('${var.parent_sku_id}');">组合</a>
													</c:if>
													<c:if test="${var.is_combination_goods == 0}">
														<a class="btn btn-xs btn-success" disabled = "ture" title="组合" onclick="combination('${var.parent_sku_id}');">组合</a>
													</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.is_syn == 1}">已同步</c:if>
												<c:if test="${var.is_syn != 1}">未同步</c:if>
												
											</td> --%>
											
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
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
						<table style="width:100%;">
							<tr>
								
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			
			change1();
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		
		
		
		//第一级值改变事件(初始第二级)
		function change1(){
			var value = $("#DICTIONARIES_ID").val();
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
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>goods/excel.do';
		}
		function combination(parent_sku_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="组合明细";
			 diag.URL = '<%=basePath%>skugroup/queryByParentId.do?parent_sku_id='+parent_sku_id;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				
				diag.close();
			 };
			 diag.show();
		}
		
		function stockinfo(data){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="库存详情";
			 diag.URL = '<%=basePath%>goods/stockinfo.do?data='+encodeURI(data);
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
	</script>


</body>
</html>