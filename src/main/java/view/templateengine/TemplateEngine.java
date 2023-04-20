package view.templateengine;

import view.ModelAndView;

import java.io.IOException;

public interface TemplateEngine {
    byte[] compile(byte[] html, ModelAndView modelAndView) throws IOException;
}
