package ru.mpryadihin.diplom.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.models.Room;
import ru.mpryadihin.diplom.servicies.PersonalService;
import ru.mpryadihin.diplom.servicies.RoomService;
import ru.mpryadihin.diplom.servicies.TypeRoomService;

@Controller
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    private final TypeRoomService typeRoomService;
    private final PersonalService personalService;
    @Autowired
    public RoomController(RoomService roomService, TypeRoomService typeRoomService, PersonalService personalService) {
        this.roomService = roomService;
        this.typeRoomService = typeRoomService;
        this.personalService = personalService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("rooms", roomService.findAll());
        return "room/index";
    }

    @GetMapping("/freeRooms")
    public String showFreeRooms(Model model){
        model.addAttribute("rooms", roomService.findFreeRooms());
        return "room/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("room", roomService.findOne(id));
        model.addAttribute("date", roomService.findDateResides(id));
        return "room/show";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newRoom(@ModelAttribute("room") Room room,
                          Model model){
        model.addAttribute("types", typeRoomService.findAll());
        model.addAttribute("personal", personalService.findAll());
        return "room/new";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("room", roomService.findOne(id));
        model.addAttribute("types", typeRoomService.findAll());
        model.addAttribute("personal", personalService.findAll());
        return "room/edit";
    }
    @PostMapping()
    public String create(@ModelAttribute("room") @Valid Room room,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeRoomService.findAll());
            model.addAttribute("personal", personalService.findAll());
            return "room/new";
        }

        roomService.save(room);
        return "redirect:/room";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("room") @Valid Room room,
                          BindingResult bindingResult , Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeRoomService.findAll());
            model.addAttribute("personal", personalService.findAll());
            return "room/edit";
        }
        roomService.update(id, room);
        return "redirect:/room";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        roomService.delete(id);
        return "redirect:/room";
    }
}
