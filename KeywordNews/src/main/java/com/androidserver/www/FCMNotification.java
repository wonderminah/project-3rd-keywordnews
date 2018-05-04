package com.androidserver.www;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.androidserver.www.dao.KeywordDAO;
import com.androidserver.www.vo.Keyword;
import com.androidserver.www.vo.News;
import net.sf.json.JSONObject;

@Controller
public class FCMNotification {

	@Autowired
	static
	KeywordDAO dao;
	
    //Method to send Notifications from server to client end.
    public final static String AUTH_KEY_FCM = "AAAACPjSbHs:APA91bE6PruzpKePwPmTy5yydvEKzCZvHFpZCZcnw4X7Xo-QhWWXTCaCy0KsRJ2DUppikEDM-n30SmzxvWJ1IS7YTqlGWy-h4muhuNiVgNhIjpPL6JvEpXeCU-PEY3gKS51wCZVeF0dq";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    
    //키워드 실시간 푸시알림
    @ResponseBody
    @RequestMapping(value = "pushFCMNotification", method = RequestMethod.POST)
    public static void pushFCMNotification(String ppid, String refreshedToken) throws Exception {
    	System.out.println("pushFCMNotification > ppid: " + ppid + " refreshedToken: " + refreshedToken);
    
    	new Thread() {
    		public void run() {
    			while (true){
    				//전체 기사에서 RSS
    				ArrayList<News> newsList = new ArrayList<>();
    				newsList = new RSSReader().readingRSS("http://api.sbs.co.kr/xml/news/rss.jsp?pmDiv=ranking"); // TODO : 정치로 임시값 설정. 원래는 전체 카테고리.
    				System.out.println(newsList.size());
    				
    				//현재시각 구하기
    				long currentTime = System.currentTimeMillis(); 
    				SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
    				String time = dayTime.format(new Date(currentTime));
    				System.out.println("pushFCMNotification > time: " + time);
    				
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                    	e.printStackTrace();
                    }
                    
                    if (time.equals("08:00:00")) { // TODO : 시간 변경. 아침 8시 및 저녁 8시.
            	        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
            	        String FMCurl = API_URL_FCM;
            	
            	        try {
	            	        URL url = new URL(FMCurl);
	            	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            	
	            	        conn.setUseCaches(false);
	            	        conn.setDoInput(true);
	            	        conn.setDoOutput(true);
	            	        conn.setRequestMethod("POST");
	            	        conn.setRequestProperty("Authorization", "key=" + authKey);
	            	        conn.setRequestProperty("Content-Type", "application/json");
	            	
	            	        JSONObject data = new JSONObject();
	            	        data.put("to", refreshedToken.trim());
	            	        JSONObject info = new JSONObject();
	            	        info.put("title", "KeywordNews"); // Notification title
	            	        info.put("body", newsList.get(0).getTitle()); // Notification body
	            	        data.put("notification", info); //data를 임시로 notification으로 고침
	            	
	            	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	            	        wr.write(data.toString());
	            	        wr.flush();
	            	        wr.close();
	            	
	            	        int responseCode = conn.getResponseCode();
	            	        System.out.println("Response Code : " + responseCode);
	            	
	            	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            	        String inputLine;
	            	        StringBuffer response = new StringBuffer();
	            	
	            	        while ((inputLine = in.readLine()) != null) {
	            	            response.append(inputLine);
	            	        }
	            	        in.close();
            	        }
            	        catch (Exception e) {
            	        	e.printStackTrace();
            	        }
            		}
                }
    		}
    	}.start();
    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        FCMNotification obj = new FCMNotification();
        obj.pushFCMNotification(null, "USER_DEVICE_TOKEN");
    }
}
