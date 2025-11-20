package com.timetable.timetable.domain;

import java.util.List;

public interface TimeTableGenerator {
    List<TimeTable> generate(List<Lecture> lecturesToRegister, List<Day> noLectureDays);
}
