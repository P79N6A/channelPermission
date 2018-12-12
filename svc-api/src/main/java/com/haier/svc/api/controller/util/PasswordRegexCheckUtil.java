package com.haier.svc.api.controller.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码正则检查类,至少8位,包括大小写字母和数字的组合
 *                       
 * @Filename: PasswordRegexCheckUtil.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class PasswordRegexCheckUtil {
    public final static int PASSWORD_LENGTH = 8; // 固定密码长度

    /**
     * 密码有效性校验
     * @param password
     * @return
     */
    public static boolean isPasswordCheckOK(String password) {
        if (password.length() < PASSWORD_LENGTH) {
            return false;
        }
        if (password.matches("\\w+")) {
            Pattern p1 = Pattern.compile("[a-z]+");
            Pattern p2 = Pattern.compile("[A-Z]+");
            Pattern p3 = Pattern.compile("[0-9]+");
            Matcher m = p1.matcher(password);
            if (!m.find())
                return false;
            else {
                m.reset().usePattern(p2);
                if (!m.find())
                    return false;
                else {
                    m.reset().usePattern(p3);
                    if (!m.find())
                        return false;
                    else {
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 生成随机密码
     * @return
     */
    public static String getRandomPassword() {
        char[] r = getChar();
        Random rr = new Random();
        char[] pw = new char[PASSWORD_LENGTH];
        StringBuilder pwd = new StringBuilder();
        for (int i = 0; i < pw.length; i++) {
            if (i == 0) { //   首字母大写
                int num = randomRangeNum(0, 25);
                pw[i] = r[num];
                pwd.append(pw[i]);
            } else if (i == 1) { // 第二个字符小写
                int num = randomRangeNum(26, 51);
                pw[i] = r[num];
                pwd.append(pw[i]);
            } else if (i == 2) { // 第三个字符为数字
                int num = randomRangeNum(52, 61);
                pw[i] = r[num];
                pwd.append(pw[i]);
            } else { // 其他位置字符串随机生成
                int num = rr.nextInt(62);
                pw[i] = r[num];
                pwd.append(pw[i]);
            }
        }
        return pwd.toString();
    }

    public static int randomRangeNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 密码的组成元素字符
     * @return
     */
    public static char[] getChar() {
        char[] passwordLit = new char[62];
        char fword = 'A';
        char mword = 'a';
        char bword = '0';
        for (int i = 0; i < 62; i++) {
            if (i < 26) { //[A-Z]
                passwordLit[i] = fword;
                fword++;
            } else if (i < 52) {//[a-z]
                passwordLit[i] = mword;
                mword++;
            } else {//[0-9]
                passwordLit[i] = bword;
                bword++;
            }
        }
        return passwordLit;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 999999; i++) {
            String password = getRandomPassword();
            boolean isOk = isPasswordCheckOK(password);
            System.out.println("索引号：" + i + "生成密码：" + password + " 校验：" + isOk);
        }
    }
}
