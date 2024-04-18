package com.example.sobesbot.services;

import com.example.sobesbot.domain.Interview;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TelegramMessagingService {

    @Autowired
    TelegramBot telegramBot;

    @Value("${telegram.chat.id}")
    private String chatId;

    public void sendTodayInterviews(Collection<Interview> interviews) {
        String header = "Собесы на сегодня: \n";
        telegramBot.execute(new SendMessage(chatId, buildMessage(interviews, header)));
    }

    public void sendNowInterviews(Collection<Interview> interviews) {
        String header = "Собес через 5 минут: \n";
        telegramBot.execute(new SendMessage(chatId, buildMessage(interviews, header)));
    }

    String buildMessage(Collection<Interview> interviews, String startMessage) {
        StringBuilder sb = new StringBuilder(startMessage);
        interviews.forEach(i ->
                sb.append(i.getApplicant())
                        .append(StringUtils.isBlank(i.getGrade()) ? " - грейд " : "")
                        .append(i.getGrade()).append(" в ")
                        .append(i.getDion()).append(" в ")
                        .append(i.getTimeSlot()).append(" ")
                        .append(i.getInterviewer()).append("\n"));
        return sb.toString();
    }
}
