package ru.mpryadihin.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.servicies.PersonDetailsService;
import ru.mpryadihin.diplom.util.PersonValidator;

import javax.validation.Valid;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonDetailsService personService;

    @Autowired
    public AdminController(PersonDetailsService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users", personService.findAll());
        return "admin/options";
    }
    @GetMapping("/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        model.addAttribute("user", personService.findOne(id));
        return "admin/show_user";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("user", personService.findOne(id));
        return "admin/edit_user";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("user") @Valid Person user,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "admin/edit_user";
        }
        personService.update(id, user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/admin";
    }

}
