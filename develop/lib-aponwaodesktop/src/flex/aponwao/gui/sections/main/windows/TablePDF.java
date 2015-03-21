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

package flex.aponwao.gui.sections.main.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.LoggingDesktopController;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.application.PDFSignatureInfo;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.aponwao.gui.sections.global.windows.InfoDialog;
import flex.aponwao.gui.sections.main.helpers.ValidarPDFHelper;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.eSign.helpers.CertificateHelper;
import flex.eSign.helpers.exceptions.CertificateHelperException;
import java.io.File;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class TablePDF extends Composite {

	private static Logger	logger	= Logger.getLogger(TablePDF.class.getName());

	private Tree	tree	= null;

	private Cursor oldCursor = null;
	private Cursor newCursor = null;


	public TablePDF(Composite parent, int style) {

		super(parent, style);
		initialize();
	}
        
	private void initialize() {

		GridLayout gridLayout = new GridLayout();
		setLayout(gridLayout);

		this.tree = new Tree(this, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
//		this.table = new Table(this, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION); // TODO borrar

		oldCursor = this.getShell().getCursor();
		newCursor = new Cursor(tree.getDisplay(), SWT.CURSOR_HELP);

		this.tree.setHeaderVisible(true);
		this.tree.setLinesVisible(true);

		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		this.tree.setLayoutData(gd);
                
		TreeColumn tcSigned = new TreeColumn(this.tree, SWT.CENTER);
		tcSigned.setWidth(45);
                
                //OJO... Modificacion Felix
//                TreeColumn tcCheck = new TreeColumn(this.tree, SWT.CHECK);
//                tcCheck.setText(LanguageResource.getLanguage().getString("section.sign.table_pdf.firmar"));
//		tcCheck.setWidth(50);
                //**************************************************************

		TreeColumn tcArchivoDestino = new TreeColumn(this.tree, SWT.LEFT);
		tcArchivoDestino.setText(LanguageResource.getLanguage().getString("section.sign.table_pdf.name"));
		tcArchivoDestino.setWidth(250);

		TreeColumn tcVersion = new TreeColumn(this.tree, SWT.LEFT);
		tcVersion.setText(LanguageResource.getLanguage().getString("section.sign.table_pdf.version"));
		tcVersion.setWidth(250);

		TreeColumn tcSigner = new TreeColumn(this.tree, SWT.LEFT);
		tcSigner.setText(LanguageResource.getLanguage().getString("section.sign.table_pdf.signer"));
		tcSigner.setWidth(300);

		TreeColumn tcDate = new TreeColumn(this.tree, SWT.LEFT);
		tcDate.setText(LanguageResource.getLanguage().getString("section.sign.table_pdf.date"));
		tcDate.setWidth(200);

		TreeColumn tcArchivoOrigen = new TreeColumn(this.tree, SWT.LEFT);
		tcArchivoOrigen.setText(LanguageResource.getLanguage().getString("section.sign.table_pdf.path"));
		tcArchivoOrigen.setWidth(800);


		this.tree.addKeyListener(new SupButtonKeyListener());



		this.tree.addListener(SWT.MouseDown, new Listener() {

			public void handleEvent(Event event) {

				Point pt = new Point(event.x, event.y);

				for (int i = 0; i< tree.getItemCount(); i++) { // recorre los items

					TreeItem item = tree.getItem(i);

					// 3
					if (item.getImage(3) != null && item.getImageBounds(3).contains(pt)) {

						PDFSignatureInfo sigInfo = ((PDFInfo)item.getData()).getSignatures().get(0);
						InfoDialog id = new InfoDialog(tree.getShell());
                                            try {
                                                id.open(sigInfo.getSignerLocalizedDescription());
                                            } catch (CertificateHelperException ex) {
                                                Logger.getLogger(TablePDF.class.getName()).log(Level.SEVERE, null, ex);
                                            }
						return;
					}

					// 4
					if (item.getImage(4) != null && item.getImageBounds(4).contains(pt)) {

						PDFSignatureInfo sigInfo = ((PDFInfo)item.getData()).getSignatures().get(0);
						InfoDialog id = new InfoDialog(tree.getShell());
						id.open(sigInfo.getDateLocalizedDescription());
						return;
					}


					TreeItem[] array = item.getItems();

					for (int j=0; j< array.length; j++) { // recorre los subitems

						TreeItem subItem = array[j];

						// 3
						if (subItem.getImage(3) != null && subItem.getImageBounds(3).contains(pt)) {

							PDFSignatureInfo sigInfo = ((PDFInfo)item.getData()).getSignatures().get(j+1);
							InfoDialog id = new InfoDialog(tree.getShell());
                                                    try {
                                                        id.open(sigInfo.getSignerLocalizedDescription());
                                                    } catch (CertificateHelperException ex) {
                                                        Logger.getLogger(TablePDF.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
							return;
						}

						// 4
						if (subItem.getImage(4) != null && subItem.getImageBounds(4).contains(pt)) {

							PDFSignatureInfo sigInfo = ((PDFInfo)item.getData()).getSignatures().get(j+1);
							InfoDialog id = new InfoDialog(tree.getShell());
							id.open(sigInfo.getDateLocalizedDescription());
							return;
						}
					}
				}
			}
		});


		this.tree.addListener(SWT.MouseMove, new Listener() {

			public void handleEvent(Event event) {

				Point pt = new Point(event.x, event.y);

				for (int i = 0; i < tree.getItemCount(); i++) { // recorre los items

					TreeItem item = tree.getItem(i);

					// 3
					if (item.getImage(3) != null && item.getImageBounds(3).contains(pt)) {

						if (tree.getCursor() == oldCursor) {
							tree.setCursor(newCursor);
						}
						return;
					}

					// 4
					if (item.getImage(4) != null && item.getImageBounds(4).contains(pt)) {

						if (tree.getCursor() == oldCursor) {
							tree.setCursor(newCursor);
						}
						return;
					}

					TreeItem[] array = item.getItems();

					for (int j = 0; j < array.length; j++) { // recorre los subitems

						TreeItem subItem = array[j];

						// 3
						if (subItem.getImage(3) != null && subItem.getImageBounds(3).contains(pt)) {

							if (tree.getCursor() == oldCursor) {
								tree.setCursor(newCursor);
							}
							return;
						}

						// 4
						if (subItem.getImage(4) != null && subItem.getImageBounds(4).contains(pt)) {

							if (tree.getCursor() == oldCursor) {
								tree.setCursor(newCursor);
							}
							return;
						}

					}

				}

				if (tree.getCursor() == newCursor) {
					tree.setCursor(oldCursor);
				}

			}
		});


	}

	public void reloadTable() throws CertificateHelperException {

                List<PDFInfo> listPdfReaderSign = getPdfReaderSignList();
                this.tree.removeAll();

		for (PDFInfo pdfParameter : listPdfReaderSign) {
			TreeItem item = new TreeItem(this.tree, SWT.NONE);
                        item.setData(pdfParameter);

                        if (pdfParameter.getDir() && !PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VIEW_CONTENT_FOLDER)){

                                if (pdfParameter.getSigned()!= null && pdfParameter.getSigned())
                                         item.setImage(0, new Image(this.getDisplay(), ImagesResource.DIR_SIGNED_IMG));
                                if (pdfParameter.getSigned()!= null && !pdfParameter.getSigned())
                                         item.setImage(0, new Image(this.getDisplay(), ImagesResource.ADD_DIR_IMG));

                                item.setText(1, pdfParameter.getOrigen().substring(0, pdfParameter.getOrigen().lastIndexOf("/")));
                                item.setText(5, pdfParameter.getOrigen().substring(0, pdfParameter.getOrigen().lastIndexOf("/")));                        

                        } else {

                                if (pdfParameter.getSigned()!= null && pdfParameter.getSigned())
                                         item.setImage(0, new Image(this.getDisplay(), ImagesResource.SIGNED_IMG));
                                if (pdfParameter.getSigned()!= null && !pdfParameter.getSigned())
                                         item.setImage(0, new Image(this.getDisplay(), ImagesResource.NOT_SIGNED_IMG));

                                File file = new File(pdfParameter.getOrigen());
                                item.setText(1, file.getName());
                                item.setText(5, file.getPath());

                                if (pdfParameter.getSignatures() != null) {

                                        if (pdfParameter.getSignatures().isEmpty()) {
                                                item.setText(2, LanguageResource.getLanguage().getString("section.sign.not.signed"));

                                        } else {
                                                for (int i =0; i< pdfParameter.getSignatures().size() ; i++) {
                                                        PDFSignatureInfo signature = pdfParameter.getSignatures().get(i);

                                                        if (i == 0) {
                                                                populateItem(item, signature);

                                                        } else {
                                                                // 	sub item
                                                                TreeItem subItem = new TreeItem(item, SWT.NONE);
                                                                subItem.setText(1, file.getName());
                                                                populateItem(subItem, signature);
                                                                subItem.setText(5, file.getPath());
                                                        }
                                                }
                                        }    
                                }
                         }
		}
	}


	public PDFInfo getSelectedPDF() {

		if (this.tree.getItemCount() == 0 || this.tree.getSelectionCount() == 0)
			return (null);
		else  {
			TreeItem [] array = this.tree.getSelection();
			return (PDFInfo)array[0].getData();
		}
	}

	public List<PDFInfo> getSelectedPDFs() {
            List<PDFInfo> list = new ArrayList<PDFInfo>();

            if ( !(this.tree.getItemCount() == 0 || this.tree.getSelectionCount() == 0) ) {
                TreeItem [] array = this.tree.getSelection();
                for (TreeItem treeItem : array) {
                    list.add((PDFInfo)treeItem.getData());
                }
            } else if (!(this.tree.getItemCount() == 0 || this.getLayoutData()!=null)) {
                list.add((PDFInfo)this.tree.getItem(0).getData());
            }
            return (list);
	}


	public List<PDFInfo> getPdfReaderSignList() {

		List<PDFInfo> list = new ArrayList<PDFInfo>();

		if ( this.tree.getItemCount() != 0 ) {

			TreeItem [] array = this.tree.getItems();
			for (TreeItem treeItem : array) {
				list.add((PDFInfo)treeItem.getData());
			}
		}

		return (list);
	}
        
	public void setPdfReaderSignList(List<PDFInfo> list) throws CertificateHelperException {
            if (list.size() > 0){
                
                    if (list.get(list.size()-1).getDir() && !PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VIEW_CONTENT_FOLDER)){
                        
                            TreeItem item = new TreeItem(this.tree, SWT.NONE);
                            item.setData(list.get(list.size()-1));

                            if (list.get(list.size()-1).getSigned()!= null && list.get(list.size()-1).getSigned())
                                     item.setImage(0, new Image(this.getDisplay(), ImagesResource.DIR_SIGNED_IMG));
                            if (list.get(list.size()-1).getSigned()!= null && !list.get(list.size()-1).getSigned())
                                     item.setImage(0, new Image(this.getDisplay(), ImagesResource.ADD_DIR_IMG));
                            
                            item.setText(1, list.get(list.size()-1).getOrigen().substring(0, list.get(list.size()-1).getOrigen().lastIndexOf("/")));
                            item.setText(5, list.get(list.size()-1).getOrigen().substring(0, list.get(list.size()-1).getOrigen().lastIndexOf("/")));                        
                            
                    } else {
                        
                            for (int i=this.tree.getItemCount(); i<list.size(); i++){
                                    PDFInfo pdfParameter = list.get(i);
                                    TreeItem item1 = new TreeItem(this.tree, SWT.NONE);
                                    item1.setData(pdfParameter);
                                        
                                    if (pdfParameter.getSigned()!= null && pdfParameter.getSigned())
                                        item1.setImage(0, new Image(this.getDisplay(), ImagesResource.SIGNED_IMG));
                                    if (pdfParameter.getSigned()!= null && !pdfParameter.getSigned())
                                        item1.setImage(0, new Image(this.getDisplay(), ImagesResource.NOT_SIGNED_IMG));

                                    File file = new File(pdfParameter.getOrigen());
                                    item1.setText(1, file.getName());
                                    item1.setText(5, file.getPath());

                                    if (pdfParameter.getSignatures() != null) {

                                            if (pdfParameter.getSignatures().isEmpty()) {
                                                    item1.setText(2, LanguageResource.getLanguage().getString("section.sign.not.signed"));

                                            } else {
                                                    for (int j =0; j< pdfParameter.getSignatures().size() ; j++) {
                                                            PDFSignatureInfo signature = pdfParameter.getSignatures().get(j);

                                                            if (j == 0) {
                                                                    populateItem(item1, signature);

                                                            } else {
                                                                    // 	sub item
                                                                    TreeItem subItem = new TreeItem(item1, SWT.NONE);
                                                                    subItem.setText(1, file.getName());
                                                                    populateItem(subItem, signature);
                                                                    subItem.setText(5, file.getPath());
                                                            }
                                                    }
                                            }
                                    }
                            }
                     }
               }
	}

	public void populateItem(TreeItem item, PDFSignatureInfo signature) throws CertificateHelperException {

		item.setText(2, signature.getName());


		if (signature.getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_VALID)
			item.setImage(3, new Image(this.getDisplay(), ImagesResource.OK_IMG));

		else if (signature.getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_ERROR)
			item.setImage(3, new Image(this.getDisplay(), ImagesResource.CANCEL_IMG));
                
                else if (signature.getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_INVALID)
			item.setImage(3, new Image(this.getDisplay(), ImagesResource.CANCEL_IMG));

		else if (signature.getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_WARNING)
			item.setImage(3, new Image(this.getDisplay(), ImagesResource.WARNING_IMG));


		// signer chain
		List<X509Certificate> chain = signature.getChain();
		if (chain != null && chain.size() != 0) {
			item.setText(3, " " + CertificateHelper.getCN(signature.getChain().get(0)));
		}

		// date
		if (signature.getDate() != null) {
			SimpleDateFormat dateFormat = LanguageResource.getFullFormater();
			item.setText(4, " " + dateFormat.format(signature.getDate()));

			if (signature.getDateSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_VALID)
				item.setImage(4, new Image(this.getDisplay(), ImagesResource.OK_IMG));

			else if (signature.getDateSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_ERROR)
				item.setImage(4, new Image(this.getDisplay(), ImagesResource.CANCEL_IMG));

			else if (signature.getDateSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_WARNING)
				item.setImage(4, new Image(this.getDisplay(), ImagesResource.WARNING_IMG));

		}

	}

	public void addSelectionListener(SelectionListener s) {

		this.tree.addSelectionListener(s);
	}

	// TODO esto deberia de estar fuera de este componente (con setters)
	public void addFiles (boolean dir) throws CertificateHelperException {
            
                List<File> fileList = null;
                List<PDFInfo> listPdfReaderSign = null;
                
		if (dir && !PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VIEW_CONTENT_FOLDER)) {
                    
			fileList = FileDialogs.openDirDialog(this.tree.getShell(), FileDialogs.PDF_TYPE);
                        listPdfReaderSign = getPdfReaderSignList();

                        if (fileList.size() > 0) {
                            PDFInfo pdfReaderSign = new PDFInfo();
                            pdfReaderSign.setOrigen(fileList.get(fileList.size()-1).getAbsolutePath());
                            pdfReaderSign.setDir(true);
                            listPdfReaderSign.add(pdfReaderSign);
                        }
                        
                } else {
                    
                        if (dir) fileList = FileDialogs.openDirDialog(this.tree.getShell(), FileDialogs.PDF_TYPE);
                        else fileList = FileDialogs.openFilesDialog(this.tree.getShell(), FileDialogs.PDF_TYPE);
                    
                        listPdfReaderSign = getPdfReaderSignList();

                        for (File file : fileList) {
                            
                                boolean sig = ValidarPDFHelper.isSigned(file.getAbsolutePath());
                                PDFInfo pdfReaderSign = new PDFInfo();
                                pdfReaderSign.setOrigen(file.getAbsolutePath());
                                if (sig) pdfReaderSign.setSigned(true);
                                
                                listPdfReaderSign.add(pdfReaderSign);
                        }
                }
                
                if(fileList.size()>0)
                    setPdfReaderSignList(listPdfReaderSign);

	}
        
        //OJO... Modificacion Felix
        public void addFilesFromFolder (boolean dir, String ruta) throws CertificateHelperException {

                List<File> fileList = null;
                if (dir)
                    fileList = FileDialogs.listarArchivos(ruta, FileDialogs.PDF_TYPE);
                else {
                    File arch = new File(ruta);
                    fileList = new ArrayList<File>();
                    fileList.add(arch);
                }


                List<PDFInfo> listPdfReaderSign = getPdfReaderSignList();

                for (File file : fileList) {
                    
                        //Modificacion... Yessica
                        boolean sig = ValidarPDFHelper.isSigned(file.getAbsolutePath());

                        PDFInfo pdfReaderSign = new PDFInfo();
                        pdfReaderSign.setOrigen(file.getAbsolutePath());
                        
                        //Si esta firmado -> true
                        if (sig) pdfReaderSign.setSigned(true);
                        
                        listPdfReaderSign.add(pdfReaderSign);
                }

                setPdfReaderSignList(listPdfReaderSign);
	}
        
        public void addFilesFromDB (String driver, String url, String dbname,
                                    String function, String user, String pass,
                                    String campoOrigen, String campoDestino,
                                    String usuSistema, String passwordSistema,
                                    int userType) throws CertificateHelperException {
            
                List<String> path = new ArrayList<>();
                List<String> destination = new ArrayList<>();
                
                try{
                    Class.forName(driver);
                    try (Connection con = DriverManager.getConnection(url+dbname, user, pass)) {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("select * from " + function + "('" + usuSistema + "','" + passwordSistema + "'," + userType + ")");
                        while (rs.next()){
                            String origen = rs.getString(campoOrigen);
                            String destino = rs.getString(campoDestino);
                            
                            path.add(origen);
                            destination.add(destino);
                        }
                        stmt.close();
                    }

                } catch(Exception e) {
                    InfoDialog id = new InfoDialog(tree.getShell());
                    id.open(e.getLocalizedMessage());
                }
                
                List<PDFInfo> listPdfReaderSign = getPdfReaderSignList();                
                for (int x=0; x< path.size(); x++) {
                        PDFInfo pdfReaderSign = new PDFInfo();
                        pdfReaderSign.setOrigen(path.get(x));
                        pdfReaderSign.setDestino(destination.get(x));
                        pdfReaderSign.setDir(true);
                        listPdfReaderSign.add(pdfReaderSign);
                }

                setPdfReaderSignList(listPdfReaderSign);
	}
        //**********************************************************************
        
	// TODO y esto realmente tambien, pero como asi esta mas encapsulado me queda mas limpio
	public void removeFile() {

		if (this.tree.getItemCount() == 0 || this.tree.getSelectionCount() == 0) {

			String mensaje = LanguageResource.getLanguage().getString("error.no_selected_file");
//			logger.info(mensaje);
			LoggingDesktopController.printError(mensaje);

		} else {
			TreeItem [] selected = this.tree.getSelection();

			for (TreeItem treeItem : selected) {
				// TODO libera los objetos de la memoria? o solo la entrada en si?
				treeItem.dispose();
			}
		}
	}

	class SupButtonKeyListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
			if (SWT.DEL == e.character) {
				removeFile();
			}
		}
	}

}