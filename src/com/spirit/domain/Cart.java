package com.spirit.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Cart {
	private Map<String, CartItem> itemMap = new HashMap<String,CartItem>();
	private Double total = 0.0;
	
	/**
	 * 获取所有的购物项
	 * @return
	 */
	public Collection<CartItem> getCartItems(){
		return itemMap.values();
	}
	public Map<String, CartItem> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, CartItem> itemMap) {
		this.itemMap = itemMap;
	}
	public Double gettotal() {
		return total;
	}
	public void settotal(Double total) {
		this.total = total;
	}

	
	/**
	 * 加入购物车
	 * @param item
	 */
	public void add2cart(CartItem item){
		//获取商品id
		String pid = item.getProduct().getPid();
		
		//判断购物车是否有
		if (itemMap.containsKey(pid)) {
			//有	修改数量 = 原来的数量 + 新加的数量
			//原有的购物项
			CartItem oItem = itemMap.get(pid);
			
			oItem.setCount(oItem.getCount()+item.getCount());
		} else {
			//无
			itemMap.put(pid, item);
		}
		//修改总金额
		total += item.getSubtotal();
	}
	
	/**
	 * 从购物车移除
	 * @param pid
	 */
	public void removeFromCart(String pid){
		//1.从购物车（map）移除购物项
		CartItem item = itemMap.remove(pid);
		
		//2.修改总金额
		total -= item.getSubtotal();
	}
	
	/**
	 * 清空购物车
	 */
	public void clearcart(){
		//1.清空map
		itemMap.clear();
		//2.修改总金额 = 0
		total = 0.0;
	}
}
