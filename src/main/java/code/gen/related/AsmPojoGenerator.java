package code.gen.related;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

public class AsmPojoGenerator {

    public static Class defineClass(String className, String packageName, Map<String, Class> properties) {
        final String fullname;
        if (packageName == null && "".equals(packageName))
            fullname = String.join("/", packageName.replace('.', '/'), className);
        else
            fullname = className;

        Class<?> clazz = new ClassLoader(AsmPojoGenerator.class.getClassLoader()) {
            public Class<?> defineClass() {
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

                // Creating class annotation
                {
                    AnnotationVisitor visitor = writer.visitAnnotation("Lcode/gen/related/Generated;", true);
                    visitor.visitEnd();
                }
                writer.visit(Opcodes.V1_8, ACC_PUBLIC, fullname, null, "java/lang/Object", new String[] {"java/io/Serializable"});

                // Creating serialVersionUID
                {
                    FieldVisitor fieldVisitor = writer.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                            "serialVersionUID", "J", null, 1L);
                    fieldVisitor.visitEnd();
                }

                writer.visitEnd();

                byte[] bytes = writer.toByteArray();
                return defineClass(fullname.replace('/', '.'), bytes, 0, bytes.length);
            }
        }.defineClass();

        return clazz;
    }

}
