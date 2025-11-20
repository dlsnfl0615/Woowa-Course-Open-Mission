package com.timetable.timetable.controller;

import com.timetable.timetable.domain.*;
import com.timetable.timetable.dto.ClientRequest;
import com.timetable.timetable.dto.LectureRequest;
import com.timetable.timetable.dto.TimeTableRequest;
import com.timetable.timetable.service.TimeTableMaker;
import com.timetable.timetable.util.ExcelParser;
import com.timetable.timetable.util.LectureMatcher;
import com.timetable.timetable.validator.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
        List<String> registerCodes = clientRequest.lectures();

        try {
            if (registerCodes.isEmpty()) {
                throw new IllegalArgumentException("최소 하나 이상의 강의를 신청해야 합니다.");
            }

            for (String code : registerCodes) {
                Validator.validateLectureCode(code);
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());

            // 결과 화면에 필요한 전체 강의 목록 다시 반환
            List<LectureRequest> lectureRequests = allLectures.stream()
                    .map(LectureRequest::from)
                    .toList();
            model.addAttribute("searchedLectures", lectureRequests);

            return "RegisterLecture";
        }

        List<Day> noLectureDays = Stream.ofNullable(clientRequest.days())
                .flatMap(List::stream)
                .map(Day::getDay)
                .toList();
        List<String> spareCodes = Stream.ofNullable(clientRequest.lecturesForSpare())
                .flatMap(List::stream)
                .toList();

        TimeTableMaker timeTableMaker = new TimeTableMaker(new DFSTimeTableGenerator(), new LectureMatcher(), allLectures);
        List<TimeTableRequest> timeTables = makeTimeTable(registerCodes, noLectureDays, timeTableMaker);
        List<TimeTableRequest> spareTimeTables = makeTimeTable(getLectureCodesForSpare(registerCodes, spareCodes), noLectureDays, timeTableMaker);

        model.addAttribute("timeTables", timeTables);
        model.addAttribute("spareTimeTables", spareTimeTables);

        return "TimeTableResult";
    }

    public List<String> getLectureCodesForSpare(List<String> registerCodes, List<String> spareCodes) {
        if (spareCodes.isEmpty()) {
            return Collections.emptyList();
        }

        return registerCodes.stream()
                .filter(code -> !spareCodes.contains(code))
                .toList();
    }

    public List<TimeTableRequest> makeTimeTable(List<String> lectureCodes, List<Day> noLectureDays, TimeTableMaker timeTableMaker) {
        if (lectureCodes.isEmpty()) {
            return Collections.emptyList();
        }

        List<TimeTable> timeTables = timeTableMaker.makeTimeTable(lectureCodes, noLectureDays);

        List<TimeTableRequest> timeTableRequests = new ArrayList<>();
        for (TimeTable timeTable : timeTables) {
            timeTableRequests.add(TimeTableRequest.from(timeTable));
        }

        return timeTableRequests;
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
