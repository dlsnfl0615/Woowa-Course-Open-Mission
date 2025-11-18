package com.timetable.timetable.controller;

import com.timetable.timetable.domain.*;
import com.timetable.timetable.dto.ClientRequest;
import com.timetable.timetable.dto.LectureRequest;
import com.timetable.timetable.dto.TimeTableRequest;
import com.timetable.timetable.service.TimeTableMaker;
import com.timetable.timetable.util.ExcelParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class TableController {
    private List<Lecture> allLectures;
    private final String PATH = "excel-data/개설강좌_테스트.xlsx";

    @GetMapping("/RegisterLecture")
    public String getLecturesToRegister(Model model) {
        ExcelParser excelParser = new ExcelParser(PATH);
        allLectures = excelParser.parseExcel();

        List<LectureRequest> lectureRequests = allLectures.stream()
                .map(LectureRequest::from)
                .toList();

        model.addAttribute("searchedLectures", lectureRequests);

        return "RegisterLecture";
    }

    @PostMapping("/receive")
    public String sendTimeTableResults(ClientRequest clientRequest, Model model) {
        List<String> lectureCodesToRegister = clientRequest.lectures();
        List<Day> noLectureDays = Stream.ofNullable(clientRequest.days())
                .flatMap(List::stream)
                .map(Day::getDay)
                .toList();

        TimeTableMaker timeTableMaker = new TimeTableMaker(new RegisterWizard(), new LectureMatcher());
        List<TimeTable> timeTables = timeTableMaker.makeTimeTable(lectureCodesToRegister, allLectures, noLectureDays);

        List<TimeTableRequest> timeTableRequests = new ArrayList<>();
        for (TimeTable timeTable : timeTables) {
            timeTableRequests.add(TimeTableRequest.from(timeTable));
        }

        model.addAttribute("timeTables", timeTableRequests);

        return "TimeTableResult";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchLecture(Model model, @RequestParam("lectureToSearch") String lectureNameToSearch) {
        List<LectureRequest> searchedLectures = new ArrayList<>();

        for (Lecture lecture : allLectures) {
            if (lecture.isSameNameAs(lectureNameToSearch)) {
                searchedLectures.add(LectureRequest.from(lecture));
            }
        }

        if (lectureNameToSearch.isEmpty()) {
            searchedLectures = allLectures.stream()
                    .map(LectureRequest::from)
                    .toList();
        }

        model.addAttribute("searchedLectures", searchedLectures);

        return "RegisterLecture";
    }
}
