/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia.Data;

import com.kigen.changia.Model.Discussion;
import com.kigen.changia.Model.Project;

import com.kigen.changia.Model.SimpleResponse;
import com.kigen.changia.Model.User;
import com.kigen.changia.Model.Volunteer;

import org.json.me.*;

/**
 *
 * @author seth
 */
public class DataAdapter {

    public static Project getProject(JSONObject job) {
        Project project = new Project();
        try {
            project.setId(Integer.parseInt(job.getString("id")));
            project.setTitle(job.getString("title"));
            project.setDescription(job.getString("description"));
            project.setGps(job.getString("gps"));
            project.setMembers(Integer.parseInt(job.getString("members")));
            project.setRequirements(job.getString("requirements"));
            project.setStart(job.getString("start"));
            project.setEnd(job.getString("end"));
            project.setUser(job.getString("user"));
            project.setVolunteer(job.getBoolean("volunteer"));
            project.setCountry(job.getString("country"));
            project.setCity(job.getString("city"));
            project.setLocality(job.getString("locality"));
            project.setDistance(job.getString("distance"));
            project.setVolunteers(job.getString("volunteers"));
            project.setDiscussions(job.getString("discussions"));

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return project;
    }

    public static Project[] getProjects(String response) {

        try {

            JSONArray jobarray = new JSONArray(response);
            Project[] project = new Project[jobarray.length()];
            for (int i = 0; i < jobarray.length(); i++) {

                project[i] = getProject(jobarray.getJSONObject(i));
            }
            return project;
        } catch (Exception ex) {
            return null;
        }

    }

    public static User getUser(String response) {
        User user = new User();

        try {
            JSONObject job = new JSONObject(response);
            user.setId(job.getInt("id"));
            user.setPhone(job.getString("phone"));
            user.setUsername(job.getString("username"));
            user.setPassword(job.getString("password"));
            user.setTwitter(job.getString("twitter"));
            user.setBio(job.getString("bio"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static Volunteer getVolunteer(JSONObject job) {
        Volunteer volunteer = new Volunteer();
        try {
            volunteer.setId(job.getString("id"));
            volunteer.setRole(job.getString("role"));
            volunteer.setUser(job.getString("user"));           
            volunteer.setUser_id(job.getString("user_id"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return volunteer;
    }

    public static Volunteer[] getvolunteers(String response) {
        try {
            JSONArray jobarray = new JSONArray(response);
            Volunteer[] volunteers = new Volunteer[jobarray.length()];
            for (int i = 0; i < jobarray.length(); i++) {
                volunteers[i] = getVolunteer(jobarray.getJSONObject(i));
            }

            return volunteers;
        } catch (Exception e) {

            return null;
        }

    }

    public static SimpleResponse getSimpleResponse(String json) {
        SimpleResponse simple = new SimpleResponse();
        try {
            JSONObject job = new JSONObject(json);
            simple.setId(job.getString("id"));
            simple.setSucess(job.getBoolean("sucess"));

        } catch (Exception ex) {
        }
        return simple;
    }

    public static Discussion[] getDiscussions(String response) {
        try {
            JSONArray jarray = new JSONArray(response);
            Discussion[] discussions = new Discussion[jarray.length()];
            for (int i = 0; i < jarray.length(); i++) {
                discussions[i] = getDiscussion(jarray.getJSONObject(i));
            }
            return discussions;
        } catch (Exception ex) {
            return null;
        }

    }

    public static Discussion getDiscussion(JSONObject job) {

        Discussion discussion = new Discussion();

        try {
            discussion.setContent(job.getString("content"));
            discussion.setDate(job.getString("created"));
            discussion.setId(job.getString("id"));
            discussion.setProject_id(job.getString("project_id"));
            discussion.setUser(job.getString("user"));
            discussion.setUser_id(job.getString("user_id"));
        } catch (Exception ex) {
            return null;
        }
        return discussion;
    }
}
