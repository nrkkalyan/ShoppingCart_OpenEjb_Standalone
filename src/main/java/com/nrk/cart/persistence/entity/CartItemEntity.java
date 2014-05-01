package com.nrk.cart.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nrk.cart.persistence.common.BaseEntity;

@Entity
public class CartItemEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;
	private long quantity;
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private CartEntity cart;

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(final ProductEntity product) {
		this.product = product;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(final long quantity) {
		this.quantity = quantity;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(final CartEntity cart) {
		this.cart = cart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CartItemEntity other = (CartItemEntity) obj;
		if (cart == null) {
			if (other.cart != null) {
				return false;
			}
		} else if (!cart.equals(other.cart)) {
			return false;
		}
		if (product == null) {
			if (other.product != null) {
				return false;
			}
		} else if (!product.equals(other.product)) {
			return false;
		}
		return true;
	}

}
