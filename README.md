# Nail


基于OKHttp3 封装的工具类。支持配置Header、配置SSL、配置proxy、Cookie等


```java

NailRequest request = NailRequest.create()
        .setHost("www.baidu.com")
        .setPath("/s")
        .addQuery("wd", "nail");
NailResponse response = Nail.request(request);

```



