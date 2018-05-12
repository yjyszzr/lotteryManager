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
						<form action="activitybonus/list.do" method="post" name="Form" id="Form">
						<div id="zhongxin" style="padding-top: 13px;">
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
								
								<td>
											<span class="input-icon" style="width:80px;text-align:right;">
													金额范围:
												</span>
										 	<span  >
												<input name="amountStart" id="amountStart"  value="${pd.amountStart }" type="text"  style="width:74px;border-radius:5px !important" placeholder="0元"  onkeyup="value=value.replace(/[^\d]/g,'')" />
												<input name="amountEnd" id="amountEnd"  value="${pd.amountEnd}" type="text"  style="width:74px;border-radius:5px !important" placeholder="0元"  onkeyup="value=value.replace(/[^\d]/g,'')" />
											</span>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													状态:
												</span>
										 	<select  name="is_enable" id="is_enable" data-placeholder="请选择" value="${pd.is_enable }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="1" <c:if test="${pd.is_enable==1}">selected</c:if>>上线</option>
											<option value="0" <c:if test="${pd.is_enable==0}">selected</c:if>>下线</option>
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
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
<!-- 									<th class="center" style="width:50px;">序号</th> -->
									<th class="center">ID</th>
									<th class="center">券类型</th>
									<th class="center">金额</th>
									<th class="center">使用条件</th>
									<th class="center">彩种限制</th>
									<th class="center">生效时间</th>
									<th class="center">有效期</th>
									<th class="center">发放数量</th>
									<th class="center">已领取</th>
									<th class="center">已使用</th>
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
											<td class='center'>
												<c:if test="${var.is_enable == 0 }"> 
														<label class="pos-rel"><input type='checkbox' name='ids' value="${var.bonus_id}" class="ace" /><span class="lbl"></span></label>
												</c:if>
											</td>
											<td class='center'>${var.bonus_id}</td>
											<td class='center'> 
												<c:if test="${var.bonus_type==1 }">注册送红包</c:if>
											</td>
											<td class='center'>${var.bonus_amount}</td>
											<td class='center'>${var.min_goods_amount}</td>
											<td class='center'> 
												<c:if test="${var.use_range==0 }">通用</c:if>
											</td>
											<td class='center'>领取后第${var.start_time}天</td>
											<td class='center'>${var.end_time}天</td>
											<td class='center'>${var.bonus_number}</td>
											<td class='center'>${var.receive_quantity}</td>
											<td class='center'>${var.use_count}</td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.is_enable eq 1}">上线</c:when>
													<c:otherwise>下线</c:otherwise>
												</c:choose>
											</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<c:choose>
															<c:when test="${var.is_enable==1}"> 
																<a class="btn btn-xs btn-success" title="下架" style="border-radius: 5px;" onclick="onOrOffLine('0','${var.bonus_id}');"> 下架</a>
															</c:when>
															<c:when test="${var.is_enable==0}">
																<a class="btn btn-xs btn-success" title="上架" style="border-radius: 5px;" onclick="onOrOffLine('1','${var.bonus_id}');"> 上架</a>
																<a class="btn btn-xs btn-success" title="编辑" style="border-radius: 5px;" onclick="edit('${var.bonus_id}');"> 编辑</a>
																<c:if test="${QX.del == 1 }">
																		<a class="btn btn-xs btn-danger" style="border-radius: 5px;"  onclick="del('${var.bonus_id}');">删除</a>
																</c:if>
															</c:when>
														</c:choose>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.bonus_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.bonus_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
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
									<a class="btn btn-mini btn-success" onclick="add();" style="border-radius:5px ; width:50px">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" style="border-radius:5px ; width:70px" >批量删除</a>
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
				$("#is_enable").empty();
				$("#amountStart").val("");
				$("#amountEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>activitybonus/goAdd.do';
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
					var url = "<%=basePath%>activitybonus/delete.do?bonus_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>activitybonus/goEdit.do?bonus_id='+Id;
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
								url: '<%=basePath%>activitybonus/deleteAll.do?tm='+new Date().getTime(),
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
				var url = "<%=basePath%>activitybonus/onAndOnLine.do?bonus_id="+bonus_id+"&is_enable="+status;
				$.get(url,function(data){
					tosearch();
				});
		}
		</script>
	</body>
</html>