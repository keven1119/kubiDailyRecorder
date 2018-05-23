package com.keven.kubi.utils;

import rx.plugins.RxJavaHooks;

public class RxJavaPluginUtils {
    public static boolean debug = false;


    public static void ifDebugCatch(Throwable throwable){
        if (RxJavaHooks.getOnError() != null){
            RxJavaHooks.getOnError().call(throwable);
        }
        if (debug){
            throw new RuntimeException(throwable);
        }
    }

    public static void handleException(Throwable throwable){
        if (RxJavaHooks.getOnError() != null){
            RxJavaHooks.getOnError().call(throwable);
        }
    }

    public static void setDebug(boolean debug) {
        RxJavaPluginUtils.debug = debug;
    }

    public static class SmartStopRunningException extends Exception{

    }
    public static class NormalStopRunningException extends Exception{

    }

}
