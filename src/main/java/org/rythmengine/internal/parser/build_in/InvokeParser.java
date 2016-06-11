/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal.parser.build_in;

import com.stevesoft.pat.Regex;
import org.rythmengine.internal.*;
import org.rythmengine.internal.parser.ParserBase;
import org.rythmengine.utils.S;

import java.util.regex.Matcher;

/**
 * Parse @invoke("tagname", ...) {body}
 */
public class InvokeParser extends KeywordParserFactory {
    @Override
    public IKeyword keyword() {
        return Keyword.INVOKE;
    }

    @Override
    protected String patternStr() {
        return "(^\\n?[ \\t\\x0B\\f]*%s(%s\\s*((?@()))\\s*)((\\.([_a-zA-Z][_a-zA-Z0-9]*)((?@())))*))";
    }

    @Override
    public IParser create(final IContext ctx) {
        return new ParserBase(ctx) {
            @Override
            public Token go() {
                Regex r = reg(dialect());
                if (!r.search(remain())) {
                    raiseParseException("Error parsing @invoke statement. Correct usage: @invoke(\"tagname\", ...)");
                }
                final String matched = r.stringMatched();
                if (matched.startsWith("\n") || matched.endsWith("\n")) {
                    ctx.getCodeBuilder().addBuilder(new Token.StringToken("\n", ctx));
                    Regex r0 = new Regex("\\n([ \\t\\x0B\\f]*).*");
                    if (r0.search(matched)) {
                        String blank = r0.stringMatched(1);
                        if (blank.length() > 0) {
                            ctx.getCodeBuilder().addBuilder(new Token.StringToken(blank, ctx));
                        }
                    }
                } else {
                    Regex r0 = new Regex("([ \\t\\x0B\\f]*).*");
                    if (r0.search(matched)) {
                        String blank = r0.stringMatched(1);
                        if (blank.length() > 0) {
                            ctx.getCodeBuilder().addBuilder(new Token.StringToken(blank, ctx));
                        }
                    }
                }
                int len = matched.length();
                if (matched.endsWith("\n")) {
                    len--;
                }
                step(len);
                //boolean ignoreNonExistsTag = matched.indexOf("ignoreNonExistsTag") != -1;
                String invocation = r.stringMatched(3);
                invocation = S.stripBrace(invocation);
                // get tag name
                int pos = invocation.indexOf(",");
                String tagName, params;
                if (-1 == pos) {
                    tagName = invocation;
                    params = "";
                } else {
                    tagName = invocation.substring(0, pos);
                    params = invocation.substring(pos + 1);
                }
                String s = remain();
                Matcher m0 = InvokeTemplateParser.P_HEREDOC_SIMBOL.matcher(s);
                Matcher m1 = InvokeTemplateParser.P_STANDARD_BLOCK.matcher(s);
                if (m0.matches()) {
                    Token tb = InvokeTemplateParser.InvokeTagWithBodyToken.dynamicTagToken(tagName, params, r.stringMatched(4), ctx());
                    ctx().step(m0.group(1).length());
                    return tb;
                } else if (m1.matches()) {
                    Token tb = InvokeTemplateParser.InvokeTagWithBodyToken.dynamicTagToken(tagName, params, r.stringMatched(4), ctx());
                    ctx().step(m1.group(1).length());
                    return tb;
                } else {
                    return InvokeTemplateParser.InvokeTagWithBodyToken.dynamicTagToken(tagName, params, r.stringMatched(4), ctx());
                }
            }
        };
    }
}
