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
						<form action="outboundnoticedetail/sendlist.do" method="post" name="Form" id="Form">
						   <input type="hidden"   id="outbound_notice_code"  name="outbound_notice_code" value="${outbound_notice_code}" />
					     <input type="hidden"  id="outbound_notice_id"  name="outbound_notice_id" value="${outbound_notice_id}"/>
					     <input type="hidden"  id="bill_type"  name="bill_type" value="${pd.bill_type}"/>
					     <input type="hidden"  id="store_id"  name="store_id" value="${pd.store_id}"/>
					     <div>
							<input type="text" name="barcode" id="barcode" onchange="addbarcode()">
							<a class="btn btn-mini btn-success" onclick="createOutBound();">出库</a>
						
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
							<label id="show_label" style="color:red; "></label>
						</div>
						
						
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
								<!-- 	<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">物料名称</th>
									<th class="center">单位</th>
									<th class="center">规格</th>
									<th class="center">是否日期批次</th>
									<th class="center">属性分类</th>
									
									<th class="center">预计发货数量</th>
									<th class="center">出库数量</th>
									<th class="center">操作</th>
									
									<!-- <th class="center">操作</th> -->
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<input v2='${vs.index}' v1='h' type='hidden' id='${var.sku_barcode }' skuid_='${var.sku_id }' spec_='${var.spec }' skuencode_='${var.sku_encode }' unitname_='${var.unit_name }' name='${var.sku_name }' value='${var.outbound_notice_detail_id }'>
										<%-- 	<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.outbound_notice_detail_id}" class="ace" /><span class="lbl"></span></label>
											</td> --%>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											
											<td class='center'>${var.sku_name}</td>
											
											<td class="center">${var.unit_name}</td>
									<td class="center">${var.spec}</td>
									
									<td class="center">
									<c:if test="${var.isopen_batch==1 }">
									     开启
									</c:if>
									
									<c:if test="${var.isopen_batch==0 }">
									     未开启
									</c:if>
									
									</td>
									<td class="center">
									
									<c:if test="${var.attr_cate==0 }">
									     原材料
									</c:if>
									<c:if test="${var.attr_cate==1 }">
									    包材
									</c:if>
									
									<c:if test="${var.attr_cate==2 }">
									     成品
									</c:if>
									
									</td>
									
									<td id='td_pre_send_${var.sku_barcode }' class='center'>${var.pre_send_quantity}</td>
									<td id='td_send_${var.sku_barcode }' class='center'>
									
									 <c:choose>
									 <c:when test="${pd.bill_type==2 }">
									     <input type="text" readonly="readonly" name="send_quantity_${var.sku_barcode }" id="send_quantity_${var.sku_barcode }" value="${var.send_quantity}" maxlength="32" placeholder="这里输入出库数量" title="出库数量" style="width:60%;"/>
									 </c:when>
									 <c:otherwise>
									 	<c:if test="${var.isopen_batch==1 }">
											${var.send_quantity}
										</c:if>
										<c:if test="${var.isopen_batch==0 }">
											<input type="text" readonly="readonly" name="send_quantity_${var.sku_barcode }" id="send_quantity_${var.sku_barcode }" value="${var.send_quantity}" maxlength="32" placeholder="这里输入出库数量" title="出库数量" style="width:60%;"/>
										</c:if>
									 </c:otherwise>
									 </c:choose>
									</td>
									<td>
										<a class="btn btn-mini btn-success" onclick="select(${var.sku_barcode });">选择</a>
									</td>		
											
										<%-- 	<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in">
												<i class="ace-icon fa fa-lock" title="无权限"></i>
												</span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.outbound_notice_detail_id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.outbound_notice_detail_id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
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
																<a style="cursor:pointer;" onclick="edit('${var.outbound_notice_detail_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.outbound_notice_detail_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div>
											</td> --%>
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
							<table id="table1" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
								<thead>
									<tr>
										<th class="center" style="width:50px;">序号</th>
										<th class="center">物料名称</th>
										<th class="center">物料批次号</th>
										<th class="center">数量</th>
										<th class="center">操作</th>
									</tr>
								</thead>
								<tbody id="tbody1"></tbody>
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
			
			$('<audio id="chatAudio"><source src="static/audio/notify.wav" type="audio/wav">'+ 
				    '<source src="static/audio/notify.mp3" type="audio/mpeg"><source src="static/audio/notify.ogg" type="audio/ogg">'+
				    '</audio>').appendTo('body');
			
			$('#barcode').focus();
			$('#barcode').keydown(function(e){
				if(e.which == 13){
					//alert("dd");
					$("#barcode").blur();
					e.preventDefault();
				}
			});
			/* $("#barcode").blur(function(){
				$('#barcode').focus();
			}); */
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
			 diag.URL = '<%=basePath%>outboundnoticedetail/goAdd.do';
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
					var url = "<%=basePath%>outboundnoticedetail/delete.do?outbound_notice_detail_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>outboundnoticedetail/goEdit.do?outbound_notice_detail_id='+Id;
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
		
		
		
		//修改
		function updateSendQuantity(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="修改出库数量  ";
			 diag.URL = '<%=basePath%>outboundnoticedetail/goEdit.do?outbound_notice_detail_id='+Id;
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
								url: '<%=basePath%>outboundnoticedetail/deleteAll.do?tm='+new Date().getTime(),
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
		
		function editbarcodetable(barcode_, addNum_, saveflag_){
			if(barcode_ != null && barcode_.length >= 20){
				var b1_ = barcode_.substring(0, 20).toString();
				var skuBarCode_ = barcode_.substring(0, 13);
				var $obj = $('#'+skuBarCode_);
				var name_ = "";
				var detailId_ = "";
				var skuid_ = "";
				var spec_ = "";
				var skuencode_ = "";
				var unitname_ = "";
				var flag = 0;
				if($obj.length > 0){
					detailId_ = $('#'+skuBarCode_).val();
					name_ = $('#'+skuBarCode_).attr('name');
					skuid_ = $('#'+skuBarCode_).attr('skuid_');
					spec_ = $('#'+skuBarCode_).attr('spec_');
					skuencode_ = $('#'+skuBarCode_).attr('skuencode_');
					unitname_ = $('#'+skuBarCode_).attr('unitname_');
				}else{
					$('#chatAudio')[0].play(); 
					flag = 1;
				}
				var $num_ = $('#num_'+b1_);
				addNum_ = parseInt(addNum_);
				var num_ = addNum_;
				 var bill_type = $('#bill_type').val();
				 if(flag==0 && parseInt(bill_type) == 1){
					 num_ = "<input type='text' id='num_input_txt_"+b1_+"' att='"+num_+"' value='"+num_+"' onfocus='m1(\""+b1_.toString()+"\")' onblur='m2(\""+b1_.toString()+"\",\""+skuBarCode_+"\")' >";
				 }
				if($num_.length > 0){
					if(saveflag_ == 0){
						var c1val_ = $('#num_'+b1_).attr('c1_');
						var c1valArr_ = c1val_.split(',');
						if(c1valArr_.length > 0){
							var isHas_ = contains(c1valArr_, barcode_);
							if(isHas_){
								return 2;
							}
						}
						$('#num_'+b1_).attr('c1_',(c1val_+','+barcode_));
						 if(parseInt(bill_type) == 1){
							 var oldnum_ = $('#num_input_txt_'+b1_).val();
							 $('#num_input_txt_'+b1_).val(parseInt(oldnum_)+addNum_);
						 }else{
							var oldnum_ = $('#num_'+b1_).text();
							$('#num_'+b1_).text(parseInt(oldnum_)+addNum_);
							//var c1val_ = $('#num_'+b1_).attr('c1_');
							//$('#num_'+b1_).attr('c1_',(c1val_+','+barcode_));
						 }
					}else{
						if(parseInt(bill_type) == 1){
							 var oldnum_ = $('#num_input_txt_'+b1_).val();
							 $('#num_input_txt_'+b1_).val(addNum_);
							 addNum_ = addNum_ - parseInt(oldnum_);
						 }else{
							var oldnum_ = $('#num_'+b1_).text();
							$('#num_'+b1_).text(addNum_);
							addNum_ = addNum_ - parseInt(oldnum_);
						 }
					}
				}else{
					var len_ = $('#tbody1 tr').length;
					var $tr = $("<tr id='tr"+len_+"'>"+
						        "<td id='index_"+len_+"'>"+(len_ + 1)+"</td>"+
						        "<td>"+name_+"</td>"+
						        "<td id='barcode_"+len_+"'>"+b1_+"</td>"+
						        "<td id='num_"+b1_+"' detailId_='"+detailId_+"' skuBarCode_='"+skuBarCode_+"' skuname_='"+name_+"' c1_='"+barcode_+"' skuid_='"+skuid_+"' spec_='"+spec_+"' skuencode_='"+skuencode_+"' unitname_='"+unitname_+"'>"
						        +num_+
						        "</td>"+
						        "<td><a id='tda_"+len_+"' class='btn btn-mini btn-danger' onclick='delTr("+len_+")'>删除</a></td>"
						        +"</tr>");
					if(flag == 1){
						console.info("sss")
						$("#show_label").text('物料码错误！');
						$tr.attr('class','tr-danger')
					}
				   $('#tbody1').append($tr);
				}
				if(flag == 0){
					var $quaObj_ = $('#send_quantity_'+skuBarCode_);
					if(!addNum_){
						addNum_=0;
					}
					if($quaObj_.length > 0){
						var sendVal_ = $('#send_quantity_'+skuBarCode_).val();
						$('#send_quantity_'+skuBarCode_).val(parseInt(sendVal_)+addNum_);
					}else{
						var sendVal_ = $('#td_send_'+skuBarCode_).text();
						$('#td_send_'+skuBarCode_).text(parseInt(sendVal_)+addNum_);
					}
				}
			}else{
				return 3;
			}
		}
		function addbarcode( ){
			console.info("ddd11")
			$("#show_label").text('');
			var barcode_ = $("#barcode").val();
			var addNum_ = 1;
			if(barcode_ != null && barcode_.length == 13){
				var $obj = $('#'+barcode_);
				if($obj.length > 0){
					select(barcode_);
				}else{
					$("#show_label").text('物料码错误！');
				}
			}else{
				var rst_ = editbarcodetable(barcode_, addNum_, 0);
				if(rst_ == 3){
					$("#barcode").tips({
						side:3,
			            msg:'错误二维码，请重新输入！',
			            bg:'#AE81FF',
			            time:2
			        });
				}else if( rst_ ==2){
					$("#barcode").tips({
						side:3,
			            msg:'不能重复扫码！',
			            bg:'#AE81FF',
			            time:2
			        });
					return false; 
				}
			}
			$("#barcode").val("");
			$("#barcode").focus();
		}
		function contains(arr, obj){
			var i=arr.length;
			while(i--){
				if(arr[i] == obj)
					return true;
			}
			return false;
		}
		function m1(b1_){
			var val_ = $('#num_input_txt_'+b1_.toString()).val();
			$('#num_input_txt_'+b1_).attr('att',val_);
		}
		function m2(b1_, skuBarCode_){
			var oldNum_ = $('#num_input_txt_'+b1_).attr('att');
			var newNum_ = $('#num_input_txt_'+b1_).val();
			var addNum_ = parseInt(newNum_)-parseInt(oldNum_);
			var $quaObj_ = $('#send_quantity_'+skuBarCode_);
			if($quaObj_.length > 0){
				var sendVal_ = $('#send_quantity_'+skuBarCode_).val();
				$('#send_quantity_'+skuBarCode_).val(parseInt(sendVal_)+addNum_);
			}else{
				var sendVal_ = $('#td_send_'+skuBarCode_).text();
				$('#td_send_'+skuBarCode_).text(parseInt(sendVal_)+addNum_);
			}
		}
		function delTr(tr_index){
			
			var id_ = $('#tda_' + tr_index).parents("tr").attr('id');
			var a = id_.substring(2,id_.length);
			var b1val_ = $('#barcode_'+tr_index).text();
			var num_ = 0;
			var skuBarCode_ = $('#num_'+b1val_).attr('skuBarCode_');
			 var bill_type = $('#bill_type').val();
			var $obj = $('#'+skuBarCode_);
			if($obj.length > 0 && parseInt(bill_type) == 1){
				num_ = $('#num_input_txt_'+b1val_).val();
			}else{
				num_ = $('#num_'+b1val_).text();
			}
			var sendVal_ = $('#td_send_'+skuBarCode_).text();
			var $quaObj_ = $('#send_quantity_'+skuBarCode_);
			if($quaObj_.length > 0){
				var sendVal_ = $('#send_quantity_'+skuBarCode_).val();
				$('#send_quantity_'+skuBarCode_).val(parseInt(sendVal_)-parseInt(num_));
			}else{
				var sendVal_ = $('#td_send_'+skuBarCode_).text();
				$('#td_send_'+skuBarCode_).text(parseInt(sendVal_)-parseInt(num_));
			}
			
			$('#tr'+tr_index).remove();
			var len_ = $('#tbody1 tr').length;
			for(i=tr_index+1; i<=len_; i++){
				$('#index_'+i).text(i);
				$('#tr'+i).attr('id', 'tr'+(i-1));
				$('#index_'+i).attr('id', 'index_'+(i-1));
				$('#barcode_'+i).attr('id', 'barcode_'+(i-1));
				$('#tda_'+i).attr('onclick','delTr('+(i-1)+')');
				$('#tda_'+i).attr('id','tda_'+(i-1))
			}
			
		}
		
		function checkBarcodeNum(){
			var len_ = $('#tbody1 tr').length;
			var args = {};
			 var bill_type = $('#bill_type').val();
			 
			for(i=0; i<len_; i++){
				var code_ = $('#barcode_'+i).text();
				var codenum_ = 0;
				if(parseInt(bill_type) == 1){
					codenum_ =$('#num_input_txt_'+code_).val();
				}else{
					codenum_ = $('#num_'+code_).text();
				}
				var skuBarCode_ = $('#num_'+code_).attr('skuBarCode_');
				var sum_ = args[skuBarCode_];
				if(sum_ == null)sum_ = 0;
				sum_ += parseInt(codenum_);
				args[skuBarCode_] = sum_;
			}
			for(var skuBarCode_ in args){
				var $quaObj_ = $('#send_quantity_'+skuBarCode_);
				var sendVal_ = $('#td_send_'+skuBarCode_).text();
				if($quaObj_.length > 0){
					sendVal_ = $('#send_quantity_'+skuBarCode_).val();
				}
				var sum_ = args[skuBarCode_];
				if(parseInt(sum_) > parseInt(sendVal_)){
					return -1;
				}
			}
			return 1;
		}
		function getBarCode(){
			debugger
			var len_ = $('#simple-table > tbody > tr').length;
			var args = {};
			// var bill_type = $('#bill_type').val();
			 for(i=0; i<len_; i++){
				 var detailId_ = $("[v2='"+i+"']").val();
				 var skuBarCode_ = $("[v2='"+i+"']").attr('id');
				 var $quaObj_ = $('#send_quantity_'+skuBarCode_);
					var sendVal_ = $('#td_send_'+skuBarCode_).text();
					if($quaObj_.length > 0){
						sendVal_ = $('#send_quantity_'+skuBarCode_).val();
					}
					args[detailId_] = parseInt(sendVal_);
			 }
			 var jsonStr_ = JSON.stringify(args);
				return jsonStr_;
		}
		var diag_ = null;
		function select(sku_barcode){
			var len_ = $('#tbody1 tr').length;
			var args = {};
			var jsonStr_ = '';
			for(i=0; i<len_; i++){
				var barcode_ = $('#barcode_'+i).text();
				var index_ = barcode_.indexOf(sku_barcode);
				if(index_ == 0){
					var num_ = $('#num_'+barcode_).text();
					args[barcode_] = num_;
					jsonStr_= jsonStr_+ barcode_+":"+num_+"_";
				}
			}
			var store_id = $('#store_id').val();
			var skuid_ = $('#'+sku_barcode).attr('skuid_');
			
			//store_id=1;
			
			top.jzts();
			diag_ = new top.Dialog();
			diag_.Drag=true;
			diag_.Title ="选择";
			diag_.URL = '<%=basePath%>stockbatch/selectList.do?sku_barcode='+sku_barcode+'&store_id='+store_id+'&sku_id='+skuid_+'&barcodes='+jsonStr_+'';
			diag_.Width = 900;
			diag_.Height = 855;
			diag_.Modal = true;				//有无遮罩窗口
			diag_. ShowMaxButton = true;	//最大化按钮
			diag_.ShowMinButton = true;		//最小化按钮 
			diag_.CancelEvent = function(){ //关闭事件
				diag_.close();
			 };
			 diag_.show();
		}
		
		function getSelectData(data){
			for(var str in data){
				var barcode_ = str;
				var addNum_ = data[str];
				var rst_ = editbarcodetable(barcode_, addNum_, 1);
			}
			diag_.close();
		}
		
		function getBarCode1(){
			var len_ = $('#tbody1 tr').length;
			var args = {};
			 var bill_type = $('#bill_type').val();
			 
			for(i=0; i<len_; i++){
				var code_ = $('#barcode_'+i).text();
				
				var detailId_ = $('#num_'+code_).attr('detailId_');
				var skuid_ = $('#num_'+code_).attr('skuid_');
				var spec_ = $('#num_'+code_).attr('spec_');
				var skuencode_ = $('#num_'+code_).attr('skuencode_');
				var unitname_ = $('#num_'+code_).attr('unitname_');
				var skuname_ = $('#num_'+code_).attr('skuname_');
				var args_ = args[detailId_];
				if(args_ == null){
					args_=[];
					args[detailId_] = args_;
				}
				var barcode_ = $('#num_'+code_).attr('c1_');
				var skuBarCode_ = $('#num_'+code_).attr('skuBarCode_');
				var arg1 = {};
				arg1['barcode_'] = barcode_;
				arg1['sku_id'] = skuid_;
				arg1['sku_name'] = skuname_;
				arg1['sku_encode'] = skuencode_;
				arg1['spec'] = spec_;
				arg1['unit_name'] = unitname_;
				if(parseInt(bill_type) == 1){
					arg1['quantity'] = $('#num_input_txt_'+code_).val();
				}else{
					arg1['quantity'] = $('#num_'+code_).text();
				}
				arg1['batch_code'] = code_;
				var $quaObj_ = $('#send_quantity_'+skuBarCode_);
				var sendVal_ = $('#td_send_'+skuBarCode_).text();
				if($quaObj_.length > 0){
					sendVal_ = $('#send_quantity_'+skuBarCode_).val();
				}
				arg1['send_quantity'] = sendVal_;
				args_.push(arg1);
			}
			var jsonStr_ = JSON.stringify(args);
			return jsonStr_;
		}
		//创建出库单
		function createOutBound()
		{
			 var dangerTrLen_ = $(".tr-danger").length;
			 if(dangerTrLen_ > 0){
				alert('有非法出库商品！');
				return
			} 
			 var bill_type = $('#bill_type').val();
			var flag = 0;
			$("[v1='h']").each(function(){
			    var skuBarCode_ = $(this).attr('id');
			    var $quaObj_ = $('#send_quantity_'+skuBarCode_);
				var sendVal_ = $('#td_send_'+skuBarCode_).text();
				if($quaObj_.length > 0){
					sendVal_ = $('#send_quantity_'+skuBarCode_).val();
				}
				var preSendVal_ = $('#td_pre_send_'+skuBarCode_).text();
				//console.info("-"+sendVal_ + "-" + preSendVal_)
			    if(parseInt(sendVal_) <=0 || parseInt(sendVal_) > parseInt(preSendVal_)){
			    		flag = 1;
			    }else if(parseInt(bill_type) == 5 && parseInt(sendVal_) != parseInt(preSendVal_)){
			    		flag = 1;
		    	}
			});
			if(flag == 1){
				alert('出库数量不合要求！');
				return
			} 
			var isChecked = checkBarcodeNum();
			if(isChecked < 0){
				alert('出库数量小于明细数量！');
				return
			}
			var noticeId=$("#outbound_notice_id").val();
			var noticeCode=$("#outbound_notice_code").val();
			var jsonStr_ = getBarCode1();
			var sendNumJson_ = getBarCode();
			//console.info(sendNumJson_)
			//console.info(jsonStr_);
			 bootbox.confirm("确定要出库吗?", function(result) {
				if(result) {
					top.jzts();
					
					$.ajax({
						  type: 'POST',
						  url: '<%=basePath%>pgtoutbound/create.do',
						  data: {'noticeId':noticeId,'noticeCode':noticeCode,'stockbatch':jsonStr_,'sendQuantity':sendNumJson_,'tm':new Date().getTime()},
						  success: function(data){
							  if(data.msg=="success")
								{
								top.Dialog.close();
								}
							else
								{
								  alert("出库失败"+data.msg);
								  
								  top.Dialog.close();
								}
						  }
						}); 
				}
			}); 
			
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>outboundnoticedetail/excel.do';
		}
	</script>


</body>
</html>