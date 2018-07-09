<%@page import="com.fh.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
						<form action="present/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td><span class="input-icon" style="width: 270px;"> </span>日期：</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:118px;" placeholder="注册开始日期" title="注册开始日期"/></td>
								<td>至</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:118px;" placeholder="注册结束日期" title="注册结束日期"/></td>
							<!--	<td>快速查看：</td>
							 <td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name=handyCheck id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value="0">最近一周</option>
									<option value="1">最近一月</option>
								  	</select>
								</td> -->	
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px">
								<span class="input-icon" style="width: 30px;"> </span>
									<a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="搜索" style="border-radius: 5px; color: blue !important; width: 50px">搜索</a></td>
								</c:if>
								<td style="vertical-align:top;padding-left:2px">
								<span class="input-icon" style="width: 30px;"> </span>
									<a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="清空" style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
								</td>
								
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">${now }注册用户</th>
									<th class="center">${now }购彩金额</th>
									<th class="center">${now }购彩用户</th>
									<th class="center">${now }充值金额</th>
									<th class="center">${now }充值用户</th>
									<th class="center">${now }安装数</th>
								</tr>
							</thead>
													
							<tbody>
										<tr>
											<td class='center'>${register }</td>
											<td class='center'>${amountBuy}</td>
											<td class='center'>${countBuy}</td>
											<td class="center">${amountRecharge}</td>
											<td class="center">${countRecharge}</td>
											<td class="center">*</td>
										</tr>
							</tbody>
						</table>
						</form>
					<div id="main" style="height:350px"></div>
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
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#handyCheck").val("");
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
			 
			
			
		 
	</script>
	<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	<script type="text/javascript">
		// 路径配置
		require.config({
			paths : {
				echarts : 'http://echarts.baidu.com/build/dist'
			}
		});

		// 使用
		require([ 'echarts', 'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
		], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('main'));

			var option = {
				title : {
					text : '购彩金额',
					subtext : '金额单位（元）'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '今日', '一周前', '一月前' ]
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					name : '时间',
					boundaryGap : false,
					data : [ '0:00', '1:00', '2:00', '3:00', '4:00', '5:00',
							'6:00', '7:00', '8:00', '9:00', '10:00', '11:00',
							'12:00', '13:00', '14:00', '15:00', '16:00',
							'17:00', '18:00', '19:00', '20:00', '21:00',
							'22:00', '23:00' ]

				} ],
				yAxis : [ {
					type : 'value',
					name : '',
					axisLabel : {
						formatter : '{value} ￥'
					}
				} ],
				series : [ {
					name : '今日',
					type : 'line',
					data : [ ${pdt.d0 },${pdt.d1 }	,${pdt.d2 }	,${pdt.d3 }	,${pdt.d4 }	,${pdt.d5 }	,
							${pdt.d6 }	,${pdt.d7 }	,${pdt.d8 }	,${pdt.d9 }	,${pdt.d10 },${pdt.d11 } ,
							${pdt.d12 } ,${pdt.d13 } ,${pdt.d14 } ,${pdt.d15 }	,${pdt.d16 } ,${pdt.d17 } ,
							${pdt.d18 } ,${pdt.d19 } ,${pdt.d20 } ,${pdt.d21 }	,${pdt.d22 } ,${pdt.d23 }
							],
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				}, {
					name : '一周前',
					type : 'line',
					data : [ ${pdt.w0 },${pdt.w1 }	,${pdt.w2 }	,${pdt.w3 }	,${pdt.w4 }	,${pdt.w5 }	,
						${pdt.w6 }	,${pdt.w7 }	,${pdt.w8 }	,${pdt.w9 }	,${pdt.w10 },${pdt.w11 } ,
						${pdt.w12 } ,${pdt.w13 } ,${pdt.w14 } ,${pdt.w15 }	,${pdt.w16 } ,${pdt.w17 } ,
						${pdt.w18 } ,${pdt.w19 } ,${pdt.w20 } ,${pdt.w21 }	,${pdt.w22 } ,${pdt.w23 }
						],
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				}, {
					name : '一月前',
					type : 'line',
					data : [ ${pdt.m0 },${pdt.m1 }	,${pdt.m2 }	,${pdt.m3 }	,${pdt.m4 }	,${pdt.m5 }	,
						${pdt.m6 }	,${pdt.m7 }	,${pdt.m8 }	,${pdt.m9 }	,${pdt.m10 },${pdt.m11 } ,
						${pdt.m12 } ,${pdt.m13 } ,${pdt.m14 } ,${pdt.m15 }	,${pdt.m16 } ,${pdt.m17 } ,
						${pdt.m18 } ,${pdt.m19 } ,${pdt.m20 } ,${pdt.m21 }	,${pdt.m22 } ,${pdt.m23 }
						],
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				} ]
			};

			// 为echarts对象加载数据 
			myChart.setOption(option);
		});
	</script>

</body>
</html>