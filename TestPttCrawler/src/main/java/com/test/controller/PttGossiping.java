package com.test.controller;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PttGossiping {
     public static void main(String args[]){     
    
         //這個就是部落格中的java反射的url
	     final String askUrl = "https://www.ptt.cc/ask/over18";
	     String gossipingUrl = null;
	     String prevPage = null;
	     int i=1;

    	 while(true){
    	     if(i==1){
    	    	 gossipingUrl = "https://www.ptt.cc/bbs/Gossiping/index.html"; 
    	     }	       
    		 else{
    			 gossipingUrl = "https://www.ptt.cc/bbs/Gossiping/index"+prevPage+".html";
    		 }
    		 
    		 
    	     System.out.println(i);
    	     try {
    	    	 Connection.Response response=
    	    			 			Jsoup.connect(askUrl)
    				 				.userAgent("Mozilla").data("yes","yes").method(Method.POST).execute();
    	    	 
    	    	 String cookie = response.cookie("over18");
    	        //抓出整個網頁
    	         Document doc = Jsoup.connect(gossipingUrl)
    	        		 .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
    	        		 .cookie("over18", cookie)
    	        		 .get();
    	
//				抓出網頁文章標題
    	         Elements titleElements = doc.select(".title a");
    	         int titleSize = titleElements.size();
    	         
    	         for(int j=0;j<titleSize;j++){
    	             String title =titleElements.get(j).text();
    	             System.out.println(j+"  "+title);
//					抓出網頁文章連結
    	             String link = titleElements.get(j).attr("abs:href");
//					抓出網頁文章
//    	             Document linkDoc = Jsoup.connect(link)
//    	            		 .userAgent("Mozilla")
//        	        		 .cookie("over18", cookie)
//    	            		 .get();
//					 抓出文章內部推文者、推文
//    	             Elements pushIdElements = linkDoc.select("#main-content .push .push-userid");
//    	             Elements pushContnetElements = linkDoc.select("#main-content .push .push-content");
//    	             int pushIdSize = linkDoc.select("#main-content .push .push-userid").size();
//    	             
//    	             for(int k=0;k<pushIdSize;k++){
//    	            	 String pushId =pushIdElements.get(k).text();
//    	            	 if("bicedb".equals(pushId)){        		 
//            	             String pushContent =pushContnetElements.get(k).text();
//            	             System.out.println(k+" "+pushId+"  "+pushContent);    	            		 
//    	            	 }
//    	             }
//    	             
    	         }
//				抓出前一頁頁碼
    	         prevPage = doc.select(".action-bar .btn-group-paging > a")
    	        		 .get(1).attr("href")
    	        		 .replaceAll("/bbs/Gossiping/index([0-9]+).html", "$1");
    	         
    	         System.out.println("prevPage="+prevPage);

//             System.out.println(doc);
           
            } catch (org.jsoup.HttpStatusException e){
            	break;
            } catch(IOException e) {
                e.printStackTrace();
            }
    	    i++;
    	 }

 }
}