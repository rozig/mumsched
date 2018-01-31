package mumsched.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class Student extends Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date birthDate;

    private Boolean track;

    private Boolean ptType;

    private Boolean isUSResident;

    @ManyToMany(
        fetch=FetchType.LAZY,
        cascade={
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name="section_students",
        joinColumns={@JoinColumn(name="student_id")},
        inverseJoinColumns={@JoinColumn(name="section_id")}
    )
    private List<Section> registeredSections = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="entry_id", nullable=false)
    private Entry entry;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getTrack() {
        return track;
    }

    public void setTrack(Boolean track) {
        this.track = track;
    }

    public Boolean getPtType() {
        return ptType;
    }

    public void setPtType(Boolean ptType) {
        this.ptType = ptType;
    }

    public Boolean getIsUSResident() {
        return isUSResident;
    }

    public void setIsUSResident(Boolean isUSResident) {
        this.isUSResident = isUSResident;
    }

    public List<Section> getRegisteredSections() {
        return registeredSections;
    }

    public void addRegisteredSection(Section section) {
        this.registeredSections.add(section);
    }
}