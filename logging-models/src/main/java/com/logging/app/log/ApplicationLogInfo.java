package com.logging.app.log;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logging.LogInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationLogInfo extends LogInfo {

    private String stage;
    private ApplicationSeverity severity;
    private String message;
    private Exception exception;

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public ApplicationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ApplicationSeverity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return getStackTrace(exception);
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getStackTrace(final Throwable throwable) {
        if (null == throwable) {
            return null;
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    @Override
    public String toString() {
        return "ApplicationLogInfo [stage=" + stage + ", severity=" + severity + ", message=" + message
                + ", exception=" + exception + "]";
    }

}
