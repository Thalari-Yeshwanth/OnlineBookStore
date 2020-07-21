package com.bridgelabz.model;

import lombok.*;
import javax.persistence.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Column
	private String _id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private long bookId;
	@Column
	private String authorName;
	@Column
	private String bookName;
	@Column
	private String image;
	@Column
	private int quantity;
	@Column
	public double price;
	@Column
	private String bookDetails;

}