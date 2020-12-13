package es.jdlopez.sqlcmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainRunner {

    public static void main (String[] args) throws Exception {
        RunnerConfig config = readArgs(args);
        InputStream in;
        if (config.getInputSQL() == null) {
            in = System.in;
        } else {
            File fIn = new File(config.getInputSQL());
            if (!fIn.exists())
                throw new ConfigException("InputSQL", config.getInputSQL() + " does not exists");
            in = new FileInputStream(fIn);
        }
        String sql = readAll(in);
        PrintWriter out = null;
        if (config.getOutputResult() == null)
            out = new PrintWriter(System.out);
        else
            out = new PrintWriter(config.getOutputResult());

        Connection conn = null;
        try {
            conn = DynamicDriver.getConnection(config.getJdbcDriverPath(), config.getJdbcDriverClass(),
                    config.getJdbcUrl(), config.getJdbcUser(), config.getJdbcPass());
            Statement st = conn.createStatement();
            boolean isResultSet = st.execute(sql);
            boolean hasMore = true;
            while (hasMore) {
                if (isResultSet)
                    writeResultSet(st.getResultSet(), out, config);
                else
                    out.println("UpdateCount = " + st.getUpdateCount());
                hasMore = st.getMoreResults();
            }

        } finally {
            if (conn != null)
                conn.close();
        }
    }

    private static void writeResultSet(ResultSet resultSet, PrintWriter out, RunnerConfig conf) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        if (conf.getPrintHeader()) {
            for (int i = 1; i <= colCount; i++) {
                out.print(rsmd.getColumnName(i));
                if (i < colCount)
                    out.print(conf.getPrintFieldSeparator());
            }
            out.println();
        } // header
        while (resultSet.next()) {
            for (int i = 1; i <= colCount; i++) {
                out.print(resultSet.getString(i));
                if (i < colCount)
                    out.print(conf.getPrintFieldSeparator());
            } // for col
            out.println();
        } // while row
        out.flush();
    }

    private static String readAll(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line);
        br.close();
        return sb.toString();
    }

    private static RunnerConfig readArgs(String[] args) throws ConfigException {
        RunnerConfig conf = null;
        if (args != null) {
            Properties p = new Properties();
            for (String a : args) {
                if (a.startsWith("-c:")) { // config
                    try {
                        p.load(new FileReader(a.substring("-c:".length())));
                    } catch (IOException e) {
                        throw new ConfigException("configFile", "Reading config file", e);
                    }
                } else if (a != null && a.startsWith("-p:")) {
                    String s = a.substring("-p:".length());
                    int idxEq = s != null? s.indexOf("="):-1;
                    if (idxEq < 0)
                        throw new ConfigException(s, "Param argument must be: -p:name=value");
                    else
                        p.setProperty(s.substring(0, idxEq), s.substring(idxEq + 1));
                }
            }
            conf = BeanReader.fromProperties(p, RunnerConfig.class);
        } else
            throw new ConfigException(null, "No arguments");
        // default values (cant use constructor, explicit or implicit)
        if (conf.getPrintFieldSeparator() == null)
            conf.setPrintFieldSeparator("\t");
        if (conf.getPrintHeader() == null)
            conf.setPrintHeader(true);
        return conf;
    }
}
