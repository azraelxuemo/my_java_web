import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private DataInputStream inputFromClient;
    private String url;
    private ResponseContent responseContent;
    public HttpRequest(DataInputStream inputFromClient){
        this.inputFromClient=inputFromClient;
        this.responseContent=new ResponseContent();
    }

    public void HandleStaticHttpRequests() throws IOException {
        if(responseContent.getReponseCode()==null) {
        responseContent.setReponseCode("200");
        }
        if(url.endsWith("html")) {
            responseContent.setResponseType("text/html");
        }else if(url.endsWith("js")) {
        responseContent.setResponseType("application/x-javascript");
        }else if(url.endsWith("jpg")) {
        responseContent.setResponseType("image/jpeg");
        }else if(url.endsWith("png")) {
        responseContent.setResponseType("image/png");
        }else if(url.endsWith("ico")) {
        responseContent.setResponseType("image/x-icon");
        }else if (url.endsWith("css")) {
        responseContent.setResponseType("text/css");
        }
        File f = new File(url);
        int length = (int) f.length();
        byte[] data = new byte[length];
        new FileInputStream(f).read(data);
        responseContent.setContent(data);
    }


    public void HandleDynymicHttpRequests() throws IOException, ScriptException {
        responseContent.setReponseCode("200");
        responseContent.setResponseType("text/html");
        BufferedReader reader = new BufferedReader(new FileReader("test.zup"));
        StringBuffer result_neirong = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            Pattern p = Pattern.compile("<%(.+?)%>");
            Matcher m =p.matcher(line);
            if(m.find()){
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("js");
                for (int i = 0; i <= m.groupCount(); i++) {
                    System.out.println(m.group(i));
                }
                Object result = engine.eval(m.group(1).strip());
                String new_line=line.replace(m.group(),result.toString());
                result_neirong.append(new_line).append("\r\n");
            }
            else {
                result_neirong.append(line).append("\r\n");
            }
        }
        responseContent.setContent(result_neirong.toString().getBytes());
    }
    public void HandleNoExistRequests() throws IOException {
        url="src/404.html";
        responseContent.setReponseCode("404");
        HandleStaticHttpRequests();
    }

    public ResponseContent HandleHttpRequests() throws IOException, ScriptException {
        url=this.inputFromClient.readLine().split(" ")[1].substring(1);
        if(url.equals("")){
            url="index.html";
        }
        File file = new File(url);
        if(!file.exists()){ 
            HandleNoExistRequests();
        }else if(url.endsWith("zup"))
        {
            HandleDynymicHttpRequests();
        }
        else{
            HandleStaticHttpRequests();
        }
        return responseContent;
    }
}
