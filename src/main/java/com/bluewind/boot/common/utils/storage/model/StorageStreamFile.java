package com.bluewind.boot.common.utils.storage.model;

import java.io.*;

/**
 * @author liuxingyu01
 * @date 2021-05-16-9:58
 * @description 存储对象
 **/
public class StorageStreamFile extends StorageFile {
    private static final long serialVersionUID = -8149682619413025601L;

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public long writeTo(OutputStream out) throws IOException {
        int ch;
        while ((ch = inputStream.read()) != -1) {
            out.write(ch);
        }
        return inputStream.available();
    }

    public long writeTo(File file) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            return writeTo(out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public long writeTo(String filename) throws IOException {
        return writeTo(new File(filename));
    }

}
