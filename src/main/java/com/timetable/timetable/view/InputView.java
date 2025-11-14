package com.timetable.timetable.view;

import camp.nextstep.edu.missionutils.Console;


public class InputView {
    public String readLectureCodes() {
        System.out.println("신청을 원하는 강의의 학수번호를 쉼표로 구분해 입력. (예: CSC-1234,CSC-6789)");
        return Console.readLine();
    }

    public String readNoLectureDays() {
        System.out.println("원하는 공강날을 쉼표로 구분해 입력. 원하는 공강날이 없을 시 엔터 입력. (예: 월,수,금)");
        return Console.readLine();
    }
}
