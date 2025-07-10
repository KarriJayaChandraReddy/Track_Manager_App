package com.Tracker.Tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.Tracker.Tracker.DTO.TrackCreateDTO;
import com.Tracker.Tracker.Model.Track;
import com.Tracker.Tracker.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class UserController {
    @Autowired
    UserService userservice;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Invalid ID: ID must be a number.");
    }
}
   
    @GetMapping("/greet")
    public String greet() {
        return "Welcome To Tracker App";
    }

    // @GetMapping("/getalltracks")
    // public List<Track> getAllTracks() {
    //     return userservice.getAllTracks();
    // }

    @GetMapping("/getalltracks")
    public List<Track> getAllFilterTracks(@RequestParam(required = false) String filter) {
        if(filter==null || filter.isEmpty())
        {
            return userservice.getAllTracks();
        }
        return userservice.getAllFilterTracks(filter);
    }
    

    @GetMapping("/describetracks/{id}")
    public Track describeTracks(@PathVariable Long id) {
            return userservice.getTrack(id);
    }

   @PostMapping("/createtrack")
    public String createTrack(@RequestBody TrackCreateDTO trackDTO) {
        System.out.println("📥 Creating Track: " + trackDTO.getTrackName());
        return userservice.createTrack(trackDTO);
    }

    @DeleteMapping("/deletetrack/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Long id) {
    boolean deleted = userservice.deleteTrack(id);
    if (deleted) {
        return ResponseEntity.ok("Track deleted successfully");
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    @PutMapping("/updatetrack/{id}") 
    ResponseEntity<String> updateTrack(@PathVariable Long id, @RequestBody TrackCreateDTO updatedData) {
    boolean updated = userservice.updateTrack(id, updatedData);
    if (updated) {
        return ResponseEntity.ok("Track updated successfully");
    } else {
        return ResponseEntity.notFound().build();
    }
}

}

    
    

    

