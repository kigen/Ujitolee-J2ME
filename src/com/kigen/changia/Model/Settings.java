package com.kigen.changia.Model;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class Settings {

    private String user_id = "null";
    private String username = "null";
    private String password = "null";
    private String near_distance = "20";
    private String search_distance = "100";
    private String results_limit = "20";
    private String location = "Nairobi West";
    private String country = "Kenya";
    private String city = "Nairobi";

    public Settings() {
        try {
            rs = RecordStore.openRecordStore("ujitolee", true);
            rs.closeRecordStore();

        } catch (RecordStoreException rsopen) {
        }
    }

    public Settings(boolean read) {
        if (read) {
            read();
        }


    }
    RecordStore rs;

    public Settings(String _url, String _username, String _password, String _distance, String _searchd, String _result_limit, String _location) {
        this.user_id = _url;
        this.username = _username;
        this.password = _password;
        this.near_distance = _distance;
        this.search_distance = _searchd;
        this.results_limit = _result_limit;
        this.location = _location;
        save();
    }

    public boolean isnew() {
        try {
            rs = RecordStore.openRecordStore("ujitolee", true);
            int x = rs.getNumRecords();
            rs.closeRecordStore();
            if (x > 0) {
                return false;

            } else {
                return true;
            }
        } catch (RecordStoreException rsopen) {
            return true;
        }
    }

    public boolean read() {
        try {
            rs = RecordStore.openRecordStore("ujitolee", true);
            if(rs.getNumRecords()==0){
                save();
            }
            byte[] ldata = rs.getRecord(1);
            byte[] tdata = rs.getRecord(2);
            byte[] udata = rs.getRecord(3);
            byte[] ddata = rs.getRecord(4);
            byte[] rdata = rs.getRecord(5);
            byte[] sdata = rs.getRecord(6);
            byte[] lldata = rs.getRecord(7);
            byte[] ctdata = rs.getRecord(8);
            byte[] codata = rs.getRecord(9);

            this.username = new String(ldata);
            this.password = new String(tdata);
            this.user_id = new String(udata);
            this.near_distance = new String(ddata);
            this.results_limit = new String(rdata);
            this.search_distance = new String(sdata);
            this.location = new String(lldata);
            this.city = new String(ctdata);
            this.country = new String(codata);

            rs.closeRecordStore();
            return true;
        } catch (Exception ex) {
            return false;

        }

    }

    public boolean save() {
        try {
            rs = RecordStore.openRecordStore("ujitolee", true);
            //read();

            byte[] ldata = this.username.getBytes();
            byte[] tdata = this.password.getBytes();
            byte[] udata = this.user_id.getBytes();
            byte[] ddata = this.near_distance.getBytes();
            byte[] rdata = this.results_limit.getBytes();
            byte[] sdata = this.search_distance.getBytes();
            byte[] lldata = this.location.getBytes();
            byte[] ctdata = this.city.getBytes();
            byte[] codata = this.country.getBytes();
            if (rs.getNumRecords() == 0) {

                try {
                    rs.addRecord(ldata, 0, ldata.length);
                    rs.addRecord(tdata, 0, tdata.length);
                    rs.addRecord(udata, 0, udata.length);
                    rs.addRecord(ddata, 0, ddata.length);
                    rs.addRecord(rdata, 0, rdata.length);
                    rs.addRecord(sdata, 0, sdata.length);
                    rs.addRecord(lldata, 0, lldata.length);
                    rs.addRecord(ctdata, 0, ctdata.length);
                    rs.addRecord(codata, 0, codata.length);

                } catch (RecordStoreException rse) {
                    return false;
                }

            } else {
                rs.setRecord(1, ldata, 0, ldata.length);
                rs.setRecord(2, tdata, 0, tdata.length);
                rs.setRecord(3, udata, 0, udata.length);
                rs.setRecord(4, ddata, 0, ddata.length);
                rs.setRecord(5, rdata, 0, rdata.length);
                rs.setRecord(6, sdata, 0, sdata.length);
                rs.setRecord(7, lldata, 0, lldata.length);
                rs.setRecord(8, ctdata, 0, ctdata.length);
                rs.setRecord(9, codata, 0, codata.length);
            }
            rs.closeRecordStore();
            return true;
        } catch (Exception rse) {
            return false;
        }

    }

    public String getLocation() {
        return location;
    }

    public String getNear_distance() {
        return near_distance;
    }

    public String getPassword() {
        return password;
    }

    public String getResults_limit() {
        return results_limit;
    }

    public RecordStore getRs() {
        return rs;
    }

    public String getSearch_distance() {
        return search_distance;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNear_distance(String near_distance) {
        this.near_distance = near_distance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResults_limit(String results_limit) {
        this.results_limit = results_limit;
    }

    public void setSearch_distance(String search_distance) {
        this.search_distance = search_distance;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
