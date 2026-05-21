package utils;

import java.time.LocalDateTime;

public class ErrorLogs {
    private String message;
    private String occurrenceClass;

    private LocalDateTime date;

    public ErrorLogs(String message, String occurrenceClass) {
        this.message = message;
        this.occurrenceClass = occurrenceClass;
        this.date = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOccurrenceClass() {
        return occurrenceClass;
    }

    public void setOccurrenceClass(String occurrenceClass) {
        this.occurrenceClass = occurrenceClass;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[").append(this.date).append("]\n");
        sb.append("[ERROR]").append("\n");
        sb.append("[").append(this.occurrenceClass).append("]\n");
        sb.append(this.message).append("\n");
        sb.append("------------------------");

        return sb.toString();
    }
}
