package view;

import application.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(String view) {
        this.view = view;
    }

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
        int count = 0;
        for (User user : list) {
            addObject("user userId" + count, user.getUserId());
            addObject("user password" + count, user.getPassword());
            addObject("user name" + count, user.getName());
            addObject("user email" + count, user.getEmail());
            count++;
        }
    }

    public void setModelAttribute(String key, String value) {
        addObject(key, value);
    }
}
