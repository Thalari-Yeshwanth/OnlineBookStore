package com.bridgelabz.model;

import com.bridgelabz.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity

public class Customer {
    @Id

    public Long customerId;
    public String pinCode;
    public String address;
    public String cityOrTown;
    public String landmark;
    public String addressType;


    public Customer(CustomerDto customerDto) {
        this.pinCode=customerDto.pinCode;
        this.address=customerDto.address;
        this.cityOrTown =customerDto.cityorTown;
        this.landmark=customerDto.landmark;
        this.addressType=customerDto.addressType;
    }

    public Customer() {

    }

    public void setCustomerId(Long userId) {
        this.customerId=userId;
    }

    public String getPinCode() {
        return this.pinCode;
    }
}