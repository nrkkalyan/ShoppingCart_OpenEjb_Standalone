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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nrk.cart.interceptors.AuditLogInterceptor;
import com.nrk.cart.persistence.dao.ProductDao;
import com.nrk.cart.persistence.entity.ProductEntity;
import com.nrk.cart.rest.domain.Product;
import com.nrk.cart.rest.utils.EntityToDomainMapper;

@Stateless
@Path("products")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Interceptors(AuditLogInterceptor.class)
public class ProductResource {

  @EJB
  private ProductDao productDao;

  @POST
  public Product createProduct(final Product product) {
    ProductEntity p = new ProductEntity();
    p.setPrice(product.getUnitPrice());
    p.setProductName(product.getProductName());
    productDao.create(p);
    return EntityToDomainMapper.entityToProduct(p);
  }

  @PUT
  public Product updateProduct(final Product product) {
    ProductEntity prd = productDao.findById(product.getProductId());
    prd.setPrice(product.getUnitPrice());
    prd.setProductName(product.getProductName());
    return EntityToDomainMapper.entityToProduct(prd);
  }

  @GET
  public List<Product> getAllProducts() {
    List<Product> cartsList = new ArrayList<>();
    for (ProductEntity p : productDao.listAll()) {
      Product c = EntityToDomainMapper.entityToProduct(p);
      cartsList.add(c);
    }
    return cartsList;
  }

  @GET
  @Path("{id}")
  public Product getProduct(@PathParam("id") final long id) {
    ProductEntity prd = productDao.findById(id);
    return EntityToDomainMapper.entityToProduct(prd);
  }

  @DELETE
  @Path("{id}")
  public Response deleteProduct(@PathParam("id") final long productId) {
    productDao.deleteById(productId);
    return Response.ok().build();
  }

}
