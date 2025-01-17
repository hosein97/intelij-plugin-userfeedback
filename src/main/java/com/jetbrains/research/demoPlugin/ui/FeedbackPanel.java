package com.jetbrains.research.demoPlugin.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.ComboBox;
import com.jetbrains.research.demoPlugin.database.DatabaseHelper;
import com.jetbrains.research.demoPlugin.model.UserFeedback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedbackPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel parentPanel;
    private DatabaseHelper dbHelper;
    private JTextField feedbackField;
    private JComboBox<String> typeComboBox;
    private JSlider ratingSlider;

    public FeedbackPanel(CardLayout cardLayout, JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.dbHelper = new DatabaseHelper();

        setLayout(new BorderLayout());

        JLabel feedbackDescriptionLabel = new JLabel("<html><b>Please answer the following questions according to the image you just looked at and then press the submit button.</b></html>");
        feedbackDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        feedbackDescriptionLabel.setFont(feedbackDescriptionLabel.getFont().deriveFont(Font.PLAIN, 14f));

        JLabel feedbackLabel = new JLabel("Please provide your feedback:");
        feedbackField = new JTextField(20);

        JLabel typeLabel = new JLabel("Type of the animal in the image:");
        typeComboBox = new ComboBox<>(new String[] {"None", "Cat", "Dog", "Hedgehog"});

        JLabel ratingLabel = new JLabel("Rate the image (1-5):");
        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);

        JPanel feedbackInputPanel = new JPanel(new GridLayout(9, 1));
        feedbackInputPanel.add(feedbackDescriptionLabel);
        feedbackInputPanel.add(feedbackLabel);
        feedbackInputPanel.add(feedbackField);
        feedbackInputPanel.add(typeLabel);
        feedbackInputPanel.add(typeComboBox);
        feedbackInputPanel.add(ratingLabel);
        feedbackInputPanel.add(ratingSlider);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedback = feedbackField.getText();
                String animalType = (String) typeComboBox.getSelectedItem();
                int rating = ratingSlider.getValue();
                System.out.println("Feedback: " + feedback);
                System.out.println("Animal Type: " + animalType);
                System.out.println("Rating: " + rating);
                ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper.insertFeedback(new UserFeedback(feedback, animalType, rating));
                        ApplicationManager.getApplication().invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                feedbackField.setText("");
                                typeComboBox.setSelectedIndex(0);
                                ratingSlider.setValue(3);
                                cardLayout.show(parentPanel, "StartPanel");
                            }
                        });

                    }
                });

            }
        });

        add(feedbackInputPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}
