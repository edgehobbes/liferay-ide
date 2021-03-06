/*******************************************************************************
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************/

package com.liferay.ide.eclipse.portlet.jsf.ui.wizard;

import com.liferay.ide.eclipse.core.util.CoreUtil;
import com.liferay.ide.eclipse.portlet.jsf.core.operation.INewJSFPortletClassDataModelProperties;
import com.liferay.ide.eclipse.portlet.jsf.core.operation.NewJSFPortletClassDataModelProvider;
import com.liferay.ide.eclipse.portlet.jsf.ui.JSFPortletTemplateContextTypeIds;
import com.liferay.ide.eclipse.portlet.jsf.ui.JSFUIPlugin;
import com.liferay.ide.eclipse.portlet.ui.wizard.NewLiferayPortletWizardPage;
import com.liferay.ide.eclipse.portlet.ui.wizard.NewPortletWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

/**
 * @author Greg Amerson
 */
@SuppressWarnings("restriction")
public class NewJSFPortletWizard extends NewPortletWizard implements INewJSFPortletClassDataModelProperties {

	public NewJSFPortletWizard() {
		super();
	}

	public NewJSFPortletWizard(IDataModel model) {
		super(model);
	}

	@Override
	public String getTitle() {
		return "New Liferay JSF Portlet";
	}

	@Override
	protected String getDefaultPageTitle() {
		return "Create Liferay JSF Portlet";
	}

	@Override
	protected void doAddPages() {
		addPage(new NewJSFPortletClassWizardPage(
			getDataModel(), "pageOne", "Create a JSF portlet.", getDefaultPageTitle(), fragment));
		addPage(new NewJSFPortletOptionsWizardPage(
			getDataModel(), "pageTwo", "Specify JSF portlet deployment descriptor details.", getDefaultPageTitle(),
			fragment));
		addPage(new NewLiferayPortletWizardPage(
			getDataModel(), "pageThree", "Specify Liferay portlet deployment descriptor details.",
			getDefaultPageTitle(), fragment));
	}

	@Override
	protected ImageDescriptor getImage() {
		return ImageDescriptor.createFromURL(JSFUIPlugin.getDefault().getBundle().getEntry("/icons/wizban/jsf_wiz.png"));
	}

	@Override
	protected IDataModelProvider getDefaultProvider() {
		// for now, no need for own template store and context type
		TemplateStore templateStore = JSFUIPlugin.getDefault().getTemplateStore();

		TemplateContextType contextType =
			JSFUIPlugin.getDefault().getTemplateContextRegistry().getContextType(JSFPortletTemplateContextTypeIds.NEW);

		return new NewJSFPortletClassDataModelProvider(templateStore, contextType, fragment);
	}

	@Override
	protected void openJavaClass() {
		// instead of opening a java class lets open the view xhtml file
		if (getDataModel().getBooleanProperty(CREATE_JSPS)) {
			try {
				String jspsFolder = getDataModel().getStringProperty(CREATE_JSPS_FOLDER);
				IProject project =
					ResourcesPlugin.getWorkspace().getRoot().getProject(getDataModel().getStringProperty(PROJECT_NAME));

				IFolder docroot = CoreUtil.getDocroot(project);

				IFile viewFile = docroot.getFile(jspsFolder + "/portletViewMode.xhtml");

				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IDE.openEditor(page, viewFile, true);
			}
			catch (Exception e) {
				// eat this exception this is just best effort
			}
		}
	}

}
