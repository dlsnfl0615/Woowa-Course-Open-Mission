package com.timetable.timetable.service;

import com.timetable.timetable.domain.Lecture;
import com.timetable.timetable.domain.LectureMatcher;
import com.timetable.timetable.domain.RegisterWizard;
import com.timetable.timetable.domain.TimeTable;
import com.timetable.timetable.domain.Day;

import java.util.List;

public class TimeTableMaker {
    private final RegisterWizard registerWizard;
    private final LectureMatcher lectureMatcher;
    private final List<Lecture> allLectures;

    public TimeTableMaker(RegisterWizard registerWizard, LectureMatcher lectureMatcher, List<Lecture> allLectures) {
        this.registerWizard = registerWizard;
        this.lectureMatcher = lectureMatcher;
        this.allLectures = allLectures;
    }

    public List<TimeTable> makeTimeTable(List<String> lectureCodes, List<Day> noLectureDays) {
        List<Lecture> lecturesToRegister = lectureMatcher.findLecturesWithCode(lectureCodes, allLectures);
        return registerWizard.makeTimeTable(lecturesToRegister, noLectureDays);
    }
}
