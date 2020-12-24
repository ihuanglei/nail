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

import okhttp3.Headers;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NailResponse {

    private Response response;

    private int statusCode;

    private String statusMessage;

    private Map<String, String> header = new HashMap<>();

    private InputStream body;

    public NailResponse(Response response) {
        this.response = response;
        statusCode = response.code();
        statusMessage = response.message();
        body = response.body().byteStream();
        Headers headers = response.headers();
        Map<String, List<String>> resultHeaders = headers.toMultimap();
        for (Map.Entry<String, List<String>> entry : resultHeaders.entrySet()) {
            this.header.put(entry.getKey(), String.join(";", entry.getValue()));
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public InputStream getResponse() {
        return body;
    }

    public String getResponseBody() {
        if (body == null) {
            return "";
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        try {
            while (true) {
                final int read = body.read(buff);
                if (read == -1) {
                    break;
                }
                os.write(buff, 0, read);
            }
        } catch (Exception e) {
            throw new NailException(e.getMessage(), e);
        }
        return new String(os.toByteArray());
    }
}
