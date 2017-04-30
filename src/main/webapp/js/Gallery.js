var contentLoader = $('#content-loader');
var selectedImages = [];

$('document').ready(function(){
	compileAllTemplates();
	showHome();
});	

function showDropdownOptions(selector){
	$('.dropdown-option').hide();
	selector.find('.dropdown-option').show();
}

function Event(id, eventThumbnailURL, folderId, branchName, collegeName, dataDelete, endDate, startDate, discription, name){
	this.id = id;
	this.eventThumbnailURL = eventThumbnailURL;
	this.folderId = folderId;
	this.branchName = branchName;
	this.collegeName = collegeName;
	this.dataDelete = dataDelete;
	this.endDate = endDate;
	this.startDate = startDate;
	this.discription = discription;
	this.name = name;
}

function UserData(events, userData){
	this.events = events;
	this.userData = userData;
	this.jsonData = () => JSON.stringify(this);
}

function showHome(){
	showSpinner(true);
	$.ajax({
		url: 'user/HomePage',
		type: 'POST',
		success: function(response){
			if(response != "")
			{
				response = JSON.parse(response);
				var result = response.result;
				
				var userData = JSON.parse(response.dataUser);
				var events = JSON.parse(response.events);
				var dataItemCountsBasedOnType = response.dataItemCountsBasedOnType;
				
				var allEvents = [];
				if(Array.isArray(events))
				{
					for(event of events)
					{
						var ev = new Event(event.id, event.eventThumbnailURL, event.folderId, userData.branchName, userData.collegeName, event.dataDelete, event.endDate, event.startDate, event.discription, event.name);
						allEvents.push(ev);
					}
				}	
				
				var ud = new UserData(allEvents, userData);
				loadTemplate(contentLoader, 'events', JSON.parse(ud.jsonData()));
				$('.dropdown-option .username').text("Hi, " + userData.name);
			}	
			else
			{
				var homeData = JSON.parse(response);
			}
			showSpinner(false);
		},
		failure: function(error){
			showSpinner(false);
		}
	});
}		

function PhotoPrenter(photos, eventName, photosAddedToCart, photosAddedToWishlist){
	this.photos = photos;
	this.eventName = eventName;
	this.photosAddedToCart = photosAddedToCart;
	this.photosAddedToWishlist = photosAddedToWishlist;
	this.jsonData = () => JSON.stringify(this);
}

function Photo(photoId, photoName, photoUrl, photoDiscription, createdDate, deletedDate, photoPrice, photoThumbnailUrl, addedToCart, addedToWishlist){
	this.formatDate = function(date){
		var dateStringSplitted = Date(createdDate).toString().split(" "); 
		return dateStringSplitted[2] + ' '+ dateStringSplitted[1] + ' ' + dateStringSplitted[3];
	};
	this.id = photoId;
	this.name = photoName;
	this.url = photoUrl;
	this.thumbnailUrl = photoThumbnailUrl;
	this.desc = photoDiscription;
	this.createDate = this.formatDate(createdDate);
	this.deleteDate = this.formatDate(deletedDate);
	this.price = photoPrice;
	this.addedToCart = addedToCart;
	this.addedToWishlist = addedToWishlist;
	this.jsonData = () => JSON.stringify(this);
}

function navigateToPhotos(folderId, eventName){
	sendAjax('user/getEventPhotos', 'POST', {folderId:folderId}, function(response){
		if(response != "")
		{
			response = JSON.parse(response);
			var photos = response.photos;
			var photosAddedToCart = response.photosAddedToCart;
			var photosAddedToWishlist = response.photosAddedToWishlist;
			
			if(Array.isArray(photos))
			{
				let allPhotos = [];
				for(photo of photos)
				{
					let photoId = photo.photoId;
					let addedToCart = photosAddedToCart.indexOf(photoId) != -1 ? 'added-to-cart' : '';
					let addedToWishlist = photosAddedToWishlist.indexOf(photoId) != -1 ? 'added-to-wishlist' : '';
					let ph = new Photo(photoId,photo.photoName,photo.photoUrl,photo.photoDiscription,photo.createdDate,photo.deletedDate,photo.photoPrice,photo.photoThumbnailUrl, addedToCart, addedToWishlist);
					allPhotos.push(ph);
				}
				
				var photoPrenter = new PhotoPrenter(allPhotos, eventName);
				loadTemplate(contentLoader, 'gallery-home', JSON.parse(photoPrenter.jsonData()));
			}	
		}	
	}, function(error){
		
	});
}

//function compileAllTemplates(){
//	var templates = $('script[type="text/x-handlebar-template"]');
//	for(template of templates)
//	{
//		let compiledTemplate = Handlebars.compile($(template).html());
//		compiledTemplates[$(template).attr('id')] = compiledTemplate;
//	}
//}

/*
	container: Element into which content will be loaded
	templateId: Id of templated to be loaded
	context: data to be loaded for template
*/
//function loadTemplate(container, templateId, context, method){
//	let template = compiledTemplates[templateId];
//	if(template != undefined)
//	{
//		var html = template(context);
//		container.html(html);
//	}
//}

function addToCart(imageId){

	var selectedPhotosData = selectedPhotos.photos;
	var selectedImageElement = $('#' + imageId + ' .addImageIcon');
	
	if(selectedImageElement.hasClass('fa-plus'))
	{
		selectedImageElement.removeClass('fa-plus').addClass('fa-check');	
	}	
	else
	{
		selectedImageElement.addClass('fa-plus').removeClass('fa-check');
	}	
}

function showCart(){
	
	sendAjax('user/showCartView', 'GET', {}, function(cartHtml){
		
		sendAjax('user/showCartView', 'POST', {}, function(jsonCartData){
			
			var cartData = JSON.parse(jsonCartData);
			if(cartData.result == "true")
			{
				var cartPresenter = cartData.dataPhotosWithPrices;
				cartPresenter = JSON.parse(cartPresenter);
				
				var templateId = compileTemplate(cartHtml);
				loadTemplate(contentLoader, templateId, {'cartPresenters': cartPresenter});
				
				var imagediv = $('.cart-view .image-div');
				var spinner = $('.spinner-main').clone();
				spinner.removeClass('spinner-main').addClass('inline-spinner-main');
				spinner.find('.spinner').addClass('inline-spinner').removeClass('spinner');
				spinner.css('width', '100%');
				imagediv.append(spinner[0].outerHTML);
				
				$('.cart-view img').on('load', function(){
					$(this).siblings('.inline-spinner-main').remove();
				});
			}	
		}, function(error){});
		
	}, function(error){
		
	});
}

function navigateToNext(){
	var activeTab = $('li.nav-item.active');
	var index = activeTab.attr('tab');
	if(index == $('li.nav-item').length - 1)
		return;
	
	var newActiveTab = $(activeTab.next());
	newActiveTab.addClass('active');
	activeTab.removeClass('active');
	$('div[data-parent]').hide();
	$('div[data-parent="'+ newActiveTab.attr('id') +'"]').show();
}

function navigateToPrevious(){
	var activeTab = $('li.nav-item.active');
	var index = activeTab.attr('tab');
	if(index == 0)
		return;
	
	var newActiveTab = $(activeTab.prev());
	newActiveTab.addClass('active');
	activeTab.removeClass('active');
	$('div[data-parent]').hide();
	$('div[data-parent="'+ newActiveTab.attr('id') +'"]').show();
}

function openImage(url){
	//$('.open-image-overlay img').attr('src', url);
	//$('.open-image-overlay').show();
	$('#myNav img').attr('src', url);
	$('#myNav').css('width', '100%');
}

function closeImage(){	
	$('#myNav').css('width', '0');	
	setTimeout(function(){
		$('#myNav img').attr('src', "");
	}, 300);	
}

function showLogoutOption(){
	$('.logout-option').show();
}
