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
					
					<form action="order/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="pgt_order_id" id="pgt_order_id" value="${pd.pgt_order_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注1:</td>
								<td><input type="number" name="pgt_order_id" id="pgt_order_id" value="${pd.pgt_order_id}" maxlength="32" placeholder="这里输入备注1" title="备注1" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注2:</td>
								<td><input type="number" name="order_id" id="order_id" value="${pd.order_id}" maxlength="32" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注3:</td>
								<td><input type="text" name="order_sn" id="order_sn" value="${pd.order_sn}" maxlength="20" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注4:</td>
								<td><input type="text" name="parent_sn" id="parent_sn" value="${pd.parent_sn}" maxlength="20" placeholder="这里输入备注4" title="备注4" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注5:</td>
								<td><input type="number" name="user_id" id="user_id" value="${pd.user_id}" maxlength="32" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注6:</td>
								<td><input type="number" name="order_status" id="order_status" value="${pd.order_status}" maxlength="32" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注7:</td>
								<td><input type="number" name="shop_id" id="shop_id" value="${pd.shop_id}" maxlength="32" placeholder="这里输入备注7" title="备注7" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注8:</td>
								<td><input type="number" name="site_id" id="site_id" value="${pd.site_id}" maxlength="32" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注9:</td>
								<td><input type="number" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注10:</td>
								<td><input type="number" name="pickup_id" id="pickup_id" value="${pd.pickup_id}" maxlength="32" placeholder="这里输入备注10" title="备注10" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注11:</td>
								<td><input type="number" name="shipping_status" id="shipping_status" value="${pd.shipping_status}" maxlength="32" placeholder="这里输入备注11" title="备注11" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注12:</td>
								<td><input type="number" name="pay_status" id="pay_status" value="${pd.pay_status}" maxlength="32" placeholder="这里输入备注12" title="备注12" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注13:</td>
								<td><input type="text" name="consignee" id="consignee" value="${pd.consignee}" maxlength="60" placeholder="这里输入备注13" title="备注13" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注14:</td>
								<td><input type="text" name="region_code" id="region_code" value="${pd.region_code}" maxlength="255" placeholder="这里输入备注14" title="备注14" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注15:</td>
								<td><input type="text" name="region_name" id="region_name" value="${pd.region_name}" maxlength="255" placeholder="这里输入备注15" title="备注15" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注16:</td>
								<td><input type="text" name="address" id="address" value="${pd.address}" maxlength="255" placeholder="这里输入备注16" title="备注16" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注17:</td>
								<td><input type="text" name="address_lng" id="address_lng" value="${pd.address_lng}" maxlength="60" placeholder="这里输入备注17" title="备注17" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注18:</td>
								<td><input type="text" name="address_lat" id="address_lat" value="${pd.address_lat}" maxlength="60" placeholder="这里输入备注18" title="备注18" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注19:</td>
								<td><input type="number" name="receiving_mode" id="receiving_mode" value="${pd.receiving_mode}" maxlength="32" placeholder="这里输入备注19" title="备注19" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注20:</td>
								<td><input type="text" name="tel" id="tel" value="${pd.tel}" maxlength="15" placeholder="这里输入备注20" title="备注20" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注21:</td>
								<td><input type="text" name="email" id="email" value="${pd.email}" maxlength="30" placeholder="这里输入备注21" title="备注21" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注22:</td>
								<td><input type="text" name="postscript" id="postscript" value="${pd.postscript}" maxlength="255" placeholder="这里输入备注22" title="备注22" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注23:</td>
								<td><input type="text" name="best_time" id="best_time" value="${pd.best_time}" maxlength="120" placeholder="这里输入备注23" title="备注23" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注24:</td>
								<td><input type="number" name="pay_id" id="pay_id" value="${pd.pay_id}" maxlength="32" placeholder="这里输入备注24" title="备注24" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注25:</td>
								<td><input type="text" name="pay_code" id="pay_code" value="${pd.pay_code}" maxlength="30" placeholder="这里输入备注25" title="备注25" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注26:</td>
								<td><input type="text" name="pay_name" id="pay_name" value="${pd.pay_name}" maxlength="120" placeholder="这里输入备注26" title="备注26" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注27:</td>
								<td><input type="text" name="pay_sn" id="pay_sn" value="${pd.pay_sn}" maxlength="255" placeholder="这里输入备注27" title="备注27" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注28:</td>
								<td><input type="number" name="is_cod" id="is_cod" value="${pd.is_cod}" maxlength="32" placeholder="这里输入备注28" title="备注28" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注29:</td>
								<td><input type="text" name="order_amount" id="order_amount" value="${pd.order_amount}" maxlength="12" placeholder="这里输入备注29" title="备注29" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注30:</td>
								<td><input type="number" name="order_points" id="order_points" value="${pd.order_points}" maxlength="32" placeholder="这里输入备注30" title="备注30" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注31:</td>
								<td><input type="text" name="money_paid" id="money_paid" value="${pd.money_paid}" maxlength="12" placeholder="这里输入备注31" title="备注31" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注32:</td>
								<td><input type="text" name="goods_amount" id="goods_amount" value="${pd.goods_amount}" maxlength="12" placeholder="这里输入备注32" title="备注32" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注33:</td>
								<td><input type="text" name="inv_fee" id="inv_fee" value="${pd.inv_fee}" maxlength="12" placeholder="这里输入备注33" title="备注33" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注34:</td>
								<td><input type="text" name="shipping_fee" id="shipping_fee" value="${pd.shipping_fee}" maxlength="12" placeholder="这里输入备注34" title="备注34" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注35:</td>
								<td><input type="text" name="cash_more" id="cash_more" value="${pd.cash_more}" maxlength="12" placeholder="这里输入备注35" title="备注35" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注36:</td>
								<td><input type="text" name="discount_fee" id="discount_fee" value="${pd.discount_fee}" maxlength="12" placeholder="这里输入备注36" title="备注36" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注37:</td>
								<td><input type="text" name="change_amount" id="change_amount" value="${pd.change_amount}" maxlength="12" placeholder="这里输入备注37" title="备注37" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注38:</td>
								<td><input type="text" name="shipping_change" id="shipping_change" value="${pd.shipping_change}" maxlength="12" placeholder="这里输入备注38" title="备注38" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注39:</td>
								<td><input type="text" name="surplus" id="surplus" value="${pd.surplus}" maxlength="12" placeholder="这里输入备注39" title="备注39" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注40:</td>
								<td><input type="text" name="user_surplus" id="user_surplus" value="${pd.user_surplus}" maxlength="12" placeholder="这里输入备注40" title="备注40" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注41:</td>
								<td><input type="text" name="user_surplus_limit" id="user_surplus_limit" value="${pd.user_surplus_limit}" maxlength="12" placeholder="这里输入备注41" title="备注41" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注42:</td>
								<td><input type="number" name="bonus_id" id="bonus_id" value="${pd.bonus_id}" maxlength="32" placeholder="这里输入备注42" title="备注42" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注43:</td>
								<td><input type="number" name="shop_bonus_id" id="shop_bonus_id" value="${pd.shop_bonus_id}" maxlength="32" placeholder="这里输入备注43" title="备注43" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注44:</td>
								<td><input type="text" name="bonus" id="bonus" value="${pd.bonus}" maxlength="12" placeholder="这里输入备注44" title="备注44" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注45:</td>
								<td><input type="text" name="shop_bonus" id="shop_bonus" value="${pd.shop_bonus}" maxlength="12" placeholder="这里输入备注45" title="备注45" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注46:</td>
								<td><input type="number" name="store_card_id" id="store_card_id" value="${pd.store_card_id}" maxlength="32" placeholder="这里输入备注46" title="备注46" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注47:</td>
								<td><input type="text" name="store_card_price" id="store_card_price" value="${pd.store_card_price}" maxlength="12" placeholder="这里输入备注47" title="备注47" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注48:</td>
								<td><input type="number" name="integral" id="integral" value="${pd.integral}" maxlength="32" placeholder="这里输入备注48" title="备注48" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注49:</td>
								<td><input type="text" name="integral_money" id="integral_money" value="${pd.integral_money}" maxlength="12" placeholder="这里输入备注49" title="备注49" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注50:</td>
								<td><input type="number" name="give_integral" id="give_integral" value="${pd.give_integral}" maxlength="32" placeholder="这里输入备注50" title="备注50" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注51:</td>
								<td><input type="text" name="order_from" id="order_from" value="${pd.order_from}" maxlength="255" placeholder="这里输入备注51" title="备注51" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注52:</td>
								<td><input type="number" name="add_time" id="add_time" value="${pd.add_time}" maxlength="32" placeholder="这里输入备注52" title="备注52" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注53:</td>
								<td><input type="number" name="pay_time" id="pay_time" value="${pd.pay_time}" maxlength="32" placeholder="这里输入备注53" title="备注53" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注54:</td>
								<td><input type="number" name="shipping_time" id="shipping_time" value="${pd.shipping_time}" maxlength="32" placeholder="这里输入备注54" title="备注54" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注55:</td>
								<td><input type="number" name="confirm_time" id="confirm_time" value="${pd.confirm_time}" maxlength="32" placeholder="这里输入备注55" title="备注55" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注56:</td>
								<td><input type="number" name="delay_days" id="delay_days" value="${pd.delay_days}" maxlength="32" placeholder="这里输入备注56" title="备注56" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注57:</td>
								<td><input type="number" name="order_type" id="order_type" value="${pd.order_type}" maxlength="32" placeholder="这里输入备注57" title="备注57" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注58:</td>
								<td><input type="number" name="service_mark" id="service_mark" value="${pd.service_mark}" maxlength="32" placeholder="这里输入备注58" title="备注58" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注59:</td>
								<td><input type="number" name="send_mark" id="send_mark" value="${pd.send_mark}" maxlength="32" placeholder="这里输入备注59" title="备注59" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注60:</td>
								<td><input type="number" name="shipping_mark" id="shipping_mark" value="${pd.shipping_mark}" maxlength="32" placeholder="这里输入备注60" title="备注60" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注61:</td>
								<td><input type="number" name="buyer_type" id="buyer_type" value="${pd.buyer_type}" maxlength="32" placeholder="这里输入备注61" title="备注61" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注62:</td>
								<td><input type="number" name="evaluate_status" id="evaluate_status" value="${pd.evaluate_status}" maxlength="32" placeholder="这里输入备注62" title="备注62" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注63:</td>
								<td><input type="number" name="evaluate_time" id="evaluate_time" value="${pd.evaluate_time}" maxlength="32" placeholder="这里输入备注63" title="备注63" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注64:</td>
								<td><input type="number" name="end_time" id="end_time" value="${pd.end_time}" maxlength="32" placeholder="这里输入备注64" title="备注64" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注65:</td>
								<td><input type="number" name="is_distrib" id="is_distrib" value="${pd.is_distrib}" maxlength="32" placeholder="这里输入备注65" title="备注65" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注66:</td>
								<td><input type="number" name="distrib_status" id="distrib_status" value="${pd.distrib_status}" maxlength="32" placeholder="这里输入备注66" title="备注66" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注67:</td>
								<td><input type="text" name="is_show" id="is_show" value="${pd.is_show}" maxlength="255" placeholder="这里输入备注67" title="备注67" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注68:</td>
								<td><input type="number" name="is_delete" id="is_delete" value="${pd.is_delete}" maxlength="32" placeholder="这里输入备注68" title="备注68" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注69:</td>
								<td><input type="text" name="order_data" id="order_data" value="${pd.order_data}" maxlength="21845" placeholder="这里输入备注69" title="备注69" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注70:</td>
								<td><input type="text" name="mall_remark" id="mall_remark" value="${pd.mall_remark}" maxlength="21845" placeholder="这里输入备注70" title="备注70" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注71:</td>
								<td><input type="text" name="shop_remark" id="shop_remark" value="${pd.shop_remark}" maxlength="21845" placeholder="这里输入备注71" title="备注71" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注72:</td>
								<td><input type="text" name="store_remark" id="store_remark" value="${pd.store_remark}" maxlength="21845" placeholder="这里输入备注72" title="备注72" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注73:</td>
								<td><input type="text" name="close_reason" id="close_reason" value="${pd.close_reason}" maxlength="255" placeholder="这里输入备注73" title="备注73" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注74:</td>
								<td><input type="number" name="cash_user_id" id="cash_user_id" value="${pd.cash_user_id}" maxlength="32" placeholder="这里输入备注74" title="备注74" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注75:</td>
								<td><input type="number" name="last_time" id="last_time" value="${pd.last_time}" maxlength="32" placeholder="这里输入备注75" title="备注75" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注76:</td>
								<td><input type="number" name="order_cancel" id="order_cancel" value="${pd.order_cancel}" maxlength="32" placeholder="这里输入备注76" title="备注76" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注77:</td>
								<td><input type="text" name="refuse_reason" id="refuse_reason" value="${pd.refuse_reason}" maxlength="255" placeholder="这里输入备注77" title="备注77" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注78:</td>
								<td><input type="number" name="sub_order_id" id="sub_order_id" value="${pd.sub_order_id}" maxlength="32" placeholder="这里输入备注78" title="备注78" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注79:</td>
								<td><input type="number" name="is_freebuy" id="is_freebuy" value="${pd.is_freebuy}" maxlength="32" placeholder="这里输入备注79" title="备注79" style="width:98%;"/></td>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#pgt_order_id").val()==""){
				$("#pgt_order_id").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pgt_order_id").focus();
			return false;
			}
			if($("#order_id").val()==""){
				$("#order_id").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_id").focus();
			return false;
			}
			if($("#order_sn").val()==""){
				$("#order_sn").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_sn").focus();
			return false;
			}
			if($("#parent_sn").val()==""){
				$("#parent_sn").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#parent_sn").focus();
			return false;
			}
			if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_id").focus();
			return false;
			}
			if($("#order_status").val()==""){
				$("#order_status").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_status").focus();
			return false;
			}
			if($("#shop_id").val()==""){
				$("#shop_id").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shop_id").focus();
			return false;
			}
			if($("#site_id").val()==""){
				$("#site_id").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#site_id").focus();
			return false;
			}
			if($("#store_id").val()==""){
				$("#store_id").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_id").focus();
			return false;
			}
			if($("#pickup_id").val()==""){
				$("#pickup_id").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pickup_id").focus();
			return false;
			}
			if($("#shipping_status").val()==""){
				$("#shipping_status").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shipping_status").focus();
			return false;
			}
			if($("#pay_status").val()==""){
				$("#pay_status").tips({
					side:3,
		            msg:'请输入备注12',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_status").focus();
			return false;
			}
			if($("#consignee").val()==""){
				$("#consignee").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#consignee").focus();
			return false;
			}
			if($("#region_code").val()==""){
				$("#region_code").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#region_code").focus();
			return false;
			}
			if($("#region_name").val()==""){
				$("#region_name").tips({
					side:3,
		            msg:'请输入备注15',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#region_name").focus();
			return false;
			}
			if($("#address").val()==""){
				$("#address").tips({
					side:3,
		            msg:'请输入备注16',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#address").focus();
			return false;
			}
			if($("#address_lng").val()==""){
				$("#address_lng").tips({
					side:3,
		            msg:'请输入备注17',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#address_lng").focus();
			return false;
			}
			if($("#address_lat").val()==""){
				$("#address_lat").tips({
					side:3,
		            msg:'请输入备注18',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#address_lat").focus();
			return false;
			}
			if($("#receiving_mode").val()==""){
				$("#receiving_mode").tips({
					side:3,
		            msg:'请输入备注19',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#receiving_mode").focus();
			return false;
			}
			if($("#tel").val()==""){
				$("#tel").tips({
					side:3,
		            msg:'请输入备注20',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tel").focus();
			return false;
			}
			if($("#email").val()==""){
				$("#email").tips({
					side:3,
		            msg:'请输入备注21',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#email").focus();
			return false;
			}
			if($("#postscript").val()==""){
				$("#postscript").tips({
					side:3,
		            msg:'请输入备注22',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#postscript").focus();
			return false;
			}
			if($("#best_time").val()==""){
				$("#best_time").tips({
					side:3,
		            msg:'请输入备注23',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#best_time").focus();
			return false;
			}
			if($("#pay_id").val()==""){
				$("#pay_id").tips({
					side:3,
		            msg:'请输入备注24',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_id").focus();
			return false;
			}
			if($("#pay_code").val()==""){
				$("#pay_code").tips({
					side:3,
		            msg:'请输入备注25',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_code").focus();
			return false;
			}
			if($("#pay_name").val()==""){
				$("#pay_name").tips({
					side:3,
		            msg:'请输入备注26',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_name").focus();
			return false;
			}
			if($("#pay_sn").val()==""){
				$("#pay_sn").tips({
					side:3,
		            msg:'请输入备注27',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_sn").focus();
			return false;
			}
			if($("#is_cod").val()==""){
				$("#is_cod").tips({
					side:3,
		            msg:'请输入备注28',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_cod").focus();
			return false;
			}
			if($("#order_amount").val()==""){
				$("#order_amount").tips({
					side:3,
		            msg:'请输入备注29',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_amount").focus();
			return false;
			}
			if($("#order_points").val()==""){
				$("#order_points").tips({
					side:3,
		            msg:'请输入备注30',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_points").focus();
			return false;
			}
			if($("#money_paid").val()==""){
				$("#money_paid").tips({
					side:3,
		            msg:'请输入备注31',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#money_paid").focus();
			return false;
			}
			if($("#goods_amount").val()==""){
				$("#goods_amount").tips({
					side:3,
		            msg:'请输入备注32',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_amount").focus();
			return false;
			}
			if($("#inv_fee").val()==""){
				$("#inv_fee").tips({
					side:3,
		            msg:'请输入备注33',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inv_fee").focus();
			return false;
			}
			if($("#shipping_fee").val()==""){
				$("#shipping_fee").tips({
					side:3,
		            msg:'请输入备注34',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shipping_fee").focus();
			return false;
			}
			if($("#cash_more").val()==""){
				$("#cash_more").tips({
					side:3,
		            msg:'请输入备注35',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cash_more").focus();
			return false;
			}
			if($("#discount_fee").val()==""){
				$("#discount_fee").tips({
					side:3,
		            msg:'请输入备注36',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#discount_fee").focus();
			return false;
			}
			if($("#change_amount").val()==""){
				$("#change_amount").tips({
					side:3,
		            msg:'请输入备注37',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#change_amount").focus();
			return false;
			}
			if($("#shipping_change").val()==""){
				$("#shipping_change").tips({
					side:3,
		            msg:'请输入备注38',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shipping_change").focus();
			return false;
			}
			if($("#surplus").val()==""){
				$("#surplus").tips({
					side:3,
		            msg:'请输入备注39',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#surplus").focus();
			return false;
			}
			if($("#user_surplus").val()==""){
				$("#user_surplus").tips({
					side:3,
		            msg:'请输入备注40',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_surplus").focus();
			return false;
			}
			if($("#user_surplus_limit").val()==""){
				$("#user_surplus_limit").tips({
					side:3,
		            msg:'请输入备注41',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_surplus_limit").focus();
			return false;
			}
			if($("#bonus_id").val()==""){
				$("#bonus_id").tips({
					side:3,
		            msg:'请输入备注42',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bonus_id").focus();
			return false;
			}
			if($("#shop_bonus_id").val()==""){
				$("#shop_bonus_id").tips({
					side:3,
		            msg:'请输入备注43',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shop_bonus_id").focus();
			return false;
			}
			if($("#bonus").val()==""){
				$("#bonus").tips({
					side:3,
		            msg:'请输入备注44',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bonus").focus();
			return false;
			}
			if($("#shop_bonus").val()==""){
				$("#shop_bonus").tips({
					side:3,
		            msg:'请输入备注45',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shop_bonus").focus();
			return false;
			}
			if($("#store_card_id").val()==""){
				$("#store_card_id").tips({
					side:3,
		            msg:'请输入备注46',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_card_id").focus();
			return false;
			}
			if($("#store_card_price").val()==""){
				$("#store_card_price").tips({
					side:3,
		            msg:'请输入备注47',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_card_price").focus();
			return false;
			}
			if($("#integral").val()==""){
				$("#integral").tips({
					side:3,
		            msg:'请输入备注48',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#integral").focus();
			return false;
			}
			if($("#integral_money").val()==""){
				$("#integral_money").tips({
					side:3,
		            msg:'请输入备注49',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#integral_money").focus();
			return false;
			}
			if($("#give_integral").val()==""){
				$("#give_integral").tips({
					side:3,
		            msg:'请输入备注50',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#give_integral").focus();
			return false;
			}
			if($("#order_from").val()==""){
				$("#order_from").tips({
					side:3,
		            msg:'请输入备注51',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_from").focus();
			return false;
			}
			if($("#add_time").val()==""){
				$("#add_time").tips({
					side:3,
		            msg:'请输入备注52',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#add_time").focus();
			return false;
			}
			if($("#pay_time").val()==""){
				$("#pay_time").tips({
					side:3,
		            msg:'请输入备注53',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_time").focus();
			return false;
			}
			if($("#shipping_time").val()==""){
				$("#shipping_time").tips({
					side:3,
		            msg:'请输入备注54',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shipping_time").focus();
			return false;
			}
			if($("#confirm_time").val()==""){
				$("#confirm_time").tips({
					side:3,
		            msg:'请输入备注55',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#confirm_time").focus();
			return false;
			}
			if($("#delay_days").val()==""){
				$("#delay_days").tips({
					side:3,
		            msg:'请输入备注56',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delay_days").focus();
			return false;
			}
			if($("#order_type").val()==""){
				$("#order_type").tips({
					side:3,
		            msg:'请输入备注57',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_type").focus();
			return false;
			}
			if($("#service_mark").val()==""){
				$("#service_mark").tips({
					side:3,
		            msg:'请输入备注58',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#service_mark").focus();
			return false;
			}
			if($("#send_mark").val()==""){
				$("#send_mark").tips({
					side:3,
		            msg:'请输入备注59',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#send_mark").focus();
			return false;
			}
			if($("#shipping_mark").val()==""){
				$("#shipping_mark").tips({
					side:3,
		            msg:'请输入备注60',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shipping_mark").focus();
			return false;
			}
			if($("#buyer_type").val()==""){
				$("#buyer_type").tips({
					side:3,
		            msg:'请输入备注61',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#buyer_type").focus();
			return false;
			}
			if($("#evaluate_status").val()==""){
				$("#evaluate_status").tips({
					side:3,
		            msg:'请输入备注62',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#evaluate_status").focus();
			return false;
			}
			if($("#evaluate_time").val()==""){
				$("#evaluate_time").tips({
					side:3,
		            msg:'请输入备注63',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#evaluate_time").focus();
			return false;
			}
			if($("#end_time").val()==""){
				$("#end_time").tips({
					side:3,
		            msg:'请输入备注64',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_time").focus();
			return false;
			}
			if($("#is_distrib").val()==""){
				$("#is_distrib").tips({
					side:3,
		            msg:'请输入备注65',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_distrib").focus();
			return false;
			}
			if($("#distrib_status").val()==""){
				$("#distrib_status").tips({
					side:3,
		            msg:'请输入备注66',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#distrib_status").focus();
			return false;
			}
			if($("#is_show").val()==""){
				$("#is_show").tips({
					side:3,
		            msg:'请输入备注67',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_show").focus();
			return false;
			}
			if($("#is_delete").val()==""){
				$("#is_delete").tips({
					side:3,
		            msg:'请输入备注68',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_delete").focus();
			return false;
			}
			if($("#order_data").val()==""){
				$("#order_data").tips({
					side:3,
		            msg:'请输入备注69',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_data").focus();
			return false;
			}
			if($("#mall_remark").val()==""){
				$("#mall_remark").tips({
					side:3,
		            msg:'请输入备注70',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mall_remark").focus();
			return false;
			}
			if($("#shop_remark").val()==""){
				$("#shop_remark").tips({
					side:3,
		            msg:'请输入备注71',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shop_remark").focus();
			return false;
			}
			if($("#store_remark").val()==""){
				$("#store_remark").tips({
					side:3,
		            msg:'请输入备注72',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_remark").focus();
			return false;
			}
			if($("#close_reason").val()==""){
				$("#close_reason").tips({
					side:3,
		            msg:'请输入备注73',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#close_reason").focus();
			return false;
			}
			if($("#cash_user_id").val()==""){
				$("#cash_user_id").tips({
					side:3,
		            msg:'请输入备注74',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cash_user_id").focus();
			return false;
			}
			if($("#last_time").val()==""){
				$("#last_time").tips({
					side:3,
		            msg:'请输入备注75',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#last_time").focus();
			return false;
			}
			if($("#order_cancel").val()==""){
				$("#order_cancel").tips({
					side:3,
		            msg:'请输入备注76',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#order_cancel").focus();
			return false;
			}
			if($("#refuse_reason").val()==""){
				$("#refuse_reason").tips({
					side:3,
		            msg:'请输入备注77',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#refuse_reason").focus();
			return false;
			}
			if($("#sub_order_id").val()==""){
				$("#sub_order_id").tips({
					side:3,
		            msg:'请输入备注78',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sub_order_id").focus();
			return false;
			}
			if($("#is_freebuy").val()==""){
				$("#is_freebuy").tips({
					side:3,
		            msg:'请输入备注79',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_freebuy").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>