/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.essential;

import org.rythmengine.TestBase;
import org.rythmengine.conf.RythmConfigurationKey;
import org.rythmengine.extension.ICodeType;
import org.rythmengine.internal.parser.build_in.CommentParser;
import org.junit.Before;
import org.junit.Test;

/**
 * Test inline and block comment
 */
public class CommentTest extends TestBase {

    @Before
    public void setUp() {
        System.getProperties().put(RythmConfigurationKey.DEFAULT_CODE_TYPE_IMPL.getKey(), ICodeType.DefImpl.HTML);
    }

    @Test
    public void testInlineComment() {
    	String commentPattern=String.format(CommentParser.COMMENT_FORMAT, "@");
    	// System.out.println(commentPattern);
    	assertFalse("comment Pattern should not be platform dependend",commentPattern.contains("\r"));
        t = "abc@/adfiauoprquwreqw\nxyz";
        s = r(t);
        assertEquals("abc\nxyz", s);
    }
    
    @Test
    public void testDirectiveInsideInlineComment() {
        t = "abc@//addfa @for loop dafd\nxyz";
        s = r(t);
        assertEquals("abc\nxyz", s);
    }
    
    @Test
    public void testInlineCommentInsideDirectiveComment() {
        System.setProperty(RythmConfigurationKey.FEATURE_NATURAL_TEMPLATE_ENABLED.getKey(), "true");
        t = "abc<!-- @//addfa @for loop dafd -->\nxyz";
        s = r(t);
        assertEquals("abc\nxyz", s);
    }
    
    @Test
    public void testBlockComment() {
        t = "abc@**\n * Special notes to this @for loop\n *\n * Rythm do it's best to speculate the Type of the iterating element \n *@xyz";
        s = r(t);
        assertEquals("abcxyz", s);
    }

    @Test
    public void testBlockCommentWithLineBreaks() {
        t = "abc\n@**\n * Special notes to this @for loop\n *\n * Rythm do it's best to speculate the Type of the iterating element \n *@xyz";
        s = r(t);
        assertEquals("abcxyz", s);
    }
    
    @Test
    public void testBlockCommentInsideDirectiveComment() {
        System.setProperty(RythmConfigurationKey.FEATURE_NATURAL_TEMPLATE_ENABLED.getKey(), "true");
        t = "abc\n<!-- @**\n * Special notes to this @for loop\n *\n * Rythm do it's best to speculate the Type of the iterating element \n *@-->\nxyz";
        s = r(t);
        assertEquals("abc\nxyz", s);
    }
    
    @Test
    public void testRemoveSpaceTillLastLineBreak() {
        t = "abc\n\t   @//xyzd daf\n\t123";
        s = r(t);
        assertEquals("abc\n\t123",s);
        
        t = "abc\t @//xyzd daf\n\t123";
        s = r(t);
        assertEquals("abc\t \n\t123",s );
        
        t = "abc\n\t   @**\n *\n * abc\n *@\n123";
        s = r(t);
        assertEquals("abc\n123", s);
        
        t = "abc\t   @**\n *\n * abc\n *@\n123";
        s = r(t);
        assertEquals("abc\t   \n123", s);
    }

    public static void main(String[] args) {
        run(CommentTest.class);
    }
}
