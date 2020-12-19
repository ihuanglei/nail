package org.huanglei.nail.okhttp;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.huanglei.nail.NailRequest;

import java.io.IOException;
import java.io.InputStream;

public class NailRequestBody extends RequestBody {

    private InputStream inputStream;
    private String contentType;

    public NailRequestBody(NailRequest request) {
        this.inputStream = request.getBody();
        this.contentType = request.getHeader().get("content-type");
    }

    @Override
    public MediaType contentType() {
        MediaType type;
        if (contentType == null || contentType.trim().isEmpty()) {
            if (inputStream == null) {
                return null;
            }
            type = MediaType.parse("application/json; charset=UTF-8;");
            return type;
        }
        return MediaType.parse(contentType);
    }

    @Override
    public long contentLength() throws IOException {
        if (inputStream != null && inputStream.available() > 0) {
            return inputStream.available();
        }
        return super.contentLength();
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        if (inputStream == null) {
            return;
        }
        byte[] buffer = new byte[1024 * 4];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            bufferedSink.write(buffer, 0, bytesRead);
        }
    }
}
