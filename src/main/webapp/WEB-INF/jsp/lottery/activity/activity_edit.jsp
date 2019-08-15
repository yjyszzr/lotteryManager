<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.fh.util.DateUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
					
					<form action="activity/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="act_id" id="act_id" value="${pd.act_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动名称:</td>
								<td><input type="text" name="act_name" id="act_name" value="${pd.act_name}" maxlength="255" placeholder="这里输入活动名称" title="活动名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动类型:</td>
								<td>
								<div class="col-sm-4">
								    <select  name="act_type" id="act_type" value="${pd.act_type}" style="width:204px;border-radius:5px !important">
								        <option value="0" <c:if test="${pd.act_type==0}">selected</c:if>>新手活动</option>
								        <option value="1" <c:if test="${pd.act_type==1}">selected</c:if>>充值活动</option>
								        <option value="2" <c:if test="${pd.act_type==2}">selected</c:if>>推广活动</option>
								    </select>
								</div>
							    </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动开始时间:</td>
								<td><input  name="start_time" id="start_time" value="${DateUtil.toSDFTime(pd.start_time*1000)}"  type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="活动开始时间" title="活动开始时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动结束时间:</td>
								
								<td><input  name="end_time" id="end_time" value="${DateUtil.toSDFTime(pd.end_time*1000)}" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="活动结束时间" title="活动结束时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#act_name").val()==""){
				$("#act_name").tips({
					side:3,
		            msg:'请输入活动名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#act_name").focus();
			return false;
			}
			if($("#act_type").val()==""){
				$("#act_type").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#act_type").focus();
			return false;
			}

			if($("#start_time").val()==""){
				$("#start_time").tips({
					side:3,
		            msg:'请输入活动开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#start_time").focus();
			return false;
			}
			if($("#end_time").val()==""){
				$("#end_time").tips({
					side:3,
		            msg:'请输入活动结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_time").focus();
			return false;
			}

            var message = '${msg}';
            console.log(message);

            if(message == "save"){
                var actType = $("#act_type").val();
                var s = $.ajax({
                    type: "POST",
                    url: '<%=basePath%>activity/checkType.do?t='+new Date().getTime()
                        + "&act_type=" + actType,
                    data: {},
                    dataType:'json',
                    cache: false,
                    success: function(data){

                        if (data.flag == false) {
                            alert(data.msg);
                            return false;
                        } else {
                            $("#Form").submit();
                            $("#zhongxin").hide();
                            $("#zhongxin2").show();
                        }
                    }
                });

            }else if(message == "edit"){
                $("#Form").submit();
                $("#zhongxin").hide();
                $("#zhongxin2").show();
            }
        }
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>