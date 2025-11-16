package com.timetable.timetable.controller;

import com.timetable.timetable.domain.Day;
import com.timetable.timetable.domain.Lecture;
import com.timetable.timetable.domain.TimeSlot;
import com.timetable.timetable.domain.TimeTable;
import com.timetable.timetable.dto.LectureRequest;
import com.timetable.timetable.dto.TimeTableRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.util.List;

@Controller
public class TableController {
    @GetMapping("/TimeTable")
    public String sendTimeTables(Model model) {
        List<Lecture> lectures = List.of(
                new Lecture("C++", "유재석", "CSC-1", List.of(new TimeSlot(Day.MON, LocalTime.of(9,0), LocalTime.of(10,0))), false),
                new Lecture("파이썬", "박명수", "CSC-2", List.of(new TimeSlot(Day.WED, LocalTime.of(9,0), LocalTime.of(10,0))), false)
        );
        TimeTable timeTable = new TimeTable(lectures);

        TimeTableRequest timeTableRequest = TimeTableRequest.from(timeTable);
        List<LectureRequest> lectureRequests = timeTableRequest.lectures();

        for (LectureRequest request : lectureRequests) {
            System.out.println(request.lectureName());
        }

        System.out.println("asdf");
        for (LectureRequest lectureRequest : lectureRequests) {
            System.out.println(lectureRequest.lectureName());
        }

        model.addAttribute("lectures", lectureRequests);

        return "TimeTable";
    }
}
