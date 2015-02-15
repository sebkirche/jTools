
/* List the classes in a jar
*
*
*
*  idea from http://stackoverflow.com/questions/15720822/how-to-get-names-of-classes-inside-a-jar-file
*
*/

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

public class ListJar {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("usage: java ListJar <file.jar> [file2.jar ... filen.jar]");
            System.exit(0);
        }
        for (String a : args) {
            processJar(a);
        }
    }

    static void processJar(String jar) {
        try {
            List<String> classNames=new ArrayList<String>();
            ZipInputStream zip=new ZipInputStream(new FileInputStream(jar));
            for(ZipEntry entry=zip.getNextEntry(); entry!=null; entry=zip.getNextEntry()) {
                //System.out.println("entry:"+entry.getName());
                if(entry.getName().endsWith(".class") && !entry.isDirectory()) {
                    // This ZipEntry represents a class. Now, what class does it represent?
                    StringBuilder className=new StringBuilder();
                    for(String part : entry.getName().split("/")) {
                        if(className.length() != 0)
                            className.append(".");
                        className.append(part);
                        if(part.endsWith(".class"))
                            className.setLength(className.length()-".class".length());
                    }
                    classNames.add(className.toString());
                }
            }

            if(classNames.size() > 0) {
                System.out.println("classes in " + jar);
                Collections.sort(classNames);
                for(String s : classNames)
                    System.out.println(s);
            }

        } catch(FileNotFoundException fnfe) {
            System.err.println(jar + "not found");
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
