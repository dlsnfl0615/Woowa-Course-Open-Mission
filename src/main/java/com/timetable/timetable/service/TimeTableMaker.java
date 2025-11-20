package com.timetable.timetable.service;

import com.timetable.timetable.domain.Lecture;
import com.timetable.timetable.util.LectureMatcher;
import com.timetable.timetable.domain.DFSTimeTableGenerator;
import com.timetable.timetable.domain.TimeTable;
import com.timetable.timetable.domain.Day;

import java.util.List;

public class TimeTableMaker {
    private final DFSTimeTableGenerator DFSTimeTableGenerator;
    private final LectureMatcher lectureMatcher;
    private final List<Lecture> allLectures;

    public TimeTableMaker(DFSTimeTableGenerator DFSTimeTableGenerator, LectureMatcher lectureMatcher, List<Lecture> allLectures) {
        this.DFSTimeTableGenerator = DFSTimeTableGenerator;
        this.lectureMatcher = lectureMatcher;
        this.allLectures = allLectures;
    }

    public List<TimeTable> makeTimeTable(List<String> lectureCodes, List<Day> noLectureDays) {
        List<Lecture> lecturesToRegister = lectureMatcher.findLecturesWithCode(lectureCodes, allLectures);
        return DFSTimeTableGenerator.generate(lecturesToRegister, noLectureDays);
    }
}
