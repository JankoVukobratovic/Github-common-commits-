import lombok.*;

import java.util.Date;


@Getter
@Setter
@RequiredArgsConstructor
public class Commit {
    @NonNull String sha;
    @NonNull String url;
    @NonNull String date;
}
