package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.IdentifiableEntity;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public abstract class AbstractDAO<T extends IdentifiableEntity> implements IGenericDAO<T>{

    private Class<T> persistenceClass;

    public AbstractDAO() {

    }

    @Override
    public Optional<T> insert(T t) {
        EntityManager em = getEntityManager();
        em.persist(t);
        return Optional.of(t);
    }

    @Override
    public Optional<T> update(T t) {
        EntityManager em = getEntityManager();
        em.merge(t);
        return Optional.of(t);
    }

    @Override
    public void delete(Object id) {
        EntityManager em = getEntityManager();
        getById(id).ifPresent(em::remove);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(t) FROM " + persistenceClass.getSimpleName() + " t";
        TypedQuery<Long> query = getEntityManager().createQuery(sql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public long countByCriteria(Map<String, Object> criteria) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> entityRoot = countQuery.from(persistenceClass);
        List<Predicate> predicates = getPredicatesList(cb, entityRoot, criteria);
        countQuery.select(cb.count(entityRoot)).where(predicates.toArray(new Predicate[0]));
        TypedQuery<Long> query = em.createQuery(countQuery);
        addParametersToQuery(query, criteria);
        return query.getSingleResult();
    }

    @Override
    public Optional<T> getById(Object id) {
       EntityManager em = JPAHelper.getEntityManager();
        return Optional.ofNullable(em.find(persistenceClass, id));
    }

    @Override
    public Optional<T> getByField(String fieldName, Object value) {
        String sql = "SELECT t FROM " + persistenceClass.getSimpleName() + " t WHERE t." + fieldName + " = :val";
        TypedQuery<T> query = JPAHelper.getEntityManager().createQuery(sql, persistenceClass);
        query.setParameter("val", value);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<T> getAll() {
        return getByCriteria(getPersistenceClass(), Collections.emptyMap());
    }

    @Override
    public List<T> getByCriteria(Map<String, Object> criteria) {
        return getByCriteria(getPersistenceClass(), criteria);
    }

    @Override
    public List<T> getByCriteria(Class<T> clazz, Map<String, Object> criteria) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> selectQuery = builder.createQuery(clazz);
        Root<T> entityRoot = selectQuery.from(clazz);

        List<Predicate> predicates = getPredicatesList(builder, entityRoot, criteria);
        selectQuery.select(entityRoot).where(predicates.toArray(new Predicate[0]));
        TypedQuery<T> query = em.createQuery(selectQuery);
        addParametersToQuery(query, criteria);

        List<T> entitiesToReturn = query.getResultList();
        if (entitiesToReturn != null) System.out.println("IN getByCriteriaDAO" + Arrays.toString(entitiesToReturn.toArray()));
        else System.out.println("IS NULL");
        return  entitiesToReturn;
    }

    @Override
    public List<T> getByCriteriaPaginated(Class<T> clazz, Map<String, Object> criteria, Integer page, Integer size) {
        TypedQuery<T> query = getTypedQueryByCriteria(clazz, criteria);
        if (page != null && size != null) {
            query.setFirstResult(page * size);
            query.setMaxResults(size);
        }
        return query.getResultList();
    }


    public EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

    protected List<Predicate> getPredicatesList(CriteriaBuilder builder, Root<T> entityRoot, Map<String , Object> criteria) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            ParameterExpression<?> val = builder.parameter(value.getClass(), buildParameterAlias(key));
            Predicate predicateLike = builder.like((Expression<String>) resolvePath(entityRoot, key), (Expression<String>) val);
            predicates.add(predicateLike);
        }
        return predicates;
    }

    protected Path<?> resolvePath(Root<T> root, String expression) {
        String[] fields = expression.split("\\.");
        Path<?> path = root.get(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }
        return path;
    }

    protected void addParametersToQuery(TypedQuery<?> query, Map<String , Object> criteria) {
        for (Map.Entry<String , Object> entry : criteria.entrySet()) {
            Object value = entry.getValue();
            query.setParameter(buildParameterAlias(entry.getKey()), value + "%");
        }
    }

    protected String buildParameterAlias(String alias) {
        return alias.replaceAll("\\.", "");
    }

    private TypedQuery<T> getTypedQueryByCriteria(Class<T> clazz, Map<String, Object> criteria) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> selectQuery = builder.createQuery(clazz);
        Root<T> entityRoot = selectQuery.from(clazz);

        List<Predicate> predicates = getPredicatesList(builder, entityRoot, criteria);
        selectQuery.select(entityRoot).where(predicates.toArray(new Predicate[0]));
        TypedQuery<T> query = em.createQuery(selectQuery);
        addParametersToQuery(query, criteria);
        return query;
    }
}
