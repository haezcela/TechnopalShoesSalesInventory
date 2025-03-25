<html lang="en">
<head>
	<!-- SITE TITTLE -->
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title></title>
	<!-- Plugins css Style -->
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fontawesome-5.15.2/css/all.min.css" rel="stylesheet">
<!--    	<link href="/static/star-2-3/Static HTML/assets/plugins/fontawesome-5.15.2/css/fontawesome.min.css" rel="stylesheet"> -->
   	<link href="/static/star-2-3/Static HTML/assets/plugins/animate/animate.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/menuzord/css/menuzord.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/menuzord/css/menuzord-animations.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/isotope/isotope.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fancybox/jquery.fancybox.min.css" rel="stylesheet">

   	<link href="/static/star-2-3/Static HTML/assets/plugins/daterangepicker/css/daterangepicker.css" rel="stylesheet">
    <link href='/static/star-2-3/Static HTML/assets/plugins/slick/slick.css' rel='stylesheet'>
    <link href='/static/star-2-3/Static HTML/assets/plugins/slick/slick-theme.css' rel='stylesheet'>
   	<link href="/static/star-2-3/Static HTML/assets/plugins/dzsparallaxer/dzsparallaxer.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/revolution/css/settings.css" rel="stylesheet">
   	
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fancybox/jquery.fancybox.min.css" rel="stylesheet">
    <link href="/static/star-2-3/Static HTML/assets/plugins/owl-carousel/owl.carousel.min.css" rel="stylesheet" media="screen">
    <link href="/static/star-2-3/Static HTML/assets/plugins/owl-carousel/owl.theme.default.min.css" rel="stylesheet" media="screen">
   	
   	<!-- GOOGLE FONT -->
   	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,600,700" rel="stylesheet">
   	<!-- CUSTOM CSS -->
   	<link href="/static/star-2-3/Static HTML/assets/css/star.css" id="option_style" rel="stylesheet">
   	<!-- FAVICON -->
   	<link rel="shortcut icon" type="image/png" href="/static/star-2-3/Static HTML//assets/img/favicon.png"/>

	<link rel="stylesheet" href="/static/star-2-3/Static HTML//assets/css/star-color1.css" id="option_style">
	
	<link href="/static/common/sweet_alert2/sweetalert2.css" rel="stylesheet"> 
	<link href="/static/common/fontawesome-free-6.6.0-web/css/all.min.css" rel="stylesheet"> 
	
	
	<style>
		.autocomplete {
		  /*the container must be positioned relative:*/
		  position: relative;
		  display: inline-block;
		}
		.autocomplete-items {
		  position: absolute;
		  border: 1px solid #d4d4d4;
		  border-bottom: none;
		  border-top: none;
		  z-index: 99;
		  /*position the autocomplete items to be the same width as the container:*/
		  top: 100%;
		  left: 0;
		  right: 0;
		}
		.autocomplete-items div {
		  padding: 10px;
		  cursor: pointer;
		  background-color: #fff;
		  border-bottom: 1px solid #d4d4d4;
		}
		.autocomplete-items div:hover {
		  /*when hovering an item:*/
		  background-color: #e9e9e9;
		}
		.autocomplete-active {
		  /*when navigating through the items using the arrow keys:*/
		  background-color: DodgerBlue !important;
		  color: #ffffff;
		}
	</style>
	
</head>     
<body>  

	<div class="autocomplete" style="width:300px;">
	  <input id="myInput" type="text" name="myCountry" placeholder="Country" onblur="autoCompleteCheckInput(this)">
	</div>
	<input type="text" id="txtSelectedAutoCompleteId">
	
	<div class="autocomplete" style="width:300px;">
	  <input id="myInput2" type="text" onblur="autoCompleteCheckInput(this)">
	</div>
	<input type="text" id="txtSelectedAutoCompleteId2">
</body>

<script src="/static/star-2-3/Static HTML/assets/plugins/jquery/jquery-3.4.1.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/menuzord/js/menuzord.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/isotope/isotope.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/images-loaded/js/imagesloaded.pkgd.min.js"></script>
<script src="/static/star-2-3/Static HTML//assets/plugins/fancybox/jquery.fancybox.min.js"></script>
<!--     <script src="/static/star-2-3/Static HTML//assets/plugins/selectric/jquery.selectric.min.js"></script> -->
<script src="/static/star-2-3/Static HTML/assets/plugins/daterangepicker/js/moment.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/daterangepicker/js/daterangepicker.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/lazyestload/lazyestload.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/dzsparallaxer/dzsparallaxer.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/slick/slick.min.js"></script>

<script src="/static/star-2-3/Static HTML/assets/plugins/revolution/js/jquery.themepunch.tools.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/revolution/js/jquery.themepunch.revolution.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/owl-carousel/owl.carousel.min.js"></script>
<script src="/static/star-2-3/Static HTML/assets/plugins/smoothscroll/SmoothScroll.js"></script>
<script src="/static/star-2-3/Static HTML/assets/js/star.js"></script>	
<script src="common/common.js"></script>

<script src="/static/common/sweet_alert2/sweetalert2.all.js"> </script>

<script>
	var countries = ["Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua &amp; Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia &amp; Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central Arfrican Republic","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cuba","Curacao","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kiribati","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauro","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","North Korea","Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre &amp; Miquelon","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","St Kitts &amp; Nevis","St Lucia","St Vincent","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad &amp; Tobago","Tunisia","Turkey","Turkmenistan","Turks &amp; Caicos","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"];
	var countriesId = ["Afg","Alb","Alg","And","Angola","Anguilla","Antigua &amp; Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia &amp; Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central Arfrican Republic","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cuba","Curacao","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kiribati","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauro","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","North Korea","Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre &amp; Miquelon","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","St Kitts &amp; Nevis","St Lucia","St Vincent","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad &amp; Tobago","Tunisia","Turkey","Turkmenistan","Turks &amp; Caicos","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"];
	
	setTimeout(function (){
		autoComplete(document.getElementById("myInput"), countries, countriesId, document.getElementById("txtSelectedAutoCompleteId"));
		autoComplete(document.getElementById("myInput2"), countries, countriesId, document.getElementById("txtSelectedAutoCompleteId2"));
	}, 50); 
</script>
</html>
