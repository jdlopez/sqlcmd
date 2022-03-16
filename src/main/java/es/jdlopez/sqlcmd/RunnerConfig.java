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
    // mail configuration:
    private String mailSendTo;
    private String mailHost;
    private String mailPort;
    private Boolean mailAuth;
    private Boolean mailTLS;
    private String mailUser;
    private String mailPass;
    private String mailFrom;
    private String mailSubject;
    private String formatterName;

    private ResultFormatter formatter = null;
    public ResultFormatter buildFormatter() {
        if (formatter == null) {
            if (ResultFormatter.HTML.equalsIgnoreCase(formatterName))
                formatter = new HtmlFormatter(this);
            else if (ResultFormatter.CSV.equalsIgnoreCase(formatterName))
                formatter = new CsvFormatter(this);
            //if (ResultFormatter.TEXT.equalsIgnoreCase(formatterName))
            else
                formatter = new TextFormatter(this);
        }
        return formatter;
    }

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

    public String getMailSendTo() {
        return mailSendTo;
    }

    public void setMailSendTo(String mailSendTo) {
        this.mailSendTo = mailSendTo;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailPort() {
        return mailPort;
    }

    public void setMailPort(String mailPort) {
        this.mailPort = mailPort;
    }

    public Boolean getMailAuth() {
        return mailAuth;
    }

    public void setMailAuth(Boolean mailAuth) {
        this.mailAuth = mailAuth;
    }

    public Boolean getMailTLS() {
        return mailTLS;
    }

    public void setMailTLS(Boolean mailTLS) {
        this.mailTLS = mailTLS;
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getMailPass() {
        return mailPass;
    }

    public void setMailPass(String mailPass) {
        this.mailPass = mailPass;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getFormatterName() {
        return formatterName;
    }

    public void setFormatterName(String formatterName) {
        this.formatterName = formatterName;
    }
}
