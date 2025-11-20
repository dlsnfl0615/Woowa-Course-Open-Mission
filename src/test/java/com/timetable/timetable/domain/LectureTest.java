package com.timetable.timetable.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LectureTest {
    List<TimeSlot> addedLectureTimes = List.of(new TimeSlot(Day.MON, LocalTime.of(8, 0), LocalTime.of(9, 0)));
    Lecture addedLecture = new Lecture("자바", "최진우", "CSC-1234", "강의실1",addedLectureTimes, false);

    @Test
    void 분반_시간_중복없음() {
        List<TimeSlot> lectureTimes1 = List.of(new TimeSlot(Day.MON, LocalTime.of(10, 30), LocalTime.of(11, 0)));
        Lecture lecture1 = new Lecture("C++", "최진우", "CSC-0987", "강의실1",lectureTimes1, false);

        assertThat(addedLecture.overlapsTimeWith(lecture1)).isFalse();
    }

    @Test
    void 분반_시간_중복있음() {
        List<TimeSlot> lectureTimes1 = List.of(new TimeSlot(Day.MON, LocalTime.of(8, 30), LocalTime.of(10, 0)));
        Lecture lecture1 = new Lecture("자바", "최진우", "CSC-5678", "강의실1",lectureTimes1, false);

        List<TimeSlot> lectureTimes2 = List.of(new TimeSlot(Day.MON, LocalTime.of(8, 30), LocalTime.of(10, 0)));
        Lecture lecture2 = new Lecture("", "최진우", "CSC-5678", "강의실1",lectureTimes2, false);

        List<TimeSlot> lectureTimes3 = List.of(new TimeSlot(Day.MON, LocalTime.of(8, 30), LocalTime.of(10, 0)));
        Lecture lecture3 = new Lecture("c++", "최진우", "CSC-1000", "강의실1",lectureTimes3, false);

        List<TimeSlot> lectureTimes4 = List.of(new TimeSlot(Day.MON, LocalTime.of(8, 30), LocalTime.of(10, 0)));
        Lecture lecture4 = new Lecture("c++", "최진우", "CSC-1000", "강의실1",lectureTimes4, false);

        assertThat(addedLecture.overlapsTimeWith(lecture1)).isTrue();
        assertThat(addedLecture.overlapsTimeWith(lecture2)).isTrue();
        assertThat(addedLecture.overlapsTimeWith(lecture3)).isTrue();
        assertThat(addedLecture.overlapsTimeWith(lecture4)).isTrue();
    }
}