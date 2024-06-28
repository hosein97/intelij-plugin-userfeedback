package com.jetbrains.demoPlugin.ui;

import com.jetbrains.demoPlugin.database.DatabaseHelper;

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
        typeComboBox = new JComboBox<>(new String[] {"None", "Cat", "Dog", "Hedgehog"});

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
                dbHelper.insertFeedback(feedback, animalType, rating);
                feedbackField.setText("");
                typeComboBox.setSelectedIndex(0);
                ratingSlider.setValue(3);
                cardLayout.show(parentPanel, "StartPanel");
            }
        });

        add(feedbackInputPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}
