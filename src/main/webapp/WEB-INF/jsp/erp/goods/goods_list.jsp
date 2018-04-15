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
						<form action="goods/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="DICTIONARIES_ID" id="DICTIONARIES_ID" value="${dictionaries.DICTIONARIES_ID}"/>
						<table style="margin-top:15px;margin-bottom:15px;">
							<tr>
								<td>
									物料编码：
											<input type="text" placeholder="这里输入商品编码" class="nav-search-input" id="sku_encode" autocomplete="off" name="sku_encode" value="${pd.sku_encode}" placeholder="这里输入商品编码"/>
								</td>
								<td style="padding-left:10px;">
								物料名称：
											<input type="text" placeholder="这里输入商品名称" class="nav-search-input" id="sku_name" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入商品名称"/>
								</td>
								<td style="padding-left:10px;">
								物料分类：
								 	<select id="cat_id1" name="cat_id1" value="${pd.cat_id1}" onchange="change2(this.value)" style="width:130px;">
                      				</select>
								 	<select id="cat_id2" name="cat_id2" value="${pd.cat_id2}" onchange="change3(this.value)" style="width:130px;" disabled="disabled">
                      				</select>
									<select id="cat_id3" name="cat_id3" value="${pd.cat_id3}" style="width:130px;" disabled="disabled">
									</select>
								</td>
								<%-- 
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								
								 <td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>
								 --%>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:10px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索">&nbsp查询</a></td>
								<td style="vertical-align:top;padding-left:10px"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">&nbsp清空</a></td>
								</c:if>
								<%-- <c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if> --%>
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
									<th class="center">商品编码</th>
									<th class="center">商品条码</th>
									<th class="center">商品名称</th>
									<!-- <th class="center">商品零售价</th> -->
									<th class="center">单位</th>
									<!-- <th class="center">供应商</th> -->
									<!-- <th class="center">添加时间</th> -->
									<!-- <th class="center">是否批次管理</th> -->
									<th class="center">规格</th>
									<th class="center">属性分类</th>
									<!-- <th class="center">税率</th> -->
									<th class="center">重量(kg)</th>
									<th class="center">是否预售</th>
									<th class="center">经营类型</th>
									<!-- <th class="center">是否组合商品</th> -->
									<th class="center">同步商城</th>
									<th class="center">操作</th>
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
											<%-- <td class='center'>${var.goods_price}</td> --%>
											<td class='center'>${var.unit_name}</td>
											<%-- <td class='center'>${var.supplier_id}</td> --%>
											<%-- <td class='center'>${var.add_time}</td> --%>
											<%-- <td class='center'>
												<c:if test="${var.isopen_batch == 0}">否</c:if>
												<c:if test="${var.isopen_batch == 1}">是</c:if>
											</td> --%>
											<td class='center'>${var.spec}</td>
											<td class='center'>
												<c:if test="${var.attr_cate == 0}">原材料</c:if>
												<c:if test="${var.attr_cate == 1}">包材</c:if>
												<c:if test="${var.attr_cate == 2}">商品</c:if>
											</td>
											<%-- <td class='center'>${var.tax_rate}</td> --%>
											<td class='center'>${var.weight}</td>
											<td class='center'>
												<c:if test="${var.is_pre_sales == 0}">非预售商品</c:if>
												<c:if test="${var.is_pre_sales != 0}">预售商品</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.is_self_goods == 0}">自营商品</c:if>
												<c:if test="${var.is_self_goods == 1}">非自营商品</c:if>
												<c:if test="${var.is_self_goods == 2}">联营商品</c:if>
											</td>
											<%-- <td class='center'>
												<c:if test="${var.is_combination_goods == 0}">非组合</c:if>
												<c:if test="${var.is_combination_goods != 0}">组合</c:if>
											</td> --%>
											<td class='center'>
												<c:if test="${var.is_syn == 1}">已同步</c:if>
												<c:if test="${var.is_syn != 1}">未同步</c:if>
											</td>
											
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.goods_id}');">
														编辑
													</a>
													<c:if test="${var.is_combination_goods != 0}">
														<a class="btn btn-xs btn-success" title="组合" onclick="combination('${var.parent_sku_id}');">组合</a>
													</c:if>
													<c:if test="${var.is_combination_goods == 0}">
														<a class="btn btn-xs btn-success" disabled = "ture" title="组合" onclick="combination('${var.parent_sku_id}');">组合</a>
													</c:if>
													
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.goods_id}');">
														删除
													</a>
													</c:if>
													<c:if test="${var.attr_cate == 2}">
													<a class="btn btn-xs btn-danger" onclick="syngoods('${var.goods_id}');">
														同步商城
													</a>
													</c:if>
													<a class="btn btn-xs btn-success" title="库存线" onclick="warningLine('${var.parent_sku_id}','${var.goods_name}');">库存线</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.goods_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.goods_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div>
											</td>
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
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									<a class="btn btn-mini btn-success" onclick="refreshcache();">刷新缓存</a>
									</c:if>
									<%-- <c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
								</td>
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
			//清空
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>goods/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 555;
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
		function combination(parent_sku_id){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="组合明细";
				 diag.URL = '<%=basePath%>skugroup/listByParentId.do?parent_sku_id='+parent_sku_id;
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
		
		function warningLine(parent_sku_id,goods_name){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="库存线";
			 diag.URL = '<%=basePath%>goodsquantityline/list.do?sku_id='+parent_sku_id+'&goods_name='+goods_name;
			 diag.Width = 950;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>goods/delete.do?goods_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						top.hangge();
						if(data.msg=="success"){
								tosearch();
							}else{
								alert(data.info);
							}
					});
				}
			});
		}
		function syngoods(Id){
			bootbox.confirm("确定要同步商城吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>goods/synshop.do?goods_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>goods/goEdit.do?goods_id='+Id;
			 diag.Width = 450;
			 diag.Height = 555;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>goods/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		
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
		
		function refreshcache(){
			var url = "<%=basePath%>goods/refreshcache.do?tm=" + new Date().getTime();
			$.get(url, function(data) {
				if (data.msg == "fail") {
					alert(data.info);
				} else {
					alert(data.info);
				}
			});
		}
	</script>


</body>
</html>