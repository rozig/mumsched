package mumsched.controller;

import static org.mockito.Matchers.intThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mumsched.entity.Block;
import mumsched.entity.Entry;
import mumsched.entity.Section;
import mumsched.service.BlockService;
import mumsched.service.EntryService;

@Controller
@RequestMapping(path = "/schedule")
public class ScheduleController {
	@Autowired
	private BlockService blockService;
	@Autowired
	private EntryService entryService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		List<Block> blocks = blockService.findAll();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("blocks", blocks);
		modelAndView.setViewName("schedule/index");
		return modelAndView;
	}

	// generate schedule
	// 1.get list of blocks order by startDate
	// 2. do loops on blocks
	// 3. for each block ,get a list of sections
	// 4. do loops on section
	@RequestMapping(value = "/generate/{entryId}", method = RequestMethod.GET)
	public void generate(@PathVariable(value = "entryId") Long entryId, Model model) {
		Entry entry = entryService.findOne(entryId);
		// TODO check if this entry have schedules generated
		
		// TODO check if exist MPP and FPP courses
		
	}

}
