package com.fertilizeo.service;

import com.fertilizeo.entity.LoginHistory;
import com.fertilizeo.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoryService {
    @Autowired
    LoginHistoryRepository loginHistoryRepository;

    public LoginHistory addHistory(LoginHistory loginHistory) {
        loginHistoryRepository.save(loginHistory);
        return loginHistory;
    }
}
