package com.bridgelabz.contoller;

import com.bridgelabz.controller.BookController;
import com.bridgelabz.exception.BookException;
import com.bridgelabz.model.Book;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.BookStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @Mock
    private BookStoreService bookStoreService;

    @InjectMocks
    private BookController bookController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }
    List<Book>bookList;

    @Test
    public void givenBookStore_WhenClickOnHomePage_ShouldReturnList() throws BookException {
        bookList = new ArrayList<>();
        Book books1 = new Book("1",1, "Jk Rowling", "Harry Porter",  "Image.jpg", 1, 100.0,"abcd");
        bookList.add(books1);
        when(bookStoreService.getAllBooks()).thenReturn(bookList);
        String id = bookList.get(0).get_id();
        ResponseEntity<Response> allBooks = bookController.getAllBooks();
        Assert.assertEquals(id, "1");
    }
    @Test
    public void givenBookStore_WhenClickOnHomePage_IfRecordListIsNull_ShouldThrowNullException() {
        bookList = new ArrayList<>();
        try {
        BookException   expectedException= new BookException("Books not available", BookException.ExceptionType.BOOKS_NOT_AVAILABLE);
            when(bookStoreService.getAllBooks()).thenThrow(expectedException);
            bookController.getAllBooks();
        } catch (BookException e) {
            System.out.println(e.type);
            Assert.assertEquals(BookException.ExceptionType.BOOKS_NOT_AVAILABLE, e.type);
        }
    }
    @Test
    public void givenBookStore_WhenClickOnSortByRElavanceInAscendingOrder_ShouldReturnList() throws BookException {
        bookList = new ArrayList<>();
        Book books1 = new Book("1",1, "Jk Rowling", "Harry Porter",  "Image.jpg", 1, 100.0,"abcd");
        Book books2 = new Book("2",2, "Chethan Bhagath", "Two States",  "Image.jpg", 1, 200.0,"abcd");
        bookList.add(books1);
        bookList.add(books2);
        when(bookStoreService.sortBooksByPriceFromLowToHigh()).thenReturn(bookList);
        ResponseEntity<Response> response=bookController.sortBooksByPriceFromLowToHigh();
        Object data = response.getBody().getData();
        Assert.assertEquals(data,bookList);
    }
    @Test
    public void givenBookStore_WhenClickOnSortByRElavanceInDescendingOrder_ShouldReturnList() throws BookException {
        bookList = new ArrayList<>();
        Book books1 = new Book("1",1, "Jk Rowling", "Harry Porter",  "Image.jpg", 1, 100.0,"abcd");
        Book books2 = new Book("2",2, "Chethan Bhagath", "Two States",  "Image.jpg", 1, 200.0,"abcd");
        bookList.add(books2);
        bookList.add(books1);
        when(bookStoreService.sortBooksByPriceFromHighToLow()).thenReturn(bookList);
        ResponseEntity<Response> response=bookController.sortBooksByPriceFromHighToLow();
        Object data = response.getBody().getData();
        Assert.assertEquals(data,bookList);
    }
    @Test
    public void givenBookStore_WhenClickOnHomePage_ShouldRetunResponse() throws BookException {
        bookList = new ArrayList<>();
        Book books1 = new Book("1",1, "Jk Rowling", "Harry Porter",  "Image.jpg", 1, 100.0,"abcd");
        bookList.add(books1);
        when(bookStoreService.getAllBooks()).thenReturn(bookList);
        String bookDetails = bookList.get(0).getBookDetails();
        ResponseEntity<Response> allBooks = bookController.getAllBooks();
        Assert.assertEquals(bookDetails, "abcd");
    }
}
