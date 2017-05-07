function addItemToCart(item, element){
	var eventId = $('.eventDetails .eventId').text();
	sendAjax("user/addToCart", 'POST', {photoId: item, eventId: eventId}, function(response){
		var result = handleResponseForCartItems(response, element, 'added-to-cart');
		if(result == 'added')
		{
			alertSuccess("Picture has been added to cart successfully");
		}	
		else
		{
			alertSuccess("Picture has been removed from cart successfully");
		}
		
	}, function(error){
		
	});
}

function addItemToWishlist(item, element){
	var eventId = $('.eventDetails .eventId').text();
	sendAjax("user/addToWishlist", 'POST', {photoId: item, eventId: eventId}, function(response){
		var result = handleResponseForCartItems(response, element, 'added-to-wishlist');
		if(result == 'added')
		{
			alertSuccess("Picture has been added to wishlist successfully");
		}	
		else
		{
			alertSuccess("Picture has been removed from wishlist successfully");
		}
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
		{
			if(response.itemAdded == "true")
			{
				$(element).addClass(className);
				return 'added';
			}
			else if(response.itemRemoved == "true")
			{
				$(element).removeClass(className);
				return 'removed';
			}
		}
	}	
}