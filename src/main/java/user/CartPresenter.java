package user;

import drive.Photos;

public class CartPresenter {

	private PhotosBag photoBag;
	private Photos photo;
	
	
	
	public CartPresenter(PhotosBag photoBag, Photos photo) {
		super();
		this.photoBag = photoBag;
		this.photo = photo;
	}
	
	public PhotosBag getPhotoBag() {
		return photoBag;
	}
	public void setPhotoBag(PhotosBag photoBag) {
		this.photoBag = photoBag;
	}
	public Photos getPhoto() {
		return photo;
	}
	public void setPhoto(Photos photo) {
		this.photo = photo;
	}
	
}
