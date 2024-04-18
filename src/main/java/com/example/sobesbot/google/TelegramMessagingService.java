package com.example.sobesbot.google;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramMessagingService {
    private static final TelegramBot bot = new TelegramBot("");

    public void sendTodayInterviews(List<Interview> interviews){
        String message = "Собесы на сегодня: \n";
        StringBuilder sb = new StringBuilder(message);
        interviews.forEach(i ->{
           sb.append(i.applicant).append("(грейд ").append(i.grade).append(") в ").append(i.dion).append(" в ").append(i.timeSlot).append(" ").append(i.interviewer);
        });
        bot.execute(new SendMessage("",sb.toString()));
    }
}
