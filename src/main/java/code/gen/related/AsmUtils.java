package code.gen.related;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Optional;

import static org.objectweb.asm.Opcodes.*;

public class AsmUtils {

    private AsmUtils() {

    }

    public static final String NO_GENERIC_SIGNATURE = null;

    public static final String[] NO_EXCEPTIONS = null;

    public static final String[] NO_INTERFACES = null;

    public static final Object NO_INITIAL_VALUE = null;

    public static final String CONSTRUCTOR = "<init>";

    public static final String TO_STRING_METHOD = "toString";

    public static final Type OBJECT_TYPE = Type.getType(Object.class);

    public static final Type OPTIONAL_TYPE = Type.getType(Optional.class);

    public static final Type STRING_TYPE = Type.getType(String.class);

    public static final Type THROWABLE_TYPE = Type.getType(Throwable.class);

    public static final Type EXCEPTION_TYPE = Type.getType(Exception.class);

    public static void visitIntConstant(final MethodVisitor method, int value) {
        switch (value) {
            case -1: method.visitInsn(ICONST_M1); return;
            case 0: method.visitInsn(ICONST_0); return;
            case 1: method.visitInsn(ICONST_1); return;
            case 2: method.visitInsn(ICONST_2); return;
            case 3: method.visitInsn(ICONST_3); return;
            case 4: method.visitInsn(ICONST_4); return;
            case 5: method.visitInsn(ICONST_5); return;
            default: method.visitIntInsn(SIPUSH, value); return;
        }
    }

    public static void visitCast(final MethodVisitor method, final Class type) {
        method.visitTypeInsn(CHECKCAST, Type.getInternalName(type));
    }

    public static String getArrayInternalName(final Class clazz) {
        return "[L" + Type.getInternalName(clazz) + ";";
    }

    public static String slashSeparated(final String className) {
        return className.replace('.', '/');
    }

    public static Class<?> createClass(final String fullClassName, final byte[] bytes) {
        return new ClassLoader() {
            public Class<?> defineClass(final String name, final byte[] bytes) {
                return defineClass(name, bytes, 0, bytes.length);
            }
        }.defineClass(fullClassName, bytes);
    }
}
