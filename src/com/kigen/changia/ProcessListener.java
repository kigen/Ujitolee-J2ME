/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kigen.changia;

import com.kigen.changia.Model.Discussion;
import com.kigen.changia.Model.Project;
import com.kigen.changia.Model.SimpleResponse;
import com.kigen.changia.Model.Volunteer;
import com.sun.lwuit.Image;



/**
 *
 * @author Seth
 */
public interface  ProcessListener {
    public void onProcessingProjectsComplete(Project[] values, String operation);
    public void onProcessingProjectsComplete(Volunteer[] values);
    public void onProcessingDiscussionsComplete(Discussion[] values, String operation);
    public void onProgressingSimpleComplete(SimpleResponse response, String operation);
    public void onProcessingImagesComplete(Image values);    
    public void onRequestError(String Error,String operation);
    
}
