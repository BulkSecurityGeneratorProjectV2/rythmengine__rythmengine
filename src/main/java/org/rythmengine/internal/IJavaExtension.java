/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal;

import java.util.regex.Pattern;

/**
 * <code>IJavaExtension</code> defines meta structure used by rythm to "extend" an expression.
 * <p/>
 * This enable rythm to provide the functionality of Java extension concept used in playframework
 * http://www.playframework.org/documentation/1.2.4/templates#extensions
 * <p/>
 * User: luog
 * Date: 21/02/12
 * Time: 6:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IJavaExtension {
    String extend(String s, String signature);

    Pattern pattern1();

    Pattern pattern2();

    String methodName();
    
    static class VoidParameterExtension implements IJavaExtension {
        private String methodName = null;
        private String fullMethodName = null;
        private Pattern pattern1 = null;
        private Pattern pattern2 = null;
        private boolean requireTemplate = false;

        public VoidParameterExtension(String waiveName, String name, String fullName) {
            this(waiveName, name, fullName, false);
        }

        public VoidParameterExtension(String waiveName, String name, String fullName, boolean requireTemplate) {
            methodName = name;
            fullMethodName = fullName;
            pattern1 = Pattern.compile(String.format(".*(?<!%s)\\.(?i)%s\\s*\\(\\s*\\)\\s*$", waiveName, methodName));
            pattern2 = Pattern.compile(String.format("\\.(?i)%s\\s*\\(\\s*\\)\\s*$", methodName));
            this.requireTemplate = requireTemplate;
         }

        @Override
        public Pattern pattern1() {
            return pattern1;
        }

        @Override
        public Pattern pattern2() {
            return pattern2;
        }


        @Override
        public String extend(String s, String signature) {
            String ptn = "%s(%s)";
            if (requireTemplate) {
                ptn = "%s(__template(), %s)";
            }
            return String.format(ptn, fullMethodName, s);
        }

        @Override
        public String methodName() {
            return methodName;
        }

    }

    static class ParameterExtension implements IJavaExtension {
        private String methodName = null;
        private String fullMethodName = null;
        private Pattern pattern1 = null;
        private Pattern pattern2 = null;
        private boolean requireTemplate = false;
        private boolean lastParam = false;

        public ParameterExtension(String waiveName, String name, String signature, String fullName) {
            this(waiveName, name, signature, fullName, false);
        }
        
        public ParameterExtension(String waiveName, String name, String signature, String fullName, boolean requireTemplate) {
            this(waiveName, name, signature, fullName, requireTemplate, false);
        }
        
        public ParameterExtension(String waiveName, String name, String signature, String fullName, boolean requireTemplate, boolean lastParam) {
            methodName = name;
            fullMethodName = fullName;
            pattern1 = Pattern.compile(String.format(".*(?<!%s)\\.%s\\s*\\((\\s*%s?\\s*)\\)\\s*$", waiveName, methodName, signature));
            pattern2 = Pattern.compile(String.format("\\.%s\\s*\\((\\s*%s?\\s*)\\)\\s*$", methodName, signature));
            this.requireTemplate = requireTemplate;
            this.lastParam = lastParam;
        }

        @Override
        public Pattern pattern1() {
            return pattern1;
        }

        @Override
        public Pattern pattern2() {
            return pattern2;
        }

        @Override
        public String extend(String s, String signature) {
            String ptn = "%s(%s, %s)";
            if (requireTemplate) {
                ptn = "%s(__template(), %s, %s)";
            }
            if (lastParam) {
                return String.format(ptn, fullMethodName, signature, s);
            } else {
                return String.format(ptn, fullMethodName, s, signature);
            }
        }

        @Override
        public String methodName() {
            return methodName;
        }

    }

}
