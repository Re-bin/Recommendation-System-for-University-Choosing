package com.ursys.web;


import com.alibaba.fastjson.JSON;
import com.ursys.myClass.MajorToSchool;
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

@WebServlet("/majorToSchool")
public class MajorToSchoolServlet extends HttpServlet {
    private static final long serialVersionUID = 3L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MajorToSchoolServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<MajorToSchool> BuildSchoolList(String filePath) throws IOException {
        List<MajorToSchool> list = new ArrayList<>();
        // ��ȡ�ļ���ÿһ�е�����
        Vector<String> textList = Tool.readTxtFile(filePath);
        int ranking = 1;
        for (String text: textList) {
//            System.out.println(text);
            String[] inf = text.split(" ");
            String averageScore = inf[1].replace(".0", "");
            String maxScore = inf[2].replace(".0", "");
            list.add(new MajorToSchool(ranking++, inf[0], averageScore, maxScore, inf[3]));
        }

        return list;
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/json;charset=utf-8");

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String major = request.getParameter("majorname");
        String province = request.getParameter("province");
        String year = request.getParameter("year");
        String type = request.getParameter("type");
        String dataPath = "E:\\������ʵѵ\\Admissions\\Admissions\\data";
        String filePath = dataPath + "\\רҵ��ѧУ\\"
                + major + "\\" + province + "\\" + (("1".equals(type))?"���":"�Ŀ�") + "\\" + year + ".txt";

        System.out.printf("��ѯרҵ: %s ʡ��: %s ���: %s �����: %s\n",major, province, year, type);
        List<MajorToSchool> schoolList = BuildSchoolList(filePath);
        System.out.println(schoolList);
        String mapJson = JSON.toJSONString(schoolList);
        System.out.println(mapJson);
        response.setContentType("text/json;charset=utf-8");
        // ���ظ�ǰ��
        PrintWriter out = response.getWriter();
        out.write(mapJson);
    }

}
