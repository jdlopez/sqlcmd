package es.jdlopez.sqlcmd;

public class RunnerConfig {
    private String jdbcDriverPath;
    private String jdbcDriverClass;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPass;
    private String inputSQL;
    private String outputResult;
    private Boolean printHeader = true;
    private String printFieldSeparator = "\t";

    public String getInputSQL() {
        return inputSQL;
    }

    public void setInputSQL(String inputSQL) {
        this.inputSQL = inputSQL;
    }

    public String getJdbcDriverPath() {
        return jdbcDriverPath;
    }

    public void setJdbcDriverPath(String jdbcDriverPath) {
        this.jdbcDriverPath = jdbcDriverPath;
    }

    public String getJdbcDriverClass() {
        return jdbcDriverClass;
    }

    public void setJdbcDriverClass(String jdbcDriverClass) {
        this.jdbcDriverClass = jdbcDriverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public String getJdbcPass() {
        return jdbcPass;
    }

    public void setJdbcPass(String jdbcPass) {
        this.jdbcPass = jdbcPass;
    }

    public String getOutputResult() {
        return outputResult;
    }

    public void setOutputResult(String outputResult) {
        this.outputResult = outputResult;
    }

    public Boolean getPrintHeader() {
        return printHeader;
    }

    public void setPrintHeader(Boolean printHeader) {
        this.printHeader = printHeader;
    }

    public String getPrintFieldSeparator() {
        return printFieldSeparator;
    }

    public void setPrintFieldSeparator(String printFieldSeparator) {
        this.printFieldSeparator = printFieldSeparator;
    }
}
