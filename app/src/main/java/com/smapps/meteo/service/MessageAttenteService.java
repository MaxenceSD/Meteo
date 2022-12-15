package com.smapps.meteo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageAttenteService {

    private List<String> messages;

    private int messageSelectionne = 0;

    public MessageAttenteService(String... messages) {
        this.messages = new ArrayList<>();
        if (messages != null && messages.length > 0) {
            this.messages.addAll(Arrays.asList(messages));
        }
    }

    public String getMessage() {
        return this.messages.size() > 0 ? this.messages.get(messageSelectionne) : "";
    }

    public String getMessageSuivant() {
        if (this.messageSelectionne + 1 < this.messages.size()) {
            this.messageSelectionne++;
        } else {
            this.messageSelectionne = 0;
        }
        return this.getMessage();
    }
}
