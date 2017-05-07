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
	
	elementOnWhichSpinnerShouldApper.on('load', function(){
		$(this).find('.inline-spinner-main').remove();
	});
}
function dismissAlert(element)
{
	$(element.parentElement).fadeOut(1000);
}

function alertSuccess(message)
{
	var alertContainer = $("<div />");

	alertContainer.addClass("alert alert-success alert-dismissable")
		.css({'margin-top' : '18px', 'position' : 'absolute', 'top' : '10px', 'right' : '35%', 'left' : '35%', 'z-index' : '1000000'})
		.attr({'id':'alertContainer'});

	var crossIcon = $("<a />").attr({'href':'#', 'data-dismiss':'alert', 'aria-label' : 'close', 'title': 'close', 'onclick' : 'dismissAlert(this)'}).addClass('close');
		crossIcon.append('x');
	
	alertContainer.append(crossIcon[0]);
	alertContainer.append(message);
	
	var alertBody = $('.alertSection').clone();
	alertBody.append(alertContainer[0].outerHTML);
	alertBody.removeClass('hide').addClass('show');
	$('body').append(alertBody[0].outerHTML);
	var currentAlert = $('.alertSection:visible');
	
	setTimeout(function(){
		currentAlert.fadeOut(1000, function(){
			$(this).remove();
		});
	}, 5000);
}