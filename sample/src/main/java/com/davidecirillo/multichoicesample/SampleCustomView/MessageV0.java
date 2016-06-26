package com.davidecirillo.multichoicesample.sampleCustomView;

/**
 * Created by davidecirillo on 24/06/2016.
 */

public class MessageV0 {

    public String messageTitle;
    public String messageContent;

    public MessageV0(String messageTitle, String messageContent) {
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
