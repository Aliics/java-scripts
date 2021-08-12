import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DayStart {
    public static void main(String[] args) {
        assert args.length >= 1 : "Please provide at least one ISO date or timestamp";

        for (final var dateArg : args) {
            try {
                // Optionally forms and instant from the provided argument:
                // 2021-01-01T00:00:00Z <- 1st branch (Some)
                // 1609412400000        <- 2nd branch (Some)
                // 2021-01-01           <- 3rd branch (None)
                final Optional<Instant> optionalInstant =
                        dateArg.contains("T") ? Optional.of(Instant.parse(dateArg)) :
                        !dateArg.contains("-") ? Optional.of(Instant.ofEpochMilli(Long.parseLong(dateArg))) :
                        Optional.empty();

                final long millis = optionalInstant.map(instant ->
                        // dateArg was a timestamp
                        instant.atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()
                ).orElseGet(() ->
                        // dateArg was probably a LocalDate
                        LocalDate.parse(dateArg)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()
                );

                System.out.printf("%s: (%016x) %d\n", dateArg, millis, millis);
            } catch (DateTimeParseException e) {
                System.out.printf("%s is not a valid DateTime\n", dateArg);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid timestamp\n", dateArg);
            }
        }
    }
}
