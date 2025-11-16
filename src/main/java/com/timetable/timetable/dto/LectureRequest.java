package com.timetable.timetable.dto;

import com.timetable.timetable.domain.Lecture;

import java.util.ArrayList;
import java.util.List;

public record LectureRequest(
        String lectureName,
        String professorName,
        String lectureCode,
        String lectureRoom,
        List<TimeSlotRequest> times,
        boolean cyberLecture
) {
    public static LectureRequest from(Lecture lecture) {
        if (lecture.isCyberLecture()) {
            return new LectureRequest(
                    lecture.getLectureName(),
                    lecture.getProfessorName(),
                    lecture.getLectureCode(),
                    null,
                    null,
                    lecture.isCyberLecture()
            );
        }

        return new LectureRequest(
                lecture.getLectureName(),
                lecture.getProfessorName(),
                lecture.getLectureCode(),
                lecture.getLectureRoom(),
                lecture.getLectureTimes().stream()
                        .map(TimeSlotRequest::from)
                        .toList(),
                lecture.isCyberLecture()
        );
    }
}