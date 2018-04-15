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
						<form action="szystore/storelist.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
								  仓库名称：<input type="text" placeholder="仓库名称"  id="nav-search-input" autocomplete="off" name="store_name" value="${pd.store_name }" placeholder="仓库名称"/>
								</td>

								<td class="pgt-query-form-td">
									仓库类型：
								 	<select name="type" id="type" data-placeholder="请选择" style="width: 150px;">
									<option value="">请选择仓库类型</option>
									<option value="1" ${pd.type=="1"?'selected':''} >总仓虚拟仓</option>
									<option value="2" ${pd.type=="2"?'selected':''} >大区仓</option>
									<option value="3" ${pd.type=="3"?'selected':''} >城市仓</option>
									<option value="4" ${pd.type=="4"?'selected':''} >门店仓</option>
									<option value="5" ${pd.type=="5"?'selected':''} >OEM仓</option>
									<option value="6" ${pd.type=="6"?'selected':''} >总仓</option>
								  	<option value="7" ${pd.type=="7"?'selected':''} >俱乐部虚拟仓</option>
								  	</select>
								</td>
								
								<td class="pgt-query-form-td">
									仓库类别：
								 	<select name="store_type_sort" id="store_type_sort" data-placeholder="请选择" style="width: 150px;">
									<option value="">请选择仓库类别</option>
									<option value="0" ${pd.store_type_sort=="0"?'selected':''} >良品仓</option>
									<option value="1" ${pd.store_type_sort=="1"?'selected':''} >不良品仓</option>
									<option value="2" ${pd.store_type_sort=="2"?'selected':''} >物流仓</option>
									<option value="4" ${pd.store_type_sort=="4"?'selected':''} >虚拟仓</option>
									<option value="5" ${pd.store_type_sort=="5"?'selected':''} >报损仓</option>
									<option value="6" ${pd.store_type_sort=="6"?'selected':''} >待检仓</option>
								  	</select>
								</td>
								
								<c:if test="${QX.cha == 1 }">
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs"onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">清空</a></td>
								</c:if>
								
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">仓库编码</th>
									<th class="center">仓库名称</th>
										<th class="center">仓库类型</th>
										<th class="center">仓库类别</th>
										
										<th class="center">上级仓库</th>
										<th class="center">不良品仓</th>
										<th class="center">物流仓</th>
											<th class="center">报损仓</th>
											<th class="center">虚拟仓</th>
										<th class="center">待检仓</th>
									<th class="center">联系人</th>
									<th class="center">联系方式</th>
									<th class="center">地址</th>
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
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
												<td class='center'>
											     <c:if test="${var.store_type_id==1 }">
											   总仓虚拟仓
											</c:if>
											<c:if test="${var.store_type_id==2 }">
											   大区仓
											</c:if>
											<c:if test="${var.store_type_id==3 }">
											   城市仓
											</c:if>
											<c:if test="${var.store_type_id==4 }">
											   门店仓
											</c:if>
											
											<c:if test="${var.store_type_id==5 }">
											OEM仓
											</c:if>
											
											<c:if test="${var.store_type_id==6 }">
											总仓
											</c:if>
											
											<c:if test="${var.store_type_id==7 }">
											俱乐部虚拟仓
											</c:if>
												
												</td>
												
												
											<td class='center'>
										    <c:if test="${var.store_type_sort==0 }">
											   良品仓
											</c:if>
											<c:if test="${var.store_type_sort==1 }">
											   不良品仓
											</c:if>
											<c:if test="${var.store_type_sort==2 }">
											   物流仓
											</c:if>
											<c:if test="${var.store_type_sort==3 }">
											  
											</c:if>
											<c:if test="${var.store_type_sort==4 }">
											 虚拟仓
											</c:if>
											
											<c:if test="${var.store_type_sort==5 }">
											 报损仓
											</c:if>
											
											<c:if test="${var.store_type_sort==6 }">
											 待检仓
											</c:if>
												
												</td>
												
											<td class='center'>${var.parent_store_name}</td>
											<td class='center'>${var.bad_store_name}</td>
											<td class='center'>${var.logistic_store_name}</td>
											
											<td class='center'>${var.scrap_store_name}</td>
											<td class='center'>${var.virtual_store_name}</td>
											<td class='center'>${var.quality_store_name}</td>
											
												
											<td class='center'>${var.contact}</td>
											<td class='center'>${var.tel}</td>
											<td class='center'>${var.address}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<%-- <div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-mini btn-success" onclick="select('${var.store_sn}','${var.store_name}')">修改</a>
												</div> --%>
												
												<div class="hidden-sm hidden-xs btn-group">
													<a ${var.store_type_sort==0?"":"disabled='disabled'" } class="btn btn-mini btn-success"  onclick="synchStore('${var.store_id}', '${var.store_name}')">同步仓库</a>
													&nbsp; &nbsp;<a class="btn btn-mini btn-success" onclick="setwarerel('${var.store_id}')">关联仓库</a>
													&nbsp; &nbsp;<a class="btn btn-mini btn-success" onclick="edit('${var.store_id}')">修改</a>
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
									
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									<a class="btn btn-mini btn-success" onclick="refreshcache();">刷新缓存</a>
								</td>
								
								<td style="vertical-align:top;">
								<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
								</td>
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
		});
		
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>szystore/add.do';
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
		
		
		//修改
		function edit(store_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title =" 修改 ";
			 diag.URL = '<%=basePath%>szystore/goEdit.do?store_id='+store_id;
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
		function synchStore(store_id, store_name){
			bootbox.confirm("确定要同步仓库-"+store_name+"-到商城吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>szystore/synchStore.do?store_id="+store_id+"&store_name="+store_name;
					$.get(url,function(data){
						//console.info(data);
						alert(data);
						tosearch();
					});
				}
			});
		}
		
		//设置仓库关系
		function setwarerel(store_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>szystore/setwarerel.do?store_id='+store_id;
			 diag.Width = 450;
			 diag.Height = 455;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch();
					 }else{
						 tosearch();
					 }
				} */
				 tosearch();
				diag.close();
			 };
			 diag.show();
		}
		
		
		
		function select(sn,name){
			top.frames[1].closeFromStormSnDiag(sn,name);
		}
		
		function refreshcache(){
			var url = "<%=basePath%>szystore/refreshcache.do?tm=" + new Date().getTime();
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