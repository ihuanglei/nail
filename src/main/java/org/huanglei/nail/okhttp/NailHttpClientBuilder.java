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

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.huanglei.nail.NailException;
import org.huanglei.nail.TrueHostnameVerifier;
import org.huanglei.nail.X509TrustManagerImp;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class NailHttpClientBuilder {

    private OkHttpClient.Builder builder;

    public NailHttpClientBuilder() {
        builder = new OkHttpClient().newBuilder();
    }

    public NailHttpClientBuilder showLog(Boolean isShowLog) {
        if (isShowLog != null && isShowLog) {
            this.builder.addNetworkInterceptor(new NailLogInterceptor());
        }
        return this;
    }

    public NailHttpClientBuilder connectTimeout(Long connectTimeout) {
        if (connectTimeout != null) {
            this.builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        }
        return this;
    }

    public NailHttpClientBuilder readTimeout(Long readTimeout) {
        if (readTimeout != null) {
            this.builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        }
        return this;
    }

    public NailHttpClientBuilder connectionPool(Integer maxIdleConns) {
        int maxIdleConnections = 5;
        if (maxIdleConns != null) {
            maxIdleConnections = maxIdleConns;
        }
        ConnectionPool connectionPool = new ConnectionPool(maxIdleConnections, 10000L, TimeUnit.MILLISECONDS);
        this.builder.connectionPool(connectionPool);
        return this;
    }

    public NailHttpClientBuilder isIgnoreSSL(Boolean ignoreSSL) {
        try {
            if (ignoreSSL != null && ignoreSSL) {
                X509TrustManager compositeX509TrustManager = new X509TrustManagerImp();
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{compositeX509TrustManager}, new SecureRandom());
                this.builder.sslSocketFactory(sslContext.getSocketFactory(), compositeX509TrustManager).
                        hostnameVerifier(new TrueHostnameVerifier());
            }
            return this;
        } catch (Exception e) {
            throw new NailException(e.getMessage(), e);
        }
    }

    public NailHttpClientBuilder certificate(InputStream inputStream, String password) {
        if (inputStream == null) {
            return this;
        }
        try {
            password = password == null || password.isEmpty() ? "" : password;
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());

            X509TrustManager compositeX509TrustManager = new X509TrustManagerImp();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{compositeX509TrustManager}, new SecureRandom());
            this.builder.sslSocketFactory(sslContext.getSocketFactory(), compositeX509TrustManager).
                    hostnameVerifier(new TrueHostnameVerifier());
            return this;
        } catch (Exception e) {
            throw new NailException(e.getMessage(), e);
        }
    }

    public NailHttpClientBuilder proxy(String proxy) {
        try {
            if (proxy != null && !proxy.isEmpty()) {
                URL url = new URL(proxy);
                this.builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort())));
            }
            return this;
        } catch (MalformedURLException e) {
            throw new NailException(e.getMessage(), e);
        }

    }

    public OkHttpClient build() {
        return this.builder.build();
    }
}