/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia.Network;

import com.kigen.changia.Constants;
import com.kigen.changia.Data.DataAdapter;
import com.kigen.changia.Model.*;
import com.kigen.changia.ProcessListener;
import com.sun.lwuit.Image;

/**
 * @author kigen
 */
public class Proxy implements ResponseListener {

    public Proxy(ProcessListener listener) {
        this.listener = listener;
    }
    ProcessListener listener;

    public void login(String user, String pass) {
        HttpTransport http = new HttpTransport("mobile/login/?" + "user=" + user + "&pass=" + pass);
        http.setResponse(this);
        try {
            operation = Constants.LOGIN;
            new Thread(http).start();


        } catch (Exception e) {
        }
    }

    public void register(User user) {
        HttpTransport http = new HttpTransport("mobile/register/?" + "user=" + user.getUsername() + "&pass=" + user.getPassword() + "&phone=" + user.getPhone() + "&email=" + user.getEmail());
        http.setResponse(this);
        try {
            operation = Constants.REGISTER;
            new Thread(http).start();

        } catch (Exception e) {
        }


    }

    public void getProjects(String user, String location, int limit) {

        HttpTransport http = new HttpTransport("mobile/list_projects/?" + "user=" + user + "&location=" + location + "&actions=all&limit=" + limit);
        http.setResponse(this);
        try {
            operation = Constants.PROJECTS;
            new Thread(http).start();

        } catch (Exception e) {
        }


    }

    public void getMyProjects(String user, String location, String limit) {


        HttpTransport http = new HttpTransport("mobile/list_projects/?" + "user=" + user + "&location=" + location + "&actions=my&limit=" + limit);
        http.setResponse(this);
        try {
            operation = Constants.MY_PROJECTS;
            new Thread(http).start();

        } catch (Exception e) {
        }


    }

    public void getMyEffortsProjects(String user, String location, String limit) {


        HttpTransport http = new HttpTransport("mobile/list_projects/?" + "user=" + user + "&location=" + location + "&actions=efforts&limit=" + limit);
        http.setResponse(this);
        try {
            operation = Constants.VOLUNTEERED_PROJECTS;
            new Thread(http).start();

        } catch (Exception e) {
        }


    }

    public void getNearProjects(String user, String current_location, String limit) {

        HttpTransport http = new HttpTransport("mobile/list_projects/?" + "user=" + user + "&location=" + current_location + "&actions=near&limit=" + limit);
        http.setResponse(this);
        try {
            operation = Constants.NEAR_PROJECTS;
            new Thread(http).start();

        } catch (Exception e) {
        }

    }

    public void registerProject(Project project, String user) {

        String params =
                "title="
                + project.getTitle().trim()
                + "&description="
                + project.getDescription().trim()
                + "&location="
                + project.getLocality().trim()
                + "&requirements="
                + project.getRequirements().trim()
                + "&members="
                + project.getMembers()
                + "&start="
                + project.getStart().trim()
                + "&end="
                + project.getEnd().trim()
                + "&user="
                + user
                + "&city="
                + project.getCity().trim()
                + "&country="
                + project.getCountry().trim()
                + "&locality="
                + project.getLocality().trim();

        HttpTransport http = new HttpTransport("mobile/add_project/");
        http.setIsPost(true);
        http.setInternal_project(params);
        http.setResponse(this);
        try {
            operation = Constants.NEW_PROJECT;
            new Thread(http).start();


        } catch (Exception e) {
        }
    }

    public void volunteer(String user_id, String project_id) {

        HttpTransport http = new HttpTransport("mobile/volunteer/?" + "user_id=" + user_id + "&project_id=" + project_id);
        http.setResponse(this);
        try {
            operation = Constants.VOLUNTEER;
            new Thread(http).start();
        } catch (Exception e) {
        }
    }

    public void quitVolunteer(String user_id, String project_id) {

        HttpTransport http = new HttpTransport("mobile/quit_volunteer/?" + "user_id=" + user_id + "&project_id=" + project_id);
        http.setResponse(this);
        try {

            operation = Constants.QUIT_PROJECT;
            new Thread(http).start();


        } catch (Exception e) {
        }
    }

    public void addDiscussion(Discussion discuss) {
        HttpTransport http = new HttpTransport(
                "mobile/add_discussion/?user_id="
                + discuss.getUser_id()
                + "&project_id="
                + discuss.getProject_id()
                + "&content="
                + discuss.getContent());
        http.setResponse(this);
        try {

            operation = Constants.ADD_DISCUSSION;
            new Thread(http).start();


        } catch (Exception e) {
        }
    }

    public void getDiscussions(String project_id) {
        HttpTransport http = new HttpTransport("mobile/list_discussion/" + project_id);
        http.setResponse(this);
        try {

            operation = Constants.DISCUSSIONS;
            new Thread(http).start();


        } catch (Exception e) {
        }
    }

    public void getVolunteers(String project_id) {
        HttpTransport http = new HttpTransport("mobile/list_volunteers/" + project_id);
        http.setResponse(this);
        try {

            operation = Constants.VOLUNTEERS;
            new Thread(http).start();


        } catch (Exception e) {
        }
    }

    public void getMap(String location,String h, String w) {
        String url = "http://m.ovi.me/?nord&app_id=958qdpVNlBwKkGV09SV9&h="+h+"&w="+w+"&z=14&f=1&c=" + location;
        HttpTransport hrequest = new HttpTransport(url, this, "map");
        hrequest.setImage_url(url);
        hrequest.setIsImage(true);
        new Thread(hrequest).start();
    }
    String operation = "";

    public void onRequestComplete(String response) {
        SimpleResponse s = new SimpleResponse();
        if (operation.equals(Constants.PROJECTS)
                | operation.equals(Constants.MY_PROJECTS)
                | operation.equals(Constants.NEAR_PROJECTS)
                | operation.equals(Constants.SEARCH)
                | operation.equals(Constants.VOLUNTEERED_PROJECTS)) {
            listener.onProcessingProjectsComplete(DataAdapter.getProjects(response), operation);

        } else if (operation.equals(Constants.DISCUSSIONS)) {
            listener.onProcessingDiscussionsComplete(DataAdapter.getDiscussions(response), operation);
        } else if (operation.equals(Constants.VOLUNTEERS)) {
            listener.onProcessingProjectsComplete( DataAdapter.getvolunteers(response));
        } else {
            s = DataAdapter.getSimpleResponse(response);
            listener.onProgressingSimpleComplete(s, operation);
        }
    }

    public void onImageRequestComplete(Image image) {
        listener.onProcessingImagesComplete(image);
    }

    public void onRequestError(String code) {
        listener.onRequestError(code, operation);


    }

    public String encode(String s) {
        StringBuffer sb = new StringBuffer("");


        char c;


        for (int i = 0; i
                < s.length(); i++) {
            c = s.charAt(i);


            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                sb.append(c);


                continue;


            }

            if (c > 15) {
                sb.append("%" + Integer.toHexString((int) c));


            } else {
                sb.append("%0" + Integer.toHexString((int) c));


            }
        }
        return sb.toString();

    }
}
