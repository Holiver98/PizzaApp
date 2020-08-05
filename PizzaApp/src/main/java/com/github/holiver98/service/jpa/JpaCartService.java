package com.github.holiver98.service.jpa;

import com.github.holiver98.dal.jpa.IOrderRepository;
import com.github.holiver98.model.*;
import com.github.holiver98.service.CartServiceBase;
import com.github.holiver98.service.IMailService;
import com.github.holiver98.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaCartService extends CartServiceBase {
    @Autowired
    private IOrderRepository orderRepository;

    public JpaCartService(IUserService userService, IMailService mailService){
        super(userService, mailService);
    }

    @Override
    protected void save(Order order) {
        if(order.getId() != null){
            throw new IllegalArgumentException("order should not have an id, if you want to save it");
        }
        orderRepository.save(order);
    }
}
