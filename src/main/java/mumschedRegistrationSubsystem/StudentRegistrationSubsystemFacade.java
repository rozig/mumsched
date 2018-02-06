package mumschedRegistrationSubsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mumsched.entity.Block;
import mumsched.entity.Entry;
import mumsched.entity.Section;
import mumsched.entity.Student;
import mumsched.service.BlockServiceImpl;
import mumsched.service.SectionServiceImpl;
import mumsched.service.StudentServiceImpl;

@Component
public class StudentRegistrationSubsystemFacade implements StudentRegistrationSubsystem{
    @Autowired
    private SectionServiceImpl sectionService;
    
    @Autowired
    private BlockServiceImpl blockService;
    
    @Autowired
    private StudentServiceImpl studentService;
    
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
	
	public boolean registerToSection(Section section, Student student){
		
		
		return false;
	}
}
