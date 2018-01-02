package code.gen.related;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import java.io.Serializable;
import java.util.Map;

import static code.gen.related.AsmUtils.*;
import static org.objectweb.asm.Opcodes.*;

public class AsmPojoGenerator {

    public static Class<?> defineClass(final String className, final String packageName, final Map<String, Class> properties) {
        final String fullname;
        if (packageName == null && "".equals(packageName))
            fullname = String.join("/", slashSeparated(packageName), className);
        else
            fullname = className;

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // Creating class annotation
        {
            AnnotationVisitor visitor = writer.visitAnnotation(Type.getInternalName(Generated.class), true);
            visitor.visitEnd();
        }
        writer.visit(V1_8, ACC_PUBLIC, fullname, NO_GENERIC_SIGNATURE, Type.getInternalName(Object.class),
                new String[]{Type.getInternalName(Serializable.class)});

        // Creating serialVersionUID
        {
            FieldVisitor fieldVisitor = writer.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                    "serialVersionUID", Type.LONG_TYPE.getDescriptor(), NO_GENERIC_SIGNATURE, 1L);
            fieldVisitor.visitEnd();
        }

        if (properties != null) {
            for (String name : properties.keySet()) {
                final FieldVisitor fieldVisitor = writer.visitField(ACC_PRIVATE,
                        name, Type.getDescriptor(properties.get(name)), NO_GENERIC_SIGNATURE, NO_INITIAL_VALUE);
                fieldVisitor.visitEnd();


            }
        }

        writer.visitEnd();

        byte[] bytes = writer.toByteArray();

        return createClass(fullname.replace('/', '.'), bytes);
    }
}
