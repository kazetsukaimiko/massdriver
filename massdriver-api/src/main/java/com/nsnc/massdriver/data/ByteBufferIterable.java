package com.nsnc.massdriver.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import com.nsnc.massdriver.crypt.CryptUtils;

public class ByteBufferIterable implements Iterable<ByteBuffer> {
	final Path path;
	final long size;
	final int chunksize;

	@Override
	public Iterator<ByteBuffer> iterator() {
		try {
			return new ByteBufferIterator(path, size, chunksize);
		} catch (IOException e) {
			throw new RuntimeException("Could not saveIndex ByteBufferIterator", e);
		}
	}

	public ByteBufferIterable(Path path, int chunksize) throws IOException {
		this.path = path;
		this.size = Files.size(path);
		this.chunksize = chunksize;
	}

	private static class ByteBufferIterator implements Iterator<ByteBuffer> {
		final SeekableByteChannel seekableByteChannel;
		long remaining;
		final int chunksize;

		private ByteBufferIterator(final Path path, final long size, final int chunksize) throws IOException {
			this.seekableByteChannel = Files.newByteChannel(path);
			this.remaining = size;
			this.chunksize = chunksize;
		}

		@Override
		public boolean hasNext() {
			return remaining > 0;
		}

		@Override
		public ByteBuffer next() {
			int allocSize = (remaining > chunksize) ? chunksize : (int) remaining;
			remaining = remaining - allocSize;
			ByteBuffer byteBuffer = ByteBuffer.allocate(allocSize);
			try {
				System.out.println(seekableByteChannel.position() + ":" + remaining);
				seekableByteChannel.read(byteBuffer);
			} catch (IOException e) {
				throw new RuntimeException("Can't read to stream", e);
			}

			byteBuffer.rewind();
			return byteBuffer;
		}
	}

}