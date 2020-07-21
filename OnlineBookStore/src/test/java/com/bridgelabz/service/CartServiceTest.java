package com.bridgelabz.service;
import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.model.Book;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.BookStoreRepository;
import com.bridgelabz.repository.CartRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.utility.JwtGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BookStoreRepository bookStoreRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void givenCartRespository_WhenClickOnGetAllItemsShouldReturnCart() throws CartException {
        List<Cart> cart=new ArrayList<>();
        List<UserModel> userDetails=new ArrayList<>();
        UserModel details=new UserModel(1234567L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        userDetails.add(details);
        Cart cart1=new Cart(1L,12L,12L,200.0,"TwoStates","JKRowling","http://", "abc",details);
        cart.add(cart1);
        Mockito.when(cartRepository.findByUserId(details.getUserId())).thenReturn(cart);
        String token = JwtGenerator.createJWT(1234567);
        cartService.getAllItemFromCart(token);

    }
    @Test
    public void givenCartRespository_WhenClickOnGetAllItemsShouldThrowException() {
        List<Cart> cart=new ArrayList<>();
        List<UserModel> userDetails=new ArrayList<>();
        UserModel details=new UserModel(1234567L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        userDetails.add(details);
        Mockito.when(cartRepository.findByUserId(details.getUserId())).thenReturn(cart);
        String token = JwtGenerator.createJWT(1234567);
        try {
            cartService.getAllItemFromCart(token);
        } catch (CartException e) {
            Assert.assertEquals(e.type, CartException.ExceptionType.EMPTY_CART);
        }

    }



    @Test
    public void givenCartRepository_WhenAddtoCart_ShouldReturnResponse() throws BookException {
        Long bookId=1L;
        String token = JwtGenerator.createJWT(1234567);
        Optional<Book>  book1 = Optional.of(new Book("1",1,"Chetan Bhagat","The Girl in Room 105'","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",12,100.0,"xyz"));
        when(bookStoreRepository.findById(bookId)).thenReturn(book1);
        List<Book> bookList=new ArrayList<>();
        Optional<UserModel> userDetails = Optional.of(new UserModel(1234567L,"ThalariYeshwanth","yeshwanththalri1998@gmail.com","9666924586","154G5a0123@",true,bookList));
        when(userRepository.findById(1234567L)).thenReturn(userDetails);
        String response = cartService.addToCart(token, bookId);
        Assert.assertEquals(response,"book added to cart successfully");
    }
    @Test
    public void givenCartRepository_WhenAddMoreQuantity_ShouldReturnUpdatedCart() {
        Long bookId=1L;
        String token = JwtGenerator.createJWT(1234567);
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findByUserId(1234567L)).thenReturn(cartList);
        List<Cart> cartList1 = cartService.addMoreItems(bookId, token);
        long quantity = cartList1.get(0).getQuantity();
        Assert.assertEquals(book.getQuantity()+1,quantity);
    }
    @Test
    public void givenCartRepository_WhenSubtractQuantity_shouldReturnUpdatedCart() {
        Long bookId=1L;
        String token = JwtGenerator.createJWT(1234567);
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findByUserId(1234567L)).thenReturn(cartList);
        List<Cart> cartList1 = cartService.subtractItem(bookId, token);
        long quantity = cartList1.get(0).getQuantity();
        Assert.assertEquals(book.getQuantity()-1,quantity);

    }
    @Test
    public void givenCartRespository_WhenSubtractQuantity_shouldReturnCart() {
        Long bookId=2L;
        String token = JwtGenerator.createJWT(1234567);
        Book book1=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart1=new Cart(book1);
        Book book2=new Book("1",2L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart2=new Cart(book2);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart1);
        cartList.add(cart2);
        when(cartRepository.findByUserId(1234567L)).thenReturn(cartList);
        List<Cart> cartList1 = cartService.subtractItem(bookId, token);
        long quantity = cartList1.get(0).getQuantity();
        Assert.assertEquals(book2.getQuantity()-1,quantity);
    }

    @Test
    public void givenCartRepoistory_WhenRemoveBook_ShouldReturnUpdatedCart() {
        Long bookId=1L;
        String token = JwtGenerator.createJWT(1234567);
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findByUserId(1234567L)).thenReturn(cartList);
        List<Cart> cartList1 = cartService.removeItem(bookId, token);
        System.out.println(cartList1);
        Assert.assertEquals(cartList1.size(),1);

    }
    @Test
    public void givenCartRepository_WhenClickOnDeleteRepository_ShouldReturnResponse() {
        String response= cartService.deleteAll();
        Assert.assertEquals(response,"Items Removed Successfully");
    }
}