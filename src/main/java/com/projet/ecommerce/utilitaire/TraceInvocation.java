package com.projet.ecommerce.utilitaire;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;


@Aspect
public class TraceInvocation {

    private static Logger LOGGER = Logger.getLogger(TraceInvocation.class.getName());

    @Before("execution(* com.projet.ecommerce.persistance.repository.*.*.*(..))")
    public void afficherTrace(JoinPoint joinPoint) {
        System.out.println("uiogyjgi");
        LOGGER.info("test");
        StringBuffer sb = new StringBuffer();
        Object obj = null;
//        String nomMethodeExecutee = proceedingJoinPoint.getSignature().getName();
//        sb.append("Nom méthode :" + nomMethodeExecutee);
//        sb.append("Arguments :");
//        Arrays.stream(proceedingJoinPoint.getArgs())
//                .forEach(x -> sb.append(x));
        LOGGER.info(String.valueOf(sb));

    }


}
