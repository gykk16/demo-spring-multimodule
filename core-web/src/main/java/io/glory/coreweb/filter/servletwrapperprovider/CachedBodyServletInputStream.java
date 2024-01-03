package io.glory.coreweb.filter.servletwrapperprovider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachedBodyServletInputStream extends ServletInputStream {

    private static final Logger log = LoggerFactory.getLogger(CachedBodyServletInputStream.class);

    private final InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException e) {
            log.error("", e);
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }

}
