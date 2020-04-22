package com.gydx.bookManager.service;


import com.gydx.bookManager.pojo.ReceiveData;

import javax.servlet.http.HttpServletRequest;

public interface ForgetService {
    String getCode(String email);

    String updatePassword(ReceiveData receiveData);
}
