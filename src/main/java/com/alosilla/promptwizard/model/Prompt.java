package com.alosilla.promptwizard.model;

public class Prompt {
    private String title;
    private String description;
    private String category;
    private String content;

    public Prompt(String title, String description, String category, String content) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return title + " - " + category;
    }
}

