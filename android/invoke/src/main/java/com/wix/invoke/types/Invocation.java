package com.wix.invoke.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by rotemm on 10/10/2016.
 */
public class Invocation {
    Target target;
    String method;
    Object[] args;

    public Invocation() {

    }

    public Invocation(Target target, String method, Object... args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Invocation(Object value) {
        HashMap params = (HashMap) value;
        HashMap targetParams = (HashMap) params.get("target");
        String type = (String) targetParams.get("type");
        Object ParamValue = targetParams.get("value");

        this.target = new Target(type, ParamValue);
        this.method = (String) params.get("method");
        ArrayList args = (ArrayList) params.get("args");

        setArgs(args.toArray());
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            Object argument = args[i];
            if (argument instanceof HashMap) {
                String type = (String) ((HashMap) argument).get("type");
                Object value = ((HashMap) argument).get("value");
                if (type.equals("Integer")) {
                    argument = (int) value;
                } else if (type.equals("Float")) {
                    argument = new Float((Double) value);
                } else if (type.equals("Double")) {
                    argument = (double) value;
                } else if (type.equals("String")) {
                    argument = (String) value;
                }

                args[i] = argument;
            }
        }

        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invocation)) return false;

        Invocation that = (Invocation) o;

        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return Arrays.equals(args, that.args);

    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}