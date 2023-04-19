package webserver;

import http.dto.RequestDto;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestMapper {
    private static final Map<RequestDto, Method> requestMapper = new LinkedHashMap<>();
    private static final String DEFAULT_CONTROLLER_DIRECTORY = "application";
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);


    public static void run() {
        Reflections reflections = new Reflections(DEFAULT_CONTROLLER_DIRECTORY);
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);
        log.debug("controllerAnnotatedClasses = {}", controllerAnnotatedClasses); //usercontroller

        for (Class<?> aClass : controllerAnnotatedClasses) {
            Controller controller = aClass.getAnnotation(Controller.class);
            log.debug("controller = {}", aClass); //usercontroller
            log.debug("controllerAnnotatedClasses = {}", controllerAnnotatedClasses); //usercontroller
        }

        controllerAnnotatedClasses.stream()
                .map(scanRequestMappingAnnotatedMethods())
                .forEach(applyAnnotatedMethods());
    }

    //@RequestMapping이붙은 메서드 탐색
    private static Function<Class<?>, Set<Method>> scanRequestMappingAnnotatedMethods() {
        return aClass -> {
            log.debug("scanRequestMappingAnnotatedMethods = {}", aClass.getName()); //usercontroller
            Set<Method> methodsAnnotatedWith = new Reflections(aClass.getPackage().getName(), new MethodAnnotationsScanner())//
                    .getMethodsAnnotatedWith(RequestMapping.class);
            log.debug("methodsAnnotatedWith = {}", methodsAnnotatedWith); //usercontroller
            return methodsAnnotatedWith;
        };
    }

    private static Consumer<Set<Method>> applyAnnotatedMethods() {
        return classes -> {
            log.debug("applyAnnotatedMethods = {}", classes); //usercontroller
            classes.forEach(addAnnotatedMethodToRequestMapper());
        };
    }

    // 어노테이션이 부여된 메소드를 requestMapper 맵에 추가
    private static Consumer<Method> addAnnotatedMethodToRequestMapper() {
        return annotatedMethod -> {
            log.debug("annotatedMethod = {}", annotatedMethod); //usercontroller
            RequestMapping annotation = annotatedMethod.getAnnotation(RequestMapping.class);
            log.debug("annotation = {}", annotation); //usercontroller
            RequestDto requestDto = new RequestDto(annotation.method(), annotation.path());
            log.debug("mappedRequest = {}", requestDto); //usercontroller
            add(requestDto, annotatedMethod);
        };
    }

    public static void add(RequestDto requestDto, Method method) {
        if (requestMapper.containsKey(requestDto)) {
            return;
            // throw new RuntimeException("중복되는 Reqeust가 있습니다");
        }
        requestMapper.put(requestDto, method);
    }

    public static Method get(RequestDto requestDto) {
        return requestMapper.get(requestDto);
    }

    public Map<RequestDto, Method> map() {
        return requestMapper;
    }
}
