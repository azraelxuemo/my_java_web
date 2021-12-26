public class ResponseContent {
    private String ReponseCode=null;
    private String ResponseType=null;
    private  byte[] Content;

    public void setReponseCode(String reponseCode) {
        ReponseCode = reponseCode;
    }
    public void setResponseType(String responseType) {
        ResponseType = responseType;
    }
    public void setContent(byte[] content) { Content = content; }

    public String getResponseType() {
        return ResponseType;
    }

    public String getReponseCode() {
        return ReponseCode;
    }

    public byte[] getContent() {
        return Content;
    }

}
