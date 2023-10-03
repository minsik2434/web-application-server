package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            InputStreamReader input = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine();
            if(line == null){
                return;
            }
            String[] tokens = line.split(" ");
            String url = tokens[1];
            int contentLength = 0;
            while(!line.equals("")){
                line = br.readLine();
                log.debug(line);
                if(line.contains("Content-Length")){
                    contentLength = getContentLength(line);
                }
            }
            if("/user/create".startsWith(url)){
                DataOutputStream dos = new DataOutputStream(out);
                String body = IOUtils.readData(br,contentLength);
                Map<String,String> params = HttpRequestUtils.parseQueryString(body);
                User user = new User(params.get("userId"),params.get("password"),params.get("name"),params.get("email"));
                DataBase.addUser(user);
                response302Header(dos,"/index.html");
            }
            else if("/user/login".startsWith(url)){
                String body = IOUtils.readData(br,contentLength);
                Map<String,String> params = HttpRequestUtils.parseQueryString(body);
                User user = DataBase.findUserById(params.get("userId"));
                if(user == null){
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] f_body = Files.readAllBytes(new File("./webapp/user/login_failed.html").toPath());
                    response200Header(dos,f_body.length);
                    responseBody(dos, f_body);
                    return;
                }
                if(user.getPassword().equals(params.get("password"))){
                    DataOutputStream dos = new DataOutputStream(out);
                    response302SuccessHeader(dos, "/index.html");
                }
                else{
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] f_body = Files.readAllBytes(new File("./webapp/user/login_failed.html").toPath());
                    response200Header(dos,f_body.length);
                    responseBody(dos, f_body);
                }
            }
            else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    private void response302Header(DataOutputStream dos, String url){
        try{
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: "+url + " \r\n");
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            dos.writeBytes("\r\n");
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }

    private void response302SuccessHeader(DataOutputStream dos, String url){
        try{
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: "+url + " \r\n");
            dos.writeBytes("Set-Cookie: login=true \r\n");
            dos.writeBytes("\r\n");
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }


    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private int getContentLength(String line){
        String[] tokens = line.split(" ");
        return Integer.parseInt(tokens[1].trim());
    }
}
