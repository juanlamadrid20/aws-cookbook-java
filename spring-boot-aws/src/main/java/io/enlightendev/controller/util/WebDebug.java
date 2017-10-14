package io.enlightendev.controller.util;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Juan on 5/9/17.
 */
public class WebDebug {
    
    private static final Logger log = LogManager.getLogger(WebDebug.class);
    
    public static String print(HttpServletRequest request) throws IOException {

        StringBuilder sb = new StringBuilder();

        sb.append(" -------------- PARAMETERS ----------------").append('\n');

        Map<String, String[]> params = request.getParameterMap();
        sb.append(printMap(params)).append('\n');

        sb.append(" -------------- HEADERS ----------------").append('\n');

        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements()){

            String header = headers.nextElement();
            String line = "Header: " + header + ", " + request.getHeader(header);
            sb.append(line).append('\n');

        }

        sb.append(" -------------- Remote Addr ----------------").append('\n');

        sb.append(request.getRemoteAddr()).append('\n');

        log.debug("WEB DEBUG (REQUEST): \n\t" + sb.toString());

        return sb.toString();
    }

    public static String printBody(HttpServletRequest request) throws IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader reader = request.getReader();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }

        log.debug("WEB DEBUG (BODY): \n\t" + sb.toString());

        return sb.toString();
    }

    public static String printMap(Map mp) {

        StringBuilder sb = new StringBuilder();

        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            String line = pair.getKey() + " = " + pair.getValue().toString();
            sb.append(line).append('\n');

            it.remove(); // avoids a ConcurrentModificationException
        }

        return sb.toString();

    }

}
