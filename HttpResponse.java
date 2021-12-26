import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private  DataOutputStream outputToClient;
    public HttpResponse(DataOutputStream outputToClient){
        this.outputToClient=outputToClient;
    }

    public void response(ResponseContent responseContent) throws IOException {
           StringBuffer result=new StringBuffer();
            if(responseContent.getReponseCode().equals("200")){
                result.append("HTTP/1.1 200 ok \r\n");
            }else  if(responseContent.getReponseCode().equals("404")){
                result.append("HTTP/1.1 404 Not Found \r\n");
            }
            result.append("Content-Type: "+responseContent.getResponseType()+"\r\n");
            result.append("Content-Language:zh-CN \r\n");
            result.append("Content-Length:" +responseContent.getContent().length + "\r\n\r\n");
            outputToClient.write(result.toString().getBytes());
            outputToClient.write(responseContent.getContent());
            outputToClient.flush();
            outputToClient.close();

    }
}
