<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<link rel="stylesheet" href="static/ace/css/jquery.cxcalendar.css" />
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
					
					<form action="deliveryvehicle/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_vehicle_work_id" id="delivery_vehicle_work_id" value="${pd.delivery_vehicle_work_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">配送组织名称:</td>
								<td>
								    <select name="delivery_org_id" id="delivery_org_id" style="width:98%;" onchange="changeCompanyList(this.value);">
										<option value="">请选择配送组织</option>
										<c:forEach items="${pd.orgList}" var="org">
											<option id="${org.delivery_org_id}" value="${org.delivery_org_id}" <c:if test="${pd.delivery_org_id == org.delivery_org_id}">selected</c:if>>${org.delivery_org_name}</option>
										</c:forEach>
									</select>
									<input type="hidden" name="delivery_org_name" id="delivery_org_name" value="${pd.delivery_org_name}"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">配送日期:</td>
								<td>
								    <input id="work_day" name="work_day" value="${pd.work_day}" type="text" readonly="readonly" style="width:98%;" placeholder="配送日期" title="配送日期"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">车牌号:</td>
								<td><input type="text" name="vehicle_number" id="vehicle_number" value="${pd.vehicle_number}" maxlength="50" placeholder="这里输入车牌号" title="车牌号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">类型:</td>
								<td>
								    <select class="chosen-select form-control" name="type" id="type" value="${pd.type}" onchange="clearCompanySelect(this.value);" style="width:98%;">
										<c:forEach items="${pd.types}" var="ts">
											<option id="${ts.type_id}" value="${ts.type_id}" <c:if test="${pd.type == ts.type_id}">selected</c:if>>${ts.type_name}</option>
										</c:forEach>
								  	</select>
							    </td>
							</tr>
							<tr id="ownCompany">
								<td style="width:75px;text-align: right;padding-top: 13px;">所属公司名称:</td>
								<td>
								    <select name="delivery_vehicle_company_id" id="delivery_vehicle_company_id" style="width:98%;" onchange="getCompanyData(this.value);">
										<option value="">请选择所属公司</option>
										<c:forEach items="${pd.companyList}" var="company">
											<option id="${company.delivery_vehicle_company_id}" value="${company.delivery_vehicle_company_id}" <c:if test="${pd.delivery_vehicle_company_id == company.delivery_vehicle_company_id}">selected</c:if>>${company.delivery_vehicle_company_name}</option>
										</c:forEach>
									</select>
									<input type="hidden" name="delivery_vehicle_company_name" id="delivery_vehicle_company_name" value="${pd.delivery_vehicle_company_name}"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td><input type="text" name="contact" id="contact" value="${pd.contact}" maxlength="50" placeholder="这里输入联系人" title="联系人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系电话:</td>
								<td><input type="text" name="tel" id="tel" value="${pd.tel}" maxlength="50" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准金额:</td>
								<td><input type="text" name="standard_amount" id="standard_amount" value="${pd.standard_amount}" maxlength="12" placeholder="这里输入标准金额" title="标准金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准时间:</td>
								<td><input type="text" name="standard_time" id="standard_time" value="${pd.standard_time}" maxlength="12" placeholder="这里输入标准时间" title="标准时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准里程:</td>
								<td><input type="text" name="standard_mileage" id="standard_mileage" value="${pd.standard_mileage}" maxlength="12" placeholder="这里输入标准里程" title="标准里程" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准时间外金额:</td>
								<td><input type="text" name="out_time_amount" id="out_time_amount" value="${pd.out_time_amount}" maxlength="12" placeholder="这里输入标准时间外金额" title="标准时间外金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准里程外金额:</td>
								<td><input type="text" name="out_mileage_amount" id="out_mileage_amount" value="${pd.out_mileage_amount}" maxlength="12" placeholder="这里输入标准里程外金额" title="标准里程外金额" style="width:98%;"/></td>
							</tr>
							<!-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
									<select class="chosen-select form-control" name="status" id="status" value="${pd.status}" style="width:98%;">
										<option value="0">未上岗</option>
										<option value="1">配送中</option>
										<option value="2">已完成</option>
								  	</select>
							    </td>
							</tr> -->
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
	<script src="static/ace/js/jquery.cxcalendar.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请输入配送组织名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			if($("#work_day").val()==""){
				$("#work_day").tips({
					side:3,
		            msg:'请输入配送日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#work_day").focus();
			return false;
			}
			if($("#vehicle_number").val()==""){
				$("#vehicle_number").tips({
					side:3,
		            msg:'请输入车牌号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#vehicle_number").focus();
			return false;
			}
			if($("#type").val()=='1' && $("#delivery_vehicle_company_id").val()==""){
				$("#delivery_vehicle_company_id").tips({
					side:3,
		            msg:'请输入所属公司',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_vehicle_company_id").focus();
			return false;
			}
			if($("#contact").val()==""){
				$("#contact").tips({
					side:3,
		            msg:'请输入联系人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#contact").focus();
			return false;
			}
			if($("#tel").val()==""){
				$("#tel").tips({
					side:3,
		            msg:'请输入联系电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tel").focus();
			return false;
			}
			if($("#standard_amount").val()==""){
				$("#standard_amount").tips({
					side:3,
		            msg:'请输入标准金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_amount").focus();
			return false;
			}
			var standard_amount = $("#standard_amount").val().trim();
			var standard_amount_replace = standard_amount.replace(/[^\d\.]/g, '');
            if((standard_amount !=null&&standard_amount !="")&&(standard_amount_replace ==null||standard_amount_replace =="")){
				 $("#standard_amount").tips({
  	                side:3,
	                msg:'标准金额只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#standard_amount").val("");
			     return false;
			}
            if((standard_amount !=null&&standard_amount !="")&&(standard_amount_replace !=null||standard_amount_replace !="")&&(parseInt(standard_amount)<=0)){
				 $("#standard_amount").tips({
  	                side:3,
	                msg:'标准金额只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#standard_amount").val("");
			     return false;
			}
			if($("#standard_time").val()==""){
				$("#standard_time").tips({
					side:3,
		            msg:'请输入标准时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_time").focus();
			return false;
			}
			var standard_time = $("#standard_time").val().trim();
			var standard_time_replace = standard_time.replace(/[^\d\.]/g, '');
            if((standard_time !=null&&standard_time !="")&&(standard_time_replace ==null||standard_time_replace =="")){
				$("#standard_time").tips({
			        side:3,
	                msg:'标准时间只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#standard_time").val("");
			     return false;
			}
			if((standard_time !=null&&standard_time !="")&&(standard_time_replace !=null||standard_time_replace !="")&&(parseInt(standard_time)<=0)){
				 $("#standard_time").tips({
			        side:3,
	                msg:'标准时间只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#standard_time").val("");
			     return false;
			}
			if($("#standard_mileage").val()==""){
				$("#standard_mileage").tips({
					side:3,
		            msg:'请输入标准里程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_mileage").focus();
			return false;
			}
			var standard_mileage = $("#standard_mileage").val().trim();
			var standard_mileage_replace = standard_mileage.replace(/[^\d\.]/g, '');
            if((standard_mileage !=null&&standard_mileage !="")&&(standard_mileage_replace ==null||standard_mileage_replace =="")){
				$("#standard_mileage").tips({
			        side:3,
	                msg:'标准里程只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#standard_mileage").val("");
			     return false;
			}
			if((standard_mileage !=null&&standard_mileage !="")&&(standard_mileage_replace !=null||standard_mileage_replace !="")&&(parseInt(standard_mileage)<=0)){
				 $("#standard_mileage").tips({
			        side:3,
	                msg:'标准里程只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#standard_mileage").val("");
			     return false;
			}
			if($("#out_time_amount").val()==""){
				$("#out_time_amount").tips({
					side:3,
		            msg:'请输入标准时间外金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#out_time_amount").focus();
			return false;
			}
			var out_time_amount = $("#out_time_amount").val().trim();
			var out_time_amount_replace = out_time_amount.replace(/[^\d\.]/g, '');
            if((out_time_amount !=null&&out_time_amount !="")&&(out_time_amount_replace ==null||out_time_amount_replace =="")){
				$("#out_time_amount").tips({
			        side:3,
	                msg:'标准时间外金额只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#out_time_amount").val("");
			     return false;
			}
			if((out_time_amount !=null&&out_time_amount !="")&&(out_time_amount_replace !=null||out_time_amount_replace !="")&&(parseInt(out_time_amount)<=0)){
				 $("#out_time_amount").tips({
			        side:3,
	                msg:'标准时间外金额只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#out_time_amount").val("");
			     return false;
			}
			if($("#out_mileage_amount").val()==""){
				$("#out_mileage_amount").tips({
					side:3,
		            msg:'请输入标准里程外金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#out_mileage_amount").focus();
			return false;
			}
            var out_mileage_amount = $("#out_mileage_amount").val().trim();
			var out_mileage_amount_replace = out_mileage_amount.replace(/[^\d\.]/g, '');
            if((out_mileage_amount !=null&&out_mileage_amount !="")&&(out_mileage_amount_replace ==null||out_mileage_amount_replace =="")){
				$("#out_mileage_amount").tips({
			        side:3,
	                msg:'标准里程外金额只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#out_mileage_amount").val("");
			     return false;
			}
			if((out_mileage_amount !=null&&out_mileage_amount !="")&&(out_mileage_amount_replace !=null||out_mileage_amount_replace !="")&&(parseInt(out_mileage_amount)<=0)){
				 $("#out_mileage_amount").tips({
			        side:3,
	                msg:'标准里程外金额只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#out_mileage_amount").val("");
			     return false;
			}
			if($("#type").val()==""){
				$("#type").tips({
					side:3,
		            msg:'请输入类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
			return false;
			}
			if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

        function changeCompanyList(Id){
            if(null == Id || Id.length <= 0){
				 $("#delivery_vehicle_company_id").removeAttr("disabled");
				 $("#delivery_vehicle_company_id").html("<option value=''>请选择所属公司</option>");
				 $("#contact").val("");
				 $("#tel").val("");
				 $("#standard_amount").val("");
				 $("#standard_time").val("");
				 $("#standard_mileage").val("");
				 $("#out_time_amount").val("");
				 $("#out_mileage_amount").val("");
				 $("#delivery_org_name").val("");
			}else{
				$.ajax({
					type: "POST",
					url: '<%=basePath%>deliveryvehicle/getCompanyList.do?tm='+new Date().getTime(),
			    	data: {delivery_org_id:Id},
					dataType:'json',
					cache: false,
					success: function(data){
					    $("#delivery_vehicle_company_id").removeAttr("disabled");
						$("#delivery_vehicle_company_id").html("<option value=''>请选择所属公司</option>");
						$.each(data.companyList, function(i, dvar){
							$("#delivery_vehicle_company_id").append("<option value="+dvar.delivery_vehicle_company_id+">"+dvar.delivery_vehicle_company_name+"</option>");
						});
						$("#contact").val("");
				 		$("#tel").val("");
						$("#standard_amount").val("");
						$("#standard_time").val("");
						$("#standard_mileage").val("");
						$("#out_time_amount").val("");
						$("#out_mileage_amount").val("");
						var delivery_org_name = $("#delivery_org_id").find("option:selected").text();
						$("#delivery_org_name").val(delivery_org_name);
					}
				});
			}
        }
        
        function getCompanyData(Id){
            if(null == Id || Id.length <= 0){
                 $("#contact").val("");
				 $("#tel").val("");
				 $("#standard_amount").val("");
				 $("#standard_time").val("");
				 $("#standard_mileage").val("");
				 $("#out_time_amount").val("");
				 $("#out_mileage_amount").val("");
				 $("#delivery_vehicle_company_name").val("");
			}else{
				$.ajax({
					type: "POST",
					url: '<%=basePath%>deliveryvehicle/getCompanyData.do?tm='+new Date().getTime(),
			    	data: {delivery_vehicle_company_id:Id},
					dataType:'json',
					cache: false,
					success: function(data){
					    $("#contact").val(data.company.contact);
				 		$("#tel").val(data.company.tel);
						$("#standard_amount").val(data.company.standard_amount);
						$("#standard_time").val(data.company.standard_time);
						$("#standard_mileage").val(data.company.standard_mileage);
						$("#out_time_amount").val(data.company.out_time_amount);
						$("#out_mileage_amount").val(data.company.out_mileage_amount);
						var delivery_vehicle_company_name = $("#delivery_vehicle_company_id").find("option:selected").text();
						$("#delivery_vehicle_company_name").val(delivery_vehicle_company_name);
					}
				});
			}
        }
        
        function clearCompanySelect(Id){
            if(Id == 1){
				 $("#ownCompany").show();
            }else{
                 $("#ownCompany").hide();
                 $("#contact").val("");
				 $("#tel").val("");
				 $("#standard_amount").val("");
				 $("#standard_time").val("");
				 $("#standard_mileage").val("");
				 $("#out_time_amount").val("");
				 $("#out_mileage_amount").val("");
            }
        }
		
		$(function() {
			// 选择日期和时间
			$('#work_day').cxCalendar();
			if($("#type").val()==1){
			    $("#ownCompany").show();
			}else{
				$("#ownCompany").hide();
			}
		});
		</script>
</body>
</html>