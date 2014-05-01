package com.nrk.cart.rest.resource;

import java.util.Date;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nrk.cart.interceptors.AuditLogInterceptor;

@Stateless
@Path("ping")
@Interceptors(AuditLogInterceptor.class)
public class PingResource {

  private static final Logger log = LoggerFactory.getLogger(PingResource.class);

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public String ping() {
    log.info("ping is called");
    return DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()) + ": System is alive.";
  }

}
