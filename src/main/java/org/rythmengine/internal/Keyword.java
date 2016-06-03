/* 
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal;

public enum Keyword implements IKeyword {
    /**
     * Assign enclosed part into a String variable
     */
    ASSIGN,
    /**
     * Declare arguments used in the template
     */
    ARGS,
    /**
     * break current loop
     */
    BREAK("(?i)(breakIf|break)"),
    /**
     * Cache the block for a certain period
     */
    CACHE,
    CASE,
    /**
     * Force compact mode in regard of engine.conf().compactEnabled()
     */
    COMPACT,
    /**
     * Continue current loop
     */
    CONTINUE("(?i)(continueIf|continue)"),
    /**
     * output debug message
     */
    DEBUG,
    /**
     * Foreach loop
     */
    EACH("(?i)(for|forEach|each)"),
    /**
     * Mark a section that expression should be output after escaped
     */
    ESCAPE,
    /**
     * Execute an macro
     */
    EXEC,
    /**
     * Expand an macro, alias of exec
     */
    EXPAND,
    /**
     * Exit parsing process if no class loaded in current class loader
     */
    EXIT_IF_NO_CLASS("__exitIfNoClass__"),
    /**
     * Declare parent template for this template
     */
    EXTENDS,
    /**
     * Mark a finally section. There can be at most one
     * "@finally{}" section per template
     */
    FINALLY,
    /**
     * Fetch named content from this or sub template
     */
    GET,
    /**
     * Lookup i18n message
     */
    I18N,
    /**
     * plain java if else block
     */
    IF("(?i)(if|ifNot)"),
    /**
     * import java packages/classes
     */
    IMPORT,
    /**
     * include other templates
     */
    INCLUDE,
    /**
     * Mark a section init code. There can be
     * at most one @init{} section per template
     */
    INIT,
    /**
     * Explicitly invoke a tag (could be used to implement dynamic tag dispatch
     */
    INVOKE,
    /**
     * Define a different locale block
     */
    LOCALE,
    /**
     * Instruct that this template needs to log execution time
     */
    LOG_TIME("__logTime__"),
    /**
     * Define an macro.
     * <p/>
     * A macro is different from an assignment or an inline tag in that
     * it is purely happen at parsing time, while the assign and inline tag
     * is evaluated at runtime
     */
    MACRO,
    /**
     * For no compact in regard to engine.conf().compactEnabled()
     */
    NOCOMPACT,
    /**
     * Indicate this is not a SIM template
     */
    NOSIM,
    /**
     * Mark a section that expression should be output as raw data
     */
    RAW,
    /**
     * Output sub template render content in place
     */
    RENDER_BODY("(renderBody|doBody)"),
    RENDER_INHERITED("(inherited|renderInherited)"),
    /**
     * Output sub template section content in place
     */
    RENDER_SECTION("(render(Section|Layout)?|doLayout)"),
    /**
     * break the current template execution process and return to caller
     */
    RETURN,
    /**
     * break the current template execution process and return to caller
     * if an expression evaluated to true
     */
    RETURN_IF("returnIf"),
    /**
     * Declare a section start
     */
    SECTION,
    /**
     * Declare named content to be used in this or parent template
     */
    SET,
    /**
     * Identify current template is a simple template
     */
    SIMPLE("__simple__"),
    SWITCH,
    /**
     * Call self as a tag
     */
    THIS,
    /**
     * output timestamp
     */
    TS,
    /**
     * define tag
     */
    TAG("(tag|def)"),
    /**
     * Verbatim tag mark a block of template source shall
     * not be parsed
     */
    VERBATIM;

    private final String s;

    private Keyword() {
        this.s = name().toLowerCase();
    }

    private Keyword(String s) {
        this.s = (null == s) ? name().toLowerCase() : s;
    }

    @Override
    public String toString() {
        return s;
    }

    @Override
    public boolean isRegexp() {
        return !s.equals(name().toLowerCase());
    }
}
