package com.ursys.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ursys.myClass.ScorelineYearScore;

import com.ursys.tool.Tool;

/**
 * Servlet implementation class SalaryServlet
 */
@WebServlet("/scoreline")
public class ScoreLineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	// ������������
	public static void AppendSub(List<ScorelineYearScore> list, int province, String filepath, String type){
		filepath = filepath + "\\" + type + ".txt";
		Vector<String> fileContent = Tool.readTxtFile(filepath);
		for(int i = 0; i < fileContent.size(); i ++){
			String line = fileContent.get(i);
			String[] words = line.split(" ");
			String Year = words[0];
			String firstNum = words[1].replace(".0", "");
			String secondNum = words[2].replace(";", "").replace(".0", "");
			int intType = ("���".equals(type))? 1:2;
			if(!firstNum.equals("-")){
				int Score = Integer.parseInt(firstNum);
				list.add(new ScorelineYearScore(Integer.parseInt(Year), Score, province, intType));
			}else if(!secondNum.equals("-")){
				int Score = Integer.parseInt(secondNum);
				list.add(new ScorelineYearScore(Integer.parseInt(Year), Score, province, intType));
			}
		}
	}

	// ��ÿ��ʡ���������
	public static List<ScorelineYearScore> BuildScoreList (String filePath){
		List<ScorelineYearScore> scoreList = new ArrayList<ScorelineYearScore>();
		for(int i = 1; i <= 33; i ++){
			String provinceFilePath = filePath + "\\" + i;
			File file = new File(provinceFilePath);
			if(file.exists()){
				// ������
				AppendSub(scoreList, i, provinceFilePath, "���");
				// ����Ŀ�
				AppendSub(scoreList, i, provinceFilePath, "�Ŀ�");
			}else {
//				System.out.println(provinceFilePath);
			}
		}
		return scoreList;
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreLineServlet() {
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
		String filePath = dataPath + "\\ѧУ������\\" + param;
//		System.out.println(filePath);
		List<ScorelineYearScore> scoreList = BuildScoreList(filePath);
		System.out.println(scoreList.size());
		String mapJson = JSON.toJSONString(scoreList);
		System.out.println(mapJson);

		//
		PrintWriter out = response.getWriter();
		out.write(mapJson);
	}
}

