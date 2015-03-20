/*
 * # Copyright 2008 zylk.net
 * #
 * # This file is part of Aponwao.
 * #
 * # Aponwao is free software: you can redistribute it and/or modify
 * # it under the terms of the GNU General Public License as published by
 * # the Free Software Foundation, either version 2 of the License, or
 * # (at your option) any later version.
 * #
 * # Aponwao is distributed in the hope that it will be useful,
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * # GNU General Public License for more details.
 * #
 * # You should have received a copy of the GNU General Public License
 * # along with Aponwao. If not, see <http://www.gnu.org/licenses/>. [^]
 * #
 * # See COPYRIGHT.txt for copyright notices and details.
 * #
 */

package flex.aponwao.gui.sections.main.events;

import java.lang.reflect.InvocationTargetException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import flex.aponwao.core.exceptions.CoreException;
import flex.aponwao.core.exceptions.CorePKCS12Exception;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.main.helpers.FirmarPDFHelper;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import sun.security.pkcs11.wrapper.PKCS11Exception;


public class LoadKeyStoreProgress implements IRunnableWithProgress {
	
	private static Logger	logger				= Logger.getLogger(LoadKeyStoreProgress.class.getName());
	
	private Shell sShell = null;

	public LoadKeyStoreProgress(Shell sShell) {
		
		this.sShell = sShell;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		
		monitor.beginTask(LanguageResource.getLanguage().getString("info.loading.certificate"), IProgressMonitor.UNKNOWN);
		
		try {
			FirmarPDFHelper.loadKeyStore(sShell);
			
		} catch (KeyStoreException e) {
			if (monitor.isCanceled())
				throw new InterruptedException("The long running operation was cancelled");
			else
				throw new InvocationTargetException(e);
		} catch (PKCS11Exception e) {
			if (monitor.isCanceled())
				throw new InterruptedException("The long running operation was cancelled");
			else
				throw new InvocationTargetException(e);
		} catch (NoSuchAlgorithmException e) {
			if (monitor.isCanceled())
				throw new InterruptedException("The long running operation was cancelled");
			else
				throw new InvocationTargetException(e);
		} catch (CoreException e) {
			if (monitor.isCanceled())
				throw new InterruptedException("The long running operation was cancelled");
			else 
				throw new InvocationTargetException(e);
		} catch (CorePKCS12Exception e) {
			if (monitor.isCanceled())
				throw new InterruptedException("The long running operation was cancelled");
			else
				throw new InvocationTargetException(e);
		}
		
		monitor.done();
	}
	
}