package main.java.com.company.server.integration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class fileDAO {
//    private static final String TABLE_NAME = "CATALOG";
//    private static final String FILE_NAME_COLUMN_NAME = "FILE-NAME";
//    private static final String SIZE_COLUMN_NAME = "SIZE";
//    private static final String OWNER_COLUMN_NAME = "OWNER";
//
//    private PreparedStatement uploadFileStmt;
//    private PreparedStatement downloadFileStmt;
//    private PreparedStatement showAllFilesStmt;
    private final EntityManagerFactory EMfactory;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public fileDAO() {
        EMfactory = Persistence.createEntityManagerFactory("files");
    }
}
