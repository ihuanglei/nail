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

public class NailOption {

    private String proxy;

    private Long connectTimeout;

    private Long readTimeout;

    private Boolean showLog;

    private Integer maxIdleConns;

    private Boolean ignoreSSL;

    private Integer retry;

    private InputStream certInputStream;

    private String certPassword;

    private Map<String, Object> param;

    public NailOption() {
        param = new HashMap<>();
    }

    public static NailOption create() {
        return new NailOption();
    }

    public NailOption proxy(String proxy) {
        this.proxy = proxy;
        return this;
    }

    public NailOption connectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public NailOption readTimeout(Long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public NailOption maxIdleConns(Integer maxIdleConns) {
        this.maxIdleConns = maxIdleConns;
        return this;
    }

    public NailOption showLog(Boolean showLog) {
        this.showLog = showLog;
        return this;
    }

    public NailOption certInputStream(InputStream certInputStream) {
        this.certInputStream = certInputStream;
        return this;
    }

    public NailOption certPassword(String certPassword) {
        this.certPassword = certPassword;
        return this;
    }

    public NailOption other(String key, Object value) {
        this.param.put(key, value);
        return this;
    }

    public NailOption ignoreSSL(Boolean ignoreSSL) {
        this.ignoreSSL = ignoreSSL;
        return this;
    }

    public NailOption retry(Integer retry) {
        this.retry = retry;
        return this;
    }

    public Integer getRetry() {
        return retry;
    }

    public String getProxy() {
        return proxy;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public Long getReadTimeout() {
        return readTimeout;
    }

    public Boolean isShowLog() {
        return showLog;
    }

    public Integer getMaxIdleConns() {
        return maxIdleConns;
    }

    public Boolean isIgnoreSSL() {
        return ignoreSSL;
    }

    public InputStream getCertInputStream() {
        return certInputStream;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public Map<String, Object> getParam() {
        return param;
    }
}
