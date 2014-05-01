package com.nrk.cart.web.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CartItem")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartItem", propOrder = {"cartItemId", "product", "quantity","subTotalPrice"})
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cartItemId;
	private Product product;
	private long quantity;
	private double subTotalPrice;

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(final Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(final Product product) {
		this.product = product;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(final long quantity) {
		this.quantity = quantity;
	}

	public double getSubTotalPrice() {
		return subTotalPrice;
	}

	public void setSubTotalPrice(final double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}

}
