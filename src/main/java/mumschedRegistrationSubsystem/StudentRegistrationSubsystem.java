package mumschedRegistrationSubsystem;

import java.util.List;

import mumsched.AjaxResponse;
import mumsched.entity.Section;
import mumsched.entity.Student;

public interface StudentRegistrationSubsystem {
	public AjaxResponse registerToSection(Section section, Student student);
}
