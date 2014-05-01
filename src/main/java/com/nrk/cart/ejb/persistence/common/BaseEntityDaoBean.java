package com.nrk.cart.ejb.persistence.common;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseEntityDaoBean<T extends BaseEntity> {
  private static final Logger LOG = LoggerFactory.getLogger(BaseEntityDaoBean.class);

  @PersistenceContext(unitName = "datasource")
  private EntityManager entityManager;

  protected abstract Class<T> getEntityClass();

  protected final EntityManager getEntityManager() {
    return entityManager;
  }

  public Session getSession() {
    return entityManager.unwrap(Session.class);
  }

  public T createOrUpdate(final T entity) {
    if (entity.getId() == null) {
      return create(entity);
    }
    return update(entity);
  }

  public T create(final T entity) {
    try {
      entity.validate();
      if (entity instanceof BaseTimeEntity) {
        ((BaseTimeEntity) entity).setCreatedTimestamp(new Date());
        ((BaseTimeEntity) entity).setLastModifiedTimestamp(new Date());
      }
      entityManager.persist(entity);
    } catch (final Exception e) {
      LOG.error("Error occured while creating the entity.", e);
      throw new RuntimeException(e);
    }
    return entity;
  }

  public T update(final T entity) {
    try {
      entity.validate();

      // TODO: Need to find if this find method is causing any performance
      // issue;
      // If yes then this shall be replaced with simple exists query.
      // if(em.find(entity.getClass(), entity.getId()) == null) {
      if (entity.getId() == null) {
        throw new IllegalArgumentException("No records found.");
      }
      if (entity instanceof BaseTimeEntity) {
        ((BaseTimeEntity) entity).setLastModifiedTimestamp(new Date());
      }
      entityManager.merge(entity);
    } catch (final Exception e) {
      LOG.error("Error occured while updating the entity.", e);
      throw new RuntimeException(e);
    }
    return entity;
  }

  public T findById(final Number id) {
    if (id == null) {
      return null;
    }
    try {
      return entityManager.find(getEntityClass(), id);
    } catch (final Exception e) {
      LOG.error("Error occured while finding the entity.", e);
      throw new RuntimeException(e);
    }
  }

  public List<T> listAll() {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
      Root<T> entity = criteria.from(getEntityClass());
      criteria.select(entity);
      TypedQuery<T> query = entityManager.createQuery(criteria);
      return query.getResultList();
    } catch (final Exception e) {
      LOG.error("Error occured while finding all entities.", e);
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public List<T> listAll(final String sql, final Object... param) {
    try {
      final javax.persistence.Query query = entityManager.createQuery(sql);
      int i = 1;
      for (final Object obj : param) {
        query.setParameter(i++, obj);
      }
      return query.getResultList();
    } catch (final Exception e) {
      LOG.error("Error occured while executing the query.", e);
      throw new RuntimeException(e);
    }
  }

  public void deleteById(final Long id) {
    if (id == null) {
      return;
    }
    try {
      final T entity = entityManager.find(getEntityClass(), id);
      if (entity == null) {
        throw new IllegalArgumentException("No record found.");
      }
      entityManager.remove(entity);
      // entityManager.remove(entityManager.merge(entity));
    } catch (final Exception e) {
      LOG.error("Error occured while deleting the entity.", e);
      throw new RuntimeException(e);
    }
  }

  protected void executeUpdate(final String sql) {
    try {
      entityManager.createQuery(sql).executeUpdate();
    } catch (final Exception e) {
      LOG.error("Error occured while execuring the query.", e);
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public T find(final String sql, final Object... param) {
    try {
      final javax.persistence.Query query = entityManager.createQuery(sql, getEntityClass());
      int i = 1;
      for (final Object obj : param) {
        query.setParameter(i++, obj);
      }
      return (T) query.getSingleResult();
    } catch (final Exception e) {
      LOG.error("Error occured while finding the entity.", e);
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public List<T> find(final String sql) {
    try {
      final javax.persistence.Query query = entityManager.createQuery(sql);
      return query.getResultList();
    } catch (final Exception e) {

      LOG.error("Error occured while finding the entity.", e);
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public List<T> find(final String sql, final int maxResults) {
    try {
      final javax.persistence.Query query = entityManager.createQuery(sql);
      query.setMaxResults(maxResults);
      return query.getResultList();
    } catch (final Exception e) {
      LOG.error("Error occured while finding the entity.", e);
      throw new RuntimeException(e);
    }
  }

  public boolean isExists(final Long id) {
    return findById(id) != null;
  }

  public List<T> findRange(final int[] range) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = builder.createQuery(getEntityClass());
    final Root<T> root = cq.from(getEntityClass());
    cq.select(root);
    final TypedQuery<T> q = getEntityManager().createQuery(cq);
    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  protected Criteria getHibernateCriteria() {
    return getSession().createCriteria(getEntityClass());
  }

  protected Criteria getCriteria(final String alias) {
    return getSession().createCriteria(getEntityClass(), alias);
  }

  @SuppressWarnings("unchecked")
  public List<T> getResult(final Criteria crit) {
    List<T> list = crit.list();
    LOG.info("Total number of items found: {}" , list.size());
    return list;
  }

  @SuppressWarnings("unchecked")
  public T getUniqueResult(final Criteria crit) {
    return (T) crit.uniqueResult();
  }

}
