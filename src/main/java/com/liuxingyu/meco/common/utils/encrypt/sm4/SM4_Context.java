package com.liuxingyu.meco.common.utils.encrypt.sm4;

/**
 * @author liuxingyu01
 * @date 2021-09-08-19:41
 * @description
 **/
public class SM4_Context {
    public int mode;

    public long[] sk;

    public boolean isPadding;

    public SM4_Context() {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
