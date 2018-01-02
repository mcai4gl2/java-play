package code.gen.related;

import org.junit.Test;

import static code.gen.related.AsmUtils.PrintGeneratedClass;
import static code.gen.related.AsmUtils.PrintSystemClass;
import static code.gen.related.AsmUtils.createInstance;
import static org.junit.Assert.assertNotNull;

public class AsmUtilsTest {

    @Test
    public void canPrintSystemClass() throws Exception {
        PrintSystemClass(Object.class, System.out);
    }

    @Test
    public void canPrintGeneratedClass() throws Exception {
        Class clazz = AsmPojoGenerator.defineClass("TestClass", null, null);
        PrintGeneratedClass(clazz, System.out);
    }

    @Test
    public void canCreateInstance() throws Exception {
        Object obj = createInstance(Object.class);
        assertNotNull(obj);
    }
}
