package com.ursys.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class Tool {
    // ���ڻ�ȡ�ļ�ÿһ�е����ݣ�����һ���б�
    public static Vector<String> readTxtFile(String filePath) {
        Vector<String> fileContent = new Vector<String>();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    fileContent.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("�ļ���ʧ��");
            }
        } catch (Exception e) {
            System.out.println("�ļ���ȡʧ��");
            e.printStackTrace();
        }
        return fileContent;
    }
}
