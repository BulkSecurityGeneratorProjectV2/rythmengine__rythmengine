/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal;

import org.rythmengine.RythmEngine;
import org.rythmengine.extension.ICodeType;
import org.rythmengine.extension.IFormatter;
import org.rythmengine.utils.S;

import java.util.*;


public class ExtensionManager {

    private final Set<IJavaExtension> _extensions = new HashSet<IJavaExtension>();
    private final List<IFormatter> _fmts = new ArrayList<IFormatter>();
    private final RythmEngine engine;

    public ExtensionManager(RythmEngine engine) {
        if (null == engine) throw new NullPointerException();
        this.engine = engine;
    }

    /**
     * Add a Java extension
     *
     * @param javaExtension
     */
    public void registerJavaExtension(IJavaExtension javaExtension) {
        _extensions.add(javaExtension);
    }

    Iterable<IJavaExtension> javaExtensions() {
        return _extensions;
    }

    /**
     * Is a specified method name a java extension?
     *
     * @param s
     * @return true if the name is a java extension
     */
    public boolean isJavaExtension(String s) {
        for (IJavaExtension ext : _extensions) {
            if (S.isEqual(s, ext.methodName())) {
                return true;
            }
        }
        return false;
    }

    public ExtensionManager registerUserDefinedParsers(IParserFactory... parsers) {
        return registerUserDefinedParsers(null, parsers);
    }

    /**
     * Register a special case parser to a dialect
     * <p/>
     * <p>for example, the play-rythm plugin might want to register a special case parser to
     * process something like @{Controller.actionMethod()} or &{'MSG_ID'} etc to "japid"
     * and "play-groovy" dialects
     *
     * @param dialect
     * @param parsers
     */
    public ExtensionManager registerUserDefinedParsers(String dialect, IParserFactory... parsers) {
        engine.dialectManager().registerExternalParsers(dialect, parsers);
        return this;
    }

    private List<IExpressionProcessor> expressionProcessors = new ArrayList<IExpressionProcessor>();

    public ExtensionManager registerExpressionProcessor(IExpressionProcessor p) {
        if (!expressionProcessors.contains(p)) expressionProcessors.add(p);
        return this;
    }

    public Iterable<IExpressionProcessor> expressionProcessors() {
        return expressionProcessors;
    }

    private List<ICodeType> codeTypeList = new ArrayList<ICodeType>();

    public ExtensionManager registerCodeType(ICodeType type) {
        codeTypeList.add(type);
        return this;
    }

    public Iterable<ICodeType> templateLangs() {
        return codeTypeList;
    }

    public boolean hasTemplateLangs() {
        return !codeTypeList.isEmpty();
    }

    public ExtensionManager registerFormatter(IFormatter fmt) {
        _fmts.add(fmt);
        return this;
    }

    public Iterable<IFormatter> formatters() {
        return new Iterable<IFormatter>() {
            @Override
            public Iterator<IFormatter> iterator() {
                return _fmts.iterator();
            }
        };
    }

}
