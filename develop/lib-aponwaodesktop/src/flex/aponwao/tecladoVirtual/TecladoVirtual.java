package flex.aponwao.tecladoVirtual;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class TecladoVirtual extends JPanel {
   private static Hashtable<String, String> myConf = new Hashtable<String, String>();
   private static Hashtable<String, Tecla> myKeys = new Hashtable<String, Tecla>();
   
   private static final long serialVersionUID = 1L;
   private static Boolean Upper     = true;

   private String mensaje           = null;

   private JLabel texto             = null;
   private JPasswordField pass      = null;
   
   private JButton Button0001       = null;
   private JButton Button0002       = null;
   private JButton Button0003       = null;
   private JButton Button0004       = null;
   private JButton Button0005       = null;
   private JButton Button0006       = null;
   private JButton Button0007       = null;
   private JButton Button0008       = null;
   private JButton Button0009       = null;
   private JButton Button0010       = null;
   private JButton Button0011       = null;
   private JButton Button0012       = null;
   private JButton Button0101       = null;
   private JButton Button0102       = null;
   private JButton Button0103       = null;
   private JButton Button0104       = null;
   private JButton Button0105       = null;
   private JButton Button0106       = null;
   private JButton Button0107       = null;
   private JButton Button0108       = null;
   private JButton Button0109       = null;
   private JButton Button0110       = null;
   private JButton Button0111       = null;
   private JButton Button0112       = null;
   private JButton Button0201       = null;
   private JButton Button0202       = null;
   private JButton Button0203       = null;
   private JButton Button0204       = null;
   private JButton Button0205       = null;
   private JButton Button0206       = null;
   private JButton Button0207       = null;
   private JButton Button0208       = null;
   private JButton Button0209       = null;
   private JButton Button0210       = null;
   private JButton Button0211       = null;
   private JButton Button0212       = null;
   private JButton Button0301       = null;
   private JButton Button0302       = null;
   private JButton Button0303       = null;
   private JButton Button0304       = null;
   private JButton Button0305       = null;
   private JButton Button0306       = null;
   private JButton Button0307       = null;
   private JButton Button0308       = null;
   private JButton Button0309       = null;
   private JButton Button0310       = null;
   private JButton Button0311       = null;
   private JButton Button0312       = null;
   private JButton Button0402       = null;
   private JButton Button0403       = null;
   private JButton Button0404       = null;
   private JButton Button0405       = null;
   private JButton Button0406       = null;
   private JButton Button0407       = null;
   private JButton Button0408       = null;
   private JButton Button0409       = null;
   private JButton Button0410       = null;
   private JButton Button0411       = null;
   
   private JButton SpaceButton      = null;
   private JButton CapsLockButton   = null;
   private JButton BorrarButton     = null;

   /**
    * This is the default constructor
    */
   public TecladoVirtual(Hashtable<String, String> myConf, Hashtable<String, Tecla>   myKeys, String mensaje) {
      this.mensaje = mensaje;
      TecladoVirtual.myConf = myConf;
      TecladoVirtual.myKeys = myKeys;
      initialize();
   }

   /**
    * This method initializes this
    * 
    * @return void
    */
   private void initialize() {
      final Integer   WindowX  = Integer.valueOf(this.myConf.get("WindowSizeX"));
      final Integer   WindowY  = Integer.valueOf(this.myConf.get("WindowSizeY"));
      this.setSize(WindowX, WindowY);
      this.getJContentPane();
   }
   
   /**
    * This method initializes the keyborad
    * 
    */
   private void getJContentPane() {
         this.setLayout(null);
         this.add(getJLabelArea(),null);
         this.add(getJTextArea(), null);
         
         this.add(getButton0001(), null);
         this.add(getButton0002(), null);
         this.add(getButton0003(), null);
         this.add(getButton0004(), null);
         this.add(getButton0005(), null);
         this.add(getButton0006(), null);
         this.add(getButton0007(), null);
         this.add(getButton0008(), null);
         this.add(getButton0009(), null);
         this.add(getButton0010(), null);
         this.add(getButton0011(), null);
         this.add(getButton0012(), null);
         this.add(getButton0101(), null);
         this.add(getButton0102(), null);
         this.add(getButton0103(), null);
         this.add(getButton0104(), null);
         this.add(getButton0105(), null);
         this.add(getButton0106(), null);
         this.add(getButton0107(), null);
         this.add(getButton0108(), null);
         this.add(getButton0109(), null);
         this.add(getButton0110(), null);
         this.add(getButton0111(), null);
         this.add(getButton0112(), null);
         this.add(getButton0201(), null);
         this.add(getButton0202(), null);
         this.add(getButton0203(), null);
         this.add(getButton0204(), null);
         this.add(getButton0205(), null);
         this.add(getButton0206(), null);
         this.add(getButton0207(), null);
         this.add(getButton0208(), null);
         this.add(getButton0209(), null);
         this.add(getButton0210(), null);
         this.add(getButton0211(), null);
         this.add(getButton0212(), null);
         this.add(getButton0301(), null);
         this.add(getButton0302(), null);
         this.add(getButton0303(), null);
         this.add(getButton0304(), null);
         this.add(getButton0305(), null);
         this.add(getButton0306(), null);
         this.add(getButton0307(), null);
         this.add(getButton0308(), null);
         this.add(getButton0309(), null);
         this.add(getButton0310(), null);
         this.add(getButton0311(), null);
         this.add(getButton0312(), null);
         this.add(getCapsLockButton(), null);
         this.add(getButton0402(), null);
         this.add(getButton0403(), null);
         this.add(getButton0404(), null);
         this.add(getButton0405(), null);
         this.add(getButton0406(), null);
         this.add(getButton0407(), null);
         this.add(getButton0408(), null);
         this.add(getButton0409(), null);
         this.add(getButton0410(), null);
         this.add(getButton0411(), null);
         this.add(getBorrarButton(), null);

         this.add(getSpaceButton(), null);
   }

   /**
    * This method adds a char to the text-field
    *
    * @return void
    */
   private void insertText(String addchar) {
//      int pos = pass.getCaretPosition();
      pass.setText(new String(pass.getPassword()) + addchar);
   }

   /**
    * This method adds a char to the text-field
    *
    * @return void
    */
   private void insertKeyText(Tecla Keys) {
      if(Upper) {
        pass.setText(new String(pass.getPassword()) + Keys.getUpperCase());
      } else {
        pass.setText(new String(pass.getPassword()) + Keys.getLowerCase());
      }
   }
   
   /**
    * This method inverts the hole keyboard from Upper to Lower and from Lower to Upper case.
    *
    * @return void
    */
   public void invertButtons() {
      if(Upper) {
         Upper = false;
      } else {
         Upper = true;
      }
      changeButton0001();
      changeButton0002();
      changeButton0003();
      changeButton0004();
      changeButton0005();
      changeButton0006();
      changeButton0007();
      changeButton0008();
      changeButton0009();
      changeButton0010();
      changeButton0011();
      changeButton0012();
      changeButton0101();
      changeButton0102();
      changeButton0103();
      changeButton0104();
      changeButton0105();
      changeButton0106();
      changeButton0107();
      changeButton0108();
      changeButton0109();
      changeButton0110();
      changeButton0111();
      changeButton0112();
      changeButton0201();
      changeButton0202();
      changeButton0203();
      changeButton0204();
      changeButton0205();
      changeButton0206();
      changeButton0207();
      changeButton0208();
      changeButton0209();
      changeButton0210();
      changeButton0211();
      changeButton0212();
      changeButton0301();
      changeButton0302();
      changeButton0303();
      changeButton0304();
      changeButton0305();
      changeButton0306();
      changeButton0307();
      changeButton0308();
      changeButton0309();
      changeButton0310();
      changeButton0311();
      changeButton0312();
      changeButton0402();
      changeButton0403();
      changeButton0404();
      changeButton0405();
      changeButton0406();
      changeButton0407();
      changeButton0408();
      changeButton0409();
      changeButton0410();
      changeButton0411();
      changeCapsLockButton();
   }

   /**
    * This method initializes jLabel
    *
    * @return javax.swing.JLabel
    */
   private JLabel getJLabelArea() {
      if (texto == null) {
         texto = new JLabel();
         texto.setText(mensaje);
         texto.setBounds(new Rectangle(15, 2, 561, 39));
      }
      return texto;
   }

   /**
    * This method initializes jTextArea   
    *    
    * @return javax.swing.JTextArea   
    */
   private JPasswordField getJTextArea() {
      if (pass == null) {
         pass = new JPasswordField();
         pass.setBounds(new Rectangle(34, 37, 561, 39));
         pass.setToolTipText("Inputarea");
         pass.setEditable(true);
      }
      return pass;
   }

   /**
    * This method initializes SpaceButton   
    *    
    * @return javax.swing.JButton   
    */
   private JButton getSpaceButton() {
      if (SpaceButton == null) {
         final Integer linepos      = Integer.valueOf(this.myConf.get("ButtonLine5"));
         final Integer ButtonStart  = Integer.valueOf(this.myConf.get("SpaceButtonStart"));
         final Integer ButtonX      = Integer.valueOf(this.myConf.get("SpaceButtonSizeX"));
         final Integer ButtonY      = Integer.valueOf(this.myConf.get("SpaceButtonSizeY"));

         SpaceButton                = new JButton();
         SpaceButton.setSize(new Dimension(ButtonX, ButtonY));
         SpaceButton.setLocation(new Point(ButtonStart, linepos));
         SpaceButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
               public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertText(" ");
               }
           });
      }
      return SpaceButton;
   }
   
   private JButton getCapsLockButton() {
      if (CapsLockButton == null) {
            final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine5"));
            final Integer ButtonStart    = Integer.valueOf(this.myConf.get("Button01Start"));
            final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
            final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
            final Tecla thisKeys = this.myKeys.get("CapsLockButton");
            CapsLockButton = new JButton();
            final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
            CapsLockButton.setBounds(new Rectangle(ButtonStart + myOffset, linepos, ButtonX, ButtonY));
            Insets Inset = new Insets(0,0,0,0);
            CapsLockButton.setMargin(Inset);
            CapsLockButton.setText(thisKeys.getUpperCase());
            CapsLockButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
               public void actionPerformed(java.awt.event.ActionEvent e) {
                  invertButtons();
               }
            });
            return CapsLockButton;
      }
      return CapsLockButton;
   }
   private void changeCapsLockButton() {
      final String upChar   = this.myKeys.get("CapsLockButton").getUpperCase();
      final String downChar = this.myKeys.get("CapsLockButton").getLowerCase();
      if(Upper) {
         CapsLockButton.setText(upChar);
      } else {
         CapsLockButton.setText(downChar);
      }
   }
   
    private JButton getBorrarButton() {
      if (BorrarButton == null) {
            final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine5"));
            final Integer ButtonStart    = Integer.valueOf(this.myConf.get("Button12Start"));
            final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
            final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
            final Tecla thisKeys = this.myKeys.get("BorrarButton");
            BorrarButton = new JButton();
            final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
            BorrarButton.setBounds(new Rectangle(ButtonStart + myOffset, linepos, ButtonX, ButtonY));
            Insets Inset = new Insets(0,0,0,0);
            BorrarButton.setMargin(Inset);
            BorrarButton.setText(thisKeys.getUpperCase());
            BorrarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
               public void actionPerformed(java.awt.event.ActionEvent e) {
                  String textAux = new String(pass.getPassword());
                    if (textAux.length() > 0){
                        textAux = textAux.substring(0, textAux.length()-1);
                        pass.setText(textAux);
                    }
               }
            });
            return BorrarButton;
      }
      return BorrarButton;
   }

   /**
   * This method initializes Button0001
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0001() {
      if (Button0001 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0001char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button01Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0001               = new JButton();
         Button0001.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0001.setMargin(Inset);
         Button0001.setText(thisKeys.getUpperCase());
         Button0001.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0001;
   }
   private void changeButton0001() {
      final String upChar   = this.myKeys.get("B0001char").getUpperCase();
      final String downChar = this.myKeys.get("B0001char").getLowerCase();
      if(Upper) {
         Button0001.setText(upChar);
      } else {
         Button0001.setText(downChar);
      }
   }
   /**
   * This method initializes Button0002
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0002() {
      if (Button0002 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0002char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button02Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0002               = new JButton();
         Button0002.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0002.setMargin(Inset);
         Button0002.setText(thisKeys.getUpperCase());
         Button0002.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0002;
   }
   private void changeButton0002() {
      final String upChar   = this.myKeys.get("B0002char").getUpperCase();
      final String downChar = this.myKeys.get("B0002char").getLowerCase();
      if(Upper) {
         Button0002.setText(upChar);
      } else {
         Button0002.setText(downChar);
      }
   }
   /**
   * This method initializes Button0003
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0003() {
      if (Button0003 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0003char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button03Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0003               = new JButton();
         Button0003.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0003.setMargin(Inset);
         Button0003.setText(thisKeys.getUpperCase());
         Button0003.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0003;
   }
   private void changeButton0003() {
      final String upChar   = this.myKeys.get("B0003char").getUpperCase();
      final String downChar = this.myKeys.get("B0003char").getLowerCase();
      if(Upper) {
         Button0003.setText(upChar);
      } else {
         Button0003.setText(downChar);
      }
   }
   /**
   * This method initializes Button0004
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0004() {
      if (Button0004 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0004char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button04Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0004               = new JButton();
         Button0004.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0004.setMargin(Inset);
         Button0004.setText(thisKeys.getUpperCase());
         Button0004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0004;
   }
   private void changeButton0004() {
      final String upChar   = this.myKeys.get("B0004char").getUpperCase();
      final String downChar = this.myKeys.get("B0004char").getLowerCase();
      if(Upper) {
         Button0004.setText(upChar);
      } else {
         Button0004.setText(downChar);
      }
   }
   /**
   * This method initializes Button0005
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0005() {
      if (Button0005 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0005char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button05Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0005               = new JButton();
         Button0005.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0005.setMargin(Inset);
         Button0005.setText(thisKeys.getUpperCase());
         Button0005.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0005;
   }
   private void changeButton0005() {
      final String upChar   = this.myKeys.get("B0005char").getUpperCase();
      final String downChar = this.myKeys.get("B0005char").getLowerCase();
      if(Upper) {
         Button0005.setText(upChar);
      } else {
         Button0005.setText(downChar);
      }
   }
   /**
   * This method initializes Button0001
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0006() {
      if (Button0006 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0006char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button06Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0006               = new JButton();
         Button0006.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0006.setMargin(Inset);
         Button0006.setText(thisKeys.getUpperCase());
         Button0006.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0006;
   }
   private void changeButton0006() {
      final String upChar   = this.myKeys.get("B0006char").getUpperCase();
      final String downChar = this.myKeys.get("B0006char").getLowerCase();
      if(Upper) {
         Button0006.setText(upChar);
      } else {
         Button0006.setText(downChar);
      }
   }
   /**
   * This method initializes Button0002
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0007() {
      if (Button0007 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0007char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button07Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0007               = new JButton();
         Button0007.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0007.setMargin(Inset);
         Button0007.setText(thisKeys.getUpperCase());
         Button0007.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0007;
   }
   private void changeButton0007() {
      final String upChar   = this.myKeys.get("B0007char").getUpperCase();
      final String downChar = this.myKeys.get("B0007char").getLowerCase();
      if(Upper) {
         Button0007.setText(upChar);
      } else {
         Button0007.setText(downChar);
      }
   }
   /**
   * This method initializes Button0003
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0008() {
      if (Button0008 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0008char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button08Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0008               = new JButton();
         Button0008.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0008.setMargin(Inset);
         Button0008.setText(thisKeys.getUpperCase());
         Button0008.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0008;
   }
   private void changeButton0008() {
      final String upChar   = this.myKeys.get("B0008char").getUpperCase();
      final String downChar = this.myKeys.get("B0008char").getLowerCase();
      if(Upper) {
         Button0008.setText(upChar);
      } else {
         Button0008.setText(downChar);
      }
   }
   /**
   * This method initializes Button0004
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0009() {
      if (Button0009 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0009char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button09Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0009               = new JButton();
         Button0009.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0009.setMargin(Inset);
         Button0009.setText(thisKeys.getUpperCase());
         Button0009.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0009;
   }
   private void changeButton0009() {
      final String upChar   = this.myKeys.get("B0009char").getUpperCase();
      final String downChar = this.myKeys.get("B0009char").getLowerCase();
      if(Upper) {
         Button0009.setText(upChar);
      } else {
         Button0009.setText(downChar);
      }
   }
   /**
   * This method initializes Button0005
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0010() {
      if (Button0010 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0010char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button10Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0010               = new JButton();
         Button0010.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0010.setMargin(Inset);
         Button0010.setText(thisKeys.getUpperCase());
         Button0010.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0010;
   }
   private void changeButton0010() {
      final String upChar   = this.myKeys.get("B0010char").getUpperCase();
      final String downChar = this.myKeys.get("B0010char").getLowerCase();
      if(Upper) {
         Button0010.setText(upChar);
      } else {
         Button0010.setText(downChar);
      }
   }
   /**
   * This method initializes Button0001
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0011() {
      if (Button0011 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0011char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button11Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0011               = new JButton();
         Button0011.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0011.setMargin(Inset);
         Button0011.setText(thisKeys.getUpperCase());
         Button0011.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0011;
   }
   private void changeButton0011() {
      final String upChar   = this.myKeys.get("B0011char").getUpperCase();
      final String downChar = this.myKeys.get("B0011char").getLowerCase();
      if(Upper) {
         Button0011.setText(upChar);
      } else {
         Button0011.setText(downChar);
      }
   }
   /**
   * This method initializes Button0002
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0012() {
      if (Button0012 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0012char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button12Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine0"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0012               = new JButton();
         Button0012.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0012.setMargin(Inset);
         Button0012.setText(thisKeys.getUpperCase());
         Button0012.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0012;
   }
   private void changeButton0012() {
      final String upChar   = this.myKeys.get("B0012char").getUpperCase();
      final String downChar = this.myKeys.get("B0012char").getLowerCase();
      if(Upper) {
         Button0012.setText(upChar);
      } else {
         Button0012.setText(downChar);
      }
   }
   /**
   * This method initializes Button0101
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0101() {
      if (Button0101 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0101char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button01Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0101               = new JButton();
         Button0101.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0101.setMargin(Inset);
         Button0101.setText(thisKeys.getUpperCase());
         Button0101.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0101;
   }
   private void changeButton0101() {
      final String upChar   = this.myKeys.get("B0101char").getUpperCase();
      final String downChar = this.myKeys.get("B0101char").getLowerCase();
      if(Upper) {
         Button0101.setText(upChar);
      } else {
         Button0101.setText(downChar);
      }
   }
   /**
   * This method initializes Button0102
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0102() {
      if (Button0102 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0102char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button02Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0102               = new JButton();
         Button0102.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0102.setMargin(Inset);
         Button0102.setText(thisKeys.getUpperCase());
         Button0102.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0102;
   }
   private void changeButton0102() {
      final String upChar   = this.myKeys.get("B0102char").getUpperCase();
      final String downChar = this.myKeys.get("B0102char").getLowerCase();
      if(Upper) {
         Button0102.setText(upChar);
      } else {
         Button0102.setText(downChar);
      }
   }
   /**
   * This method initializes Button0103
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0103() {
      if (Button0103 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0103char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button03Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0103               = new JButton();
         Button0103.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0103.setMargin(Inset);
         Button0103.setText(thisKeys.getUpperCase());
         Button0103.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0103;
   }
   private void changeButton0103() {
      final String upChar   = this.myKeys.get("B0103char").getUpperCase();
      final String downChar = this.myKeys.get("B0103char").getLowerCase();
      if(Upper) {
         Button0103.setText(upChar);
      } else {
         Button0103.setText(downChar);
      }
   }
   /**
   * This method initializes Button0104
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0104() {
      if (Button0104 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0104char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button04Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0104               = new JButton();
         Button0104.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0104.setMargin(Inset);
         Button0104.setText(thisKeys.getUpperCase());
         Button0104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0104;
   }
   private void changeButton0104() {
      final String upChar   = this.myKeys.get("B0104char").getUpperCase();
      final String downChar = this.myKeys.get("B0104char").getLowerCase();
      if(Upper) {
         Button0104.setText(upChar);
      } else {
         Button0104.setText(downChar);
      }
   }
   /**
   * This method initializes Button0105
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0105() {
      if (Button0105 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0105char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button05Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0105               = new JButton();
         Button0105.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0105.setMargin(Inset);
         Button0105.setText(thisKeys.getUpperCase());
         Button0105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0105;
   }
   private void changeButton0105() {
      final String upChar   = this.myKeys.get("B0105char").getUpperCase();
      final String downChar = this.myKeys.get("B0105char").getLowerCase();
      if(Upper) {
         Button0105.setText(upChar);
      } else {
         Button0105.setText(downChar);
      }
   }
   /**
   * This method initializes Button0106
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0106() {
      if (Button0106 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0106char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button06Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0106               = new JButton();
         Button0106.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0106.setMargin(Inset);
         Button0106.setText(thisKeys.getUpperCase());
         Button0106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0106;
   }
   private void changeButton0106() {
      final String upChar   = this.myKeys.get("B0106char").getUpperCase();
      final String downChar = this.myKeys.get("B0106char").getLowerCase();
      if(Upper) {
         Button0106.setText(upChar);
      } else {
         Button0106.setText(downChar);
      }
   }
   /**
   * This method initializes Button0107
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0107() {
      if (Button0107 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0107char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button07Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0107               = new JButton();
         Button0107.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0107.setMargin(Inset);
         Button0107.setText(thisKeys.getUpperCase());
         Button0107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0107;
   }
   private void changeButton0107() {
      final String upChar   = this.myKeys.get("B0107char").getUpperCase();
      final String downChar = this.myKeys.get("B0107char").getLowerCase();
      if(Upper) {
         Button0107.setText(upChar);
      } else {
         Button0107.setText(downChar);
      }
   }
   /**
   * This method initializes Button0108
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0108() {
      if (Button0108 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0108char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button08Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0108               = new JButton();
         Button0108.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0108.setMargin(Inset);
         Button0108.setText(thisKeys.getUpperCase());
         Button0108.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0108;
   }
   private void changeButton0108() {
      final String upChar   = this.myKeys.get("B0108char").getUpperCase();
      final String downChar = this.myKeys.get("B0108char").getLowerCase();
      if(Upper) {
         Button0108.setText(upChar);
      } else {
         Button0108.setText(downChar);
      }
   }
   /**
   * This method initializes Button0109
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0109() {
      if (Button0109 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0109char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button09Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0109               = new JButton();
         Button0109.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0109.setMargin(Inset);
         Button0109.setText(thisKeys.getUpperCase());
         Button0109.addActionListener(new java.awt.event.ActionListener() {
                @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0109;
   }
   private void changeButton0109() {
      final String upChar   = this.myKeys.get("B0109char").getUpperCase();
      final String downChar = this.myKeys.get("B0109char").getLowerCase();
      if(Upper) {
         Button0109.setText(upChar);
      } else {
         Button0109.setText(downChar);
      }
   }
   /**
   * This method initializes Button0110
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0110() {
      if (Button0110 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0110char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button10Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0110               = new JButton();
         Button0110.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0110.setMargin(Inset);
         Button0110.setText(thisKeys.getUpperCase());
         Button0110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0110;
   }
   private void changeButton0110() {
      final String upChar   = this.myKeys.get("B0110char").getUpperCase();
      final String downChar = this.myKeys.get("B0110char").getLowerCase();
      if(Upper) {
         Button0110.setText(upChar);
      } else {
         Button0110.setText(downChar);
      }
   }
   /**
   * This method initializes Button0111
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0111() {
      if (Button0111 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0111char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button11Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0111               = new JButton();
         Button0111.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0111.setMargin(Inset);
         Button0111.setText(thisKeys.getUpperCase());
         Button0111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0111;
   }
   private void changeButton0111() {
      final String upChar   = this.myKeys.get("B0111char").getUpperCase();
      final String downChar = this.myKeys.get("B0111char").getLowerCase();
      if(Upper) {
         Button0111.setText(upChar);
      } else {
         Button0111.setText(downChar);
      }
   }
   /**
   * This method initializes Button0112
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0112() {
      if (Button0112 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0112char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button12Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine1"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0112               = new JButton();
         Button0112.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0112.setMargin(Inset);
         Button0112.setText(thisKeys.getUpperCase());
         Button0112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0112;
   }
   private void changeButton0112() {
      final String upChar   = this.myKeys.get("B0112char").getUpperCase();
      final String downChar = this.myKeys.get("B0112char").getLowerCase();
      if(Upper) {
         Button0112.setText(upChar);
      } else {
         Button0112.setText(downChar);
      }
   }
   /**
   * This method initializes Button0201
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0201() {
      if (Button0201 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0201char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button01Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0201               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0201.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0201.setMargin(Inset);
         Button0201.setText(thisKeys.getUpperCase());
         Button0201.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0201;
   }
   private void changeButton0201() {
      final String upChar   = this.myKeys.get("B0201char").getUpperCase();
      final String downChar = this.myKeys.get("B0201char").getLowerCase();
      if(Upper) {
         Button0201.setText(upChar);
      } else {
         Button0201.setText(downChar);
      }
   }
   /**
   * This method initializes Button0202
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0202() {
      if (Button0202 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0202char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button02Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0202               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0202.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0202.setMargin(Inset);
         Button0202.setText(thisKeys.getUpperCase());
         Button0202.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0202;
   }
   private void changeButton0202() {
      final String upChar   = this.myKeys.get("B0202char").getUpperCase();
      final String downChar = this.myKeys.get("B0202char").getLowerCase();
      if(Upper) {
         Button0202.setText(upChar);
      } else {
         Button0202.setText(downChar);
      }
   }
   /**
   * This method initializes Button0203
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0203() {
      if (Button0203 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0203char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button03Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0203               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0203.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0203.setMargin(Inset);
         Button0203.setText(thisKeys.getUpperCase());
         Button0203.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0203;
   }
   private void changeButton0203() {
      final String upChar   = this.myKeys.get("B0203char").getUpperCase();
      final String downChar = this.myKeys.get("B0203char").getLowerCase();
      if(Upper) {
         Button0203.setText(upChar);
      } else {
         Button0203.setText(downChar);
      }
   }
   /**
   * This method initializes Button0204
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0204() {
      if (Button0204 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0204char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button04Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0204               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0204.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0204.setMargin(Inset);
         Button0204.setText(thisKeys.getUpperCase());
         Button0204.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0204;
   }
   private void changeButton0204() {
      final String upChar   = this.myKeys.get("B0204char").getUpperCase();
      final String downChar = this.myKeys.get("B0204char").getLowerCase();
      if(Upper) {
         Button0204.setText(upChar);
      } else {
         Button0204.setText(downChar);
      }
   }
   /**
   * This method initializes Button0205
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0205() {
      if (Button0205 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0205char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button05Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0205               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0205.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0205.setMargin(Inset);
         Button0205.setText(thisKeys.getUpperCase());
         Button0205.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0205;
   }
   private void changeButton0205() {
      final String upChar   = this.myKeys.get("B0205char").getUpperCase();
      final String downChar = this.myKeys.get("B0205char").getLowerCase();
      if(Upper) {
         Button0205.setText(upChar);
      } else {
         Button0205.setText(downChar);
      }
   }
   /**
   * This method initializes Button0206
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0206() {
      if (Button0206 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0206char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button06Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0206               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0206.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0206.setMargin(Inset);
         Button0206.setText(thisKeys.getUpperCase());
         Button0206.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0206;
   }
   private void changeButton0206() {
      final String upChar   = this.myKeys.get("B0206char").getUpperCase();
      final String downChar = this.myKeys.get("B0206char").getLowerCase();
      if(Upper) {
         Button0206.setText(upChar);
      } else {
         Button0206.setText(downChar);
      }
   }
   /**
   * This method initializes Button0207
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0207() {
      if (Button0207 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0207char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button07Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0207               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0207.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0207.setMargin(Inset);
         Button0207.setText(thisKeys.getUpperCase());
         Button0207.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0207;
   }
   private void changeButton0207() {
      final String upChar   = this.myKeys.get("B0207char").getUpperCase();
      final String downChar = this.myKeys.get("B0207char").getLowerCase();
      if(Upper) {
         Button0207.setText(upChar);
      } else {
         Button0207.setText(downChar);
      }
   }
   /**
   * This method initializes Button0208
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0208() {
      if (Button0208 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0208char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button08Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0208               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0208.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0208.setMargin(Inset);
         Button0208.setText(thisKeys.getUpperCase());
         Button0208.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0208;
   }
   private void changeButton0208() {
      final String upChar   = this.myKeys.get("B0208char").getUpperCase();
      final String downChar = this.myKeys.get("B0208char").getLowerCase();
      if(Upper) {
         Button0208.setText(upChar);
      } else {
         Button0208.setText(downChar);
      }
   }
   /**
   * This method initializes Button0209
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0209() {
      if (Button0209 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0209char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button09Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0209               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0209.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0209.setMargin(Inset);
         Button0209.setText(thisKeys.getUpperCase());
         Button0209.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0209;
   }
   private void changeButton0209() {
      final String upChar   = this.myKeys.get("B0209char").getUpperCase();
      final String downChar = this.myKeys.get("B0209char").getLowerCase();
      if(Upper) {
         Button0209.setText(upChar);
      } else {
         Button0209.setText(downChar);
      }
   }
   /**
   * This method initializes Button0210
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0210() {
      if (Button0210 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0210char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button10Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0210               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0210.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0210.setMargin(Inset);
         Button0210.setText(thisKeys.getUpperCase());
         Button0210.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0210;
   }
   private void changeButton0210() {
      final String upChar   = this.myKeys.get("B0210char").getUpperCase();
      final String downChar = this.myKeys.get("B0210char").getLowerCase();
      if(Upper) {
         Button0210.setText(upChar);
      } else {
         Button0210.setText(downChar);
      }
   }
   /**
   * This method initializes Button0211
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0211() {
      if (Button0211 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0211char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button11Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0211               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0211.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0211.setMargin(Inset);
         Button0211.setText(thisKeys.getUpperCase());
         Button0211.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0211;
   }
   private void changeButton0211() {
      final String upChar   = this.myKeys.get("B0211char").getUpperCase();
      final String downChar = this.myKeys.get("B0211char").getLowerCase();
      if(Upper) {
         Button0211.setText(upChar);
      } else {
         Button0211.setText(downChar);
      }
   }
   /**
   * This method initializes Button0212
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0212() {
      if (Button0212 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0212char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button12Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine2"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0212               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0212.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0212.setMargin(Inset);
         Button0212.setText(thisKeys.getUpperCase());
         Button0212.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0212;
   }
   private void changeButton0212() {
      final String upChar   = this.myKeys.get("B0212char").getUpperCase();
      final String downChar = this.myKeys.get("B0212char").getLowerCase();
      if(Upper) {
         Button0212.setText(upChar);
      } else {
         Button0212.setText(downChar);
      }
   }
   /**
   * This method initializes Button0301
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0301() {
      if (Button0301 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0301char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button01Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0301               = new JButton();
         Button0301.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0301.setMargin(Inset);
         Button0301.setText(thisKeys.getUpperCase());
         Button0301.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0301;
   }
   private void changeButton0301() {
      final String upChar   = this.myKeys.get("B0301char").getUpperCase();
      final String downChar = this.myKeys.get("B0301char").getLowerCase();
      if(Upper) {
         Button0301.setText(upChar);
      } else {
         Button0301.setText(downChar);
      }
   }
   /**
   * This method initializes Button0302
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0302() {
      if (Button0302 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0302char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button02Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0302               = new JButton();
         Button0302.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0302.setMargin(Inset);
         Button0302.setText(thisKeys.getUpperCase());
         Button0302.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0302;
   }
   private void changeButton0302() {
      final String upChar   = this.myKeys.get("B0302char").getUpperCase();
      final String downChar = this.myKeys.get("B0302char").getLowerCase();
      if(Upper) {
         Button0302.setText(upChar);
      } else {
         Button0302.setText(downChar);
      }
   }
   /**
   * This method initializes Button0303
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0303() {
      if (Button0303 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0303char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button03Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0303               = new JButton();
         Button0303.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0303.setMargin(Inset);
         Button0303.setText(thisKeys.getUpperCase());
         Button0303.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0303;
   }
   private void changeButton0303() {
      final String upChar   = this.myKeys.get("B0303char").getUpperCase();
      final String downChar = this.myKeys.get("B0303char").getLowerCase();
      if(Upper) {
         Button0303.setText(upChar);
      } else {
         Button0303.setText(downChar);
      }
   }
   /**
   * This method initializes Button0304
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0304() {
      if (Button0304 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0304char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button04Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0304               = new JButton();
         Button0304.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0304.setMargin(Inset);
         Button0304.setText(thisKeys.getUpperCase());
         Button0304.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0304;
   }
   private void changeButton0304() {
      final String upChar   = this.myKeys.get("B0304char").getUpperCase();
      final String downChar = this.myKeys.get("B0304char").getLowerCase();
      if(Upper) {
         Button0304.setText(upChar);
      } else {
         Button0304.setText(downChar);
      }
   }
   /**
   * This method initializes Button0305
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0305() {
      if (Button0305 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0305char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button05Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0305               = new JButton();
         Button0305.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0305.setMargin(Inset);
         Button0305.setText(thisKeys.getUpperCase());
         Button0305.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0305;
   }
   private void changeButton0305() {
      final String upChar   = this.myKeys.get("B0305char").getUpperCase();
      final String downChar = this.myKeys.get("B0305char").getLowerCase();
      if(Upper) {
         Button0305.setText(upChar);
      } else {
         Button0305.setText(downChar);
      }
   }
   /**
   * This method initializes Button0306
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0306() {
      if (Button0306 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0306char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button06Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0306               = new JButton();
         Button0306.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0306.setMargin(Inset);
         Button0306.setText(thisKeys.getUpperCase());
         Button0306.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0306;
   }
   private void changeButton0306() {
      final String upChar   = this.myKeys.get("B0306char").getUpperCase();
      final String downChar = this.myKeys.get("B0306char").getLowerCase();
      if(Upper) {
         Button0306.setText(upChar);
      } else {
         Button0306.setText(downChar);
      }
   }
   /**
   * This method initializes Button0307
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0307() {
      if (Button0307 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0307char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button07Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0307               = new JButton();
         Button0307.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0307.setMargin(Inset);
         Button0307.setText(thisKeys.getUpperCase());
         Button0307.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0307;
   }
   private void changeButton0307() {
      final String upChar   = this.myKeys.get("B0307char").getUpperCase();
      final String downChar = this.myKeys.get("B0307char").getLowerCase();
      if(Upper) {
         Button0307.setText(upChar);
      } else {
         Button0307.setText(downChar);
      }
   }
   /**
   * This method initializes Button0308
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0308() {
      if (Button0308 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0308char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button08Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0308               = new JButton();
         Button0308.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0308.setMargin(Inset);
         Button0308.setText(thisKeys.getUpperCase());
         Button0308.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0308;
   }
   private void changeButton0308() {
      final String upChar   = this.myKeys.get("B0308char").getUpperCase();
      final String downChar = this.myKeys.get("B0308char").getLowerCase();
      if(Upper) {
         Button0308.setText(upChar);
      } else {
         Button0308.setText(downChar);
      }
   }
   /**
   * This method initializes Button0309
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0309() {
      if (Button0309 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0309char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button09Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0309               = new JButton();
         Button0309.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0309.setMargin(Inset);
         Button0309.setText(thisKeys.getUpperCase());
         Button0309.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0309;
   }
   private void changeButton0309() {
      final String upChar   = this.myKeys.get("B0309char").getUpperCase();
      final String downChar = this.myKeys.get("B0309char").getLowerCase();
      if(Upper) {
         Button0309.setText(upChar);
      } else {
         Button0309.setText(downChar);
      }
   }
   /**
   * This method initializes Button0310
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0310() {
      if (Button0310 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0310char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button10Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0310               = new JButton();
         Button0310.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0310.setMargin(Inset);
         Button0310.setText(thisKeys.getUpperCase());
         Button0310.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0310;
   }
   private void changeButton0310() {
      final String upChar   = this.myKeys.get("B0310char").getUpperCase();
      final String downChar = this.myKeys.get("B0310char").getLowerCase();
      if(Upper) {
         Button0310.setText(upChar);
      } else {
         Button0310.setText(downChar);
      }
   }
   /**
   * This method initializes Button0311
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0311() {
      if (Button0311 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0311char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button11Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0311               = new JButton();
         Button0311.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0311.setMargin(Inset);
         Button0311.setText(thisKeys.getUpperCase());
         Button0311.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0311;
   }
   private void changeButton0311() {
      final String upChar   = this.myKeys.get("B0311char").getUpperCase();
      final String downChar = this.myKeys.get("B0311char").getLowerCase();
      if(Upper) {
         Button0311.setText(upChar);
      } else {
         Button0311.setText(downChar);
      }
   }
   /**
   * This method initializes Button0312
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0312() {
      if (Button0312 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0312char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button12Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine3"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0312               = new JButton();
         Button0312.setBounds(new Rectangle(start, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0312.setMargin(Inset);
         Button0312.setText(thisKeys.getUpperCase());
         Button0312.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0312;
   }
   private void changeButton0312() {
      final String upChar   = this.myKeys.get("B0312char").getUpperCase();
      final String downChar = this.myKeys.get("B0312char").getLowerCase();
      if(Upper) {
         Button0312.setText(upChar);
      } else {
         Button0312.setText(downChar);
      }
   }
   
   /**
   * This method initializes Button0402
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0402() {
      if (Button0402 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0402char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button02Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0402               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0402.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0402.setMargin(Inset);
         Button0402.setText(thisKeys.getUpperCase());
         Button0402.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0402;
   }
   private void changeButton0402() {
      final String upChar   = this.myKeys.get("B0402char").getUpperCase();
      final String downChar = this.myKeys.get("B0402char").getLowerCase();
      if(Upper) {
         Button0402.setText(upChar);
      } else {
         Button0402.setText(downChar);
      }
   }
   /**
   * This method initializes Button0403
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0403() {
      if (Button0403 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0403char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button03Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0403               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0403.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0403.setMargin(Inset);
         Button0403.setText(thisKeys.getUpperCase());
         Button0403.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0403;
   }
   private void changeButton0403() {
      final String upChar   = this.myKeys.get("B0403char").getUpperCase();
      final String downChar = this.myKeys.get("B0403char").getLowerCase();
      if(Upper) {
         Button0403.setText(upChar);
      } else {
         Button0403.setText(downChar);
      }
   }
   /**
   * This method initializes Button0404
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0404() {
      if (Button0404 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0404char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button04Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0404               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0404.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0404.setMargin(Inset);
         Button0404.setText(thisKeys.getUpperCase());
         Button0404.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0404;
   }
   private void changeButton0404() {
      final String upChar   = this.myKeys.get("B0404char").getUpperCase();
      final String downChar = this.myKeys.get("B0404char").getLowerCase();
      if(Upper) {
         Button0404.setText(upChar);
      } else {
         Button0404.setText(downChar);
      }
   }
   /**
   * This method initializes Button0405
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0405() {
      if (Button0405 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0405char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button05Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0405               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0405.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0405.setMargin(Inset);
         Button0405.setText(thisKeys.getUpperCase());
         Button0405.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0405;
   }
   private void changeButton0405() {
      final String upChar   = this.myKeys.get("B0405char").getUpperCase();
      final String downChar = this.myKeys.get("B0405char").getLowerCase();
      if(Upper) {
         Button0405.setText(upChar);
      } else {
         Button0405.setText(downChar);
      }
   }
   /**
   * This method initializes Button0406
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0406() {
      if (Button0406 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0406char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button06Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0406               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0406.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0406.setMargin(Inset);
         Button0406.setText(thisKeys.getUpperCase());
         Button0406.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0406;
   }
   private void changeButton0406() {
      final String upChar   = this.myKeys.get("B0406char").getUpperCase();
      final String downChar = this.myKeys.get("B0406char").getLowerCase();
      if(Upper) {
         Button0406.setText(upChar);
      } else {
         Button0406.setText(downChar);
      }
   }
   /**
   * This method initializes Button0407
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0407() {
      if (Button0407 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0407char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button07Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0407               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0407.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0407.setMargin(Inset);
         Button0407.setText(thisKeys.getUpperCase());
         Button0407.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0407;
   }
   private void changeButton0407() {
      final String upChar   = this.myKeys.get("B0407char").getUpperCase();
      final String downChar = this.myKeys.get("B0407char").getLowerCase();
      if(Upper) {
         Button0407.setText(upChar);
      } else {
         Button0407.setText(downChar);
      }
   }
   /**
   * This method initializes Button0408
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0408() {
      if (Button0408 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0408char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button08Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0408               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0408.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0408.setMargin(Inset);
         Button0408.setText(thisKeys.getUpperCase());
         Button0408.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0408;
   }
   private void changeButton0408() {
      final String upChar   = this.myKeys.get("B0408char").getUpperCase();
      final String downChar = this.myKeys.get("B0408char").getLowerCase();
      if(Upper) {
         Button0408.setText(upChar);
      } else {
         Button0408.setText(downChar);
      }
   }
   /**
   * This method initializes Button0409
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0409() {
      if (Button0409 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0409char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button09Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0409               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0409.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0409.setMargin(Inset);
         Button0409.setText(thisKeys.getUpperCase());
         Button0409.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0409;
   }
   private void changeButton0409() {
      final String upChar   = this.myKeys.get("B0409char").getUpperCase();
      final String downChar = this.myKeys.get("B0409char").getLowerCase();
      if(Upper) {
         Button0409.setText(upChar);
      } else {
         Button0409.setText(downChar);
      }
   }
   /**
   * This method initializes Button0410
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0410() {
      if (Button0410 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0410char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button10Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0410               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0410.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0410.setMargin(Inset);
         Button0410.setText(thisKeys.getUpperCase());
         Button0410.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0410;
   }
   private void changeButton0410() {
      final String upChar   = this.myKeys.get("B0410char").getUpperCase();
      final String downChar = this.myKeys.get("B0410char").getLowerCase();
      if(Upper) {
         Button0410.setText(upChar);
      } else {
         Button0410.setText(downChar);
      }
   }
   /**
   * This method initializes Button0411
   *
   * @return javax.swing.JButton
   */
   private JButton getButton0411() {
      if (Button0411 == null) {
         final Tecla      thisKeys = this.myKeys.get("B0411char");
         final Integer   start    = Integer.valueOf(this.myConf.get("Button11Start"));
         final Integer   linepos  = Integer.valueOf(this.myConf.get("ButtonLine4"));
         final Integer   ButtonX  = Integer.valueOf(this.myConf.get("ButtonSizeX"));
         final Integer   ButtonY  = Integer.valueOf(this.myConf.get("ButtonSizeY"));
         Button0411               = new JButton();
         final Integer   myOffset    = Integer.valueOf(this.myConf.get("ButtonLineOffset"));
         Button0411.setBounds(new Rectangle(start + myOffset, linepos, ButtonX, ButtonY));
         Insets Inset = new Insets(0,0,0,0);
         Button0411.setMargin(Inset);
         Button0411.setText(thisKeys.getUpperCase());
         Button0411.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  insertKeyText(thisKeys);
            }
         });
      }
      return Button0411;
   }
   
   private void changeButton0411() {
      final String upChar   = this.myKeys.get("B0411char").getUpperCase();
      final String downChar = this.myKeys.get("B0411char").getLowerCase();
      if(Upper) {
         Button0411.setText(upChar);
      } else {
         Button0411.setText(downChar);
      }
   }
   
   public String getPassword() {
       return new String(pass.getPassword());
   }

}