package com.haier.stock.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFile {

    public static String read(String path) {
        String content = "";
        FileInputStream fileinputstream =null;
        try {
            fileinputstream = new FileInputStream(path);//读取模块文件
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];
            fileinputstream.read(bytes);
            content = new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        		try {
					fileinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
        return content;
    }

    //指定编码读取文件
    public static String read(String path, String encode) {
        String content = "";
        String line = "";
        StringBuffer buffer = new StringBuffer();
        InputStream inputstream=null;
        BufferedReader reader=null;
        try {
            inputstream = new FileInputStream(path);//读取模块文件
            reader = new BufferedReader(new InputStreamReader(inputstream, encode));
            line = reader.readLine(); //  读取第一行 
            while (line != null) { //  如果  line  为空说明读完了 
                buffer.append(line); //  将读到的内容添加到  buffer  中 
                buffer.append("\n"); //  添加换行符 
                line = reader.readLine(); //  读取下一行 
            }
            content = buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        		try {
        			if(reader!=null){
					    reader.close();
        			}
        			if(inputstream!=null){
                		inputstream.close();
                	}
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
        return content;
    }
}
