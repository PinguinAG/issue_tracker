package ebuero.aatasiei.tracker.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public class NewIssueRequest {

    protected String issueType;
    protected String title;
    protected String description;
    protected LocalDate creationDate;
    protected String status;
    protected Integer estimate;
    protected String priority;
    protected Long assignedDeveloperId;

    protected NewIssueRequest() {
    }

    public String getIssueType() {
        return issueType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Integer getEstimate() {
        return estimate;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getPriority() {
        return priority;
    }

    protected void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    protected void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    protected void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    protected void setPriority(String priority) {
        this.priority = priority;
    }

    protected void setAssignedDeveloperId(Long assignedDeveloperId) {
        this.assignedDeveloperId = assignedDeveloperId;
    }

    public Long getAssignedDeveloperId() {
        return assignedDeveloperId;
    }
}
