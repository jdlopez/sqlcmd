package es.jdlopez.sqlcmd;

import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class TestMain {

    @Test
    public void testCreateTable() throws Exception {
        // create table must be in previous statement: hsqldb docs
        Properties p = new Properties();
        p.load(new FileReader("src/test/resources/sample.properties"));
        Connection conn = DriverManager.getConnection(p.getProperty("jdbcUrl"),
                p.getProperty("jdbcUser"), p.getProperty("jdbcPass"));
        conn.createStatement().executeUpdate("create table testtable1 ( field1 int, field2 varchar(10))");
        String[] args = new String[]{"-c:src/test/resources/sample.properties", "-p:inputSQL=src/test/resources/test1.sql"};
        MainRunner.main(args);
        conn.createStatement().executeUpdate("drop table testtable1");
    }
}
