package mumsched.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Entry;
import mumsched.entity.Faculty;
import mumsched.entity.Section;
import mumsched.repository.BlockRepository;
import mumsched.repository.CourseRepository;
import mumsched.repository.SectionRepository;

public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
    BlockRepository blockRepository;

	@Autowired
    SectionRepository sectionRepository;
	
	@Autowired
    CourseRepository courseRepository;
	
	@Value("${course.capacity}")
    private String capacity;
	
	@Value("${course.mpp.code}")
    private String mppCode;
	
	@Value("${course.fpp.code}")
    private String fppCode;
	
	private static Course MPP =null;
	private static Course FPP =null;
	
	public ScheduleServiceImpl() {
		MPP = courseRepository.findByCode(mppCode);
		FPP = courseRepository.findByCode(fppCode);
	}

	@Override
	public void generate(Entry entry) {
		List<Block> blocks = blockRepository.findBlocksByEntryId(entry.getId());
		// TODO Check the number of block is right
		
		// remove all before generating
		sectionRepository.deleteAll();
		
		List<Section> sectionsForFpp = buildSectionsForFpp(blocks, countSections(entry.getFppNumber()));
		List<Section> sectionsForMpp = buildSectionsFoMpp(blocks, countSections(entry.getMppNumber()));

		//Create sections for MPP and FPP track
		//update entry

	}
	
	private int countSections(int numOfStu) {
		int maxSize = Integer.valueOf(capacity);
		int count = numOfStu/maxSize;
		if(numOfStu%maxSize>0) {
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
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(FPP);
					section.setFaculty(getBestFaculty(blocks.get(i),FPP));
					fppSections.add(section);
				}
			}else if(i==2) {
				for(int j=0;j<fppcount;j++) {
					Section section = new Section();
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(MPP);
					section.setFaculty(getBestFaculty(blocks.get(i),MPP));
					fppSections.add(section);
				}
			}else if(i==3) {
				for(int j=0;j<fppcount;j++) {
					Course course = getNextPreCourse(fppSections);
					Section section = new Section();
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i),course));
					fppSections.add(section);
				}
			}else {
				for(int j=0;j<fppcount;j++) {
					Course course = getNextCourse(fppSections);
					Section section = new Section();
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i),course));
					fppSections.add(section);
				}
			}
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
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(MPP);
					section.setFaculty(getBestFaculty(blocks.get(i),MPP));
					sections.add(section);
				}
			}else if(i==2) {
				for(int j=0;j<mppcount;j++) {
					Course course = getNextPreCourse(sections);
					Section section = new Section();
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i),course));
					sections.add(section);
				}
			}else {
				for(int j=0;j<mppcount;j++) {
					Course course = getNextCourse(sections);
					Section section = new Section();
					section.setBlock(blocks.get(i));
					section.setBuilding("Unassigned");
					section.setRoomNumber("Unassigned");
					section.setCourse(course);
					section.setFaculty(getBestFaculty(blocks.get(i),course));
					sections.add(section);
				}
			}
		}
		return sections;
	}
	
	/**
	 * find the best faculty for the course, if there is no best faculty, return UNASSIGNED
	 * @param block
	 * @param course
	 * @return
	 */
	private Faculty getBestFaculty(Block block,Course course) {
		return null;
	}
	
	/**
	 * find the next prerequisite course 
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
	 * find the next course which have prerequisite course
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
