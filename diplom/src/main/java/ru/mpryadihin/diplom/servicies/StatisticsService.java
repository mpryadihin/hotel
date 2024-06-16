package ru.mpryadihin.diplom.servicies;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.repositories.ResidesRepository;

import java.awt.*;
import java.time.Month;
import java.util.*;
import java.util.List;

@Service
public class StatisticsService {

    private final ResidesRepository residesRepository;
    private final ResidesService residesService;

    private final ScheduleService scheduleService;


    @Autowired
    public StatisticsService(ResidesRepository residesRepository, ResidesService residesService, ScheduleService scheduleService) {
        this.residesRepository = residesRepository;
        this.residesService = residesService;
        this.scheduleService = scheduleService;
    }

    public List<String> getYears(){
        List<String> result = new ArrayList<>();
        List<Resides> resides = residesRepository.findAll();
        if (resides.isEmpty()) {
            return Collections.singletonList("Нет данных");
        }
        int temp = resides.get(0).getDateOut().getYear();
        while (temp <= resides.get(resides.size()-1).getDateOut().getYear()){
            result.add(String.valueOf(temp));
            temp++;
        }
        return result;
    }
    private JFreeChart createChartResides(CategoryDataset dataset, int year){

        JFreeChart chart = ChartFactory.createBarChart(
                String.valueOf(year),
                null,                   // x-axis label
                "кол-во",                // y-axis label
                dataset);
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }
    private CategoryDataset createDataResides(int year){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Resides> resides = residesService.findByYear(year);
        HashMap<Month,Integer> map = new HashMap<>();
        for (Month m : Month.values()) {
            dataset.addValue(resides.stream().filter(r -> r.getDateIn().getMonth().equals(m)).count(), "посещаемость", m.toString());
        }
        return  dataset;
    }
    public JFreeChart countResides(int year){
        CategoryDataset dataset = createDataResides(year);
        return createChartResides(dataset, year);
    }

    private CategoryDataset createDataIncomes(int year){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Resides> resides = residesService.findByYear(year);
        HashMap<Month,Integer> map = new HashMap<>();
        int days = 0;
        int incomes = 0;
        for (Month m : Month.values()){
            int salary = scheduleService.getSalary(year, m.getValue());

            for (Resides r : resides){
                if (r.getDateIn().getMonth().equals(r.getDateOut().getMonth()) && r.getDateIn().getMonth().equals(m)){
                    incomes = r.getResultPrice();
                    map.merge(m,incomes, Integer::sum);
                    incomes = 0;
                    continue;
                }
                if (r.getDateIn().getMonth().equals(m) && r.getDateOut().getMonth().getValue() > m.getValue()){
                    days = m.maxLength() - r.getDateIn().getDayOfMonth();
                    incomes = r.getRoom().getPrice() * days;
                    map.merge(m,incomes, Integer::sum);
                    incomes = 0;
                }
                if (r.getDateIn().getMonth().getValue() < m.getValue() && r.getDateOut().getMonth().equals(m)){
                    days = r.getDateOut().getDayOfMonth();
                    incomes = r.getRoom().getPrice() * days;
                    map.merge(m,incomes, Integer::sum);
                    incomes = 0;
                }
                if (!r.getDateIn().getMonth().equals(m) && !r.getDateOut().getMonth().equals(m)) {
                    incomes = 0;
                    map.merge(m,incomes, Integer::sum);
                }
            }
            dataset.addValue(map.get(m)-salary, "рубли", m.toString());
        }
        return dataset;
    }
    private JFreeChart createChartIncomes(CategoryDataset dataset, int year){

        JFreeChart chart = ChartFactory.createBarChart(
                String.valueOf(year),
                null,                   // x-axis label
                "доходность",                // y-axis label
                dataset);
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }
    public JFreeChart countIncomes(int year){
        CategoryDataset dataset = createDataIncomes(year);
        return createChartIncomes(dataset, year);
    }
}
