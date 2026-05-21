package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class WriteErrorLogs {

    //Class responsible for write error logs

    private final File logFolder = new File("Logs");

    public void makeLogFolder () {
        logFolder.mkdir();
    }

    public void writeLogs (ErrorLogs logs) {
        if (!logFolder.exists()) makeLogFolder();

        LocalDateTime creationDate = logs.getDate();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HHmm");

        String fileName = creationDate.format(df)
                + "T"
                + creationDate.format(tf) + "-"
                + "PET-SYSTEM-ERROR"
                + ".txt";

        File logInDirectory = new File(logFolder, fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logInDirectory))) {
            bw.write(String.valueOf(logs));
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
