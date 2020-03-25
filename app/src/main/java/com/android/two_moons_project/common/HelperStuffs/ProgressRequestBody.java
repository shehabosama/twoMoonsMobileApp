package com.android.two_moons_project.common.HelperStuffs;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private File file ;
    private UploadCallBacks listenr;
    public static final int DEFAULT_BUFFER_SIZE = 4096;

    public ProgressRequestBody(File file, UploadCallBacks listenr) {
        this.file = file;
        this.listenr = listenr;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("image/jpg");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        long filelength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;
        try{
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while((read = in.read(buffer))!= -1)
            {
                handler.post(new ProgressUpdater(uploaded,filelength));
                uploaded +=read;
                sink.write(buffer,0,read);
            }

        }finally {

            in.close();
        }

    }
    private class ProgressUpdater implements Runnable
    {

        private long uploaded;
        private long fileLength;
        public ProgressUpdater(long uploaded,long fileLength)
        {
            this.uploaded = uploaded;
            this.fileLength = fileLength;
        }
        @Override
        public void run() {

            listenr.onProgressUpdate((int)(100*uploaded/fileLength));
        }
    }
}
