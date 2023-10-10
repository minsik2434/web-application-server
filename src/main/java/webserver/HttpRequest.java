package webserver;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private String method;
    private String path;
    private Map<String, String> params = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private RequestLine requestLine;
    public HttpRequest(InputStream in){
        try{
            InputStreamReader input = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine();
            if(line == null){
                return;
            }

            requestLine = new RequestLine(line);

            line = br.readLine();
            while(!line.equals("")){
                log.debug("header : {}" , line);
                String[] tokens = line.split(":");
                headers.put(tokens[0].trim(), tokens[1].trim());
                line = br.readLine();
            }

            if("POST".equals(method)){
                String body = IOUtils.readData(br,Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            }
            else{
                params = requestLine.getParams();
            }
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }

    public String getMethod(){
        return requestLine.getMethod();
    }
    public String getPath(){
        return requestLine.getPath();
    }
    public String getHeader(String name){
        return headers.get(name);
    }
    public String getParameter(String name){
        return params.get(name);
    }
}