package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ResourceFileUtils {


    public static String readResourceFile(String fileName){
        StringBuilder strBuilder = null;
        ClassLoader classLoader = ResourceFileUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {
            strBuilder  = new StringBuilder("");
            while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    strBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strBuilder.toString();
        }


        public static String readExternalResourceFile(String fileName){
            StringBuilder strBuilder = null;
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            try (Scanner scanner = new Scanner(file)) {
                strBuilder  = new StringBuilder("");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    strBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strBuilder.toString();
        }



}

