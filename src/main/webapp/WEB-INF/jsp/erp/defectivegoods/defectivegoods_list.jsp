<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.fh.common.DefetiveConstants"%>
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
						<form action="defectivegoods/list.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									不良品单号：<input type="text" placeholder="不良品单号" id="nav-search-input" autocomplete="off" name="defective_code" value="${pd.defective_code}" />
								</td>
								
								<td class="pgt-query-form-td">
									仓库列表：
								 	<select name="store_sn" id="store_sn" data-placeholder="仓库列表" style="width: 150px;">
										<option value="">请选择仓库</option>
										<c:if test="${not empty storeList}">
											<c:forEach items="${storeList}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${pd.store_sn==var.store_sn}">
													    <option selected="selected" value="${var.store_sn}">${var.store_name}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.store_sn}">${var.store_name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
								  	</select>
							    </td>
								<!-- <td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								 -->
							
								<td class="pgt-query-form-td">
									状态：
								 	<select name="status" id="status" data-placeholder="请选择" style="width: 150px;">
									<option value="">全部</option>
									<c:if test="${pd.status==0}">
									<option value="0" selected="selected">未提交</option>
									</c:if>
									<c:if test="${pd.status!=0}">
									<option value="0" >未提交</option>
									</c:if>
									<c:if test="${pd.status==1}">
									<option value="1" selected="selected">待审</option>
									</c:if>
									<c:if test="${pd.status!=1}">
									<option value="1">待审</option>
									</c:if>
									<c:if test="${pd.status==2}">
									<option value="2" selected="selected">通过</option>
									</c:if>
									<c:if test="${pd.status!=2}">
									<option value="2">通过</option>
									</c:if>
									<c:if test="${pd.status==8}">
									<option value="8" selected="selected">驳回</option>
									</c:if>
									<c:if test="${pd.status!=8}">
									<option value="8">驳回</option>
									</c:if>
									<c:if test="${pd.status==9}">
									<option value="9" selected="selected">完成</option>
									</c:if>
									<c:if test="${pd.status!=9}">
									<option value="9">完成</option>
									</c:if>
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
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									
									
									<th class="center">不良品单号</th>
									<th class="center">仓库编码</th>
									<th class="center">仓库名称</th>
									<th class="center">审批状态</th>
									<th class="center">提报说明</th>
									<th class="center">提交人</th>
									<th class="center">提交时间</th>
									<th class="center">审批人</th>
									<th class="center">审批时间</th>
									
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
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.defective_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											
											<td class='center'>${var.defective_code}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>
											<c:choose>
											<c:when test="${var.status==DefetiveConstants.BUSINESS_STATUS_UNSUBMIT}">未提交</c:when>
											<c:when test="${var.status==DefetiveConstants.BUSINESS_STATUS_SUBMIT}">待审</c:when>
											<c:when test="${var.status==DefetiveConstants.BUSINESS_STATUS_AGREE}">通过</c:when>
											<c:when test="${var.status==DefetiveConstants.BUSINESS_STATUS_REJECT}">驳回</c:when>
											<c:when test="${var.status==DefetiveConstants.BUSINESS_STATUS_COMPLETE}">完成</c:when>
											</c:choose>
											</td>
											<td class='center'>${var.remark}</td>
											<td class='center'>${var.commitby}</td>
											<td class='center'><fmt:formatDate value="${var.commit_time}" pattern="yyyy-MM-dd"/></td>
											<td class='center'>${var.auditby}</td>
											<td class='center'><fmt:formatDate value="${var.audit_time}" pattern="yyyy-MM-dd"/></td>
											
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
												   
													<c:if test="${QX.edit == 1 }">
													<a ${var.status==DefetiveConstants.BUSINESS_STATUS_UNSUBMIT?"":"disabled='disabled'" }  class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.defective_id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑">编辑</i>
													</a>
													</c:if>
													
													 <a  ${var.status==DefetiveConstants.BUSINESS_STATUS_UNSUBMIT?"":"disabled='disabled'" } class="btn btn-xs btn-success" onclick="addDetailList('${var.defective_code}','${var.defective_id}','${var.store_id}','${var.store_sn}','${var.store_name}','${var.remark}');">
															<i class="ace-icon fa bigger-120" title="详情">添加详情</i>
													</a> 
													<a ${var.status==DefetiveConstants.BUSINESS_STATUS_UNSUBMIT?"":"disabled='disabled'" }  class="btn btn-xs btn-success" onclick="commit('${var.defective_id}','${var.defective_code}' );">
															<i class="ace-icon fa bigger-120" title="提交">提交</i>
													</a>
													
													 <a class="btn btn-xs btn-success" onclick="toDetailList('${var.defective_code}','${var.defective_id}','${var.store_id}','${var.store_sn}','${var.store_name}','${var.remark}');">
														<i class="ace-icon fa bigger-120" title="详情">详情</i>
													</a> 
													<a ${var.status==DefetiveConstants.BUSINESS_STATUS_UNSUBMIT?"":"disabled='disabled'" }  class="btn btn-xs btn-success" title="删除" onclick="del('${var.defective_id}');">
														<i class="ace-icon fa bigger-120" title="删除">删除</i>
													</a>
												</div>
												<%-- <div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.defective_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.defective_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div> --%>
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
									</c:if>
									
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
			 diag.URL = '<%=basePath%>defectivegoods/goAdd.do';
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
		//更新状态
		function commit(defective_id,defective_code){
			var promptMess = "确定要提交此不良品单吗？";
			
			bootbox.confirm(promptMess, function(result) {
				if(result) {
					$.ajax({
						  type: 'POST',
						  url: '<%=basePath%>defectivegoods/commit.do',
						  data: {'defective_id':defective_id,'defective_code':defective_code},
						  dataType:'json',
                          cache: false,
						  success: function(data){
							  if(data.msg=="success")
								{
									tosearch();
								}
							    else
								{
								  alert("不良品单无明细，不能提交！");  
								}
						  }
						});
				}
			});
			
			
		}
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>defectivegoods/delete.do?defective_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>defectivegoods/goEdit.do?defective_id='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
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
		
		
		
		//修改
		function toDetailList(defective_code, defective_id, store_id,store_sn,store_name,remark){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="不良品单详情";
		     diag.URL = '<%=basePath%>defectivegoodsdetail/list.do?defective_code='+defective_code+'&defective_id='+defective_id+'&store_id='+store_id+'&store_sn='+store_sn+'&store_name='+store_name+'&option=list'+'&remark='+remark;
 			 diag.Width = $(window).width();
			 diag.Height = $(window).height();
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
		//修改
		function addDetailList(defective_code, defective_id, store_id,store_sn,store_name,remark){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="不良品单详情";
		     diag.URL = '<%=basePath%>defectivegoodsdetail/list.do?defective_code='+defective_code+'&defective_id='+defective_id+'&store_id='+store_id+'&store_sn='+store_sn+'&store_name='+store_name+'&option=add'+'&remark='+remark;
		     diag.Width = $(window).width();
			 diag.Height = $(window).height();
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
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
								url: '<%=basePath%>defectivegoods/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>defectivegoods/excel.do';
		}
	</script>


</body>
</html>