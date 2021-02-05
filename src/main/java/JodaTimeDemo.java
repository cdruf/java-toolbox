import org.joda.time.LocalDate;

public class JodaTimeDemo {

    public static void main(String[] args) {
        LocalDate ld = new LocalDate();
        for (int i = 0; i < 8; i++) {
            System.out.println(ld.plusDays(i).dayOfWeek().getAsShortText());
        }

    }

}
