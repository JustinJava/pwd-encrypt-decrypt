package com.justin;

import com.justin.service.pass.IDecrypt;
import com.justin.service.pass.impl.Decrypt;

/**
 * @program: DataStructures
 * @description: 数据库密文解密测试类
 * @author: JustinQin
 * @create: 2021/7/18 20:07
 * @version: v1.0.0
 **/
public class DecryptTest {
    public static void main(String[] args) {
        String dePass = "127.0.0.1:root:C33D583B7575AF82FFDCE895C9F5E8FA:E5DBAC8F8FFF3EA5DC670221DAF820B3:DC9AB9C2382A04F2A479891CF9B411C9";
        IDecrypt decrypt = new Decrypt();
        String password = null;
        try {
            password = decrypt.getDepass(dePass);
        } catch (Exception e) {
            throw new RuntimeException("数据库密文解密失败：" + e.getMessage());
        }
        System.out.print(password);
    }
}
