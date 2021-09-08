package com.liuxingyu.meco.common.utils.encrypt.sm3;


/**
 * @author liuxingyu01
 * @date 2021-01-02-14:20
 * @description 国密3加密算法工具类
 **/
public class SM3Digest {
    /** SM3值的长度 */
    private static final int BYTE_LENGTH = 32;

    /** SM3分组长度 */
    private static final int BLOCK_LENGTH = 64;

    /** 缓冲区长度 */
    private static final int BUFFER_LENGTH = BLOCK_LENGTH * 1;

    /** 缓冲区 */
    private byte[] xBuf = new byte[BUFFER_LENGTH];

    /** 缓冲区偏移量 */
    private int xBufOff;

    /** 初始向量 */
    private byte[] V = SM3.iv.clone();

    private int cntBlock = 0;

    public SM3Digest() {
    }

    public SM3Digest(SM3Digest t) {
        System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
        this.xBufOff = t.xBufOff;
        System.arraycopy(t.V, 0, this.V, 0, t.V.length);
    }

    /**
     * SM3结果输出
     *
     * @param out    保存SM3结构的缓冲区
     * @param outOff 缓冲区偏移量
     * @return
     */
    public int doFinal(byte[] out, int outOff) {
        byte[] tmp = doFinal();
        System.arraycopy(tmp, 0, out, 0, tmp.length);
        return BYTE_LENGTH;
    }

    public void reset() {
        xBufOff = 0;
        cntBlock = 0;
        V = SM3.iv.clone();
    }

    /**
     * 明文输入
     *
     * @param in    明文输入缓冲区
     * @param inOff 缓冲区偏移量
     * @param len   明文长度
     */
    public void update(byte[] in, int inOff, int len) {
        int partLen = BUFFER_LENGTH - xBufOff;
        int inputLen = len;
        int dPos = inOff;
        if (partLen < inputLen) {
            System.arraycopy(in, dPos, xBuf, xBufOff, partLen);
            inputLen -= partLen;
            dPos += partLen;
            doUpdate();
            while (inputLen > BUFFER_LENGTH) {
                System.arraycopy(in, dPos, xBuf, 0, BUFFER_LENGTH);
                inputLen -= BUFFER_LENGTH;
                dPos += BUFFER_LENGTH;
                doUpdate();
            }
        }

        System.arraycopy(in, dPos, xBuf, xBufOff, inputLen);
        xBufOff += inputLen;
    }

    private void doUpdate() {
        byte[] B = new byte[BLOCK_LENGTH];
        for (int i = 0; i < BUFFER_LENGTH; i += BLOCK_LENGTH) {
            System.arraycopy(xBuf, i, B, 0, B.length);
            doHash(B);
        }
        xBufOff = 0;
    }

    private void doHash(byte[] B) {
        byte[] tmp = SM3.CF(V, B);
        System.arraycopy(tmp, 0, V, 0, V.length);
        cntBlock++;
    }

    private byte[] doFinal() {
        byte[] B = new byte[BLOCK_LENGTH];
        byte[] buffer = new byte[xBufOff];
        System.arraycopy(xBuf, 0, buffer, 0, buffer.length);
        byte[] tmp = SM3.padding(buffer, cntBlock);
        for (int i = 0; i < tmp.length; i += BLOCK_LENGTH) {
            System.arraycopy(tmp, i, B, 0, B.length);
            doHash(B);
        }
        return V;
    }

    public void update(byte in) {
        byte[] buffer = new byte[] { in };
        update(buffer, 0, 1);
    }

    public int getDigestSize() {
        return BYTE_LENGTH;
    }

    /**
     * 测试加密算法
     * @param args
     */
    public static void main(String[] args) {
        // 加密盐
        String salt = "{1#2$3%4(5)6@7!poeeww$3%4(5)djjkkldss}";

        String str = "wl@2021" + salt;

        byte[] msg2 = str.getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg2, 0, msg2.length);
        byte[] md2 = new byte[32];
        sm3.doFinal(md2, 0);
        String s2 = HexUtil.byte2HexStr(md2).toLowerCase();
        System.out.println("加密后的字符串msg2: " + s2);
    }

    /**
     * 国密3-加密字符串
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String encode(String str) {
        // 加密盐（公共）
        String salt = "{1#2$3%4(5)6@7!poeeww$3%4(5)djjkkldss}";
        str = str + salt;
        byte[] md = new byte[32];
        byte[] msg = str.getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg, 0, msg.length);
        sm3.doFinal(md, 0);
        return HexUtil.byte2HexStr(md).toLowerCase();
    }


    /**
     * 国密3-加密字符串
     * @param str 待加密字符串
     * @param salt 盐
     * @return 加密后的字符串
     */
    public static String encode(String str, String salt) {
        byte[] md = new byte[32];
        byte[] msg = (str + salt).getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg, 0, msg.length);
        sm3.doFinal(md, 0);
        return HexUtil.byte2HexStr(md).toLowerCase();
    }

}
