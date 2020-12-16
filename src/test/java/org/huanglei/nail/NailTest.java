package org.huanglei.nail;

import org.junit.Assert;
import org.junit.Test;


public class NailTest {

    @Test
    public void testGet() throws Exception {
        NailRequest request = NailRequest.create()
                .setHost("www.baidu.com")
                .setPath("/s")
                .addQuery("wd", "nail");
        NailResponse response = Nail.request(request);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testPost() throws Exception {
        NailRequest request = NailRequest.create();
        NailResponse response = Nail.request(request);
        Assert.assertEquals(200, response.getStatusCode());
    }
}
