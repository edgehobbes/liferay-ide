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

package com.liferay.ide.eclipse.project.ui.action;

import com.liferay.ide.eclipse.project.ui.wizard.SDKProjectConvertWizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Greg Amerson
 */
public class ConvertProjectAction implements IObjectActionDelegate {

	private ISelection fSelection;

	public ConvertProjectAction() {
	}

	public Display getDisplay() {
		Display display = Display.getCurrent();
		
		if (display == null)
			display = Display.getDefault();
		
		return display;
	}

	public void run(IAction action) {

		if (fSelection instanceof IStructuredSelection) {
			Object[] elems = ((IStructuredSelection) fSelection).toArray();
			
			IProject project = null;
			
			Object elem = elems[0];

			if (elem instanceof IProject) {
				project = (IProject) elem;
			}

			SDKProjectConvertWizard wizard = new SDKProjectConvertWizard(project);

			final Display display = getDisplay();
			
			final WizardDialog dialog = new WizardDialog(display.getActiveShell(), wizard);
			
			BusyIndicator.showWhile(display, new Runnable() {

				public void run() {
					dialog.open();
				}
			});
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		fSelection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
