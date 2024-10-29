package com.alosilla.promptwizard.service;

import com.alosilla.promptwizard.model.Prompt;
import com.alosilla.promptwizard.repository.PromptRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PromptSearchService {
    private final PromptRepository promptRepository;

    public PromptSearchService() {
        this.promptRepository = PromptRepository.getInstance();
    }

    public List<Prompt> searchPrompts(String query) {
        return promptRepository.getAllPrompts().stream()
                .filter(prompt -> prompt.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        prompt.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        prompt.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Método para obtener todos los prompts
    public List<Prompt> getAllPrompts() {
        return promptRepository.getAllPrompts(); // Retorna todos los prompts
    }

    // Método para agregar un prompt a favoritos
    public void addToFavorites(Prompt prompt) {
        promptRepository.addToFavorites(prompt); // Llama al método en el repositorio
    }
}
