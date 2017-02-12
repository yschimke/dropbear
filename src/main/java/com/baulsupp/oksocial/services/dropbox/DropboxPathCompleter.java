package com.baulsupp.oksocial.services.dropbox;

import com.baulsupp.oksocial.Main;
import com.baulsupp.oksocial.completion.ArgumentCompleter;
import com.baulsupp.oksocial.completion.UrlList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.baulsupp.oksocial.services.dropbox.DropboxRequests.cleanPath;
import static java.util.stream.Collectors.toList;

public class DropboxPathCompleter implements ArgumentCompleter {
  private OkHttpClient client;
  private Request.Builder requestBuilder;

  public DropboxPathCompleter(OkHttpClient client, Request.Builder requestBuilder) {
    this.client = client;
    this.requestBuilder = requestBuilder;
  }

  @Override public UrlList urlList(String path) throws IOException {
    if (path.endsWith("/")) {
      path = cleanPath(path);
    } else {
      path = cleanPath(new File(path).getParent());
    }

    List<String> completions = completions(path);

    return new UrlList(UrlList.Match.EXACT, completions);
  }

  private List<String> completions(String parentPath) throws IOException {
    List<String> completions;
    FileList files =
        DropboxRequests.queryFileList(client, requestBuilder, parentPath);

    String prefix = parentPath.equals("") ? parentPath : parentPath + "/";

    completions = files.getFileEntries().stream().flatMap(f -> {
      String childPath = prefix + f.getName();
      if (f.isFolder()) {
        return Stream.of(childPath, childPath + "/");
      } else {
        return Stream.of(childPath);
      }
    }).collect(toList());
    return completions;
  }

  public static void main(String[] args) {
    System.setProperty("command.name", "dbls");

    Main.main("--urlCompletion", "Photos");
  }
}
