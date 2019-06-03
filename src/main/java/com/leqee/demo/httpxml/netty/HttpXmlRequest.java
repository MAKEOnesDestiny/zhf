package com.leqee.demo.httpxml.netty;

import io.netty.handler.codec.http.FullHttpRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpXmlRequest {

    private FullHttpRequest request;
    private Object body;

}
