package com.example.backendtporacle.uuid;

import java.util.UUID;

public class GenerateUUID {

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase().replace("-", "");
    }
}
