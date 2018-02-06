package mumsched.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.validation.constraints.Digits;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message="{notBlank.message}")
    private String name;

    @NotBlank(message="{notBlank.message}")
    private String code;

    @Digits(integer=10, fraction=0, message = "{invalidNumber.message}")
    private int maxStudent;

    @Digits(integer=10, fraction=0, message = "{invalidNumber.message}")
    private int level;

    @ManyToOne
    @JoinColumn(name="prereq_course_id")
    private Course preRequisite;

    @OneToMany(mappedBy="course")
    private List<Section> sections;

    @ManyToMany(
        fetch=FetchType.LAZY,
        cascade={
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy="courses"
    )
    private List<Faculty> faculties;

    public Course() {
        this.sections = new ArrayList<Section>();
        this.faculties = new ArrayList<Faculty>();
    }

    @Override
    public String toString() {
        return getCode() + " " + getName();
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Course getPreRequisite() {
        return preRequisite;
    }

    public void setPreRequisite(Course preRequisite) {
        this.preRequisite = preRequisite;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void addFaculty(Faculty faculty) {
        this.faculties.add(faculty);
    }

    public Boolean hasPreRequisite() {
        return this.preRequisite != null;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }
}
