package es.jdlopez.sqlcmd;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BeanReader {

    private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public static <T> T fromProperties(Properties properties, Class<T> clazz) throws ConfigException {
        try {
            T ret = clazz.getDeclaredConstructor().newInstance();
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors()) {
                propertyDescriptor.getWriteMethod().invoke(ret,
                        convertValue(propertyDescriptor, properties.getProperty(propertyDescriptor.getName())));
            }
            return ret;
        } catch (ConfigException e) {
            throw e; // no need to cascade one more
        } catch (Exception e) {
            throw new ConfigException(null, e.getMessage(), e);
        }
    }

    private static Object convertValue(PropertyDescriptor propertyDescriptor, String value) throws ConfigException {
        Class<?> clazz = propertyDescriptor.getWriteMethod().getParameterTypes()[0];
        //if (clazz.isAssignableFrom(String.class))
        //    return value;
        //else
        try {
            if (value == null && !clazz.isPrimitive())
                return null;
            if (clazz.isAssignableFrom(Integer.class))
                return Integer.valueOf(value);
            else if (clazz.isAssignableFrom(Date.class))
                return df.parse(value);
            else if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class))
                return Boolean.valueOf(value);
            else
                return value;
        } catch (Exception e) {
            String name = "unknown";
            if (propertyDescriptor != null)
                name = propertyDescriptor.getName();
            throw new ConfigException(name, e.getMessage(), e);
        }
    }
}
