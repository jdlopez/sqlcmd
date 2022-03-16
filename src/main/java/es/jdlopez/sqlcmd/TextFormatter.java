package es.jdlopez.sqlcmd;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TextFormatter extends ResultFormatter {
    private RunnerConfig conf;

    public TextFormatter(RunnerConfig c) {
        this.conf = c;
    }

    @Override
    public void writeHeader(PrintWriter out, ResultSetMetaData rsmd) throws SQLException {
        int colCount = rsmd.getColumnCount();
        if (conf.getPrintHeader()) {
            StringBuffer underline = new StringBuffer();
            for (int i = 1; i <= colCount; i++) {
                out.print(rsmd.getColumnName(i));
                underline.append(repeatChar('=', rsmd.getColumnName(i).length()));
                if (i < colCount) {
                    out.print(conf.getPrintFieldSeparator());
                    underline.append(conf.getPrintFieldSeparator());
                }
            }
            out.println();
            out.println(underline.toString());
        } // header

    }

    @Override
    public void writeRow(PrintWriter out, ResultSet resultSet, int colCount) throws SQLException {
        for (int i = 1; i <= colCount; i++) {
            out.print(resultSet.getString(i));
            if (i < colCount)
                out.print(conf.getPrintFieldSeparator());
        } // for col
        out.println();
    }

    @Override
    public void writeEndResultSet(PrintWriter out) {
        // nothing needed
    }

    @Override
    public void writeBeginResultSet(PrintWriter out) {
        // nothing needed
    }

    @Override
    public void writeUpdate(PrintWriter out, int updateCount) {
        String s;
        if (conf.getPrintHeader())
            s = String.format("==================\nUpdateCount = %d\n==================", updateCount);
        else
            s = "UpdateCount = " + updateCount;
        out.println(s);
    }

    @Override
    public String getMime() {
        return "text/plain";
    }
}
