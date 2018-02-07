package mumschedRegistrationSubsystem;

import java.util.List;

import mumsched.entity.Section;
import mumsched.entity.Student;

interface StudentRegistrationSubsystem {
	public List<Section> getSectionList(Student student);
}
