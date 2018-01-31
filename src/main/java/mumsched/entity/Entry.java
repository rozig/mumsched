package mumsched.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message="{notBlank.message}")
    private String name;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private Integer mppNumber;

    private Integer fppNumber;

    private Integer optNumber;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="entry")
    private List<Student> students = new ArrayList<>();

    @ManyToMany(
        fetch=FetchType.LAZY,
        cascade={
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy="entries"
    )
    private List<Block> blocks = new ArrayList<>();

    private Boolean schedulePublish;

    @Override
    public String toString() {
        return getName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMppNumber() {
        return mppNumber;
    }

    public void setMppNumber(Integer mppNumber) {
        this.mppNumber = mppNumber;
    }

    public Integer getFppNumber() {
        return fppNumber;
    }

    public void setFppNumber(Integer fppNumber) {
        this.fppNumber = fppNumber;
    }

    public Integer getOptNumber() {
        return optNumber;
    }

    public void setOptNumber(Integer optNumber) {
        this.optNumber = optNumber;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public Boolean getSchedulePublish() {
        return schedulePublish;
    }

    public void setSchedulePublish(Boolean schedulePublish) {
        this.schedulePublish = schedulePublish;
    }
}
