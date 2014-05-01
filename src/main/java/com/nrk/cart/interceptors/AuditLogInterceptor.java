package com.nrk.cart.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditLogInterceptor {
  private static final Logger LOG = LoggerFactory.getLogger(AuditLogInterceptor.class);

  /*
   * TODO: Need to see if we need to log any specific parameters. Never log passwords or any
   * sensitive information like card numbers
   */
  @AroundInvoke
  public Object aroundInvoke(final InvocationContext context) throws Exception {
    long startTime = System.currentTimeMillis();
    try {
      LOG.info("Inside :{}.{}", context.getMethod().getDeclaringClass(), context.getMethod()
          .getName());
      return context.proceed();
    } finally {
      LOG.info("\n{}.{} took {} ms", context.getMethod().getDeclaringClass(), context.getMethod()
          .getName(), System.currentTimeMillis() - startTime);
    }
  }
}
