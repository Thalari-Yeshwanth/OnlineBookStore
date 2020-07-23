package com.bridgelabz.model;

import com.bridgelabz.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sequenceNo;

    private long userId;
    private String fullName;
    private String phoneNumber;
    private long  pinCode;
    private String locality;
    private String address;
    private String city;
    private String state;
    private String landMark;
    private String locationType;


    public Customer(CustomerDto customerDto) {
        this.fullName=customerDto.getFullName();
        this.phoneNumber=customerDto.getPhoneNumber();
        this.pinCode=customerDto.getPinCode();
        this.locality=customerDto.getLocality();
        this.address=customerDto.getAddress();
        this.city=customerDto.getCity();
        this.state=customerDto.getState();
        this.landMark=customerDto.getLandMark();
        this.locationType=customerDto.getLocationType();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getLandMark() {
        return landMark;
    }

    public Customer() {
    }
    public void setUserId(long userId) {
        this.userId=userId;
    }

    public long getPinCode() { return this.pinCode; }

    public String getFullName() {return this.fullName; }
}