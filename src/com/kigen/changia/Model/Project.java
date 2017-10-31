/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kigen.changia.Model;


/**
 *
 * @author caleb
 */
public class Project {

    int id;
    String title;
    String description;
    String gps;
    String requirements;
    int members;
    String start;
    String end;
    String user;
    boolean volunteer;
    String country;
    String city;
    String locality;
    String distance;
    String volunteers;
    String discussions;

    public Project() {
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


    public String getDescription() {
        return description;
    }

    public String getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }

    public String getGps() {
        return gps;
    }

    public int getMembers() {
        return members;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getStart() {
        return start;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
     public boolean isVolunteer() {
        return volunteer;
    }

    public void setVolunteer(boolean volunteer) {
        this.volunteer = volunteer;
    }

    public boolean  isOwner(String userId){
        if (this.user.equals(userId)){
            return true;
        }
     return false;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDiscussions() {
        return discussions;
    }

    public void setDiscussions(String discussions) {
        this.discussions = discussions;
    }

    public String getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(String volunteers) {
        this.volunteers = volunteers;
    }

}
