package mumsched.entity;

import org.springframework.format.annotation.DateTimeFormat;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class Student extends Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    private Boolean track;

    private Boolean ptType;

    private Boolean isUSResident;

    @ManyToMany(
        fetch=FetchType.LAZY
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public void setRegisteredSections(List<Section> registeredSections) {
        this.registeredSections = registeredSections;
    }

    public Boolean getUSResident() {
        return isUSResident;
    }

    public void setUSResident(Boolean USResident) {
        isUSResident = USResident;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
}