package springbootlearn.springbootlearn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import springbootlearn.springbootlearn.entity.ConfigJournalApp;
import springbootlearn.springbootlearn.repository.ConfigRepository;

@Component
public class AppCache {

    public enum Keys {

        WEATHER_API;
    }
    public   Map<String, String> APP_CACHE;
   
    
    
    @Autowired
    private ConfigRepository configRepository;

    @PostConstruct
    public void initialize() {
          APP_CACHE=new HashMap<>();
        List<ConfigJournalApp> allConfigs = configRepository.findAll();
        for (ConfigJournalApp config : allConfigs) {
            APP_CACHE.put(config.getKey(), config.getValue());
        }

    }
}
