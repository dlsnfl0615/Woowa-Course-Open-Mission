package com.timetable.timetable.domain;

import org.junit.jupiter.api.Test;
import com.timetable.timetable.dto.TimeTableRequest;
import com.timetable.timetable.lectures.Data;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterWizardTest {
    List<Lecture> lectureToRegister = List.of(Data.lecture1, Data.lecture2, Data.lecture3, Data.lecture4, Data.lecture5, Data.lecture6, Data.lecture7);
    RegisterWizard wizard = new RegisterWizard();

    @Test
    void 공강날짜지정_제작성공() {
        List<Day> noLectureDays = List.of(Day.FRI);
        List<TimeTable> timeTables = wizard.makeTimeTable(lectureToRegister, noLectureDays);

        for (int i = 0; i < timeTables.size(); i++) {
            TimeTable table = timeTables.get(i);
            TimeTableRequest request = TimeTableRequest.from(table);

            System.out.printf("%d번째 시간표\n", i + 1);
            request.lectures().forEach(lecture -> {
                System.out.printf("강의명: %s, 교수명: %s, 학수번호: %s\n", lecture.lectureName(), lecture.professorName(), lecture.lectureCode());
                lecture.times().forEach(time -> {
                    System.out.printf("%s, %s ~ %s\n", time.day(), time.start(), time.end());
                });
            });

            System.out.println();
        }
    }

    @Test
    void 공강날짜지정_제작실패() {
        List<Day> noLectureDays = List.of(Day.MON, Day.TUE, Day.WED, Day.THU, Day.FRI);
        List<TimeTable> timeTables = wizard.makeTimeTable(lectureToRegister, noLectureDays);

        assertThat(timeTables.isEmpty()).isTrue();
    }
}