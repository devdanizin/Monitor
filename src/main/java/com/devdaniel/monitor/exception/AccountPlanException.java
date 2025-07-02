package com.devdaniel.monitor.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountPlanException extends AuthenticationException {
    public AccountPlanException(String msg) {
        super(msg);
    }
}