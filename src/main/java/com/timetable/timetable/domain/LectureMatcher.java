package com.timetable.timetable.domain;

import java.util.ArrayList;
import java.util.List;

public class LectureMatcher {
    public List<Lecture> findLecturesWithCode(List<String> lectureCodes, List<Lecture> availLectures) {
        List<Lecture> matchedLectures = new ArrayList<>();
        for (Lecture lecture : availLectures) {
            if (matches(lecture, lectureCodes)) {
                matchedLectures.add(lecture);
            }
        }

        return matchedLectures;
    }

    public boolean matches(Lecture lecture, List<String> lectureCodes) {
        for (String lectureCode : lectureCodes) {
            if (lecture.isSameCodeAs(lectureCode)) {
                return true;
            }
        }

        return false;
    }
}
