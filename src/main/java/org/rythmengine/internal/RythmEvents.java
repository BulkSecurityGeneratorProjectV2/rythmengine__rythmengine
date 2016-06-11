/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal;

import org.rythmengine.internal.compiler.TemplateClass;
import org.rythmengine.template.ITemplate;
import org.rythmengine.template.TemplateBase;
import org.rythmengine.utils.F;

/**
 * Built in {@link IEvent event}s
 */
public class RythmEvents<RETURN, PARAM> implements IEvent<RETURN, PARAM> {
    /**
     * Right before template parsing started
     */
    public static final
    IEvent<String, CodeBuilder> ON_PARSE = new RythmEvents<String, CodeBuilder>(true);

    /**
     * Before start building java source code. A good place to inject implicit
     * imports and render args
     */
    public static final
    IEvent<Void, CodeBuilder> ON_BUILD_JAVA_SOURCE = new RythmEvents<Void, CodeBuilder>(true);

    /**
     * Before close generated java source code class. A good place to inject
     * implicit java source
     */
    public static final
    IEvent<Void, CodeBuilder> ON_CLOSING_JAVA_CLASS = new RythmEvents<Void, CodeBuilder>(true);

    /**
     * Immediately after template get parsed and before final template java
     * source code generated
     */
    public static final
    IEvent<Void, CodeBuilder> PARSED = new RythmEvents<Void, CodeBuilder>(true);

    /**
     * Triggered upon parse exception
     */
    public static final
    IEvent<Void, TemplateClass> PARSE_FAILED = new RythmEvents<Void, TemplateClass>(true);

    /**
     * Right before template compilation started
     */
    public static final
    IEvent<Void, String> ON_COMPILE = new RythmEvents<Void, String>(true);

    /**
     * Immediately after template compilation finished and before get cached on disk
     * A good place to do byte code enhancement
     */
    public static final
    IEvent<byte[], byte[]> COMPILED = new RythmEvents<byte[], byte[]>(true);

    /**
     * Triggered upon compilation of a template class failed
     */
    public static final
    IEvent<Void, TemplateClass> COMPILE_FAILED = new RythmEvents<Void, TemplateClass>(true);

    /**
     * Before template render start. A good place to set implicit render args
     */
    public static final
    IEvent<Void, ITemplate> ON_RENDER = new RythmEvents<Void, ITemplate>(false);

    /**
     * After template rendered.
     */
    public static final
    IEvent<Void, ITemplate> RENDERED = new RythmEvents<Void, ITemplate>(true);

    /**
     * Before tag invocation
     */
    public static final
    IEvent<Void, F.T2<TemplateBase, ITemplate>> ON_TAG_INVOCATION = new RythmEvents<Void, F.T2<TemplateBase, ITemplate>>(false);

    /**
     * Triggered immediately when RythmEngine.invokeTemplate() method get called
     */
    public static final
    IEvent<Void, TemplateBase> ENTER_INVOKE_TEMPLATE = new RythmEvents<Void, TemplateBase>(false);

    /**
     * Triggered after RythmEngine.invokeTemplate() method get called
     */
    public static final
    IEvent<Void, TemplateBase> EXIT_INVOKE_TEMPLATE = new RythmEvents<Void, TemplateBase>(false);

    /**
     * Before tag invocation
     */
    public static final
    IEvent<Void, F.T2<TemplateBase, ITemplate>> TAG_INVOKED = new RythmEvents<Void, F.T2<TemplateBase, ITemplate>>(false);

    /**
     * Render execution exception captured
     */
    public static final
    IEvent<Boolean, F.T2<TemplateBase, Exception>> ON_RENDER_EXCEPTION = new RythmEvents<Boolean, F.T2<TemplateBase, Exception>>(true);
    
    private boolean safe = false;
    
    private RythmEvents() {}
    private RythmEvents(boolean isSafe) {
        safe = isSafe;
    }
    
    public boolean isSafe() {
        return safe;
    }

    @Override
    public RETURN trigger(IEventDispatcher eventBus, PARAM eventParam) {
        return (RETURN) eventBus.accept(this, eventParam);
    }
}
