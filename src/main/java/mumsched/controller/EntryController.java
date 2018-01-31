package mumsched.controller;

import mumsched.AjaxResponse;
import mumsched.entity.Entry;
import mumsched.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/entry")
public class EntryController {
    @Autowired
    private EntryService entryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Entry> entries = entryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("entries", entries);
        modelAndView.setViewName("entry/index");
        return modelAndView;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable(value = "id") Long id, Model model) {

        Entry entry = entryService.findOne(id);
        if (entry == null) {
            // not found
            return "404";
        }
        model.addAttribute("entry", entry);

        return "entry/read";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEntry(Model model) {
        model.addAttribute("entry", new Entry());
        model.addAttribute("entries", entryService.findAll());

        return "entry/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Entry entry, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("entry", entry);
            model.addAttribute("entries", entryService.findAll());
            return "entry/create";
        }

        entryService.save(entry);

        return "redirect:/entry/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Model model) {

        Entry entry = entryService.findOne(id);
        if (entry == null) {
            // not found
            return "404";
        }
        model.addAttribute("entry", entry);
        model.addAttribute("entries", entryService.findAllExcept(entry.getId()));

        return "entry/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Long id,
                         @Valid @ModelAttribute("entry") Entry entry,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("entries", entryService.findAllExcept(entry.getId()));
            model.addAttribute("entry", entry);
            model.addAttribute("errors", result.getAllErrors());
            return "entry/update";
        }
        Entry c = entryService.findOne(id);
        c.setName(entry.getName());
        c.setDate(entry.getDate());
        c.setMppNumber(entry.getMppNumber());
        c.setFppNumber(entry.getFppNumber());
        c.setOptNumber(entry.getOptNumber());
        entryService.save(c);

        return "redirect:/entry/";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value = "id") Long id) {
        Entry entry = entryService.findOne(id);
        AjaxResponse response = new AjaxResponse();
        if (entry != null) {
            try {
                entryService.delete(id);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch (DataIntegrityViolationException ignore) {
                // Cannot remove entry that is prerequisite of other entry.
                response.success = false;
                response.msg = "Cannot remove entry that is prerequisite of other entry.";
            }
        }
        return response;
    }

}
