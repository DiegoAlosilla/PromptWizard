package com.alosilla.promptwizard.repository;

import com.alosilla.promptwizard.model.Prompt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PromptRepository {
    private static PromptRepository instance;
    private List<Prompt> prompts;
    private List<Prompt> favoritePrompts;

    private PromptRepository() {
        prompts = new ArrayList<>();
        loadPromptsFromFile("D:/Prompts/prompts.txt"); // Cambiar al archivo local real
    }

    public synchronized static PromptRepository getInstance() {
        if (instance == null) {
            instance = new PromptRepository();
        }
        return instance;
    }

    private void loadPromptsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    prompts.add(new Prompt(parts[0], parts[1], parts[2], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Prompt> getAllPrompts() {
        return prompts;
    }

    public void addToFavorites(Prompt prompt) {
        if (!favoritePrompts.contains(prompt)) {
            favoritePrompts.add(prompt);
        }
    }

    public List<Prompt> getFavoritePrompts() {
        return new ArrayList<>(favoritePrompts);
    }


    public void addPrompt(Prompt prompt) {
        prompts.add(prompt);
    }
}
