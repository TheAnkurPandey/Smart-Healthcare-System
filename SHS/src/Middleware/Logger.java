package Middleware;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    static String fileName = "logs.txt";

    public static void log(String exception) {
        try {
            File file = new File(fileName);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));

            if (file.exists() == false)
                file.createNewFile();

            String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());

            printWriter.println(exception + "\nException occurred at: " + time + "\n");
            printWriter.close();

        }catch (IOException ioException) {
            ioException.getMessage();
        }

    }
}
