package org.example;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

public class HibernateConfig {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");

            String dbUrl = System.getenv("DB_URL");
            if (dbUrl == null) {
                dbUrl = "jdbc:postgresql://ep-spring-base-a5bluwyw-pooler.us-east-2.aws.neon.tech/zad5?sslmode=require";
            }
            configuration.setProperty("hibernate.connection.url", dbUrl);
            configuration.setProperty("hibernate.connection.username", "zad5_owner");
            configuration.setProperty("hibernate.connection.password", "npg_KefEH7kiXaO3");

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.registerTypeOverride(new JsonBinaryType(), new String[]{"jsonb"});
            configuration.addAnnotatedClass(Vehicle.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Rental.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Initialize Hibernate ERROR: " + ex);
        }
    }
}
