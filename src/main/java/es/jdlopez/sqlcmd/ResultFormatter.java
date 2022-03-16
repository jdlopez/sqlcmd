package es.jdlopez.sqlcmd;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class ResultFormatter {

    public static final String TEXT = "text";
    public static final String HTML = "html";
    public static final String CSV  = "csv";

    public abstract void writeHeader(PrintWriter out, ResultSetMetaData rsmd) throws SQLException;

    public abstract void writeRow(PrintWriter out, ResultSet resultSet, int colCount) throws SQLException;

    public abstract void writeEndResultSet(PrintWriter out);

    public abstract void writeUpdate(PrintWriter out, int updateCount);

    public abstract void writeBeginResultSet(PrintWriter out);

    public abstract String getMime();

    public static String repeatChar(char c, int length) {
        return new String(new char[length]).replace('\0', c);
    }

}