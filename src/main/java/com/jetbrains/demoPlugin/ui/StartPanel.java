package com.jetbrains.demoPlugin.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class StartPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel parentPanel;
    private List<String> imagePaths;
    private Random random;

    public StartPanel(CardLayout cardLayout, JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.random = new Random();

        // Load image paths
        imagePaths = Arrays.asList(
                "/toolWindow/hedgehog.jpg",
                "/toolWindow/cat.jpg",
                "/toolWindow/dog.jpg"
        );

        setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedImagePath = imagePaths.get(random.nextInt(imagePaths.size()));
                ((MainPane) parentPanel).getImagePanel().updateImagePanel(selectedImagePath);

                cardLayout.show(parentPanel, "ImagePanel");
                ((MainPane) parentPanel).getImagePanel().startTimer();
            }
        });
        add(startButton, new GridBagConstraints());
    }
}
