/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia.Model;

/**
 *
 * @author Seth
 */
public class SimpleResponse {

    String id;
    boolean sucess;

    public String getId() {
        return id;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }
}
