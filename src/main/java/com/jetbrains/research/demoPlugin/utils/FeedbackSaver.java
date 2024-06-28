//package com.jetbrains.demoPlugin.utils;
//
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class FeedbackSaver {
//    public static void saveFeedback(String feedback) {
//        try (FileWriter writer = new FileWriter("feedback.txt", true)) {
//            writer.write(feedback + "\n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
