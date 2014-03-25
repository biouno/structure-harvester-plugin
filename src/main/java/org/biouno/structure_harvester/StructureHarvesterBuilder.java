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

import hudson.AbortException;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.Builder;
import hudson.util.ArgumentListBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.biouno.structure_harvester.util.Messages;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Structure Harvester builder.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class StructureHarvesterBuilder extends Builder {
	/*
	 * Le logger.
	 */
	//private static final Logger LOGGER = Logger.getLogger("org.biouno.structure_structure");
	/**
	 * Structure Harvester installation.
	 */
	private final String structureHarvesterInstallationName;
	/**
	 * Directory with Structure results.
	 */
	private final String resultsDir;

	/**
	 * Where harvester will put its output files.
	 */
	private final String outDir;

	/**
	 * Whether it should try to use the Evanno method or not.
	 */
	private final Boolean evanno;

	/**
	 * Whether it should generate clumpp files.
	 */
	private final Boolean clumpp;
	/**
	 * Le builder extension.
	 */
	@Extension
	public static final StructureHarvesterBuilderDescriptor DESCRIPTOR = new StructureHarvesterBuilderDescriptor();
	/**
	 * Constructor with args, called from Jelly populating the object properties
	 * from the form.
	 * @param structureHarvesterInstallationName
	 * @param resultsDir
	 * @param outDir
	 * @param evanno
	 * @param clumpp
	 */
	@DataBoundConstructor
	public StructureHarvesterBuilder(String structureHarvesterInstallationName, String resultsDir, String outDir, Boolean evanno, Boolean clumpp) {
		super();
		this.structureHarvesterInstallationName = structureHarvesterInstallationName;
		this.resultsDir = resultsDir;
		this.outDir = outDir;
		this.evanno = evanno;
		this.clumpp = clumpp;
	}
	/**
	 * @return the structureInstallationName
	 */
	public String getStructureHarvesterInstallationName() {
		return structureHarvesterInstallationName;
	}
	
	public String getResultsDir() {
		return resultsDir;
	}

	public String getOutDir() {
		return outDir;
	}

	public Boolean getEvanno() {
		return evanno;
	}

	public Boolean getClumpp() {
		return clumpp;
	}
	/**
	 * Creates one mainparam file for each K, and creates jobs for running 
	 * structure using each mainparam file. Finally, the output files are 
	 * sent back to this builder's job workspace. Then an action is included in 
	 * the build, to render summary about the plug-in execution.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, final BuildListener listener) throws AbortException,InterruptedException, IOException {
		listener.getLogger().println(Messages.StructureHarvesterBuilder_InvokingStructureHarvester());

		final EnvVars envVars = build.getEnvironment(listener);
		envVars.overrideAll(build.getBuildVariables());
		
		// Get the structure harvester installation used
		final StructureHarvesterInstallation structureHarvesterInstallation = DESCRIPTOR.getInstallationByName(this.structureHarvesterInstallationName);
		if (structureHarvesterInstallation == null) {
			throw new AbortException(Messages.StructureHarvesterBuilder_InvalidStructureHarvesterInstallation());
		}
		
		// Inform the user about some important info
		listener.getLogger().println("Using structure harvester " + structureHarvesterInstallation.getName() + " at " + structureHarvesterInstallation.getPathToScript());
		
		final FilePath workspace = build.getWorkspace();
		
		// Create structure command line
		final ArgumentListBuilder args = new ArgumentListBuilder();
		args.add(structureHarvesterInstallation.getPathToScript());
		args.add("--dir=" + new FilePath(workspace, this.getResultsDir()).getRemote());
		args.add("--out=" + new FilePath(workspace, this.getOutDir()).getRemote());
		if (this.getEvanno() != null && this.getEvanno()) {
			args.add("--evanno");
		}
		if (this.getClumpp() != null && this.getClumpp()) {
			args.add("--clumpp");
		}
		
		// Execute structure
		// Env vars
		final Map<String, String> env = build.getEnvironment(listener);
		listener.getLogger().println(Messages.StructureHarvesterBuilder_StructureHarvesterCommand(args.toStringWithQuote()));
		final Integer exitCode = launcher.launch().cmds(args).envs(env).stdout(listener).pwd(build.getModuleRoot()).join();
		

		if (exitCode != 0) {
			listener.getLogger().println(Messages.StructureHarvesterBuilder_ErrorExecutingCommand(exitCode));
			return Boolean.FALSE;
		} else {
			// If the command was executed with success, send the output dir back to the master
			FilePath outFileFilePath = new FilePath(workspace, getOutDir());
			
			// Get output files
			final List<String> resultFiles = new ArrayList<String>();
			String summary = "";
			String evanno = "";
			for (FilePath resultFile : outFileFilePath.list()) {
				if (resultFile.getName().equals("summary.txt")) {
					listener.getLogger().println("Found the summary.txt file");
					summary = Util.loadFile(new File(resultFile.getRemote()));
				} else if (resultFile.getName().equals("evanno.txt")) {
					listener.getLogger().println("Found the evanno.txt file");
					evanno = Util.loadFile(new File(resultFile.getRemote()));
				} else {
					resultFiles.add(resultFile.getName());
				}
			}
			listener.getLogger().printf("Found %d other files.\n", resultFiles.size());
			
			if(outFileFilePath.exists()) {
				build.addAction(new StructureHarvesterBuildSummaryAction(build, resultFiles.toArray(new String[0]), this.getResultsDir(), this.getOutDir(), summary, evanno));
			} else {
				listener.fatalError("Couldn't find structure output file. Expected " + outFileFilePath.getRemote());
			}
			
			listener.getLogger().println(Messages.StructureHarvesterBuilder_Success());
			return Boolean.TRUE;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see hudson.tasks.Builder#getDescriptor()
	 */
	@Override
	public StructureHarvesterBuilderDescriptor getDescriptor() {
		return (StructureHarvesterBuilderDescriptor) super.getDescriptor();
	}

}
