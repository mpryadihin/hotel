package ru.mpryadihin.diplom.controllers;

import org.jfree.chart.ChartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.servicies.ResidesService;
import ru.mpryadihin.diplom.servicies.StatisticsService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {


    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    //возвращает форму с кнопками по статистике - кол-во посещений за год по месяцам
    //                                          - доходы за год по месяцам
    @GetMapping()
    public String statistics(Model model){
        model.addAttribute("years", statisticsService.getYears());
        return "statistics/index";
    }

    @GetMapping("/{year}")
    public String show(@PathVariable("year") String year, Model model){
        return "statistics/show";
    }

    //передаем hashmap и выводим диаграмму
    @GetMapping("/{year}/resides")
    public String resides(@PathVariable("year") int year, Model model){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsJPEG(outputStream, statisticsService.countResides(year), 1000, 500);

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] chartByteArray = outputStream.toByteArray();
        model.addAttribute("chartResides", Base64.getEncoder().encodeToString(chartByteArray));
        return "statistics/resides";
    }


    //диаграмма доходов
    @GetMapping("/{year}/incomes")
    public String incomes(@PathVariable("year") int year, Model model){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsJPEG(outputStream, statisticsService.countIncomes(year), 1000, 500);
        } catch (IOException e){
            e.printStackTrace();
        }
        byte[] chartByArray = outputStream.toByteArray();
        model.addAttribute("chartIncomes", Base64.getEncoder().encodeToString(chartByArray));
        return "statistics/incomes";
    }



}
