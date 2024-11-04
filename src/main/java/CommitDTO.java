import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CommitDTO {

    private String sha;
    private String url;

    @SerializedName("html_url")
    private String htmlUrl;


    private CommitDetails commit;
    private Author author;
    private Committer committer;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommitDetails {
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Author {
        private String name;
        private String email;
        private String date;
        private long id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Committer {
        private String name;
        private String email;
        private String date;
    }
}
