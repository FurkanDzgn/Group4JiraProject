package Pojo.PojoJira;

import java.util.Map;

public class ResponseBodyAuth {

    private Map<String, String> session;
    private Map<String, Object> loginInfo;

    public Map<String, String> getSession() {
        return session;
    }

    public void setSession(Map<String, String> session) {
        this.session = session;
    }

    public Map<String, Object> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Map<String, Object> loginInfo) {
        this.loginInfo = loginInfo;
    }


}
