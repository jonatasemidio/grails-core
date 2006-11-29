/*
 * Copyright 2004-2005 the original author or authors.
 * 
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
 */ 
package org.codehaus.groovy.grails.plugins;

import groovy.util.slurpersupport.GPathResult;

import java.math.BigDecimal;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.commons.spring.RuntimeSpringConfiguration;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

/**
 * <p>Plugin interface that adds Spring {@link org.springframework.beans.factory.config.BeanDefinition}s
 * to a registry based on a {@link GrailsApplication} object. After all <code>GrailsPlugin</code> classes
 * have been processed the {@link org.springframework.beans.factory.config.BeanDefinition}s in the registry are
 * loaded in a Spring {@link org.springframework.context.ApplicationContext} that's the singular
 * configuration unit of Grails applications.</p>
 *
 * <p>It's up to implementation classes to determine where <code>GrailsPlugin</code> instances are loaded
 * from.</p>
 *
 * @author Steven Devijver
 * @author Graeme Rocher
 * 
 * @since 0.2
 * @see BeanDefinitionRegistry
 */
public interface GrailsPlugin {

    String ON_CHANGE = "onChange";
	String DO_WITH_WEB_DESCRIPTOR = "doWithWebDescriptor";
	String TRAILING_NAME = "GrailsPlugin";
	String VERSION = "version";
	String DO_WITH_SPRING = "doWithSpring";
	String DO_WITH_APPLICATION_CONTEXT = "doWithApplicationContext";
	String DEPENDS_ON = "dependsOn";


	/**
     * <p>This method is called to allow the plugin to add {@link org.springframework.beans.factory.config.BeanDefinition}s
     * to the {@link BeanDefinitionRegistry}.</p>
     *
     * @param applicationContext
     */
    void doWithApplicationContext(ApplicationContext applicationContext);
    
    
    /**
     * Executes the plugin code that performs runtime configuration as defined in the doWithSpring closure
     * 
     * @param springConfig The RuntimeSpringConfiguration instance
     */
    void doWithRuntimeConfiguration(RuntimeSpringConfiguration springConfig);

    /**
     * Handles processing of web.xml. The method is passed a GPathResult which is parsed by
     * groovy.util.XmlSlurper. A plug-in can then manipulate the in-memory XML however it chooses
     * Once all plug-ins have been processed the web.xml is then written to disk based on its in-memory form
     * 
     * @param webXml The GPathResult representing web.xml
     */
    void doWithWebDescriptor(GPathResult webXml);
    
    /**
     * 
     * @return The name of the plug-in
     */
	String getName();


	/**
	 * 
	 * @return The version of the plug-in
	 */
	BigDecimal getVersion();


	/**
	 * 
	 * @return The names of the plugins this plugin is dependant on
	 */
	String[] getDependencyNames();


	/**
	 * The version of the specified dependency
	 * 
	 * @param name the name of the dependency
	 * @return The version
	 */
	BigDecimal getDependentVersion(String name);
	
	
	/**
	 * When called this method checks for any changes to the plug-ins watched resources
	 * and reloads appropriately
	 *
	 */
	void checkForChanges();
}
