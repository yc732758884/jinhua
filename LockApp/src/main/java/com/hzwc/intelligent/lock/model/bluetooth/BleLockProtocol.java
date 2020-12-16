package com.hzwc.intelligent.lock.model.bluetooth;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/6/25.
 */

public class BleLockProtocol {
    public static final byte BLELOCK_STATE_CLOSE = 0;
    public static final byte BLELOCK_STATE_OPEN = 1;
    public static final byte CLOSE_BLELOCK = 2;
    public static final byte COMMAND_AUTH_PWD = 65;
    public static final byte COMMAND_ENERGY = 32;
    public static final byte COMMAND_KEY_OPERATE_PWD = 80;
    public static final byte COMMAND_OPERATE_LOCK = 16;
    public static final byte COMMAND_SETTING_PWD_MODE = 96;
    public static final byte COMMAND_SET_PASSWORD = 64;
    public static final byte COMMAND_STATUS = 48;
    public static final byte OPEN_BLELOCK = 1;
    public static final byte REQUEST_ID = 85;
    public static final byte RESPOND_ID = -86;

  //  public static final byte[] sKey = {0x3A, 0x60, 0x44, 0x2A, 0x5C, 0x01, 0x21, 0x1F, 0x29, 0x1E, 0x0F, 0x4E, 0x0C, 0x13, 0x28, 0x25};;  //固定key值
   public static final byte[] sKey = {(byte) 0x87, 0x65, 0x78, 0x71, 0x67, 0x69, 0x50, 0x48, 0x49, 0x57, 0x66, (byte) 0x84, 0x76, 0x79, 0x67, 0x75};  //固定key值
    public static final byte[] sSrc = {0x06, 0x01, 0x01, 0x01, 0x2D, 0x1A, 0x68, 0x3D, 0x48, 0x27, 0x1A, 0x18, 0x31, 0x6E, 0x47, 0x1A};






    public static byte[] buildAuthPassword(int paramInt) {
        ByteBuffer localByteBuffer = ByteBuffer.allocate(6);
        localByteBuffer.put(REQUEST_ID);
        localByteBuffer.put(COMMAND_AUTH_PWD);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        localByteBuffer.put(hexStringToByteArray(String.format("%06x", arrayOfObject)));
        localByteBuffer.put(checkSum(localByteBuffer.array()));
        return localByteBuffer.array();
    }

    public static byte[] buildOperateBleLock(byte paramByte) {
        ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
        localByteBuffer.put(REQUEST_ID);
        localByteBuffer.put(COMMAND_OPERATE_LOCK);
        localByteBuffer.put(paramByte);
        localByteBuffer.put(checkSum(localByteBuffer.array()));
        return localByteBuffer.array();
    }

    public static String connectRightCommand(int var0) {
        ByteBuffer var1 = ByteBuffer.allocate(6);
        var1.put(RESPOND_ID);
        var1.put(COMMAND_AUTH_PWD);
        Object[] var4 = new Object[]{Integer.valueOf(var0)};
        var1.put(hexStringToByteArray(String.format("%06x", var4)));
        var1.put(checkSum(var1.array()));
        return Arrays.toString(var1.array());
    }

    public static String connectErrorCommand() {
        ByteBuffer var0 = ByteBuffer.allocate(3);
        var0.put(RESPOND_ID);
        var0.put(COMMAND_AUTH_PWD);
        var0.put((byte) -21);
        return Arrays.toString(var0.array());
    }

    private static byte checkSum(byte[] paramArrayOfByte) {
        byte i = 0;
        for (int j = 0; j < paramArrayOfByte.length; j++)
            i = (byte) (i ^ paramArrayOfByte[j]);
        return i;
    }

    public static byte[] hexStringToByteArray(String paramString) {
        if (paramString.length() < 2)
            paramString = "0" + paramString;
        int i = paramString.length();
        byte[] arrayOfByte = new byte[i / 2];
        for (int j = 0; j < i; j += 2)
            arrayOfByte[(j / 2)] = (byte) ((Character.digit(paramString.charAt(j), 16) << 4) + Character.digit(paramString.charAt(j + 1), 16));
        return arrayOfByte;
    }

    //数据加密
    public static byte[] Encrypt(byte[] sSrc, byte[] sKey) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc);
            return encrypted;
        } catch (Exception ex) {
            return null;
        }
    }

    //数据解密
    public static byte[] Decrypt(byte[] sSrc, byte[] sKey) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] dncrypted = cipher.doFinal(sSrc);
            return dncrypted;
        } catch (Exception ex) {
            return null;
        }
    }

    //格式化开锁字节
    public static byte[] sendUnlock(byte[] b,byte[] sKey) {
        //前三位为固定字节 中间6位为密码 后四位 锁发送的token 后三位 无效字节
        byte[] bytes = {0x05, 0x01, 0x06, '6', '6', '6', '6', '6', '6', b[3], b[4], b[5], b[6], 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }

    //格式化开锁字节
    public static byte[] sendUnlock(byte[] b,String password,byte[] sKey) {
        //前三位为固定字节 中间6位为密码 后四位 锁发送的token 后三位 无效字节
        byte[] pwd = getBytes(password.toCharArray());
        byte[] bytes = {0x05, 0x01, 0x06, pwd[0], pwd[1], pwd[2], pwd[3], pwd[4], pwd[5], b[3], b[4], b[5], b[6], 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }

    private static byte[] getBytes (char[] chars) {
        Charset cs = Charset.forName ("ASCII");
        CharBuffer cb = CharBuffer.allocate (chars.length);
        cb.put (chars);
        cb.flip ();
        ByteBuffer bb = cs.encode (cb);
        return bb.array();
    }

    //格式化获取电量
    public static byte[] electric(byte[] b,byte[] sKey) {
        byte[] bytes = {0x02, 0x01, 0x01, 0x01, b[3], b[4], b[5], b[6], 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }

  //  格式化复位通信帧
    public static byte[]  resetCloseLock(byte[] b,byte[] sKey) {
        byte[] bytes = {0x05, 0x0C, 0x01, 0x01, b[3], b[4], b[5], b[6], 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }


    public static   byte[]   SendOldPwd(byte[] token,String  pass,byte[] sKey) {
        byte[] pwd = getBytes(pass.toCharArray());
        byte[] bytes = {0x05, 0x03, 0x06, pwd[0], pwd[1], pwd[2], pwd[3], pwd[4],pwd[5], token[3], token[4], token[5], token[6], 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }

    public   static   byte[]  sendNewPwd(byte[] token,String  pass,byte[] sKey){

        byte[] pwd = getBytes(pass.toCharArray());
        byte[] bytes = {0x05, 0x04, 0x06, pwd[0], pwd[1], pwd[2], pwd[3], pwd[4],pwd[5], token[3], token[4], token[5], token[6], 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }


    public   static   byte[]  sendState(byte[] token,byte[] sKey){


        byte[] bytes = {0x05, 0x0E, 0x01, 0x01, token[3], token[4], token[5], token[6],0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return Encrypt(bytes, sKey);
    }



}
