package com.moein.game.repository;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Stateless
public class JpaCrudRepository<T, I> implements CrudRepository<T, I> {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(T t) {
        entityManager.persist(t);
    }

    public T saveAndFlush(T t) {
        entityManager.persist(t);
        entityManager.flush();
        return t;
    }

    public void update(T t) {
        entityManager.merge(t);
    }

    public void remove(T t) {
        entityManager.remove(entityManager.merge(t));
    }

    public T findOne(Class<T> tClass, I i) {
        return entityManager.find(tClass, i);
    }

    public List<T> findAll(Class<T> tClass) {
        Entity entity = tClass.getAnnotation(Entity.class);
        String entityName = entity.name();
        Query query = entityManager.createQuery("select entity from " + entityName + " entity");
        return query.getResultList();
    }

    public List<T> findAll(Class<T> tClass, String whereClause, Map<String, Object> params) {
        Entity entity = tClass.getAnnotation(Entity.class);
        String entityName = entity.name();
        Query query = entityManager.createQuery("select entity from " + entityName + " entity where " + whereClause);
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query.getResultList();
    }

    public List<T> findAllPaging(Class<T> tClass, String whereClause, Map<String, Object> params, String entityId, int firstResultPage, int maxSizePage) {
        Entity entity = tClass.getAnnotation(Entity.class);
        String entityName = entity.name();
        //String q = "select entity from " + entityName + " entity where " + whereClause + " order by entity." + entityId + " desc";
        Query query = entityManager.createQuery("select entity from " + entityName + " entity where " + whereClause + " order by entity." + entityId + " desc");
        query.setFirstResult(firstResultPage);
        query.setMaxResults(maxSizePage);
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query.getResultList();
    }

    public List<T> findAllWithChild(Class<T> tClass, String childName) {
        Entity entity = tClass.getAnnotation(Entity.class);
        String entityName = entity.name();
        Query query = entityManager.createQuery("select distinct (entity) from " + entityName + " entity left join fetch entity." + childName + " child");
        return query.getResultList();
    }

    public List<T> findAllWithChild(Class<T> tClass, String childName, String whereClause, Map<String, Object> params) {
        Entity entity = tClass.getAnnotation(Entity.class);
        String entityName = entity.name();
        Query query = entityManager.createQuery("select distinct (entity) from " + entityName + " entity left join fetch entity." + childName + " child where " + whereClause);
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query.getResultList();
    }

    public List<T> findAllWithChildrenPaging(Class<T> tClass, String childOneName, String childTwoName, String whereClause, Map<String, Object> params, String entityId, int firstResultPage, int maxSizePage) {
        Entity entity = tClass.getAnnotation(Entity.class);
        String entityName = entity.name();
        //String q = "select entity from " + entityName + " entity where " + whereClause + " order by entity." + entityId + " desc";
        Query query = entityManager.createQuery("select distinct (entity) from " + entityName + " entity left join fetch entity." + childOneName + " childOne left join fetch entity." + childTwoName + " childTwo where " + whereClause + " order by entity." + entityId + " desc");
        query.setFirstResult(firstResultPage);
        query.setMaxResults(maxSizePage);
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query.getResultList();
    }
}
