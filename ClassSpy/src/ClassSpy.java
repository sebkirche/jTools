
/*
*  Display a class modifiers, type parameters, interfaces, annotations
*
*  Sébastien Kirche - 2013
*
*  taken from the java tutorial "Discovering Class Members"
*
*/

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

public class ClassSpy {

    public static void main(String... args) {
        if(args.length != 1) {
            System.out.println("Usage : ClassSpy <className>");
            System.exit(1);
        }
        processClass(args[0]);
    }

    private static void processClass(String className) {
        try {
            Class<?> c = Class.forName(className);
            out.format("Class:%n  %s%n%n", c.getCanonicalName());
            out.format("Modifiers:%n  %s%n%n",
                       Modifier.toString(c.getModifiers()));

            out.format("Type Parameters:%n");
            TypeVariable[] tv = c.getTypeParameters();
            if (tv.length != 0) {
                out.format("  ");
                for (TypeVariable t : tv) {
                    out.format("%s ", t.getName());
                }
                out.format("%n%n");
            } else {
                out.format("  -- No Type Parameters --%n%n");
            }

            out.format("Implemented Interfaces:%n");
            Type[] intfs = c.getGenericInterfaces();
            if (intfs.length != 0) {
                for (Type intf : intfs) {
                    out.format("  %s%n", intf.toString());
                }
                out.format("%n");
            } else {
                out.format("  -- No Implemented Interfaces --%n%n");
            }

            out.format("Inheritance Path:%n");
            List<Class> l = new ArrayList<Class>();
            printAncestor(c, l);
            if (l.size() != 0) {
                for (Class<?> cl : l) {
                    out.format("  %s%n", cl.getCanonicalName());
                    intfs = cl.getGenericInterfaces();
                    if (intfs.length != 0) {
                        out.format("    Interfaces:%n");
                        for (Type intf : intfs)
                            out.format("    %s%n", intf.toString());
                    }
                }
                out.format("%n");
            } else {
                out.format("  -- No Super Classes --%n%n");
            }

            out.format("Annotations:%n");
            Annotation[] ann = c.getAnnotations();
            if (ann.length != 0) {
                for (Annotation a : ann) {
                    out.format("  %s%n", a.toString());
                }
                out.format("%n");
            } else {
                out.format("  -- No Annotations --%n%n");
            }

        } catch (ClassNotFoundException x) {
            //x.printStackTrace();
            System.err.println(className + " was not found.");
        }

    }

    private static void printAncestor(Class<?> c, List<Class> l) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
            printAncestor(ancestor, l);
        }
    }
}
