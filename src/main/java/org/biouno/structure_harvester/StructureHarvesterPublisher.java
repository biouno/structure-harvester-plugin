package org.biouno.structure_harvester;

import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

public class StructureHarvesterPublisher extends Notifier {

	public StructureHarvesterPublisher(String resultsDir, String outDir,
			Boolean evanno, Boolean clumpp) {

	}
	
	public BuildStepMonitor getRequiredMonitorService() {
		return null;
	}
	
	@Override
	public BuildStepDescriptor getDescriptor() {
		return (StructureHarvesterPublisherDescriptor) super.getDescriptor();
	}

}
