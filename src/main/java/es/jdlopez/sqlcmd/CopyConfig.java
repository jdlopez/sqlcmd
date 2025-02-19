package es.jdlopez.sqlcmd;

/**
 * CopyData configuration
 */
public class CopyConfig {
    private String sourceDriverPath;
    private String sourceDriverClass;
    private String sourceJdbcUrl;
    private String sourceJdbcUser;
    private String sourceJdbcPass;
    private String destDriverPath;
    private String destDriverClass;
    private String destJdbcUrl;
    private String destJdbcUser;
    private String destJdbcPass;
    private String sourceSql;
    private String destInsertSql;
    private String destPreviousSql;
    private String destPostSql;

    public String getSourceDriverPath() {
        return sourceDriverPath;
    }

    public void setSourceDriverPath(String sourceDriverPath) {
        this.sourceDriverPath = sourceDriverPath;
    }

    public String getSourceDriverClass() {
        return sourceDriverClass;
    }

    public void setSourceDriverClass(String sourceDriverClass) {
        this.sourceDriverClass = sourceDriverClass;
    }

    public String getSourceJdbcUrl() {
        return sourceJdbcUrl;
    }

    public void setSourceJdbcUrl(String sourceJdbcUrl) {
        this.sourceJdbcUrl = sourceJdbcUrl;
    }

    public String getSourceJdbcUser() {
        return sourceJdbcUser;
    }

    public void setSourceJdbcUser(String sourceJdbcUser) {
        this.sourceJdbcUser = sourceJdbcUser;
    }

    public String getSourceJdbcPass() {
        return sourceJdbcPass;
    }

    public void setSourceJdbcPass(String sourceJdbcPass) {
        this.sourceJdbcPass = sourceJdbcPass;
    }

    public String getDestDriverPath() {
        return destDriverPath;
    }

    public void setDestDriverPath(String destDriverPath) {
        this.destDriverPath = destDriverPath;
    }

    public String getDestDriverClass() {
        return destDriverClass;
    }

    public void setDestDriverClass(String destDriverClass) {
        this.destDriverClass = destDriverClass;
    }

    public String getDestJdbcUrl() {
        return destJdbcUrl;
    }

    public void setDestJdbcUrl(String destJdbcUrl) {
        this.destJdbcUrl = destJdbcUrl;
    }

    public String getDestJdbcUser() {
        return destJdbcUser;
    }

    public void setDestJdbcUser(String destJdbcUser) {
        this.destJdbcUser = destJdbcUser;
    }

    public String getDestJdbcPass() {
        return destJdbcPass;
    }

    public void setDestJdbcPass(String destJdbcPass) {
        this.destJdbcPass = destJdbcPass;
    }

    public String getSourceSql() {
        return sourceSql;
    }

    public void setSourceSql(String sourceSql) {
        this.sourceSql = sourceSql;
    }

    public String getDestInsertSql() {
        return destInsertSql;
    }

    public void setDestInsertSql(String destInsertSql) {
        this.destInsertSql = destInsertSql;
    }

    public String getDestPreviousSql() {
        return destPreviousSql;
    }

    public void setDestPreviousSql(String destPreviousSql) {
        this.destPreviousSql = destPreviousSql;
    }

    public String getDestPostSql() {
        return destPostSql;
    }

    public void setDestPostSql(String destPostSql) {
        this.destPostSql = destPostSql;
    }
}
