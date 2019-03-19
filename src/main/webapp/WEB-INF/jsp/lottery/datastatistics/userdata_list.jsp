﻿<%@page import="com.fh.util.DateUtil"%>
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
						<div class="col-xs-12"  style="overflow:scroll;">
							
						<!-- 检索  -->
						<form action="userdata/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<%--<td ><span class="input-icon" style="width: 70px;"> 累计消费：</span></td>--%>
								<%--<td style="padding-left:2px;"><input   name="totalStart" id="totalStart"  value="${pd.totalStart }" type="number"  --%>
									 <%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<%--<td>—</td>--%>
								<%--<td style="padding-left:2px;"><input   name="totalEnd" id="totalEnd"  value="${pd.totalEnd }" type="number" --%>
									 <%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<%--<td><span class="input-icon" style="width: 70px;"> 累计充值：</span></td>--%>
								<%--<td style="padding-left:2px;"><input   name="rtotalStart" id="rtotalStart"  value="${pd.rtotalStart }" type="number"  --%>
								 	<%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<%--<td>—</td>--%>
								<%--<td style="padding-left:2px;"><input   name="rtotalEnd" id="rtotalEnd"  value="${pd.rtotalEnd }" type="number" --%>
								 	<%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<td><span class="input-icon" style="width: 60px;"> 日期：</span></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:100px;" placeholder="注册开始日期" title="注册开始日期"/></td>
								<td>至</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:100px;" placeholder="注册结束日期" title="注册结束日期"/></td>
								<span class="input-icon" style="width: 30px;"> </span>
								<td style="width:70px;">app名称:</td>
								<td>
									<select name="app_code_name" id="app_code_name"  title = "app名称" onchange="change1(this.value)">
										<option></option>
									</select>
								</td>
								<td style="width:12px;"></td>
								<td style="width:100px;">app渠道名称:</td>
								<td>
									<select id="channel" title = "app渠道"  name="channel">
										<option></option>
									</select>
								</td>
								<td style="vertical-align: top; padding-left: 2px;">
									<span class="input-icon" style="width: 30px;"> </span>
									<a class="btn btn-light btn-xs blue" onclick="toExcel();" title="导出EXCEL" style="border-radius: 5px; color: blue !important; width: 70px"> 导出EXCEL</a>
								</td>
							</tr>
							<tr>	
								<%--<td><span class="input-icon" style="width: 70px;">累计中奖： </span></td>--%>
								<%--<td style="padding-left:2px;"><input   name="atotalStart" id="atotalStart"  value="${pd.atotalStart }" type="number"  --%>
								 	<%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<%--<td>—</td>--%>
								<%--<td style="padding-left:2px;"><input   name="atotalEnd" id="atotalEnd"  value="${pd.atotalEnd }" type="number" --%>
								 	<%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<%--<td><span class="input-icon" style="width: 70px;"> 账户余额：</span></td>--%>
								<%--<td style="padding-left:2px;"><input   name="moneyStart" id="moneyStart"  value="${pd.moneyStart }" type="number"  --%>
								 	<%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<%--<td>—</td>--%>
								<%--<td style="padding-left:2px;"><input   name="moneyEnd" id="moneyEnd"  value="${pd.moneyEnd }" type="number" --%>
								 	<%--style="width:100px;border-radius: 5px !important;"  /></td>--%>
								<td><span class="input-icon" style="width: 70px;"> 手机号：</span></td>
								<td style="padding-left:2px;"><input   name="mobile" id="mobile"  value="${pd.mobile }" type="text"  
								 	style="width:100px;border-radius: 5px !important;"  /></td>
								<td><span class="input-icon" style="width: 30px;"> </span></td>
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align: bottom;; padding-left: 2px">
									<span class="input-icon" style="width: 30px;"> </span> 
									<a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="搜索"
												style="border-radius: 5px; color: blue !important; width: 50px">搜索</a>
									</td>
								</c:if>
								<td style="vertical-align: top; padding-left: 2px">
								<span class="input-icon" style="width: 30px;"> </span>
								 <a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="清空"
											style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover table-condensed" style="margin-top:5px;min-width:1500px;">	
							<thead>
								<tr>
									<%--<th class="center">用户ID</th>--%>
									<%--<th class="center">用户昵称</th>--%>
									<%--<th class="center">级别</th>--%>
									<th class="center">手机号</th>
									<%--<th class="center">真实姓名</th>--%>
									<%--<th class="center">身份证号</th>--%>
									<%--<th class="center">性别</th>--%>
									<%--<th class="center">年龄</th>--%>
									<%--<th class="center">终端</th>--%>
									<th class="center">地域</th>
									<th class="center">渠道</th>
									<th class="center">累计消费</th>
									<th class="center">累计充值</th>
									<th class="center">累计中奖</th>
									<th class="center">账户余额</th>
									<th class="center">注册时间</th>
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
											<%--<td width="50" class='center'>${var.user_id}</td>--%>
											<%--<td class='center'>${var.nickname}</td>--%>
											<%--<td class='center'>*</td>--%>
											<td class='center'>${var.mobile}</td>
											<%--<td class="center">${var.real_name}</td>--%>
											<%--<td style="word-wrap:break-word;" class="center">${var.id_code}</td>--%>
											<%--<td class="center"><c:if test="${var.sex == 2 }">女</c:if><c:if test="${var.sex == 1 }">男</c:if></td>--%>
											<%--<td class="center">${var.age}</td>--%>
											<%--<td class="center">${var.brand}</td>--%>
											<td class="center">${var.area}</td>
											<td class="center">
											${var.phone_channel }
											<c:if test="${empty var.phone_channel}">
											${var.device_channel }
											</c:if>
											</td>
											<td class="center">${var.total }</td>
											<td class="center">${var.rtotal}</td>
											<td class="center">${var.atotal}</td>
											<td class="center">${ var.user_money_limit + var.user_money }</td>
											<td class='center'>${DateUtil.toSDFTime(var.reg_time*1000)}</td>
											<td class='center'>
												<a class="btn btn-xs btn-success" title="查看" onclick="see('mobile=' + '${var.mobile}' + '');">
													<i class="ace-icon fa fa-pencil-square-o bigger-120" title="查看">查看</i>
												</a>
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
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(status){
			if (status == 0) {
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#atotalStart").val("");
				$("#mobile").val("");
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
			
			 
		});


        function see(str){

            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="";
            diag.URL = '<%=basePath%>usermanagercontroller/seeTotal.do?' + str;
            /**
             diag.Width = 450;
             diag.Height = 355;
             **/
            diag.Width = 950;
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

        //初始第一级
        $(function() {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>switchappconfig/getLevels.do?tm='+new Date().getTime(),
                data: {},
                dataType:'json',
                cache: false,
                success: function(data){
                    //$("#app_name").html('<option>app名称必选</option>');
                    $.each(data.list, function(i, dvar){
                        $("#app_code_name").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
                    });
                }
            });
        });
        //第一级值改变事件(初始第二级)
        function change1(value){
            $.ajax({
                type: "POST",
                url: '<%=basePath%>switchappconfig/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:value},
                dataType:'json',
                cache: false,
                success: function(data){
                    $("#channel").html('<option>app下载渠道必选</option>');
                    $.each(data.list, function(i, dvar){
                        $("#channel").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
                    });
                }
            });
        }


		//导出excel
		function toExcel(){
			//window.location.href='<%=basePath%>matchdata/excel.do';
			 $("#Form").attr("action","userdata/excel.do").submit();
			 $("#Form").attr("action","userdata/list.do");

		}
		
	</script>


</body>
</html>