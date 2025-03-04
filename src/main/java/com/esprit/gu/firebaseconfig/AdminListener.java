package com.esprit.gu.firebaseconfig;

import com.google.firebase.database.*;

public class AdminListener {
    public static void listen() {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("notifications");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                String message = snapshot.getValue(String.class);
                System.out.println("New Notification: " + message);
                NotificationPopup.showPopup(message);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) { }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) { }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) { }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Database Error: " + error.getMessage());
            }
        });
    }
}
