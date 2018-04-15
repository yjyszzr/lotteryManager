﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
						<form action="purchasereturndetail/list.do?purchase_return_code=${pd.purchase_return_code}&purchase_code=${pd.purchase_code}&status=${pd.status}&return_id=${pd.return_id}" method="post" name="Form" id="Form">
						<input type="hidden" name="status" id="status" value="${pd.status}" />
						<input type="hidden" name="purchase_return_code" id="purchase_return_code" value="${pd.purchase_return_code}" />
						<input type="hidden" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" />
						<input type="hidden" name="return_id" id="return_id" value="${pd.return_id}" />
						<input type="hidden" name="oper_type" id="oper_type" value="${pd.oper_type}" />
						<input type="hidden" name="menu_type" id="menu_type" value="${pd.menu_type}" />
						<%-- <table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
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
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
							</tr>
						</table> --%>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center" style="display:none;">采购退货明细Id</th>
									<th class="center">采购退货编号</th>
									<th class="center">采购订单编号</th>
									<th class="center">物料编码</th>
									<th class="center">物料名称</th>
									<th class="center">单位</th>
									<th class="center" style="display:none;">商品Id</th>
									<th class="center">退货数量</th>
									<th class="center">待退货数量</th>
									<th class="center">出库数量</th>
									<th class="center">退货说明</th>
									<c:if test="${pd.oper_type == 1 and (pd.status==1 ||  pd.status==2)}">
									<th class="center">本次退货</th>
									</c:if>
									 
									
									
									<%-- <c:if test="${pd.status == 1}">
										<th class="center">操作</th>
									</c:if> --%>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${page.currentResult+vs.index+1}</td>
											<td class='center' style="display:none;">${var.return_detail_id}</td>
											<td class='center'>${pd.purchase_return_code}</td>
											<td class='center'>${pd.purchase_code}</td>
											<td class='center'>${var.sku_encode}</td>
											<td class='center'>${var.sku_name}</td>
											<td class='center'>${var.unit}</td>
											<td class='center' style="display:none;">${var.sku_id}</td>
											<td class='center'>${var.return_quantity}</td>
											<%--待发货数量等于订单数量减去已发货数量 --%>
											<td class='center'>${var.return_quantity-var.send_quantity}</td>
											<td class='center'>${var.quantity}</td>
											<td class='center'>${var.remark}	</td>
											</td>
											<c:if test="${pd.oper_type == 1 and(pd.status==1 ||  pd.status==2)}">
											<td class='center'>
											<input type="number" name="sendCount" min="0" value="0" style="width:70px" >
											
											</c:if>
										<%--不单一编辑，改成批量 
											<c:if test="${pd.status == 1}">
												<td class="center">
													<c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
													</c:if>
													<div class="hidden-sm hidden-xs btn-group">
														<c:if test="${QX.edit == 1 }">
															<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.return_detail_id}','${var.return_quantity}');">编辑</a>
														</c:if>
													</div>
													<div class="hidden-md hidden-lg">
														<div class="inline pos-rel">
															<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
															</button>
				
															<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																<c:if test="${QX.edit == 1 }">
																<li>
																	<a style="cursor:pointer;" onclick="edit('${var.return_detail_id}','${var.return_quantity}');" class="tooltip-success" data-rel="tooltip" title="修改">
																		<span class="green">
																			<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if>
															</ul>
														</div>
													</div>
												</td>
											</c:if>
 --%>										</tr>
									
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
							   <c:if test="${pd.oper_type == 1 and(pd.status==1 ||  pd.status==2)}">
									<a class="btn btn-mini btn-success" onclick="returnGoods('${pd.return_id}','${pd.purchase_code}');">退货</a>
								</c:if>
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
			 diag.URL = '<%=basePath%>purchasereturndetail/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
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
		
		//退货
		function returnGoods(Id,code){
			 var data = getTableContent("simple-table");
			 if(data.length == 0){
				 bootbox.alert("当前无可退货项，无法进行退货！");
				 return;
			 }
			 if("error"==data){
				 return ;
			 }
			 
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="退货";
			 diag.URL = '<%=basePath%>purchasereturn/goAudited.do?returnGoodsData='+encodeURI(data)+'&return_id='+Id+'&purchase_code='+code+'&menu_type='+$("#menu_type").val();
			 diag.Width = 550;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 /* if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				 } */
				tosearch();
				diag.close();
			 };
			 diag.show();
		}
		
		/* //得到表格内容
		function getTableContent(id){
		    var mytable = document.getElementById(id);
		    var data = [];
		    var str = "";
		    for(var i=0,rows=mytable.rows.length; i<rows; i++){
		      for(var j=0,cells=mytable.rows[i].cells.length; j<cells; j++){
		    	if(i==0)continue;
		    	if(mytable.rows[i].cells.length == 1) break;
		    	if(j==mytable.rows[i].cells.length-1){
		    		data[i][j] = ';'; 
		    		continue;
		    	}
		        if(!data[i]){
		          data[i] = new Array();
		        }
	        	str = mytable.rows[i].cells[j].innerHTML;
	        	str = str.replace(";","");
	        	str = str.replace("；","");
	        	data[i][j] = str;
	        	str = "";
		      }
		    }
		    return data;
		} */
		//得到表格内容
		function getTableContent(id){
		    var mytable = document.getElementById(id);
		    var data = [];
		    var str = "";
		    for(var i=1,rows=mytable.rows.length; i<rows; i++){
		    	//校验本次发货值，只取大于0的
		    	var sendCount = mytable.rows[i].cells[mytable.rows[i].cells.length-1].getElementsByTagName("input")[0].value;
	    		if( !(/^\d+$/.test(sendCount))){
					$(mytable.rows[i].cells[j].getElementsByTagName("input")[0]).tips({
						side:3,
			            msg:'请输入正确的数量值',
			            bg:'#AE81FF',
			            time:2
			        });
					return "error";
			}
	    		
	    		if(parseInt(sendCount)>parseInt(mytable.rows[i].cells[9].innerText)){
					$( mytable.rows[i].cells[mytable.rows[i].cells.length-1].getElementsByTagName("input")[0]).tips({
						side:3,
			            msg:'发货数量不能大于待发货数量',
			            bg:'#AE81FF',
			            time:2
			        });
					return "error";
			}
	    		if(parseInt(sendCount)==0){
	    			continue;
	    		}
	    		//-----校验本次发货值--结束
		      for(var j=0,cells=mytable.rows[i].cells.length; j<cells; j++){
		   
		    	if(mytable.rows[i].cells.length == 1) break;
		    	if(j==mytable.rows[i].cells.length-1){
		    		data[i][j] = sendCount; 
		    		data[i][j+1] = ";"; 
		    		
		    		continue;
		    	}
		        if(!data[i]){
		          data[i] = new Array();
		        }
	        	str = mytable.rows[i].cells[j].innerHTML;
	        	str = str.replace(";","");
	        	str = str.replace("；","");
	        	data[i][j] = str;
	        	str = "";
		      }
		    }
		   
		    return data;
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>purchasereturndetail/delete.do?return_detail_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id,returnQuantity){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>purchasereturndetail/goEdit.do?return_detail_id='+Id+'&return_quantity='+returnQuantity;
			 diag.Width = 450;
			 diag.Height = 175;
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
								url: '<%=basePath%>purchasereturndetail/deleteAll.do?tm='+new Date().getTime(),
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
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>purchasereturndetail/excel.do';
		}
	</script>


</body>
</html>