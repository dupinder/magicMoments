$(document).ready(function(){


	$('.login-button').click(function(){


		var usernameEle = $('input#username'); 
		var passwordEle = $('input#password');
		var username = usernameEle.val().trim();
		var password = passwordEle.val().trim();

		var isValidated = validateEmail(username, usernameEle);
		if(isValidated)
			isValidated = validatePassword(password, passwordEle);

		if(isValidated)
			window.location.href ="Home.html";

		/* $.ajax({
			url: '',
			type: post,
			data: {username: username, password: password}
		});
		*/
	});
});

function validateEmail(email, e){
	e.removeClass("validation-error");
	var emailPattern = /^[a-zA-Z]{1,30}[\w-]{0,30}(\.[A-Za-z0-9-_]{1,30})*@[a-zA-Z0-9-]{1,30}(\.[A-Za-z0-9-]{1,30}){1,2}$/; /* Email Validation Regex which supports  lowercase,uppercase, digits and special characters like dot,underscore and hyphen  */

	if(email == undefined || !emailPattern.test(email))
	{
		shakeElement(e);
		return false;
	}

	return true;
}

function validatePassword(password, e){
	e.removeClass("validation-error");
	if(password == undefined || password == "")
	{
		shakeElement(e);
		return false;
	}

	return true;
}

function shakeElement(element){
	element.removeClass("validation-error");
	setTimeout(function(){
		element.addClass("validation-error");
	}, 100);
}