package biz.ei6.interventions.desktop.framework.interventions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StatusDTO {

    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
