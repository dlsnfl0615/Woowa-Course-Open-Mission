package com.timetable.timetable.lectures;

import com.timetable.timetable.domain.Day;
import com.timetable.timetable.domain.Lecture;
import com.timetable.timetable.domain.TimeSlot;

import java.time.LocalTime;
import java.util.List;

public class Data {
    // 자바, 월 08:30~10:00 (파이썬(lecture5), 알고리즘(lecture8)과 겹침)
    public static final List<TimeSlot> lectureTimes1 =
            List.of(new TimeSlot(Day.MON, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture1 =
            new Lecture("자바", "유재석", "CSC-1", lectureTimes1);

    // 자바, 화 08:30~10:00 (알고리즘(lecture9)과 겹침)
    public static final List<TimeSlot> lectureTimes2 =
            List.of(new TimeSlot(Day.TUE, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture2 =
            new Lecture("자바", "박명수", "CSC-2", lectureTimes2);

    // C++, 수 08:30~10:00 (겹침 없음)
    public static final List<TimeSlot> lectureTimes3 =
            List.of(new TimeSlot(Day.WED, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture3 =
            new Lecture("C++", "정준하", "CSC-3", lectureTimes3);

    // C++, 목 08:30~10:00 (겹침 없음)
    public static final List<TimeSlot> lectureTimes4 =
            List.of(new TimeSlot(Day.THU, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture4 =
            new Lecture("C++", "정형돈", "CSC-4", lectureTimes4);

    // 파이썬, 월 09:00~10:00 (자바(lecture1), 알고리즘(lecture8)과 겹침)
    public static final List<TimeSlot> lectureTimes5 =
            List.of(new TimeSlot(Day.MON, LocalTime.of(9, 0), LocalTime.of(10, 0)));
    public static final Lecture lecture5 =
            new Lecture("파이썬", "길", "CSC-5", lectureTimes5);

    // 파이썬, 화 11:00~12:00 (겹침 없음)
    public static final List<TimeSlot> lectureTimes6 =
            List.of(new TimeSlot(Day.TUE, LocalTime.of(11, 0), LocalTime.of(12, 0)));
    public static final Lecture lecture6 =
            new Lecture("파이썬", "노홍철", "CSC-6", lectureTimes6);

    // 파이썬, 금 08:30~10:00 (겹침 없음)
    public static final List<TimeSlot> lectureTimes7 =
            List.of(new TimeSlot(Day.FRI, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture7 =
            new Lecture("파이썬", "하하", "CSC-7", lectureTimes7);

    // 알고리즘, 월 08:30~10:00 (자바(lecture1), 파이썬(lecture5)과 겹침)
    public static final List<TimeSlot> lectureTimes8 =
            List.of(new TimeSlot(Day.MON, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture8 =
            new Lecture("알고리즘", "유재석", "CSC-1", lectureTimes8);

    // 알고리즘, 화 08:30~10:00 (자바(lecture2)과 겹침)
    public static final List<TimeSlot> lectureTimes9 =
            List.of(new TimeSlot(Day.TUE, LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Lecture lecture9 =
            new Lecture("알고리즘", "박명수", "CSC-2", lectureTimes9);

}
