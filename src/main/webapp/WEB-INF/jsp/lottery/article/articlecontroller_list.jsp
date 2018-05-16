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
						<form action="articlecontroller/list.do" method="post" name="Form" id="Form">
						<div id="zhongxin" style="padding-top: 13px;">
												<input type="hidden"  id="match_id" autocomplete="off" name="match_id" value="${pd.match_id }"  />
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												文章ID:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="文章ID" class="nav-search-input" id="article_id" autocomplete="off" name="article_id" value="${pd.article_id }"  onkeyup="value=value.replace(/[^\d]/g,'')" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												标题:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="标题" class="nav-search-input" id="title" autocomplete="off" name="title" value="${pd.title}" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													状态:
												</span>
										 	<select  name="status" id="status" data-placeholder="请选择" value="${pd.status }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
											<option value="1" <c:if test="${pd.status==1}">selected</c:if>>已发布</option>
											<option value="2" <c:if test="${pd.status==2}">selected</c:if>>草稿</option>
											<option value="4" <c:if test="${pd.status==4}">selected</c:if>>已过期</option>
											<option value="5" <c:if test="${pd.status==5}">selected</c:if>>已删除</option>
										  	</select>
										  	</div>
									</td>
									</tr>
									<tr style="margin:2px">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												作者:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="作者" class="nav-search-input" id="author" autocomplete="off" name="author" value="${pd.author}" />
											</span>
										</div>
									</td>
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												发布时间:
											</span>
											<span  >
												<input name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
											</span>
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
<!-- 									<th class="center" style="width:35px;"> -->
<!-- 									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label> -->
<!-- 									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">文章ID</th>
									<th class="center" width="300px">文章标题</th>
									<th class="center">发布时间</th>
									<th class="center">文章状态</th>
									<th class="center">作者</th>
<!-- 									<th class="center">标签</th> -->
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
<%-- 												<c:if test="${var.status==2}"> --%>
<%-- 													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.article_id}" class="ace" /><span class="lbl"></span></label> --%>
<%-- 												</c:if> --%>
<!-- 											</td> -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.article_id}</td>
											<c:choose>
												<c:when test="${fn:length(var.title)  <= 20 }">
													<td  title="${var.title}"><a style="cursor:pointer;"  onclick="toPreShow('${var.article_id}');">${var.title}</a></td>
												</c:when>
												<c:otherwise>
													<td title="${var.title}"> <a style="cursor:pointer;" onclick="toPreShow('${var.article_id}');">${fn:substring(var.title,0,20)}... </a></td>
												</c:otherwise>
											</c:choose>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center'> 
											<c:choose>
													<c:when test="${var.status==1}">已发布</c:when>
													<c:when test="${var.status==2}">草稿</c:when>
													<c:when test="${var.status==4}">已过期</c:when>
													<c:when test="${var.status==5}">已删除</c:when>
												</c:choose>
											</td>
											<td class='center'>${var.author}</td>
<%-- 											<td class='center'>${var.related_team}</td> --%>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
														<c:choose>
															<c:when test="${var.status==1}"> 
																<a class="btn btn-xs btn-success" title="下架" style="border-radius: 5px;" onclick="onOrOffLine('2','${var.article_id}');"> 下架</a>
																	<c:choose>
																		<c:when test="${var.is_stick==1}"> 
																			<a class="btn btn-xs " title="取消置顶" style="border-radius: 5px; color:#yellow" onclick="isStickOrNot('0','${var.article_id}');">取消置顶</a>
																		</c:when>
																		<c:otherwise>
																			<a class="btn btn-xs " title="置顶" style="border-radius: 5px; color:#yellow" onclick="isStickOrNot('1','${var.article_id}');"> 置顶</a>
																		</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${var.status==2}">
																<c:if test="${QX.edit == 1 }">
																<a class="btn btn-xs btn-primary" title="上架" style="border-radius: 5px;" onclick="onOrOffLine('1','${var.article_id}');"> 上架</a>
																<a class="btn btn-xs btn-info" title="编辑" style="border-radius: 5px;" onclick="edit('${var.article_id}');"> 编辑</a>
																<a class="btn btn-xs btn-default" title="过期" style="border-radius: 5px;" onclick="onOrOffLine('4','${var.article_id}');">过期</a>
																</c:if>
																<c:if test="${QX.del == 1 }">
																		<a class="btn btn-xs btn-danger" style="border-radius: 5px;"  onclick="onOrOffLine('5','${var.article_id}');">删除</a>
																</c:if>
															</c:when>
															<c:when test="${var.status==4}">
																<c:if test="${QX.edit == 1 }">
																		<a class="btn btn-xs btn-warning" title="恢复" style="border-radius: 5px;" onclick="onOrOffLine('2','${var.article_id}');"> 恢复</a>
																		<a class="btn btn-xs btn-danger" style="border-radius: 5px;"  onclick="onOrOffLine('5','${var.article_id}');">删除</a>
																</c:if>
															</c:when>
															<c:when test="${var.status==5}">
																<c:if test="${QX.edit == 1 }">
																		<a class="btn btn-xs btn-warning" title="恢复" style="border-radius: 5px;" onclick="onOrOffLine('2','${var.article_id}');"> 恢复</a>
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
									<a class="btn btn-mini btn-success"  style="border-radius: 5px;"  onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
<!-- 									<a class="btn btn-mini btn-danger" style="border-radius: 5px;"  onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >批量删除</a> -->
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
						</div> <!-- /.col -->
					</div> <!-- /.row -->
				</div> <!-- /.page-content -->
			</div>
		</div> <!-- /.main-content -->
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
	<script src="static/ace/js/My97Date/WdatePicker.js"</script>	
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
				function tosearch(status){
			if(status==0){
				$("#title").val("");
				$("#author").val("");
				$("#article_id").val("");
				$("#status").empty();
				$("#lastStart").val("");
				$("#lastEnd").val("");
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
			var match_id = $("#match_id").val();
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>articlecontroller/goAdd.do?match_id='+match_id;
			 diag.Width = 800;
			 diag.Height = 800;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch(0);
					 }else{
						 tosearch(0);
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
					var url = "<%=basePath%>articlecontroller/delete.do?article_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch(0);
					});
				}
			});
		}
		//上架和下架 1-已发布 2-草稿箱
		function onOrOffLine(status,articleId){   
				top.jzts();
				if(status==2){
					//下架时顺便取消置顶
				var url = "<%=basePath%>articlecontroller/stickAndOnLine.do?article_id="+articleId+"&tm="+new Date().getTime()+"&status="+status+"&is_stick=0";
				}else{
				var url = "<%=basePath%>articlecontroller/stickAndOnLine.do?article_id="+articleId+"&tm="+new Date().getTime()+"&status="+status;
				}
				$.get(url,function(data){
					tosearch(0);
				});
		}
		 
		//置顶&&取消置顶   0 - 未置顶 1-已置顶
		function isStickOrNot(stick,articleId){
				top.jzts();
				var url = "<%=basePath%>articlecontroller/stickAndOnLine.do?article_id="+articleId+"&tm="+new Date().getTime()+"&is_stick="+stick;
				$.get(url,function(data){
					tosearch(0);
				});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>articlecontroller/goEdit.do?article_id='+Id;
			 diag.Width = 800;
			 diag.Height = 800;
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
								url: '<%=basePath%>articlecontroller/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch(0);
									 });
								}
							});
						}
					}
				}
			});
		};
		
		function toPreShow(id){
			var url = "<%=basePath%>articlecontroller/toPreShow.do";
			$.ajax({
				  type: 'POST',
				  url: url,
				  data: {article_id:id},
				  dataType: "json",
				  success: function(result){
						if(result){
							var toPreShowContent =  result.pd.content;
							var str = '<style type="text/css">#mydiv{margin:0 auto;  text-align:center; width:375px; height:667px; border:1px solid red; overflow:hidden; } #mydiv img{ max-width:375px; max-height:200px;overflow:hidden; }</style>';
							var toPreShowContentValue= str+"<div id='mydiv'  style='OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +  toPreShowContent +"</div>";
								bootbox.confirm(toPreShowContentValue, function(result) {});
						}
				 	 }
				});
		}
	 
	</script>
</body>
</html>