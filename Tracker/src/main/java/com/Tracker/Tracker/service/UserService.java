package com.Tracker.Tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tracker.Tracker.DTO.TrackCreateDTO;
import com.Tracker.Tracker.Model.Track;

@Service
public interface UserService {
    public List<Track> getAllTracks();

    public List<Track> getAllFilterTracks(String filter);

    public Track getTrack(Long id);

    //public String createTrack(List<TrackCreateDTO> dataList);

    public boolean deleteTrack(Long id);

    public boolean updateTrack(Long id, TrackCreateDTO updatedData);

    public String createTrack(TrackCreateDTO trackDTO);
}
    
