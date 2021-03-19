package biz.ei6.interventions.desktop.framework.medias;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MediaDTO {

    String id;
    String intervention_id;
    String date;
    String fileName;
    String deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterventionId() {
        return intervention_id;
    }

    public void setInterventionId(String interventionId) {
        this.intervention_id = intervention_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
