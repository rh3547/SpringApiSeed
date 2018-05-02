package com.example.springapi.payload;

import com.example.springapi.model.Note;

import javax.validation.constraints.NotBlank;

public class NoteRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Note toNote() {
        Note note = new Note();
        note.setTitle(getTitle());
        note.setContent(getContent());
        return note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
