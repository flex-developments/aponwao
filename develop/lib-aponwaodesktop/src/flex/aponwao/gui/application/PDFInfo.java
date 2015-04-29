/*
# Copyright 2008 zylk.net
#
# This file is part of Sinadura.
#
# Sinadura is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
#
# Sinadura is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Sinadura.  If not, see <http://www.gnu.org/licenses/>. [^]
#
# See COPYRIGHT.txt for copyright notices and details.
#
*/
package flex.aponwao.gui.application;

import java.io.File;
import java.util.List;


public final class PDFInfo {

	
	/**
	 * Firmado en la ejecucion en curso de sinadura
	 */
        private String name;
	private String	path;
        private String destination;
        private boolean signed;
	/**
	 * null si no ha sido validado aun
	 * Lista vacia si el documento no tiene ninguna firma
	 */
	private List<PDFSignatureInfo> signatures;
        private boolean dir;
	
	
	public PDFInfo () {
                this.setName(null);
		this.setOrigen(null);
                this.setDestino(null);
                this.signed = false;
		this.signatures = null;
                this.dir = false;
	}
	
	public PDFInfo (String name, String path, String destination, boolean isSigned, List<PDFSignatureInfo> signatures, boolean isDir) {
                File f = new File(path);
                this.setName(f.getName());
		this.setOrigen(f.getAbsolutePath());
                this.setDestino(destination);
		this.setSigned(isSigned);
		this.signatures = signatures;
		this.setDir(isDir);
	}
	

	public void setSigned(Boolean signed) {
		this.signed = signed;
	}

	public Boolean getSigned() {
		return signed;
	}
        
        public void setDir(Boolean dir) {
		this.dir = dir;
	}

	public Boolean getDir() {
		return dir;
	}

	/**
	 * @param signatures last signer in the first position
	 */
	public void setSignatures(List<PDFSignatureInfo> signatures) {
		this.signatures = signatures;
	}

	/**
	 * @return last signer in the first position
	 */
	public List<PDFSignatureInfo> getSignatures() {
		return signatures;
	}

	public void setOrigen(String path) {
		this.path = path;
	}

	public String getOrigen() {
		return path;
	}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDestino() {
            return destination;
        }

        public void setDestino(String destination) {
            this.destination = destination;
        }
}
