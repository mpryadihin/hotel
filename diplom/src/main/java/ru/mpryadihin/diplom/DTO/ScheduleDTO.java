package ru.mpryadihin.diplom.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mpryadihin.diplom.models.Schedule;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private List<Schedule> schedules;

    public void addSchedule(Schedule schedule){
        this.schedules.add(schedule);
    }
}
