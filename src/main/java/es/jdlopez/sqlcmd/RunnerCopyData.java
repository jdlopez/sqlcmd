package es.jdlopez.sqlcmd;

import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Mail class for copy data from query surce to destination table
 */
public class RunnerCopyData {

    public static void main(String[] args) throws Exception {
        Properties p = new Properties();
        if (args.length > 0) {
            p.load(new FileInputStream(args[0]));
        } else {
            p.load(System.in);
        }
        CopyConfig config = BeanReader.fromProperties(p, CopyConfig.class);
        Connection sourceConn = null;
        Connection destConn = null;
        int resultCount = 0;
        try {
            sourceConn = DynamicDriver.getConnection(config.getSourceDriverPath(),
                    config.getSourceDriverClass(),
                    config.getSourceJdbcUrl(),
                    config.getSourceJdbcUser(),
                    config.getSourceJdbcPass()
            );
            destConn = DynamicDriver.getConnection(
                    config.getDestDriverPath(),
                    config.getDestDriverClass(),
                    config.getDestJdbcUrl(),
                    config.getDestJdbcUser(),
                    config.getDestJdbcPass()
            );
            if (config.getDestPreviousSql() != null) {
                int ret = destConn.createStatement().executeUpdate(config.getDestPreviousSql());
                System.out.println("Executed previous sql: " + ret);
            }
            ResultSet rsSource = sourceConn.createStatement().executeQuery(config.getSourceSql());
            PreparedStatement psInsert = destConn.prepareStatement(config.getDestInsertSql());
            int columns = -1;
            while (rsSource.next()) {
                if (columns == -1)
                    columns = rsSource.getMetaData().getColumnCount();
                updateStatement(rsSource, psInsert, columns);
                psInsert.executeUpdate();
                resultCount++;
            }
            if (config.getDestPostSql() != null) {
                int ret = destConn.createStatement().executeUpdate(config.getDestPostSql());
                System.out.println("Executed post sql: " + ret);
            }

        } finally {
            if (sourceConn != null)
                sourceConn.close();
            if (destConn != null)
                destConn.close();
        }
        System.out.println("Number of rows copied: " + resultCount);
    }

    private static void updateStatement(ResultSet rsSource, PreparedStatement psInsert, int columns) throws SQLException {
        psInsert.clearParameters();
        for (int i = 1; i <= columns; i++) {
            psInsert.setObject(i, rsSource.getObject(i));
        }
    }

}