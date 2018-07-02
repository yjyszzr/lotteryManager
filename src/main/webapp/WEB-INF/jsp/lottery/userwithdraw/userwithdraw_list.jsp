<%@page import="com.fh.util.DateUtil"%>
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
						<form action="userwithdraw/list.do" method="post" name="Form" id="Form">
							<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												订单编号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="订单编号" class="nav-search-input" id="withdrawal_sn" autocomplete="off" name="withdrawal_sn" value="${pd.withdrawal_sn }"/>
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												手机号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="手机号" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"   onkeyup="value=value.replace(/[^\d]/g,'')" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												用户昵称:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="用户昵称" class="nav-search-input" id="user_name" autocomplete="off" name="user_name" value="${pd.user_name}" />
											</span>
										</div>
									</td>
									</tr>
									<tr style="margin:2px">
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												提现时间:
											</span>
											<span  >
												<input name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
											</span>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													提现状态:
												</span>
										 	<select  name="status" id="status" data-placeholder="请选择" value="${pd.status }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="0" <c:if test="${pd.status!=NULL && pd.status!='' && pd.status == 0}">selected</c:if>>待审核</option>
											<option value="1" <c:if test="${pd.status==1}">selected</c:if>>通过</option>
											<option value="2" <c:if test="${pd.status==2}">selected</c:if>>拒绝</option>
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
									<th class="center" style="width:50px;">序号</th>
									<th class="center">提现编号</th>
<!-- 									<th class="center">交易流水号</th> -->
<!-- 									<th class="center">用户昵称</th> -->
									<th class="center">真实姓名</th>
									<th class="center">电话</th>
									<th class="center">提现金额</th>
									<th class="center">银行名称</th>
									<th class="center">卡号</th>
									<th class="center">申请提现时间</th>
									<th class="center">状态</th>
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
											<td class='center'>	<a title="详情" style="cursor:pointer" onclick="edit('${var.id}');">${var.withdrawal_sn}</a></td>
<%-- 											<td class='center'>${var.withdrawal_sn}</td> --%>
<%-- 											<td class='center'>${var.user_name}</td> --%>
											<td class='center'>${var.real_name}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.amount}</td>
											<td class='center'>${var.bank_name}</td>
											<td class='center'>${var.card_no}</td>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
												<td class='center'> 
													<c:choose>
													<c:when test="${var.status==1}"><lable style="color:green">通过</lable></c:when>
													<c:when test="${var.status==2}"><lable style="color:red;font-weight:bold">拒绝</lable></c:when>
													<c:otherwise><lable style="color:orange;font-weight:bold">待审核</lable></c:otherwise>
												</c:choose>
											</td>
											
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
														<c:choose>
															<c:when test="${var.status == 0 }">
																<a class="btn btn-xs btn-success" title="审核" style="border-radius:5px;" onclick="edit('${var.id}');">审核</a>
															</c:when>
															<c:when test="${var.status == 1 }">
																<a  title="详情" style="cursor:pointer" onclick="edit('${var.id}');">详情</a>
															</c:when>
															<c:when test="${var.status == 2 }">
																<a title="详情" style="cursor:pointer" onclick="edit('${var.id}');">详情</a>
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
<!-- 								<lable style="color:red">￥:</lable> -->
								总计:提现成功  ${successAmount}元，提现失败 ${failAmount}元，提现未完成${unfinished}元。
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
						</div> <!-- /.col -->
					</div> <!-- /.row -->
				</div>  <!-- /.page-content -->
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

			function tosearch(status){		//检索
			if(status==0){
				$("#user_name").val("");
				$("#mobile").val("");
				$("#withdrawal_sn").val("");
				$("#status").empty();
				$("#lastStart").val("");
				$("#lastEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
 
		function edit(Id){	//修改
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>userwithdraw/goEdit.do?id='+Id;
			 diag.Width =800;
			 diag.Height = 280;
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
	</script>
</body>
</html>