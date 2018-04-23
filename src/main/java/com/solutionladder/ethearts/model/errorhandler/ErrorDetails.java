package com.solutionladder.ethearts.model.errorhandler;

import java.util.Date;
import java.util.List;

/**
 * Error handler model
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private List<String> details;

    public ErrorDetails(Date timestamp, String message, List<String> details) {
      super();
      this.timestamp = timestamp;
      this.message = message;
      this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}