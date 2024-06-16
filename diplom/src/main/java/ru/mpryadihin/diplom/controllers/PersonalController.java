package ru.mpryadihin.diplom.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.servicies.JobService;
import ru.mpryadihin.diplom.servicies.PersonalService;

import java.io.IOException;

@Controller
@RequestMapping("/personal")
public class PersonalController {
    private final PersonalService personalService;
    private final JobService jobService;
    @Autowired
    public PersonalController(PersonalService personalService, JobService jobService) {
        this.personalService = personalService;
        this.jobService = jobService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("personal", personalService.findAll());
        return "personal/index";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newPersonal(@ModelAttribute("personal") Personal personal, Model model){
        model.addAttribute("job", jobService.findAll());
        return "personal/new";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("personal", personalService.findOne(id));
        String[] sentencesArray = jobService.findOne(id).getResp().split("(?<=[.!?])\\s*");
        model.addAttribute("sentences",sentencesArray);
        return "personal/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("personal", personalService.findOne(id));
        model.addAttribute("job",jobService.findAll());
        return "personal/edit";
    }
    @PostMapping()
    public String create(@RequestParam("file") MultipartFile file,
                         @ModelAttribute("personal") @Valid Personal personal,
                         BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("job", jobService.findAll());
            return "personal/new";
        }
        personalService.save(personal, file);
        return "redirect:/personal";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @RequestParam("file") MultipartFile file,
                         @ModelAttribute("personal") @Valid Personal personal,
                         BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("job", jobService.findAll());
            return "personal/edit";
        }
        personalService.update(id, personal, file);
        return "redirect:/personal";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personalService.delete(id);
        return "redirect:/personal";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id){
        Personal personal = personalService.findOne(id);
        if (personal != null && personal.getImage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(personal.getImage());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
