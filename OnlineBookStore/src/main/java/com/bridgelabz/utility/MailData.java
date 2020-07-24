package com.bridgelabz.utility;


import com.bridgelabz.model.Book;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Service
public class MailData {

    private String bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    private String Header = "\t\t\t\t\t\t\t\t\tORDER CONFIRMATION\n\n";
    private String shopName = "\t\t\t\t\t\t\t\t\t\tBookStore Private Limited.\n\n";
    private String shopAdd = "N0 42,\n15th Cross,&14th Main Road\nHsr Layout Opposite to BDA Complex,\nKarnataka 560102\n\n";
    private String sincere = "Sincerely,\nBookstore Private Limited\nadmin@booksStore,in\n";
    private String content= "Thank you again for your order.\n\n"+"We are received your order  and will contact you as soon as your package is shipped\n";
    private String acknowledge="We acknowledge the receipt of your purchase order ";



    public String  getOrderMail(Long orderId, Customer customer, double totalPrice, List<Cart> cart) {
        String allBookData = "";
        for (Cart book : cart) {
            allBookData += "BookName "+book.getBookName() + "\tQuantity " + book.getQuantity() + "\tBookPrice : Rs." + book.getQuantity() * book.getPrice() + "\n";
        }
        String customerDetails="\nShipping Address :"+"\n"+customer.getPhoneNumber()+",\n"+customer.getAddress()+",\n"+customer.getLandMark()+",\n"
                +customer.getCity()+",\n"+customer.getState()+",\n"+customer.getPinCode()+".\n\n";
        return Header + bookingTime + "\n\n" + shopAdd + "Dear  " + customer.getFullName()+ ",\n\n" +
               "Order Number : "+orderId+"\n"+allBookData+"Total Book Price : Rs."+totalPrice+"\n\n"+customerDetails+acknowledge + content + sincere;
    }
}
