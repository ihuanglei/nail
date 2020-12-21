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

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NailRetryIntercepter implements Interceptor {

    public int maxRetryCount;

    private int count = 0;

    public NailRetryIntercepter(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return retry(chain);
    }

    public Response retry(Chain chain) {
        Response response = null;
        Request request = chain.request();
        try {
            response = chain.proceed(request);
            while (!response.isSuccessful() && count < maxRetryCount) {
                count++;
                response = retry(chain);
            }
        } catch (Exception e) {
            while (count < maxRetryCount) {
                count++;
                response = retry(chain);
            }
        }
        return response;
    }
}
