import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Functional {

    @Test
    public void testParamsInURI() {
        try {
            URL url = new URL("http://localhost:8080/weight/1/0");
            URLConnection connection = url.openConnection();
            assertEquals("Wrong response code", 200, ((HttpURLConnection) connection).getResponseCode());
            InputStream stream = connection.getInputStream();
            Reader r = new InputStreamReader(stream);
            StringBuffer result = new StringBuffer();
            int c;
            while ((c = r.read()) != -1) {
                result.append(String.valueOf((char) c));
            }
            r.close();
            assertEquals("Wrong result", "25", result.toString());
        } catch (Exception e) {
            fail("Fail to connect");
        }
    }

    @Test
    public void testParamsInGET() {
        try {
            URL url = new URL("http://localhost:8080/weight?level=1&index=1");
            URLConnection connection = url.openConnection();
            assertEquals("Wrong response code", 200, ((HttpURLConnection) connection).getResponseCode());
            InputStream stream = connection.getInputStream();
            Reader r = new InputStreamReader(stream);
            StringBuffer result = new StringBuffer();
            int c;
            while ((c = r.read()) != -1) {
                result.append(String.valueOf((char) c));
            }
            r.close();
            assertEquals("Wrong result", "25", result.toString());
        } catch (Exception e) {
            fail("Fail to connect");
        }
    }

    @Test
    public void testBadURI() {
        try {
            URL url = new URL("http://localhost:8080/_eight?level=1&index=1");
            URLConnection connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());

            url = new URL("http://localhost:8080/_eight/1/1");
            connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());
        } catch (Exception e) {
            fail("Fail to connect");
        }
    }

    @Test
    public void testBadParams() {
        try {
            URL url = new URL("http://localhost:8080/weight/?_evel=1&index=1");
            URLConnection connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());

            url = new URL("http://localhost:8080/weight/?level=1");
            connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());

            url = new URL("http://localhost:8080/weight/1/");
            connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());

            url = new URL("http://localhost:8080/weight/1/x");
            connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());

            url = new URL("http://localhost:8080/weight/x/1");
            connection = url.openConnection();
            assertEquals("Wrong response code", 404, ((HttpURLConnection) connection).getResponseCode());

        } catch (Exception e) {
            fail("Fail to connect");
        }
    }

}
