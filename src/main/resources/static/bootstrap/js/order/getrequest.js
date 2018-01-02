$( document ).ready(function() {
    
	var url = window.location;
	
	$("#ordtnId").click(function(event){
		
		event.preventDefault();
			ajaxPost();
	    	
	})

    
    // DO GET
    function ajaxPost(){
		
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/order",			
			dataType: 'text',
			success : function(result) {
				$("#postResultMsg").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" + result);
			},
			error : function(e) {
				alert("Error!")
			}
		});
    }
   
})