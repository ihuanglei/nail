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

import okhttp3.OkHttpClient;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NailHttpClientHelper {

    private static ConcurrentHashMap<String, OkHttpClient> clients = new ConcurrentHashMap<>();

    public static OkHttpClient getOkHttpClient(final String host, final int port, Map<String, Object> map) throws Exception {
        String key;
        if (map.get("httpProxy") != null || map.get("httpsProxy") != null) {
            Object urlString = map.get("httpProxy") == null ? map.get("httpsProxy") : map.get("httpProxy");
            URL url = new URL(String.valueOf(urlString));
            key = getClientKey(url.getHost(), url.getPort());
        } else {
            key = getClientKey(host, port);
        }

        OkHttpClient client = clients.get(key);
        if (client == null) {
            client = createOkHttpClient(map);
            clients.put(key, client);
        }
        return client;
    }

    private static OkHttpClient createOkHttpClient(Map<String, Object> map) {
        NailHttpClientBuilder builder = new NailHttpClientBuilder();
        return builder.connectTimeout(map).readTimeout(map).connectionPool(map).certificate(map).proxy(map).build();
    }

    private static String getClientKey(String host, int port) {
        return String.format("%s:%d", host, port);
    }
}
