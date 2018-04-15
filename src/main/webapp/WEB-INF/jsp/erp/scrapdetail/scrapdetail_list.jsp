<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.fh.common.ScrapConstants" %>
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
							<input type="hidden" name="store_id" id="store_id" value="${pd.store_id }">
						<!-- 检索  -->
						<form action="scrapdetail/list.do?scrap_code=${pd.scrap_code }+&scrap_id=${pd.scrap_id}+&store_id=${pd.store_id}+&option=${option}" method="post" name="Form" id="Form">
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
							<table id="table_report" style="margin-top: 10px;margin-bottom:10px" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:100px;text-align: center;vertical-align:middle;">报损说明:</td>
									<td><input readonly="readonly" type="text" name="remark" id="remark" value="${pd.remark}" maxlength="32" style="width:100%;"/></td>
								</tr>
							</table>
								<table id="simple-table"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center" style="width: 35px;"><label
												class="pos-rel"><input type="checkbox" class="ace"
													id="zcheckbox" /><span class="lbl"></span></label></th>
											<th class="center" style="width: 50px;">序号</th>
											<!-- <th class="center">报损编码</th>
									<th class="center">物料号</th> -->
											<th class="center">物料编码</th>
											<th class="center">物料名称</th>
											<th class="center">批次号</th>
											<th class="center">物料单位</th>
											<th class="center">物料规格</th>
											<th class="center">核准状态</th>
											<th class="center">报损数量</th>
											<th class="center">核准数量</th>
											<th class="center">核准人</th>
											<th class="center">核准时间</th>
											<!-- <th class="center">单位</th> -->
											<th class="center">报损说明</th>
											<th class="center">处理建议类型</th>
											<th class="center">处理建议说明</th>
											<!-- <th class="center">创建人</th>
									<th class="center">创建时间</th>
									<th class="center">更新人</th>
									<th class="center">更新时间</th> -->
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody id="tbody2">
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'><label class="pos-rel"><input
																	type='checkbox' name='ids'
																	value="${var.scrap_detail_id}" class="ace" /><span
																	class="lbl"></span></label></td>
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<%-- <td class='center'>${var.scrap_code}</td>
											<td class='center'>${var.sku_id}</td> --%>
															<td class='center'>${var.sku_encode}</td>
															<td class='center'>${var.sku_name}</td>
															<td class='center'>${var.batch_code}</td>
															<td class='center'>${var.unit}</td>
															<td class='center'>${var.spec}</td>
															<td class='center'><c:if test="${var.status==0 }">待审</c:if>
																<c:if test="${var.status==1 }">核准完成</c:if></td>
															<td id="sq_${vs.index}" class='center'>${var.scrap_quantity}</td>
															<td id="aq_${vs.index}" sta_="${var.status}" class='center'>${var.audit_quantity}</td>
															<td class='center'>${var.auditby}</td>
															<td class='center'>
																<fmt:formatDate value="${var.audit_time}" pattern="yyyy-MM-dd"/>
															</td>
															<%-- <td class='center'>${var.unit}</td> --%>
															<td class='center'>${var.remark}</td>
															<td class='center'><c:if
																	test="${var.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_0 }">可用</c:if>
																<c:if
																	test="${var.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_1 }">不可用</c:if>
																<c:if
																	test="${var.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_2 }">其它</c:if>
															</td>
															<td class='center'>${var.deal_suggest_des}</td>
															<%-- <td class='center'>${var.createby}</td>
											<td class='center'>${var.create_time}</td>
											<td class='center'>${var.updateby}</td>
											<td class='center'>${var.update_time}</td> --%>
															<td class="center"><c:if
																	test="${QX.edit != 1 && QX.del != 1 }">
																	<span
																		class="label label-large label-grey arrowed-in-right arrowed-in"><i
																		class="ace-icon fa fa-lock" title="无权限"></i></span>
																</c:if>
																<div class="hidden-sm hidden-xs btn-group">
																	<c:if test="${QX.edit == 1 && option == 'add'}">
																		<a class="btn btn-xs btn-success" title="编辑"
																			onclick="edit('${var.scrap_detail_id}');"> <i
																			class="ace-icon fa fa-pencil-square-o bigger-120"
																			title="编辑"></i>
																		</a>
																	</c:if>
																	<c:if test="${QX.del == 1 && option == 'add'}">
																		<a class="btn btn-xs btn-danger"
																			onclick="del('${var.scrap_detail_id}');"> <i
																			class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
																		</a>
																	</c:if>
																	<c:if test="${option == 'check' }">
																		<a style="cursor: pointer;"
																			onclick="check('${var.scrap_detail_id}');"
																			class="tooltip-success" data-rel="tooltip" title="审核">
																			<span class="green"> <i
																				class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																		</span>
																		</a>
																	</c:if>
																</div>
																<div class="hidden-md hidden-lg">
																	<div class="inline pos-rel">
																		<button
																			class="btn btn-minier btn-primary dropdown-toggle"
																			data-toggle="dropdown" data-position="auto">
																			<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																		</button>

																		<ul
																			class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																			<c:if test="${QX.edit == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="edit('${var.scrap_detail_id}');"
																					class="tooltip-success" data-rel="tooltip"
																					title="修改"> <span class="green"> <i
																							class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																					</span>
																				</a></li>
																				<li><a style="cursor: pointer;"
																					onclick="check('${var.scrap_detail_id}');"
																					class="tooltip-success" data-rel="tooltip"
																					title="审核"> <span class="green"> <i
																							class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>
																			<c:if test="${QX.del == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="del('${var.scrap_detail_id}');"
																					class="tooltip-error" data-rel="tooltip" title="删除">
																						<span class="red"> <i
																							class="ace-icon fa fa-trash-o bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>
																		</ul>
																	</div>
																</div></td>
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
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
								<c:if test="${option == 'add' }">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add('${pd.scrap_code}', '${pd.store_id }');">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
								</c:if>
								<c:if test="${option == 'check' }">
									<a class="btn btn-mini btn-success" onclick="complete('${pd.scrap_id}');">完成</a>
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
		function add(scrap_code, store_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>scrapdetail/goAdd.do?scrap_code='+scrap_code +'&store_id='+store_id;
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
		//审核完成
		function complete(Id){
			var len_ = $('#tbody2 tr').length;
			var flag = true;
			for(i=0; i<len_; i++){
				var sqVal_ = $('#sq_'+i).text();
				var aqVal_ = $('#aq_'+i).text();
				var status_ = $('#aq_'+i).attr('sta_');
				if(parseInt(status_) == 0 ){
					alert("有未执行核准的记录！");
					flag = false;
					break;
				}
				if(parseInt(aqVal_) < 0){
					alert("有未执行核准的记录！");
					flag = false;
					break;
				}
			}
			if(flag){
				bootbox.confirm("确定要完成审核吗?", function(result) {
					if(result) {
						top.jzts();
						var url = "<%=basePath%>scrap/completeCheck.do?scrap_id="+Id+"&status=2";
						$.get(url,function(data){
							tosearch();
							top.Dialog.close();
						});
					}
				});
			}
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>scrapdetail/delete.do?scrap_detail_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>scrapdetail/goEdit.do?scrap_detail_id='+Id;
			 diag.Width = 650;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				} */
					 tosearch();
				diag.close();
			 };
			 diag.show();
		}
		function check(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="审核";
			 diag.URL = '<%=basePath%>scrapdetail/goCheck.do?scrap_detail_id='+Id;
			 diag.Width = 650;
			 diag.Height = 755;
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
								url: '<%=basePath%>scrapdetail/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>scrapdetail/excel.do';
		}
	</script>


</body>
</html>