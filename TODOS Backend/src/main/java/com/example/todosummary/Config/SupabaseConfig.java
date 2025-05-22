package com.example.todosummary.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "supabase")
public class SupabaseConfig {

    private String url;

  
    public String getUrl() {
        return url;
    }

   
    public void setUrl(String url) {
        this.url = url;
    }
}
