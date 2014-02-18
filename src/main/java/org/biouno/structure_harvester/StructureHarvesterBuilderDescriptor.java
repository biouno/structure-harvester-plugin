package org.biouno.structure_harvester;

import hudson.CopyOnWrite;
import hudson.model.Descriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.biouno.structure_harvester.util.Messages;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Descriptor of Structure Harvester builder.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 * @see {@link StructureHarvesterBuilder}
 */
public final class StructureHarvesterBuilderDescriptor extends Descriptor<Builder> {
	/**
	 * Exposed for jelly.
	 */
	public final Class<StructureHarvesterBuilder> builderType = StructureHarvesterBuilder.class;
	/**
	 * Structure name displayed in the build configuration screen.
	 */
	private static final String DISPLAY_NAME = Messages.StructureHarvesterBuilder_DisplayName();
	/**
	 * The list of available installations. They are copied when the form is 
	 * submitted.
	 */
	@CopyOnWrite
	private volatile StructureHarvesterInstallation[] installations = new StructureHarvesterInstallation[0];
	/**
	 * No args constructor to ensure the descriptor pattern.
	 */
	public StructureHarvesterBuilderDescriptor() {
		super(StructureHarvesterBuilder.class);
		load();
	}
	/* (non-Javadoc)
	 * @see hudson.model.Descriptor#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}
	/**
	 * Gets the list of installations. Never <code>null</code>.
	 * @return StructureInstallation[]
	 */
	public StructureHarvesterInstallation[] getInstallations() {
		return this.installations;
	}
	/**
	 * Gets an installation by its name, or <code>null</code> if none found.
	 * @param name the installation name
	 * @return StructureInstallation or <code>null</code>
	 */
	public StructureHarvesterInstallation getInstallationByName(String name) {
		StructureHarvesterInstallation found = null;
		for(StructureHarvesterInstallation installation : this.installations) {
			if (StringUtils.isNotEmpty(installation.getName())) {
				if(name.equals(installation.getName())) {
					found = installation;
					break;
				}
			}
		}
		return found;
	}
	/* (non-Javadoc)
	 * @see hudson.model.Descriptor#configure(org.kohsuke.stapler.StaplerRequest, net.sf.json.JSONObject)
	 */
	@Override
	public boolean configure(StaplerRequest req, JSONObject json)
			throws hudson.model.Descriptor.FormException {
		this.installations = req.bindParametersToList(StructureHarvesterInstallation.class, "StructureHarvester.").toArray(new StructureHarvesterInstallation[0]);
		save();
		return Boolean.TRUE;
	}
	@Override
	public Builder newInstance(StaplerRequest arg0, JSONObject arg1)
			throws hudson.model.Descriptor.FormException {
		return super.newInstance(arg0, arg1);
	}
	/**
	 * Validates required fields.
	 * @param value the value
	 * @return FormValidation
	 */
	public FormValidation doRequired(@QueryParameter String value) {
		FormValidation returnValue = FormValidation.ok();
		if(StringUtils.isBlank(value)) {
			returnValue = FormValidation.error(Messages.StructureHarvesterBuilder_Required());
		}
		return returnValue;
	}
	/**
	 * Validates required long fields.
	 * @param value the value
	 * @return FormValidation
	 */
	public FormValidation doLongRequired(@QueryParameter String value) {
		FormValidation returnValue = FormValidation.ok();
		if(StringUtils.isNotBlank(value)) {
			try {
				Long.parseLong(value);
			} catch ( NumberFormatException nfe ) {
				returnValue = FormValidation.error("This value must be an integer");
			}
		}
		return returnValue;
	}
	/**
	 * Validates required double fields.
	 * @param value the value
	 * @return FormValidation
	 */
	public FormValidation doDoubleRequired(@QueryParameter String value) {
		FormValidation returnValue = FormValidation.ok();
		if(StringUtils.isNotBlank(value)) {
			try {
				Double.parseDouble(value);
			} catch ( NumberFormatException nfe ) {
				returnValue = FormValidation.error("This value must be an float");
			}
		}
		return returnValue;
	}

}
