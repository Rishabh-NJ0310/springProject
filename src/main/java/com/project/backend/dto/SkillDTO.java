import java.util.List;

public class SkillDTO {
    private Long id;
    private String name;
    private String category; // Technical, Management, Soft Skills, etc.
    private String description;
    private int proficiencyLevel; // 1-5 scale where 5 is expert
    private List<EmployeeDTO> employees; // Employees with this skill
    private List<ProjectDTO> projects; // Projects requiring this skill

    // Default constructor
    public SkillDTO() {
    }

    // Parameterized constructor
    public SkillDTO(Long id, String name, String category, String description, int proficiencyLevel) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.proficiencyLevel = proficiencyLevel;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(int proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", proficiencyLevel=" + proficiencyLevel +
                '}';
    }
}