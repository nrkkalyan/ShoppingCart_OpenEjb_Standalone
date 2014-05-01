/*
 * ----------------------------------------------------------------------------- Created on 23 aug
 * 2011 by bostmyren Copyright NetGiro Systems AB
 * ---------------------------------------------------------------------------
 */
package com.nrk.cart;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import javax.ejb.embeddable.EJBContainer;

import org.apache.openejb.OpenEjbContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Retrieves a configuration from the config service and configures logging. Messages are written to
 * stdout to avoid using a logger before it is properly configured.
 */

public final class ShoppingCartMain implements Runnable {
  private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartMain.class);

  public static final String applicationName = "ShoppingCart";


  public static void main(final String[] args) throws Exception {
    System.out.println("Application starting...");
    System.out.println("Using file.encoding=" + System.getProperty("file.encoding"));
    try {
      final Thread thread = new Thread(new ShoppingCartMain());
      thread.start();
    } catch (final Exception e) {
      LOG.error("Application terminating with exception: ", e);
      throw e;
    }
  }

  @Override
  public void run() {
    EJBContainer ejbContainer = null;
    try {
      LOG.info("Starting " + applicationName);
      final Properties properties = new Properties();
      properties.setProperty(EJBContainer.APP_NAME, applicationName);
      properties.setProperty(EJBContainer.PROVIDER, OpenEjbContainer.class.getName());
      properties.setProperty("httpejbd.port", "8080");
      properties.setProperty("httpejbd.bind", "0.0.0.0");
      properties.setProperty("ejbd.disabled", "true");
      properties.setProperty("ejbds.disabled", "true");
      properties.setProperty("admin.disabled", "true");
      properties.setProperty(OpenEjbContainer.OPENEJB_EMBEDDED_REMOTABLE, "true");

      // To format JSON and CROS stuff
      properties.setProperty("cxf.jaxrs.providers",
          "jaxbProvider,org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter");

      
      properties.load(getClass().getClassLoader().getResourceAsStream("persistence.properties"));
      properties.put("jtaDatasource", "new://Resource?type=DataSource");
      properties.put("jtaDatasource.JdbcDriver", "org.hsqldb.jdbcDriver");
      properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
      properties.put("jtaDatasource.JtaManaged", "true");
      properties.put("jtaDatasource.JdbcUrl", "jdbc:hsqldb:mem:dbName;sql.enforce_strict_size=true");
      properties.put("hibernate.hbm2ddl.auto", "create-drop");

      
      
      // This is the line starting the EJB container
      ejbContainer = EJBContainer.createEJBContainer(properties);
      ejbContainer.getContext().bind("inject", this);

      LOG.info("{} application started successfully.", applicationName);

      final CountDownLatch latch = new CountDownLatch(1);
      // Graceful shutdown
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          try {
            LOG.info("Shutting down..");
            latch.countDown();
            LOG.info("Shutdown completed successfully.");
          } catch (final Exception e) {
            LOG.error("Graceful shutdown went wrong. SIGKILL (kill -9) if you want.", e);
          }
        }
      });
      try {
        latch.await();
      } catch (final InterruptedException e) {
        // ignored
      }
    } catch (final Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (ejbContainer != null) {
        ejbContainer.close();
      }
    }

  }

}
