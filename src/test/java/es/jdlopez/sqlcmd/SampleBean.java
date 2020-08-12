package es.jdlopez.sqlcmd;

import java.util.Date;

public class SampleBean {

    private String name;
    private Integer number;
    private Date someDate;
    private boolean yesNo;
    private Boolean yesNoNull;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getSomeDate() {
        return someDate;
    }

    public void setSomeDate(Date someDate) {
        this.someDate = someDate;
    }

    public boolean isYesNo() {
        return yesNo;
    }

    public void setYesNo(boolean yesNo) {
        this.yesNo = yesNo;
    }

    public Boolean getYesNoNull() {
        return yesNoNull;
    }

    public void setYesNoNull(Boolean yesNoNull) {
        this.yesNoNull = yesNoNull;
    }
}
