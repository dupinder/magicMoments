function sendAjax(url, type, dataJson, successCallback, failureCallback)
{
	showSpinner(true);
	
	$.ajax({
		url: url,
		type: type,
		data: dataJson,
		success: successCallback,
		failure: failureCallback,
	}).always(function(){
		showSpinner(false);
	});
}

function showSpinner(show){
	if(show)
		$('.spinner-main').css('width', '100%');
	else
		$('.spinner-main').css('width', '0');
}

function showInlineSpinner(elementOnWhichSpinnerShouldApper){
	var spinner = $('.inline-spinner-main').clone();
	spinner.css('width', '100%');
	elementOnWhichSpinnerShouldApper.append(spinner[0].outerHTML);
}