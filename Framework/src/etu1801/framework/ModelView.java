package etu1801.framework;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelView {
    private boolean json;
    private final Gson gson;
    private boolean api;

    private String view;
    private HashMap<String, Object> data;
    private HashMap<String, String> session;
    private List<String> removeSession;
    private boolean invalidateSession;

    public ModelView() {
        data = new HashMap<>();
        session = new HashMap<>();
        removeSession = new ArrayList<>();
        json = false;
        gson = new Gson();
        invalidateSession = false;
    }

    public String toJson() {
        return !isApi() ? gson.toJson(data) : gson.toJson(data.get("objectDataResultFramework"));
    }

    public void addItem(String key, Object value) {
        data.put(key, value);
    }

    public void addSession(String key, String value) {
        session.put(key, value);
    }

    public void removeSession(String sessionName) {
        this.getRemoveSession().add(sessionName);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, String> getSession() {
        return session;
    }

    public void setSession(HashMap<String, String> session) {
        this.session = session;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    public boolean isApi() {
        return api;
    }

    public void setApi(boolean api) {
        this.api = api;
    }

    public List<String> getRemoveSession() {
        return removeSession;
    }

    public void setRemoveSession(List<String> removeSession) {
        this.removeSession = removeSession;
    }

    public boolean isInvalidateSession() {
        return invalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        this.invalidateSession = invalidateSession;
    }
}
