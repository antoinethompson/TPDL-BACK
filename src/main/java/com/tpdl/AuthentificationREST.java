package com.tpdl;

import ch.qos.logback.core.status.Status;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@RestController
@RequestMapping(value = "/test")
public class AuthentificationREST {

    @GetMapping
    public ResponseEntity auth(){

        String locationString=null;
        try {
            URL url = new URL("https://api.login.yahoo.com/oauth2/request_auth?client_id=dj0yJmk9bHU5b2h1a0ZLdkFUJmQ9WVdrOWRteEdPREJIWm1rbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWUx&redirect_uri=https://tpdl-back.herokuapp.com/test/ok&response_type=code");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod("GET");
            System.out.println(con.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM) {
                locationString = con.getHeaderField("Location");
                URL newUrl = new URL(locationString);
                //con = (HttpURLConnection) newUrl.openConnection();
            }

            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println(content);
            in.close();

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        java.net.URI location = null;
        try {
            location = new java.net.URI(locationString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED.MOVED_PERMANENTLY).location(location).build();
    }

    @GetMapping(value = "ok")
    public void ok(){
        System.out.println("ok");
    }
}
