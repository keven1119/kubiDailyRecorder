package com.keven.kubi.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * 日志记录，增加可拓展接口，用于写入文件保存，日志只分为4个等级，INFO、DEBUG、ERROR、LOGGER，这个类在Java中同样适用
 * Created by 陶伟基Wiki on 16/1/31.
 */
public class LogUtils {
    /**
     * 不打印
     */
    public static final int PRINTLN_PLACE_NONE = 0;
    /**
     * LogCat打印
     */
    public static final int PRINTLN_PLACE_LOG_CAT = 1;
    /**
     * Java控制台打印，用于单元测试
     */
    public static final int PRINTLN_PLACE_CONSOLE = 2;

    private static int sPrintlnPlace = PRINTLN_PLACE_CONSOLE;

    public static final int INFO = Log.INFO;
    public static final int DEBUG = Log.DEBUG;
    public static final int ERROR = Log.ERROR;
    public static final int LOGGER = 10;
    private static final int MAXLENGTH = 3000;
    public static OnLogPrintlnListener sOnLogPrintlnListener;
    private static int sOnLogPrintlnListenerPriority = LOGGER;

    /**
     * 打印到控制台
     */
    @SuppressWarnings("WrongConstant")
    private static int println(int priority, String tag, Object msg, Throwable tr, StackTraceElement stackTraceElement) {
        String msgString;
        if (msg == null) {
            msgString = "null";
        } else {
            msgString = msg.toString();
        }
        if (sOnLogPrintlnListener != null && priority >= sOnLogPrintlnListenerPriority) {
            sOnLogPrintlnListener.println(priority, tag, msgString, tr, stackTraceElement);
        }
        if (sPrintlnPlace == PRINTLN_PLACE_NONE) {
            return 0;
        }

        if (tr != null) {
            msgString = msgString + '\n' + getStackTraceString(tr);
        }
        if (tag == null) {
            if (stackTraceElement != null) {
                tag = getStackTraceElementTag(stackTraceElement);
            } else {
                tag = "UnKnown";
            }
        } else {
            tag = getStackTraceElementTag(stackTraceElement) + " " + tag;
        }

        if (sPrintlnPlace == PRINTLN_PLACE_LOG_CAT) {
            if (priority >= sOnLogPrintlnListenerPriority) {
                tag = "[logger]" + tag;
            }
            if (priority == LOGGER) {
                priority = ERROR;
            }
            //如果字符串长度超出3000，则分段显示
            while (msgString.length() > MAXLENGTH) {
                String temp = msgString.substring(0, MAXLENGTH);
                msgString = msgString.substring(MAXLENGTH);
                Log.println(priority, tag, temp + " ==>");
            }
            return Log.println(priority, tag, msgString);
        } else if (sPrintlnPlace == PRINTLN_PLACE_CONSOLE) {
            String priorityName = "INFO  ";
            if (priority == ERROR) {
                priorityName = "ERROR ";
            } else if (priority == DEBUG) {
                priorityName = "DEBUG ";
            } else if (priority == LOGGER) {
                priorityName = "LOGGER";
            }
            System.out.println(String.format("%s %s: %s", priorityName, tag, msgString));
        }
        return 0;
    }

    private static StackTraceElement getStackTraceElement() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace.length >= 3 ? stackTrace[2] : null;
    }

    public static String getStackTraceElementTag(StackTraceElement stackTraceElement) {
        if (stackTraceElement == null) {
            return "";
        }
        StringBuilder buf = new StringBuilder(80);

        buf.append(stackTraceElement.getClassName());
        buf.append('.');
        buf.append(stackTraceElement.getMethodName());

        if (stackTraceElement.isNativeMethod()) {
            buf.append("(Native Method)");
        } else {
            String fName = stackTraceElement.getFileName();

            if (fName == null) {
                buf.append("(Unknown Source)");
            } else {
                int lineNum = stackTraceElement.getLineNumber();

                buf.append('(');
                buf.append(fName);
                if (lineNum >= 0) {
                    buf.append(':');
                    buf.append(lineNum);
                }
                buf.append(')');
            }
        }
        buf.delete(0, stackTraceElement.getClassName().length());
        return buf.toString();
    }


    //DEBUG
    public static void d(Object msg) {
         if (isLog()){
             println(DEBUG, null, msg, null, getStackTraceElement());
         }
    }

    //DEBUG
    public static void d(String tag, Object msg) {
         if (isLog()){
             println(DEBUG, tag, msg, null, getStackTraceElement());
         }
    }

    //DEBUG
    public static void d(String tag, Object msg, Throwable tr) {
         if (isLog()){
             println(DEBUG, tag, msg, tr, getStackTraceElement());
         }
    }


    //LOGGER
    public static void logger(Object msg) {
         println(LOGGER, null, msg, null, getStackTraceElement());
    }

    //LOGGER
    public static void logger(String tag, Object msg) {
         println(LOGGER, tag, msg, null, getStackTraceElement());
    }
    public static void logger(String tag, Throwable tr) {
         println(LOGGER, tag, "", tr, getStackTraceElement());
    }


    //LOGGER
    public static void logger(String tag, Object msg, Throwable tr) {
         println(LOGGER, tag, msg, tr, getStackTraceElement());
    }

    //LOGGER
    public static void logger(Throwable tr) {
         println(LOGGER, null, null, tr, getStackTraceElement());
    }

    //ERROR
    public static void e(Object msg) {
        if (isLog()) {
            println(ERROR, null, msg, null, getStackTraceElement());
        }
    }

    //ERROR
    public static void e(String tag, Object msg) {
        if (isLog()) {
            println(ERROR, tag, msg, null, getStackTraceElement());
        }
    }

    /**
     * ERROR 如果用户需要打印代码调用路径，建议使用该方法 LogUtils.e(null, null, new Throwable());
     */
    public static void e(String tag, Object msg, Throwable tr) {
        if (isLog()) {
            println(ERROR, tag, msg, tr, getStackTraceElement());
        }
    }

    public static void e(Throwable tr) {
        if (isLog()) {
            println(ERROR, null, null, tr, getStackTraceElement());
        }
    }


    //INFO
    public static void i(Object msg) {
        if (isLog()) {
            println(INFO, null, msg, null, getStackTraceElement());
        }
    }

    //INFO
    public static void i(String tag, Object msg) {
        if (isLog()) {
            println(INFO, tag, msg, null, getStackTraceElement());
        }
    }

    //INFO
    public static void i(String tag, Object msg, Throwable tr) {
        if (isLog()) {
            println(INFO, tag, msg, tr, getStackTraceElement());
        }
    }

    public static boolean isLog() {
        return LogUtils.sPrintlnPlace != PRINTLN_PLACE_NONE;
    }

    /**
     * @param onLogPrintlnListener 监听器
     * @param priority             写入日志的最小等级
     */
    public static void setOnLogPrintlnListener(OnLogPrintlnListener onLogPrintlnListener, int priority) {
        LogUtils.sOnLogPrintlnListener = onLogPrintlnListener;
        LogUtils.sOnLogPrintlnListenerPriority = priority;
    }


    public interface OnLogPrintlnListener {
        void println(int priority, String tag, String msg, Throwable tr, StackTraceElement stackTraceElement);
    }

//	public static void setPrintlnPlace(int printlnPlace) {
//		LogUtils.sPrintlnPlace = printlnPlace;
//	}

    public static void asJvmLog() {
        LogUtils.sPrintlnPlace = PRINTLN_PLACE_CONSOLE;
    }

    public static void asAndroidLog() {
        LogUtils.sPrintlnPlace = PRINTLN_PLACE_LOG_CAT;
    }

    public static void asNoLog() {
        LogUtils.sPrintlnPlace = PRINTLN_PLACE_NONE;
    }


    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
