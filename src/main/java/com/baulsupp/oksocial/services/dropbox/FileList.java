package com.baulsupp.oksocial.services.dropbox;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class FileList {
  private Map<String, Object> response;

  public FileList(Map<String, Object> response) {
    this.response = response;
  }

  public List<String> getFileNames() {
    List<Map<String, Object>> entries = (List<Map<String, Object>>) response.get("entries");

    return entries.stream().map(m -> (String) m.get("name")).collect(toList());
  }

  public List<FileEntry> getFileEntries() {
    List<Map<String, Object>> entries = (List<Map<String, Object>>) response.get("entries");

    return entries.stream().map(FileEntry::new).collect(toList());
  }
}
