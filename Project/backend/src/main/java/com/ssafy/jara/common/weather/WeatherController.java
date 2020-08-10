package com.ssafy.jara.common.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

	@GetMapping("")
	public String getWeather() throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst"); /*URL*/
//		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=서비스키"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("DysdYWgqnU55g9Q7PoDso1H3%2BQJFLffwk4YkWWCS4cSluMp9qKnSyq0J0u1PXFITKErf1yqWK%2FrwEUKlHTVePw%3D%3D", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + "DysdYWgqnU55g9Q7PoDso1H3%2BQJFLffwk4YkWWCS4cSluMp9qKnSyq0J0u1PXFITKErf1yqWK%2FrwEUKlHTVePw%3D%3D"); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("40", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        
        Calendar cal = Calendar.getInstance(Locale.KOREA);
//        System.out.println(cal);
//        System.out.println(cal.get(cal.YEAR));
//        System.out.println(cal.get(cal.MONTH) + 1);
//        System.out.println(cal.get(cal.DATE));
//        System.out.println(cal.get(cal.HOUR_OF_DAY));
        
        String year = String.valueOf(cal.get(cal.YEAR));
        String month = String.valueOf(cal.get(cal.MONTH) + 1);
        String day = String.valueOf(cal.get(cal.DATE));
//        String time = String.valueOf(cal.get(cal.HOUR_OF_DAY)); 
        
//        System.out.println(year + ((month.length() < 2)? "0" + month : month) + day);
//        System.out.println((time.length() < 2 ? "0" + time : time) + "00");
        
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(year + ((month.length() < 2)? "0" + month : month) + day, "UTF-8")); /*현재날짜(20200810) 발표*/
//        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode((time.length() < 2 ? "0" + time : time) + "00", "UTF-8")); /*현재시각 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1750", "UTF-8")); /*현재시각 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("61", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("129", "UTF-8")); /*예보지점 Y 좌표*/
       
        System.out.println(urlBuilder.toString());
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        return sb.toString();
	}
}
