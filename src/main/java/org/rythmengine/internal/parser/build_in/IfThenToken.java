/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.internal.parser.build_in;

/*-
 * #%L
 * Rythm Template Engine
 * %%
 * Copyright (C) 2017 - 2021 OSGL (Open Source General Library)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.rythmengine.internal.IContext;
import org.rythmengine.internal.parser.CodeToken;
import org.rythmengine.utils.F;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luog
 * Date: 26/05/13
 * Time: 5:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class IfThenToken extends CodeToken {
    private List<F.T2<String, String>> ifthen = new ArrayList<F.T2<String, String>>();
    public IfThenToken(String IF, String THEN, IContext context) {
        super(null, context);
        ifthen.add(F.T2(IF, THEN));
    }
    public IfThenToken(IContext context, String... conditions) {
        super(null, context);
        int len = conditions.length;
        if (len % 2 != 0) {
            throw new IllegalArgumentException("the number of if-then params should be even");
        }
        for (int i = 0; i < len; i += 2) {
            ifthen.add(F.T2(conditions[i], conditions[i+1]));
        }
    }

    @Override
    public void output() {
        int size = ifthen.size();
        if (size == 0) return;
        F.T2<String, String> pair = ifthen.get(0);
        String IF = pair._1.trim();
        if (IF.endsWith("@")) {
            IF = IF.substring(0, IF.length() - 1);
            IF = "__eval(\"" + IF + "\")";
        }
        IF = "org.rythmengine.utils.Eval.eval(" + IF + ")";
        p("if (").p(IF).p(") {").p(pair._2).p(";}");
        pline();
        for (int i = 1; i < size; ++i) {
            pair = ifthen.get(i);
            p("else if (").p(IF).p(") {").p(pair._2).p(";}");
            pline();
        }
    }
}
