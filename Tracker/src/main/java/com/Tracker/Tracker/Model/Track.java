package com.Tracker.Tracker.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long track_id;
    private String track_name;
    private String creator;
    private LocalDate created_on;
    private LocalDate published_on;
    private String description;
    public Track(String track_name,String creator,LocalDate created_on,LocalDate published_on,String description)
    {
        this.track_name=track_name;
        this.creator=creator;
        this.created_on=created_on;
        this.published_on=published_on;
        this.description=description;
    }
    public Track() {
        
    }
    public long getTrackId()
    {
        return track_id;
    }
    public String getTrackName()
    {
        return track_name;
    }
    public String getCreator()
    {
        return creator;
    }
    public LocalDate getCreatedOn()
    {
        return created_on;
    }
    public LocalDate getPublishedOn()
    {
        return published_on;
    }
    public String getDescription()
    {
        return description;
    }


    public void setTrackId(long track_id)
    {
        this.track_id=track_id;
    }
    public void setTrackName(String track_name)
    {
        this.track_name=track_name;
    }
    public void setCreator(String creator)
    {
        this.creator=creator;
    }
    public void setCreatedOn(LocalDate created_on)
    {
        this.created_on=created_on;
    }
    public void setPublishedOn(LocalDate published_on)
    {
        this.published_on=published_on;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }


}
