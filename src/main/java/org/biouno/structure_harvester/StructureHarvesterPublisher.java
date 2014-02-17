package org.biouno.structure_harvester;

import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

public class StructureHarvesterPublisher extends Notifier {

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

	public StructureHarvesterPublisher(String resultsDir, String outDir,
			Boolean evanno, Boolean clumpp) {
		this.resultsDir = resultsDir;
		this.outDir = outDir;
		this.evanno = evanno;
		this.clumpp = clumpp;
	}

	public BuildStepMonitor getRequiredMonitorService() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BuildStepDescriptor getDescriptor() {
		return (StructureHarvesterPublisherDescriptor) super.getDescriptor();
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

}
