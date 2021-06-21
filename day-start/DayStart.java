import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

public final class DayStart {
    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide at least one ISO date");
            return;
        }

        for (final String dateArg : args) {
            try {
                final long millis = LocalDate.parse(dateArg)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

                System.out.printf("%s: (%016x) %d\n", dateArg, millis, millis);
            } catch (final DateTimeParseException e) {
                System.out.printf("%s is not a valid DateTime\n", dateArg);
            }
        }
    }
}
