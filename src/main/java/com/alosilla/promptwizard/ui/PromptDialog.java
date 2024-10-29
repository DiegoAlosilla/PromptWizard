package com.alosilla.promptwizard.ui;

import com.alosilla.promptwizard.model.Prompt;
import com.alosilla.promptwizard.service.PromptSearchService;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PromptDialog extends DialogWrapper {
    private final PromptSearchService searchService;
    private JTextField searchField;
    private JList<Prompt> resultList;
    private DefaultListModel<Prompt> listModel;
    private JTextArea previewArea;

    private JLabel lblSearch;

    public PromptDialog() {
        super(true);
        setTitle("Prompt Wizard BCP");
        searchService = new PromptSearchService();
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        JPanel body = new JPanel(new BorderLayout());
        JPanel foot = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Logo redimensionado
        ImageIcon originalIcon = new ImageIcon("D:/Prompts/bcp.png"); // Cambia la ruta al logo
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        header.add(logoLabel, BorderLayout.NORTH);

        lblSearch = new JLabel();
        lblSearch.setText("Buscar: ");
        header.add(lblSearch, BorderLayout.WEST);

        // Campo de búsqueda
        searchField = new JTextField();
        searchField.addActionListener(e -> searchPrompts());
        header.add(searchField, BorderLayout.CENTER); // Agregar al norte del panel

        // Botón para limpiar
        JButton cleanButton = new JButton("Limpiar");
        cleanButton.addActionListener(e -> loadAllPrompts());
        header.add(cleanButton,BorderLayout.EAST);


        // Lista de resultados
        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.addListSelectionListener(e -> updatePreview()); // Actualizar vista previa al seleccionar
        body.add(new JScrollPane(resultList), BorderLayout.WEST); // Agrega la lista al panel

        // Area de vista previa
        previewArea = new JTextArea(10, 30); // Ajusta el tamaño según sea necesario
        previewArea.setEditable(false);
        previewArea.setLineWrap(true); // Habilitar ajuste de línea
        previewArea.setWrapStyleWord(true); // Ajustar por palabra
        JScrollPane previewScroll = new JScrollPane(previewArea);
        body.add(previewScroll, BorderLayout.EAST); // Agregar el área de vista previa al panel principal

        // Botón para agregar a favoritos
        JButton favoriteButton = new JButton("Agregar Favoritos");
        favoriteButton.addActionListener(e -> addToFavorites());
        foot.add(favoriteButton);

        JButton useButton = new JButton("Usar");
        useButton.addActionListener(e -> addToFavorites());
        foot.add(useButton);

        panel.add(header, BorderLayout.NORTH);
        panel.add(body, BorderLayout.CENTER);
        panel.add(foot, BorderLayout.SOUTH);

        // Cargar todos los prompts por defecto
        loadAllPrompts();

        return panel;
    }

    private void loadAllPrompts() {
        List<Prompt> allPrompts = searchService.getAllPrompts();
        listModel.clear();
        for (Prompt prompt : allPrompts) {
            listModel.addElement(prompt);
        }
    }

    private void searchPrompts() {
        String query = searchField.getText();
        List<Prompt> results = searchService.searchPrompts(query);
        listModel.clear();
        for (Prompt prompt : results) {
            listModel.addElement(prompt);
        }
    }

    private void updatePreview() {
        Prompt selectedPrompt = resultList.getSelectedValue();
        if (selectedPrompt != null) {
            previewArea.setText(selectedPrompt.getDescription()); // Muestra el contenido del prompt en el área de vista previa
        } else {
            previewArea.setText(""); // Limpia el área si no hay selección
        }
    }

    private void addToFavorites() {
        Prompt selectedPrompt = resultList.getSelectedValue();
        if (selectedPrompt != null) {
            searchService.addToFavorites(selectedPrompt);
            JOptionPane.showMessageDialog(null, "Prompt added to favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a prompt to add to favorites.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Prompt getSelectedPrompt() {
        return resultList.getSelectedValue();
    }
}
