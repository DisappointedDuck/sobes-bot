package com.example.sobesbot.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableFetchingService {
    private final String tableId = "1tHhMy1EUuhV2JHRfZsZqpOic123w_1UDEnQ7hsaLoTw";
    private final String range = "Sheet1!A:D";
    private final GoogleOAuthClient googleOAuthClient;

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

        return values.stream().map(row ->
                        new Interview()
                                .setDate(row.get(1).toString())
                                .setTimeSlot(row.get(2).toString())
                                .setApplicant(row.get(4).toString())
                                .setGrade(row.get(5).toString())
                                .setDion(row.get(6).toString())
                                .setInterviewer(row.get(7).toString()))
                .collect(Collectors.toList());

    }

}
