package view;

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

    public void setView(String view) {
        this.view = view;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
