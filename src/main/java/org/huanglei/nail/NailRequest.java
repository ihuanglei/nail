/*
 * Copyright 2020-present huanglei.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.huanglei.nail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NailRequest {

    private static String OS_INFO = "(" + System.getProperty("os.name").toLowerCase() + ")";

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

    private Map<String, String> header;

    private Map<String, String> query;

    private InputStream body;

    public NailRequest() {
        protocol = Protocol.HTTP;
        method = Method.GET;
        port = 80;
        header = new HashMap<>();
        header.put("User-Agent", "nail/0.0.1 " + OS_INFO);
        query = new HashMap<>();
    }

    public static NailRequest create() {
        return new NailRequest();
    }

    public Protocol getProtocol() {
        if (protocol == null) {
            return Protocol.HTTP;
        }
        return protocol;
    }

    public NailRequest protocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getHost() {
        return host;
    }

    public NailRequest host(String host) {
        this.host = host;
        return header("Host", host);
    }

    public Integer getPort() {
        return port;
    }

    public NailRequest port(Integer port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public NailRequest path(String path) {
        this.path = path;
        return this;
    }

    public Method getMethod() {
        if (method == null) {
            return Method.GET;
        }
        return method;
    }

    public NailRequest method(Method method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public NailRequest header(Map<String, String> headers) {
        this.header = headers;
        return this;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public NailRequest query(Map<String, String> query) {
        this.query = query;
        return this;
    }

    public InputStream getBody() {
        return body;
    }

    public NailRequest body(InputStream body) {
        this.body = body;
        return this;
    }

    public NailRequest header(String key, String value) {
        header.put(key, value);
        return this;
    }

    public NailRequest query(String key, String value) {
        query.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Protocol: ").append(this.protocol).append("\n");
        sb.append("Port: ").append(this.port).append("\n");
        sb.append(this.method).append(" ").append(this.path).append("\n");
        sb.append("Query: ").append("\n");
        for (Map.Entry<String, String> e : this.query.entrySet()) {
            sb.append("  ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        sb.append("Header: ").append("\n");
        for (Map.Entry<String, String> e : this.header.entrySet()) {
            sb.append("  ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
