function addItemToCart(item, element){
	var eventId = $('.gallery-header-text .eventId').text();
	sendAjax("user/addToCart", 'POST', {photoId: item, eventId: eventId}, function(response){
		handleResponseForCartItems(response, element, 'added-to-cart');
	}, function(error){
		
	});
}

function addItemToWishlist(item, element){
	var eventId = $('.gallery-header-text .eventId').text();
	sendAjax("user/addToWishlist", 'POST', {photoId: item, eventId: eventId}, function(response){
		handleResponseForCartItems(response, element, 'added-to-wishlist');
	}, function(error){
		
	});
}

function handleResponseForCartItems(response, element, className){
	if(response == "false")
	{
		console.log("Something went wrong");
	}	
	else
	{
		response = JSON.parse(response);
		if(response.result == "false")
		{
			console.log("Something went wrong");
		}	
		else
			$(element).addClass(className);
	}	
}