package com.esprit.gu.firebaseconfig;

import javax.swing.*;

public class NotificationPopup {
    public static void showPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "New Feedback", JOptionPane.INFORMATION_MESSAGE);
    }
}
