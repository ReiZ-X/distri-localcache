package com.reizx.localcache.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author junke
 */
public class AbstractSpElAop {

    public StandardEvaluationContext varContext(JoinPoint joinPoint) {
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(joinPoint.getArgs());
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer
                = new LocalVariableTableParameterNameDiscoverer();
        String[] parametersName = parameterNameDiscoverer.getParameterNames(targetMethod);
        if (null != parametersName && args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                standardEvaluationContext.setVariable(parametersName[i], args[i]);
            }
        }
        return standardEvaluationContext;
    }

    /**
     * 通过key SEL表达式获取值
     */
    public String getElValue(String key, StandardEvaluationContext context) {
        if (null == key || key.isEmpty()) {
            return "";
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(key);

        return exp.getValue(context, String.class);

    }
}
