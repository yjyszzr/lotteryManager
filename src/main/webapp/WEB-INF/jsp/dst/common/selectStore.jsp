<%@page import="com.fh.common.StoreTypeConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script type="text/javascript">
    var storeForOther = "<%=StoreTypeConstants.STORE_GOOD+","+StoreTypeConstants.STORE_GIFT+","+StoreTypeConstants.STORE_LOSS%>"
  //选择供应商仓库
	function selectStore(types){
		 top.jzts();
		 selectStoreDiag = new top.Dialog();
		 selectStoreDiag.Drag=true;
		 selectStoreDiag.Title ="选择仓库";
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
	
</script>
