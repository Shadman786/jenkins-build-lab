package com.example.hello;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        String version = System.getenv().getOrDefault("APP_VERSION", "dev");
        Greeter greeter = new Greeter();

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/health", ex ->
                respond(ex, "{\"status\":\"UP\",\"version\":\"" + version + "\"}"));
        server.createContext("/hello", ex -> {
            String query = ex.getRequestURI().getQuery();
            String name = (query != null && query.startsWith("name=")) ? query.substring(5) : null;
            respond(ex, greeter.greet(name));
        });
        server.start();
        System.out.println("hello-app " + version + " listening on port " + port);
    }

    private static void respond(HttpExchange ex, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}
