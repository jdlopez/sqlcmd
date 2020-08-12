package es.jdlopez.sqlcmd;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Jdbc Dynamic Driver loads .jar file driver from path instead of classpath
 * @see <a href="https://stackoverflow.com/questions/14478870/dynamically-load-the-jdbc-driver#14479658">dynamically-load-the-jdbc-driver on stackoverflow</a>
 */
public class DynamicDriver implements Driver {
    private Driver driver;

    public DynamicDriver(Driver d) {
        this.driver = d;
    }

    /**
     * Gets connection using DriverManager.registerDriver
     */
    public static Connection getConnection(String driverPath, String driverClass, String jdbcUrl, String jdbcUser, String jdbcPass) throws SQLException {
        Connection conn = null;
        Driver d = null;
        try {
            URL u = new URL("jar:file:" + driverPath + "!/");
            URLClassLoader ucl = new URLClassLoader(new URL[]{u}, DynamicDriver.class.getClassLoader());
            d = (Driver) Class.forName(driverClass, true, ucl).getConstructor().newInstance();
            DriverManager.registerDriver(new DynamicDriver(d));
            conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | MalformedURLException | NoSuchMethodException | InvocationTargetException e) {
            throw new SQLException(e.getMessage(), e);
        }
        return conn;

    }

    public boolean acceptsURL(String u) throws SQLException {
        return this.driver.acceptsURL(u);
    }

    public Connection connect(String u, Properties p) throws SQLException {
        return this.driver.connect(u, p);
    }

    public int getMajorVersion() {
        return this.driver.getMajorVersion();
    }

    public int getMinorVersion() {
        return this.driver.getMinorVersion();
    }

    public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
        return this.driver.getPropertyInfo(u, p);
    }

    public boolean jdbcCompliant() {
        return this.driver.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}