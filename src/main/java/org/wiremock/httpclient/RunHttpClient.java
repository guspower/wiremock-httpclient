package org.wiremock.httpclient;

import com.github.tomakehurst.wiremock.http.HttpClientFactory;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;

public class RunHttpClient {

    private static final int MINUTES = 1000 * 60;

    private final HttpClient client;
    private final URI endpoint;
    private final long runDuration = 10 * 1000;

    private long endTime = 0;
    private long requestCount = 0;

    public RunHttpClient() throws Exception {
        client = HttpClientFactory.createClient(1000, 5 * MINUTES);
        endpoint = new URI("http://localhost:5050");
    }

    public void run() {
        printSystem();
        endTime = calculateEndTime();

        while(keepRunning()) {
            try {
                doRequest();
            } catch (NoHttpResponseException noResponse) {
                bugReplicated(noResponse);
            } catch (ConnectException cannotConnect) {
                handleConnectException(cannotConnect);
            } catch (IOException unexpected) {
                handleIOException(unexpected);
            }
        }
    }

    private void printSystem() {
        System.out.println("JVM:");
        System.getProperties().forEach((key, value) -> {
                    if (key.toString().startsWith("java.vm")) {
                        System.out.println(value);
                    }
                }
        );
        System.out.println("");
    }

    private boolean keepRunning() {
        return System.currentTimeMillis() < endTime;
    }

    private long calculateEndTime() {
        return System.currentTimeMillis() + runDuration;
    }

    private void doRequest() throws IOException {
        client.execute(new HttpGet(endpoint));
        incrementRequestCount();
    }

    private void incrementRequestCount() {
        requestCount++;
        if((requestCount % 1000) == 0) {
            System.out.println("" + requestCount);
        }
    }

    private void handleConnectException(ConnectException cannotConnect) {
        throw new RuntimeException("Unable to connect - is target server running?", cannotConnect);
    }

    private void bugReplicated(NoHttpResponseException noResponse) {
        throw new RuntimeException("+++ BUG REPLICATED +++", noResponse);
    }

    private void handleIOException(IOException unexpected) {
        throw new RuntimeException("Unexpected IOException", unexpected);
    }

    public static void main(String[] args) throws Exception {
        new RunHttpClient().run();
    }

}
