package io.github.scitia.susieserver.sprint.dto;

import java.time.ZonedDateTime;

public class SprintDTOBuilder {

    private Integer id;
    
    private String name;
    
    private ZonedDateTime startTime;
    
    private Boolean active;
    
    private String sprintGoal;
    
    private Integer projectID;

    SprintDTOBuilder() {
    }

    public static SprintDTOBuilder builder() {
        return new SprintDTOBuilder();
    }

    public SprintDTOBuilder id(final Integer id) {
        this.id = id;
        return this;
    }

    public SprintDTOBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SprintDTOBuilder startTime(final ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public SprintDTOBuilder active(final Boolean active) {
        this.active = active;
        return this;
    }

    public SprintDTOBuilder sprintGoal(final String sprintGoal) {
        this.sprintGoal = sprintGoal;
        return this;
    }

    public SprintDTOBuilder projectID(final Integer projectID) {
        this.projectID = projectID;
        return this;
    }

    public SprintDTO build() {
        return new SprintDTO(this.id, this.name, this.startTime, this.active, this.sprintGoal, this.projectID);
    }

    
    public String toString() {
        String var10000 = String.valueOf(this.id);
        return "SprintDTO.SprintDTOBuilder(id=" + var10000 + ", name=" + this.name + ", startTime=" + this.startTime + ", active=" + this.active + ", sprintGoal=" + this.sprintGoal + ", projectID=" + this.projectID + ")";
    }
}
