package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryOrderDao;
import com.github.holiver98.model.*;
import com.github.holiver98.service.CartServiceBase;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.service.IMailService;
import com.github.holiver98.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryCartService extends CartServiceBase {
    private IInMemoryOrderDao orderDao;

    public InMemoryCartService(IUserService userService, IInMemoryOrderDao inMemoryOrderDao, IMailService mailService) {
        super(userService, mailService);
        orderDao = inMemoryOrderDao;
    }

    @Override
    protected void save(Order order) {
        orderDao.saveOrder(order);
    }
}
