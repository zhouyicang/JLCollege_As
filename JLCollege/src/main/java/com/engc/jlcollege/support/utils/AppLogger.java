package com.engc.jlcollege.support.utils;

import com.engc.jlcollege.BuildConfig;

import android.util.Log;


/**
 * User: Jiang Qi
 * Date: 12-7-31
  */

/**
 * Wrapper API for sending log output.
 */
public class AppLogger {
    protected static final String TAG = "JLCollege";

    private AppLogger() {
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
        Log.v(TAG, buildMessage(msg));
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String msg, Throwable thr) {
        Log.v(TAG, buildMessage(msg), thr);
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
        Log.d(TAG, buildMessage(msg));
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr  An exception to log
     */
    public static void d(String msg, Throwable thr) {
        Log.d(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        Log.i(TAG, buildMessage(msg));
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String msg, Throwable thr) {
        Log.i(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        Log.e(TAG, buildMessage(msg));
    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        Log.w(TAG, buildMessage(msg));
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String msg, Throwable thr) {
        Log.w(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    public static void w(Throwable thr) {
        Log.w(TAG, buildMessage(""), thr);
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String msg, Throwable thr) {
        Log.e(TAG, buildMessage(msg), thr);
    }
    
 // 下面是传入类名打印log
 	public static void i(Class<?> _class, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(_class.getName(), buildMessage(msg));
 	}

 	public static void d(Class<?> _class, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(_class.getName(), buildMessage(msg));
 	}

 	public static void e(Class<?> _class, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(_class.getName(), buildMessage(msg));
 	}

 	public static void v(Class<?> _class, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(_class.getName(), buildMessage(msg));
 	}

 	// 下面是传入自定义tag的函数
 	public static void i(String tag, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(tag, buildMessage(msg));
 	}

 	public static void d(String tag, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(tag, buildMessage(msg));
 	}

 	public static void e(String tag, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(tag, buildMessage(msg));
 	}

 	public static void v(String tag, String msg) {
 		if (BuildConfig.DEBUG)
 			Log.i(tag, buildMessage(msg));
 	}

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        return new StringBuilder()
                .append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("(): ")
                .append(msg).toString();
    }
}
