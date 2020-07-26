package com.bridgelabz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public class CustomerDto {

    private String fullName;
    private String phoneNumber;
    private long pinCode;
    private String locality;
    private String address;
    private String city;
    private String state;
    private String landMark;
    private String locationType;

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getPinCode() {
        return pinCode;
    }

    public String getLocality() {
        return locality;
    }

    public String getAddress() {
        return address;
    }


    public String getState() {
        return state;
    }

    public String getLandMark() {
        return landMark;
    }

    public String getLocationType() {
        return locationType;
    }
}
