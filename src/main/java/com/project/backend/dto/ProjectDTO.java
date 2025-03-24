import java.util.Date;
import java.util.List;

public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status; // Active, Completed, On Hold, Cancelled
    private String clientName;
    private String priority; // High, Medium, Low
    private List<SkillDTO> requiredSkills;
    private List<AssignmentDTO> assignments;

    // Default constructor
    public ProjectDTO() {
    }

    // Parameterized constructor
    public ProjectDTO(Long id, String name, String description, Date startDate, Date endDate, 
                    String status, String clientName, String priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.clientName = clientName;
        this.priority = priority;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<SkillDTO> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<SkillDTO> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDTO> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", clientName='" + clientName + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}