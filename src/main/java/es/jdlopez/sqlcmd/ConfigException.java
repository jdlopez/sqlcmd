package es.jdlopez.sqlcmd;

public class ConfigException extends Exception {
    private String fieldName;

    public ConfigException(String fieldName, String message, Throwable cause) {
        super(message, cause);
        this.fieldName = fieldName;
    }

    public ConfigException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
