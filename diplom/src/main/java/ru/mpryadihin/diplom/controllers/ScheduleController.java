package ru.mpryadihin.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mpryadihin.diplom.DTO.ScheduleDTO;
import ru.mpryadihin.diplom.models.Schedule;
import ru.mpryadihin.diplom.servicies.PersonalService;
import ru.mpryadihin.diplom.servicies.ScheduleService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final PersonalService personalService;

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(PersonalService personalService, ScheduleService scheduleService) {
        this.personalService = personalService;
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public String index(Model model
                        , @RequestParam("year")  int year
                        , @RequestParam("month") int month){
        model.addAttribute("monthValue", scheduleService.getMonthValue(month));
        model.addAttribute("scheduleList", scheduleService.findAllByYearAndMonth(year, month));
        model.addAttribute("personal", personalService.findAll());
        model.addAttribute("days", scheduleService.allDays(year, month));
        return "schedule/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit")
    public String edit(Model model,
             @RequestParam("year") int year,
             @RequestParam("month") int month) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setSchedules(scheduleService.findAllByYearAndMonth(year, month));
        model.addAttribute("monthValue", scheduleService.getMonthValue(month));
        model.addAttribute("scheduleListDTO", scheduleDTO);
        model.addAttribute("personal", personalService.findAll());
        model.addAttribute("days", scheduleService.allDays(year, month));
        return "schedule/edit";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateSchedule(@ModelAttribute ScheduleDTO scheduleListDTO,
                                 @RequestParam("year") int year,
                                 @RequestParam("month") int month,
                                 RedirectAttributes redirectAttributes) {
        scheduleService.updateAll(scheduleListDTO.getSchedules());
        redirectAttributes.addAttribute("year", year);
        redirectAttributes.addAttribute("month", month);
        return "redirect:/schedule";
    }

}