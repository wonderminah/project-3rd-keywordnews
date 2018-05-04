package com.androidserver.www;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.androidserver.www.vo.News;

@Controller
public class RSSReader {

	//RSS
    @ResponseBody
    @RequestMapping(value = "readingRSS", method = RequestMethod.GET) // TODO : GET을 POST로 바꿔야 할 수도.
    public ArrayList<News> readingRSS(String page) {
//    	System.out.println("route log: KeywordController > readingRSS > page: " + page); // TODO : LOG
    	
    	//URL connect
    	String html = null;
    	try {
            URL url = new URL(page);
            HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
            
            if(urlConnection == null) return null;
            urlConnection.setConnectTimeout(10000); //최대 10초 대기
            urlConnection.setUseCaches(false); 		//매번 서버에서 읽어오기
            StringBuilder sb = new StringBuilder(); //고속 문자열 결합체

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");

                //한줄씩 읽기
                BufferedReader br = new BufferedReader(isr);
                while(true) {
                    String line = br.readLine(); //웹페이지의 html 코드 읽어오기
                    if (line == null) break; 	 //스트림이 끝나면 null리턴
                    sb.append(line+"\n");
                } //end while
                br.close();
            } //end if
            html = sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("route log: KeywordController > readingRss > html: " + html); // TODO : LOG

        //XML parsing
        ArrayList<News> newsArrayList = new ArrayList<>();
        try {
            //DOM 파싱.
            ByteArrayInputStream bai = new ByteArrayInputStream(html.getBytes(Charset.forName("UTF-8")));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document parse = builder.parse(bai); //DOM 파서

            //<item>들을 datas(NodeList)에 담기.
            NodeList datas = parse.getElementsByTagName("item");
//            System.out.println("route log: KeywordController > readingRss > datas.getLength(): " + datas.getLength()); // TODO : LOG

            //부모태그 (<item>)
            for (int idx = 0; idx < datas.getLength(); idx++) {
            	System.out.println("route log: KeywordController > readingRss > idx: " + idx); // TODO : LOG
            	
                String title = "";
                String link = "";
                String description = "";
                String category = "";
                String imgSrc = "";
                String pubDate = "";
                String author = "";
                Node node = datas.item(idx);
                int childLength = node.getChildNodes().getLength();
//                System.out.println("route log: childLength: " + childLength); // TODO : LOG

                //자식태그 (<title>, <link>, <description> ...)
                NodeList childNodes = node.getChildNodes();
                for (int childIdx = 0; childIdx < childLength; childIdx++) {
                    Node childNode = childNodes.item(childIdx);
//                    System.out.println("route log: childNode" + childIdx + ": " + childNode.getNodeName()); // TODO : LOG
                    if(childNode.getNodeType() == Node.ELEMENT_NODE){
                        String tag = childNode.getNodeName();
                        switch (tag) {
                            case "title": title = childNode.getFirstChild().getNodeValue(); break;
                            case "link": link = childNode.getFirstChild().getNodeValue(); break;
                            case "description": description = childNode.getFirstChild().getNodeValue(); break;
                            case "category": if (category.equals("")) {category = childNode.getFirstChild().getNodeValue();} break;
//                            case "enclosure": imgSrc = childNode.getAttributes().getNamedItem("url").getNodeValue(); break;
                            case "pubDate": pubDate = childNode.getFirstChild().getNodeValue(); break;
//                            case "author": author = childNode.getFirstChild().getNodeValue(); break;
                        } //switch
                    } //if
                } //inner for 

                //뉴스 객체 세팅
                News news = new News();
                news.setTitle(title);
                news.setLink(link);
                news.setPubDate(pubDate);
//                news.setAuthor(author);
                news.setCategory(category);
                news.setDescription(description);
//                news.setImgSrc(imgSrc);
                newsArrayList.add(news);
//                System.out.println("route log: KeywordController > readingRss > news.toString(): " + "\n" + news.toString()); // TODO : LOG
            } //outer for
        }
        catch (Exception e) {
            System.out.println("route log: KeywordController > readingRss > XMLParsing catch");
            e.printStackTrace();
        }
        return newsArrayList;
    }
}
