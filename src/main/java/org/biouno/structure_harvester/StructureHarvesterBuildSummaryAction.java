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

import hudson.model.Action;
import hudson.model.AbstractBuild;

/**
 * Structure Harvester build action. Adds Harvester summary and, when available, 
 * evanno output.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class StructureHarvesterBuildSummaryAction implements Action {
	/*
	 * Constants of the action.
	 */
	private static final String URL = "structureHarvesterResults";
	private static final String DISPLAY_NAME = "Structure Harvester results";
	// private static final String ICON_URL =
	// "/plugin/structure/icons/icon-details.gif";
	/**
	 * Le owner of me.
	 */
	private final AbstractBuild<?, ?> owner;
	/**
	 * The list of files.
	 */
	private final String[] files;
	/**
	 * The input directory.
	 */
	private final String inputDirectory;
	/**
	 * The output directory.
	 */
	private final String outputDirectory;
	/**
	 * The summary output.
	 */
	private final String summary;
	/**
	 * The summary HTML output.
	 */
	private final String summaryHTML;
	/**
	 * The evanno output.
	 */
	private final String evanno;
	/**
	 * The evanno HTML output.
	 */
	private final String evannoHTML;
	/**
	 * Constructor with args.
	 * 
	 * @param owner the build that is owner of this action
	 * @param files the files
	 * @param inputDirectory
	 * @param outputDirectory
	 * @param summary
	 * @param evanno
	 */
	public StructureHarvesterBuildSummaryAction(AbstractBuild<?, ?> owner,
			String[] files, String inputDirectory, String outputDirectory, 
			String summary, String evanno) {
		this.owner = owner;
		this.files = files;
		this.inputDirectory = inputDirectory;
		this.outputDirectory = outputDirectory;
		this.summary = summary;
		this.evanno = evanno;
		this.summaryHTML = summary.replaceAll("(\r\n|\n)", "<br />");
		this.evannoHTML = evanno.replaceAll("(\r\n|\n)", "<br />");
	}
	/**
	 * @return the owner
	 */
	public AbstractBuild<?, ?> getOwner() {
		return owner;
	}
	/**
	 * @return the files
	 */
	public String[] getFiles() {
		return files;
	}
	/**
	 * @return the inputDirectory
	 */
	public String getInputDirectory() {
		return inputDirectory;
	}
	/**
	 * @return the outputDirectory
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @return the summary as HTML
	 */
	public String getSummaryHTML() {
		return summaryHTML;
	}
	/**
	 * @return the evannoFile
	 */
	public String getEvanno() {
		return evanno;
	}
	/**
	 * @return the evannoFile
	 */
	public String getEvannoHTML() {
		return evannoHTML;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Action#getIconFileName()
	 */
	public String getIconFileName() {
		// return ICON_URL;
		return null;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Action#getDisplayName()
	 */
	public String getDisplayName() {
		return DISPLAY_NAME;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Action#getUrlName()
	 */
	public String getUrlName() {
		return URL;
	}
}
