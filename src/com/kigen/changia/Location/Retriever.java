///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.kigen.changia.Location;
//
//
//
///**
// *
// * @author kigen
// */
//import com.kigen.changia.ChangiaMidlet;
//import javax.microedition.location.Coordinates;
//import javax.microedition.location.Criteria;
//import javax.microedition.location.Location;
//import javax.microedition.location.LocationProvider;
//public class Retriever extends Thread
//{
//   private ChangiaMidlet midlet;
//   public Retriever(ChangiaMidlet midlet)
//   {
//      this.midlet = midlet;
//   }
//
//   public void run()
//   {
//      try
//      {
//         checkLocation();
//      }
//      catch (Exception ex)
//      {
//         ex.printStackTrace();
//        // midlet.displayString(ex.toString());
//      }
//   }
//
//   public void checkLocation() throws Exception
//   {
//      String string;
//      Location l;
//      LocationProvider lp;
//      Coordinates c;
//
//      Criteria cr= new Criteria();
//      cr.setHorizontalAccuracy(500);
//
//      lp= LocationProvider.getInstance(cr);
//      l = lp.getLocation(60);
//
//      c = l.getQualifiedCoordinates();
//      if(c != null ) {
//         // Use coordinate information
//         double lat = c.getLatitude();
//         double lon = c.getLongitude();
//         //string = "" + lat + ", " + lon;
//       } else {
//         string ="failed";
//       }
//      // midlet.displayLocation(string);
//   }
//}