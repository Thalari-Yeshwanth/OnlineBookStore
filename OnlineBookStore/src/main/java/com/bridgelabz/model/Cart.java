package com.bridgelabz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Cart")
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
		private Long id;
		private Long bookId;
		private long quantity;
		private double price;
		private String bookName;
		private String authorName;
		private String image;
		private String bookDetails;
		private boolean isInWishList;


		@JsonIgnore
		@ManyToOne()
		@JoinColumn(name="userId")
		public UserModel userDetails;



	public Cart(Book book) {
		this.bookId=book.getBookId();
		this.quantity=1;
		this.price=book.getPrice();
		this.bookName=book.getBookName();
		this.authorName=book.getAuthorName();
		this.image=book.getImage();
		this.bookDetails=this.getBookDetails();
	}

	public Cart(long id, long bookId, long quantity, double price, String bookName, String authorName, String image, String bookDetails,  UserModel userDetails) {

		this.id = id;
		this.bookId = bookId;
		this.quantity = quantity;
		this.price = price;
		this.bookName = bookName;
		this.authorName = authorName;
		this.image = image;
		this.bookDetails = bookDetails;
		this.userDetails = userDetails;
	}
}
