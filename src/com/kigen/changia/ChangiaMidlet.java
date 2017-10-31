/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia;

import com.kigen.changia.Model.Discussion;
import com.kigen.changia.Model.Project;

import com.kigen.changia.Model.Settings;
import com.kigen.changia.Model.SimpleResponse;
import com.kigen.changia.Model.User;
import com.kigen.changia.Model.Volunteer;
import com.kigen.changia.Network.Proxy;
import com.sun.lwuit.Button;
import com.sun.lwuit.Calendar;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.events.SelectionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GridLayout;

import com.sun.lwuit.list.ListModel;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.spinner.Spinner;
import com.sun.lwuit.util.Resources;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import lcduilwuit.Alert;
import lcduilwuit.DateField;

/**
 * @author Seth
 */
public class ChangiaMidlet extends MIDlet implements ActionListener, ProcessListener {

    private Form loginForm,
            mainForm,
            registerForm,
            settingsForm,
            projectListForm,
            projectViewForm,
            newProjectForm,
            SearchForm,
            aboutForm,
            progressForm,
            discussionListForm,
            addDiscussionForm,
            mapviewForm,
            volunteersForm;
    private List projectList,
            discussionList, volunteersList;
    private TextField txtloginuser,
            txtloginpassword,
            txtusername,
            txtemail,
            txtpassword,
            txtphone,
            txtTitle,
            txtMember,
            txtLocation,
            searchtxtField;
    TextArea txtDescription,
            txtRequirements,
            txtdiscuss;
    private Calendar main_calender;
    private Command backCommand,
            exitCommand,
            cancelCommand,
            okCommand,
            logoutCommand,
            registerCommand,
            volunteercommand,
            quitVolunteerCommand,
            viewMapCommand,
            discussCommand;
    //grid buttons
    Button near_Button,
            my_project_Button,
            my_effort_Button,
            search_Button,
            add_project_button,
            Settings_button,
            about_button,
            help_button;
    //Images Variables
    Image near_image,
            my_project_Image,
            my_effort_Image,
            search_Image,
            add_project_Image,
            Settings_Image,
            about_Image,
            help_Image,
            login_Image,
            progress_image;
    //String has been saved. clear controls for new data
    boolean saved = false;
    TimeSelector Start_timeSpinner,
            End_timeSpinner;
    private Transition in, out, fade;
    String prev_form = "home";
    TextField txtSettings_Location,
            txtnear_distance,
            txtsearch_distance,
            txtresults_limit,
            txtCity,
            txtCountry,
            txtLocality;
    Proxy proxy;
    Settings appsettings;
    Label progressStatus,
            camera_image;
    DateField dfstart,
            dfstop;
    Project[] current_projects;
    String current_discussion_project_id = "";
    Font large, small, medium;

    public void startApp() {
        Display.init(this);
        try {
            Resources r = Resources.open("/theme.res");
            UIManager.getInstance().setThemeProps(
                    r.getTheme(r.getThemeResourceNames()[0]));
        } catch (java.io.IOException e) {
        }
        final Spinner speed = Spinner.create(0, 50000, 500, 100);
        int runSpeed = ((Integer) speed.getValue()).intValue();
        out = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, runSpeed);
        in = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, runSpeed);
        fade = CommonTransitions.createFade(2000);
        small = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        medium = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        large = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);
        proxy = new Proxy(this);
        appsettings = new Settings();
        appsettings.read();
        if (appsettings.getUser_id().equals("null")) {
            getLoginForm().show();
        } else {
            getMainForm().show();
        }


    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource().equals(near_Button)) {
                prev_form = Constants.HOME;
                proxy.getNearProjects(appsettings.getUser_id(), appsettings.getLocation(), appsettings.getResults_limit());
                getProgressForm().show();
            }
            if (ae.getSource().equals(my_project_Button)) {
                prev_form = Constants.HOME;
                proxy.getMyProjects(appsettings.getUser_id(), appsettings.getLocation(), appsettings.getResults_limit());
                getProgressForm().show();
            }
            if (ae.getSource().equals(my_effort_Button)) {
                //write code
                prev_form = Constants.HOME;
                proxy.getMyEffortsProjects(appsettings.getUser_id(), appsettings.getLocation(), appsettings.getResults_limit());
                getProgressForm().show();
            }
            // if (ae.getSource().equals(search_Button)) {
            //    getSearchForm().show();
            // }
            if (ae.getSource().equals(add_project_button)) {
                getNewProjectForm().show();
            }

            if (ae.getSource().equals(Settings_button)) {
                getSettingsForm().show();
            }

            if (ae.getSource().equals(about_button)) {
                getAboutForm().show();
            }
        } catch (Exception ex) {
        }
    }

    public Form getLoginForm() {
        if (loginForm == null) {
            loginForm = new Form("Login");
            loginForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            loginForm.addComponent(new Label("User Id"));
            txtloginuser = new TextField("");
            loginForm.addComponent(txtloginuser);

            loginForm.addComponent(new Label("Password"));
            txtloginpassword = new TextField();
            txtloginpassword.setConstraint(TextField.PASSWORD | TextField.SENSITIVE);
            loginForm.addComponent(txtloginpassword);

            Button loginButton = new Button("Login", getLogin_Image());
            loginForm.addComponent(loginButton);
            loginButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    proxy = new Proxy(ChangiaMidlet.this);
                    prev_form = Constants.LOGIN;
                    proxy.login(txtloginuser.getText(), txtloginpassword.getText());
                    getProgressForm().show();
                }
            });


            loginForm.addCommand(getRegisterCommand());
            loginForm.addCommand(getExitCommand());

            loginForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {

                    if (ae.getCommand().equals(getRegisterCommand())) {
                        getRegisterForm().show();
                    }
                    if (ae.getCommand().equals(getExitCommand())) {
                        notifyDestroyed();
                    }
                }
            });
        }

        return loginForm;
    }

    public Form getRegisterForm() {
        if (registerForm == null) {
            registerForm = new Form("Register");
            registerForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            registerForm.addComponent(new Label("Username"));
            txtusername = new TextField("");
            registerForm.addComponent(txtusername);
            registerForm.addComponent(new Label("Password:"));
            txtpassword = new TextField("");
            txtpassword.setConstraint(TextField.PASSWORD);
            registerForm.addComponent(txtpassword);

            registerForm.addComponent(new Label("Email:"));
            txtemail = new TextField("");
            txtemail.setConstraint(TextField.EMAILADDR);
            registerForm.addComponent(txtemail);

            registerForm.addComponent(new Label("Phone"));
            txtphone = new TextField("");
            txtphone.setConstraint(TextField.PHONENUMBER);
            registerForm.addComponent(txtphone);

            registerForm.addCommand(getOkCommand());
            registerForm.addCommand(getCancelCommand());
            registerForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {

                    if (ae.getCommand().equals(getOkCommand())) {
                        //Push server side
                        //Check values
                        if (txtusername.getText().trim().equals("")) {
                            txtusername.setFocus(true);
                        } else if (txtpassword.getText().trim().equals("")) {
                            txtpassword.setFocus(true);
                        } else if (txtemail.getText().trim().equals("")) {
                            txtemail.setFocus(true);
                        } else if (txtphone.getText().trim().equals("")) {
                            txtphone.setFocus(true);
                        } else {
                            //send server

                            User user = new User();
                            user.setEmail(txtemail.getText());
                            System.out.println(user.getEmail());
                            user.setPassword(txtpassword.getText());
                            user.setUsername(txtusername.getText());
                            user.setPhone(txtphone.getText());
                            prev_form = Constants.REGISTER;

                            proxy.register(user);
                            getProgressForm().show();
                        }
                    }

                    if (ae.getCommand().equals(getCancelCommand())) {
                        //
                        getLoginForm().show();
                    }
                }
            });

        }
        return registerForm;
    }

    public Form getMainForm() {
        if (mainForm == null) {
            mainForm = new Form("Ujitolee");
            mainForm.setLayout(new GridLayout(8, 1));

            near_Button = new Button("Nearest projects", getNear_image());
            near_Button.addActionListener(this);
            mainForm.addComponent(near_Button);

            my_project_Button = new Button("My projects", getMy_project_Image());
            my_project_Button.addActionListener(this);
            mainForm.addComponent(my_project_Button);

            my_effort_Button = new Button("Volunteered", getMy_effort_Image());
            my_effort_Button.addActionListener(this);
            mainForm.addComponent(my_effort_Button);

            //search_Button = new Button("Search", getSearch_Image());
            //search_Button.addActionListener(this);
            //mainForm.addComponent(search_Button);

            add_project_button = new Button("Create Project", getAdd_project_Image());
            add_project_button.addActionListener(this);
            mainForm.addComponent(add_project_button);

            Settings_button = new Button("Options", getSettings_Image());
            Settings_button.addActionListener(this);
            mainForm.addComponent(Settings_button);

            about_button = new Button("About", getAbout_Image());
            about_button.addActionListener(this);
            mainForm.addComponent(about_button);

            mainForm.addCommand(getLogoutCommand());
            mainForm.addCommand(getExitCommand());

            mainForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    System.out.println(ae.getSource().toString());
                    if (ae.getCommand().equals(getExitCommand())) {
                        notifyDestroyed();
                    }
                    if (ae.getCommand().equals(getLogoutCommand())) {
                        getLoginForm().show();
                        appsettings.read();
                        appsettings.setUser_id("null");
                        appsettings.setUsername("null");
                    }
                }
            });
        }
        return mainForm;
    }

    public List getProjectList(Project[] project) {
        if (projectList == null) {
            projectList = new List();
        }

        final Project[] project_list = project;
        projectList.setListCellRenderer(new ProjectsRenderer());

        projectList.setModel(new ListModel() {

            public Object getItemAt(int i) {
                return project_list[i % project_list.length];
            }

            public int getSize() {
                return project_list.length;
            }
            int selection;

            public int getSelectedIndex() {
                return selection;
            }

            public void setSelectedIndex(int i) {
                selection = i;
            }

            public void addDataChangedListener(DataChangedListener dl) {
            }

            public void removeDataChangedListener(DataChangedListener dl) {
            }

            public void addSelectionListener(SelectionListener sl) {
            }

            public void removeSelectionListener(SelectionListener sl) {
            }

            public void addItem(Object o) {
            }

            public void removeItem(int i) {
            }
        });
        projectList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                int i = projectList.getSelectedIndex();
                getProjectViewForm(current_projects[i]).show();
            }
        });

        return projectList;
    }

    public Form getProjectListForm(Project[] p) {
        if (projectListForm == null) {
            projectListForm = new Form("Project");
            projectListForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            projectListForm.addCommand(getBackCommand());
            projectListForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    if (ae.getCommand().equals(getBackCommand())) {
                        getMainForm().show();
                    }
                    if (ae.getSource().equals(projectList)) {
                    }
                }
            });
        }
        if (projectList != null) {
            projectListForm.removeComponent(projectList);
        }
        projectListForm.addComponent(getProjectList(p));
        return projectListForm;
    }

    public Form getProjectViewForm(Project  project) {
        final Project internal = project;

        projectViewForm = new Form("Project");
        projectViewForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        //project view
        projectViewForm.setTitle(project.getTitle());
        Label tlabel = new Label("Project Title:");
        tlabel.getStyle().setFont(large, true);
        projectViewForm.addComponent(tlabel);
        Label project_label = new Label(project.getTitle());
        projectViewForm.addComponent(project_label);

        Label dlabel = new Label("Description:");
        dlabel.getStyle().setFont(large, true);
        projectViewForm.addComponent(dlabel);
        TextArea tx = new TextArea(project.getDescription(), 250);
        tx.getStyle().setBorder(Border.createEmpty(), true);
        tx.setEditable(false);
        tx.setFocusable(false);
        projectViewForm.addComponent(tx);


        Label llocation = new Label("Location:");
        llocation.getStyle().setFont(large, true);
        projectViewForm.addComponent(llocation);
        Label location_label = new Label(project.getCity() + "," + project.getLocality());
        projectViewForm.addComponent(location_label);

        Label lmember = new Label("Volunteers needed:");
        lmember.getStyle().setFont(large, true);
        projectViewForm.addComponent(lmember);
        Label members_label = new Label(project.getMembers() + "");
        projectViewForm.addComponent(members_label);

        Label lrmembers = new Label("Requirements:");
        lrmembers.getStyle().setFont(large, true);
        projectViewForm.addComponent(lrmembers);
        TextArea txRequire = new TextArea(project.getRequirements(), 250);
        txRequire.getStyle().setBorder(Border.createEmpty(), true);
        txRequire.setFocusable(false);
        txRequire.setEditable(false);
        projectViewForm.addComponent(txRequire);

        projectViewForm.addComponent(new Label("From: " + project.getStart()));
        projectViewForm.addComponent(new Label("To: " + project.getEnd()));

        final String project_id = project.getId() + "";
        final String gps = project.getGps();
        Button volunteersButton = new Button("Volunteers [" + project.getVolunteers() + "]");
      
        volunteersButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                prev_form = Constants.PROJECT_VIEW;
                proxy.getVolunteers(project_id);
                getProgressForm().show();
            }
        });

        Button discussionsButton = new Button("Discussions [" + project.getDiscussions() + "]");
       
        discussionsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                proxy.getDiscussions(project_id);
                prev_form = Constants.PROJECT_VIEW;
                current_discussion_project_id = project_id;
                getProgressForm().show();

            }
        });

        projectViewForm.addComponent(volunteersButton);
        projectViewForm.addComponent(discussionsButton);

        projectViewForm.addCommand(getBackCommand());
        if (project.isVolunteer() || project.isOwner("")) {
            projectViewForm.addCommand(getQuitVolunteerCommand());
        } else {

            projectViewForm.addCommand(getVolunteercommand());
        }

        projectViewForm.addCommand(getViewMapCommand());

        projectViewForm.addCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                prev_form = Constants.PROJECT_VIEW;
                if (ae.getCommand().equals(getBackCommand())) {
                    projectListForm.show();

                }
                if (ae.getCommand().equals(getVolunteercommand())) {
                    proxy.volunteer(appsettings.getUser_id(), internal.getId() + "");
                    getProgressForm().show();
                }
                if (ae.getCommand().equals(getQuitVolunteerCommand())) {
                    //Quit volunteer.
                    proxy.quitVolunteer(appsettings.getUser_id(), internal.getId() + "");
                    getProgressForm().show();
                }

                if (ae.getCommand().equals(getViewMapCommand())) {
                    //view map
                    String h = Display.getInstance().getCurrent().getHeight() + "";
                    String w = Display.getInstance().getCurrent().getWidth() + "";

                    proxy.getMap(gps, h, w);
                    getProgressForm().show();
                }
            }
        });


        return projectViewForm;
    }
    TextField txt_location, txt_near_distance, txt_result_limit, txt_search_distance;

    public Form getSettingsForm() {
        if (settingsForm == null) {

            settingsForm = new Form("Settings");
            settingsForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

            settingsForm.addComponent(new Label("Country:"));
            txtCountry = new TextField("");
            settingsForm.addComponent(txtCountry);

            settingsForm.addComponent(new Label("City:"));
            txtCity = new TextField("");
            settingsForm.addComponent(txtCity);

            settingsForm.addComponent(new Label("Location:"));
            txt_location = new TextField("Nairobi");
            settingsForm.addComponent(txt_location);

            settingsForm.addComponent(new Label("What is near? [KM]"));
            txt_near_distance = new TextField("20");
            txt_near_distance.setConstraint(TextField.NUMERIC);
            settingsForm.addComponent(txt_near_distance);

            settingsForm.addComponent(new Label("# of Results"));
            txt_result_limit = new TextField("20");
            txt_result_limit.setConstraint(TextField.NUMERIC);
            settingsForm.addComponent(txt_result_limit);

            settingsForm.addComponent(new Label("How far do you search [km]"));
            txt_search_distance = new TextField("100");
            txt_search_distance.setConstraint(TextField.NUMERIC);
            settingsForm.addComponent(txt_search_distance);


            //Load settings!
            if (!appsettings.isnew()) {

                appsettings.read();
                txt_location.setText(appsettings.getLocation());
                txt_near_distance.setText(appsettings.getNear_distance());
                txt_result_limit.setText(appsettings.getResults_limit());
                txt_search_distance.setText(appsettings.getSearch_distance());
                txtCity.setText(appsettings.getCity());
                txtCountry.setText(appsettings.getCountry());
            }
            settingsForm.addCommand(getOkCommand());
            settingsForm.addCommand(getCancelCommand());
            settingsForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {

                    if (ae.getCommand().equals(getOkCommand())) {
                        //Save data.

                        appsettings.setLocation(txt_location.getText());
                        appsettings.setNear_distance(txt_near_distance.getText());
                        appsettings.setResults_limit(txt_result_limit.getText());
                        appsettings.setSearch_distance(txt_search_distance.getText());
                        appsettings.setCity(txtCity.getText());
                        appsettings.setCountry(txtCountry.getText());
                        appsettings.save();
                        getMainForm().show();
                    }
                    if (ae.getCommand().equals(getCancelCommand())) {
                        getMainForm().show();
                    }
                }
            });

        }
        return settingsForm;
    }

    public Form getNewProjectForm() {
        if (newProjectForm == null || saved) {
            saved = false;
            newProjectForm = new Form("Add Project");
            newProjectForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            newProjectForm.addComponent(new Label("Title:"));
            txtTitle = new TextField();
            newProjectForm.addComponent(txtTitle);

            newProjectForm.addComponent(new Label("Description [250]:"));
            txtDescription = new TextArea("", 250);
            newProjectForm.addComponent(txtDescription);

            newProjectForm.addComponent(new Label("Required Members:"));
            txtMember = new TextField();
            txtMember.setConstraint(TextField.NUMERIC);
            newProjectForm.addComponent(txtMember);

            newProjectForm.addComponent(new Label("Requirements:"));
            txtRequirements = new TextArea("", 250);
            newProjectForm.addComponent(txtRequirements);

            newProjectForm.addComponent(new Label("Country:"));
            txtCountry = new TextField(appsettings.getCountry());
            newProjectForm.addComponent(txtCountry);

            newProjectForm.addComponent(new Label("City:"));
            txtCity = new TextField(appsettings.getCity());
            newProjectForm.addComponent(txtCity);

            newProjectForm.addComponent(new Label("Locality:"));
            txtLocality = new TextField(appsettings.getLocation());
            newProjectForm.addComponent(txtLocality);

            dfstart = new DateField("Start:", DateField.DATE);
            newProjectForm.addComponent(dfstart);
            Start_timeSpinner = new TimeSelector();
            newProjectForm.addComponent(Start_timeSpinner);

            dfstop = new DateField("To:", DateField.DATE);
            newProjectForm.addComponent(dfstop);
            End_timeSpinner = new TimeSelector();
            newProjectForm.addComponent(End_timeSpinner);

            newProjectForm.addCommand(getCancelCommand());
            newProjectForm.addCommand(getOkCommand());
            newProjectForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    if (ae.getCommand().equals(getOkCommand())) {

                        String start_time = dfstart.getDateSimpleString() + " " + Start_timeSpinner.GetTime();
                        String stop_time = dfstop.getDateSimpleString() + " " + End_timeSpinner.GetTime();
                        //saved data
                        if (txtDescription.getText().trim().equals("")) {
                            txtDescription.setFocus(true);
                        } else if (txtTitle.getText().trim().equals("")) {
                            txtTitle.setFocus(true);

                        } else if (txtMember.getText().trim().equals("")) {
                            txtMember.setFocus(true);
                        } else if (start_time.trim().equals("")) {
                        } else if (stop_time.trim().equals("")) {
                        } else if (txtCity.getText().trim().equals("")) {
                            txtCity.setFocus(true);
                        } else if (txtCountry.getText().trim().equals("")) {
                            txtCountry.setFocus(true);
                        } else {
                            saved = true;
                            prev_form = Constants.NEW_PROJECT;
                            Project project = new Project();
                            project.setDescription(txtDescription.getText());
                            project.setTitle(txtTitle.getText());
                            project.setRequirements(txtRequirements.getText());
                            project.setMembers(Integer.parseInt(txtMember.getText()));
                            project.setUser(appsettings.getUser_id());
                            project.setStart(start_time);
                            project.setEnd(stop_time);
                            project.setUser(appsettings.getUser_id());
                            project.setCity(txtCity.getText());
                            project.setCountry(txtCountry.getText());
                            project.setLocality(txtLocality.getText());
                            proxy.registerProject(project, appsettings.getUser_id());
                            getProgressForm().show();

                        }
                    }

                    if (ae.getCommand().equals(getCancelCommand())) {
                        getMainForm().show();
                    }

                }
            });


        }
        return newProjectForm;
    }

    public Form getSearchForm() {
        if (SearchForm == null) {
            SearchForm = new Form("Search");
            SearchForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            SearchForm.setTransitionInAnimator(in);
            //searchForm.setTransitionOutAnimator(out);
            Label txtLabel = new Label("Enter your query");
            SearchForm.addComponent(txtLabel);
            searchtxtField = new TextField("");
            SearchForm.addComponent(searchtxtField);
            SearchForm.addCommand(getBackCommand());
            SearchForm.addCommand(getOkCommand());
            SearchForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {

                    if (ae.getCommand().equals(getOkCommand())) {
                        String search_item = searchtxtField.getText();
                    }
                    if (ae.getCommand().equals(getBackCommand())) {

                        getMainForm().show();
                    }
                }
            });
        }

        return SearchForm;
    }

    public Form getProgressForm() {

        if (progressForm == null) {
            progressForm = new Form("..:Please Wait:..");
            progressForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            Label l = new Label("..:Loading:..");
            l.getStyle().setAlignment(Label.CENTER);

            Label imgLabel = new Label(getProgress_image());
            progressStatus = new Label();


            imgLabel.getStyle().setAlignment(Label.CENTER);
            progressForm.addComponent(imgLabel);
            progressForm.addComponent(l);
            progressForm.addComponent(progressStatus);
            progressForm.setTransitionInAnimator(out);
            progressForm.setTransitionOutAnimator(fade);
            progressForm.addCommand(getCancelCommand());
            progressForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    if (ae.getCommand().equals(getCancelCommand())) {
                        //TODO:Implement extra stuff here
                        if (prev_form.equals(Constants.HOME)) {
                            getMainForm().show();
                        }
                        if (prev_form.equals(Constants.LOGIN)) {
                            getLoginForm().show();
                        }
                        if (prev_form.equals(Constants.PROJECT_VIEW)) {
                            projectViewForm.show();
                        }
                        if (prev_form.equals(Constants.PROJECT_LIST)) {
                            projectListForm.show();
                        }
                        if (prev_form.equals(Constants.NEW_PROJECT)) {
                            getNewProjectForm().show();
                        }
                        if (prev_form.equals(Constants.REGISTER)) {
                            getRegisterForm().show();
                        }
                        if (prev_form.equals(Constants.DISCUSSIONS)) {
                            getAddDiscussionForm().show();
                        }
                    }
                }
            });

        }
        return progressForm;
    }

    public Form getAboutForm() {
        if (aboutForm == null) {
            aboutForm = new Form("About");
            aboutForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            aboutForm.setTransitionOutAnimator(out);
            aboutForm.addComponent(new Label("Application:"));
            aboutForm.addComponent(new Label("Ujitolee"));
            aboutForm.addComponent(new Label("Developer:"));
            aboutForm.addComponent(new Label("Seth Kigen"));
            aboutForm.addComponent(new Label("Version:"));
            aboutForm.addComponent(new Label("v 1.0"));
            aboutForm.addComponent(new Label("Support Email:"));
            aboutForm.addComponent(new Label("ujitolee[at]live.com"));
            aboutForm.addComponent(new Label("Twitter Support:"));
            aboutForm.addComponent(new Label("@ujitolee"));
            aboutForm.addComponent(new Label("Description:"));
            aboutForm.addComponent(new Label("Mobilize volunteers"));
            aboutForm.addCommand(getBackCommand());
            aboutForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    if (ae.getCommand().equals(getBackCommand())) {
                        getMainForm().show();
                    }
                }
            });

        }
        return aboutForm;
    }

    public Form getAddDiscussionForm() {
        if (addDiscussionForm == null) {
            addDiscussionForm = new Form("Add discussion");
            addDiscussionForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            txtdiscuss = new TextArea("");
            addDiscussionForm.addComponent(txtdiscuss);
            addDiscussionForm.addCommand(getBackCommand());
            addDiscussionForm.addCommand(getOkCommand());

            addDiscussionForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    if (ae.getCommand().equals(getBackCommand())) {
                        discussionListForm.show();
                    }
                    if (ae.getCommand().equals(getOkCommand())) {
                        Discussion d = new Discussion();
                        d.setContent(txtdiscuss.getText());
                        d.setProject_id(current_discussion_project_id);
                        d.setUser_id(appsettings.getUser_id());
                        proxy.addDiscussion(d);
                        getProgressForm().show();
                    }
                }
            });

        }

        return addDiscussionForm;
    }

    public List getDiscussionList(Discussion[] discussions) {
        if (discussionList == null) {
            discussionList = new List();
        }
        final Discussion[] discuss = discussions;

        discussionList.setListCellRenderer(new DiscussionsRenderer());

        discussionList.setModel(new ListModel() {

            int selected;

            public Object getItemAt(int i) {
                return discuss[i % discuss.length];
            }

            public int getSize() {
                return discuss.length;
            }

            public int getSelectedIndex() {
                return selected;
            }

            public void setSelectedIndex(int i) {
                selected = i;
            }

            public void addDataChangedListener(DataChangedListener dl) {
            }

            public void removeDataChangedListener(DataChangedListener dl) {
            }

            public void addSelectionListener(SelectionListener sl) {
            }

            public void removeSelectionListener(SelectionListener sl) {
            }

            public void addItem(Object o) {
            }

            public void removeItem(int i) {
            }
        });

        return discussionList;
    }

    public Form getDiscussionListForm(Discussion[] discussions) {
        if (discussionListForm == null) {
            discussionListForm = new Form("Discussion");
            discussionListForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            discussionListForm.addCommand(getDiscussCommand());
            discussionListForm.addCommand(getBackCommand());
            discussionListForm.addComponent(getDiscussionList(discussions));
            discussionListForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    if (ae.getCommand().equals(getBackCommand())) {
                        projectViewForm.show();
                    }
                    if (ae.getCommand().equals(getDiscussCommand())) {
                        getAddDiscussionForm().show();
                    }
                }
            });
        } else {
            discussionListForm.removeComponent(discussionList);
            discussionListForm.addComponent(getDiscussionList(discussions));
        }
        return discussionListForm;
    }
    Label mapLabel;

    public Form getMapviewForm(Image img) {
        if (mapviewForm == null) {
            mapviewForm = new Form("Map");
            mapLabel = new Label(img);
            mapviewForm.addComponent(mapLabel);
            mapviewForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            mapviewForm.addCommand(getBackCommand());
            mapviewForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    projectViewForm.show();
                }
            });
        } else {
            mapLabel.setIcon(img);
        }
        return mapviewForm;
    }

    public Form getVolunteersForm(Volunteer[] volunteers) {
        if (volunteersForm == null) {
            volunteersForm = new Form("Volunteers");
            volunteersForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

            volunteersForm.addComponent(getVolunteersList(volunteers));
            volunteersForm.addCommand(getBackCommand());
            volunteersForm.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {

                    if (ae.getCommand().equals(getBackCommand())) {
                        projectViewForm.show();
                    }
                }
            });

        } else {
            volunteersForm.removeComponent(volunteersList);
            volunteersForm.addComponent(getVolunteersList(volunteers));
        }

        return volunteersForm;
    }

    public List getVolunteersList(Volunteer[] volunteers) {

        String[] vs = new String[volunteers.length];
        for (int i = 0; i < volunteers.length; i++) {
            vs[i] = volunteers[i].getUser() + "  [" + volunteers[i].getRole() + "]";
        }
        volunteersList = new List(vs);
        return volunteersList;
    }

//--| Command --| // 
    public Command getBackCommand() {
        if (backCommand == null) {
            backCommand = new Command("Back");
        }
        return backCommand;
    }

    public Command getExitCommand() {
        if (exitCommand == null) {
            exitCommand = new Command("Exit");
        }
        return exitCommand;
    }

    public Command getOkCommand() {
        if (okCommand == null) {
            okCommand = new Command("Ok");
        }
        return okCommand;
    }

    public Command getCancelCommand() {
        if (cancelCommand == null) {
            cancelCommand = new Command("Cancel");
        }
        return cancelCommand;
    }

    public Command getLogoutCommand() {
        if (logoutCommand == null) {
            logoutCommand = new Command("Logout");
        }
        return logoutCommand;
    }

    public Command getRegisterCommand() {
        if (registerCommand == null) {
            registerCommand = new Command("Register");
        }
        return registerCommand;
    }

    public Command getQuitVolunteerCommand() {
        if (quitVolunteerCommand == null) {
            quitVolunteerCommand = new Command("Bail out");
        }
        return quitVolunteerCommand;
    }

    public Command getViewMapCommand() {
        if (viewMapCommand == null) {
            viewMapCommand = new Command("View Map");
        }
        return viewMapCommand;
    }

    public Command getVolunteercommand() {
        if (volunteercommand == null) {
            volunteercommand = new Command("Volunteer");
        }
        return volunteercommand;
    }

    public Command getDiscussCommand() {
        if (discussCommand == null) {
            discussCommand = new Command("Discuss");
        }
        return discussCommand;
    }

    //Images
    public Image getSettings_Image() {
        if (Settings_Image == null) {
            try {
                Settings_Image = Image.createImage("/tools.png");
            } catch (IOException ex) {
            }
        }
        return Settings_Image;
    }

    public Image getAbout_Image() {
        if (about_Image == null) {
            try {
                about_Image = Image.createImage("/info.png");
            } catch (IOException ex) {
            }
        }
        return about_Image;
    }

    public Image getAdd_project_Image() {
        if (add_project_Image == null) {
            try {
                add_project_Image = Image.createImage("/add.png");
            } catch (IOException ex) {
            }
        }
        return add_project_Image;
    }

    public Image getHelp_Image() {

        if (help_Image == null) {
            try {
                help_Image = Image.createImage("/help.png");
            } catch (IOException ex) {
            }
        }
        return help_Image;
    }

    public Image getMy_effort_Image() {
        if (my_effort_Image == null) {
            try {
                my_effort_Image = Image.createImage("/effort.png");
            } catch (IOException ex) {
            }
        }
        return my_effort_Image;
    }

    public Image getMy_project_Image() {
        if (my_project_Image == null) {
            try {
                my_project_Image = Image.createImage("/my.png");
            } catch (IOException ex) {
            }
        }
        return my_project_Image;
    }

    public Image getNear_image() {
        if (near_image == null) {
            try {
                near_image = Image.createImage("/near.png");
            } catch (IOException ex) {
            }
        }
        return near_image;
    }

    public Image getSearch_Image() {
        if (search_Image == null) {
            try {
                search_Image = Image.createImage("/search.png");
            } catch (IOException ex) {
            }
        }
        return search_Image;
    }

    public Image getLogin_Image() {

        if (login_Image == null) {
            try {
                login_Image = Image.createImage("/accept.png");
            } catch (IOException ex) {
            }
        }
        return login_Image;
    }

    public Image getProgress_image() {
        if (progress_image == null) {

            try {
                progress_image = Image.createImage("/progress.png");
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        }
        return progress_image;
    }

    public void onProcessingProjectsComplete(Project[] values, String operation) {
        current_projects = values;
        if (operation.equals(Constants.MY_PROJECTS)) {
            getProjectListForm(values).show();
            projectListForm.setTitle("My projects");
        }
        if (operation.equals(Constants.NEAR_PROJECTS)) {
            getProjectListForm(values).show();
            projectListForm.setTitle("Nearest projects");
        }
        if (operation.equals(Constants.SEARCH)) {
            getProjectListForm(values).show();
            projectListForm.setTitle("Search Results");
        }
        if (operation.equals(Constants.VOLUNTEERED_PROJECTS)) {
            getProjectListForm(values).show();
            projectListForm.setTitle("Volunteered");
        }
    }

    public void onProgressingSimpleComplete(SimpleResponse response, String operation) {
        if (operation.equals(Constants.LOGIN)) {
            if (response.isSucess()) {

                getMainForm().show();
                if (appsettings.isnew()) {
                    appsettings.read();
                    appsettings.setUser_id(response.getId());
                    appsettings.setUsername(txtloginuser.getText());
                    appsettings.save();
                } else {
                    appsettings.read();
                    appsettings.setUser_id(response.getId());
                    appsettings.setUsername(txtloginuser.getText());
                    appsettings.save();
                }

            } else {
                    new Dialog().show("Failed!", "Password is wrong", "Ok", "Cancel");
               getLoginForm().show();
           

            }
        }
        if (operation.equals(Constants.NEW_PROJECT)) {
            if (response.isSucess()) {
                prev_form = Constants.HOME;
                proxy.getMyProjects(appsettings.getUser_id(), appsettings.getLocation(), appsettings.getResults_limit());
                getProgressForm().show();
            } else {
                
                new Dialog().show("Failed!", "Project registration failed", "Ok", "Cancel");
                getNewProjectForm().show();
            }
        }
        if (operation.equals(Constants.REGISTER)) {
            if (response.isSucess()) {
//                new Dialog().show("Success!", "You have registred sucessfully", "Ok", "Dismiss");
                getLoginForm().show();

            } else {

               
                
                 new Dialog().show("Failure!", "Registration failed, try again ", "Ok", "Dismiss");
                 getRegisterForm().show();
            }

        }
        if (operation.equals(Constants.VOLUNTEER)) {

            if (response.isSucess()) {
                new Dialog().show("Success!", "You have volunteered in this project", "Ok", "Dismiss");
                proxy.getMyEffortsProjects(appsettings.getUser_id(), appsettings.getLocation(), appsettings.getResults_limit());
                getProgressForm().show();
            } else {

                
                new Dialog().show("Failure!", "Failed to volunteer! ", "Ok", "Dismiss");
                getProjectViewForm(null).show();
            }

        }
        if (operation.equals(Constants.QUIT_PROJECT)) {
            if (response.isSucess()) {
                new Dialog().show("Success!", "You have sucessfully quit this project", "Ok", "Dismiss");
                proxy.getMyEffortsProjects(appsettings.getUser_id(), appsettings.getLocation(), appsettings.getResults_limit());
                getProgressForm().show();
            } else {


                new Dialog().show("Failure!", "Failed to quit project! ", "Ok", "Dismiss");
                projectViewForm.show();
            }
        }
        if (operation.equals(Constants.ADD_DISCUSSION)) {

            if (response.isSucess()) {
                proxy.getDiscussions(current_discussion_project_id);

            } else {
                discussionListForm.show();
            }
        }
    }

    public void onProcessingImagesComplete(Image values) {
        getMapviewForm(values).show();
    }

    public void onRequestError(String Error, String operation) {

        if (operation.equals(Constants.NEW_PROJECT)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            newProjectForm.show();
        }
        if (operation.equals(Constants.MY_PROJECTS)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getMainForm().show();
        }
        if (operation.equals(Constants.NEAR_PROJECTS)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getMainForm().show();
        }
        if (operation.equals(Constants.SEARCH)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getMainForm().show();
        }
        if (operation.equals(Constants.VOLUNTEER)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getProjectViewForm(null).show();
        }
        if (operation.equals(Constants.LOGIN)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getLoginForm().show();
        }
        if (operation.equals(Constants.NEAR_PROJECTS)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getMainForm().show();
        }
        if (operation.equals(Constants.REGISTER)) {
            new Dialog().show("Errror!", Error, "Ok", "Dismiss");
            getRegisterForm().show();

        }

    }

    public void onProcessingDiscussionsComplete(Discussion[] values, String operation) {
        getDiscussionListForm(values).show();
    }

    public void onProcessingProjectsComplete(Volunteer[] values) {
        getVolunteersForm(values).show();
    }
}
