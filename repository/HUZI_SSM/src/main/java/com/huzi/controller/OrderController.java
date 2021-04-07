package com.huzi.controller;


import com.huzi.domain.Order;
import com.huzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @RequestMapping("/insertOrder.do")
    public int insertOrder(Order order){

        //todo 验证非空 数量>0

        int num = orderService.insertOrder(order);


        return num;
    }

}
