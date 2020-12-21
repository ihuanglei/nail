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

import okhttp3.*;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

class NailLogInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(NailLogInterceptor.class);

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (!logger.isDebugEnabled()) {
            return chain.proceed(request);
        }

        String reqId = UUID.randomUUID().toString();

        String body = "";
        RequestBody requestBody = request.body();

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }

        Protocol protocol = chain.connection().protocol();

        logger.debug("Nail/{} >>>\n{} {} {}\n{}\n{}\n",
                reqId, request.method(), request.url(), protocol, request.headers(), body);

        final long startTime = System.nanoTime();
        Response response = chain.proceed(request);
        final long responseTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        ResponseBody responseBody = response.body();

        String content = response.body().string();

        logger.debug("Nail/{} <<<\n{} {} {}\nStatus Code: {} {}\nTime: {}ms\n\n{}\n{}\n",
                reqId, request.method(), request.url(), protocol, response.code(), response.message(), responseTime, request.headers(), content);

        return response.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(), content))
                .build();
    }
}