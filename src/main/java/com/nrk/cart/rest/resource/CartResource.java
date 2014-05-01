package com.nrk.cart.rest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nrk.cart.interceptors.AuditLogInterceptor;
import com.nrk.cart.persistence.dao.CartDao;
import com.nrk.cart.persistence.dao.CartItemDao;
import com.nrk.cart.persistence.dao.ProductDao;
import com.nrk.cart.persistence.entity.CartEntity;
import com.nrk.cart.persistence.entity.CartItemEntity;
import com.nrk.cart.persistence.entity.ProductEntity;
import com.nrk.cart.rest.domain.Cart;
import com.nrk.cart.rest.domain.CartItem;
import com.nrk.cart.rest.domain.OrderStatus;
import com.nrk.cart.rest.domain.Product;
import com.nrk.cart.rest.utils.EntityToDomainMapper;

@Stateless
@Path("carts")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Interceptors(AuditLogInterceptor.class)
public class CartResource {

  @EJB
  private CartDao cartDao;
  @EJB
  private CartItemDao cartItemDao;
  @EJB
  private ProductDao productDao;

  @POST
  public Cart createCart(final CartItem cartItem) {
    CartEntity cartEntity = new CartEntity();
    CartItemEntity cie = mapCartItemToEntity(cartItem);
    cie.setCart(cartEntity);
    cartEntity.addCartItem(cie);
    cartDao.create(cartEntity);
    return EntityToDomainMapper.entityToCart(cartEntity);
  }

  private CartItemEntity mapCartItemToEntity(final CartItem cartItem) {
    CartItemEntity cie = new CartItemEntity();
    Product product = cartItem.getProduct();
    ProductEntity pe = productDao.findById(product.getProductId());
    cie.setProduct(pe);
    cie.setQuantity(cartItem.getQuantity());
    return cie;
  }

  @POST
  @Path("{id}/items")
  public Cart addItem(@PathParam("id") final long cartId, final CartItem cartItem) {
    CartEntity cart = cartDao.findById(cartId);
    if (cart.getOrderStatus() != OrderStatus.UNPAID) {
      throw new WebApplicationException(Response.Status.CONFLICT);
    }
    updateCart(cartItem, cart);
    return EntityToDomainMapper.entityToCart(cart);
  }

  private void updateCart(final CartItem cartItem, final CartEntity cartEntity) {
    long productId = cartItem.getProduct().getProductId();
    CartItemEntity existingCartItem = cartEntity.getExistingCartItem(productId);
    if (existingCartItem != null) {
      long oldQuantity = existingCartItem.getQuantity();
      existingCartItem.setQuantity(oldQuantity + cartItem.getQuantity());
    } else {
      CartItemEntity cie = mapCartItemToEntity(cartItem);
      cie.setCart(cartEntity);
      cartEntity.addCartItem(cie);
    }
  }

  @PUT
  @Path("{id}")
  public Cart checkOut(@PathParam("id") final long cartId, final Cart cart) {
    CartEntity ce = cartDao.findById(cartId);

    if (cart.getOrderStatus() == null) {
      throw new IllegalArgumentException("Status should not be empty");
    }

    if (!ce.getId().equals(cart.getCartId())) {
      throw new WebApplicationException(Response.Status.CONFLICT);
    }
    ce.getCartItems().clear();
    for (CartItem cartItem : cart.getCartItems()) {
      updateCart(cartItem, ce);
    }
    ce.setOrderStatus(cart.getOrderStatus());
    return EntityToDomainMapper.entityToCart(ce);
  }

  @DELETE
  @Path("{cartid}/items/{itemid}")
  public Response deleteItem(@PathParam("cartid") final long cartId,
      @PathParam("itemid") final long itemId) {
    cartItemDao.deleteById(itemId);
    return Response.ok().build();
  }

  @DELETE
  @Path("{id}")
  public Response deleteCart(@PathParam("id") final long cartId) {
    cartDao.deleteById(cartId);
    return Response.ok().build();
  }

  @GET
  public List<Cart> getAllCarts() {
    List<Cart> cartsList = new ArrayList<>();
    for (CartEntity cart : cartDao.listAll()) {
      Cart c = EntityToDomainMapper.entityToCart(cart);
      cartsList.add(c);
    }
    return cartsList;
  }

  @GET
  @Path("{id}")
  public Cart getCart(@PathParam("id") final long id) {
    CartEntity cart = cartDao.findById(id);
    return EntityToDomainMapper.entityToCart(cart);
  }

}
