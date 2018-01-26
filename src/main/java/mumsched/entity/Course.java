package mumsched.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{notBlank.message}")
    private String code;

    @NotBlank(message = "{notBlank.message}")
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "prereq_course_id")
    private Course preRequisite;

    @Override
    public String toString() {
        return getCode() + " " + getName();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Course getPreRequisite() {
        return preRequisite;
    }

    public void setPreRequisite(Course preRequisite) {
        this.preRequisite = preRequisite;
    }
}
