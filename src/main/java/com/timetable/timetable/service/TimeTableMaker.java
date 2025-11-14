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

    public TimeTableMaker(RegisterWizard registerWizard, LectureMatcher lectureMatcher) {
        this.registerWizard = registerWizard;
        this.lectureMatcher = lectureMatcher;
    }

    public List<TimeTable> makeTimeTable(List<String> lectureCodes, List<Lecture> availableLectures, List<Day> noLectureDays) {
        List<Lecture> lecturesToRegister = lectureMatcher.findLecturesWithCode(lectureCodes, availableLectures);
        return registerWizard.makeTimeTable(lecturesToRegister, noLectureDays);
    }
}
