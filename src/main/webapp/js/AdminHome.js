$(document).ready(function(){
	showSpinner(true);
});

function showSpinner(show){
	if(show)
		$('.spinner-main').css('width', '100%');
	else
		$('.spinner-main').css('width', '0');
}

function showDropdownOptions(selector){
	$('.dropdown-option').hide();
	selector.find('.dropdown-option').show();
}