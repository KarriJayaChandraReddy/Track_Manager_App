package com.Tracker.Tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tracker.Tracker.Model.Track;

@Repository
public interface UserRepository extends JpaRepository<Track,Long> {
    
}
