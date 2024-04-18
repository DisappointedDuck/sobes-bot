package com.example.sobesbot.services;

import com.example.sobesbot.domain.Interview;
import com.example.sobesbot.google.GoogleOAuthClient;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TableFetchingService {
    @Value("${google.sheet.id}")
    private String tableId;

    @Value("${google.sheet.range}")
    private String range;

    @Autowired
    private GoogleOAuthClient googleOAuthClient;

    public List<Interview> getAllInterviews() throws GeneralSecurityException, IOException {
        GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service =
                new Sheets.Builder(
                        HTTP_TRANSPORT,
                        JSON_FACTORY,
                        googleOAuthClient.authorizeSheets(HTTP_TRANSPORT, JSON_FACTORY))
                        .setApplicationName("SobesBot")
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(tableId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            throw new RuntimeException("No Data Found");
        }
        values.remove(0);
        return values.stream().map(row -> {
            Interview interview = new Interview()
                    .setDate(fetchAllWithValues(row, 0))
                    .setTimeSlot(fetchAllWithValues(row, 1))
                    .setApplicant(fetchAllWithValues(row, 3))
                    .setGrade(fetchAllWithValues(row, 4))
                    .setDion(fetchAllWithValues(row, 5))
                    .setInterviewer(fetchAllWithValues(row, 6));
        log.info(interview.toString());
        return interview;
        })

                .collect(Collectors.toList());

    }

    public String fetchAllWithValues(List<Object> row, int i){
        String res;
        try {
            res = row.get(i).toString();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
        return res;
    }

}
