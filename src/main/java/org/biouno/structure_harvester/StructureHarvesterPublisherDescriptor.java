package org.biouno.structure_harvester;

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

public class StructureHarvesterPublisherDescriptor extends BuildStepDescriptor<Publisher>{

	@Override
	public String getDisplayName() {
		return "Invoke Structure Harvester";
	}

	@Override
	public boolean isApplicable(@SuppressWarnings("rawtypes") Class<? extends AbstractProject> jobType) {
		return true;
	}

}
