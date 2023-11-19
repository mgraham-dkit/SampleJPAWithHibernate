package foreign_keys.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(length=100)
    private String name;

    @Column(length=500)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dueDate;

    @ManyToMany
    @JoinTable(name="project_team", joinColumns = {@JoinColumn(name = "project_id")}, inverseJoinColumns =
            {@JoinColumn(name="employee_id")})
    private Set<Employee> team;

    public Project() {
    }

    public Project(long id, String name, String description, LocalDate startDate, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        team = new HashSet<>();
    }

    public Project(String name, String description, LocalDate startDate, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        team = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private String getTeamNames(){
        if(team.isEmpty()){
            return "[None]";
        }
        Iterator<Employee> iter = team.iterator();
        Employee employee = iter.next();
        String names = "[" + employee.getfName() + " " + employee.getlName();
        while(iter.hasNext()){
            employee = iter.next();
            names = names + ", " + employee.getfName() + " " + employee.getlName();
        }

        return names+ "]";
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", team=" + this.getTeamNames() +
                '}';
    }

    public boolean addTeamMember(Employee e){
        if(this.team.add(e)){
            e.addProject(this);
            return true;
        }
        return false;
    }
}
