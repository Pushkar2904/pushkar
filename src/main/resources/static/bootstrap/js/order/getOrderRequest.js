$( document ).ready(function() {
    
	var url = window.location;
	
	$("#orderBtnId").click(function(event){
        event.preventDefault();
        // Open Bootstrap Modal
        openModel();
        // get data from Server
        ajaxGet();
	})
	
    // Open Bootstrap Modal
    function openModel(){
    	$("#modalId").modal();
    }
	
	/*<script>var ctx = "${pageContext.request.contextPath}"</script>*/
    
    // DO GET
    function ajaxGet(){
        $.ajax({
            type : "GET",
            url : "/order",            
            contentType: "application/json; charset=utf-8",
            success: function(data){            	
            	$("#postResultMsg").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" + JSON.stringify(data));
            },
            error : function(e) {
            	alert("Error!")
            }
        }); 
    }
    
  
})