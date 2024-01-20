package domain_layer;

public class PawBookings {
    
    private static PawBookings PB;

    private Cane caneSelezionato;

    private PawBookings(){
        
    }

    public static PawBookings getInstance(){
        if(PB == null){
            PB = new PawBookings();
            System.out.println("istanza creata");
        }
        else{
            System.out.println("Istanza già creata");
        }
        return PB;
    }





}
