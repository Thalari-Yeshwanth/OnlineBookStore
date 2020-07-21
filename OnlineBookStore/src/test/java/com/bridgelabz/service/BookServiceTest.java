package com.bridgelabz.service;
import com.bridgelabz.exception.BookException;
import com.bridgelabz.model.Book;
import com.bridgelabz.repository.BookStoreRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    List<Book> booksList;
    List<Book> expectedBookList;

    @Mock
    private ModelMapper mapper;

    @Mock
    private BookStoreRepository mockedBookShopRepository;

    @InjectMocks
    private BookStoreService bookStoreServices;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void givenBookStore_WhenLoginSuccessful_ShouldReturnList() throws BookException {
        booksList = new ArrayList<>();
        expectedBookList = new ArrayList<>();
        Book books1 = new Book("1", (long) 1,"Chetan Bhagat","The Girl in Room 105",
                "http://", 12,193.0,"abc");
        booksList.add(books1);
        when(mockedBookShopRepository.findAll()).thenReturn(booksList);
        expectedBookList = bookStoreServices.getAllBooks();
        Assert.assertEquals(booksList.get(0).getAuthorName(), expectedBookList.get(0).getAuthorName());
    }

    @Test
    public void givenBookStore_WhenLoginSuccessful_ShouldReturnTotalSizeOfRecord() throws BookException {
        booksList = new ArrayList<>();
        Book books1 = new Book("1",(long)1,"Chetan Bhagat","The Girl in Room 105'","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",12,100.0,"xyz");
        Book books2 = new Book("1",(long)2,"Jk Rowling","Harry Porter","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",5,200.0,"abc");
        booksList.add(books1);
        booksList.add(books2);
        when(mockedBookShopRepository.findAll()).thenReturn(booksList);
        List<Book> allBooks = bookStoreServices.getAllBooks();
        int size = booksList.size();
        Assert.assertEquals(allBooks.size(), size);
    }


    @Test
    public void givenBookStore_WhenClickSortByLowPriceOnHomePage_ShouldReturnRecordsBasedOnLowestPrice() throws BookException {
        booksList = new ArrayList<>();
        Book books1 = new Book("1",(long)1,"Chetan Bhagat","The Girl in Room 105'","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",12,100.0,"xyz");
        Book books2 = new Book("1",(long)2,"Jk Rowling","Harry Porter","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",5,200.0,"abc");
        booksList.add(books2);
        booksList.add(books1);
        when(mockedBookShopRepository.findAll()).thenReturn(booksList);
        expectedBookList = bookStoreServices.sortBooksByPriceFromLowToHigh();
        Assert.assertEquals(expectedBookList.get(0),booksList.get(0));
    }
    @Test
    public void givenBookStore_WhenClickSortByHighPriceOnHomePage_ShouldReturnRecordsBasedOnHighestPrice() throws BookException {
        booksList = new ArrayList<>();
        Book books1 = new Book("1",(long)1,"Chetan Bhagat","The Girl in Room 105'","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",12,100.0,"xyz");
        Book books2 = new Book("1",(long)2,"Jk Rowling","Harry Porter","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",5,200.0,"abc");
        booksList.add(books2);
        booksList.add(books1);
        when(mockedBookShopRepository.findAll()).thenReturn(booksList);
        expectedBookList = bookStoreServices.sortBooksByPriceFromHighToLow();
        Assert.assertEquals(expectedBookList.get(0),booksList.get(1));
    }

}
