package mumsched.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.List;

@Entity
public class Faculty extends Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToMany(
        fetch=FetchType.LAZY,
        cascade={
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name="faculty_courses",
        joinColumns={@JoinColumn(name="faculty_id")},
        inverseJoinColumns={@JoinColumn(name="course_id")}
    )
    private List<Course> courses;

    @OneToMany(mappedBy="course")
    private List<Section> sections;

    public Long getId() {
        return id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }
}