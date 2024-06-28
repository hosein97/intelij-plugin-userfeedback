package com.jetbrains.research.demoPlugin.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ImagePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel parentPanel;
    private JLabel imageLabel;
    private JProgressBar timeProgressBar;
    private Timer timer;

    public ImagePanel(CardLayout cardLayout, JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;

        setLayout(new BorderLayout());

        JLabel descriptionLabel = new JLabel("<html><b>Please look at the following image for 10 seconds.</b></html>");
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(Font.PLAIN, 14f));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        timeProgressBar = new JProgressBar(0, 100);
        timeProgressBar.setStringPainted(true);

        add(descriptionLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(timeProgressBar, BorderLayout.SOUTH);
    }

    public void updateImagePanel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    public void startTimer() {
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
                            cardLayout.show(parentPanel, "FeedbackPanel");
                        }
                    });
                }
            }
        }, delay, period);
    }
}
