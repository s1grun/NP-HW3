package main.java.com.company.server.integration;

import javax.persistence.EntityManagerFactory;

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

    public fileDAO(EntityManagerFactory eMfactory) {
        EMfactory = eMfactory;
    }
}
