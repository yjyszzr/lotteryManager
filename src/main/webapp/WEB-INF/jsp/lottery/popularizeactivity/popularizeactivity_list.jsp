<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.fh.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
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
						<form action="popularizeactivity/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;"  >
							<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												活动名称:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="活动名称" class="nav-search-input" id="act_name" autocomplete="off" name="act_name" value="${pd.act_name}" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													活动类型:
												</span>
										 	<select  name="act_type" id="act_type" data-placeholder="请选择" value="${pd.act_type }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
											<option value="3" <c:if test="${pd.act_type==3}">selected</c:if>>人数奖励</option>
											<option value="4" <c:if test="${pd.act_type==4}">selected</c:if>>消费奖励</option>
										  	</select>
										  	</div>
									</td>
									
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													状态:
												</span>
										 	<select  name="is_finish" id="is_finish" data-placeholder="请选择" value="${pd.is_finish }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="0" <c:if test="${pd.is_finish == 0}">selected</c:if>>未结束</option>
											<option value="1" <c:if test="${pd.is_finish == 1}">selected</c:if>>结束</option>
										  	</select>
										  	</div>
									</td>		
								
										</tr>
									<tr>
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
									<th class="center" style="width:50px;">序号</th>
									<th class="center">活动名称</th>
									<th class="center">标题</th>
									<th class="center">活动类型</th>
									<th class="center">开始时间</th>
									<th class="center">结束时间</th>
									<th class="center">是否结束</th>
									<th class="center">奖励金</th>
									<th class="center">邀请数量</th>
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
											<td class='center'>${var.act_name}</td>
											<td class='center'>${var.act_title}</td>
											<td class='center'> 
												<c:if test="${var.act_type == 3 }">人数奖励</c:if>
												<c:if test="${var.act_type == 4 }">消费奖励</c:if>
											</td>
											<td class='center'>${DateUtil.toSDFTime(var.start_time*1000)} </td>
											<td class='center'>${DateUtil.toSDFTime(var.end_time*1000)} </td>
											<td class='center'>
												<c:if test="${var.is_finish == 0 }">未结束</c:if>
												<c:if test="${var.is_finish == 1 }">已结束</c:if>
												<c:if test="${empty var.is_finish }">-- --</c:if>
											</td>
											<td class='center'>${var.reward_money}</td>
											<td class='center'>${var.number}</td>
															<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:choose>
													<c:when test="${var.is_finish==1 || empty var.is_finish}"> 
														<a class="btn btn-xs btn-primary" title="上架" style="border-radius: 5px;" onclick="onOrOffLine('0','${var.act_id}');"> 上架</a>
														<a class="btn btn-xs btn-info" title="编辑" style="border-radius: 5px;" onclick="edit('${var.act_id}');"> 编辑</a>
														<c:if test="${QX.del == 1 }">
															<a class="btn btn-xs btn-danger" style="border-radius: 5px;"  onclick="del('${var.act_id}');">删除</a>
														</c:if>
													</c:when>
													<c:when test="${var.is_finish==0}">
														<a class="btn btn-xs btn-default" title="下架" style="border-radius: 5px;"onclick="onOrOffLine('1','${var.act_id}');">下架</a>
													</c:when>
													</c:choose>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
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
									<a class="btn btn-mini btn-success" style="border-radius: 5px;"  onclick="add();">新增</a>
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
	
	function tosearch(status){
		if(status==0){
			$("#act_name").val("");
			$("#is_finish").empty();
			$("#act_type").empty();
		}
		top.jzts();
		$("#Form").submit();
	}
	
	//上架和下架  
	function onOrOffLine(status,act_id){   
			top.jzts();
			var url = "<%=basePath%>popularizeactivity/onLineOrOffLine.do?act_id="+act_id+"&is_finish="+status;
			$.get(url,function(data){
				tosearch(0);
			});
	}
	
		$(top.hangge());//关闭加载状态
		
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增活动";
			 diag.URL = '<%=basePath%>popularizeactivity/goAdd.do';
			 diag.Width = 410;
			 diag.Height = 410;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;		//最小化按钮
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
					var url = "<%=basePath%>popularizeactivity/delete.do?act_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>popularizeactivity/goEdit.do?act_id='+Id;
			 diag.Width = 410;
			 diag.Height = 455;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;	//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>popularizeactivity/excel.do';
		}
	</script>


</body>
</html>