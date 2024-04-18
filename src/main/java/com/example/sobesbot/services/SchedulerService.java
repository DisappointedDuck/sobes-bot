package com.example.sobesbot.services;


import com.example.sobesbot.domain.Interview;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    TableFetchingService tableFetchingService;

    @Autowired
    TelegramMessagingService telegramMessagingService;

    private static Set<Interview> interviews;

    @Scheduled(cron = "0 0 10 ? * MON-FRI")
    public void dailyReminder() throws GeneralSecurityException, IOException {
        List<Interview> allInterviews = tableFetchingService.getAllInterviews();
        Set<Interview> filtered = allInterviews.stream()
                .filter(interview -> interview.getDate().equals(LocalDate.now()))
                .filter(interview -> StringUtils.isNotBlank(interview.getApplicant()))
                .collect(Collectors.toSet());
        log.info(allInterviews.toString());
        if(filtered.size() > 0){
            telegramMessagingService.sendTodayInterviews(filtered);
        }
    }

    @Scheduled(cron = "0 55 * ? * MON-FRI")
    public void hourlyReminder() throws GeneralSecurityException, IOException {
        List<Interview> allInterviews = tableFetchingService.getAllInterviews();
        Set<Interview> filtered = allInterviews.stream()
                .filter(interview -> interview.getDate().equals(LocalDate.now()))
                .filter(interview -> StringUtils.isNotBlank(interview.getApplicant()))
                .filter(interview -> interview.isItTime())
                .collect(Collectors.toSet());
        if(filtered.size() > 0){
            telegramMessagingService.sendNowInterviews(filtered);
        }
    }
}
