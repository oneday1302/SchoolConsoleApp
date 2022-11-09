package ua.foxminded.javaspring.schoolconsoleapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class SqlScriptRunner {
    private final String sqlFileName;
    
    public SqlScriptRunner(String sqlFileName) {
        if (sqlFileName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.sqlFileName = sqlFileName;
    }

    public void run() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1996")) {
            ScriptRunner runner = new ScriptRunner(con);
            runner.setStopOnError(true);
            runner.setLogWriter(null);
            Reader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(sqlFileName)));
            runner.runScript(reader);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}