package com.elf.crud.exception;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String invalidOtp) {

        super(invalidOtp);
    }
}
