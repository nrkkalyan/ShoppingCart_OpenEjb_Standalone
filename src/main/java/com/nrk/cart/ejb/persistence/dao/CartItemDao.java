package com.nrk.cart.ejb.persistence.dao;

import javax.ejb.Stateless;

import com.nrk.cart.ejb.persistence.common.BaseEntityDaoBean;
import com.nrk.cart.ejb.persistence.entity.CartItemEntity;

@Stateless
public class CartItemDao extends BaseEntityDaoBean<CartItemEntity> {

  @Override
  protected Class<CartItemEntity> getEntityClass() {
    return CartItemEntity.class;
  }


}
