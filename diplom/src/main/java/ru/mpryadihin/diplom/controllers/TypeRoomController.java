package ru.mpryadihin.diplom.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.models.TypeRoom;
import ru.mpryadihin.diplom.servicies.TypeRoomService;

@Controller
@RequestMapping("/typeRoom")
public class TypeRoomController {
    private final TypeRoomService typeRoomService;
    @Autowired
    public TypeRoomController(TypeRoomService typeRoomService) {
        this.typeRoomService = typeRoomService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("type", typeRoomService.findAll());
        return "typeRoom/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("type", typeRoomService.findOne(id));
        return "typeRoom/show";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newType(@ModelAttribute("type") TypeRoom typeRoom){
        return "typeRoom/new";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("type", typeRoomService.findOne(id));
        return "typeRoom/edit";
    }
    @PostMapping()
    public String create(@ModelAttribute("type") @Valid TypeRoom type,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "typeRoom/new";
        typeRoomService.save(type);
        return "redirect:/typeRoom";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("type") @Valid TypeRoom type,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "typeRoom/edit";
        typeRoomService.update(id, type);
        return "redirect:/typeRoom";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        typeRoomService.delete(id);
        return "redirect:/typeRoom";
    }
}
