package view;

public class ModelAndView {

    private String viewName;
    private MyView myView;
    private Model model;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView() {
    }

    public String getViewName() {
        return viewName;
    }

    public void setView(MyView viewName) {
        this.myView = viewName;
    }
}
