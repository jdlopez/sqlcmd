package es.jdlopez.sqlcmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
        StringWriter mailBody = null;
        if (config.getMailSendTo() != null) {
            mailBody = new StringWriter();
            out = new PrintWriter(mailBody);
        }
        else if (config.getOutputResult() == null)
            out = new PrintWriter(System.out, true);
        else
            out = new PrintWriter(config.getOutputResult());

        Connection conn = null;
        try {
            if (config.getJdbcDriverPath() == null) {
                // driver in classpath and registered
                conn = DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUser(), config.getJdbcPass());
            } else
                conn = DynamicDriver.getConnection(config.getJdbcDriverPath(), config.getJdbcDriverClass(),
                    config.getJdbcUrl(), config.getJdbcUser(), config.getJdbcPass());
            Statement st = conn.createStatement();
            boolean isResultSet = st.execute(sql);
            boolean hasMore = true;
            int updateCount = st.getUpdateCount(); // just once per result
            while (hasMore) {
                if (isResultSet && updateCount < 0)
                    writeResultSet(st.getResultSet(), out, config);
                else
                    writeUpdateCount(updateCount, out, config);
                isResultSet = st.getMoreResults();
                updateCount = st.getUpdateCount();
                hasMore = isResultSet || updateCount > -1;
            }
            // send result to mail?
            if (config.getMailSendTo() != null && mailBody != null) {
                SendMail sendMail;
                if (config.getMailAuth() != null) {
                    sendMail = new SendMail(config.getMailHost(),
                            config.getMailPort(),
                            config.getMailFrom(),
                            Boolean.valueOf(config.getMailAuth()),
                            Boolean.valueOf(config.getMailTLS()),
                            config.getMailUser(),
                            config.getMailPass()
                    );
                } else {
                    sendMail = new SendMail(config.getMailHost(),
                            config.getMailPort(),
                            config.getMailFrom());
                }
                if (ResultFormatter.HTML.equalsIgnoreCase(config.getFormatterName()))
                    sendMail.sendMime(config.buildFormatter().getMime(),
                            config.getMailSubject(), mailBody.toString(), config.getMailSendTo());
                else // careful 'no object DCH for MIME type csv or others needs activation
                    sendMail.sendText(config.getMailSubject(), mailBody.toString(), config.getMailSendTo());
            }

        } finally {
            if (conn != null)
                conn.close();
        }
    }

    private static void writeUpdateCount(int updateCount, PrintWriter out, RunnerConfig config) {
        ResultFormatter fmt = config.buildFormatter();
        fmt.writeUpdate(out, updateCount);
    }

    private static void writeResultSet(ResultSet resultSet, PrintWriter out, RunnerConfig conf) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        ResultFormatter fmt = conf.buildFormatter();
        fmt.writeBeginResultSet(out);
        fmt.writeHeader(out, rsmd);
        while (resultSet.next()) {
            fmt.writeRow(out, resultSet, colCount);
        } // while row
        out.flush();
        fmt.writeEndResultSet(out);
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
        if (conf.getFormatterName() == null)
            conf.setFormatterName("text");
        return conf;
    }
}
