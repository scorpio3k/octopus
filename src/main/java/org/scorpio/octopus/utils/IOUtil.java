package org.scorpio.octopus.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * IO操作工具类，提供了对输入流、输出流、字节流、字符流的操作。
 * <p>
 * 此类的部分源码参考自APACHE项目
 * </p>
 * 
 */
public class IOUtil {

	/**
	 * 获取编码后的文件名
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static String getEncodeFileName(String fileName) {
		return getEncodeFileName(null, fileName);
	}

	/**
	 * 获取编码后的文件名
	 * 
	 * @param agent
	 *            浏览器代理
	 * @param fileName
	 *            文件名
	 */
	public static String getEncodeFileName(String agent, String fileName) {
		String result = fileName;
		try {
			if (agent != null && agent.toLowerCase().indexOf("firefox") > -1) {
				// fireFox下载时会对空格进行截断，暂时将文件名中的空格替换掉
				result = StringUtils.replace(fileName, " ", "");
				result = new String(result.getBytes("GBK"), "iso8859-1");
			} else {
				result = URLEncoder.encode(result, "UTF-8");
				result = StringUtils.replace(result, "+", "%20");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * 
	 * 
	 * 从控制台读取一串字符串
	 * 
	 * 
	 * 
	 * @return 读取的字符串
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jun 15, 2008 6:42:29 PM
	 * 
	 */
	public static String readStringFromSystemIn() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} finally {
			if (br != null)
				br.close();
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 将输入流输出到输出流
	 * 
	 * @deprecated 由 {@link #copy(InputStream, OutputStream)}取代
	 * 
	 * @param in
	 * 
	 *            输入流
	 * 
	 * @param out
	 * 
	 *            输出流
	 * 
	 * @param bufferSize
	 * 
	 *            缓冲区大小
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jun 15, 2008 5:57:24 PM
	 * 
	 */

	public static void in2OutStream(InputStream in, OutputStream out,

			int bufferSize) throws IOException {

		byte[] buffer = new byte[bufferSize];// 缓冲区

		for (int bytesRead = 0; (bytesRead = in.read(buffer)) != -1;) {

			out.write(buffer, 0, bytesRead);

			Arrays.fill(buffer, (byte) 0);

		}

	}

	/**
	 * 
	 * 
	 * 
	 * 将reader中的内容写入writer
	 * 
	 * @deprecated 由{@link #copy(Reader, Writer)}取代
	 * 
	 * 
	 * @param bufferSize
	 *            缓冲区大小
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jul 2, 2008 11:22:42 PM
	 * 
	 */

	public static void read2Writer(Reader in, Writer out, int bufferSize)

			throws IOException {

		char[] buffer = new char[bufferSize];// 缓冲区

		for (int bytesRead = 0; (bytesRead = in.read(buffer)) != -1;) {

			out.write(buffer, 0, bytesRead);

			buffer = new char[bufferSize];

		}

	}

	/**
	 * 
	 * 
	 * 
	 * 将reader中的内容写入writer(逐行)
	 * 
	 * 
	 * 
	 * @param in
	 *            缓冲区大小
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jul 2, 2008 11:22:42 PM
	 * 
	 */

	public static void read2Writer(BufferedReader in, PrintWriter out)

			throws IOException {

		for (String line; (line = in.readLine()) != null;) {

			out.println(line);

		}

	}

	/**
	 * 
	 * 
	 * 
	 * 从Reader对象中读取字符串
	 * 
	 * @deprecated 由{@link #toString(Reader)}取代
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jul 2, 2008 11:08:32 PM
	 * 
	 */

	public static String readString(Reader reader) throws IOException {

		PrintWriter sw = null;

		BufferedReader in = null;

		try {

			in = new BufferedReader(reader);

			StringWriter swr = new StringWriter();

			sw = new PrintWriter(swr, true);

			read2Writer(in, sw);

			return swr.toString();

		} finally {

			try {

				if (in != null)

					in.close();

			} finally {

				if (sw != null)

					sw.close();

			}

		}

	}

	/**
	 * 
	 * 生成对象输入流。
	 * 
	 * 当ObjectInputStream对象调用
	 * 
	 * readObject()时,会从ByteArrayInputStream流中反序列化出的对象
	 * 
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jun 15, 2008 7:07:53 PM
	 * 
	 */

	public static ObjectInputStream buildObjectInputStream(

			ByteArrayInputStream bi) throws IOException {

		return new ObjectInputStream(bi);

	}

	/**
	 * 
	 * 生成对象输出流。
	 * 
	 * 当ObjectOutputStream对象调用
	 * 
	 * writeObject(o);时,o对象会序列化到ByteArrayOutputStream流中去
	 * 
	 * 
	 * 
	 * @param bos
	 * 
	 *            字节数组流
	 * 
	 * @return 对象输出流
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 * @since Jun 15, 2008 7:06:00 PM
	 * 
	 */

	public static ObjectOutputStream buildObjectOutputStream(

			ByteArrayOutputStream bos) throws IOException {

		return new ObjectOutputStream(bos);

	}

	/**
	 * 返回{@link BufferedReader}。
	 * 
	 * @param str
	 *            提供字符流的字符串
	 */
	public static BufferedReader buildBufferedReader(String str) {

		return new BufferedReader(new StringReader(str));

	}

	/**
	 * 返回{@link ByteArrayInputStream}。 Creates a ByteArrayInputStream so that it
	 * uses buf as its buffer array. The buffer array is not copied. The initial
	 * value of pos is 0 and the initial value of count is the length of buf.
	 * 
	 * @param str
	 *            提供字节流的字符串
	 */
	public static ByteArrayInputStream buildByteArrayInputStream(String str) {

		return new ByteArrayInputStream(str.getBytes());

	}

	/**
	 * 返回{@link ByteArrayInputStream}。 Creates a ByteArrayInputStream so that it
	 * uses buf as its buffer array. The buffer array is not copied. The initial
	 * value of pos is 0 and the initial value of count is the length of buf.
	 * 
	 * @param bt
	 *            提供字节流的字节数组
	 */
	public static ByteArrayInputStream buildByteArrayInputStream(byte[] bt) {

		return new ByteArrayInputStream(bt);

	}

	/**
	 * 返回{@link BufferedReader}。
	 * 
	 * @param is
	 *            提供字符流的输入流
	 */

	public static BufferedReader buildReader(InputStream is) {

		return new BufferedReader(new InputStreamReader(is));

	}
	// NOTE: This class is focussed on InputStream, OutputStream, Reader and
	// Writer. Each method should take at least one of these as a parameter,
	// or return one of them.

	/**
	 * The Unix directory separator character.
	 */
	public static final char DIR_SEPARATOR_UNIX = '/';
	/**
	 * The Windows directory separator character.
	 */
	public static final char DIR_SEPARATOR_WINDOWS = '\\';
	/**
	 * The system directory separator character.
	 */
	public static final char DIR_SEPARATOR = File.separatorChar;
	/**
	 * The Unix line separator string.
	 */
	public static final String LINE_SEPARATOR_UNIX = "\n";
	/**
	 * The Windows line separator string.
	 */
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
	/**
	 * The system line separator string.
	 */
	public static final String LINE_SEPARATOR;

	static {
		// avoid security issues
		StringWriter buf = new StringWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
	}

	/**
	 * The default buffer size to use.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	// -----------------------------------------------------------------------
	/**
	 * 关闭<code>Reader</code>。 Unconditionally close an <code>Reader</code>.
	 * <p>
	 * Equivalent to {@link Reader#close()}, except any exceptions will be
	 * ignored. This is typically used in finally blocks.
	 *
	 * @param input
	 *            the Reader to close, may be null or already closed
	 */
	public static void close(Reader input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 关闭<code>Writer</code>。 Unconditionally close a <code>Writer</code>.
	 * <p>
	 * Equivalent to {@link Writer#close()}, except any exceptions will be
	 * ignored. This is typically used in finally blocks.
	 *
	 * @param output
	 *            the Writer to close, may be null or already closed
	 */
	public static void close(Writer output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 关闭输入流。 Unconditionally close an <code>InputStream</code>.
	 * <p>
	 * Equivalent to {@link InputStream#close()}, except any exceptions will be
	 * ignored. This is typically used in finally blocks.
	 *
	 * @param input
	 *            the InputStream to close, may be null or already closed
	 */
	public static void close(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 关闭输出流。 Unconditionally close an <code>OutputStream</code>.
	 * <p>
	 * Equivalent to {@link OutputStream#close()}, except any exceptions will be
	 * ignored. This is typically used in finally blocks.
	 *
	 * @param output
	 *            the OutputStream to close, may be null or already closed
	 */
	public static void close(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	// read toByteArray
	// -----------------------------------------------------------------------
	/**
	 * 从输入流中获取字节数组。 Get the contents of an <code>InputStream</code> as a
	 * <code>byte[]</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @return the requested byte array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * 从Reader中获取字节数组。 Get the contents of a <code>Reader</code> as a
	 * <code>byte[]</code> using the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @return the requested byte array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static byte[] toByteArray(Reader input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * 从Reader中获取字节数组,使用指定的字符编码。 Get the contents of a <code>Reader</code> as a
	 * <code>byte[]</code> using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the requested byte array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static byte[] toByteArray(Reader input, String encoding) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output, encoding);
		return output.toByteArray();
	}

	// read char[]
	// -----------------------------------------------------------------------
	/**
	 * 从输入流中获取字符数组。 Get the contents of an <code>InputStream</code> as a
	 * character array using the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param is
	 *            the <code>InputStream</code> to read from
	 * @return the requested character array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static char[] toCharArray(InputStream is) throws IOException {
		CharArrayWriter output = new CharArrayWriter();
		copy(is, output);
		return output.toCharArray();
	}

	/**
	 * 从输入流中获取字符数组，使用指定的编码。 Get the contents of an <code>InputStream</code> as a
	 * character array using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param is
	 *            the <code>InputStream</code> to read from
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the requested character array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static char[] toCharArray(InputStream is, String encoding) throws IOException {
		CharArrayWriter output = new CharArrayWriter();
		copy(is, output, encoding);
		return output.toCharArray();
	}

	/**
	 * 从Reader中获取字符数组。 Get the contents of a <code>Reader</code> as a character
	 * array.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @return the requested character array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static char[] toCharArray(Reader input) throws IOException {
		CharArrayWriter sw = new CharArrayWriter();
		copy(input, sw);
		return sw.toCharArray();
	}

	// read toString
	// -----------------------------------------------------------------------
	/**
	 * 从输入流中获取字符串，使用默认的字符编码。 Get the contents of an <code>InputStream</code> as
	 * a String using the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @return the requested String
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	/**
	 * 从输入流中获取字符串，使用指定的字符编码。 Get the contents of an <code>InputStream</code> as
	 * a String using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the requested String
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String toString(InputStream input, String encoding) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	/**
	 * 从Reader中获取字符串。 Get the contents of a <code>Reader</code> as a String.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @return the requested String
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String toString(Reader input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	// readLines
	// -----------------------------------------------------------------------
	/**
	 * 从输入流中获取字符串列表，每行对应List中的一个实体，使用默认的字符编码。 Get the contents of an
	 * <code>InputStream</code> as a list of Strings, one entry per line, using
	 * the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 *
	 * @param input
	 *            the <code>InputStream</code> to read from, not null
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static List readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

	/**
	 * 从输入流中获取字符串列表，每行对应List中的一个实体，使用指定的字符编码。 Get the contents of an
	 * <code>InputStream</code> as a list of Strings, one entry per line, using
	 * the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 *
	 * @param input
	 *            the <code>InputStream</code> to read from, not null
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static List readLines(InputStream input, String encoding) throws IOException {
		if (encoding == null) {
			return readLines(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLines(reader);
		}
	}

	/**
	 * 从Reader中获取字符串列表，每行对应List中的一个实体。 Get the contents of a <code>Reader</code>
	 * as a list of Strings, one entry per line.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 *
	 * @param input
	 *            the <code>Reader</code> to read from, not null
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static List readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List list = new ArrayList();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

	// -----------------------------------------------------------------------
	/**
	 * 将字符串转换为输入流，按照字节流编码，使用默认的字符编码。 Convert the specified string to an input
	 * stream, encoded as bytes using the default character encoding of the
	 * platform.
	 *
	 * @param input
	 *            the string to convert
	 * @return an input stream
	 * @since Commons IO 1.1
	 */
	public static InputStream toInputStream(String input) {
		byte[] bytes = input.getBytes();
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * 将字符串转换为输入流，按照字节编码，使用指定的字符编码。 Convert the specified string to an input
	 * stream, encoded as bytes using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 *
	 * @param input
	 *            the string to convert
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws IOException
	 *             if the encoding is invalid
	 * @return an input stream
	 * @since Commons IO 1.1
	 */
	public static InputStream toInputStream(String input, String encoding) throws IOException {
		byte[] bytes = encoding != null ? input.getBytes(encoding) : input.getBytes();
		return new ByteArrayInputStream(bytes);
	}

	// write byte[]
	// -----------------------------------------------------------------------
	/**
	 * 将字节数组中的字节流写入输出流。 Writes bytes from a <code>byte[]</code> to an
	 * <code>OutputStream</code>.
	 * 
	 * @param data
	 *            the byte array to write, do not modify during output, null
	 *            ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(byte[] data, OutputStream output) throws IOException {
		if (data != null) {
			output.write(data);
		}
	}

	/**
	 * 将字节数组中的字节流写入Writer,使用默认的字符编码。 Writes bytes from a <code>byte[]</code> to
	 * chars on a <code>Writer</code> using the default character encoding of
	 * the platform.
	 * <p>
	 * This method uses {@link String#String(byte[])}.
	 * 
	 * @param data
	 *            the byte array to write, do not modify during output, null
	 *            ignored
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(byte[] data, Writer output) throws IOException {
		if (data != null) {
			output.write(new String(data));
		}
	}

	/**
	 * 将字节数组中的字节流写入Writer,使用指定的字符编码。 Writes bytes from a <code>byte[]</code> to
	 * chars on a <code>Writer</code> using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link String#String(byte[], String)}.
	 * 
	 * @param data
	 *            the byte array to write, do not modify during output, null
	 *            ignored
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(byte[] data, Writer output, String encoding) throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(new String(data, encoding));
			}
		}
	}

	// write char[]
	// -----------------------------------------------------------------------
	/**
	 * 将字符数组中的字符流写入Writer,使用默认的字符编码。 Writes chars from a <code>char[]</code> to
	 * a <code>Writer</code> using the default character encoding of the
	 * platform.
	 * 
	 * @param data
	 *            the char array to write, do not modify during output, null
	 *            ignored
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(char[] data, Writer output) throws IOException {
		if (data != null) {
			output.write(data);
		}
	}

	/**
	 * 将字符数组中的字符流写入输出流。 Writes chars from a <code>char[]</code> to bytes on an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method uses {@link String#String(char[])} and
	 * {@link String#getBytes()}.
	 * 
	 * @param data
	 *            the char array to write, do not modify during output, null
	 *            ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(char[] data, OutputStream output) throws IOException {
		if (data != null) {
			output.write(new String(data).getBytes());
		}
	}

	/**
	 * 将字符数组中的字符流写入输出流，使用指定的字符编码。 Writes chars from a <code>char[]</code> to
	 * bytes on an <code>OutputStream</code> using the specified character
	 * encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link String#String(char[])} and
	 * {@link String#getBytes(String)}.
	 * 
	 * @param data
	 *            the char array to write, do not modify during output, null
	 *            ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(char[] data, OutputStream output, String encoding) throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(new String(data).getBytes(encoding));
			}
		}
	}

	// write String
	// -----------------------------------------------------------------------
	/**
	 * 将字符串写入Writer。 Writes chars from a <code>String</code> to a
	 * <code>Writer</code>.
	 * 
	 * @param data
	 *            the <code>String</code> to write, null ignored
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(String data, Writer output) throws IOException {
		if (data != null) {
			output.write(data);
		}
	}

	/**
	 * 将String转换为字节数组后写入输出流，使用默认的字符编码。 Writes chars from a <code>String</code>
	 * to bytes on an <code>OutputStream</code> using the default character
	 * encoding of the platform.
	 * <p>
	 * This method uses {@link String#getBytes()}.
	 * 
	 * @param data
	 *            the <code>String</code> to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(String data, OutputStream output) throws IOException {
		if (data != null) {
			output.write(data.getBytes());
		}
	}

	/**
	 * 将String转换为字节数组后写入输出流，使用指定的字符编码。 Writes chars from a <code>String</code>
	 * to bytes on an <code>OutputStream</code> using the specified character
	 * encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link String#getBytes(String)}.
	 * 
	 * @param data
	 *            the <code>String</code> to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(String data, OutputStream output, String encoding) throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(data.getBytes(encoding));
			}
		}
	}

	// write StringBuffer
	// -----------------------------------------------------------------------
	/**
	 * 将StringBuffer写入Writer。 Writes chars from a <code>StringBuffer</code> to a
	 * <code>Writer</code>.
	 * 
	 * @param data
	 *            the <code>StringBuffer</code> to write, null ignored
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(StringBuffer data, Writer output) throws IOException {
		if (data != null) {
			output.write(data.toString());
		}
	}

	/**
	 * 将<code>StringBuffer</code>转换为字节数组写入输出流，使用默认的字符编码。 Writes chars from a
	 * <code>StringBuffer</code> to bytes on an <code>OutputStream</code> using
	 * the default character encoding of the platform.
	 * <p>
	 * This method uses {@link String#getBytes()}.
	 * 
	 * @param data
	 *            the <code>StringBuffer</code> to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(StringBuffer data, OutputStream output) throws IOException {
		if (data != null) {
			output.write(data.toString().getBytes());
		}
	}

	/**
	 * 将<code>StringBuffer</code>转换为字节数组写入输出流，使用指定的字符编码。 Writes chars from a
	 * <code>StringBuffer</code> to bytes on an <code>OutputStream</code> using
	 * the specified character encoding.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link String#getBytes(String)}.
	 * 
	 * @param data
	 *            the <code>StringBuffer</code> to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void write(StringBuffer data, OutputStream output, String encoding) throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(data.toString().getBytes(encoding));
			}
		}
	}

	// writeLines
	// -----------------------------------------------------------------------
	/**
	 * 将集合中的每个实体调用<code>toString()</code>的返回值写入输出流，按行写入，使用默认的字符编码。 Writes the
	 * <code>toString()</code> value of each item in a collection to an
	 * <code>OutputStream</code> line by line, using the default character
	 * encoding of the platform and the specified line ending.
	 *
	 * @param lines
	 *            the lines to write, null entries produce blank lines
	 * @param lineEnding
	 *            the line separator to use, null is system default
	 * @param output
	 *            the <code>OutputStream</code> to write to, not null, not
	 *            closed
	 * @throws NullPointerException
	 *             if the output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void writeLines(Collection lines, String lineEnding, OutputStream output) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				output.write(line.toString().getBytes());
			}
			output.write(lineEnding.getBytes());
		}
	}

	/**
	 * 将集合中的每个实体调用<code>toString()</code>的返回值写入输出流，按行写入，使用指定的字符编码。 Writes the
	 * <code>toString()</code> value of each item in a collection to an
	 * <code>OutputStream</code> line by line, using the specified character
	 * encoding and the specified line ending.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 *
	 * @param lines
	 *            the lines to write, null entries produce blank lines
	 * @param lineEnding
	 *            the line separator to use, null is system default
	 * @param output
	 *            the <code>OutputStream</code> to write to, not null, not
	 *            closed
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if the output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void writeLines(Collection lines, String lineEnding, OutputStream output, String encoding)
			throws IOException {
		if (encoding == null) {
			writeLines(lines, lineEnding, output);
		} else {
			if (lines == null) {
				return;
			}
			if (lineEnding == null) {
				lineEnding = LINE_SEPARATOR;
			}
			for (Iterator it = lines.iterator(); it.hasNext();) {
				Object line = it.next();
				if (line != null) {
					output.write(line.toString().getBytes(encoding));
				}
				output.write(lineEnding.getBytes(encoding));
			}
		}
	}

	/**
	 * 将集合中的每个实体调用<code>toString()</code>的返回值写入<code>Writer</code>，
	 * 按行写入，使用默认的字符编码。 Writes the <code>toString()</code> value of each item in
	 * a collection to a <code>Writer</code> line by line, using the specified
	 * line ending.
	 *
	 * @param lines
	 *            the lines to write, null entries produce blank lines
	 * @param lineEnding
	 *            the line separator to use, null is system default
	 * @param writer
	 *            the <code>Writer</code> to write to, not null, not closed
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void writeLines(Collection lines, String lineEnding, Writer writer) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				writer.write(line.toString());
			}
			writer.write(lineEnding);
		}
	}

	// copy from InputStream
	// -----------------------------------------------------------------------
	/**
	 * 从输入流复制字节流到输出流。 Copy bytes from an <code>InputStream</code> to an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * Large streams (over 2GB) will return a bytes copied value of
	 * <code>-1</code> after the copy has completed since the correct number of
	 * bytes cannot be returned as an int. For large streams use the
	 * <code>copyLarge(InputStream, OutputStream)</code> method.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @return the number of bytes copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ArithmeticException
	 *             if the byte count is too large
	 * @since Commons IO 1.1
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * 将大数据量(超过2GB)的输入流复制到输出流。 Copy bytes from a large (over 2GB)
	 * <code>InputStream</code> to an <code>OutputStream</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @return the number of bytes copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.3
	 */
	public static long copyLarge(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * 将输入流复制到Writer，使用默认的字符编码。 Copy bytes from an <code>InputStream</code> to
	 * chars on a <code>Writer</code> using the default character encoding of
	 * the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * This method uses {@link InputStreamReader}.
	 *
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void copy(InputStream input, Writer output) throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	/**
	 * 将输入流复制到Writer，使用指定的字符编码。 Copy bytes from an <code>InputStream</code> to
	 * chars on a <code>Writer</code> using the specified character encoding.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link InputStreamReader}.
	 *
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void copy(InputStream input, Writer output, String encoding) throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	// copy from Reader
	// -----------------------------------------------------------------------
	/**
	 * 将Reader中的字符流复制到Writer。 Copy chars from a <code>Reader</code> to a
	 * <code>Writer</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 * Large streams (over 2GB) will return a chars copied value of
	 * <code>-1</code> after the copy has completed since the correct number of
	 * chars cannot be returned as an int. For large streams use the
	 * <code>copyLarge(Reader, Writer)</code> method.
	 *
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @return the number of characters copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ArithmeticException
	 *             if the character count is too large
	 * @since Commons IO 1.1
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * 将大数据量(超过 2GB)的<code>Reader</code>中的字符数组复制到Writer。 Copy chars from a large
	 * (over 2GB) <code>Reader</code> to a <code>Writer</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 *
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @return the number of characters copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.3
	 */
	public static long copyLarge(Reader input, Writer output) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * 将Reader中的字节数组复制到输出流，使用默认的字符编码。 Copy chars from a <code>Reader</code> to
	 * bytes on an <code>OutputStream</code> using the default character
	 * encoding of the platform, and calling flush.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 * Due to the implementation of OutputStreamWriter, this method performs a
	 * flush.
	 * <p>
	 * This method uses {@link OutputStreamWriter}.
	 *
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void copy(Reader input, OutputStream output) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(output);
		copy(input, out);
		// XXX Unless anyone is planning on rewriting OutputStreamWriter, we
		// have to flush here.
		out.flush();
	}

	/**
	 * 将Reader中的字节数组复制到输出流，使用指定的字符编码。 Copy chars from a <code>Reader</code> to
	 * bytes on an <code>OutputStream</code> using the specified character
	 * encoding, and calling flush.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 * Character encoding names can be found at
	 * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * Due to the implementation of OutputStreamWriter, this method performs a
	 * flush.
	 * <p>
	 * This method uses {@link OutputStreamWriter}.
	 *
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			OutputStreamWriter out = new OutputStreamWriter(output, encoding);
			copy(input, out);
			// XXX Unless anyone is planning on rewriting OutputStreamWriter,
			// we have to flush here.
			out.flush();
		}
	}
	// ----------------------------------------------------------------
	// byte[] -> OutputStream
	// ----------------------------------------------------------------

	/**
	 * 将字节数组中的字节流复制到输出流，使用默认的字符编码。 Copy bytes from a <code>byte[]</code> to an
	 * <code>OutputStream</code>.
	 * 
	 * @param input
	 *            the byte array to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws IOException
	 *             In case of an I/O problem
	 */
	public static void copy(byte[] input, OutputStream output) throws IOException {
		output.write(input);
	}

	// ----------------------------------------------------------------
	// byte[] -> Writer
	// ----------------------------------------------------------------
	/**
	 * 将字节数组转换为字符流，复制到Writer，使用默认的字符编码。 Copy and convert bytes from a
	 * <code>byte[]</code> to chars on a <code>Writer</code>. The platform's
	 * default encoding is used for the byte-to-char conversion.
	 * 
	 * @param input
	 *            the byte array to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws IOException
	 *             In case of an I/O problem
	 */
	public static void copy(byte[] input, Writer output) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(input);
		copy(in, output);
	}

	/**
	 * 将字节数组转换为字符流，复制到Writer，使用指定的字符编码。 Copy and convert bytes from a
	 * <code>byte[]</code> to chars on a <code>Writer</code>, using the
	 * specified encoding.
	 * 
	 * @param input
	 *            the byte array to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @param encoding
	 *            The name of a supported character encoding. See the
	 *            <a href="http://www.iana.org/assignments/character-sets">IANA
	 *            Charset Registry</a> for a list of valid encoding types.
	 * @throws IOException
	 *             In case of an I/O problem
	 */
	public static void copy(byte[] input, Writer output, String encoding) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(input);
		copy(in, output, encoding);
	}

	// ----------------------------------------------------------------
	// String -> OutputStream
	// ----------------------------------------------------------------
	/**
	 * 从字符串转换的字节数组中得到序列化字符流，复制到输出流。 Serialize chars from a <code>String</code>
	 * to bytes on an <code>OutputStream</code>, and flush the
	 * <code>OutputStream</code>.
	 * 
	 * @param input
	 *            the <code>String</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws IOException
	 *             In case of an I/O problem
	 */
	public static void copy(String input, OutputStream output) throws IOException {
		StringReader in = new StringReader(input);
		OutputStreamWriter out = new OutputStreamWriter(output);
		copy(in, out);
		// XXX Unless anyone is planning on rewriting OutputStreamWriter, we
		// have to flush here.
		out.flush();
	}
	// ----------------------------------------------------------------
	// String -> Writer
	// ----------------------------------------------------------------

	/**
	 * 从字符串中复制字符流到Writer。 Copy chars from a <code>String</code> to a
	 * <code>Writer</code>.
	 * 
	 * @param input
	 *            the <code>String</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws IOException
	 *             In case of an I/O problem
	 */
	public static void copy(String input, Writer output) throws IOException {
		output.write(input);
	}

	// content equals
	// -----------------------------------------------------------------------
	/**
	 * 比较两个输入流的内容是否相等。 Compare the contents of two Streams to determine if they
	 * are equal or not.
	 * <p>
	 * This method buffers the input internally using
	 * <code>BufferedInputStream</code> if they are not already buffered.
	 *
	 * @param input1
	 *            the first stream
	 * @param input2
	 *            the second stream
	 * @return true if the content of the streams are equal or they both don't
	 *         exist, false otherwise
	 * @throws NullPointerException
	 *             if either input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return (ch2 == -1);
	}

	/**
	 * 比较两个Reader的内容是否相等。 Compare the contents of two Readers to determine if
	 * they are equal or not.
	 * <p>
	 * This method buffers the input internally using
	 * <code>BufferedReader</code> if they are not already buffered.
	 *
	 * @param input1
	 *            the first reader
	 * @param input2
	 *            the second reader
	 * @return true if the content of the readers are equal or they both don't
	 *         exist, false otherwise
	 * @throws NullPointerException
	 *             if either input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static boolean contentEquals(Reader input1, Reader input2) throws IOException {
		if (!(input1 instanceof BufferedReader)) {
			input1 = new BufferedReader(input1);
		}
		if (!(input2 instanceof BufferedReader)) {
			input2 = new BufferedReader(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return (ch2 == -1);
	}

}
