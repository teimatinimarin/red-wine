package com.beuwa.redwine.core.utils;

public abstract class HttpMethod {
    public static final String[] methods = {"GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"};
    public static final int GET = 0;
    public static final int HEAD = 1;
    public static final int POST = 2;
    public static final int PUT = 3;
    public static final int PATCH = 4;
    public static final int DELETE = 5;
    public static final int OPTIONS = 6;
    public static final int TRACE = 7;
}
