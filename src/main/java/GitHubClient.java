import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GitHubClient {
    private final String owner;
    private final String repo;
    private final String token;

    public List<Commit> getCommitsFromBranch(String branch) throws IOException{
        List<Commit> commits = new ArrayList<>();
        String nextPageUrl = String.format("https://api.github.com/repos/%s/%s/commits?sha=%s", owner, repo, branch);

        var client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder builder = request.newBuilder()
                            .addHeader("Authorization", "token " + token);
                    return chain.proceed(builder.build());
                })
                .build();

        while(nextPageUrl != null) {
            Request request = new Request.Builder()
                    .url(nextPageUrl)
                    .build();


            try (Response response = client.newCall(request).execute()) {
                if(!response.isSuccessful()){
                    throw new IOException();
                }

                assert response.body() != null;
                String responseBody = response.body().string();
                Gson gson = new GsonBuilder().create();
                CommitDTO commitdto = gson.fromJson(responseBody, CommitDTO.class);
                Commit current = new Commit(commitdto.getSha(), commitdto.getUrl(), commitdto.getAuthor().getDate());

                commits.add(current);

                nextPageUrl = getNextPageUrl(response.header("Link"));
            }
        }

        return commits;
    }

    private String getNextPageUrl(String link) {
        if(link == null)
            return null;

        String[] links = link.split(",");
        for (String _link : links) {
            String[] parts = _link.split(";");
            if (parts.length != 2) {
                continue;
            }

            String url = parts[0].trim().replaceAll("<(.*)>", "$1");
            String rel = parts[1].trim().replaceAll("\"", "");

            if ("next".equals(rel)) {
                return url;
            }
        }

        return null;
    }

}