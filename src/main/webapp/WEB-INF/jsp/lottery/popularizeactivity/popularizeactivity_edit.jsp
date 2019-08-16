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
					<form action="popularizeactivity/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="act_id" id="act_id" value="${pd.act_id}"/>
						<input type="hidden" name="is_finish" id="is_finish" value="${pd.is_finish}"/>
						<input type="hidden" name="params" id="params" />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动名称:</td>
								<td><input type="text" name="act_name" id="act_name" value="${pd.act_name}" maxlength="255" placeholder="这里输入活动名称" title="活动名称" style="width:270px; border-radius:5px !important"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标题:</td>
								<td><input type="text" name="act_title" id="act_title" value="${pd.act_title}" maxlength="255" placeholder="这里输入标题" title="标题" style="width:270px; border-radius:5px !important"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动类型:</td>
								<td>
									<div>
									    <select  name="act_type" id="act_type" value="${pd.act_type}" style="width:270px;border-radius:5px !important"  onchange="changeInfo()">
									        <option value="3" <c:if test="${pd.act_type==3}">selected</c:if>>人数奖励</option>
									        <option value="4" <c:if test="${pd.act_type==4}">selected</c:if>>消费奖励</option>
									    </select>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td><input  type="text" name="start_time" id="start_time" value="${DateUtil.toSDFTime(pd.start_time*1000)}" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="活动开始时间" title="活动开始时间" style="width:270px;border-radius:5px !important"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td><input type="text" name="end_time" id="end_time" value="${DateUtil.toSDFTime(pd.end_time*1000)}"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="活动结束时间" title="活动结束时间" style="width:270px;border-radius:5px !important"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">奖励设置:</td>
								<td>
									<span class ="yaoqing">邀请人数:</span>
									<input type="text" name="number" id="number" value="${pd.number}" maxlength="32" title="邀请数量" style="width:75px;border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');"/>&nbsp;&nbsp;
									<span class ="yaoqingnum">奖励金额:</span>
									<input type="text" name="reward_money" id="reward_money" value="${pd.reward_money}" maxlength="12" title="奖励金额" style="width:75px;border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');"/>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td style="width:75px;text-align: right;padding-top: 13px;">邀请数量:</td> -->
<%-- 								<td><input type="text" name="number" id="number" value="${pd.number}" maxlength="32" placeholder="这里输入邀请数量" title="邀请数量" style="width:270px;border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');"/></td> --%>
<!-- 							</tr> -->
								<c:choose>
								<c:when test="${not empty configList}">
							<c:forEach items="${configList}" var="var" varStatus="vs">
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">额外奖励:</td>
									<td>
										<span class = "yaoqingewai" >邀请人数:</span>
										<input type="text" name="gear_position"   class = "gear_position" id="gear_position" style="width:50px; border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');" value = "${var.gear_position }"/>
										<span >额外奖励:</span>
										<input type="text" name="gear_position_money"   class = "gear_position_money" id="gear_position_money" style="width:50px; border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');" value = "${var.gear_position_money }"/>&nbsp;&nbsp;
										<c:if test="${vs.index == 0 }">
											<button type="button" class="btn btn-mini btn-success" style="border-radius:5px !important" id='btn'>添加</button>
										</c:if>
										<c:if test="${vs.index != 0 }">
											<button type="button" class="btn btn-mini btn-danger" style="border-radius:5px !important" onClick="sbtn(this)">删除</button>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td style="width:75px;text-align: center;padding-top: 13px;">额外奖励:</td>
									<td>
										<span class ="yaoqingewai">邀请人数:</span>
										<input type="text" name="gear_position"  class = "gear_position" id="gear_position" style="width:50px; border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');"/>
										<span  >额外奖励:</span>
										<input type="text" name="gear_position_money"  class = "gear_position_money" id="gear_position_money" style="width:50px; border-radius:5px !important" oninput="this.value = this.value.replace(/[^0-9]/g, '');"/>&nbsp;&nbsp;
										<button type="button" class="btn btn-mini btn-success" style="border-radius:5px !important" id='btn'>添加</button>
									</td>
								</tr>
							</c:otherwise>
							</c:choose>
							
						</table>
							<div style="text-align: center;">
								<a  style="border-radius: 5px;"  class="btn btn-mini btn-primary" onclick="save();">保存</a>
								<a  style="border-radius: 5px;"  class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
							</div>
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
		<script type="text/javascript">
		
		$.ready(changeInfo());
		
		function changeInfo(){
			var actType = $("#act_type").val();
			if(actType == 3){
				$(" .yaoqing").text("邀请人数:");
				$(" .yaoqingnum").text("奖励金额:");
				$(" .yaoqingewai").text("邀请人数:");
			}else if(actType == 4){
				$(" .yaoqing").text("消费奖励:");
				$(" .yaoqingnum").text("%,奖励上限:");
				$(" .yaoqingewai").text("累计金额:");
			}
		}
		$('#btn').on('click',function(){
			var stryq = "";
			var actType = $("#act_type").val();
			if(actType == 3){
			  stryq = '邀请人数:';
			}else if(actType == 4){
			  stryq = '累计金额:';
			}
			var str  = '<tr>';
				str += '<td style="width:75px;text-align: right;padding-top: 13px;">额外奖励:</td>';
				str += '<td>';
				str += '<span class ="yaoqingewai">'+stryq+'</span>';
			    str += '&nbsp;<input style="width:50px; border-radius:5px !important" type="text" class = "gear_position" name="gear_position" id="gear_position" oninput="this.value = this.value.replace(/[^0-9]/g, \'\');"/>';
		    	str += '<span>&nbsp;额外奖励:&nbsp;</span>';
		    	str += '<input style="width:50px; border-radius:5px !important" type="text"  class = "gear_position_money" name="gear_position_money" id="gear_position_money" oninput="this.value = this.value.replace(/[^0-9]/g, \'\');"/>&nbsp;&nbsp;&nbsp;';
		    	str += '<button type="button" class="btn btn-mini btn-danger" style="border-radius:5px !important" onClick="sbtn(this)">删除</button>';
	    		str += '</td>';
    			str += '</tr>';
			$('#table_report').append(str);
		})
		function sbtn(e){
			$(e).parent().parent().remove();
		}
		
		
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
			if($("#act_title").val()==""){
				$("#act_title").tips({
					side:3,
		            msg:'请输入标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#act_title").focus();
			return false;
			}
			if($("#act_type").val()==""){
				$("#act_type").tips({
					side:3,
		            msg:'请输入活动类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#act_type").focus();
			return false;
			}
			if($("#start_time").val()==""){
				$("#start_time").tips({
					side:3,
		            msg:'请输入开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#start_time").focus();
			return false;
			}
			if($("#end_time").val()==""){
				$("#end_time").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_time").focus();
			return false;
			}
			if($("#number").val()==""){
				$("#number").tips({
					side:3,
		            msg:'请输入邀请数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#number").focus();
			return false;
			}
			if($("#reward_money").val()==""){
				$("#reward_money").tips({
					side:3,
		            msg:'请输入奖励金',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#reward_money").focus();
			return false;
			}
	
			var params={} ;
			var gearPosition=new Array();//定义数组对象
			var gearPositionMoney=new Array();//定义数组对象
			var $inputArr = $('.gear_position');//获取class为gear_position的input对象
			var sum = 0;
			$inputArr.each(function(){
				if ($(this).val()=="") {
					sum++;
				}
				gearPosition.push($(this).val());//遍历存入数组
			});
				if (sum > 0) {
					alert("您有未输入项,请检查!");
					return;
				}
			var $inputArr1 = $('.gear_position_money');//获取class为gear_position_money的input对象
			$inputArr1.each(function(){
				if ($(this).val()=="") {
					sum++;
				}
				gearPositionMoney.push($(this).val());//遍历存入数组
			});
				if (sum > 0) {
					alert("您有未输入项,请检查!");
					return;
				}
		params['gear_position']=gearPosition; 
		params['gear_position_money']=gearPositionMoney; 
		var str = JSON.stringify(params);
		$("#params").val(str);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>