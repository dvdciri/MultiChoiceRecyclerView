package com.davidecirillo.multichoicesample.SampleCustomView;

/**
 * Created by davidecirillo on 24/06/2016.
 */

class MessageV0 {

    private final String messageTitle;
    private final String messageContent;

    MessageV0(String messageTitle, String messageContent) {
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
    }

     String getMessageTitle() {
        return messageTitle;
    }

    String getMessageContent() {
        return messageContent;
    }
}
