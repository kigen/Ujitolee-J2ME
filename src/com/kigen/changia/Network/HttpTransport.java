/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia.Network;


import com.sun.lwuit.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/*/**
 *
 * @author Seth
 */
public class HttpTransport implements Runnable {

    private String baseurl = "http://ujitolee.com/";
    //private String baseurl = "http://192.168.16.1/changia/";
    private String additional_url = "";
    ResponseListener _response;
    String operation = "";
    String action;
    String controller;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAdditional_url() {
        return additional_url;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setAdditional_url(String additional_url) {
        this.additional_url = additional_url;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getUrl() {
        return baseurl;
    }

    public void setUrl(String url) {
        this.baseurl = url;
    }
    private String serverData;

    public String getServerData() {
        return serverData;
    }

    public void setServerData(String serverData) {
        this.serverData = serverData;
    }

    /****
     * Url encoder
     * */
    private String EncodeURL(String URL) {
        URL = replace(URL, '�', "%E0");
        URL = replace(URL, '�', "%E8");
        URL = replace(URL, '�', "%E9");
        URL = replace(URL, '�', "%EC");
        URL = replace(URL, '�', "%F2");
        URL = replace(URL, '�', "%F9");
        URL = replace(URL, '$', "%24");
        URL = replace(URL, '#', "%23");
        URL = replace(URL, '�', "%A3");
        URL = replace(URL, '@', "%40");
        URL = replace(URL, '\'', "%27");
        URL = replace(URL, ' ', "%20");

        return URL;
    }

    /****
     *This scans the baseurl and then encodes it char by char
     * */
    private String replace(String source, char oldChar, String dest) {
        String ret = "";
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) != oldChar) {
                ret += source.charAt(i);
            } else {
                ret += dest;
            }
        }
        return ret;
    }

    public void setResponse(ResponseListener _response) {
        this._response = _response;
    }

    public HttpTransport(String url, ResponseListener _res, String _operation) {
        this.additional_url = url;
        this.serverData = "null";
        this._response = _res;
        this.operation = _operation;
    }

    public HttpTransport() {
        this.serverData = "null";
    }

    public HttpTransport(String url) {
        this.additional_url = url;
    }

    /****
     * this function is used to make an http GET request to the server
     * */
    public void doget() throws IOException {

        InputStream is = null;
        StringBuffer sb = null;
        HttpConnection http = null;

        try {
            System.out.println("HTTP started.....");
            String URI = urlBiulder();
            URI = EncodeURL(URI);
            System.out.println(URI);
            // establish the connection
            http = (HttpConnection) Connector.open(URI);
            // set the request method as GET
            http.setRequestMethod(HttpConnection.GET);
            // server response
            if (http.getResponseCode() == HttpConnection.HTTP_OK) {
                sb = new StringBuffer();
                int ch;
                is = http.openInputStream();
                while ((ch = is.read()) != -1) {
                    sb.append((char) ch);
                }

                this.serverData = sb.toString();
                System.out.println(this.serverData);
                _response.onRequestComplete(serverData);

                http.close();


            } else {

                System.out.println("Network error");

                this.serverData = " Data Error";
                _response.onRequestError(serverData);

            }
        } catch (Exception e) {

            System.err.println("Error: " + e.toString());
            this.serverData = " Network Error, ensure you are connected";
            _response.onRequestError(serverData);
        } finally {
            http.close();
        }
    }

    public void dopost() throws IOException {
        HttpConnection hcon = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        StringBuffer responseMessage = new StringBuffer();
        // the request body
        String requeststring = "This is a POST.";

        try {
            // an HttpConnection with both read and write access
            System.out.println("HTTP started.....");
            String URI = urlBiulder();
            URI = EncodeURL(URI);
            System.out.println(URI);

            hcon = (HttpConnection) Connector.open(URI, Connector.READ_WRITE);

            // set the request method to POST
            hcon.setRequestMethod(HttpConnection.POST);
            hcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // obtain DataOutputStream for sending the request string
            dos = hcon.openDataOutputStream();
            byte[] request_body = getInternal_project().getBytes();

            // send request string to server
            for (int i = 0; i < request_body.length; i++) {
                dos.writeByte(request_body[i]);
            }

            // obtain DataInputStream for receiving server response
            dis = new DataInputStream(hcon.openInputStream());

           
            int ch;
            while ((ch = dis.read()) != -1) {
                responseMessage.append((char) ch);
            }
            this.serverData =responseMessage.toString();
            _response.onRequestComplete(serverData);
            System.out.println(serverData);

        } catch (Exception e) {
            e.printStackTrace();
            _response.onRequestError("Network Error");
        } finally {
           
            try {
                if (hcon != null) {
                    hcon.close();
                }
                if (dis != null) {
                    dis.close();
                }
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    public void doGetImage() throws IOException {

        HttpConnection hc = null;
        DataInputStream in = null;

        try {
            String url = image_url;
            hc = (HttpConnection) Connector.open(url);
            int length = (int) hc.getLength();
            byte[] data = null;
            if (length != -1) {
                data = new byte[length];
                in = new DataInputStream(hc.openInputStream());
                in.readFully(data);
            } else {
                // If content length is not given, read in chunks.
                int chunkSize = 512;
                int index = 0;
                int readLength = 0;
                in = new DataInputStream(hc.openInputStream());
                data = new byte[chunkSize];
                do {
                    if (data.length < index + chunkSize) {
                        byte[] newData = new byte[index + chunkSize];
                        System.arraycopy(data, 0, newData, 0, data.length);
                        data = newData;
                    }
                    readLength = in.read(data, index, chunkSize);
                    index += readLength;
                } while (readLength == chunkSize);
                length = index;
            }
            Image image = Image.createImage(data, 0, length);

            _response.onImageRequestComplete(image);
        } catch (IOException ioe) {
            _response.onRequestError(ioe.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (hc != null) {
                    hc.close();
                }
            } catch (IOException ioe) {
            }
        }




    }

    public String urlBiulder() {
        return this.baseurl + additional_url;
    }
    boolean isImage = false;
    boolean isPost = false;
    String image_url = "";
    String internal_project;

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }

    public boolean isIsPost() {
        return isPost;
    }

    public void setIsPost(boolean isPost) {
        this.isPost = isPost;
    }

    public String getInternal_project() {
        return internal_project;
    }

    public void setInternal_project(String internal_project) {
        this.internal_project = internal_project;
    }

    public void run() {
        try {
            if (isImage) {
                doGetImage();
            } else if (isPost) {
                dopost();
            } else {
                doget();
            }
        } catch (Exception ex) {
        }
    }
}
