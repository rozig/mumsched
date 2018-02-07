package mumsched.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String roomNumber;

    private String building;

    @ManyToMany(
        fetch=FetchType.LAZY,
        mappedBy="registeredSections"
    )
    private List<Student> enrolledStudents;

    @ManyToOne
    @JoinColumn(name="faculty_id", nullable=true)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=true)
    private Course course;

    @ManyToOne
    @JoinColumn(name="block_id", nullable=false)
    private Block block;

    public Section() {
        this.enrolledStudents = new ArrayList<Student>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void addEnrolledStudents(Student student) {
        this.enrolledStudents.add(student);
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getEnrolledStudentsCount() {
        return this.enrolledStudents.size();
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
