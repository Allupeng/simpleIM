package com.oneorange.simpleimclient.util;

import io.netty.buffer.ByteBuf;

public class ByteBufUtil {
    public static void release(ByteBuf in){
        if (in.refCnt() > 0){
            in.release();
        }
    }
}
