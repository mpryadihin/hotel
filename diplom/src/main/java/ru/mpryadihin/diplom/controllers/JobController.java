package ru.mpryadihin.diplom.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.models.Job;
import ru.mpryadihin.diplom.servicies.JobService;

import java.util.ArrayList;

@Controller
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("job", jobService.findAll());
        return "job/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("job", jobService.findOne(id));
        String[] sentencesArray = jobService.findOne(id).getResp().split("(?<=[.!?])\\s*");
        model.addAttribute("sentences",sentencesArray);
        return "job/show";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("job",jobService.findOne(id));
        return "job/edit";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newJob(@ModelAttribute("job") Job job){
        return "job/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("job") @Valid Job job,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "job/new";
        }
        jobService.save(job);
        return "redirect:/job";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("job") Job job,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "job/edit";
        jobService.update(id, job);
        return "redirect:/job";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        jobService.delete(id);
        return "redirect:/job";
    }
}
