package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;


public class MailPreferences extends FieldEditorPreferencePage {
    private RadioGroupFieldEditor mailClient = null;
    private StringFieldEditor mailSMTP = null;
    private StringFieldEditor mailPort = null;
    private StringFieldEditor mailUser = null;
    private BooleanFieldEditor mailAuthentication = null;
    private BooleanFieldEditor mailTTLS = null;
    private BooleanFieldEditor mailSign = null;

    public MailPreferences() {
            super(GRID);
    }


    @Override
    protected void createFieldEditors() {
        String[][] client = new String[2][2];
        client[0][0]=LanguageResource.getLanguage().getString("preferences.email.client.aponwao");
        client[0][1]=PreferencesHelper.EMAIL_CLIENT_APONWAO;
        client[1][0]=LanguageResource.getLanguage().getString("preferences.email.client.external");
        client[1][1]=PreferencesHelper.EMAIL_CLIENT_EXTERNAL;

        mailClient = new RadioGroupFieldEditor(PreferencesHelper.EMAIL_CLIENT, LanguageResource.getLanguage().getString(
                        "preferences.email.client"), 1, client, getFieldEditorParent(), true);
        addField(mailClient);

        mailSMTP = new StringFieldEditor(PreferencesHelper.EMAIL_SMTP_HOST, LanguageResource.getLanguage()
                        .getString("preferences.email.smtp"), getFieldEditorParent());
        addField(mailSMTP);

        mailPort = new StringFieldEditor(PreferencesHelper.EMAIL_SMTP_PORT, LanguageResource.getLanguage()
                        .getString("preferences.email.port"), getFieldEditorParent());
        addField(mailPort);

        mailUser = new StringFieldEditor(PreferencesHelper.EMAIL_SMTP_USER, LanguageResource.getLanguage()
                        .getString("preferences.email.user"), getFieldEditorParent());
        addField(mailUser);

        mailAuthentication = new BooleanFieldEditor(PreferencesHelper.EMAIL_SMTP_AUTH, LanguageResource.getLanguage().getString(
                        "preferences.email.authentication"), getFieldEditorParent());
        addField(mailAuthentication);

        mailTTLS = new BooleanFieldEditor(PreferencesHelper.EMAIL_SMTP_STARTTLS_ENABLE, LanguageResource.getLanguage().getString(
                        "preferences.email.ttls"), getFieldEditorParent());
        addField(mailTTLS);

        mailSign = new BooleanFieldEditor(PreferencesHelper.EMAIL_SIGN, LanguageResource.getLanguage().getString(
                        "preferences.email.sign"), getFieldEditorParent());
        addField(mailSign);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

            super.propertyChange(event);

    }

    @Override
    public boolean performOk() {

            boolean ok = true;
            //mailClientEditor.setStringValue(mailClient.toString());

//		if (proxyEnabled != null && proxyEnabled.getBooleanValue()
//				&& (proxyHost.getStringValue() == null || proxyHost.getStringValue().equals("")
//						|| proxyPort.getStringValue() == null || proxyPort.getStringValue().equals(""))) {
//
//			InfoDialog id = new InfoDialog(this.getShell());
//			id.open(LanguageResource.getLanguage().getString("error.proxy_configuration"));
//			logger.severe(LanguageResource.getLanguage().getString("error.proxy_configuration"));
//			
//		} else {
                    ok = super.performOk();
//			ResourceHelper.configureProxy();
//		}
//		
            return ok;
    }
}