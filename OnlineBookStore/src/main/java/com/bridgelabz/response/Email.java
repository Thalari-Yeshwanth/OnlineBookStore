package com.bridgelabz.response;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    private String emailTo;
    private String emailFrom;
    private String subject;
    private String message;
}
