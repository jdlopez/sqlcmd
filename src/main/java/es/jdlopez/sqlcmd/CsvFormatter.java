package es.jdlopez.sqlcmd;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CsvFormatter extends ResultFormatter {
    private RunnerConfig conf;

    public CsvFormatter(RunnerConfig c) {
        this.conf = c;
    }

    @Override
    public void writeHeader(PrintWriter out, ResultSetMetaData rsmd) throws SQLException {
        int colCount = rsmd.getColumnCount();
        if (conf.getPrintHeader()) {
            for (int i = 1; i <= colCount; i++) {
                out.print(rsmd.getColumnName(i));
                if (i < colCount) {
                    out.print(conf.getPrintFieldSeparator());
                }
            }
            out.println();
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
        out.println(updateCount);
    }

    @Override
    public String getMime() {
        return "text/csv";
    }
}
