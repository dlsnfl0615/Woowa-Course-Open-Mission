package com.timetable.timetable.util;

import com.timetable.timetable.domain.Day;
import com.timetable.timetable.domain.Lecture;
import com.timetable.timetable.domain.TimeSlot;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelParser {
    private final String path;
    private final int START_TIME = 0;
    private final int END_TIME = 1;

    public ExcelParser(String path) {
        this.path = path;
    }

    public List<Lecture> parseExcel() {
        List<Lecture> parsedLectures = new ArrayList<>();

        try (InputStream file = new ClassPathResource(path).getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0); // 첫 번째 시트의 내용 가져오기

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String lectureName = row.getCell(5).getStringCellValue();
                String professorName = row.getCell(6).getStringCellValue();
                String lectureCode = row.getCell(4).getStringCellValue();
                String lectureRoom = row.getCell(8).getStringCellValue();
                String time = row.getCell(7).getStringCellValue();

                addLectures(parsedLectures, lectureName, professorName, lectureCode, lectureRoom, time);
            }
        } catch (IOException e) {
            System.out.println("파일을 열 수 없습니다.");
            e.printStackTrace();
        }

        return parsedLectures;
    }

    public void addLectures(List<Lecture> parsedLectures, String lectureName, String professorName, String lectureCode, String lectureRoom, String time) {
        // 사이버강의이면
        if (time.isEmpty()) {
            parsedLectures.add(new Lecture(lectureName, professorName, lectureCode, null, null, true));
            return;
        }

        List<TimeSlot> lectureTimes = parseLectureTimes(time);
        parsedLectures.add(new Lecture(lectureName, professorName, lectureCode, lectureRoom, lectureTimes, false));
    }

    // 강의 시간 가져오기
    public List<TimeSlot> parseLectureTimes(String lectureTime) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        String removeSpace = deleteSpace(lectureTime);
        String[] lectureTimeParts = removeSpace.split(",");

        for (int current = 0; current < lectureTimeParts.length; current++) {
            int next = current + 1;

            // 두 요일의 강의 시간이 같은 경우, 시간이 표기된 강의의 시간을 복사해옴
            if (next < lectureTimeParts.length && lectureTimeParts[current].length() == 1) {
                lectureTimeParts[current] += lectureTimeParts[next].substring(1);
            }
        }

        for (String part : lectureTimeParts) {
            Day day = Day.getDay(part.substring(0, 1));
            List<LocalTime> startEndTime = extractLocalTime(part);

            timeSlots.add(new TimeSlot(day, startEndTime.get(START_TIME), startEndTime.get(END_TIME)));
        }

        return timeSlots;
    }

    public List<LocalTime> extractLocalTime(String lectureTime) {
        // 24시간 형태로 각 자릿수를 정확히 포함하도록
        Pattern pattern = Pattern.compile("\\b([01]\\d|2[0-3]):[0-5]\\d\\b");
        Matcher matcher = pattern.matcher(lectureTime);

        List<LocalTime> matchedTimes = new ArrayList<>();
        while (matcher.find()) {
            String[] splitTime = matcher.group().split(":");
            int hour = Integer.parseInt(splitTime[0]);
            int minute = Integer.parseInt(splitTime[1]);

            matchedTimes.add(LocalTime.of(hour, minute));
        }

        return matchedTimes;
    }

    public String deleteSpace(String str) {
        String result = "";

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                result += str.charAt(i);
            }
        }

        return result;
    }
}
