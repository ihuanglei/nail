package org.huanglei.nail;

import org.junit.Assert;
import org.junit.Test;

public class NailTest {

    @Test
    public void testGet() throws Exception {
        NailRequest request = NailRequest.create()
                .setHost("www.baidu.com")
                .setPath("/s")
                .putQuery("wd", "nail");
        NailResponse response = Nail.request(request);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testPost() throws Exception {
        NailRequest request = NailRequest.create()
                .setMethod(NailRequest.Method.POST)
                .setHost("192.168.10.205")
                .putHeader("content-type", "application/x-www-form-urlencoded")
                .setPath("/api/login").setBody(Nail.toReadable("emAccount=admin&password=f379eaf3c831b04de153469d1bec345e"));
        NailResponse response = Nail.request(request);
        System.out.println(response.getResponseBody());
        Assert.assertEquals(200, response.getStatusCode());
    }
}
