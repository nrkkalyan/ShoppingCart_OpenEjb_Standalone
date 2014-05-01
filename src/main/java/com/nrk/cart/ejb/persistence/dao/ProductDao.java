package com.nrk.cart.ejb.persistence.dao;

import javax.ejb.Stateless;

import com.nrk.cart.ejb.persistence.common.BaseEntityDaoBean;
import com.nrk.cart.ejb.persistence.entity.ProductEntity;

@Stateless
public class ProductDao extends BaseEntityDaoBean<ProductEntity> {

	@Override
	protected Class<ProductEntity> getEntityClass() {
		return ProductEntity.class;
	}

}
