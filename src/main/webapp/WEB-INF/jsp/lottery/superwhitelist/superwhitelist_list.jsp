<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
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
						<form action="superwhitelist/list.do" method="post" name="Form" id="Form">
							<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px">
									<td >
										<div class="nav-search">
											手机号:
										<span class="input-icon">
											<input type="text" placeholder="手机号" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"  onkeyup="value=value.replace(/[^\d]/g,'')" />
										</span>
                                        <span  >
                                            <input name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" style="width:50;overflow-x:visible;overflow-y:visible;"   onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" />
                                            <input name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" style="width:50;overflow-x:visible;overflow-y:visible;" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间"/>
                                        </span>
										</div>
									</td>
									<c:if test="${QX.cha == 1 }">
										<td style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:40px;"> </span>
											<span>
												<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
										</span>

											<span class="input-icon" style="width:80px;text-align:right;">
											平台来源:
										</span>
											<select  name="app_code_name" id="app_code_name" data-placeholder="请选择" value="${pd.app_code_name }" style="width:154px;border-radius:5px !important" onchange="changgeStyle()" >
												<option value="10" <c:if test="${pd.app_code_name !='' && pd.app_code_name == '10'}">selected</c:if>>球多多</option>
												<option value="11" <c:if test="${pd.app_code_name == '11'}">selected</c:if>> 圣和彩店 </option>
											</select>
											<span class="input-icon" style="width:23px;"> </span>
											<span>
												<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
										</span>
											<span class="input-icon" style="width:23px;"> </span>
											<span>
												<a class="btn btn-light btn-xs blue" onclick="toExcel();"  title="导出到Excel"  style="border-radius:5px;color:blue !important; width:50px">EXCEL</a>
										</span>

											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
											<span>
												<a id="superWhiteUser" class="btn btn-light btn-xs blue" onclick="setIsSupperWhite();"  title="加超级白名单"  style="border-radius:5px;color:blue !important; width:100px">加超级白名单</a>
										</span>

										</td>

									</c:if>
									<c:if test="${pd.personal == 1 }">
										<td style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:20px;"> </span>
											<span>
											<a class="btn btn-light btn-xs" onclick="perExcel();" title="审核导出"  style="border-radius:5px;color:blue !important; width:60px">审核导出 </a>
										</span><span class="input-icon" style="width:20px;"> </span>
											<span >
											<a class="btn btn-light btn-xs" onclick="fromExcel();" title="审核导入"  style="border-radius:5px;color:blue !important; width:60px">审核导入 </a>
										</span>
										</td>
									</c:if>
								</tr>
							</table>

							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
								<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">用户id</th>
									<th class="center">用户名</th>
									<th class="center">昵称</th>
									<th class="center">手机号</th>
									<th class="center">充值金额</th>
									<th class="center">最近充值时间</th>
									<th class="center">可提现余额</th>
									<th class="center">大礼包总金额</th>
									<th class="center">平台来源</th>
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
													<td class='center'>${var.user_id}</td>
													<td class='center'>${var.user_name}</td>
													<td class='center'>${var.nickname}</td>
													<td class='center'>
														<a title="查看流水" onclick="goListAccount('user_id=' + '${var.user_id}' + '&store_id=' + '${var.store_id}' + '&app_code_name=' + '${var.app_code_name}');">
																${var.mobile}
														</a>
													</td>
													<td class='center'>${var.money_limit}</td>
													<td class='center'>${var.recharge_time_latest}</td>
													<td class='center'>${var.money}</td>
													<td class='center'>${var.recharge_card_real_value}</td>
													<td class='center'>
														<c:if test="${var.app_code_name == '10'}">
															球多多
														</c:if>
														<c:if test="${var.app_code_name == '11'}">
															圣和彩店
														</c:if>
													</td>
													<td class="center">
														<c:if test="${QX.edit != 1 && QX.del != 1 }">
															<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
														</c:if>
														<div class="hidden-sm hidden-xs btn-group">
																<a class="btn btn-xs btn-success" title="充值"
																   onclick="recharge('user_id=' + '${var.user_id}' + '&store_id=' + '${var.store_id}' + '&mobile=' + '${var.mobile}');">
																	<i class="ace-icon fa fa-pencil-square-o bigger-120" title="充值">充值</i>
																</a>
																<a class="btn btn-xs btn-success" title="扣款"
																   onclick="deduction('user_id=' + '${var.user_id}' + '&store_id=' + '${var.store_id}');" >
																	<i class="ace-icon fa fa-pencil-square-o bigger-120" title="扣款">扣款</i>
																</a>
														</div>
														<div class="hidden-md hidden-lg">
															<div class="inline pos-rel">
																<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																	<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																</button>

																<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																	<c:if test="${QX.edit == 1 }">
																		<li>
																			<a style="cursor:pointer;" onclick="edit('${var.user_id_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																			</a>
																		</li>
																	</c:if>
																	<c:if test="${QX.del == 1 }">
																		<li>
																			<a style="cursor:pointer;" onclick="del('${var.user_id_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
											<!--
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
									 -->
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
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    $(top.hangge());//关闭加载状态
    //检索
    function tosearch(status){
        if(status==0){
            $("#user_id").val("");
            $("#user_name").val("");
            $("#nickname").val("");
            $("#mobile").val("");
            $("#store_name").val("");
            $("#lastStart").val("");
            $("#lastEnd").val("");
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

        var app_code_name = $("#app_code_name").val();
        if(app_code_name == "11"){
            $("#superWhiteUser").hide();
        }else{
            $("#superWhiteUser").show();
        }
    });

    //新增
    function add(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '<%=basePath%>superwhitelist/goAdd.do';
        diag.Width = 450;
        diag.Height = 355;
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
                var url = "<%=basePath%>superwhitelist/delete.do?user_id_id="+Id+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    tosearch();
                });
            }
        });
    }


    function setIsSupperWhite(){
        var mobile = $("#mobile").val();

        if (mobile == null || mobile == "") {
            alert("请先输入手机号");
            return null;
        }

        mobile = mobile.replace(/(^\s*)|(\s*$)/g,"");

        var str = "确定要将手机号：" + mobile + " 设为超级白名单用户";

        bootbox.confirm(str, function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>superwhitelist/setIsSupperWhite.do?mobile="+ mobile +"&tm="+new Date().getTime();
                $.get(url,function(data){
                    tosearch(1);
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
        diag.URL = '<%=basePath%>superwhitelist/goEdit.do?user_id_id='+Id;
        diag.Width = 450;
        diag.Height = 355;
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

    function recharge(str){
        var app_code_name = $("#app_code_name").val();
        str = str +'&app_code_name=' + app_code_name;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="";
        diag.URL = '<%=basePath%>superwhitelist/goRecharge.do?'+str;
        diag.Width = 450;
        diag.Height = 425;
        diag.Modal = true;				//有无遮罩窗口
        diag.ShowMaxButton = false;	//最大化按钮
        diag.ShowMinButton = false;		//最小化按钮
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                tosearch();
            }
            diag.close();
        };
        diag.show();
    }

    function goListAccount(str){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="查看流水";
        diag.URL = '<%=basePath%>superwhitelist/listAccount.do?' + str;
        /**
         diag.Width = 450;
         diag.Height = 355;
         **/
        diag.Width = 1200;
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

    function deduction(str){
        var app_code_name = $("#app_code_name").val();
        str = str +'&app_code_name=' + app_code_name;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="扣款";
        diag.URL = '<%=basePath%>superwhitelist/goDeduction.do?' + str;
        diag.Width = 450;
        diag.Height = 425;
        diag.Modal = true;				//有无遮罩窗口
        diag.ShowMaxButton = false;	//最大化按钮
        diag.ShowMinButton = false;		//最小化按钮
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
                            url: '<%=basePath%>superwhitelist/deleteAll.do?tm='+new Date().getTime(),
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

    function changgeStyle(){
        debugger;
        var app_code_name = $("#app_code_name").val();
        if(app_code_name == "11"){
            $("#superWhiteUser").hide();
        }else{
            $("#superWhiteUser").show();
        }
        top.jzts();
        $("#Form").submit();

    }

    //导出excel
    function toExcel(){
        var user_id =  $("#user_id").val();
        var user_name =  $("#user_name").val();
        var nickname = $("#nickname").val();
        var mobile = $("#mobile").val();
        var store_name = $("#store_name").val();
        var appCodeName = $("#app_code_name").val();
        var lastStart = $("#lastStart").val();
        var lastEnd = $("#lastEnd").val();

        var url = '<%=basePath%>superwhitelist/excel.do?tm=' + new Date().getTime()
            + "&user_id=" + user_id
            + "&user_name=" + user_name
            + "&nickname=" + nickname
            + "&mobile=" + mobile
            + "&store_name=" + store_name
			+ "&app_code_name=" + appCodeName
            + "&lastStart=" + lastStart
            + "&lastEnd=" + lastEnd
        ;

        window.location.href= url;

    }
</script>


</body>
</html>