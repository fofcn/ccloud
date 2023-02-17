package com.github.ccloud.common.sync.fetcher;

import static org.junit.Assert.assertTrue;

import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.meta.FileMetaManager;
import com.github.ccloud.common.sync.progress.Progress;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FileFetchManagerTest {

    private FileFetchManager fileFetchManager;

    @Before
    public void beforeEach() {
        FileFetcher fileFetcher = new DefaultFileFetcher();
        FileMetaManager fileMetaManager = new DefaultFileMetaManager();
        fileFetchManager = new FileFetchManager(fileFetcher, fileMetaManager);
    }

    @Test
    public void test_init_when_do_nothing_then_return_true() {
        boolean initResult = fileFetchManager.init();
        assertTrue(initResult);
    }

    @Test(timeout = 2000L)
    public void test_start_when_file_meta_list_is_empty_then_sleep() {
        fileFetchManager.start();
    }

    @Test
    public void test_stop_when_everything_is_ok_then_stop_without_exception() {
        fileFetchManager.start();
        fileFetchManager.stop();
    }

    class DefaultFileFetcher implements FileFetcher {

        @Override
        public List<FileMeta> fetch(int pageSize, int offset) {
            return null;
        }
    }

    class DefaultFileMetaManager implements FileMetaManager {

        @Override
        public List<FileMeta> getFileMetaList(Progress progress) {
            return null;
        }

        @Override
        public boolean saveFileMeta(List<FileMeta> fileMetaList) {
            return true;
        }

        @Override
        public boolean setSynced(FileMeta fileMeta) {
            return false;
        }
    }
}
