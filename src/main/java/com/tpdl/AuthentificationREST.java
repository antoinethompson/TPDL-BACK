package com.tpdl;

import ch.qos.logback.core.status.Status;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@RestController
@RequestMapping(value = "/test")
public class AuthentificationREST {

    @GetMapping(value = "/auth")
    public ResponseEntity auth(){

        String locationString=null;
        try {
            URL url = new URL("https://api.login.yahoo.com/oauth2/request_auth?client_id=dj0yJmk9Q0xRWnl4MEVQVXU2JmQ9WVdrOVNFaHNlWFJSYkVRbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTE4&response_type=code&redirect_uri=https://tpdl-back.herokuapp.com/test/ok");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM) {
                locationString = con.getHeaderField("Location");
            }
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
        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY).location(location).build();
    }

    @GetMapping(value = "/ok")
    public ResponseEntity<String> ok(@RequestParam("code") String authcode, HttpServletResponse httpServletResponse){
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}
