package com.example.sobesbot.google;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Interview {
    LocalDate date;
    String timeSlot;
    String applicant;
    String grade;
    String interviewer;
    String dion;
    String tg;

    public Interview setDate(String date) {
        this.date = LocalDate.parse(date);
        return this;
    }
}
