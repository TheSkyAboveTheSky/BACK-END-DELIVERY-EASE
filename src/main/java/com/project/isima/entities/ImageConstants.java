package com.project.isima.entities;

import java.nio.file.Paths;

public class ImageConstants {
    public final static String DIRECTORY = "pictures/";
    public final static String ABSOLUTE_PATH = Paths.get("src", "main", "resources").toAbsolutePath().toString();
    public final static String BASE_URL = "http://localhost:8093/content/";
}
