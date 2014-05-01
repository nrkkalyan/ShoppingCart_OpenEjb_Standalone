package com.nrk.cart.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Cart")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cart", propOrder = {"cartId", "cartItems", "totalPrice","orderStatus"})
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cartId;
	private final List<CartItem> cartItems = new ArrayList<>();
	private double totalPrice;
	private OrderStatus orderStatus;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(final Long cartId) {
		this.cartId = cartId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void addCartItem(final CartItem cartItem) {
		this.cartItems.add(cartItem);
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(final double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(final OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void addCartItems(final CartItem item) {
		cartItems.add(item);
	}

}
