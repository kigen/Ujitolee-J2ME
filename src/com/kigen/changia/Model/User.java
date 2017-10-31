/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kigen.changia.Model;

/**
 *
 * @author caleb
 */
public class User {
    
    int id;
    String username;
    String email;
    String phone;
    String twitter;
    String password;
    String bio;

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
