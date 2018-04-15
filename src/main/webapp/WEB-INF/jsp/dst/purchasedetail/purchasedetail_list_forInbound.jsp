<%@page import="com.fh.util.DateUtil"%>
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
						<form action="purchasedetail/listForInbound.do" method="post" name="Form" id="Form">
<%-- 						到货单编码：<input type="text" name="inbound_pre_notice_code" id="inbound_pre_notice_code" readonly="readonly" value="${pd.inbound_pre_notice_code}">
 --%>					
 						预到货单编码：<input type="text" name="inbound_pre_notice_code" id="inbound_pre_notice_code" readonly="readonly" value="${pd.inbound_pre_notice_code}">
						<input type="hidden" name="purchase_code"  id="purchase_code" value="${pd.purchase_code}" />
						<input type="hidden" name="business_type"  id="business_type " value="${pd.business_type}" />
						仓库：<input type="text" name="store_name" id="store_name" onclick="selectStore('${pd.purchase_code}')" value="${pd.store_name}" readonly="readonly" maxlength="32" placeholder="这里输入仓库名称" title="仓库名称" />
						<input type="hidden" name="store_sn" id="store_sn" value="${pd.store_sn}" />
						预计到货时间：<td><input class="span10 date-picker" name="pre_arrival_time" id="pre_arrival_time" value="${pd.pre_arrival_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预计到货时间" title="预计到货时间"/></td>
								    
					
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
								<th class="center" style="display:none">ID</th>
									<th class="center" style="display:none">采购单编码</th>
									<th class="center" style="display:none">sku_id</th>
									
									<th class="center">商品编码</th>
									<th class="center">商品名</th>
									<th class="center">数量</th>
									<th class="center">单位</th>
									<th class="center">规格</th>
									<th class="center">可发货数量</th>
									<th class="center">已发货数量</th>
									<th class="center">入库数量</th>
									<th class="center">退货数量</th>
									<!-- <th class="center">单价</th>
									<th class="center">含税单价</th>
									<th class="center">税率</th>
									<th class="center">状态</th> -->
									<th class="center">预计到货时间</th>
									<!-- <th class="center">创建人</th>
									<th class="center">创建时间</th>
									<th class="center">更新人</th>
									<th class="center">更新时间</th> -->
									<th class="center">本次发货</th>
									
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.detail_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center' style="display:none">${var.detail_id} <input type="hidden" name="purchase_detail_id" value="${var.detail_id}"></td>
											<td class='center' style="display:none">${var.purchase_code}</td>
											<td class='center' style="display:none">${var.sku_id} <input type="hidden" name="sku_id" value="${var.sku_id}"></td>
											<td class='center'>${var.sku_encode} <input type="hidden" name="sku_encode" value="${var.sku_encode}"></td>
											<td class='center'>${var.sku_name} <input type="hidden" name="sku_name" value="${var.sku_name}"> <input type="hidden" name="spec" value="${var.spec}"></td>
											<td class='center'>${var.quantity}</td>
											<td class='center'>${var.unit}</td>
											<td class='center'>${var.spec}</td>
											<td class='center'>${var.total_send_quantity} <input type="number" style="display:none" name="total_send_quantity" value="${var.total_send_quantity}"></td>
											<td class='center'>${var.total_sended_quantity}</td>
											<td class='center'>${var.good_quantity}</td>
											<td class='center'>${var.bad_quantity}</td>
											<%--<td class='center'>${var.price}</td>
											<td class='center'>${var.tax_price}</td>
											<td class='center'>${var.tax_rate}</td>
											<td class='center'>${var.status}</td> --%>
											<td class='center'>${DateUtil.toDateStr(var.pre_arrival_time)}</td>
											<%-- <td class='center'>${var.createby}</td>
											<td class='center'>${var.create_time}</td>
											<td class='center'>${var.updateby}</td>
											<td class='center'>${var.update_time}</td> --%>
											<td class='center'><input type="number" name="pre_arrival_quantity" value='0' style="width:60px"></td>
											
										
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
									<a class="btn btn-mini btn-success" id="submitBt" onclick="addInbound();">创建到货单</a>
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
		function tosearch(){
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
		});
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>purchasedetail/goAdd.do?purchase_code=${pd.purchase_code}';
			 diag.Width = 450;
			 diag.Height = 555;
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
					var url = "<%=basePath%>purchasedetail/delete.do?detail_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>purchasedetail/goEdit.do?detail_id='+Id;
			 diag.Width = 450;
			 diag.Height = 755;
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
								url: '<%=basePath%>purchasedetail/deleteAll.do?tm='+new Date().getTime(),
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
		/**
		创建到货单
		*/
		function addInbound(){
			
			if($("#inbound_pre_notice_code").val()==""){
				$("#inbound_pre_notice_code").tips({
					side:3,
		            msg:'单号不能为空',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inbound_pre_notice_code").focus();
			return false;
			}
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请选择仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
			return false;
			}
			if($("#pre_arrival_time").val()==""){
				$("#pre_arrival_time").tips({
					side:3,
		            msg:'请选择仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_arrival_time").focus();
			return false;
			}
			
			
			/* 		</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.detail_id} <input type="hidden" name="purchase_detail_id" value="${var.detail_id}"></td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.sku_id} <input type="hidden" name="sku_id" value="${var.sku_id}"</td> */
			var pre_arrival_quantitys = document.getElementsByName('pre_arrival_quantity');
			var purchase_detail_ids = document.getElementsByName('purchase_detail_id');
			var sku_ids = document.getElementsByName('sku_id');
			var sku_names = document.getElementsByName('sku_name');
			var sku_encodes = document.getElementsByName('sku_encode');
			var specs = document.getElementsByName('spec');
			var total_send_quantitys = document.getElementsByName('total_send_quantity');
			var jsonStr = "";
			var detailStr = "";
			//数据校验
				for(var i=0;i < pre_arrival_quantitys.length;i++){
					if( !(/^\d+$/.test(pre_arrival_quantitys[i].value))){
						$(pre_arrival_quantitys[i]).tips({
							side:3,
				            msg:'请输入正确的数量值',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					
				}
			//发货数量详情
			for(var i=0;i < pre_arrival_quantitys.length;i++){
				var pre_arrival_quantity = pre_arrival_quantitys[i];
				if(pre_arrival_quantity.value>0){
					if(parseInt(pre_arrival_quantity.value)>parseInt(total_send_quantitys[i].value)){
						$(pre_arrival_quantitys[i]).tips({
							side:3,
				            msg:'发货数量不能大于可发货数量',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					var inboundDetail = "{pre_arrival_quantity:"+pre_arrival_quantity.value+",purchase_detail_id:'"+purchase_detail_ids[i].value+"',sku_id:'"+sku_ids[i].value+"',sku_name:'"+sku_names[i].value+"',sku_encode:'"+sku_encodes[i].value+"',spec:'"+specs[i].value+"'},";
					detailStr += inboundDetail;
				}
			}
			if(detailStr.length>0){
				detailStr=detailStr.substring(0,detailStr.length-1);//去掉最后一个逗号
				
				jsonStr+="{inbound_pre_notice_code:'${pd.inbound_pre_notice_code}',purchase_group_id:'${pd.purchase_group_id}',purchase_code:'"+$("#purchase_code").val()+"',store_name:'"+$("#store_name").val()+"',store_sn:'"+$("#store_sn").val()+"',pre_arrival_time:'"
						+$("#pre_arrival_time").val()+"',pre_arrival_time:'"+$("#pre_arrival_time").val()+"',business_type:${pd.business_type},details:["+detailStr+"]";
				jsonStr+="}";
			//	alert(jsonStr);
				//发送
				top.jzts();
				$("#submitBt").attr("disabled","disabled");
				$.ajax({
					type: "POST",
				//	url: '<%=basePath%>inboundnotice/saveDetail.do?tm='+new Date().getTime(),
					url: '<%=basePath%>inboundprenotice/saveDetail.do?tm='+new Date().getTime(),
			    	data: {inboundJson:jsonStr},
					dataType:'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data){
						top.hangge();
						if(data.responseText=="success"){
						//	alert( "创建成功");
						bootbox.alert( "创建成功",function(){
								top.Dialog.close();
								//tosearch();
							});
						//	window.close();
						}else{
							bootbox.alert("创建失败");
							$("#submitBt").removeAttr("disabled");
						}

					},
					error:function(data){
						top.hangge();
						if(data.responseText=="success"){
							bootbox.alert( "创建成功",function(){
								top.Dialog.close();
								//tosearch();
							});
						
							//Window.close();
						}else{
							bootbox.alert("创建失败");
							$("#submitBt").removeAttr("disabled");
						}
					}
				});
			}else{
				alert("请填写发货数据");
			}
			
			
		}
		
		
		
		//选择供应商仓库
		function selectStore(sn){
			var types = "0,4";
			 top.jzts();
			 selectStoreDiag = new top.Dialog();
			 selectStoreDiag.Drag=true;
			 selectStoreDiag.Title ="选择到货仓库";
			 selectStoreDiag.URL = '<%=basePath%>szystore/listForSelect.do?types='+types;
			 selectStoreDiag.Width = 1250;
			 selectStoreDiag.Height = 755;
			 selectStoreDiag.Modal = true;				//有无遮罩窗口
			 selectStoreDiag. ShowMaxButton = true;	//最大化按钮
			 selectStoreDiag.ShowMinButton = true;		//最小化按钮
			 selectStoreDiag.CancelEvent = function(){ //关闭事件
				 selectStoreDiag.close();
			 };
			 selectStoreDiag.show();
		}
		
		//关闭选择供应商仓库页面
		function closeSelectStoreDiag(sn,name){
			$("#store_sn").val(sn);
			$("#store_name").val(name);
			selectStoreDiag.close();
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>purchasedetail/excel.do';
		}
	</script>


</body>
</html>