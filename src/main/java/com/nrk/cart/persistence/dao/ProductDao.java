package com.nrk.cart.persistence.dao;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.nrk.cart.interceptors.AuditLogInterceptor;
import com.nrk.cart.persistence.common.BaseEntityDaoBean;
import com.nrk.cart.persistence.entity.ProductEntity;

@Stateless
@Interceptors(AuditLogInterceptor.class)
public class ProductDao extends BaseEntityDaoBean<ProductEntity> {

	@Override
	protected Class<ProductEntity> getEntityClass() {
		return ProductEntity.class;
	}

}
