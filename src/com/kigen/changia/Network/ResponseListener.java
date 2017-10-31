/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kigen.changia.Network;

import com.sun.lwuit.Image;

/**
 *
 * @author Seth
 */
public interface  ResponseListener {
    	
	public void onRequestComplete(String response);

        public void onImageRequestComplete(Image image);

	public void onRequestError(String code);    
}
