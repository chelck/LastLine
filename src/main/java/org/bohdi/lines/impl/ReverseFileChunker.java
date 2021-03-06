package org.bohdi.lines.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ReverseFileChunker implements Iterator<List<Long>> {
    private final int CHUNK_SIZE = 1024;

    private final RandomAccessFile m_file;
    private long m_filePosition;
    private final byte[] m_buffer;

    public ReverseFileChunker(RandomAccessFile file) throws IOException {
        m_file = file;
        m_file.seek(m_file.length());
        m_filePosition = m_file.getFilePointer();
        m_buffer = new byte[CHUNK_SIZE];
    }

    public boolean hasNext() {
        try {
            //System.err.format("ReverseFileChunker.hasNext() %d%n", nextChunkSize());
            return nextChunkSize() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Long> next() {

        try {
            long position = m_filePosition;
            int size = nextChunkSize();

            //System.err.format("ReverseFileChunker.next(): position: %d, size: %d%n",
            //                  position,
            //                  size);

            m_file.seek(position - size);
            m_file.readFully(m_buffer, 0, size);
            m_filePosition = position - size;

            //System.err.format("ReverseFileChunker.next().exit: %d%n", m_file.getFilePointer());
            return Util.getReverseOffsets(m_file.length(), position - size, m_buffer, size);
        } catch (IOException e) {
            throw new NoSuchElementException();
        }

    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

    private int nextChunkSize() throws IOException {
        long remaining = m_filePosition;

        if (remaining > CHUNK_SIZE)
            return CHUNK_SIZE;
        else
            return (int) remaining;
    }

}


