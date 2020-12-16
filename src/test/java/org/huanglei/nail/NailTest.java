package org.huanglei.nail;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class NailTest {

    @Test
    public void testGet() throws Exception {
        NailRequest request = NailRequest.create()
                .setHost("www.baidu.com")
                .setPath("/s")
                .addQuery("wd", "nail");

        Map<String, Object> options = new HashMap<>();
        NailResponse response = Nail.request(request, options);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testPost() throws Exception {
        NailRequest request = NailRequest.create();
        Map<String, Object> options = new HashMap<>();
        NailResponse response = Nail.request(request, options);
        Assert.assertEquals(200, response.getStatusCode());
    }
}
