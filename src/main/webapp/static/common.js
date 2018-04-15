		function doResetForm(){
			$(':input','#Form') 
			.not(':button, :submit, :reset, :hidden') 
			.val('') 
			.removeAttr('checked') 
			.removeAttr('selected');
		}