package com.baulsupp.oksocial.services.dropbox;

import com.baulsupp.oksocial.Main;
import com.baulsupp.oksocial.commands.MainAware;
import com.baulsupp.oksocial.commands.ShellCommand;
import com.baulsupp.oksocial.completion.ArgumentCompleter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static java.util.Optional.of;

public class DropboxListFile implements ShellCommand, MainAware {
  private Main main;

  @Override public String name() {
    return "dbls";
  }

  @Override public List<Request> buildRequests(OkHttpClient client, Request.Builder requestBuilder,
      List<String> arguments) throws Exception {
    String path = arguments.get(0);

    FileList fileList = DropboxRequests.queryFileList(client, requestBuilder, path);

    for (FileEntry file : fileList.getFileEntries()) {
      main.outputHandler.info(file.getName());
    }

    return Arrays.asList();
  }

  @Override public Optional<ArgumentCompleter> completer() {
    ArgumentCompleter completer = new DropboxPathCompleter(main.client, main.requestBuilder);
    return of(completer);
  }

  @Override public boolean handlesRequests() {
    return true;
  }

  @Override public Optional<String> authenticator() {
    return of("dropbox");
  }

  @Override public void setMain(Main main) {
    this.main = main;
  }
}
