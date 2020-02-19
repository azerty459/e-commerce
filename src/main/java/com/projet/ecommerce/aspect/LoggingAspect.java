package com.projet.ecommerce.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(* com.projet.ecommerce..business..*(..))")
    public void log(JoinPoint joinPoint) throws Throwable {

        Object arguments[] = joinPoint.getArgs();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] argNames = codeSignature.getParameterNames();
        System.out.println("         * * * * * LOGGER * * * * * ");
        System.out.println("Business call : " +
                onlyClassName(joinPoint.getTarget().getClass().toString()) +
                "." + joinPoint.getSignature().getName() + "(" +
                arguments(argNames, arguments) + ")");
    }

    private String onlyClassName(String wholeName){
        return wholeName.substring(wholeName.lastIndexOf('.')+1);
    }

    protected String arguments(String[] argNames, Object[] arguments) {
        StringBuffer logStr = new StringBuffer();
        for (int i = 0; i < arguments.length; i++) {
            logStr.append((i>0 ? ", ":"")+ argNames[i] + "="
                    + arguments[i]);
        }
        return logStr.toString();
    }

    private String toStringIfNotNull(Object o) {
        if (o==null) return "null";
        else return o.toString();
    }
}
