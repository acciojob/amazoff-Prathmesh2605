package com.driver;


import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.*;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap = new HashMap<>();
    private HashMap<String, DeliveryPartner> deliveryPartnerMap = new HashMap<>();
    private HashMap<String, List<String>> partnerOrderMap = new HashMap<>();//partner mapped to list of order
    private HashMap<String, String> orderPairMap = new HashMap<>();

    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
    }

    public void addPartner(String partnerId){
        deliveryPartnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    //Assign an order to a partner
    public void assignOrderToPartner(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && deliveryPartnerMap.containsKey(partnerId)){
            List<String> ls = new ArrayList<>();
            if(ls.contains(partnerId)){
                ls = partnerOrderMap.get(partnerId);
            }
        ls.add(orderId);
        partnerOrderMap.put(partnerId, ls);
        DeliveryPartner partner = deliveryPartnerMap.get(partnerId);
        partner.setNumberOfOrders(ls.size());
        orderPairMap.put(orderId,partnerId);

    }}

    //Get order by id
    public Order getOrder(String orderId){
        return orderMap.get(orderId);
    }

    //get partner by id
    public DeliveryPartner getPartner(String partnerId){
        return deliveryPartnerMap.get(partnerId);
    }

    //get no of ordersByPartner
    public int getNoOfOrderByPartner(String partnerId){
        if(partnerOrderMap.containsKey(partnerId)) {
            return partnerOrderMap.get(partnerId).size();
        }
        return 0;
    }
    //get list of orders by partner
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> allOrders = new ArrayList<>();
        if(partnerOrderMap.containsKey(partnerId)){
            allOrders = partnerOrderMap.get(partnerId);
        }
        return allOrders;
    }
    //get all orders
    public List<String> getAllOrders(){
        List<String> allOrders = new ArrayList<>();
        for(String x: orderMap.keySet()){
            allOrders.add(x);
        }
        return allOrders;
    }

    //count of unassigned orders
    public int getCountOfUnassignedOrders(){
        int count = 0;
        for(String x:orderMap.keySet()){
            if(!orderPairMap.containsKey(x)){
                count++;
            }

        }
        return count;
    }

    //count of order left undelivered after given time
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int count = 0;
        Integer hour = Integer.valueOf(time.substring(0, 2));
        Integer minutes = Integer.valueOf(time.substring(3));
        int dtime = hour*60+minutes;
        for (List<String> x :partnerOrderMap.values()){
            for(String orders: x){
                if(orderMap.get(orders).getDeliveryTime()>dtime){
                    count++;
                }
            }
        }
        return count;
    }

    //last delivery by partner
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int max = 0;
        for (List<String> x :partnerOrderMap.values()){
            for(String orders: x){
                max = Math.max(orderMap.get(orders).getDeliveryTime(),max);


            }
        }
        return String.valueOf(max);
    }

    //Delete partner
    public void deletePartner(String partnerId){
        List<String> allOrders = partnerOrderMap.get(partnerId);
        for(String x:allOrders){
            orderPairMap.remove(x);

        }
        partnerOrderMap.remove(partnerId);
    }

    //delete order
    public void deleteOrder(String orderId){
        orderPairMap.remove(orderId);

        orderMap.remove(orderId);

    }

}
