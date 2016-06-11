/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.extension;

/**
 * Use application or framework plugins based on rythm engine could
 * implement this interface to allow further process compiled
 * template classes.
 * <p/>
 * <p>One {@link org.rythmengine.RythmEngine engine instance} can have zero
 * or one <code>ITemplateClassEnhancer</code></p>
 */
public interface IByteCodeEnhancer {
    /**
     * Enhance byte code. This method is called after a template
     * class get compiled and before it is cached to disk
     *
     * @param className
     * @param classBytes
     * @return the bytecode
     * @throws Exception
     */
    byte[] enhance(String className, byte[] classBytes) throws Exception;

    /**
     * Not to be used by user application
     */
    public static class INSTS {
        public static final IByteCodeEnhancer NULL = new IByteCodeEnhancer() {
            @Override
            public byte[] enhance(String className, byte[] classBytes) throws Exception {
                return new byte[0];
            }
        };

        private INSTS() {
        }
    }
}
