<%@page import="com.fh.util.DateUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<style type="text/css">
#xuxian{
width:100%;
height:0;
border-bottom:#778899 1px dashed;
overflow:hidden;
}/*#778899表示灰色，边框宽度为一个像素，边框线型为虚线*/
.news{
overflow:hidden;
}
.news .reply {
display:block;
  float: left;
  cursor:pointer;
  }
.news .userName {
display:block;
  float: right;
  }

</style>
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
						<form action="complain/list.do" method="post" name="Form" id="Form">
							 <table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												姓名:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="姓名" class="nav-search-input" id="user_name" autocomplete="off" name="user_name" value="${pd.user_name }"  />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												内容:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="内容" class="nav-search-input" id="complain_content" autocomplete="off" name="complain_content" value="${pd.complain_content}" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												电话:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="电话" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}" />
											</span>
										</div>
									</td>
							
									</tr>
									<tr style="margin:2px">
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												时间:
											</span>
											<span  >
												<input  name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input  name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
											</span>
									</td>
											<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													状态:
												</span>
										 	<select  name="is_read" id="is_read" data-placeholder="请选择" value="${pd.is_read }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="0" <c:if test="${pd.is_read!=NULL && pd.is_read!='' && pd.is_read == 0}">selected</c:if>>未读</option>
											<option value="1" <c:if test="${pd.is_read==1}">selected</c:if>>已读</option>
										  	</select>
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
							</table> <!-- 检索结束 -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">投诉者</th>
									<th class="center">电话</th>
									<th class="center" width="600px">投诉内容</th>
									<th class="center">投诉时间</th>
									<th class="center">回复内容</th>
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
											<td class='center'  style = "width:5%">${var.user_name}</td>
											<td class='center' style = "width:6%">${var.mobile}</td>
											<td class='center' style = "width:30%">${var.complain_content}</td>
											<td class='center' style = "width:10%">${DateUtil.toSDFTime(var.complain_time*1000)} </td>
											<td class='center'>
												<div>
													<c:forEach items="${var.replyEntityList}" var="replyEntityList" varStatus = "vss">
														<div  class="news">
															<div class="reply" title="${replyEntityList.reply}">
																	<c:choose>
																	<c:when test="${fn:length(replyEntityList.reply)  <= 30 }">
																		${replyEntityList.reply}
																	</c:when>
																	<c:otherwise>
													 					 ${fn:substring(replyEntityList.reply,0,30)}...  
																	</c:otherwise>
																</c:choose>
															</div>
															<div  class="userName">
																<lable style="color:red">
																	${replyEntityList.userName}
																	${DateUtil.toSDFTime(replyEntityList.time*1000)}
																</lable>
															</div>
														<c:if test="${! vss.last}">
															<div id="xuxian"></div>
														</c:if>
														</div>
													</c:forEach>
												</div>
											</td>
											<td class="center" style = "width:10%">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
														<c:if test="${var.is_read==0 || empty var.is_read }">
<%-- 															<a class="btn btn-xs btn-success" style="border-radius: 5px;"  title="置为已读" onclick="readOrUnread('1','${var.id}');">置为已读 </a> --%>
															<a class="btn btn-xs btn-info" title="回复" style="border-radius: 5px;" onclick="edit('${var.id}');"> 回复</a>
														</c:if>
														<c:if test="${var.is_read == 1 }">--</c:if>
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
								<td style="vertical-align:top; display:none;"> </td>
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
			function tosearch(status){
			if(status==0){
				$("#user_name").val("");
				$("#mobile").val("");
				$("#complain_content").val("");
				$("#is_read").empty();
				$("#lastStart").val("");
				$("#lastEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		//置为已读
		function readOrUnread(status,complainId){
				top.jzts();
				var url = "<%=basePath%>complain/toReadOrUnread.do?id="+complainId+"&is_read="+status;
				$.get(url,function(data){
					tosearch(0);
				});
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="处理页面";
			 diag.URL = '<%=basePath%>complain/goEdit.do?id='+Id;
			 diag.Width = 800;
			 diag.Height = 550;
// 			 diag.Width = 450;
// 			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
  				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch(0);
				}  
				diag.close();
			 };
			 diag.show();
		}
	</script>
</body>
</html>