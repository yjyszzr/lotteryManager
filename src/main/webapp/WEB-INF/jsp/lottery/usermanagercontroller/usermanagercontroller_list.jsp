<%@page import="com.fh.util.DateUtil"%>
<%@page import="com.fh.util.MoneyUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						<form action="usermanagercontroller/list.do" method="post" name="Form" id="Form">
						
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												真实姓名:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="真实姓名" class="nav-search-input" id="real_name" autocomplete="off" name="real_name" value="${pd.real_name }"  />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												身份证号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="身份证号" class="nav-search-input" id="id_code" autocomplete="off" name="id_code" value="${pd.id_code}" />
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
												注册时间:
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
										 	<select  name="user_status" id="user_status" data-placeholder="请选择" value="${pd.user_status }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="0" <c:if test="${pd.user_status!=NULL && pd.user_status!='' && pd.user_status == 0}">selected</c:if>>正常</option>
											<option value="1" <c:if test="${pd.user_status==1}">selected</c:if>>锁定</option>
											<option value="2" <c:if test="${pd.user_status==2}">selected</c:if>>冻结</option>
										  	</select>
										  	</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													实名认证:
												</span>
										 	<select  name="is_real" id="is_real" data-placeholder="请选择" value="${pd.is_real }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="0" <c:if test="${pd.is_real!=NULL && pd.is_real!='' && pd.is_real == 0}">selected</c:if>>未认证</option>
											<option value="1" <c:if test="${pd.is_real==1}">selected</c:if>>已认证</option>
										  	</select>
										  	</div>
									</td>
									</tr>
									<tr style="margin:2px">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												昵称:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="昵称" class="nav-search-input" id="nickname" autocomplete="off" name="nickname" value="${pd.nickname}" />
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
									<th class="center" style="width:50px;">序号</th>
									<th class="center">用户昵称</th>
									<th class="center">手机号</th>
									<th class="center">真实姓名</th>
									<th class="center">身份证号</th>
									<th class="center">累计消费</th>
									<th class="center">累计充值</th>
									<th class="center">累计中奖</th>
									<th class="center">账户余额</th>
									<th class="center">注册时间</th>
									<th class="center">最后登录时间</th>
									<th class="center">状态</th>
									<th class="center">注册来源</th>
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
											<td class='center'><a onclick="toDetail('${var.user_id}');" style=" cursor:pointer;">${var.nickname}</a></td>
											<td class='center'>${var.mobile}</td>
											<td class="center">${var.real_name}</td>
											<td class="center">${var.id_code}</td>
											<td class="center"><c:if test="${var.total == 0 }">${var.total }</c:if><c:if test="${var.total != 0 }"><a onclick="toConsumeDetail('${var.user_id}');" style=" cursor:pointer;" title="消费详情">${-var.total }</a></c:if></td>
											<td class="center">${var.rtotal}</td>
											<td class="center">${var.atotal}</td>
											<td class="center">${ var.user_money_limit + var.user_money }</td>
											<td class='center'>${DateUtil.toSDFTime(var.reg_time*1000)}</td>
											<td class='center'>${DateUtil.toSDFTime(var.last_time*1000)}</td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.user_status == 0 }">正常</c:when>
													<c:when test="${var.user_status == 1 }"><lable style = "color:yellow">锁定</c:when>
													<c:when test="${var.user_status == 2 }"><lable style = "color:red">冻结</c:when>
												</c:choose>
											</td>
											<td class='center'>${var.reg_from}</td>
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
<%-- 									<c:if test="${QX.add == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-success" onclick="add();">新增</a> -->
<%-- 									</c:if> --%>
<%-- 									<c:if test="${QX.del == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a> -->
<%-- 									</c:if> --%>
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(status){
			if(status==0){
				$("#real_name").val("");
				$("#mobile").val("");
				$("#id_code").val("");
				$("#nickname").val("");
				$("#user_status").empty();
				$("#is_real").empty();
				$("#lastStart").val("");
				$("#lastEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		function toConsumeDetail(userId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="消费详情";
			 diag.URL = '<%=basePath%>usermanagercontroller/toConsumeDetail.do?user_id='+userId;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
  				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch(0);
				}  
				diag.close();
			 };
			 diag.show();
		}		
		
		
		function toDetail(userId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="用户详情";
			 diag.URL = '<%=basePath%>usermanagercontroller/toDetail.do?user_id='+userId;
			 diag.Width = 800;
			 diag.Height = 400;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
  				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch(0);
				}  
				diag.close();
			 };
			 diag.show();
		}		
  
		
// 		//用户详情页
// 		function toDetail(userId){
<%-- 			window.location.href='<%=basePath%>usermanagercontroller/toDetail.do?user_id='+userId; --%>
// 		}
		
	</script>


</body>
</html>