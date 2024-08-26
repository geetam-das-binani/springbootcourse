package springbootlearn.springbootlearn.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import springbootlearn.springbootlearn.cache.AppCache;
import springbootlearn.springbootlearn.placeholders.Constants;
import springbootlearn.springbootlearn.apiResponse.WeatherResponse;

@Service
@Component
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key}")
    private String APIKEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeatherResponse(String city) {

        try {
            // Attempt to get the weather data from the cache
            WeatherResponse weatherResponse = redisService.get( city, WeatherResponse.class);
            if (weatherResponse != null) {
                return weatherResponse;
            } else {
                // If not in cache, construct the API URL
                String finalApi = appCache.APP_CACHE.get(AppCache.Keys.WEATHER_API.toString())
                    .replace(Constants.City, city)
                    .replace("<apikey>", "79487e10b9a362eec78787a28e30f6e5");
    
                // Make the API call
                ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
                logger.info(response.getBody().toString());
     
                if (response.getStatusCode() == HttpStatus.OK) {
                    WeatherResponse body = response.getBody();
                    logger.info(body.getWeather()[0].getDescription());
                    if (body != null) {
                        logger.info(body.getWeather()[0].getDescription(),"inside");
                        // Cache the response for future requests
                        redisService.set(city, body, 300L);
                        return body;
                    } else {
                        logger.error("Received empty body from weather API for city: " + city);
                        throw new RuntimeException("Received empty body from weather API");
                    }
                } else {
                    logger.error("Failed to fetch weather data, HTTP status: " + response.getStatusCode());
                    throw new RuntimeException("Failed to fetch weather data");
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching weather data for city: " + city, e);
            throw new RuntimeException("Error occurred while fetching weather data" + e.getMessage());
        }

    }

}
