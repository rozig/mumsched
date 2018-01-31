package mumsched.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{notBlank.message}")
    private String name;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @ManyToMany(
        fetch=FetchType.LAZY,
        cascade={
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name="entry_blocks",
        joinColumns={@JoinColumn(name="block_id")},
        inverseJoinColumns={@JoinColumn(name="entry_id")}
    )
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy="block")
    private List<Section> sections;

    public Block() {
        this.entries = new ArrayList<Entry>();
        this.sections = new ArrayList<Section>();
    }

    @Override
    public String toString() {
        return getName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }
}
