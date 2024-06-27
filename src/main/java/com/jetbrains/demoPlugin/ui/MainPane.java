package com.jetbrains.demoPlugin.ui;

import com.jetbrains.demoPlugin.database.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MainPane extends JPanel {
    private CardLayout cardLayout;
    private JPanel startPanel;
    private JPanel imagePanel;
    private JPanel feedbackPanel;
    private JProgressBar timeProgressBar;
    private Timer timer;
    private DatabaseHelper dbHelper;
    private JTextField feedbackField;
    private JComboBox<String> typeComboBox;
    private JSlider ratingSlider;
    private JLabel imageLabel;
    private List<String> imagePaths;
    private Random random;

    public MainPane() {
        dbHelper = new DatabaseHelper();
        random = new Random();

        // Load image paths
        imagePaths = Arrays.asList(
                "/toolWindow/hedgehog.jpg",
                "/toolWindow/cat.jpg",
                "/toolWindow/dog.jpg"
        );

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        startPanel = createStartPanel();
        imagePanel = createImagePanel();
        feedbackPanel = createFeedbackPanel();

        add(startPanel, "StartPanel");
        add(imagePanel, "ImagePanel");
        add(feedbackPanel, "FeedbackPanel");

        cardLayout.show(this, "StartPanel");
    }

    private JPanel createStartPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Randomly select an image and update the image panel
                String selectedImagePath = imagePaths.get(random.nextInt(imagePaths.size()));
                updateImagePanel(selectedImagePath);

                // Show the image panel when the button is clicked
                cardLayout.show(MainPane.this, "ImagePanel");
                startTimer();
            }
        });
        panel.add(startButton, new GridBagConstraints());
        return panel;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel descriptionLabel = new JLabel("<html><b>Please look at the following image for 10 seconds.</b></html>");
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(Font.PLAIN, 14f));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        timeProgressBar = new JProgressBar(0, 100);
        timeProgressBar.setStringPainted(true);

        panel.add(descriptionLabel, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(timeProgressBar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());

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
                cardLayout.show(MainPane.this, "StartPanel");
            }
        });

        panel.add(feedbackInputPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        return panel;
    }

    private void updateImagePanel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void startTimer() {
        timer = new Timer();
        int delay = 100; // Update progress bar every 100 milliseconds
        int period = 100;
        timer.scheduleAtFixedRate(new TimerTask() {
            int timeElapsed = 0;
            @Override
            public void run() {
                timeElapsed += period;
                int progress = (int) ((double) timeElapsed / 10000 * 100); // 10 seconds in milliseconds
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        timeProgressBar.setValue(progress);
                        timeProgressBar.setString(String.format("%d seconds", 10 - timeElapsed / 1000));
                    }
                });
                if (timeElapsed >= 10000) {
                    timer.cancel();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            cardLayout.show(MainPane.this, "FeedbackPanel");
                        }
                    });
                }
            }
        }, delay, period);
    }
}
