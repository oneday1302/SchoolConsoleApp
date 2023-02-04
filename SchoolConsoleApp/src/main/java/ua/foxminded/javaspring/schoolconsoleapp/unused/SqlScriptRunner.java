package ua.foxminded.javaspring.schoolconsoleapp.unused;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.jdbc.ScriptRunner;

public class SqlScriptRunner {
    private final String sqlFileName;
    private final DataSource dataSource;

    public SqlScriptRunner(String sqlFileName, DataSource dataSource) {
        if (sqlFileName == null || dataSource == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.sqlFileName = sqlFileName;
        this.dataSource = dataSource;
    }

    public void run() {
        try (Connection con = dataSource.getConnection()) {
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