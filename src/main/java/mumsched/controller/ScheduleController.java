package mumsched.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mumsched.entity.Entry;
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
		
		// find all sections
		List<Section> sections = sectionService.findAll();
		modelAndView.addObject("sections", sections);
		
		modelAndView.setViewName("schedule/index");
		return modelAndView;
	}

	@RequestMapping(value = "/generate/{entryId}", method = RequestMethod.POST)
	public void generate(@PathVariable(value = "entryId") Long entryId, Model model) {
		// TODO check if this entry have schedules generated
		Entry entry = entryService.findOne(entryId);
		scheduleService.generate(entry);
		
	}

}
