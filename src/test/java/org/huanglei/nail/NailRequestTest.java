package org.huanglei.nail;

import org.junit.Test;

public class NailRequestTest {

    @Test
    public void testRequest() {
        NailRequest request = NailRequest.create().setProtocol(NailRequest.Protocol.HTTPS).setHost("www.baidu.com").setPath("/s").addQuery("wd", "123");
        System.out.println(request);
    }
}
