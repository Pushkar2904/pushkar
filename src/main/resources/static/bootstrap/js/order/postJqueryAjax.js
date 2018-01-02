$(document).ready(function(){
	
	// SUBMIT FORM
    $("#orderStackedForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		
		var inputs = $(this).find('input');
		
		var data = {
	    		id 			: $(inputs[0]).val(),
	    		itemId 		: $(inputs[1]).val(),
	    		quantity 	: $(inputs[2]).val()
	    	}


		ajaxPut(data);
    	
    	// reset input data
    	$(inputs[0]).val("");
    	$(inputs[1]).val(""),
    	$(inputs[2]).val("")
	});
    
    function ajaxPut(data){
    	
    	// DO POST
    	$.ajax({
			type : "PUT",
			contentType : "application/json",
			url : "/order/"+data.id+"/addItem/"+data.itemId+"/"+data.quantity,
			data : JSON.stringify(data),
			dataType: 'text',			
			success : function(result) {
				$("#postResultMsg").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" + result);
			},
			error : function(e) {
				alert("Error!")
			}
		});
    	
    }
    
});