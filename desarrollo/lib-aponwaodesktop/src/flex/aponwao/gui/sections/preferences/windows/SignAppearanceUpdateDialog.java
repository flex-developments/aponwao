package flex.aponwao.gui.sections.preferences.windows;

import java.util.Map;
import java.util.logging.Logger;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.aponwao.gui.sections.global.windows.InfoDialog;
import flex.aponwao.gui.sections.preferences.helpers.ImageSignPreferences;
import java.util.TreeMap;

import java.awt.Rectangle;
import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author yessica
 */
public class SignAppearanceUpdateDialog extends Dialog {

	private static Logger	logger	= Logger.getLogger(SignAppearanceUpdateDialog.class.getName());

	private Shell sShell = null;

        private static final int POSX_DEFAULT = 0;
        private static final int POSY_DEFAULT = 0;
        private static final int HEIGHT_DEFAULT = 40;
        private static final int WIDTH_DEFAULT = 60;
        private static final String PAGE_DEFAULT = "1";
        private static final String REASON_DEFAULT = "Aprobación";
        private static final String LOCATE_DEFAULT = "Venezuela";
        private static final String PATH_DEFAULT = ResourceHelper.getRESOURCES_PATH()+
                File.separator +"images" + File.separator + "suscerte.png";

        private Button checkVisible = null;
        private Button radioAdobe = null;
        private Button radioImage = null;
        private Button checkSello = null;

	private Text textName = null;
        private Text textPath = null;
        private Text textPage = null;
        private Text textReason = null;
        private Text textLocate = null;

        private static String valuePrimeraDefault = "1";
        private static String valueUltimaDefault = "-1";
        private static String valueTodasDefault = "0";
        private static String valueOtraDefault = "2";
        private Map<String, String>  tempMapPage = null;
        private Combo comboPage = null;

        private int posX;
        private int posY;
        private int height;
        private int width;

	private Map<String, ImageSignPreferences>  tempMap = null;
	private String selectedName = null;

        private Rectangle rectangle = null;


	public SignAppearanceUpdateDialog(Shell parent, Map<String, ImageSignPreferences> map) {

		super(parent);
		this.tempMap = map;

		posX = POSX_DEFAULT;
                posY = POSY_DEFAULT;
                width = HEIGHT_DEFAULT;
                height = WIDTH_DEFAULT;                

		this.rectangle = new Rectangle(
				posX,
				posY,
				width,
				height);

                // inicializar la lista con los valores de las paginas
		tempMapPage = new TreeMap<String, String>();
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.first"), valuePrimeraDefault);
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.last"), valueUltimaDefault);
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.all"), valueTodasDefault);
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.other"), valueOtraDefault);
	}

	public SignAppearanceUpdateDialog(Shell parent, Map<String, ImageSignPreferences> map, String selectedName) {
		super(parent);
		this.tempMap = map;
		this.selectedName = selectedName;

                posX = new Integer(tempMap.get(selectedName).getPosX());
                posY = new Integer(tempMap.get(selectedName).getPosY());
                width = new Integer(tempMap.get(selectedName).getWidth());
                height = new Integer(tempMap.get(selectedName).getHeight());

		this.rectangle = new Rectangle(
				posX,
				posY,
				width,
				height);

                // inicializar la lista con los valores de las paginas
		tempMapPage = new TreeMap<String, String>();
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.first"), valuePrimeraDefault);
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.last"), valueUltimaDefault);
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.all"), valueTodasDefault);
                tempMapPage.put(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.other"), valueOtraDefault);
	}


	public void open() {

		Shell parent = getParent();

		sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		sShell.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.title"));
		sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
                gridLayout.makeColumnsEqualWidth = false;
		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginTop = 5;
		sShell.setLayout(gridLayout);

		createCompositeFields();
		createCompositeButtons();

		sShell.pack();

		// to center the shell on the screen
		Monitor primary = this.sShell.getDisplay().getPrimaryMonitor();
                org.eclipse.swt.graphics.Rectangle bounds = primary.getBounds();
                org.eclipse.swt.graphics.Rectangle rect = this.sShell.getBounds();
                int x = bounds.x + (bounds.width - rect.width) / 2;
                int y = bounds.y + (bounds.height - rect.height) / 3;
                this.sShell.setLocation(x, y);

		sShell.open();

		Display display = parent.getDisplay();

		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}


	private void createCompositeFields() {

                Label labelName = new Label(sShell, SWT.NONE);
		labelName.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.name"));
		textName = new Text(sShell, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.minimumWidth = 400;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		textName.setLayoutData(gd);

                checkVisible = new Button(sShell, SWT.CHECK);
		checkVisible.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.sign_visible"));
		GridData gdVisible = new GridData();
		gdVisible.horizontalSpan = 4;
		gdVisible.grabExcessHorizontalSpace = true;
		checkVisible.setLayoutData(gdVisible);
                checkVisible.addSelectionListener(new CheckVisibleListener());

                GridData radioGrid = new GridData();
		radioGrid.horizontalSpan = 4;
		radioGrid.grabExcessHorizontalSpace = true;

                radioAdobe = new Button(sShell, SWT.RADIO);
		radioAdobe.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.sign_visible.adobe"));
                radioAdobe.setLayoutData(radioGrid);

                radioImage = new Button(sShell, SWT.RADIO);
		radioImage.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.sign_visible.image"));
                radioImage.setLayoutData(radioGrid);

                checkSello = new Button(sShell, SWT.CHECK);
		checkSello.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp_active"));
		GridData gdSello = new GridData();
		gdSello.horizontalSpan = 4;
		gdSello.grabExcessHorizontalSpace = true;
		checkSello.setLayoutData(gdSello);
                checkSello.addSelectionListener(new CheckSelloListener());

                Label labelPath = new Label(sShell, SWT.NONE);
		labelPath.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.path"));
		textPath = new Text(sShell, SWT.BORDER);
		GridData gdPath = new GridData();
		gdPath.horizontalSpan = 1;
		gdPath.minimumWidth = 300;
                gdPath.horizontalAlignment = GridData.FILL;
		gdPath.grabExcessHorizontalSpace = true;
		textPath.setLayoutData(gdPath);

                Button buttonBrowse = new Button(sShell, SWT.NONE);
		buttonBrowse.setText(LanguageResource.getLanguage().getString("button.browse"));
		buttonBrowse.addSelectionListener(new ButtonBrowseListener());

                Button buttonPosition = new Button(sShell, SWT.NONE);
		buttonPosition.setImage(new Image(sShell.getDisplay(), ImagesResource.STAMP_POSITION_IMG));
		buttonPosition.addSelectionListener(new ButtonPositionListener());

                Label labelPage = new Label(sShell, SWT.NONE);
		labelPage.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.page"));

                comboPage = new Combo(sShell, SWT.READ_ONLY);
		comboPage.addSelectionListener(new ComboPageChangeListener());

                textPage = new Text(sShell, SWT.BORDER);
		GridData gdTextPage = new GridData();
                gdTextPage.horizontalSpan = 2;
		gdTextPage.minimumWidth = 100;
		gdTextPage.horizontalAlignment = SWT.FILL;
		gdTextPage.grabExcessHorizontalSpace = true;
		textPage.setLayoutData(gdTextPage);

                if (selectedName != null)
                    textPage.setText(tempMap.get(selectedName).getPage());
                else textPage.setText(PAGE_DEFAULT);

                reloadcomboPage();

                Label labelReason = new Label(sShell, SWT.NONE);
		labelReason.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.reason"));
                GridData gdlabelReason = new GridData();
		gdlabelReason.horizontalSpan = 3;
		gdlabelReason.grabExcessHorizontalSpace = true;
		labelReason.setLayoutData(gdlabelReason);

		textReason = new Text(sShell, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gdReason = new GridData();
                gdReason.horizontalSpan = 4;
		gdReason.grabExcessHorizontalSpace = true;
		gdReason.horizontalAlignment = SWT.FILL;
		gdReason.grabExcessVerticalSpace = true;
		gdReason.verticalAlignment = SWT.FILL;
		textReason.setLayoutData(gdReason);

                Label labelLocate = new Label(sShell, SWT.NONE);
		labelLocate.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.locate"));
                GridData gdLocation = new GridData();
		gdLocation.horizontalSpan = 3;
		gdLocation.grabExcessHorizontalSpace = true;
		labelLocate.setLayoutData(gdLocation);

		textLocate = new Text(sShell, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gdLocate = new GridData();
                gdLocate.horizontalSpan = 4;
		gdLocate.grabExcessHorizontalSpace = true;
		gdLocate.horizontalAlignment = SWT.FILL;
		gdLocate.grabExcessVerticalSpace = true;
		gdLocate.verticalAlignment = SWT.FILL;
		textLocate.setLayoutData(gdLocate);


		if (selectedName != null) {
                    //Valores de la imagen seleccionada
                    textName.setText(selectedName);
                    textPath.setText(tempMap.get(selectedName).getPath());
                    textPage.setText(tempMap.get(selectedName).getPage());
                    textReason.setText(tempMap.get(selectedName).getReason());
                    textLocate.setText(tempMap.get(selectedName).getLocate());
                    checkSello.setSelection(Boolean.parseBoolean(tempMap.get(selectedName).getImageVisible()));

                    if (!(checkSello.getSelection())) textPath.setEnabled(false);
                    
                    if (tempMap.get(selectedName).getType().equalsIgnoreCase("noimage"))
                        checkVisible.setSelection(false);
                    else
                        checkVisible.setSelection(true);

                    if (tempMap.get(selectedName).getType().equalsIgnoreCase("adobe")){
                       radioAdobe.setSelection(true);
                       radioImage.setSelection(false);
                    } else if (tempMap.get(selectedName).getType().equalsIgnoreCase("simple")){
                        radioAdobe.setSelection(false);
                        radioImage.setSelection(true);
                    }

		} else{
                    //Valores por defecto
                    textPath.setText(PATH_DEFAULT);
                    textPage.setText(PAGE_DEFAULT);
                    checkVisible.setSelection(true);
                    radioAdobe.setSelection(false);
                    radioImage.setSelection(true);
                    checkSello.setSelection(true);
                    textReason.setText(REASON_DEFAULT);
                    textLocate.setText(LOCATE_DEFAULT);
                }

                if (!checkVisible.getSelection()){
                    radioAdobe.setEnabled(false);
                    radioImage.setEnabled(false);
                    textPath.setEnabled(false);
                    checkSello.setEnabled(false);
                }

	}

	private void createCompositeButtons() {

		Composite compositeButtons = new Composite(sShell, SWT.NONE);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan = 4;
		compositeButtons.setLayoutData(gd);

		GridLayout gridLayout5 = new GridLayout();
		gridLayout5.numColumns = 2;
		gridLayout5.horizontalSpacing = 50;
		compositeButtons.setLayout(gridLayout5);

		Button buttonAceptar = new Button(compositeButtons, SWT.NONE);
		buttonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));
		buttonAceptar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
		buttonAceptar.setLayoutData(new GridData(GridData.END));
		buttonAceptar.addSelectionListener(new ButtonOkListener());

		Button buttonCancel = new Button(compositeButtons, SWT.NONE);
		buttonCancel.setText(LanguageResource.getLanguage().getString("button.cancel"));
		buttonCancel.setImage(new Image(this.sShell.getDisplay(), ImagesResource.CANCEL_IMG));
		buttonCancel.setLayoutData(new GridData(GridData.END));
		buttonCancel.addSelectionListener(new BotonCancelarListener());

	}

	class ButtonOkListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {

			if (!textName.getText().equals("") &&
                            !textPath.getText().equals("") && !textPage.getText().equals("") &&
                            !textReason.getText().equals("") && !textLocate.getText().equals("") &&
                            textName.getText() != null &&
                            textPath.getText() != null && textPage.getText() != null &&
                            textReason.getText() != null && textLocate != null) {
                                ImageSignPreferences t = new ImageSignPreferences();
                                
				if (selectedName == null) {
                                    t.setName(textName.getText());
                                    t.setPath(textPath.getText());
                                    t.setPage(textPage.getText());
                                    t.setReason(textReason.getText());
                                    t.setLocate(textLocate.getText());
                                    t.setImageVisible(new Boolean (checkSello.getSelection()).toString());

                                    if (!checkVisible.getSelection()){
                                        t.setType("noimage");
                                    } else {
                                        if (radioAdobe.getSelection())
                                            t.setType("adobe");
                                        else if (radioImage.getSelection())
                                            t.setType("simple");
                                    }

                                    t.setPosX(String.valueOf(rectangle.x));
                                    t.setPosY(String.valueOf(rectangle.y));
                                    t.setHeight(String.valueOf(rectangle.height));
                                    t.setWidth(String.valueOf(rectangle.width));

                                    tempMap.put(t.getName(), t);
                                    
                                } else {
                                    t.setName(textName.getText());
                                    t.setPath(textPath.getText());
                                    t.setPage(textPage.getText());
                                    t.setReason(textReason.getText());
                                    t.setLocate(textLocate.getText());
                                    t.setImageVisible(new Boolean (checkSello.getSelection()).toString());

                                    if (!checkVisible.getSelection()){
                                        t.setType("noimage");
                                    } else {
                                        if (radioAdobe.getSelection())
                                            t.setType("adobe");
                                        else if (radioImage.getSelection())
                                            t.setType("simple");
                                    }

                                    t.setPosX(String.valueOf(rectangle.x));
                                    t.setPosY(String.valueOf(rectangle.y));
                                    t.setHeight(String.valueOf(rectangle.height));
                                    t.setWidth(String.valueOf(rectangle.width));
                                    
                                    tempMap.remove(selectedName);
                                    tempMap.put(t.getName(), t);
				}

				sShell.dispose();

			} else {

				InfoDialog id = new InfoDialog(sShell);
				id.open(LanguageResource.getLanguage().getString("info.campos_obligatorios"));
			}

		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

        class ButtonBrowseListener implements SelectionListener {

            public void widgetSelected(SelectionEvent event) {
                    String ruta = FileDialogs.openFileDialog(sShell, FileDialogs.IMAGE_TYPE);
                    if (ruta != null)
                            textPath.setText(ruta);
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                    widgetSelected(event);
            }
	}

         class CheckVisibleListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
                    if (!checkVisible.getSelection()){
                        radioAdobe.setEnabled(false);
                        radioImage.setEnabled(false);
                        textPath.setEnabled(false);
                        checkSello.setEnabled(false);
                    } else {
                        radioAdobe.setEnabled(true);
                        radioImage.setEnabled(true);
                        textPath.setEnabled(true);
                        checkSello.setEnabled(true);
                    }
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

         class ComboPageChangeListener implements SelectionListener {
                @Override
		public void widgetSelected(SelectionEvent event) {
                    if(comboPage.getText().equals(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.first"))) {
                        //Primera pagina
                        textPage.setVisible(false);
                        textPage.setText(valuePrimeraDefault);
                    } else if(comboPage.getText().equals(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.last"))) {
                        //Ultima pagina
                        textPage.setVisible(false);
                        textPage.setText(valueUltimaDefault);
                    } else if(comboPage.getText().equals(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.all"))) {
                        //Todas las paginas
                        textPage.setVisible(false);
                        textPage.setText(valueTodasDefault);
                    } else {
                        //Pagina N°
                        textPage.setVisible(true);
                        textPage.setText(valueOtraDefault);
                    }
		}

                @Override
		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

        private void reloadcomboPage() {
		// cargar combo
		comboPage.removeAll();
		for (String name : tempMapPage.keySet()) {
			comboPage.add(name);
		}
		comboPage.getParent().layout();

                if((textPage.getText()==null)||(textPage.getText().equals(""))) textPage.setText(valuePrimeraDefault);

                if(textPage.getText().equals(valuePrimeraDefault)) {
                    textPage.setVisible(false);
                    comboPage.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.first"));

                } else if(textPage.getText().equals(valueUltimaDefault)) {
                    textPage.setVisible(false);
                    comboPage.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.last"));

                } else if(textPage.getText().equals(valueTodasDefault)) {
                    textPage.setVisible(false);
                    comboPage.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.all"));

                } else {
                    textPage.setVisible(true);
                    comboPage.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.page.other"));
                }
	}

        class ButtonPositionListener implements SelectionListener {

            @Override
            public void widgetSelected(SelectionEvent event) {

                    ImagePositionDialog imagePositionDialog = new ImagePositionDialog(Display.getDefault().getActiveShell(), textPath.getText(), rectangle);
                    rectangle = imagePositionDialog.createSShell();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent event) {
                    widgetSelected(event);
            }
	}

        class CheckSelloListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
                    if (!checkSello.getSelection()){
                        textPath.setEnabled(false);
                    } else {
                        textPath.setEnabled(true);
                    }
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

}

