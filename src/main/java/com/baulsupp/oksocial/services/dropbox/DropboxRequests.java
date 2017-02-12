package com.baulsupp.oksocial.services.dropbox;

import com.baulsupp.oksocial.authenticator.AuthUtil;
import com.baulsupp.oksocial.util.JsonUtil;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DropboxRequests {

  public static FileList queryFileList(OkHttpClient client, Request.Builder requestBuilder,
      String path) throws IOException {
    path = cleanPath(path);

    Request request =
        requestBuilder.url("https://api.dropboxapi.com/2/files/list_folder").method("POST",
            RequestBody.create(JsonUtil.JSON,
                "{\"path\": \"" + path + "\",\"include_media_info\": true}")).build();

    return new FileList(AuthUtil.makeJsonMapRequest(client, request));
  }

  public static String cleanPath(String path) {
    if (path == null) {
      return "";
    }

    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    return path;
  }
}
