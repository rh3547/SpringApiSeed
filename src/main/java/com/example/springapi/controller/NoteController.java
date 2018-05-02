package com.example.springapi.controller;

import com.example.springapi.exception.AppException;
import com.example.springapi.exception.ResourceNotFoundException;
import com.example.springapi.model.Note;
import com.example.springapi.model.User;
import com.example.springapi.payload.ApiResponse;
import com.example.springapi.payload.ApiResponseData;
import com.example.springapi.payload.NoteRequest;
import com.example.springapi.repository.NoteRepository;
import com.example.springapi.repository.UserRepository;
import com.example.springapi.security.CurrentUser;
import com.example.springapi.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    NoteRepository noteRepo;

    @Autowired
    UserRepository userRepo;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    //===============================================================
    //  GET ALL NOTES
    //===============================================================
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<Note> getAllNotes(@CurrentUser UserPrincipal currentUser) {
        User user = getUserFromPrincipalUser(currentUser);
        return noteRepo.findAllByUser(user);
    }

    //===============================================================
    //  GET A SINGLE NOTE
    //===============================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public Note getNoteById(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long noteId) {
        User user = getUserFromPrincipalUser(currentUser);

        return noteRepo.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    //===============================================================
    //  CREATE A NOTE
    //===============================================================
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createNote(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid NoteRequest noteRequest) {
        User user = getUserFromPrincipalUser(currentUser);
        Note note = noteRequest.toNote();
        note.setUser(user);
        Note result = noteRepo.save(note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{noteId}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseData(true, "Note Created Successfully", result));
    }

    //===============================================================
    //  UPDATE A NOTE
    //===============================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateNote(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long noteId, @Valid @RequestBody NoteRequest noteRequest) {
        User user = getUserFromPrincipalUser(currentUser);
        Note note = noteRepo.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        Note result = noteRepo.save(note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{noteId}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Note Updated Successfully"));
    }

    //===============================================================
    //  DELETE A NOTE
    //===============================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNote(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long noteId) {
        User user = getUserFromPrincipalUser(currentUser);
        Note note = noteRepo.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepo.delete(note);

        return ResponseEntity.ok().build();
    }

    private User getUserFromPrincipalUser(UserPrincipal currentUser) {
        return userRepo.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
    }
}
