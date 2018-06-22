<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fh.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
						<form action="questionsandanswers/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
								<tr style="margin:2px ">
										<td>
											<div class="nav-search">
												<span class="input-icon" style="width:80px;text-align:right;">
													赛事:
												</span>
												<span class="input-icon">
													<input type="text" placeholder="	赛事" class="nav-search-input" id="guessing_title" autocomplete="off" name="guessing_title" value="${pd.guessing_title }"/>
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
<!-- 									<th class="center" style="width:35px;"> -->
<!-- 									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label> -->
<!-- 									</th> -->
									<th class="center" style="width:50px;">序号</th>
<!-- 									<th class="center">id</th> -->
									<th class="center">赛事</th>
									<th class="center">期次</th>
									<th class="center">答题开始时间</th>
									<th class="center">答题结束时间</th>
									<th class="center">所属活动</th>
									<th class="center">状态</th>
<!-- 									<th class="center">最低参与金额</th> -->
<!-- 									<th class="center">奖池</th> -->
<!-- 									<th class="center">竞猜题</th> -->
<!-- 									<th class="center">人数</th> -->
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
<!-- 											<td class='center'> -->
<%-- 												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label> --%>
<!-- 											</td> -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
<%-- 											<td class='center'>${var.id}</td> --%>
											<td class='center'>${var.guessing_title}</td>
											<td class='center'>${var.period}</td>
											<td class='center'>${DateUtil.toSDFTime(var.start_time*1000)}</td>
											<td class='center'>${DateUtil.toSDFTime(var.end_time*1000)}</td>
											<td class='center'>
											<c:choose>
													<c:when test="${var.scope_of_activity ==1}">竞彩足球</c:when>
													<c:otherwise>其他</c:otherwise>
												</c:choose>
											</td>
											<td class='center'>
												<c:choose>
													<c:when test="${var.status ==0}">草稿</c:when>
													<c:when test="${var.status ==1}">已发布</c:when>
													<c:when test="${var.status ==2}">已公布答案</c:when>
													<c:when test="${var.status ==3}">已派奖</c:when>
												</c:choose>
											</td>
<%-- 											<td class='center'>${var.limit_lottery_amount}</td> --%>
<%-- 											<td class='center'>${var.bonus_pool}</td> --%>
<%-- 											<td class='center'>${var.question_and_answer}</td> --%>
<%-- 											<td class='center'>${var.num_of_people}</td> --%>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<c:choose>
															<c:when test="${var.status ==0}">
																<a class="btn btn-xs btn-success" title="编辑"   style="border-radius: 5px;"  onclick="edit('${var.match_id}');">编辑</a>
																<a class="btn btn-xs btn-primary" title="发布"   style="border-radius: 5px;"  onclick="updateStatus('${var.id}','1');" >发布</a>
															</c:when>
															<c:when test="${var.status ==1}">
															<c:if test="${var.end_time >= currentTime}">
																<a class="btn btn-xs btn-danger" title="待公布答案"   style="border-radius: 5px;" >－－－－</a>
															</c:if>
															<c:if test="${var.end_time < currentTime}">
																<a class="btn btn-xs btn-danger" title="公布答案"   style="border-radius: 5px;"  onclick="edit('${var.match_id}');">公布答案</a>
															</c:if>
															</c:when>
															<c:when test="${var.status ==2}">
																<a class="btn btn-xs btn-primary" title="派奖"   style="border-radius: 5px;"  onclick="edit('${var.match_id}');">派奖</a>
															</c:when>
															<c:when test="${var.status ==3}">
																<a class="btn btn-xs btn-orange" title="详情"   style="border-radius: 5px;"  onclick="edit('${var.match_id}');">详情</a>
															</c:when>
													</c:choose>
													</c:if>
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
									</c:if>
									<c:if test="${QX.del == 1 }">
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
		function tosearch(status){
			if(status==0){
				$("#guessing_title").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		//更新状态
		function updateStatus(Id,status){
			bootbox.confirm("确定要发布该竞猜题吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>questionsandanswers/updateStatus.do?id="+Id+"&status="+status;
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(matchId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="答题竞猜";
			 diag.URL = '<%=basePath%>questionsandanswers/goEdit.do?matchId='+matchId;
			 diag.Width = 700;
			 diag.Height = 700;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch(1);
				}
				diag.close();
			 };
			 diag.show();
		}
	</script>
</body>
</html>