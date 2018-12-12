//package com.haier.svc.api.controller.util.intercept;
//
//import org.apache.log4j.LogManager;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.lang.reflect.Method;
//
///**
// * Created by 李超 on 2018/2/8.
// */
//
//@Aspect
//@Component
//public class Interceptor {
//    private final static org.apache.log4j.Logger logger = LogManager.getLogger(Interceptor.class);
//
//    @Pointcut("execution(* com.haier.svc.api.controller..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    public void controllerMethodPointcut(){}
//
//    @Around("controllerMethodPointcut()")
//    public Object Interceptor(ProceedingJoinPoint pjp){
////        long beginTime = System.currentTimeMillis();
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod(); //获取被拦截的方法
//        String methodName = method.getName(); //获取被拦截的方法名
//        logger.info("请求开始，方法："+ methodName);
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//
//        boolean flag = this.isLogin(request,response);
//
//        Object result = null;
//        try {
//            //用户没有登录的情况下
//            if (flag == false) {
//
//                if (methodName.equals("accordion")){
//                    result = pjp.proceed();
//                    return result;
//                }
//                //访问的页面不是登录页面
//                if (!methodName.equals("toLogin")) {
//                    //跳转至登录页面
//                    response.sendRedirect("/login.html");
//                    System.out.println("跳转登录");
//                //访问的页面是登录页面
//                }else {
//                    //放开拦截 继续执行方法
//                    result = pjp.proceed();
//                    return result;
//                }
//            }
//            if (methodName.equals("logout")){
//                request.getSession().invalidate();
//            }
//            //用户登录的情况下 放开拦截 继续执行方法
//             result = pjp.proceed();
//        } catch (Throwable e) {
//            logger.info("exception: ", e);
//        }
//        return result;
//    }
//
//    //判断是否已经登录
//    private boolean isLogin(HttpServletRequest request,HttpServletResponse response) {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("userName") != null) {
//            return true;
//        }
////        return false;
//        return true;
//    }
//}
