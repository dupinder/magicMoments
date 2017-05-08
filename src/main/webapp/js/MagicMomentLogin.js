
	$(document).ready(function(){
		compileAllTemplates();
		showSignin();
	});

	var compiledTemplates = new Object();
	function compileAllTemplates(){
		var templates = $('script[type="text/x-handlebar-template"]');
		for(template of templates)
		{
			let compiledTemplate = Handlebars.compile($(template).html());
			compiledTemplates[$(template).attr('id')] = compiledTemplate;
		}
	}

	/*
	container: Element into which content will be loaded
	templateId: Id of templated to be loaded
	context: data to be loaded for template
	*/
	function loadTemplate(container, templateId, context, method){
		let template = compiledTemplates[templateId];
		if(template != undefined)
		{
			var html = template(context);
			container.html(html);
		}
	}
	
	function showSignin(data){
		loadTemplate($('#content-loader'), 'signin', data);
		$('.signin').bind('click', signin);	
	}
	
	function showSignup(data){
		loadTemplate($('#content-loader'), 'email-verification', data);
		$('.verify').bind('click', verifyEmail);
	}
	
	function showSecondLevelSignup(data){
		loadTemplate($('#content-loader'), 'signup', data);
		$('.signup').bind('click', signup);
	}
	
	function signup(){
		var signinBlock = $('.signup-block');
		$.ajax({
			url: 'UserSignup',
			data:{email: signinBlock.find('.email').val().trim(), otp: signinBlock.find('.otp').val().trim(), password: signinBlock.find('.password').val().trim(), confirmPassword: signinBlock.find('.confirm-password').val().trim()},
			type: 'POST',
			success: function(response){
				var response = JSON.parse(response);
				var result = response['result'];
				let message;
				if("true" == result)
				{
					showSignin();
					message = "You have signed up successfully. Signin now..!!";
					$('.signin-block .result .result-content').text(failureCause).addClass('alert alert-denger');
					$('.signin-block .result').removeClass('hide');
				}
				else if(result == "false")
				{
					let cause = response['cause'];
					if(cause !== undefined)
						message = cause;
					else
						message = "Whoahh..Something went wrong..!!";
					
					signinBlock.find('.result .result-content').html(message).addClass('alert alert-warning');
					signinBlock.find('.result').removeClass('hide');
				}
				
			},
			failure: function(error){}
		});
	}
	
	function verifyEmail(){
		showSpinner(true);
		var email = $('#verification-email').val();
		$.ajax({
			url: 'VerifyEmail',
			data:{email: email},
			type: 'POST',
			success: function(response){
				if("" == response || "false" == response)
				{
					var resultDiv = $('.email-verification .result');
					resultDiv.find('.result-content').text("Invalid email address").addClass('alert alert-warning');
					resultDiv.removeClass('hide');
				}
				else
				{
					var jsonResponse = JSON.parse(response);
					var isValidUser = jsonResponse['isValidUser'];
					var isNewUser = jsonResponse['isNewUser'];
					if(isNewUser == "false" && isValidUser == "true")
					{
						var resultDiv = $('.email-verification .result');
						resultDiv.find('.result-content').text("You are already registered with us..!").addClass('alert alert-warning');
						resultDiv.removeClass('hide');
					}	
					else if(isNewUser == "true" && isValidUser == "true")
					{
						var data = {
								email: email,
								registrationMessage: 'Seems you are not registered with us yet.',
								otpMessage: 'An email has been sent to your email id. Complete registration process',
						}
						
						showSecondLevelSignup(data);
					}	
					else if(isValidUser == "false")
					{
						var resultDiv = $('.email-verification .result');
						resultDiv.find('.result-content').text("Whoah...Something went wrong..!").addClass('alert alert-denger');
						resultDiv.removeClass('hide');
					}			
				}	
				showSpinner(false);
			},
			failure: function(error){showSpinner(false);}
		});
	}
	
	function signin(){
		var email = $('.signin-block .email').val();
		var password = $('.signin-block .password').val();
		
		var isValidEmail = validateEmail(email);
		var isValidPassword = validatePassword(password);
		
		var errorBlock = $('.signin-block .error-content');
		errorBlock.html('');
		errorBlock.addClass('hide');
		
		 var resultDiv = $('.signin-block .result');
		resultDiv.addClass('hide');
		
		if(!isValidEmail || !isValidPassword)
		{
			if(!isValidEmail)
			{
				errorBlock.append('- ' + emailValidationErrorMessage);	
			}	
			if(!isValidPassword)
			{
				errorBlock.append('<br>' + '- ' + passwordValidationErrorMessage);	
			}	
			
			resultDiv.removeClass('hide');
			errorBlock.removeClass('hide');
			return;
		}	
		
		showSpinner(true);
		$.ajax({
			url: 'UserSignin',
			data: {email: email, password: password},
			type: 'POST',
			success: function(response){
				var response = JSON.parse(response);
				var result = response['result'];
				if(result == "true")
				{
					showHome();
				}
				else if(result == "false")
				{
					var failureCause = response['cause'];
					if(failureCause !== undefined)
					{
						$('.signin-block .result .result-content').text('');
						$('.signin-block .result .result-content').text(failureCause).addClass('alert alert-danger');
						$('.signin-block .result').removeClass('hide');
					}
				}
				showSpinner(false);	
			},
			failure: function(error){
				showSpinner(false);
			}
		});
	}
	

	function showSpinner(show){
		if(show)
			$('.spinner-main').css('width', '100%');
		else
			$('.spinner-main').css('width', '0');
	}
	
	function showHome(){
		window.location.href = "Gallery.html";
	}
	
	var emailValidationErrorMessage = "Enter valid email";
	function validateEmail(email){
		var pattern = /^[a-zA-Z]{1,30}[\w-]{0,30}(\.[A-Za-z0-9-_]{1,30})*@[a-zA-Z0-9-]{1,30}(\.[A-Za-z0-9-]{1,30}){1,2}$/;
		if(email != '' && email != undefined && pattern.test(email))
			return true;
		else
			return false;
	}
	
	var passwordValidationErrorMessage = "Password must contain atleast an uppercase letter & a numeric";
	function validatePassword(password){
		var pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
		if(password != '' && password != undefined && pattern.test(password))
			return true;
		else
			return false;		
	}