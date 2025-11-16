package com.timetable.timetable.controller;

import com.timetable.timetable.domain.Day;
import com.timetable.timetable.domain.Lecture;
import com.timetable.timetable.domain.TimeSlot;
import com.timetable.timetable.domain.TimeTable;
import com.timetable.timetable.dto.LectureRequest;
import com.timetable.timetable.dto.TimeTableRequest;
import com.timetable.timetable.service.TimeTableMaker;
import com.timetable.timetable.util.ExcelParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TableController {
    @GetMapping("/TimeTableResult")
    public String sendTimeTableResults(Model model) {
        List<Lecture> lectures = List.of(
                new Lecture("C++", "유재석", "CSC-1", "강의실1", List.of(new TimeSlot(Day.MON, LocalTime.of(9,0), LocalTime.of(10,0))), false),
                new Lecture("파이썬", "박명수", "CSC-2", "강의실2",
                        List.of(
                                new TimeSlot(Day.WED, LocalTime.of(9,0), LocalTime.of(10,0)),
                                new TimeSlot(Day.FRI, LocalTime.of(9,0), LocalTime.of(10,0))
                        ), false),
                new Lecture("자아와명상", "정준하", "ABC-1", null, null, true)
        );
        TimeTable timeTable = new TimeTable(lectures);

        TimeTableRequest timeTableRequest = TimeTableRequest.from(timeTable);
        List<LectureRequest> lectureRequests = timeTableRequest.lectures();

        model.addAttribute("lectures", lectureRequests);

        return "TimeTableResult";
    }

    @GetMapping("/RegisterLecture")
    public String getLecturesToRegister(Model model) {
        ExcelParser excelParser = new ExcelParser("excel-data/개설강좌_테스트.xlsx");

        List<LectureRequest> lectureRequests = new ArrayList<>();
        for (Lecture lecture : excelParser.parseExcel()) {
            lectureRequests.add(LectureRequest.from(lecture));
        }

        model.addAttribute("lectureLists", lectureRequests);

        return "RegisterLecture";
    }
}
