package ru.mpryadihin.diplom.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.servicies.ClientService;
import ru.mpryadihin.diplom.servicies.ResidesService;
import ru.mpryadihin.diplom.servicies.RoomService;
import ru.mpryadihin.diplom.util.ResidesValidator;

@Controller
@RequestMapping("/resides")
public class ResidesController {

    private final ResidesService residesService;
    private final ClientService clientService;
    private final RoomService roomService;
    private final ResidesValidator residesValidator;

    @Autowired
    public ResidesController(ResidesService residesService, ClientService clientService, RoomService roomService, ResidesValidator residesValidator) {
        this.residesService = residesService;
        this.clientService = clientService;
        this.roomService = roomService;
        this.residesValidator = residesValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("resides", residesService.findAll());
        return "resides/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("resides", residesService.findOne(id));
        return "resides/show";
    }
    @GetMapping("/new")
    public String newResides(@ModelAttribute("resides") Resides resides,
                             Model model){
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "resides/new";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("resides")
                        @Valid Resides resides, Model model){
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("rooms", roomService.findAll());
       return "resides/edit";
    }
    @PostMapping()
    public String create(@ModelAttribute("resides") @Valid Resides resides,
                         BindingResult bindingResult, Model model){
        residesValidator.validate(resides, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("rooms", roomService.findAll());
            return "resides/new";
        }
        residesService.save(resides);
        return "redirect:/resides";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("resides") @Valid Resides resides,
                         BindingResult bindingResult, Model model){
        residesValidator.validate(resides, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("rooms", roomService.findAll());
            return "resides/edit";
        }
        residesService.update(id,resides);
        return "redirect:/resides";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        residesService.delete(id);
        return "redirect:/resides";
    }

}
