
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
				if("" != response)
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
						$('.signin-block .result .result-content').text(failureCause).addClass('alert alert-denger');
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