package es.jdlopez.sqlcmd;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

// TODO: put all html code in configurable templates
public class HtmlFormatter extends ResultFormatter {
    private RunnerConfig conf;

    public HtmlFormatter(RunnerConfig c) {
        this.conf = c;
    }

    @Override
    public void writeHeader(PrintWriter out, ResultSetMetaData rsmd) throws SQLException {
        if (conf.getPrintHeader()) { // this check must be on MainRunner :-(
            int colCount = rsmd.getColumnCount();
            // add thead tag???
            out.println("<tr>");
            for (int i = 1; i <= colCount; i++) {
                out.print(String.format("<th>%s</th>", rsmd.getColumnName(i)));
            }
            out.println("</tr>");
        } // header
    }

    @Override
    public void writeRow(PrintWriter out, ResultSet resultSet, int colCount) throws SQLException {
        out.println("<tr>");
        for (int i = 1; i <= colCount; i++) {
            out.print(String.format("<td>%s</td>", resultSet.getString(i)));
        } // for col
        out.println("</tr>");
    }

    @Override
    public void writeBeginResultSet(PrintWriter out) {
        out.println("<table>");
    }

    @Override
    public void writeEndResultSet(PrintWriter out) {
        out.println("</table>");
    }

    @Override
    public void writeUpdate(PrintWriter out, int updateCount) {
        out.println(String.format("<hr/>UpdateCount: %d<hr/>", updateCount));
    }

    @Override
    public String getMime() {
        return "text/html";
    }
}
