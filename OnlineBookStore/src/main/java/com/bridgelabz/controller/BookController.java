package com.bridgelabz.controller;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.model.Book;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.IBookStoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class BookController {

    @Autowired
    public IBookStoreService bookStoreService;

    @ApiOperation("For getting all books")
    @GetMapping("/all")
    public ResponseEntity<Response> getAllBooks() throws BookException {
        List<Book> booksList = bookStoreService.getAllBooks();
        return new ResponseEntity<>(new Response(200, "Returned all books successfully" ,booksList), HttpStatus.OK);
    }

    @ApiOperation("For sorting the books by price from high to low")
    @GetMapping("/sort/byprice/descending")
    public ResponseEntity<Response> sortBooksByPriceFromLowToHigh() throws BookException {
        List<Book> booksList = bookStoreService.sortBooksByPriceFromLowToHigh();
        return new ResponseEntity<>(new Response(200, "Books returned in ascending order by price", booksList), HttpStatus.OK);
    }

    @ApiOperation("For sorting the books by price from low to high")
    @GetMapping("sort/byprice/ascending")
    public ResponseEntity<Response> sortBooksByPriceFromHighToLow() throws BookException {
        List<Book> booksList = bookStoreService.sortBooksByPriceFromHighToLow();
        return new ResponseEntity<>(new Response(200,"Books returned in descending order by price", booksList), HttpStatus.OK);
    }

}

