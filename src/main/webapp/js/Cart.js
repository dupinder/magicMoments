function addItemToCart(item, element){
	sendAjax("user/addToCart", 'POST', {photoId: item}, function(response){
		$(element).css('color', 'green');
	}, function(error){
		
	});
}

function addItemToWishlist(item, element){
	sendAjax("user/addToWishlist", 'POST', {photoId: item}, function(response){
		$(element).css('color', 'green');
	}, function(error){
		
	});
}