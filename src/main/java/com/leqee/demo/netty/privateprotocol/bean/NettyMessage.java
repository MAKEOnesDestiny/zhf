package com.leqee.demo.netty.privateprotocol.bean;

import lombok.Data;

@Data
public class NettyMessage {

    private Header header;
    private Object body;

}
