/*
 * The MIT License
 *
 * Copyright (c) <2014> <Bruno P. Kinoshita>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.biouno.structure_harvester;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Stores information about the installation of Structure Harvester. This information is 
 * used by the Builder to call Structure Harvester. This information is stored within a 
 * Descriptor that is, by its turn, used in the Builder.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class StructureHarvesterInstallation implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5680905599172787394L;
	/**
	 * A name for a Structure installation. This name is referenced in the Job 
	 * set up.
	 */
	private final String name;
	/**
	 * Path to Structure executable.
	 */
	private final String pathToScript;
	/**
	 * @param name the name for a Structure installation
	 * @param pathToScript the path to structureHarvester.py
	 */
	@DataBoundConstructor
	public StructureHarvesterInstallation(String name, String pathToScript) {
		this.name = name;
		this.pathToScript = pathToScript;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the pathToScript
	 */
	public String getPathToScript() {
		return pathToScript;
	}
}
