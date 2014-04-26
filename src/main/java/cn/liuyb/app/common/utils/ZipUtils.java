package cn.liuyb.app.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

public class ZipUtils {
    /**
     *
     * @param zipIn
     * @param zipEntry
     * @param destDir
     * @return
     * @throws IOException
     */
    public static ZipEntry unzipDirectoryTo(ZipInputStream zipIn,
            ZipEntry zipEntry, File backupDir) throws IOException {
        if (zipEntry.isDirectory()) {
            String dirName = zipEntry.getName();
            File extractDir = new File(backupDir, dirName);
            // 采取完全覆盖的办法
            if (extractDir.exists()) {
                FileUtils.cleanDirectory(extractDir);
            } else {
                extractDir.mkdir();
            }
            do {
                ZipEntry entry = zipIn.getNextEntry();
                if (entry == null)
                    return null;
                System.out.println("unzip:" + entry.getName());

                String fileName = entry.getName();
                if (fileName.startsWith(dirName)) {
                    if (entry.isDirectory()) {
                        ZipEntry nextEntry = unzipDirectoryTo(zipIn, entry, backupDir);
                        if ((nextEntry == null)
                                || (nextEntry.getName().startsWith(dirName)))
                            return nextEntry;
                    } else {
                        File extractFile = new File(backupDir, fileName);
                        extractFile.createNewFile();
                        MyFileUtils.copyStreamToFileWithoutClose(zipIn, extractFile);
                    }
                } else return entry;
            } while (true);
        }
        return zipIn.getNextEntry();
    }

}
