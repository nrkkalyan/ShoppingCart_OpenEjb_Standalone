package com.nrk.cart.persistence.dao;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.nrk.cart.interceptors.AuditLogInterceptor;
import com.nrk.cart.persistence.common.BaseEntityDaoBean;
import com.nrk.cart.persistence.entity.CartEntity;

@Stateless
@Interceptors(AuditLogInterceptor.class)
public class CartDao extends BaseEntityDaoBean<CartEntity> {

	@Override
	protected Class<CartEntity> getEntityClass() {
		return CartEntity.class;
	}

}
