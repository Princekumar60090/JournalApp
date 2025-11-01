package com.edigest.journalApp.cache;

import com.edigest.journalApp.entity.ConfigJournalAppEntity;
import com.edigest.journalApp.repository.ConfigJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
   @Autowired
    private ConfigJournalRepository configJournalRepository;

    public Map<String,String> APP_CACHE = new HashMap<>();


    @PostConstruct
    public void init(){

      List<ConfigJournalAppEntity> all = configJournalRepository.findAll();

      all.forEach(configJournalAppEntity -> {
          APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
      });

    }
}
