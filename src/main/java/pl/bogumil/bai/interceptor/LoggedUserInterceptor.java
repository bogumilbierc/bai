package pl.bogumil.bai.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.bogumil.bai.helper.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bbierc on 2016-03-31.
 */
public class LoggedUserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SessionHelper sessionHelper;

    // after the handler is executed
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("USER", sessionHelper.getUserFromSession());
    }
}
