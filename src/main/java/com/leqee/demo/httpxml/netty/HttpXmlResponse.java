package com.leqee.demo.httpxml.netty;

import io.netty.handler.codec.http.FullHttpResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpXmlResponse {

    private FullHttpResponse response;

    private Object result;

}
