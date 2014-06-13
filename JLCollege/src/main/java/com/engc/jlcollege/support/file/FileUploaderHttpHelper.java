package com.engc.jlcollege.support.file;


public class FileUploaderHttpHelper {

    public static interface ProgressListener {
        public void transferred(long data);

        public void completed();
    }
}
