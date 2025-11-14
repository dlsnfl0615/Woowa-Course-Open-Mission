package com.timetable.timetable.domain;

import java.util.List;

public class TimeTable {
    private final List<Lecture> lectures;

    public TimeTable(List<Lecture> registeredLectures) {
        lectures = registeredLectures;
    }

    public boolean isEmpty() {
        return lectures.isEmpty();
    }

    public List<Lecture> getLectures() {
        return List.copyOf(lectures);
    }
}
