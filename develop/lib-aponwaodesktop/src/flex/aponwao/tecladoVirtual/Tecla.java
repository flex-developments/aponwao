package flex.aponwao.tecladoVirtual;

public class Tecla {
   private String UpperCase   = null;
   private String LowerCase   = null;

   
   /**
    * This method returns a ready Tecla, just hand him (LowerCase, UpperCase)
    *
    * @return Tecla
    */
   public Tecla setKeys(String LowerCase, String UpperCase) {
      this.LowerCase = LowerCase;
      this.UpperCase = UpperCase;
      return this;
   }

   /**
    * This method returns LowerCase
    *
    * @return java.lang.String
    */

   public String getUpperCase() {
      return this.UpperCase;
   }

   /**
    * This method sets UpperCase
    *
    * @return void
    */

   public void setUpperCase(String UpperCase) {
      this.UpperCase = UpperCase;
   }

   
   /**
    * This method returns LowerCase
    *
    * @return java.lang.String
    */

   public String getLowerCase() {
      return this.LowerCase;
   }

   /**
    * This method sets UpperCase
    *
    * @return void
    */

   public void setLowerCase(String LowerCase) {
      this.LowerCase = LowerCase;
   }
}
