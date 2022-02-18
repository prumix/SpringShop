package com.prumi.web.recommendations.model;

import com.prumi.web.api.carts.CartDto;
import com.prumi.web.api.carts.CartItemDto;
import com.prumi.web.api.core.OrderItemDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Statistic {
    private List<StatisticItem> itemList;

    public Statistic() {
        this.itemList = new ArrayList<>();
    }

    public void addCart(List<CartItemDto> cartItemDtoList) {
        for (CartItemDto cid: cartItemDtoList){
           if (add("Cart",cid.getProductTitle(),cid.getQuantity())){
               return;
           }
           itemList.add(new StatisticItem(cid.getProductTitle(),"Cart", cid.getQuantity()));
       }
    }

    public void addOrder(List<OrderItemDto> orderItemDtoList) {
        for (OrderItemDto oid: orderItemDtoList){
            if (add("Order",oid.getProductTitle(),oid.getQuantity())){
                return;
            }
            itemList.add(new StatisticItem(oid.getProductTitle(),"Order", oid.getQuantity()));
        }
    }

    public boolean add(String from, String title, int delta) {
        for (StatisticItem si : itemList) {
            if (si.getTitle().equals(title) && si.getFrom().equals(from)) {
                si.changeQuantity(delta);
                return true;
            }
        }
        return false;
    }

    public void clear(){
        itemList.clear();
    }
}
