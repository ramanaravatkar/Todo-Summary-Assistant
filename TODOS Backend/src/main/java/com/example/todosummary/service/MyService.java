package com.example.todosummary.service;

import org.springframework.stereotype.Service;

import com.example.todosummary.Config.OpenAIConfig;
import com.example.todosummary.Config.SlackConfig;
import com.example.todosummary.Config.SupabaseConfig;

@Service
public class MyService {

    private final SupabaseConfig supabaseConfig;
    private final OpenAIConfig openAIConfig;
    private final SlackConfig slackConfig;

    public MyService(SupabaseConfig supabaseConfig, OpenAIConfig openAIConfig, SlackConfig slackConfig) {
        this.supabaseConfig = supabaseConfig;
        this.openAIConfig = openAIConfig;
        this.slackConfig = slackConfig;
    }

    public void printConfigs() {
        System.out.println("Supabase URL: " + supabaseConfig.getUrl());
        System.out.println("OpenAI API Key: " + openAIConfig.getKey());
        System.out.println("Slack Webhook URL: " + slackConfig.getUrl());
    }
}

