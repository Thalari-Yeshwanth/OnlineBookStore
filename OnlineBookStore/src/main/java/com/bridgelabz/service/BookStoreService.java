package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.model.Book;
import com.bridgelabz.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookStoreService implements IBookStoreService{

    @Autowired
    public BookStoreRepository bookstoreRepository;

    @Override
    public List<Book> getAllBooks() throws BookException {
        List<Book> booksList = bookstoreRepository.findAll();
        if (booksList.isEmpty()) {
            throw new BookException("Books are not available", BookException.ExceptionType.BOOKS_NOT_AVAILABLE);
        }
         return booksList;
    }

    @Override
    public List<Book> sortBooksByPriceFromLowToHigh() throws BookException {
        List<Book> booksList= getAllBooks();
        return booksList.stream().sorted((firstBook,secondBook)-> (int)(secondBook.price-firstBook.price))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> sortBooksByPriceFromHighToLow() throws BookException {
        List<Book> booksList= getAllBooks();
        return booksList.stream().sorted((firstBook,secondBook)-> (int) (firstBook.price-secondBook.price))
                .collect(Collectors.toList());
    }


}
