package org.example.Service;

import org.example.Executor.Exec;
import org.example.Http.Client;
import org.example.Log.Logger;

import java.io.File;

import static org.example.Log.Logger.errorLog;

public class Service {
    public static String state = "Starting";
    public static int value = 0;
    private static Thread request;

    public static boolean start() {
        Thread shutdown = new Thread(Service::stop);
        Runtime.getRuntime().addShutdownHook(shutdown);
        File dir = new File("/etc/jenda/");
        dir.mkdirs();
        Logger.writeLog("Initiated start");
        try {
            request = new Thread(new Client());
            request.start();
        } catch (RuntimeException e) {
            errorLog(e.toString());
            return false;
        }
        Client.actionState = 1;
        state = "Started";
        Logger.writeLog("Started");
        return true;
    }

    public static void stop() {
        state = "Stopping";
        Logger.writeLog("Initiated shutdown");
        Client.actionState = -1;
        if (!Exec.execute(0))
            Logger.errorLog("Failed to execute default state on shutdown");
        if (request.isAlive())
            request.interrupt();
        state = "Stopped";
        Logger.writeLog("Stopped");
        Runtime.getRuntime().halt(0);
    }

}
