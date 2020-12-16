package org.huanglei.nail.okhttp;

import okhttp3.Request;
import org.huanglei.nail.NailRequest;

import java.net.URL;
import java.util.Map;

public class NailRequestBuilder {

    private Request.Builder builder;

    public NailRequestBuilder() {
        builder = new Request.Builder();
    }

    public NailRequestBuilder url(URL url) {
        this.builder.url(url);
        return this;
    }

    public NailRequestBuilder header(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.builder.header(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Request build(NailRequest request) {
        NailRequestBody requestBody;
        switch (request.getMethod()) {
            case DELETE:
                this.builder.delete();
                break;
            case POST:
                requestBody = new NailRequestBody(request);
                this.builder.post(requestBody);
                break;
            case PUT:
                requestBody = new NailRequestBody(request);
                this.builder.put(requestBody);
                break;
            case PATCH:
                requestBody = new NailRequestBody(request);
                this.builder.patch(requestBody);
                break;
            default:
                this.builder.get();
                break;
        }
        return this.builder.build();
    }

}
