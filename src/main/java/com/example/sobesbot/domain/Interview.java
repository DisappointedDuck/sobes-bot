package com.example.sobesbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Slf4j
public class Interview {
    private LocalDate date;
    private String timeSlot;
    private String applicant;
    private String grade;
    String interviewer;
    String dion;
    String tg;

    public Interview setDate(String date) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            this.date = LocalDate.parse(date, dateTimeFormatter);
        } catch (Exception e){
            log.error("Дата не православная ", e);
            this.date = LocalDate.MIN;
        }
        return this;
    }

    public boolean isItTime(){
        boolean equals = String.valueOf(LocalDateTime.now().getHour() - 1).equals(timeSlot.toString().substring(0, 2));
        return equals;
    }
}
