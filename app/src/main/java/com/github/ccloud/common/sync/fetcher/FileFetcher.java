package com.github.ccloud.common.sync.fetcher;

import com.github.ccloud.common.sync.meta.FileMeta;

import java.util.List;

public interface FileFetcher {

    List<FileMeta> fetch(int pageSize, int offset);
}
