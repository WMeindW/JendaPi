package org.example.Executor;

import org.example.Log.Logger;

import java.io.IOException;

public class Exec {
    public static boolean execute(int value) {
        String command = "sh controlExecutor.sh " + value; // Example command, you can replace it with any shell command
        try {
            // Execute the command
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            Logger.errorLog(e.toString());
            return false;
        }
        Logger.writeLog("Executed value: " + value);
        return true;
    }
}
