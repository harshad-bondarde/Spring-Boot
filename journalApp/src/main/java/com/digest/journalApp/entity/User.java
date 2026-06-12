package com.digest.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Getter
public class User {
    @Id //by writing this will map to id of mongodb object
    private ObjectId id;
    @Indexed(unique=true) // to craete this write spring.data.mongodb.auto-index-creation=true
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
