package com.baulsupp.oksocial.services.dropbox;

import java.util.Map;

public class FileEntry {
  private Map<String, Object> response;

  public FileEntry(Map<String, Object> response) {
    this.response = response;
  }

  public String getName() {
    return (String) response.get("name");
  }

  public boolean isFolder() {
    return response.get(".tag").equals("folder");
  }
}
