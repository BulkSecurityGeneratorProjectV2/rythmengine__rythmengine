/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal.parser.build_in;

import org.rythmengine.exception.ParseException;
import org.rythmengine.internal.CodeBuilder;
import org.rythmengine.internal.IContext;
import org.rythmengine.internal.Token;
import org.rythmengine.internal.parser.CodeToken;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luog
 * Date: 19/07/12
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExecMacroToken extends CodeToken {
    public ExecMacroToken(String macro, IContext context, int line) {
        super(macro, context);
        this.line = line;
    }

    @Override
    public void output() {
        CodeBuilder cb = ctx.getCodeBuilder();
        if (!cb.hasMacro(s)) {
            throw new ParseException(ctx.getEngine(), ctx.getTemplateClass(), line, "Cannot find macro definition for \"%s\"", s);
        }
        List<Token> list = cb.getMacro(s);
        for (Token tb : list) {
            tb.build();
        }
    }
}
