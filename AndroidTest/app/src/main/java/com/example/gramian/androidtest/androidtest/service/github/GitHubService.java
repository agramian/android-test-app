package com.example.gramian.androidtest.androidtest.service.github;

import java.io.IOException;
import java.util.List;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

public final class GitHubService {
    public static final String API_URL = "https://api.github.com";

    private Retrofit retrofit;
    private GitHub github;

    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
    }

    public GitHubService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        github = retrofit.create(GitHub.class);
    }

    public List<Contributor> getContributors() throws IOException {
        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        // Fetch and print a list of the contributors to the library.
        List<Contributor> contributors = call.execute().body();
        return contributors;
    }
}
