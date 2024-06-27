package com.jetbrains.demoPlugin.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainPane extends JPanel {
    private CardLayout cardLayout;
    private JPanel startPanel;
    private JPanel imagePanel;
    private JPanel feedbackPanel;
    private JProgressBar timeProgressBar;
    private Timer timer;

    public MainPane() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Initialize panels
        startPanel = createStartPanel();
        imagePanel = createImagePanel();
        feedbackPanel = createFeedbackPanel();

        // Add panels to CardLayout
        add(startPanel, "StartPanel");
        add(imagePanel, "ImagePanel");
        add(feedbackPanel, "FeedbackPanel");

        // Show the start panel initially
        cardLayout.show(this, "StartPanel");
    }

    private JPanel createStartPanel() {
        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the image panel when the button is clicked
                cardLayout.show(MainPane.this, "ImagePanel");
                startTimer();
            }
        });
        panel.add(startButton);
        return panel;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Load and add the image
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/META-INF/hedgehog.jpg"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add time progress bar at the bottom
        timeProgressBar = new JProgressBar(0, 100);
        timeProgressBar.setStringPainted(true);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(timeProgressBar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel feedbackLabel = new JLabel("Please provide your feedback:");
        JTextField feedbackField = new JTextField(20); // Specify the preferred column width

        JPanel feedbackInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        feedbackInputPanel.add(feedbackField);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle feedback submission here (e.g., store feedback)
                // Clear the feedback field
                feedbackField.setText("");
                // After submission, return to the start panel
                cardLayout.show(MainPane.this, "StartPanel");
            }
        });

        // Add components to the feedback panel
        panel.add(feedbackLabel, BorderLayout.NORTH);
        panel.add(feedbackInputPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        return panel;
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
