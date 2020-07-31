package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.model.Book;

import java.util.List;

public interface IBookStoreService {

    List<Book> getAllBooks() throws BookException;

    List<Book> sortBooksByPriceFromLowToHigh() throws BookException;

    List<Book> sortBooksByPriceFromHighToLow() throws BookException;
}
