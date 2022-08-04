package com.theroc.message.Utilities;

import java.util.ArrayList;
import java.util.List;

public class PreloadedMessages {

    List<Conversation> createMessages() {
        List<Conversation> messages = new ArrayList<>();
        List<String> textList = new ArrayList<>();

        textList.add("Εντάξει!");
        textList.add("Πάρε με τηλ όποτε μπορέσεις");
        //Conversation conversation = new Conversation("Γιώργος Κ.", textList, "Τετάρτη · 18:21", "Τετ 19:02");
        //messages.add(conversation);
        textList.clear();

        textList.add("ΟΚ");
        textList.add("3 και τεταρτο");
        textList.add("Τι ωρα η κληση σημερα?");
        //conversation = new Conversation("Κώστας Α.", textList, "Τρίτη · 10:54", "Τρι 11:00");
        //messages.add(conversation);
        return messages;
    }
}
