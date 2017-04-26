var folderData = {
			    events: [ 
			      { id: '1', url: '', thumbnailUrl: 'img/drive-download/DSC08271.jpg', name: 'LPU Fest'},
			      { id: '2', url: '', thumbnailUrl: 'img/drive-download/DSC08279.jpg',  name:'IIT Manipur farewell 2016'},
			      { id: '3', url: '', thumbnailUrl: 'img/drive-download/DSC08283.jpg',  name:'IIFA internation awards'},
			      { id: '4', url: '', thumbnailUrl: 'img/drive-download/DSC08289.jpg',  name:'Academy award 2017'},
			      { id: '5', url: '', thumbnailUrl: 'img/drive-download/DSC08296.jpg',  name:'Mickael Jackson concert LIVE..' },
			      { id: '6', url: '', thumbnailUrl: 'img/drive-download/DSC08312.jpg',  name:'David Guetta - Sunburn 2016 GOA' },
			      { id: '7', url: '', thumbnailUrl: 'img/drive-download/mikka.jpg',  name:'Doctor ke kaam'},
			      { id: '8', url: '', thumbnailUrl: 'img/drive-download/DSC08438.jpg',  name:'Bibek Saini ne kiya nach'},
			      { id: '9', url: '', thumbnailUrl: 'img/LPU fest/i.jpg',  name:'Peele rang se savdhan Full searies' },
			      { id: '10', url: '', thumbnailUrl: 'img/LPU fest/j.jpg',  name:'Paddlers Party, February, 2017'},
			    ]
			  };

var photos = {
	events: [ 
	  { id: '1', url: '', thumbnailUrl: 'img/drive-download/DSC08271.jpg', name: 'David Guetta' },
	  { id: '2', url: '', thumbnailUrl: 'img/drive-download/DSC08279.jpg', name: 'Meditation and menifest' },
	  { id: '3', url: '', thumbnailUrl: 'img/drive-download/DSC08283.jpg', name: 'Jatta de putt'},
	  { id: '4', url: '', thumbnailUrl: 'img/drive-download/DSC08289.jpg', name: 'DBNMSHFBRMsansaeWEDNDScccecc wdwdwdddwdwd'},
	  { id: '5', url: '', thumbnailUrl: 'img/drive-download/mikka.jpg', name: 'Counting stars'},
	]
};

var contentLoader = $('#content-loader');
var selectedImages = [];

$('document').ready(function(){
	compileAllTemplates();
	showHome();
});	

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

function UserData(events, photos){
	this.events = events;
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
				
				var ud = new UserData(allEvents);
				loadTemplate(contentLoader, 'events', JSON.parse(ud.jsonData()));
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

function PhotoPrenter(photos, eventName){
	this.photos = photos;
	this.eventName = eventName;
	this.jsonData = () => JSON.stringify(this);
}

function Photo(photoId, photoName, photoUrl, photoDiscription, createdDate, deletedDate, photoPrice, photoThumbnailUrl, eventName){
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
	this.eventName = eventName;
	this.jsonData = () => JSON.stringify(this);
}

function navigateToPhotos(folderId, eventName){
	sendAjax('user/getEventPhotos', 'POST', {folderId:folderId}, function(response){
		if(response != "")
		{
			var photos = JSON.parse(response);
			if(Array.isArray(photos))
			{
				var allPhotos = [];
				for(photo of photos)
				{
					var ph = new Photo(photo.photoId,photo.photoName,photo.photoUrl,photo.photoDiscription,photo.createdDate,photo.deletedDate,photo.photoPrice,photo.photoThumbnailUrl);
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
		/*jQuery.getJSON('').then(function(promiseObejct){
			console.log(promiseObejct.stringify());
		});*/

	var selectedPhotos = {
	photos: [
	  { id: '1', url: 'img/try.jpg', desc: '', name: 'LPU Youth vibe, 2017', location: 'Jalandhar'},
	  { id: '2', url: 'img/LPU Fest/b.jpg', desc: '', name: 'Academy Award, 2017', location: 'Kodak cinema, Las Vegas'},
	  { id: '3', url: 'img/LPU Fest/f.jpg', desc: '', name: 'CatalystOne solutions Seminar', location: 'Hotel Marriot, Chandigarh'},
	  { id: '4', url: 'img/LPU Fest/a.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '5', url: 'img/LPU Fest/d.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '6', url: 'img/LPU Fest/g.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '7', url: 'img/LPU Fest/e.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '8', url: 'img/drive-download/DSC08271.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '9', url: 'img/drive-download/DSC08279.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '10', url: 'img/drive-download/DSC08283.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '11', url: 'img/drive-download/DSC08289.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '12', url: 'img/drive-download/DSC08296.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '13', url: 'img/drive-download/mikka.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	  { id: '14', url: 'img/drive-download/DSC08358.jpg', desc: '', name: 'Life is Easy Seminar', location: 'Baba chandandas Ashram'},
	]
};

	loadTemplate(contentLoader, 'cart', selectedPhotos);
	$('div[data-parent]').not('[data-parent="'+ $('li.nav-item.active').attr('id') +'"]').hide();
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
