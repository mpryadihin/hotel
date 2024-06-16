package ru.mpryadihin.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mpryadihin.diplom.security.PersonDetails;
import ru.mpryadihin.diplom.servicies.*;

@Controller
@RequestMapping("/main")
public class MainController {

    private final ClientService clientService;
    private final JobService jobService;
    private final PersonalService personalService;
    private final ResidesService residesService;
    private final RoomService roomService;
    private final StatisticsService statisticsService;
    private final TypeRoomService typeRoomService;

    @Autowired
    public MainController(ClientService clientService,
                          JobService jobService, PersonalService personalService,
                          ResidesService residesService, RoomService roomService,
                          StatisticsService statisticsService, TypeRoomService typeRoomService) {
        this.clientService = clientService;
        this.jobService = jobService;
        this.personalService = personalService;
        this.residesService = residesService;
        this.roomService = roomService;
        this.statisticsService = statisticsService;
        this.typeRoomService = typeRoomService;
    }
    @GetMapping
    public String index(Model model){
        model.addAttribute("resides", residesService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("jobs", jobService.findAll());
        model.addAttribute("persons", personalService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("statistics", statisticsService.getYears());
        model.addAttribute("types", typeRoomService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        return "main/start";
    }
}
