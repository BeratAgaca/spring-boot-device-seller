package com.sha.springbootdeviceseller.service;

import com.sha.springbootdeviceseller.model.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
