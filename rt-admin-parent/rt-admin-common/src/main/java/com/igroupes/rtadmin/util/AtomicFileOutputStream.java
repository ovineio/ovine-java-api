package com.igroupes.rtadmin.util;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * A FileOutputStream that has the property that it will only show up at its
 * destination once it has been entirely written and flushed to disk. While
 * being written, it will use a .tmp suffix.
 *
 * When the output stream is closed, it is flushed, fsynced, and will be moved
 * into place, overwriting any file that already exists at that location.
 *
 * <b>NOTE</b>: on Windows platforms, it will not atomically replace the target
 * file - instead the target file is deleted before this one is moved into
 * place.
 */
public class AtomicFileOutputStream extends FilterOutputStream {
    private static final String TMP_EXTENSION = ".tmp";

    private final static Logger LOG = LoggerFactory
            .getLogger(AtomicFileOutputStream.class);

    private final File origFile; // 原文件对象
    private final File tmpFile; // 临时文件对象

    public AtomicFileOutputStream(File f) throws FileNotFoundException {
        // Code unfortunately must be duplicated below since we can't assign
        // anything
        // before calling super
        super(new FileOutputStream(new File(f.getParentFile(), f.getName()
                + TMP_EXTENSION)));
        origFile = f.getAbsoluteFile();
        tmpFile = new File(f.getParentFile(), f.getName() + TMP_EXTENSION)
                .getAbsoluteFile();
    }

    /**
     * The default write method in FilterOutputStream does not call the write
     * method of its underlying input stream with the same arguments. Instead
     * it writes the data byte by byte, override it here to make it more
     * efficient.
     */
    @Override
    public void write(byte b[], int off, int len) throws IOException {
        out.write(b, off, len);
    }

    /**
     * 关闭时将tmp文件直接转为原命名文件
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        boolean triedToClose = false, success = false;
        try {
            flush();
            ((FileOutputStream) out).getFD().sync();
            triedToClose = true;
            super.close();
            success = true;
        } finally {
            if (success) {
                boolean renamed = tmpFile.renameTo(origFile);
                if (!renamed) {
                    // On windows, renameTo does not replace.
                    if (!origFile.delete() || !tmpFile.renameTo(origFile)) {
                        throw new IOException(
                                "Could not rename temporary file " + tmpFile
                                        + " to " + origFile);
                    }
                }
            } else {
                if (!triedToClose) {
                    // If we failed when flushing, try to close it to not leak
                    // an FD
                    IOUtils.closeQuietly(out);
                }
                // close wasn't successful, try to delete the tmp file
                if (!tmpFile.delete()) {
                    LOG.warn("Unable to delete tmp file " + tmpFile);
                }
            }
        }
    }

    /**
     * Close the atomic file, but do not "commit" the temporary file on top of
     * the destination. This should be used if there is a failure in writing.
     */
    public void abort() {
        try {
            super.close();
        } catch (IOException ioe) {
            LOG.warn("Unable to abort file " + tmpFile, ioe);
        }
        if (!tmpFile.delete()) {
            LOG.warn("Unable to delete tmp file during abort " + tmpFile);
        }
    }
}
