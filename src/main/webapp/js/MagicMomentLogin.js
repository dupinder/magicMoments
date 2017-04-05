var data = {
					email: '',
					message: 'An OPT on provided email has been sent.',
					otp: '',
			}
	$(document).ready(function(){
		compileAllTemplates();
		showVerificationTemplate();

		$('.verify').click(function(){
			var email = $('#verification-email').val();
			$.ajax({
				url: 'VerifyEmail',
				data:{email: email},
				type: 'POST',
				success: function(response){
					if("true" == response)
					{
						data.email = email;
						loadTemplate($('#content-loader'), 'signin', data);
						
						$('.login').bind('click', function(){
							$.ajax({
								url: 'UserSignup',
								data:{email: data.email, otp: $('.otp').val().trim(), password: $('.password').val().trim(), confirmPassword: $('.confirm-password').val().trim()},
								type: 'POST',
								success: function(response){
									//showHome();
									if("true" == response)
									{
										console.log("Singed up successfully");
										window.location.href = "Gallery.html";
									}
								},
								failure: function(error){}
							});
						});	
					}	
										},
				failure: function(error){}
			});
		});	
	});

	var compiledTemplates = new Object();
	function showVerificationTemplate(){
		loadTemplate($('#content-loader'), 'email-verification', null);
	}

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