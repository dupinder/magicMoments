var $mobileDevice = false;
var slidableEle = $('.slide');
		$('document').ready(function(element){
			

			if($(window).width() >= 800)
			{
				$mobileDevice = false;
				$('.slide').removeClass('slide');
				$('.signup').hide();
			}	
			else
				$mobileDevice = true;

			$('.login-wrap .nav li').click(function(element){

				var activeTab = $('.login-wrap .nav li.active');
				var activeTabData = activeTab.attr('data-tab-name');
				var tabData = $(this).attr('data-tab-name');

				if(activeTabData == tabData)
					return;

				if(!$mobileDevice)
				{
					if(tabData == "register")
					{
						$('.signup').show();
						$('.login').hide();
					}	
					else
					{
						$('.login').show();
						$('.signup').hide();
					}	
				}	
				else
					$('.reel').removeClass(activeTabData).addClass(tabData).attr('data-remove');

				activeTab.removeClass('active');
				$(this).addClass('active');
				
			});

			$('.show-password, .show-password').click(function(){

				if($(this).hasClass('show-password'))
				{
					$('.element-container #password').attr('type', 'text');
					$(this).addClass('hide-password').removeClass('show-password');
					$(this).text('hide');
				}	
				else if($(this).hasClass('hide-password'))
				{
					$('.element-container #password').attr('type', 'password');
					$(this).addClass('show-password').removeClass('hide-password');
					$(this).text('show');
				}
				
				
			});
		});