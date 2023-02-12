package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }
    public void addPartner(String partnerId){
        orderRepository.addPartner(partnerId);
    }
    public void assignOrderToPartner(String orderId, String partnerId){
        orderRepository.assignOrderToPartner(orderId,partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrder(orderId);

    }
    public DeliveryPartner getPartner(String partnerId){
        return orderRepository.getPartner(partnerId);
    }
    public int getNoOfOrderByPartner(String partnerId){
        return  orderRepository.getOrderCountByPartnerId(partnerId);

    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();

    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartner(String partnerId){
        orderRepository.deletePartner(partnerId);
    }

    public void deleteOrder(String orderId){
        orderRepository.deleteOrder(orderId);
    }

}
