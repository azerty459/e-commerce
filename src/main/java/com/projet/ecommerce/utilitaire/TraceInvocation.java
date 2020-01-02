package com.projet.ecommerce.utilitaire;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;


@Aspect
@Component
public class TraceInvocation {

    private static Logger LOGGER = Logger.getLogger(TraceInvocation.class.getName());

    @Pointcut("execution(* com.projet.ecommerce.*.*.*.*(..))")
    public void k(){}//pointcut name

    @Before("k()")
    public void afficherTrace(JoinPoint joinPoint) {

        StringBuffer sb = new StringBuffer();
        Object obj = null;
        String nomMethodeExecutee = joinPoint.getSignature().getName();
        sb.append("Nom méthode :" + nomMethodeExecutee);
        sb.append("Arguments :");
        Arrays.stream(joinPoint.getArgs())
                .forEach(x -> sb.append(x));
        LOGGER.info(String.valueOf(sb));

    }


}
