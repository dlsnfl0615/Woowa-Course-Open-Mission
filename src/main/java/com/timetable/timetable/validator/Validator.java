package com.timetable.timetable.validator;

public class Validator {
    private static final String pattern = "[a-zA-Z]{3}[0-9]{4}-[0-9]{2}";
    private static final String NO_BLANK = "학수번호로 공백을 입력할 수 없습니다.";
    private static final String INCORRECT_PATTERN = "학수번호는 [알파벳 세 자리][숫자 네 자리] '-' [숫자 두자리]입니다";

    private Validator() {}

    public static void validateLectureCode(String lectureCode) {
        if (lectureCode == null || lectureCode.isBlank()) {
            throw new IllegalArgumentException(NO_BLANK);
        }

        if (!lectureCode.matches(pattern)) {
            throw new IllegalArgumentException(INCORRECT_PATTERN + " : " + lectureCode);
        }
    }
}
