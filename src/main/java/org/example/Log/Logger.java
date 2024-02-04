package org.example.Log;

import org.example.Service.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    public static boolean writeLog(String literal) {
        File logFile = new File("/etc/jenda/log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                errorLog(e.toString());
                return false;
            }
        }
        try (FileWriter writer = new FileWriter(logFile,true)) {
            writer.append(LocalDateTime.now().toString()).append("\n").append(literal).append(" ").append(Service.state).append("\n");
        } catch (IOException e) {
            errorLog(e.toString());
            return false;
        }
        return true;
    }

    public static void errorLog(String literal) {
        File logFile = new File("/etc/jenda/errorLog.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter writer = new FileWriter(logFile,true)) {
            writer.append(LocalDateTime.now().toString()).append("\n").append(literal).append(" ").append(Service.state).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
