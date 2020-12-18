package com.ursys.web;

import com.alibaba.fastjson.JSON;
import com.ursys.myClass.MajorToSchool;
import com.ursys.myClass.SchoolToMajor;
import com.ursys.myClass.ScorelineYearScore;
import com.ursys.tool.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@WebServlet("/schoolToMajor")
public class SchoolToMajorServlet extends HttpServlet{
    private static final long serialVersionUID = 2L;

    // ����ļ��㣬һ���ļ��������е�רҵ��Ϣ
    private static List<SchoolToMajor.Major> AppendYear(List<SchoolToMajor> list, int province, int type, String subFilepath, int year){
        String yearFilepath = subFilepath + "\\" + year + ".txt";
        File file = new File(yearFilepath);
        List<SchoolToMajor.Major> majorList = null;
        if (file.exists()) {
            // �ļ����ڣ�����רҵ�б�
            majorList = new ArrayList<SchoolToMajor.Major>();
            // ��ȡ���ļ��е���������
            Vector<String> fileContent = Tool.readTxtFile(yearFilepath);
            int ranking = 1;
            for (String text: fileContent){
                // ��ȡ������Ϣ
                String[] inf = text.split(" ");
                String majorName = inf[0];
                String averageScore = inf[1].replace(".0", "");
                String maxScore = inf[2].replace(".0", "");
                // ��רҵ�б������Ԫ��
                majorList.add(new SchoolToMajor.Major(ranking++, majorName, averageScore, maxScore));
            }
        }
        return majorList;
    }

    // ����Ʋ㣬�����������ݣ�����SchoolToMajor�б�
    private static void AppendSub(List<SchoolToMajor> list, int province, String provinceFilepath, String type){
        String subFilepath = provinceFilepath + "\\" + type;
        File file = new File(subFilepath);
        // ����Ϊ�˹���SchoolToMajor����
        int intType = ("���".equals(type))? 1:2;
        if (file.exists()){
            for (int year = 2015; year < 2018; year++) {
                // רҵ��Ϣ�б�
                List<SchoolToMajor.Major> majorList = AppendYear(list, province, intType, subFilepath, year);
                if (majorList != null) // ˵���ļ�����
                    list.add(new SchoolToMajor(year, province, intType, majorList));
            }
        }

    }

    // ʡ�ݲ㴫��ȥ��һ���·������һ����ܵ�·�����
    private List<SchoolToMajor> BuildSchoolList(String filePath) throws IOException {
        List<SchoolToMajor> list = new ArrayList<>();

        for(int i = 1; i <= 33; i ++){
            String provinceFilePath = filePath + "\\" + i;
            File file = new File(provinceFilePath);
            if(file.exists()){
                // ������
                AppendSub(list, i, provinceFilePath, "���");
                // ����Ŀ�
                AppendSub(list, i, provinceFilePath, "�Ŀ�");
            }else {
                System.out.println(provinceFilePath);
            }
        }

        return list;
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchoolToMajorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // ��ȡ����
        String param = request.getParameter("schoolname");

        // ת����JSON
        System.out.println("��ѯѧУ: " + param);
        String dataPath = "E:\\������ʵѵ\\Admissions\\Admissions\\data";
        String filePath = dataPath + "\\ѧУ��רҵ\\" + param;
        System.out.println(filePath);
        List<SchoolToMajor> scoreList = BuildSchoolList(filePath);
        System.out.println(scoreList.size());
        String mapJson = JSON.toJSONString(scoreList);
//		System.out.println(mapJson);

        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(mapJson);
    }
}
