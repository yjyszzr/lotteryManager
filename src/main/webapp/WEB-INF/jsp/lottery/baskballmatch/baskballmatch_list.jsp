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
						<form action="baskballmatch/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
						
							<tr style="margin:2px ">
										<td>
											<div class="nav-search">
												<span class="input-icon" style="width:80px;text-align:right;">
													联赛名称:
												</span>
												<span class="input-icon">
													<input type="text" placeholder="联赛名称" class="nav-search-input" id="league_name" autocomplete="off" name="league_name" value="${pd.league_name }"/>
												</span>
											</div>
										</td>
										<c:if test="${QX.cha == 1 }">
										<td style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:80px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
											</span>
											<span class="input-icon" style="width:43px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
											</span>
										</td>
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
									<th class="center">场次Id</th>
									<th class="center">联赛Id</th>
									<th class="center">场次</th>
									<th class="center">联赛名称</th>
									<th class="center">联赛简称</th>
<!-- 									<th class="center">场次Id</th> -->
<!-- 									<th class="center">主队Id</th> -->
									<th class="center">主队名称</th>
<!-- 									<th class="center">主队简称</th> -->
<!-- 									<th class="center">主队排名</th> -->
<!-- 									<th class="center">客队Id</th> -->
									<th class="center">客队名称</th>
<!-- 									<th class="center">客队简称</th> -->
<!-- 									<th class="center">客队排名</th> -->
									<th class="center">比赛时间</th>
									<th class="center">截售时间</th>
<!-- 									<th class="center">创建时间</th> -->
<!-- 									<th class="center">是否展示</th> -->
<!-- 									<th class="center">是否删除</th> -->
<!-- 									<th class="center">比赛编号</th> -->
<!-- 									<th class="center">全场</th> -->
									<th class="center">状态</th>
									<th class="center">是否热门</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.match_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.match_id}</td>
											<td class='center'>${var.league_id}</td>
											<td class='center'>${var.changci}</td>
											<td class='center'>${var.league_name}</td>
											<td class='center'>${var.league_abbr}</td>
<%-- 											<td class='center'>${var.changci_id}</td> --%>
<%-- 											<td class='center'>${var.home_team_id}</td> --%>
											<td class='center'>${var.home_team_name}</td>
<%-- 											<td class='center'>${var.home_team_abbr}</td> --%>
<%-- 											<td class='center'>${var.home_team_rank}</td> --%>
<%-- 											<td class='center'>${var.visiting_team_id}</td> --%>
											<td class='center'>${var.visiting_team_name}</td>
<%-- 											<td class='center'>${var.visiting_team_abbr}</td> --%>
<%-- 											<td class='center'>${var.visiting_team_rank}</td> --%>
											<td class='center'>${var.match_time}</td>
											<td class='center'>${var.show_time}</td>
<%-- 											<td class='center'>${var.create_time}</td> --%>
<%-- 											<td class='center'>${var.is_show}</td> --%>
<%-- 											<td class='center'>${var.is_del}</td> --%>
<%-- 											<td class='center'>${var.match_sn}</td> --%>
<%-- 											<td class='center'>${var.whole}</td> --%>
												<td class='center'> 
											<c:choose>
												<c:when test="${var.status == 0 }">待开赛</c:when>
												<c:when test="${var.status == 1 }">已结束</c:when>
											</c:choose>
											</td>
											<td class='center'> 
											<c:choose>
												<c:when test="${var.is_hot == 0 }">非热门</c:when>
												<c:when test="${var.is_hot == 1 }">热门</c:when>
											</c:choose>
											</td>
											
											<td class="center">
											
														<c:if test="${QX.edit == 1 }">
														<c:choose>
															<c:when test="${var.is_hot == 0 }"><a  onclick="updateStatus('${var.match_id}','1');" style="border-radius: 5px;cursor:pointer;" class="btn btn-xs btn-warning" title="置为热门"   >置为热门</a></c:when>
															<c:when test="${var.is_hot == 1 }"><a  onclick="updateStatus('${var.match_id}','0');" style="border-radius: 5px;cursor:pointer;" class="btn btn-xs btn-blue" title="撤销热门"   >撤销热门</a></c:when>
														</c:choose>
														<c:choose>
															<c:when test="${var.is_del == 0 }"><a  onclick="updateDel('${var.match_id}','1');" style="border-radius: 5px;cursor:pointer;" class="btn btn-xs btn-success" title="下架"   >下架</a></c:when>
															<c:when test="${var.is_del == 1 }"><a  onclick="updateDel('${var.match_id}','0');" style="border-radius: 5px;cursor:pointer;" class="btn btn-xs btn-primary" title="上架"   >上架</a></c:when>
														</c:choose>
															 
													</c:if>
											
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
<!-- 								<td style="vertical-align:top;"> -->
<%-- 									<c:if test="${QX.add == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-success" onclick="add();">新增</a> -->
<%-- 									</c:if> --%>
<%-- 									<c:if test="${QX.del == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a> -->
<%-- 									</c:if> --%>
<!-- 								</td> -->
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
				$("#league_name").val("");
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
			 diag.URL = '<%=basePath%>baskballmatch/goAdd.do';
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
					var url = "<%=basePath%>baskballmatch/delete.do?match_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>baskballmatch/goEdit.do?match_id='+Id;
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
								url: '<%=basePath%>baskballmatch/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>baskballmatch/excel.do';
		}
		
		
		
		function updateStatus(Id,status){
			var str = "";
			if(status == 1){
				str = "确定置为热门吗?";
			}else{
				str = "确定撤销热门吗?";
			}
			bootbox.confirm(str, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>baskballmatch/updateStatus.do?match_id="+Id+"&is_hot="+status;
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		function updateDel(Id,status){
			var str = "";
			if(status == 1){
				str = "确定下架吗?";
			}else{
				str = "确定上架吗?";
			}
			bootbox.confirm(str, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>baskballmatch/updateDel.do?match_id="+Id+"&is_del="+status;
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		
	</script>


</body>
</html>