package com.test.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.test.vo.PttPushData;


public class PttGossipingTestCrawler extends HttpServlet {
	  protected void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
		
	     final String askUrl = "https://www.ptt.cc/ask/over18";
	     String gossipingUrl = null;
	     String prevPage = null;
//		儲存爬到的推文id、內容
	     List<PttPushData> dataList = new ArrayList<>();
	     
	     List<PttGossipingTestWithMultiThread> threadList = new ArrayList<>();
	     
	     String queryId = httpRequest.getParameter("queryId");
	     
	     gossipingUrl = "https://www.ptt.cc/bbs/Gossiping/index.html";

    	 try {
//			先通過檢查是否滿18歲之網頁    		 
			Connection.Response response=
			 			Jsoup.connect(askUrl)
						.userAgent("Mozilla").data("yes","yes").method(Method.POST).execute();
		
  	    	 String cookie = response.cookie("over18");

//	   	        抓出整個網頁
   	         Document doc = Jsoup.connect(gossipingUrl)
   	        		 .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
   	        		 .cookie("over18", cookie)
   	        		 .get();

//			抓出網頁文章標題
   	         Elements titleElements = doc.select(".title a");
   	         int titleSize = titleElements.size();
   	         
   	         for(int j=0;j<titleSize;j++){
   	             String title =titleElements.get(j).text();
//   	             System.out.println(j+"  "+title);
   	             
   	          String link = titleElements.get(j).attr("abs:href");
   	         }
//			抓出前一頁頁碼
   	         prevPage = doc.select(".action-bar .btn-group-paging > a")
   	        		 .get(1).attr("href")
   	        		 .replaceAll("/bbs/Gossiping/index([0-9]+).html", "$1");

    	 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i = Integer.parseInt(prevPage);i>Integer.parseInt(prevPage)-100;i--){
			System.out.println(i);
			
			PttGossipingTestWithMultiThread test = new PttGossipingTestWithMultiThread(new Integer(i).toString(),queryId);
			Thread thr = new Thread( test );
			thr.start();
			
			threadList.add(test);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}	   	    
		}

//		for(int j = 0;j<Integer.parseInt(prevPage);j++){
		for(int j = 0;j<100;j++){
			PttGossipingTestWithMultiThread test = threadList.get(j);
			List<PttPushData> list = test.getPttPushData();
			

			for(int k = 0;k<list.size();k++){
				PttPushData data = new PttPushData();
				data = list.get(k);
				dataList.add(data);
			}

		}

		for(int k = 0;k<dataList.size();k++){
			System.out.println("PushId:"+dataList.get(k).getPushId()+" PushContent:"+dataList.get(k).getPushContent());
		}

		String json = new Gson().toJson(dataList);
		PrintWriter out = null;
		
		httpResponse.setContentType("text/html; charset=UTF-8");
		httpResponse.setCharacterEncoding("UTF-8");

//		httpRequest.setAttribute("dataList", dataList);
		try {
			out = httpResponse.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(json);
		
//		RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/queryResult.jsp");
//		dispatcher.forward(httpRequest, httpResponse);
	}
}
