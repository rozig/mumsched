package mumsched.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Entry;
import mumsched.entity.Faculty;
import mumsched.entity.Schedule;
import mumsched.entity.ScheduleStatus;
import mumsched.entity.Section;
import mumsched.repository.BlockRepository;
import mumsched.repository.CourseRepository;
import mumsched.repository.FacultyRepository;
import mumsched.repository.ScheduleRepository;
import mumsched.repository.SectionRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
    BlockRepository blockRepository;

	@Autowired
    SectionRepository sectionRepository;
	
	@Autowired
    CourseRepository courseRepository;
	
	@Autowired
    ScheduleRepository scheduleRepository;
	
	@Autowired
    FacultyRepository facultyRepository;
	
    private static int capacity=25;
	
	//@Value("${course.mpp.code}")
    private static String mppCode="CS401";
	
	//@Value("${course.fpp.code}")
    private static String fppCode="CS390";
	
	private static Course MPP =null;
	private static Course FPP =null;
	
	@Override
	public void generate(Entry entry) {
		MPP = courseRepository.findByCode(mppCode);
		FPP = courseRepository.findByCode(fppCode);
		
		// remove all before generating
		sectionRepository.deleteAll();
		
		// remove all the blocks
		blockRepository.deleteAll();
		
		List<Block> blocks = blockRepository.findByEntryId(entry.getId());
		if(blocks.size()==0) {
			blocks = buildBlocks(entry);
		}
		
		List<Section> sectionsForFpp = buildSectionsForFpp(blocks, countSections(entry.getFppNumber()));
		List<Section> sectionsForMpp = buildSectionsFoMpp(blocks, countSections(entry.getMppNumber()));
		
		// create blocks
		blockRepository.save(blocks);
		
		//Create sections for MPP and FPP track
		sectionsForMpp.addAll(sectionsForFpp);
		sectionRepository.save(sectionsForMpp);
		
		// save schedule
		Schedule schedule = new Schedule();
		schedule.setStatus(ScheduleStatus.DRAFT);
		schedule.setEntry(entry);
		schedule.setGeneratedDate(LocalDate.now());
		scheduleRepository.save(schedule);
		
	}
	
	private List<Block> buildBlocks(Entry entry){
		int count = entry.getFppNumber()>0?6:5;
		List<Entry> entries = new ArrayList<>();
		entries.add(entry);
		
		List<Block> blocks = new ArrayList<>();
		for(int i=0;i<count;i++){
			Block block = new Block();
			block.setName("Block"+(i+1));
			block.setStartDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().plus(i), 1));
			block.setEndDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().plus(i), 28));
			block.setEntries(entries);
			block.setSections(null);
			blocks.add(block) ;
		}
		return blocks;
	}
	/**
	 * Count sections for each track
	 * @param numOfStu
	 * @return
	 */
	private int countSections(int numOfStu) {
		int count = numOfStu/capacity;
		if(numOfStu%capacity>0) {
			count++;
		}
		return count;
	}
	
	/**
	 * build sections for FPP track
	 * @param blocks
	 * @param fppcount
	 * @return
	 */
	private List<Section> buildSectionsForFpp(List<Block> blocks,int fppcount){
		List<Section> fppSections = new ArrayList<>();
		for(int i=1;i<=blocks.size();i++) {
			if(i==1) {
				for(int j=0;j<fppcount;j++) {
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(FPP);
					section.setFaculty(getBestFaculty(blocks.get(i-1),FPP));
					fppSections.add(section);
				}
			}else if(i==2) {
				for(int j=0;j<fppcount;j++) {
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(MPP);
					section.setFaculty(getBestFaculty(blocks.get(i-1),MPP));
					fppSections.add(section);
				}
			}else if(i==3) {
				for(int j=0;j<fppcount;j++) {
					Course course = getNextPreCourse(fppSections);
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i-1),course));
					fppSections.add(section);
				}
			}else {
				for(int j=0;j<fppcount;j++) {
					Course course = getNextCourse(fppSections);
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i-1),course));
					fppSections.add(section);
				}
			}
			//important
			blocks.get(i-1).setSections(fppSections);
		}
		return fppSections;
		
	}
	
	/**
	 * build sections for MPP track
	 * @param blocks
	 * @param mppcount
	 * @return
	 */
	private List<Section> buildSectionsFoMpp(List<Block> blocks,int mppcount){
		List<Section> sections = new ArrayList<>();
		for(int i=1;i<=blocks.size();i++) {
			if(i==1) {
				for(int j=0;j<mppcount;j++) {
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(MPP);
					section.setFaculty(getBestFaculty(blocks.get(i-1),MPP));
					sections.add(section);
				}
			}else if(i==2) {
				for(int j=0;j<mppcount;j++) {
					Course course = getNextPreCourse(sections);
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i-1),course));
					sections.add(section);
				}
			}else {
				for(int j=0;j<mppcount;j++) {
					Course course = getNextCourse(sections);
					Section section = new Section();
					section.setBlock(blocks.get(i-1));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i-1),course));
					sections.add(section);
				}
			}
			//important
			blocks.get(i-1).setSections(sections);
		}
		return sections;
	}
	
	/**
	 * Find the best faculty for the course, if there is no best faculty, return UNASSIGNED
	 * @param block
	 * @param course
	 * @return
	 */
	private Faculty getBestFaculty(Block block,Course course) {
		List<Faculty> availibleFaculties = facultyRepository.findByCourses(course);
		List<Section> sections = block.getSections();
		if(null!=sections && sections.size() >0) {
			for(Section section : sections) {
				if(availibleFaculties.contains(section.getFaculty())) {
					availibleFaculties.remove(section.getFaculty());
				}
			}
		}
		if(null!=availibleFaculties && availibleFaculties.size() >0) {
			return availibleFaculties.get(0);
		}
		return null;
	}
	
	/**
	 * Find the next prerequisite course 
	 * @param sections
	 * @return
	 */
	private Course getNextPreCourse(List<Section> sections) {
		
		List<Course> precourse = courseRepository.findPreRequisiteCourses();
		
		Course targetCourse = null; 
		if(precourse.size() >0) {
			targetCourse = precourse.get(0);
		}
		
		for(int i=0;i<sections.size();i++) {
			if(precourse.contains(sections.get(i).getCourse())) {
				precourse.remove(sections.get(i).getCourse());
			}
		}
		if(precourse.size()>0) {
			targetCourse =  precourse.get(0);
		}
		return targetCourse;
	}
	
	/**
	 * Find the next course which have prerequisite course
	 * @param sections
	 * @return
	 */
	private Course getNextCourse(List<Section> sections) {
		List<Course> courses = courseRepository.findCourses();
		Course targetCourse = null; 
		if(courses.size() >0) {
			targetCourse = courses.get(0);
		}
		for(int i=0;i<sections.size();i++) {
			if(courses.contains(sections.get(i).getCourse())) {
				courses.remove(sections.get(i).getCourse());
			}
		}
		if(courses.size()>0) {
			targetCourse = courses.get(0);
		}
		return targetCourse;
	}

}
