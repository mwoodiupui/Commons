/*
 * Copyright 2016 Mark H. Wood.
 */

package com.markhwood.launcher;

/**
 * Concrete subclasses of this interface implement commands that may be invoked
 * by a command launcher.
 *
 * @author mhwood
 */
public interface Tool
{
    /**
     * Execute the tool's function.
     *
     * @param name the name of the tool, from the configuration Properties file.
     * @param argv command line arguments for the tool.
     * @return zero on success, nonzero on failure.
     * @throws Exception if something went wrong in the implementation.
     */
    public int run(String name, String[] argv)
            throws Exception;

    /**
     * Return a description of the tool's action.
     *
     * @return descriptive text.
     */
    public String getDescription();
}
