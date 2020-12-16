package org.huanglei.nail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NailRequest {

    public enum Method {
        POST, PUT, GET, DELETE, PATCH
    }

    public enum Protocol {
        HTTP, HTTPS
    }

    private Protocol protocol;

    private String host;

    private Integer port;

    private String path;

    private Method method;

    private Map<String, String> headers;

    private Map<String, String> query;

    private InputStream body;


    public NailRequest() {
        protocol = Protocol.HTTP;
        method = Method.GET;
        port = 80;
        headers = new HashMap<>();
        query = new HashMap<>();
    }

    public static NailRequest create() {
        return new NailRequest();
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public NailRequest setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getHost() {
        return host;
    }

    public NailRequest setHost(String host) {
        this.host = host;
        return addHeader("host", host);
    }

    public Integer getPort() {
        return port;
    }

    public NailRequest setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public NailRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public NailRequest setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public NailRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public NailRequest setQuery(Map<String, String> query) {
        this.query = query;
        return this;
    }

    public InputStream getBody() {
        return body;
    }

    public NailRequest setBody(InputStream body) {
        this.body = body;
        return this;
    }

    public NailRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public NailRequest addQuery(String key, String value) {
        query.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Protocol:").append(this.protocol).append("\n");
        sb.append("Port:").append(this.port).append("\n");
        sb.append(this.method).append(" ").append(this.path).append("\n");
        sb.append("Query:").append("\n");
        for (Map.Entry<String, String> e : this.query.entrySet()) {
            sb.append("  ").append(e.getKey()).append(":").append(e.getValue()).append("\n");
        }
        sb.append("Header:").append("\n");
        for (Map.Entry<String, String> e : this.headers.entrySet()) {
            sb.append("  ").append(e.getKey()).append(":").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
