package org.example.Http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.Log.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Client extends Thread implements Runnable {
    public static float actionState = -1;
    private LocalDateTime stateChange;
    private CloseableHttpClient client = HttpClients.createDefault();
    private HttpGet request = new HttpGet("https://daniellinda.net/jenda/action");

    @Override
    public void run() {
        while (actionState != -1) {
            try {
                HttpResponse response = client.execute(request);
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                Logger.errorLog(e.toString());
                continue;
            }
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
            System.out.println(actionState);
        }
    }
}
