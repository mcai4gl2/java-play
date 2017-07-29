package code.gen.related;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AsmPojoGeneratorTest {

    @Test
    public void canGenerateClassWithSerialVersionUID() throws Exception {
        Class clazz = AsmPojoGenerator.defineClass("TestClass", null, null);
        Field field = clazz.getField("serialVersionUID");
        assertNotNull(field);
        assertEquals("serialVersionUID", field.getName());
        assertEquals("long", field.getType().getName());
        assertTrue(Serializable.class.isAssignableFrom(clazz));
    }

}
