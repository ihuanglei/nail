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
        builder.url(url);
        return this;
    }

    public NailRequestBuilder header(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Request build(NailRequest request) {
        NailRequestBody requestBody;
        switch (request.getMethod()) {
            case DELETE:
                builder.delete();
                break;
            case POST:
                requestBody = new NailRequestBody(request);
                builder.post(requestBody);
                break;
            case PUT:
                requestBody = new NailRequestBody(request);
                this.builder.put(requestBody);
                break;
            case PATCH:
                requestBody = new NailRequestBody(request);
                builder.patch(requestBody);
                break;
            default:
                builder.get();
                break;
        }
        return builder.build();
    }

}
