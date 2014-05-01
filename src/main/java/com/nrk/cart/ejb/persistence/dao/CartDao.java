package com.nrk.cart.ejb.persistence.dao;

import javax.ejb.Stateless;

import com.nrk.cart.ejb.persistence.common.BaseEntityDaoBean;
import com.nrk.cart.ejb.persistence.entity.CartEntity;

@Stateless
public class CartDao extends BaseEntityDaoBean<CartEntity> {

	@Override
	protected Class<CartEntity> getEntityClass() {
		return CartEntity.class;
	}

}
