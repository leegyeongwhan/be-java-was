package view;

import application.model.User;

import java.util.*;

public class ModelAndView {

    private String view;
    private Map<String, Object> model = new HashMap<>();

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void addObject(String key, Object value) {
        model.put(key, value);
    }

    public void setModelAttribute(String listName, Collection<User> list) {
        model.put(listName, list);
    }

    public void setModelAttribute(String key, String value) {
        addObject(key, value);
    }

    public Object getModelAttribute(String name) {
        return this.model.get(name);
    }

    public void setView(String view) {
        this.view = view;
    }
}
