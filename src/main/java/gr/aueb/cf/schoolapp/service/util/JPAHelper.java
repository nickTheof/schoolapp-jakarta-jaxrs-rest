package gr.aueb.cf.schoolapp.service.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class JPAHelper {
    private static EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

    private static final HikariConfig config = new HikariConfig();
    private static final Map<String, Object> properties = new HashMap<>();


    static {
        config.setJdbcUrl(System.getenv("SCHOOL7_DB_HOST") + ":" + System.getenv("SCHOOL7_DB_PORT") +
                "/" + System.getenv("SCHOOL7_DB_STAGE_DATABASE") + "?serverTimezone=UTC");
        config.setUsername(System.getenv("SCHOOL7_DB_USERNAME"));
        config.setPassword(System.getenv("SCHOOL7_DB_PASSWORD"));

        DataSource dataSource =new HikariDataSource(config);
        properties.put("hibernate.connection.datasource", dataSource);

    }

    private JPAHelper() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) emf = Persistence.createEntityManagerFactory("school7DBContext", properties);
        return emf;
    }

    public static void closeEntityManagerFactory(){
        if (emf != null) {
            emf.close();
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
            em = getEntityManagerFactory().createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
         getEntityManager().close();
         threadLocal.remove();
    }

    public static void startTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void commitTransaction() {
        getEntityManager().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        getEntityManager().getTransaction().rollback();
    }


}
