<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <script type="text/javascript">
<!--

//-->

//选择商品
		function selectPurchaseDetailSku(){
			 top.jzts();
			 selectPurchaseDetailSkuDiag = new top.Dialog();
			 selectPurchaseDetailSkuDiag.Drag=true;
			 selectPurchaseDetailSkuDiag.Title ="选择商品";
			 selectPurchaseDetailSkuDiag.URL = '<%=basePath%>goodssku/list.do';
			 selectPurchaseDetailSkuDiag.Width = 1250;
			 selectPurchaseDetailSkuDiag.Height = 755;
			 selectPurchaseDetailSkuDiag.Modal = true;				//有无遮罩窗口
			 selectPurchaseDetailSkuDiag. ShowMaxButton = true;	//最大化按钮
			 selectPurchaseDetailSkuDiag.ShowMinButton = true;		//最小化按钮
			 selectPurchaseDetailSkuDiag.CancelEvent = function(){ //关闭事件
				 selectPurchaseDetailSkuDiag.close();
			 };
			 selectPurchaseDetailSkuDiag.show();
			 
		}
		
		//关闭选择商品页面
		function closeSelectPurchaseDetailSkuDiag(Id,name, encode, barcode, unit, spec, isopenBatch, attrCate, taxRate){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			$("#sku_encode").val(encode);
			if(!taxRate)taxRate=0.17;
			$("#tax_rate").val(taxRate);
			$("#spec").val(spec);
			$("#unit").val(unit);
			selectPurchaseDetailSkuDiag.close();
			if(onSelectGoodsClose){
				onSelectGoodsClose();
			}
		}
		</script>