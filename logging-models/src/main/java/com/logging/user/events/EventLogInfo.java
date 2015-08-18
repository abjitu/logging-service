package com.logging.user.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logging.LogInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventLogInfo extends LogInfo {

    private String sessionId;

    private UserInfo userInfo;

    private SenderInfo senderInfo;

    private UserEventType eventType;

    private Payload payload;

    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public UserEventType getEventType() {
        return eventType;
    }

    public void setEventType(UserEventType eventType) {
        this.eventType = eventType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "EventLogInfo [sessionId=" + sessionId + ", userInfo=" + userInfo + ", senderInfo=" + senderInfo
                + ", eventType=" + eventType + ", payload=" + payload + ", currencyCode=" + currencyCode + "]";
    }

}
