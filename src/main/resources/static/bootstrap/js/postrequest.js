$( document ).ready(function() {
    
	var url = window.location;
	
	$("#btnId").click(function(event){
        event.preventDefault();
        // Open Bootstrap Modal
        openModel();
        // get data from Server
        ajaxPost();
	})
	
    // Open Bootstrap Modal
    function openModel(){
    	$("#modalId").modal();
    }
	
	/*<script>var ctx = "${pageContext.request.contextPath}"</script>*/
    
    // DO GET
	
	var itemName = $("#itemName");
	var itemPrice = $("#price");
    function ajaxPost(){
        $.ajax({
            type : "POST",
            url : "/item",
            data : { name:itemName, price:itemPrice }",
            contentType: "application/json; charset=utf-8",
            success: function(data){
            	// fill data to Modal Body
                fillData(data);
            },
            error : function(e) {
            	fillData(null);
            }
        }); 
    }
    
    function fillData(data){
    	if(data!=null){
            $(".modal-body #greetingId").text(data);
    	}else{
            $(".modal-body #greetingId").text("Can Not Get Data from Server!");
    	}
    }
})