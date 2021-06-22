import java.io.*;
import java.util.Scanner;

public class Shell {

    // private String output = "";
    // private String basePath = "";
    // private int exitCode = Integer.MIN_VALUE;
    // private InputStream instream;
    // private OutputStream outstream;
    // private ProcessBuilder processBuilder;
    // private Process process;

    // public Shell() {
    //     processBuilder = new ProcessBuilder();
    // }

    // public void runCmd() throws IOException, InterruptedException {
    //     Process p = new ProcessBuilder(
    //         "bash", "-c", "javac ~/Desktop/Sequence.java").start();
    //     // receive from child
    //     new Thread(() -> {
    //         try {
    //             int c;
    //             while ((c = p.getInputStream().read()) != -1)
    //                 System.out.write((byte)c);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }).start();
    //     // send to child
    //     try (Writer w = new OutputStreamWriter(p.getOutputStream(), "UTF-8")) {
    //         w.write("send to child\n");
    //     }
    //     System.out.println("rc=" + p.waitFor());
    // }


    // public boolean execute(String command) {
    //     try {
    //         processBuilder.command("bash", "-c", command);
    //         process = processBuilder.start();
    //         exitCode = process.waitFor();
    //         instream = success() ? process.getInputStream() : process.getErrorStream();
    //         output = Util.readlines(instream);
    //     } catch (InterruptedException | IOException e) {
    //         Util.ERROR(e.getMessage());
    //     }
    //     return success();
    // }

    // public void print(String data) {
    //     try (Writer writer = new OutputStreamWriter(process.getOutputStream(), "UTF-8")) {
    //         writer.write(data);
    //         writer.close();
    //     } catch (IOException e) {
    //         Util.ERROR(e.getMessage());
    //     }
    // }

    // public void echo() {
    //     System.out.println(process.isAlive());
    // }

    // public boolean success() {
    //     return exitCode == 0;
    // }

    // public String getOutput() {
    //     if (exitCode == Integer.MIN_VALUE)
    //         return "waiting for command to finish execution.";
    //     return output;
    // }

    // public int getExitCode() {
    //     return exitCode;
    // }
}
