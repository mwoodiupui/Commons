/*
 * Copyright 2016 Mark H. Wood.
 */

package com.markhwood.launcher;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Invoke a named tool, passing the remaining argument list to it.
 *
 * @author mhwood
 */
public class Launcher
{
    /**
     * Select a tool (named by the first argument) and run it.
     *
     * @param argv name of the desired tool, followed by arguments for it.
     * @throws Exception if something went wrong.
     */
    static public void main(String[] argv)
            throws Exception
    {
        // Load all command mappings
        final String resourceName
                = Launcher.class.getPackage().getName().replace('.', '/')
                + "/tools.properties";
        Enumeration<URL> toolsEnumerated = Launcher.class.getClassLoader()
                .getResources(resourceName);
        TreeMap<String, String> tools = new TreeMap<>();
        for (URL url : Collections.list(toolsEnumerated))
        {
            Properties props = new Properties();
            props.load(url.openStream());
            for (Map.Entry<Object, Object> entry : props.entrySet())
            {
                tools.put((String)entry.getKey(), (String)entry.getValue());
            }
        }

        // Which command?
        String tool = argv.length > 0 ? argv[0] : "/h";

        // Look it up and execute it
        if (tools.containsKey(tool))
        {
            Class<Tool> toolClass = (Class<Tool>) Class.forName(tools.get(tool));
            Tool theTool = toolClass.newInstance();
            int status = theTool.run(tool, Arrays.copyOfRange(argv, 1, argv.length));
            System.exit(status);
        }
        else
        {
            if (!"/h".equals(tool))
                System.err.format("Unknown verb \"%s\".  ", tool);
            System.err.println("Known verbs are:");
            for (String knownTool : tools.keySet())
            {
                Class<Tool> toolClass = (Class<Tool>) Class.forName(tools.get(knownTool));
                Tool theTool = toolClass.newInstance();
                System.err.format("  %s:  %s%n", knownTool, theTool.getDescription());
            }
            System.exit(1);
        }
    }
}
