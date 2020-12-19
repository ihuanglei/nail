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

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.huanglei.nail.okhttp.NailHttpClientHelper;
import org.huanglei.nail.okhttp.NailRequestBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class Nail {

    public final static String URL_ENCODING = "UTF-8";

    /**
     * 整理url内容
     */
    private static String composeUrl(NailRequest request) throws UnsupportedEncodingException {
        String host = request.getHost();
        NailRequest.Protocol protocol = request.getProtocol();
        StringBuilder sb = new StringBuilder();
        sb.append(protocol);
        sb.append("://").append(host);
        if (request.getPath() != null) {
            sb.append(request.getPath());
        }
        Map<String, String> queries = request.getQuery();
        if (queries.size() > 0) {
            if (sb.indexOf("?") >= 1) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            for (Map.Entry<String, String> entry : queries.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                if (val == null || "null".equals(val)) {
                    continue;
                }
                sb.append(URLEncoder.encode(key, URL_ENCODING));
                sb.append("=");
                sb.append(URLEncoder.encode(val, URL_ENCODING));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 设置代理
     */
    private static Map<String, String> setProxyAuthorization(Map<String, String> header, Object httpsProxy) throws MalformedURLException {
        if (httpsProxy != null) {
            String str = String.valueOf(httpsProxy);
            if (str != null && !str.isEmpty()) {
                URL proxyUrl = new URL(str);
                String userInfo = proxyUrl.getUserInfo();
                if (userInfo != null) {
                    String[] userMessage = userInfo.split(":");
                    String credential = Credentials.basic(userMessage[0], userMessage[1]);
                    header.put("Proxy-Authorization", credential);
                }
            }
        }
        return header;
    }

    /**
     * http请求
     *
     * @param request
     * @param runtimeOptions
     * @return
     * @throws Exception
     */
    public static NailResponse request(NailRequest request, Map<String, Object> runtimeOptions) throws Exception {
        String urlStr = composeUrl(request);
        URL url = new URL(urlStr);
        OkHttpClient okHttpClient = NailHttpClientHelper.getOkHttpClient(url.getHost(), url.getPort(), runtimeOptions);
        Request okRequest = new NailRequestBuilder().url(url).header(setProxyAuthorization(request.getHeader(), runtimeOptions.get("httpsProxy"))).build(request);
        Response okResponse = okHttpClient.newCall(okRequest).execute();
        return new NailResponse(okResponse);
    }

    /**
     * http请求
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static NailResponse request(NailRequest request) throws Exception {
        return request(request, Collections.emptyMap());
    }

    public static InputStream toReadable(String string) {
        return toReadable(string.getBytes(StandardCharsets.UTF_8));
    }

    public static InputStream toReadable(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

}
