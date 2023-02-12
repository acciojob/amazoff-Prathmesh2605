package com.driver;


import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.*;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap = new HashMap<>();
    private HashMap<String, DeliveryPartner> deliveryPartnerMap = new HashMap<>();
    private HashMap<String, HashSet<String>> partnerOrderMap = new HashMap<>();//partner mapped to list of order
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
            HashSet<String> ls = new HashSet<>();
;            if(ls.contains(partnerId)){
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
       if(partnerOrderMap.containsKey(partnerId)){
           return partnerOrderMap.get(partnerId).size();
       }
       return 0;

    }
    //get list of orders by partner
    public List<String> getOrdersByPartnerId(String partnerId){
        HashSet<String>allOrders = new HashSet<>();

        if(partnerOrderMap.containsKey(partnerId)){
            allOrders = partnerOrderMap.get(partnerId);
        }
        return new ArrayList<>(allOrders);
    }
    //get all orders
    public List<String> getAllOrders(){
        return new ArrayList<>(orderMap.keySet());
    }

    //count of unassigned orders
    public int getCountOfUnassignedOrders(){
        Integer count = 0;
        HashSet<String> orders = new HashSet<>(orderMap.keySet());

        for(String order: orders){
            if(!orderPairMap.containsKey(order)){
                count++;
            }
        }
        return count;
    }

    //count of order left undelivered after given time
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        Integer count = 0;
        Integer hour = Integer.valueOf(time.substring(0, 2));
        Integer minutes = Integer.valueOf(time.substring(3));
        Integer dtime = hour*60+minutes;

        if(partnerOrderMap.containsKey(partnerId)){
            HashSet<String> x  = partnerOrderMap.get(partnerId);

            for (String orders : x) {
                if(orderMap.containsKey(orders)){

                    Order order = orderMap.get(orders);
                    if(dtime<order.getDeliveryTime()){
                        count++;
                }
            }
        }
        }
        return count;
    }

    //last delivery by partner
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        Integer max = 0;
        if(partnerOrderMap.containsKey(partnerId)){
            HashSet<String> orders= partnerOrderMap.get(partnerId);
        for (String order: orders){
            if(orderMap.containsKey(order)){
                Order currOrder = orderMap.get(order);
                max = Math.max(currOrder.getDeliveryTime(),max);


            }
        }}
        int hour = max/60;
        int minutes = max%60;

        String hourS = String.valueOf(hour);
        String minS = String.valueOf(minutes);
        if(hourS.length()==1)hourS="0"+hourS;
        if(minS.length()==1)minS="0"+minS;
        return hourS+":"+minS;
    }

    //Delete partner
    public void deletePartner(String partnerId){

        HashSet<String> allOrders = new HashSet<>();
        if(partnerOrderMap.containsKey(partnerId)){
            allOrders = partnerOrderMap.get(partnerId);
        for(String x:allOrders){
            if(orderPairMap.containsKey(x)){
                orderPairMap.remove(x);
            }
        }

        }
        if(deliveryPartnerMap.containsKey(partnerId)){
            deliveryPartnerMap.remove(partnerId);
        }
    }

    //delete order
    public void deleteOrder(String orderId){
        orderMap.remove(orderId);
       if(orderPairMap.containsKey(orderId)){
           String partnerId = orderPairMap.get(orderId);
           HashSet<String> orders = partnerOrderMap.get(partnerId);
           orders.remove(orderId);
           partnerOrderMap.put(partnerId,orders);

           DeliveryPartner partner = deliveryPartnerMap.get(partnerId);
           partner.setNumberOfOrders(orders.size());
       }

    }

}
