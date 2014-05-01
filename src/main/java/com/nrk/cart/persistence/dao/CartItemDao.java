package com.nrk.cart.persistence.dao;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.nrk.cart.interceptors.AuditLogInterceptor;
import com.nrk.cart.persistence.common.BaseEntityDaoBean;
import com.nrk.cart.persistence.entity.CartItemEntity;

@Stateless
@Interceptors(AuditLogInterceptor.class)
public class CartItemDao extends BaseEntityDaoBean<CartItemEntity> {

  @Override
  protected Class<CartItemEntity> getEntityClass() {
    return CartItemEntity.class;
  }


}
