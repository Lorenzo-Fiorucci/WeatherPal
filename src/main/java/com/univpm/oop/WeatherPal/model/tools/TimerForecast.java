package com.univpm.oop.WeatherPal.model.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class TimerForecast {

    public static void main(String[] args) {

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                String url = "https://api.openweathermap.org/data/2.5/weather?q=ancona&appid=65e03c27f11e0b756f47a70056be962f";

                String root = System.getProperty("user.dir");
                String folderPath = "\\src\\main\\resources\\static\\Every1h\\" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm")) + ".txt";

                File file = new File(root + folderPath);

                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(httpGET(url).body());
                    writer.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 10, 60000);
    }


    /**
     * The method to make a GET request to an API.
     * @param url : url leading to the endpoint, with eventual query params
     * @return the response, of the class {@code HttpResponse<String>}.
     * @throws InterruptedException if the operation of sending the request to the API is interrupted
     * @throws IOException if an I/O error occurs when sending the request to the API
     **/

    private static HttpResponse<String> httpGET(String url) throws InterruptedException, IOException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder(new URI(url)).GET().build();
        } catch (URISyntaxException e) {
            // l'url rispetta le regole dell'api
        }
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
