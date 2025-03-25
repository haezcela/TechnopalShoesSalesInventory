<div class="col-lg-12 text-center" id="divCalendar"></div>
<script>
	function myClock() {         
		setTimeout(function() {  
		    const d = new Date();
		    const options = {
		    	weekday: 'long',
		    	year: 'numeric',
		    	month: 'long',
		    	day: 'numeric',
		    };
		    
		    $("#divCalendar").html("<h2 class='text-white'>" + d.toLocaleDateString("en-US", options) + " | " + d.toLocaleTimeString() + "</h2>"); 
		    myClock();             
		  }, 1000)
	}
	myClock();    
</script>