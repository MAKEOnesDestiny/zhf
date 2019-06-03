package com.leqee.demo.httpxml.netty;

import io.netty.handler.codec.http.FullHttpResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpXmlResponse {

    private FullHttpResponse response;

    private Object result;

}
