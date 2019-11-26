package com.company.server.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class fileDAO {
    private static final String TABLE_NAME = "CATALOG";
    private static final String FILE_NAME_COLUMN_NAME = "FILE-NAME";
    private static final String SIZE_COLUMN_NAME = "SIZE";
    private static final String OWNER_COLUMN_NAME = "OWNER";

    private PreparedStatement uploadFileStmt;
    private PreparedStatement downloadFileStmt;
    private PreparedStatement showAllFilesStmt;

    public fileDAO (String mysql, String localhost){
        Connection connection =
    }

}
