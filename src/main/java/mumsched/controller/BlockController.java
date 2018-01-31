package mumsched.controller;

import mumsched.AjaxResponse;
import mumsched.entity.Block;
import mumsched.entity.Entry;
import mumsched.service.BlockService;
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
@RequestMapping(path = "/block")
public class BlockController {
    @Autowired
    private BlockService blockService;
    @Autowired
    private EntryService entryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Block> blocks = blockService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("blocks", blocks);
        modelAndView.setViewName("block/index");
        return modelAndView;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable(value = "id") Long id, Model model) {

        Block block = blockService.findOne(id);
        if (block == null) {
            // not found
            return "404";
        }
        model.addAttribute("block", block);

        return "block/read";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newBlock(Model model) {
        model.addAttribute("block", new Block());
        model.addAttribute("blocks", blockService.findAll());
        model.addAttribute("entries", entryService.findAll());

        return "block/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Block block, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("block", block);
            model.addAttribute("blocks", blockService.findAll());
            model.addAttribute("entries", entryService.findAll());
            return "block/create";
        }

        blockService.save(block);

        return "redirect:/block/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Model model) {

        Block block = blockService.findOne(id);
        if (block == null) {
            // not found
            return "404";
        }
        model.addAttribute("block", block);
        model.addAttribute("blocks", blockService.findAllExcept(block.getId()));
        model.addAttribute("entries", entryService.findAll());

        return "block/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Long id,
                         @Valid @ModelAttribute("block") Block block,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("blocks", blockService.findAllExcept(block.getId()));
            model.addAttribute("entries", entryService.findAll());
            model.addAttribute("block", block);
            model.addAttribute("errors", result.getAllErrors());
            return "block/update";
        }
        Block c = blockService.findOne(id);
        c.setName(block.getName());
        c.setStartDate(block.getStartDate());
        c.setEndDate(block.getEndDate());
        c.getEntries().clear();
        for (Entry e: block.getEntries())
            c.addEntry(e);
        blockService.save(c);

        return "redirect:/block/";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value = "id") Long id) {
        Block block = blockService.findOne(id);
        AjaxResponse response = new AjaxResponse();
        if (block != null) {
            try {
                blockService.delete(id);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch (DataIntegrityViolationException ignore) {
                // Cannot remove block that is prerequisite of other block.
                response.success = false;
                response.msg = "Cannot remove block that is prerequisite of other block.";
            }
        }
        return response;
    }

}
