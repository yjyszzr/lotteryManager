<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.fh.util.DateUtil"%>
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
						<form action="activityBonusSH/shlist.do" method="post" name="Form" id="Form">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">序号</th>
<!-- 									<th class="center">券类型</th> -->
									<th class="center">金额</th>
									<th class="center">使用条件</th>
<!-- 									<th class="center">彩种限制</th> -->
									<th class="center">设置人</th>
									<th class="center">设置时间</th>
									<th class="center">有效期</th>
									<th class="center">所属礼包</th>
									<th class="center">派发数量</th>
									<th class="center">使用数量</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
<%-- 											<td class='center'>${var.bonus_id}</td> --%>
<!-- 											<td class='center'>  -->
<%-- 												<c:if test="${var.bonus_type==1 }">注册送红包</c:if> --%>
<%-- 												<c:if test="${var.bonus_type==2 }">西安活动红包</c:if> --%>
<%-- 												<c:if test="${var.bonus_type==3 }">充值活动红包</c:if> --%>
<%-- 												<c:if test="${var.bonus_type==4 }">指定赠送红包(用于派发)</c:if> --%>
<!-- 											</td> -->
											<td class='center'>${var.bonus_amount}</td>
											<td class='center'>${var.min_goods_amount}</td>
<!-- 											<td class='center'>  -->
<%-- 												<c:if test="${var.use_range==0}">通用</c:if> --%>
<!-- 											</td> -->
											<td class='center'>${var.addUser} </td>
											<td class='center'>
											<c:if test="${null != var.addTime  }">
												${DateUtil.toSDFTime(var.addTime*1000)}</td>
											</c:if>
											<td class='center'>${var.end_time}天</td>
											<td class='center'>${var.recharge_card_name}</td>
						

											<td class='center'>${var.receive_quantity}</td>
											<td class='center'>${var.use_count}</td>
											<td class="center">
<%-- 												<a class="btn btn-xs btn-success" title="编辑" style="border-radius: 5px;" onclick="edit('${var.bonus_id}');"> 编辑</a> --%>
												<a class="btn btn-xs btn-danger" style="border-radius: 5px;"  onclick="del('${var.bonus_id}');">删除</a>
											</td>
										</tr>
									
									</c:forEach>
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
									<a class="btn btn-mini btn-success" onclick="addBonus();" style="border-radius:5px；width:50px">新增</a>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
					
						</div>
					</div>
				</div>
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
				$("#is_enable").empty();
				$("#amountStart").val("");
				$("#amountEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		//新增
		function addBonus(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>activityBonusSH/goshAdd.do';
			 diag.Width = 700;
			 diag.Height = 410;
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
					var url = "<%=basePath%>activityBonusSH/delete.do?bonus_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>activityBonusSH/goEdit.do?bonus_id='+Id;
	 		 diag.Width = 700;
			 diag.Height = 410;
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
								url: '<%=basePath%>activityBonusSH/deleteAll.do?tm='+new Date().getTime(),
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
		
		//上架和下架 1-已发布 2-草稿箱
		function onOrOffLine(status,bonus_id){   
				top.jzts();
				var url = "<%=basePath%>activityBonusSH/onAndOnLine.do?bonus_id="+bonus_id+"&is_enable="+status;
				$.get(url,function(data){
					tosearch();
				});
		}
		</script>
	</body>
</html>