package com.example.sobesbot.google;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    TableFetchingService tableFetchingService;

    @Autowired
    TelegramMessagingService telegramMessagingService;

    private static Set<Interview> interviews;

    @Scheduled(fixedDelay = 10000)
    public void checkTable() throws GeneralSecurityException, IOException {
        List<Interview> allInterviews = tableFetchingService.getAllInterviews();
        log.info(allInterviews.toString());
    }
}
