package com.jetbrains.research.demoPlugin.ui;

import javax.swing.*;
import java.awt.*;

public class MainPane extends JPanel {
    private CardLayout cardLayout;
    private StartPanel startPanel;
    private ImagePanel imagePanel;
    private FeedbackPanel feedbackPanel;

    public MainPane() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        startPanel = new StartPanel(cardLayout, this);
        imagePanel = new ImagePanel(cardLayout, this);
        feedbackPanel = new FeedbackPanel(cardLayout, this);

        add(startPanel, "StartPanel");
        add(imagePanel, "ImagePanel");
        add(feedbackPanel, "FeedbackPanel");

        cardLayout.show(this, "StartPanel");
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public FeedbackPanel getFeedbackPanel() {
        return feedbackPanel;
    }
}
