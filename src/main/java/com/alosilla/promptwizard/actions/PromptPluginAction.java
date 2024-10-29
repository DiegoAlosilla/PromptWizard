package com.alosilla.promptwizard.actions;

import com.alosilla.promptwizard.service.PromptSearchService;
import com.alosilla.promptwizard.ui.PromptDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class PromptPluginAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PromptDialog dialog = new PromptDialog();
        dialog.show();

        if (dialog.isOK()) {
            var selectedPrompt = dialog.getSelectedPrompt();
            if (selectedPrompt != null) {
                // Aquí se puede integrar el prompt con Copilot o mostrarlo en el editor
                Messages.showMessageDialog(event.getProject(),
                        "Selected Prompt:\n" + selectedPrompt.getDescription(),
                        "Prompt Selected", Messages.getInformationIcon());
            }
        }
    }
}
