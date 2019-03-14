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
						<form action="superwhitelist/list.do" method="post" name="Form" id="Form">
					 
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
							<!-- 
							<tr style="margin:2px ">
								<td>
									<div class="nav-search">
											用户id:
										<span class="input-icon">
											<input type="text" placeholder="用户id:" class="nav-search-input" id="user_id" autocomplete="off" name="user_id" value="${pd.user_id}"/>
										</span>
									</div>
								</td>
								<td>
									<div class="nav-search">
											用户名:
										<span class="input-icon">
											<input type="text" placeholder="用户名" class="nav-search-input" id="user_name" autocomplete="off" name="user_name" value="${pd.user_name}"/>
										</span>
									</div>
								</td>
								<td>
									<div class="nav-search">
											用户昵称:
										<span class="input-icon">
											<input type="text" placeholder="用户昵称" class="nav-search-input" id="nickname" autocomplete="off" name="nickname" value="${pd.nickname}" />
										</span>
									</div>
								</td>
							</tr>
							 -->
								
								
								<tr style="margin:2px">
								<td >
									<div class="nav-search">
											手机号:
										<span class="input-icon">
											<input type="text" placeholder="手机号" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"  onkeyup="value=value.replace(/[^\d]/g,'')" />
										</span>
									</div>
								</td>
								
								<!-- 
								<td>
									<div class="nav-search">
											店铺:
										<span class="input-icon">
											<input type="text" placeholder="店铺" class="nav-search-input" id="store_name" autocomplete="off" name="store_name" value="${pd.store_name}"  />
										</span>
									</div>
								</td>
								 -->
								 
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align:top;padding-left:2px">
										<span class="input-icon" style="width:40px;"> </span>
										<span>
												<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
										</span>
										<span class="input-icon" style="width:23px;"> </span>
										<span>
												<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
										</span>
										<span class="input-icon" style="width:23px;"> </span>
										<span>
												<a class="btn btn-light btn-xs blue" onclick="toExcel();"  title="导出到Excel"  style="border-radius:5px;color:blue !important; width:50px">EXCEL</a>
										</span>
										
										<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
										<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
										<span>
												<a class="btn btn-light btn-xs blue" onclick="setIsSupperWhite();"  title="加超级白名单"  style="border-radius:5px;color:blue !important; width:100px">加超级白名单</a>
										</span>
										
									</td>
									
								</c:if>
								<!--
								<c:if test="${QX.toExcel == 1 }">
									<td style="vertical-align:top;padding-left:2px">
									<span class="input-icon" style="width:20px;"> </span>
										<span>
											<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"  style="border-radius:5px;color:blue !important; width:80px">导出到EXCEL </a>
										</span>
									</td>
								</c:if>
							    -->
								<c:if test="${pd.personal == 1 }">
									<td style="vertical-align:top;padding-left:2px">
									<span class="input-icon" style="width:20px;"> </span>
										<span>
											<a class="btn btn-light btn-xs" onclick="perExcel();" title="审核导出"  style="border-radius:5px;color:blue !important; width:60px">审核导出 </a>
										</span><span class="input-icon" style="width:20px;"> </span>
										<span >
											<a class="btn btn-light btn-xs" onclick="fromExcel();" title="审核导入"  style="border-radius:5px;color:blue !important; width:60px">审核导入 </a>
										</span>
									</td>
								</c:if>
							</tr>
							
							
							<!-- 
							<tr style="margin:2px">
								<td >
									<div class="nav-search">
										<a class="btn btn-xs btn-success" title="新增" onclick="add();">
											<i class="ace-icon fa fa-pencil-square-o bigger-120" title="新增">新增</i>
										</a>
									</div>
								</td>
								<td>
									<div class="nav-search">
										 
									</div>
								</td>
								
							</tr>
							 -->
							 
						</table>
					 
						
						
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- 
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									 -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">用户id</th>
									<!-- 
									<th class="center">备注2</th>
									 -->
									<th class="center">用户名</th>
									<th class="center">昵称</th>
									<th class="center">手机号</th>
									<th class="center">充值金额</th>
									<th class="center">可提现余额</th>
									
									<th class="center">大礼包总金额</th>
									
									<th class="center">店铺</th>
									<!-- 
									<th class="center">备注4</th>
									<th class="center">备注5</th>
									
									<th class="center">备注6</th>
									
									<th class="center">备注8</th>
									<th class="center">备注9</th>
									<th class="center">备注10</th>
									<th class="center">备注11</th>
									
									<th class="center">备注13</th>
									<th class="center">备注14</th>
									<th class="center">备注15</th>
									<th class="center">备注16</th>
									<th class="center">备注17</th>
									<th class="center">备注18</th>
									<th class="center">备注19</th>
									<th class="center">备注20</th>
									<th class="center">备注21</th>
									<th class="center">备注22</th>
									<th class="center">备注23</th>
									<th class="center">备注24</th>
									<th class="center">备注25</th>
									<th class="center">备注26</th>
									<th class="center">备注27</th>
									<th class="center">备注28</th>
									<th class="center">备注29</th>
									<th class="center">备注30</th>
									<th class="center">备注31</th>
									<th class="center">备注32</th>
									<th class="center">备注33</th>
									<th class="center">备注34</th>
									<th class="center">备注35</th>
									<th class="center">备注36</th>
									<th class="center">备注37</th>
									<th class="center">备注38</th>
									<th class="center">备注39</th>
									<th class="center">备注40</th>
									<th class="center">备注41</th>
									 -->
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
											<!-- 
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.user_id_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											 -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.user_id}</td>
											<td class='center'>${var.user_name}</td>
											<td class='center'>${var.nickname}</td>
											<td class='center'>
												<a title="查看流水" onclick="goListAccount('user_id=' + '${var.user_id}' + '&store_id=' + '${var.store_id}');">
													${var.mobile}
												</a>
												<!-- 
												 ${var.mobile}	
												 -->
											</td>
											<td class='center'>${var.money_limit}</td>
											<td class='center'>${var.money}</td>
											<td class='center'>${var.recharge_card_real_value}</td>
											
											<td class='center'>${var.name}</td>
											<!-- 
											<td class='center'>${var.email}</td>
											<td class='center'>${var.password}</td>
											
											<td class='center'>${var.salt}</td>
											
											<td class='center'>${var.sex}</td>
											<td class='center'>${var.birthday}</td>
											<td class='center'>${var.detail_address}</td>
											<td class='center'>${var.headimg}</td>
											
											<td class='center'>${var.user_money_limit}</td>
											<td class='center'>${var.frozen_money}</td>
											<td class='center'>${var.pay_point}</td>
											<td class='center'>${var.rank_point}</td>
											<td class='center'>${var.reg_time}</td>
											<td class='center'>${var.reg_ip}</td>
											<td class='center'>${var.last_time}</td>
											<td class='center'>${var.last_ip}</td>
											<td class='center'>${var.mobile_supplier}</td>
											<td class='center'>${var.mobile_province}</td>
											<td class='center'>${var.mobile_city}</td>
											<td class='center'>${var.reg_from}</td>
											<td class='center'>${var.surplus_password}</td>
											<td class='center'>${var.pay_pwd_salt}</td>
											<td class='center'>${var.user_status}</td>
											<td class='center'>${var.pass_wrong_count}</td>
											<td class='center'>${var.user_type}</td>
											<td class='center'>${var.is_real}</td>
											<td class='center'>${var.user_remark}</td>
											<td class='center'>${var.add_time}</td>
											<td class='center'>${var.push_key}</td>
											<td class='center'>${var.device_channel}</td>
											<td class='center'>${var.is_business}</td>
											<td class='center'>${var.lon}</td>
											<td class='center'>${var.lat}</td>
											<td class='center'>${var.city}</td>
											<td class='center'>${var.province}</td>
											<td class='center'>${var.has_third_user_id}</td>
											<td class='center'>${var.is_super_white}</td>
											 -->
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													 
													<c:if test="${QX.edit == 1 }">
													<!-- 
													<a class="btn btn-xs btn-success" title="编辑11111" onclick="edit('${var.user_id_id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													 -->
													<a class="btn btn-xs btn-success" title="充值" 
													   onclick="recharge('user_id=' + '${var.user_id}' + '&store_id=' + '${var.store_id}');"	   
													   
													>
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="充值">充值</i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<!-- 
													<a class="btn btn-xs btn-danger" onclick="del('${var.user_id_id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													-->
													<a class="btn btn-xs btn-success" title="扣款" 
														onclick="deduction('user_id=' + '${var.user_id}' + '&store_id=' + '${var.store_id}');"
													>
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="扣款">扣款</i>
													</a>
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
																<a style="cursor:pointer;" onclick="edit('${var.user_id_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.user_id_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
									<!-- 
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
									 -->
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
		function tosearch(status){
			if(status==0){
				$("#user_id").val(""); 
				$("#user_name").val("");
				$("#nickname").val("");
				$("#mobile").val("");
				$("#store_name").val("");
			}
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
			 diag.URL = '<%=basePath%>superwhitelist/goAdd.do';
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>superwhitelist/delete.do?user_id_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		
		function setIsSupperWhite(){
			var mobile = $("#mobile").val();
			
			if (mobile == null || mobile == "") {
				alert("请先输入手机号");
				return null;
			}
			
			mobile = mobile.replace(/(^\s*)|(\s*$)/g,"");
			
			var str = "确定要将手机号：" + mobile + " 设为超级白名单用户";
			
			bootbox.confirm(str, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>superwhitelist/setIsSupperWhite.do?mobile="+ mobile +"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch(1);
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
			 diag.URL = '<%=basePath%>superwhitelist/goEdit.do?user_id_id='+Id;
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
		
		function recharge(str){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="充值";
			 diag.URL = '<%=basePath%>superwhitelist/goRecharge.do?'+str;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		function goListAccount(str){
			 
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看流水";
			 diag.URL = '<%=basePath%>superwhitelist/listAccount.do?' + str;
			 /**
			 diag.Width = 450;
			 diag.Height = 355;
			 **/
			 diag.Width = 1200;
			 diag.Height = 700;
			 
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}				
		
		function deduction(str){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="扣款";
			 diag.URL = '<%=basePath%>superwhitelist/goDeduction.do?' + str;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
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
								url: '<%=basePath%>superwhitelist/deleteAll.do?tm='+new Date().getTime(),
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
			
			var user_id =  $("#user_id").val();
			var user_name =  $("#user_name").val();
			var nickname = $("#nickname").val();
			var mobile = $("#mobile").val();
			var store_name = $("#store_name").val();
			
			var url = '<%=basePath%>superwhitelist/excel.do?tm=' + new Date().getTime() 
				+ "&user_id=" + user_id 
				+ "&user_name=" + user_name		
				+ "&nickname=" + nickname
				+ "&mobile=" + mobile
				+ "&store_name=" + store_name
				;
			
	//			alert("url=" + url);
			
			window.location.href= url;
		
		}
	</script>


</body>
</html>