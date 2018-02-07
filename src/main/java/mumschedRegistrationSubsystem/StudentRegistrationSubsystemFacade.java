package mumschedRegistrationSubsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import mumsched.AjaxResponse;
import mumsched.entity.Block;
import mumsched.entity.Entry;
import mumsched.entity.Section;
import mumsched.entity.Student;
import mumsched.entity.TrackCode;
import mumsched.service.SectionServiceImpl;
import mumsched.service.StudentServiceImpl;

@Component
public class StudentRegistrationSubsystemFacade implements StudentRegistrationSubsystem{
    @Autowired
    private SectionServiceImpl sectionService;
    
    @Autowired
    private StudentServiceImpl studentService;
	
    //1. Check if it is null or not.
	private boolean isNull(Section section, Student student) {
		return (section == null || student == null);
	}
	
	//2. Check max capacity
	private boolean checkMaxCapacity(Section section) {
		return (section.getEnrolledStudentsCount() >= section.getCourse().getMaxStudent());
	}
	
	//3. If students track is MPP then can't choose FPP
	private boolean mppToChooseFpp(Section section, Student student) {
		return ( section.getCourse().getCode() == TrackCode.FPP.getCode() && student.getTrack() );
	}
	
	//4. Can't choose chosen course again.
	private boolean isAlreadyChosen(Section section, Student student) {
		for( Section s : student.getRegisteredSections() ) {
			if( s.getCourse().getId() == section.getCourse().getId() ) {
				return true;
			}
		}
		
		return false;
	}
	
	//5. Check prerequisite chosen or not
	private boolean isPrereqChosen(Section section, Student student) {
		if( section.getCourse().hasPreRequisite() ){
			List<Section> l = sectionService.findByCourseAndEnrolledStudents(section.getCourse().getPreRequisite(), student);
			if( l == null || l.size() == 0 ) {
				return false;
			}
		}
		
		return true;
	}
	
	//6. Check one block one section
	private boolean isChosenInBlock(Section section, Student student) {
		List<Section> list = sectionService.findByBlockAndEnrolledStudents(section.getBlock(), student);
		if(list != null && list.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	public List<Section> getSectionList(Student student){
		Entry entry = student.getEntry();
		
		List<Block> blockList = entry.getBlocks();
		
		List<Section> sectionList = new ArrayList();
		
		for(Block b: blockList) {
			List<Section> sections = sectionService.findByBlock(b);
			
			sectionList.addAll(sections);
		}
		
		return sectionList;
	}
	
	public AjaxResponse registerToSection(Section section, Student student){
		AjaxResponse response = new AjaxResponse();
		
		//1. Check if it is null or not.
		if( isNull(section, student) ) {
			response.success = false;
            response.msg = "Set course or Student first.";
			return response;
		}
		
		//2. Check max capacity
		if( checkMaxCapacity(section) ) {
			response.success = false;
            response.msg = "Cannot register course. Because this course is full.";
			return response;
		}
		
		//3. If students track is MPP then can't choose FPP
		if( mppToChooseFpp(section, student) ) {
			response.success = false;
            response.msg = "Cannot register course. Because you are MPP track and can't choose FPP.";
			return response;
		}
		
		//4. Can't choose chosen course again.
		if( isAlreadyChosen(section, student) ) {
			response.success = false;
            response.msg = "Cannot register course. Because you already chosen this course.";
			return response;
		}
		
		//5. Check prerequisite chosen or not
		if( !isPrereqChosen(section, student) ){
			response.success = false;
            response.msg = "Cannot register course. Because you are not chosen prerequisite course previous blocks.";
			return response;
		}
		
		boolean redirect = false;
		
		//6. Check one block one section
		if( isChosenInBlock(section, student) ) {
			List<Section> list = sectionService.findByBlockAndEnrolledStudents(section.getBlock(), student);

			// delete previous chosen section
			List<Section> l = student.getRegisteredSections();
			l.remove( list.get(0) );
		}
		
		try {
        	student.addRegisteredSection(section);
        	studentService.save(student);
            response.success = true;
            response.msg = "Successfully registered.";
        } catch (DataIntegrityViolationException ignore) {
            response.success = false;
            response.msg = "Cannot register course. Because:" + ignore.getMessage();
        }
		
		
		return response;
	}
}
