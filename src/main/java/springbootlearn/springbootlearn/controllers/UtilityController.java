package springbootlearn.springbootlearn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootlearn.springbootlearn.cache.AppCache;


@RestController
@RequestMapping("/api/v1/utility")
public class UtilityController {

    @Autowired
    AppCache appCache;

    @GetMapping("/clear-cache")
    public void clearAppCache() {
        appCache.initialize();
    }
}
