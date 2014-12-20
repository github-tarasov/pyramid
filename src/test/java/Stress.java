import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Stress {

    Runnable templateThread = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://localhost:8080/weight/3/1");
                URLConnection connection;
                InputStream stream;
                Reader r;
                StringBuffer result = new StringBuffer();
                int c;
                for (int i = 1; i <= 1000; i++) {
                    connection = url.openConnection();
                    assertEquals("Wrong response code", ((HttpURLConnection) connection).getResponseCode(), 200);
                    stream = connection.getInputStream();
                    r = new InputStreamReader(stream);
                    result.setLength(0);
                    while ((c = r.read()) != -1) {
                        result.append((char)c);
                    }
                    r.close();
                    assertEquals("Wrong result", result.toString(), "106,25");
                }
            } catch (Exception e) {
                fail("Fail to connect");
            }
        }
    };

    @Test
    public void testParamsInURI() {
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(templateThread);
            threads.add(thread);
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
