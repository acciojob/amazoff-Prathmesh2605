package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }
    public void addPartner(String partnerId){
        orderRepository.addPartner(partnerId);
    }
    public void assignOrderToPartner(String orderId, String partnerId){
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);

    }
    public DeliveryPartner getPartner(String partnerId){
        return orderRepository.getPartnerById(partnerId);
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
        int countOfOrders = orderRepository.getCountOfUnassignedOrders();
        return countOfOrders;

    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int countOfOrders = orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartner(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrder(String orderId){
        orderRepository.deleteOrderById(orderId);
    }

}
