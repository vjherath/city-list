package com.example.citylist.constant;

public final class ApiPathConstants {

    public static final String PATH_SEPARATOR = "/";
    public static final String API_PREFIX = "api";
    public static final String API_VERSION_V1 = "v1";

    public static final String API_PREFIX_VERSION_V1 = API_PREFIX + PATH_SEPARATOR + API_VERSION_V1;

    public static final String API_V1_USER = API_PREFIX_VERSION_V1 + PATH_SEPARATOR + "user";
    public static final String API_CITY = API_PREFIX_VERSION_V1 + PATH_SEPARATOR + "city";

    public static final String FILE_DOWNLOAD = API_PREFIX_VERSION_V1 + PATH_SEPARATOR + "download/file";

}
