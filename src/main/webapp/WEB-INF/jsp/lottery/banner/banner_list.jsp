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
						<form action="banner/list.do" method="post" name="Form" id="Form">
							<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												标题:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="标题" class="nav-search-input" id="title" autocomplete="off" name="title" value="${pd.title }"/>
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													是否显示:
												</span>
										 	<select  name="is_show" id="is_show" data-placeholder="请选择" value="${pd.is_show }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="0" <c:if test="${pd.is_show!=NULL && pd.is_show!='' && pd.is_show == 0}">selected</c:if>>已过期</option>
											<option value="1" <c:if test="${pd.is_show==1}">selected</c:if>>已发布</option>
										  	</select>
										  	</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													是否显示:
												</span>
										 	<select  name="is_transaction" id="is_transaction" data-placeholder="请选择" value="${pd.is_transaction }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="1" <c:if test="${pd.is_transaction == 1}">selected</c:if>>资讯版</option>
											<option value="2" <c:if test="${pd.is_transaction == 2}">selected</c:if>>交易版</option>
										  	</select>
										  	</div>
									</td>
									</tr>
									<tr style="margin:2px">
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												投放开始:
											</span>
											<span  >
												<input name="startTimeStart" id="startTimeStart"  value="${pd.startTimeStart }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input name="startTimeEnd" id="startTimeEnd"  value="${pd.startTimeEnd}" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
											</span>
									</td>
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												投放结束:
											</span>
											<span  >
												<input name="endTimeStart" id="endTimeStart"  value="${pd.endTimeStart }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input name="endTimeEnd" id="endTimeEnd"  value="${pd.endTimeEnd }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
											</span>
									</td>
											<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													发布平台:
												</span>
										 	<select  name="app_code_name" id="app_code_name" data-placeholder="请选择" value="${pd.app_code_name }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="10" <c:if test="${pd.app_code_name == 10}">selected</c:if>>球多多</option>
											<option value="11" <c:if test="${pd.app_code_name == 11}">selected</c:if>>圣和彩店</option>
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
							</table> <!-- 检索结束 -->
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
<!-- 									<th class="center" style="width:35px;"> -->
<!-- 									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label> -->
<!-- 									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">ID</th>
									<th class="center">标题</th>
									<th class="center">图片</th>
									<th class="center">投放资源</th>
									<th class="center">排序字段</th>
									<th class="center">位置</th>
									<th class="center">发布平台</th>
									<th class="center">版本类型</th>
									<th class="center">状态</th>
									<th class="center">投放开始时间</th>
									<th class="center">投放结束时间</th>
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
<%-- 												<c:if test="${var.is_show==0}"> --%>
<%-- 												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label> --%>
<%-- 												</c:if> --%>
<!-- 											</td> -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.id}</td>
											<c:choose>
												<c:when test="${fn:length(var.banner_name)  <= 5 }">
													<td  title="${var.banner_name}">${var.banner_name}</td>
												</c:when>
												<c:otherwise>
													<td title="${var.banner_name}">  ${fn:substring(var.banner_name,0,5)}...  </td>
												</c:otherwise>
											</c:choose>
											<td class='center'><img src="${var.banner_image}" width="48px" hight="24px"/></td>
										
												<c:choose>
													<c:when test="${var.banner_param==1}"><td>(文章ID)${var.banner_link}</td></c:when>
													<c:when test="${var.banner_param==2}"><td>(赛事ID)${var.banner_link}</td></c:when>
													<c:when test="${var.banner_param==4}"><td>(小白课堂)${var.banner_link}</td></c:when>
													<c:otherwise>
															<c:choose>
															<c:when test="${fn:length(var.banner_link)  <= 15 }">
																<td  title="(活动URL)${var.banner_link}">(活动URL)${var.banner_link}</td>
															</c:when>
															<c:otherwise>
																<td title="(活动URL)${var.banner_link}">  (活动URL)${fn:substring(var.banner_link,0,15)}...  </td>
															</c:otherwise>
														</c:choose>
													 </c:otherwise>
												</c:choose>
											<td class='center'>${var.banner_sort}</td>
											<td class='center'>
												<c:if test="${var.show_position ==0}">首页轮播图</c:if>
											</td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.app_code_name==10}">球多多</c:when>
													<c:when test="${var.app_code_name==11}"> 圣和彩店</c:when>
													<c:otherwise>球多多</c:otherwise>
												</c:choose>
											</td>						
											<td class='center'> 
												<c:choose>
													<c:when test="${var.is_transaction==2}"> 交易版 </c:when>
													<c:when test="${var.is_transaction==1}"> 资讯版 </c:when>
													<c:otherwise>--</c:otherwise>
												</c:choose>
											</td>						
											<td class='center'> 
												<c:choose>
													<c:when test="${var.is_show==0}"><lable style="color:red">已过期</lable></c:when>
													<c:otherwise>已发布</c:otherwise>
												</c:choose>
											</td>						
											<td class='center'>${DateUtil.toSDFTime(var.start_time*1000)} </td>
											<td class='center'>${DateUtil.toSDFTime(var.end_time*1000)} </td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
												<c:if test="${var.is_show==0}">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-primary" title="发布" style="border-radius: 5px;" onclick="editStatus('1','${var.id}');">发布</a>
													<a class="btn btn-xs btn-success" style="border-radius: 5px;"  title="编辑" onclick="edit('${var.id}');">编辑 </a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger"   style="border-radius: 5px;"  onclick="editStatus('2','${var.id}');">删除</a>
													</c:if>
													</c:if>
												<c:if test="${var.is_show==1}">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-warming"   style="border-radius: 5px;"  onclick="editStatus('0','${var.id}');">置为过期</a>
												</c:if>
												</c:if>
												<c:if test="${var.is_show==2}">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs"   style="border-radius: 5px;color:#yellow"  onclick="editStatus('0','${var.id}');">恢复</a>
												</c:if>
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
									<a class="btn btn-mini btn-success"  style="border-radius: 5px;" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
<!-- 									<a class="btn btn-mini btn-danger" style="border-radius: 5px;" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >批量删除 </a> -->
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(status){
			if(status==0){
				$("#title").val("");
				$("#is_show").empty();
				$("#is_transaction").empty();
				$("#app_code_name").empty();
				$("#startTimeStart").val("");
				$("#startTimeEnd").val("");
				$("#endTimeStart").val("");
				$("#endTimeEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>banner/goAdd.do';
			 diag.Width = 800;
			 diag.Height = 520;
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
					var url = "<%=basePath%>banner/delete.do?id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>banner/goEdit.do?id='+Id;
			 diag.Width = 800;
			 diag.Height = 520;
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
								url: '<%=basePath%>banner/deleteAll.do?tm='+new Date().getTime(),
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
 function editStatus(status,id){
	var url = "<%=basePath%>banner/onOrOffLine.do?id="+id+"&is_show="+status;
	$.get(url,function(data){
		tosearch(0);
	});
 }
	</script>


</body>
</html>