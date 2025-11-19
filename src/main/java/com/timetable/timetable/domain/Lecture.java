package com.timetable.timetable.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lecture {
    private final String lectureName;
    private final String professorName;
    private final String lectureCode;
    private final String lectureRoom;
    private final List<TimeSlot> lectureTimes;
    private final boolean cyberLecture;

    public Lecture(String lectureName, String professorName, String lectureCode, String lectureRoom, List<TimeSlot> lectureTimes, boolean cyberLecture) {
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.lectureCode = lectureCode;
        this.lectureRoom = lectureRoom;
        this.lectureTimes = lectureTimes;
        this.cyberLecture = cyberLecture;
    }

    public boolean overlapsNameWith(Lecture otherLecture) {
        return this.isSameNameAs(otherLecture.lectureName);
    }

    public boolean isSameNameAs(String otherLectureName) {
        return this.lectureName.equals(otherLectureName);
    }

    public boolean isSameCodeAs(String otherLectureCode) {
        return this.lectureCode.equals(otherLectureCode);
    }

    public boolean overlapsTimeWith(Lecture otherLecture) {
        for (TimeSlot timeSlot : this.lectureTimes) {
            if (hasConflictWith(timeSlot, otherLecture.lectureTimes)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasConflictWith(TimeSlot thisTimeSlot, List<TimeSlot> otherLectureTimes) {
        for (TimeSlot otherTimeSlot : otherLectureTimes) {
            if (thisTimeSlot.overlapsWith(otherTimeSlot)) {
                return true;
            }
        }

        return false;
    }

    public void divide(Map<String, List<Lecture>> map) {
        if (!map.containsKey(lectureName)) {
            map.put(lectureName, new ArrayList<>(List.of(this)));
            return;
        }

        map.get(lectureName).add(this);
    }

    public boolean conflictsWithNoLectureDays(List<Day> noLectureDays) {
        boolean conflict = false;

        if (noLectureDays.isEmpty()) {
            return false;
        }

        for (TimeSlot lectureTime : lectureTimes) {
            conflict = lectureTime.isSameDayWith(noLectureDays);

            if (conflict) { // 해당 강의가 공강날이랑 겹치면
                break;
            }
        }

        return conflict;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public String getLectureRoom() {
        return lectureRoom;
    }

    public List<TimeSlot> getLectureTimes() {
        return List.copyOf(lectureTimes);
    }

    public boolean isCyberLecture() {
        return cyberLecture;
    }
}
