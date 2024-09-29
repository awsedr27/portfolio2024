package com.portfolio.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TransactionAspect {

    private final PlatformTransactionManager transactionManager;

    public TransactionAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Pointcut("execution(* com.portfolio..service..*ServiceImpl.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object applyTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    	//log.info("트랜잭션실행");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(30);  
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            Object result = joinPoint.proceed(); 
            transactionManager.commit(status);    
            return result;
        } catch (Throwable e) {
            transactionManager.rollback(status); 
            throw e;
        }
    }
}
