package com.nrk.cart.ejb.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.nrk.cart.ejb.persistence.common.BaseEntity;
import com.nrk.cart.web.domain.OrderStatus;

@Entity
public class CartEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus = OrderStatus.UNPAID;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
	private Set<CartItemEntity> cartItems = new HashSet<>();
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime = new Date();

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(final OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Set<CartItemEntity> getCartItems() {
		return cartItems;
	}

	public void setCartItems(final Set<CartItemEntity> cartItems) {
		this.cartItems = cartItems;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(final Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Transient
	public void addCartItem(final CartItemEntity cartItem) {
		cartItems.add(cartItem);
	}

	@Transient
	public CartItemEntity getExistingCartItem(final long productId) {
		for (CartItemEntity cie : cartItems) {
			if (cie.getProduct().getId().equals(productId)) {
				return cie;
			}
		}
		return null;
	}

}
