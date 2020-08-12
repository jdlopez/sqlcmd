package es.jdlopez.sqlcmd;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Properties;

public class TestBeanWriter {

    @Test
    public void testRead() throws ConfigException {
        String name = "some name";
        Integer number = 123;
        Date theDate = new Date();


        Properties p = new Properties();
        p.setProperty("name", name);
        p.setProperty("number", number.toString());
        p.setProperty("someDate", String.format("%1$td/%1$tm/%1$tY", theDate));
        p.setProperty("yesNo", "true");
        //p.setProperty("yesNoNull", null);

        SampleBean res = BeanReader.fromProperties(p, SampleBean.class);

        Assert.assertEquals(name, res.getName());
        Assert.assertEquals(number, res.getNumber());
        Assert.assertEquals(String.format("%1$td/%1$tm/%1$tY", theDate),
                String.format("%1$td/%1$tm/%1$tY", res.getSomeDate()));
        Assert.assertEquals(true, res.isYesNo());
        Assert.assertNull(res.getYesNoNull());
    }
}

