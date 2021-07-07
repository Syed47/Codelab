package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Util {

    private Util() {} // to hide default public constructor

    public static void ECHO(Object o) {
        System.out.println(String.valueOf(o));
    }

    public static void ERROR(String err) {
        System.err.println(String.format("Error: %s\n", err));
    }

    public static void DEBUG(Object o) {
        ECHO(o);
    }

    public static boolean checkRegex(String regex, String text) {
        return Pattern.compile(regex).matcher(text).find();
    }

    public static String readlines(String path) {
        String text = null;
        try { text = read(new FileInputStream(path), "\n"); } 
        catch (FileNotFoundException e) { ERROR(e.getMessage()); }
        return text;
    }

    public static String read(InputStream in, String appender) {
        StringBuilder output = new StringBuilder();
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                output.append(line + appender);
            }
        } catch (IOException e) {
            ERROR(e.getMessage());
        }
        return output.toString();
    }

    public static void writeToFile(String path, String data) {
        try {
            write(new FileOutputStream(path), data);
        } catch(FileNotFoundException e) {
            ERROR(e.getMessage());
        } 
    }

    public static void write(OutputStream out, String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e) {
            ERROR(e.getMessage());
        } finally {
            try {
                writer.close();
                DEBUG("data write successful");
            } catch (IOException e) {
                ERROR(e.getMessage());
            }
        }
    }

    public static String[] getFileNames(String path) {
        return getFileNames(path, "");
    }

    public static String[] getFilePaths(String path) {
        return getFilePaths(path, "");
    }

    public static File[] filesInDir(String path) {
        return filesInDir(path, "");
    }

    public static String[] getFileNames(String path, String filter) {
        String[] files = getFilePaths(path, filter);
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String[] tokens = files[i].split("/");
            names[i] = tokens[tokens.length-1];
        }
        return names;
    } 

    public static String[] getFilePaths(String path, String filter) {
        File[] files = filesInDir(path, filter);
        String[] paths = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            paths[i] = files[i].getPath();
        }
        return paths;
    } 

    public static File[] filesInDir(String path, String filter) {
        ArrayList<File> files = new ArrayList<>();
        if (isDirectory(path)) {
            for (File file : new File(path).listFiles()) {
                if (file.getName().endsWith(filter)) {
                    files.add(file);
                }
            }
        }
        return files.toArray(new File[0]); // don't ask why String[0], trust the code
    }

    public static String fileTitle(String fileName) {
        return (fileName.contains(".") ? 
                fileName.substring(0, fileName.indexOf(".")) : 
                fileName);
    }

    public static boolean isValidPath(String path) {
        return new File(path).exists();
    }

    public static boolean isDirectory(String path) {
        if (isValidPath(path)) return new File(path).isDirectory();
        return false;
    }
}
