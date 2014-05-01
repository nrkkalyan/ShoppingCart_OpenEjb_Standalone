package com.nrk.cart.rest.utils;

import com.nrk.cart.persistence.entity.CartEntity;
import com.nrk.cart.persistence.entity.CartItemEntity;
import com.nrk.cart.persistence.entity.ProductEntity;
import com.nrk.cart.rest.domain.Cart;
import com.nrk.cart.rest.domain.CartItem;
import com.nrk.cart.rest.domain.Product;

public class EntityToDomainMapper {

	public static Product entityToProduct(final ProductEntity entity) {
		Product p = new Product();
		p.setProductId(entity.getId());
		p.setProductName(entity.getProductName());
		p.setUnitPrice(entity.getPrice());
		return p;
	}

	public static Cart entityToCart(final CartEntity entity) {
		Cart c = new Cart();
		c.setCartId(entity.getId());
		c.setOrderStatus(entity.getOrderStatus());
		double totalPrice = 0;
		for (CartItemEntity cie : entity.getCartItems()) {
			CartItem ci = entityToCartItem(cie);
			totalPrice += ci.getSubTotalPrice();
			c.addCartItems(ci);
		}
		c.setTotalPrice(Math.floor(totalPrice));
		return c;
	}

	public static CartItem entityToCartItem(final CartItemEntity entity) {
		CartItem ci = new CartItem();
		ci.setCartItemId(entity.getId());
		ci.setQuantity(entity.getQuantity());
		ProductEntity productEntity = entity.getProduct();
		ci.setSubTotalPrice(entity.getQuantity() * productEntity.getPrice());
		ci.setProduct(entityToProduct(productEntity));
		return ci;
	}

}
