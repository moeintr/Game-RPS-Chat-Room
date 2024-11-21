package com.moein.game.repository;

import java.util.List;
import java.util.Map;

public interface CrudRepository<T, I> {
    void save(T t);

    T saveAndFlush(T t);

    void update(T t);

    void remove(T t);

    T findOne(Class<T> tClass, I i);

    List<T> findAll(Class<T> tClass);

    List<T> findAll(Class<T> tClass, String whereClause, Map<String, Object> params);

    List<T> findAllPaging(Class<T> tClass, String whereClause, Map<String, Object> params, String entityId, int firstResultPage, int maxSizePage);

    List<T> findAllWithChild(Class<T> tClass, String childName);

    List<T> findAllWithChildren(Class<T> tClass, String childOneName, String childTwoName);

    List<T> findAllWithChild(Class<T> tClass, String childName, String whereClause, Map<String, Object> params);

    List<T> findAllWithChildrenPaging(Class<T> tClass, String childOneName, String childTwoName, String whereClause, Map<String, Object> params, String entityId, int firstResultPage, int maxSizePage);
}
