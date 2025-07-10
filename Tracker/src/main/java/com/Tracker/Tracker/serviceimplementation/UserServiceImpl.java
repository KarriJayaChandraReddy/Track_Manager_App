package com.Tracker.Tracker.serviceimplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tracker.Tracker.DTO.TrackCreateDTO;
import com.Tracker.Tracker.Model.Track;
import com.Tracker.Tracker.Repository.UserRepository;
import com.Tracker.Tracker.service.UserService;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Track> getAllTracks() {
        List<Track> allTracks= userRepository.findAll();
       return allTracks;
    }

     @Override
    public List<Track> getAllFilterTracks(String filter) {
        List<Track> allTracksF= userRepository.findAll();
        return allTracksF.stream()
            .filter(track -> track.getTrackName().toLowerCase()
            .contains(filter.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public Track getTrack(Long id) {
        Optional<Track> optTrack=userRepository.findById(id);
        if(optTrack.isPresent())
        {
            return optTrack.get();
        }
        return null;
    }

    // @Override
    // public String createTrack(List<TrackCreateDTO> dataList) {
    //     List<Track> tracks=new ArrayList<>();
    //     for(TrackCreateDTO data:dataList)
    //     {
    //         Track track=new Track();
    //         track.setTrackName(data.getTrackName());
    //         track.setCreator(data.getCreator());
    //         track.setCreatedOn(data.getCreatedOn());
    //         track.setPublishedOn(data.getPublishedOn());
    //         track.setDescription(data.getDescription());
    //         //userRepository.save(track);
    //         tracks.add(track);
    //     }
        
    //     userRepository.saveAll(tracks);
    //     return "Tracks Are Created";
    // }


    @Override
    public String createTrack(TrackCreateDTO dto) {
        Track track = new Track(
            dto.getTrackName(),
            dto.getCreator(),
            dto.getCreatedOn(),
            dto.getPublishedOn(),
            dto.getDescription()
        );

        userRepository.save(track);
        return "Track created successfully!";
    }

    @Override
    public boolean deleteTrack(Long id) {
        if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return true;
    }
    return false;
    }

    @Override
    public boolean updateTrack(Long id, TrackCreateDTO updatedData) {
        Optional<Track> optionalTrack = userRepository.findById(id);
    if (optionalTrack.isPresent()) {
        Track track = optionalTrack.get();

        track.setTrackName(updatedData.getTrackName());
        track.setCreator(updatedData.getCreator());
        track.setDescription(updatedData.getDescription());
        track.setCreatedOn(updatedData.getCreatedOn());
        track.setPublishedOn(updatedData.getPublishedOn());

        userRepository.save(track);
        return true;
    }
    return false;
    }

    

    


    
}
