package mumsched.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mumsched.entity.Entry;
import mumsched.entity.Schedule;
import mumsched.entity.ScheduleStatus;
import mumsched.entity.Section;
import mumsched.service.EntryService;
import mumsched.service.ScheduleService;
import mumsched.service.SectionService;

@Controller
@RequestMapping(path = "/schedule")
public class ScheduleController {
	@Autowired
	private EntryService entryService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private SectionService sectionService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		List<Entry> entries = entryService.findAll();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("entries", entries);
		
		List<Schedule> schedules = scheduleService.findAll();
		modelAndView.addObject("schedules", schedules);
		
		// find all sections
		//List<Section> sections = sectionService.findAll();
		//sections.stream().sorted(Comparator<Section>.comparing(s->s.))
		//modelAndView.addObject("sections", sections);
		
		modelAndView.setViewName("schedule/index");
		return modelAndView;
	}
	
	@RequestMapping(value = "/detail/{entryId}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "entryId") Long entryId, Model model) {
		Entry entry = entryService.findOne(entryId);
        if (entry == null) {
            // not found
            return "404";
        }
        	// find all sections
 		List<Section> sections = sectionService.findAllOrderByBlock();
 		//sections.stream().sorted(Comparator<Section>.comparing(s->s.))
        model.addAttribute("sections", sections);
        model.addAttribute("entry", entry);
        return "schedule/detail";
	}
	

	@RequestMapping(value = "/generate/{entryId}", method = RequestMethod.POST)
	public String generate(@PathVariable(value = "entryId") Long entryId) {
		Entry entry = entryService.findOne(entryId);
		// TODO check if this entry have schedules generated
		scheduleService.generate(entry);
		
//		AjaxResponse response = new AjaxResponse();
//		try {
//            response.success = true;
//            response.msg = "Schedule Generated.";
//        } catch (DataIntegrityViolationException ignore) {
//            response.success = false;
//            response.msg = "Unforturenatly, fail to generate the schedule";
//        }
		return "redirect:/schedule/";
	}
	
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.GET)
	public String approve(@PathVariable(value = "id") Long id) {
		Schedule schedule = scheduleService.findOne(id);
		
		if(schedule.getStatus().equals(ScheduleStatus.DRAFT)) {
			schedule.setStatus(ScheduleStatus.APPROVED);
			schedule.setApprovedDate(LocalDate.now());
			scheduleService.save(schedule);
		}
		return "redirect:/schedule/";
	}

}
