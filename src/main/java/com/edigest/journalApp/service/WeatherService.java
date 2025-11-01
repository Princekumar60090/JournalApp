package com.edigest.journalApp.service;

import com.edigest.journalApp.api.response.WeatherResponse;
import com.edigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
  //  private static  String apiKey="a006cb2c65079f840abb2f7a264e6bf6";
   // private static final String API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate   restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String  city){

     WeatherResponse weatherResponse =   redisService.get("weather_of_"+city,WeatherResponse.class);

     if(weatherResponse!=null){
         return weatherResponse;
     }
     else{
         String finalAPI = appCache.APP_CACHE.get("weather_api").replace("<city>",city).replace("<apiKey>",apiKey);
         ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
         WeatherResponse body = response.getBody();
         if(body!=null){
             redisService.set("weather_of_"+city,body,300l);
         }
        return  body;
     }

//       String finalAPI = API.replace("CITY",city).replace("API_KEY",apiKey);
//
//
//          POST
////       HttpHeaders httpHeaders =new HttpHeaders();
////       httpHeaders.set("key","value");
////       User user = (User) User.builder().username("prince").password("vipul").build();
////       HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeaders);
////       ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity , WeatherResponse.class);
//
//            GET
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
//        WeatherResponse  body = response.getBody();
 //      return body;



        //FROM MONGODB(APp cache)

//        String finalAPI=appCache.get(AppCache.ge).("weather_api").replace("<city>",city).replace("<apiKey>",apiKey);


    }

}
