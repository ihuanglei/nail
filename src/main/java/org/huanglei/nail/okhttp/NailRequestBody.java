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
