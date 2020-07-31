package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.model.Book;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.BookStoreRepository;
import com.bridgelabz.repository.CartRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.response.Response;
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
import java.util.stream.Collectors;

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
        Cart cart1=new Cart(1L,12L,12L,200.0,"TwoStates","JKRowling","http://", "abc",details,false);
        cart.add(cart1);
        Mockito.when(cartRepository.findByUserId(details.getUserId())).thenReturn(cart);
        String token = JwtGenerator.createJWT(1234567);
        List<Cart> allItemFromCart = cartService.getAllItemFromCart(token);
        Assert.assertEquals(allItemFromCart,cart);
    }

    @Test
    public void givenCartRespository_WhenClickOnGetBooksFromWishListShouldReturnCart() throws CartException, BookException {
        List<Cart> actualcart=new ArrayList<>();
        List<UserModel> userDetails=new ArrayList<>();
        UserModel details=new UserModel(1234567L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        userDetails.add(details);
        Cart cart1=new Cart(1l,1L,1,200.0,"HarryPorter","JK rowling","https","abc", details,true);
        actualcart.add(cart1);
        Mockito.when(cartRepository.findByUserId(details.getUserId())).thenReturn(actualcart);
        String token = JwtGenerator.createJWT(1234567);
        List<Cart> expectedCart = cartService.getAllItemFromWishList(token);
        Assert.assertEquals(expectedCart,actualcart);
    }
    @Test
    public void givenCartRespository_WhenClickOnGetBooksFromWishListShouldEmptyCart() throws CartException, BookException {
        List<Cart> actualcart=new ArrayList<>();
        List<UserModel> userDetails=new ArrayList<>();
        UserModel details=new UserModel(1234567L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        userDetails.add(details);
        new Cart();
        Mockito.when(cartRepository.findByUserId(details.getUserId())).thenReturn(actualcart);
        String token = JwtGenerator.createJWT(1234567);
        List<Cart> expectedCart = cartService.getAllItemFromWishList(token);
        Assert.assertEquals(expectedCart,actualcart);
    }
    @Test
    public void givenCartRespository_WhenClickOnGetAllItemsShouldThrowException() {
        List<Cart> cart=new ArrayList<>();
        List<UserModel> userDetails=new ArrayList<>();
        UserModel details=new UserModel(1234567L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        userDetails.add(details);
        Mockito.when(cartRepository.findByUserId(details.getUserId())).thenReturn(cart);
        String token = JwtGenerator.createJWT(1234567);
        cartService.getAllItemFromCart(token);
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
        String token = JwtGenerator.createJWT(1234567l);
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findByUserId(1234567L)).thenReturn(cartList);
        Optional<Book>  book1 = Optional.of(new Book("1",1,"Chetan Bhagat","The Girl in Room 105'","http://books.google.com/books/content?id=GHt_uwEACAAJ&printsec=frontcover&img=1&zoom=5'",2,100.0,"xyz"));
        when(bookStoreRepository.findById(bookId)).thenReturn(book1);
        List<Cart> cartList1 = cartService.addMoreItems(bookId, token);
        long quantity = cartList1.get(0).getQuantity();
        Assert.assertEquals(book.getQuantity()+1,quantity);
    }

    @Test
    public void givenCartRepository_WhenAddMoreQuantity_ShouldReturnUpdatedCart1() {
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Book book1=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findByUserId(book.getBookId())).thenReturn(cartList);
        when(bookStoreRepository.findById(book.getBookId())).thenReturn(Optional.of(book1));
        String token = JwtGenerator.createJWT(1234567l);
        cartService.addMoreItems(123456l,token);

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
        List<Cart> cartList1 = null;
        try {
            cartList1 = cartService.removeItem(bookId, token);
            Assert.assertEquals(cartList1.size(),1);
        } catch (CartException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCartRepository_WhenClickOnDeleteRepository_ShouldReturnResponse() {
        String token = JwtGenerator.createJWT(121);
    //     Mockito.when(cartRepository.deleteByUserId(121L)).thenReturn("Items removed Successfully");
        String response= cartService.deleteAll(token);
        Assert.assertEquals(response,"Items removed Successfully");
    }
    @Test
    public void givenWishlistRepository_WhenClickOnGetAllItemsShouldReturnCart() throws BookException {
        List<Cart> cartList=new ArrayList<>();
        String token = JwtGenerator.createJWT(1234567L);
        Long userId = JwtGenerator.decodeJWT(token);
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        Mockito.when(cartRepository.findByUserId(userId).stream().filter(Cart::isInWishList).collect(Collectors.toList())).thenReturn(cartList);
        List<Cart> allItemFromWishList = cartService.getAllItemFromWishList(token);
//        Assert.assertEquals(cartList,allItemFromWishList);
    }

    @Test
    public void deleteFromWishlist() {
        String token = JwtGenerator.createJWT(1234567L);
        Long userId = JwtGenerator.decodeJWT(token);
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        Mockito.when(cartRepository.findByUserId(userId).stream().filter(Cart::isInWishList).collect(Collectors.toList())).thenReturn(cartList);
        List<Cart> carts = cartService.deleteFromWishlist(1L, token);
        Assert.assertEquals(carts,cartList);
    }

    @Test
    public void addFromWishListToCart() {
        String token = JwtGenerator.createJWT(1234567L);
        long id = JwtGenerator.decodeJWT(token);
        Book book=new Book("1",3L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        Mockito.when(cartRepository.findByUserIdAndBookId(id,3L)).thenReturn(cart);
        Response response = cartService.addFromWishlistToCart(3L, token);
        Assert.assertEquals(response.getStatus(),200);
    }

    @Test
    public void addTOWishList() throws BookException {
        String token = JwtGenerator.createJWT(1234567L);
        long id = JwtGenerator.decodeJWT(token);
        Book book=new Book("1",2L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(11,3L,2,200.0,"Harry Porter","Jk Rowling","http:/","abc",new UserModel(),false);
        Cart cart1=new Cart(12,4L,2,200.0,"Harry Porter","Jk Rowling","http:/","abc",new UserModel(),false);
        List<Book> bookList=new ArrayList<>();
        Optional<UserModel> userDetails = Optional.of(new UserModel(1234567L,"ThalariYeshwanth","yeshwanththalri1998@gmail.com","9666924586","154G5a0123@",true,bookList));
        Optional<Book> book1 = Optional.of(new Book("1",3L,"JK Rowling","Two States","Two States", 2, 200.0,"abc"));
        Mockito.when(cartRepository.findByUserIdAndBookId(id,cart.getBookId())).thenReturn(cart1);
        Mockito.when(cartRepository.findDuplicateBookId(cart.getBookId())).thenReturn(cart.getBookId());
        Mockito.when(bookStoreRepository.findById(book.getBookId())).thenReturn(book1);
        Mockito.when(userRepository.findById(id)).thenReturn(userDetails);
        Response response = cartService.addToWishList(book.getBookId(), token);
       Assert.assertEquals(response.getMessage(),"Book added to WishList");
    }

    @Test
    public void givenCartRepository_WhenRemoveBook_ShouldThrowException() {
        Long bookId=1L;
        String token = JwtGenerator.createJWT(1234567);
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 2, 200.0,"abc");
        Cart cart=new Cart(book);
        List<Cart> cartList1=new ArrayList<>();
        when(cartRepository.findByUserId(1234567L)).thenReturn(cartList1);
        try {
            cartList1 = cartService.removeItem(bookId, token);
            Assert.assertEquals(cartList1.size(),1);
        } catch (CartException e) {
            System.out.println("not removed");
            e.printStackTrace();
        }
    }

}