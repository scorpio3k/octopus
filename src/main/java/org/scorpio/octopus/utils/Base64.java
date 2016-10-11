package org.scorpio.octopus.utils;

import java.io.*;
import java.util.zip.*;

/**
 * Base64加解密工具集。
 * 
 * <p>
 * This class provides encode/decode for RFC 2045 Base64 as defined by RFC 2045.
 * N. Freed and N. Borenstein. RFC 2045: Multipurpose Internet Mail Extensions
 * (MIME) Part One: Format of Internet Message Bodies. Reference 1996 Available
 * at: http://www.ietf.org/rfc/rfc2045.txt This class is used by XML Schema
 * binary format validation This implementation does not encode/decode streaming
 * data. You need the data that you will encode/decode already on a byte arrray.
 * <p>
 */
public final class Base64 {
	static private final int BASELENGTH = 255;
	static private final int LOOKUPLENGTH = 64;
	static private final int TWENTYFOURBITGROUP = 24;
	static private final int EIGHTBIT = 8;
	static private final int SIXTEENBIT = 16;
	static private final int FOURBYTE = 4;
	static private final int SIGN = -128;
	static private final byte PAD = (byte) '=';
	static private final boolean fDebug = false;
	static private byte[] base64Alphabet = new byte[BASELENGTH];
	static private byte[] lookUpBase64Alphabet = new byte[LOOKUPLENGTH];

	static {

		for (int i = 0; i < BASELENGTH; i++) {
			base64Alphabet[i] = -1;
		}
		for (int i = 'Z'; i >= 'A'; i--) {
			base64Alphabet[i] = (byte) (i - 'A');
		}
		for (int i = 'z'; i >= 'a'; i--) {
			base64Alphabet[i] = (byte) (i - 'a' + 26);
		}

		for (int i = '9'; i >= '0'; i--) {
			base64Alphabet[i] = (byte) (i - '0' + 52);
		}

		base64Alphabet['+'] = 62;
		base64Alphabet['/'] = 63;

		for (int i = 0; i <= 25; i++) {
			lookUpBase64Alphabet[i] = (byte) ('A' + i);

		}
		for (int i = 26, j = 0; i <= 51; i++, j++) {
			lookUpBase64Alphabet[i] = (byte) ('a' + j);

		}
		for (int i = 52, j = 0; i <= 61; i++, j++) {
			lookUpBase64Alphabet[i] = (byte) ('0' + j);
		}
		lookUpBase64Alphabet[62] = (byte) '+';
		lookUpBase64Alphabet[63] = (byte) '/';

	}

	protected static boolean isWhiteSpace(byte octect) {
		return (octect == 0x20 || octect == 0xd || octect == 0xa || octect == 0x9);
	}

	protected static boolean isPad(byte octect) {
		return (octect == PAD);
	}

	protected static boolean isData(byte octect) {
		return (base64Alphabet[octect] != -1);
	}

	/**
	 * 判断字符串是否经过加密。
	 * 
	 * @param isValidString
	 *            需要验证的字符串
	 * @return
	 */
	public static boolean isBase64(String isValidString) {
		if (isValidString == null) {
			return false;
		}
		return (isArrayByteBase64(isValidString.getBytes()));
	}

	/**
	 * 判断octect是否是Base64加密字节
	 * 
	 * @param octect
	 *            待验证的字节
	 * @return
	 */
	public static boolean isBase64(byte octect) {
		return (isWhiteSpace(octect) || isPad(octect) || isData(octect));
	}

	/**
	 * 删除空白符。 remove WhiteSpace from MIME containing encoded Base64 data. e.g. "
	 * 
	 * @param data
	 *            待处理的已编码数据
	 * @return
	 */
	public static synchronized byte[] removeWhiteSpace(byte[] data) {
		if (data == null) {
			return null;
		}

		int newSize = 0;
		int len = data.length;
		int i = 0;
		for (; i < len; i++) {
			if (!isWhiteSpace(data[i])) {
				newSize++;
			}
		}

		if (newSize == len) {
			return data; // return input array since no whiteSpace
		}

		byte[] arrayWithoutSpaces = new byte[newSize]; // Allocate new array
														// without whiteSpace

		int j = 0;
		for (i = 0; i < len; i++) {
			if (isWhiteSpace(data[i])) {
				continue;
			} else {
				arrayWithoutSpaces[j++] = data[i]; // copy non-WhiteSpace
			}
		}
		return arrayWithoutSpaces;

	}

	/**
	 * 判断是否是经过加密的字节数组。
	 * 
	 * @param arrayOctect
	 *            待验证的字节数组
	 * @return
	 */
	public static synchronized boolean isArrayByteBase64(byte[] arrayOctect) {
		return (getDecodedDataLength(arrayOctect) >= 0);
	}

	/**
	 * Encodes hex octets into Base64
	 * 
	 * @param binaryData
	 *            Array containing binaryData
	 * @return Encoded Base64 array
	 */
	public static synchronized byte[] encode(byte[] binaryData) {
		if (binaryData == null) {
			return null;
		}

		int lengthDataBits = binaryData.length * EIGHTBIT;
		int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
		int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
		byte encodedData[] = null;

		if (fewerThan24bits != 0) // data not divisible by 24 bit
		{
			encodedData = new byte[(numberTriplets + 1) * 4];
		} else
		// 16 or 8 bit
		{
			encodedData = new byte[numberTriplets * 4];

		}
		byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

		int encodedIndex = 0;
		int dataIndex = 0;
		int i = 0;
		if (fDebug) {
			System.out.println("number of triplets = " + numberTriplets);
		}
		for (i = 0; i < numberTriplets; i++) {

			dataIndex = i * 3;
			b1 = binaryData[dataIndex];
			b2 = binaryData[dataIndex + 1];
			b3 = binaryData[dataIndex + 2];

			if (fDebug) {
				System.out.println("b1= " + b1 + ", b2= " + b2 + ", b3= " + b3);
			}

			l = (byte) (b2 & 0x0f);
			k = (byte) (b1 & 0x03);

			encodedIndex = i * 4;
			byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);

			byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
			byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

			encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
			if (fDebug) {
				System.out.println("val2 = " + val2);
				System.out.println("k4 = " + (k << 4));
				System.out.println("vak = " + (val2 | (k << 4)));
			}

			encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
			encodedData[encodedIndex + 2] = lookUpBase64Alphabet[(l << 2) | val3];
			encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3f];
		}

		// form integral number of 6-bit groups
		dataIndex = i * 3;
		encodedIndex = i * 4;
		if (fewerThan24bits == EIGHTBIT) {
			b1 = binaryData[dataIndex];
			k = (byte) (b1 & 0x03);
			if (fDebug) {
				System.out.println("b1=" + b1);
				System.out.println("b1<<2 = " + (b1 >> 2));
			}
			byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
			encodedData[encodedIndex + 2] = PAD;
			encodedData[encodedIndex + 3] = PAD;
		} else if (fewerThan24bits == SIXTEENBIT) {

			b1 = binaryData[dataIndex];
			b2 = binaryData[dataIndex + 1];
			l = (byte) (b2 & 0x0f);
			k = (byte) (b1 & 0x03);

			byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

			encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
			encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
			encodedData[encodedIndex + 3] = PAD;
		}
		return encodedData;
	}

	/**
	 * Decodes Base64 data into octets
	 * 
	 * @param base64Data
	 *            Byte array containing Base64 data
	 * @return Array containind decoded data.
	 */
	public static synchronized byte[] decode(byte[] base64Data) {

		if (base64Data == null) {
			return null;
		}

		byte[] normalizedBase64Data = removeWhiteSpace(base64Data);

		if (normalizedBase64Data.length % FOURBYTE != 0) {
			return null; // should be divisible by four
		}

		int numberQuadruple = (normalizedBase64Data.length / FOURBYTE);

		if (numberQuadruple == 0) {
			return new byte[0];
		}

		byte decodedData[] = null;
		byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
		byte d1 = 0, d2 = 0, d3 = 0, d4 = 0;

		// Throw away anything not in normalizedBase64Data
		// Adjust size
		int i = 0;
		int encodedIndex = 0;
		int dataIndex = 0;
		decodedData = new byte[(numberQuadruple) * 3];

		for (; i < numberQuadruple - 1; i++) {

			if (!isData((d1 = normalizedBase64Data[dataIndex++])) || !isData((d2 = normalizedBase64Data[dataIndex++]))
					|| !isData((d3 = normalizedBase64Data[dataIndex++]))
					|| !isData((d4 = normalizedBase64Data[dataIndex++]))) {
				return null; // if found "no data" just return null
			}

			b1 = base64Alphabet[d1];
			b2 = base64Alphabet[d2];
			b3 = base64Alphabet[d3];
			b4 = base64Alphabet[d4];

			decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
			decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
			decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
		}

		if (!isData((d1 = normalizedBase64Data[dataIndex++])) || !isData((d2 = normalizedBase64Data[dataIndex++]))) {
			return null; // if found "no data" just return null
		}

		b1 = base64Alphabet[d1];
		b2 = base64Alphabet[d2];

		d3 = normalizedBase64Data[dataIndex++];
		d4 = normalizedBase64Data[dataIndex++];
		if (!isData((d3)) || !isData((d4))) { // Check if they are PAD
												// characters
			if (isPad(d3) && isPad(d4)) { // Two PAD e.g. 3c[Pad][Pad]
				if ((b2 & 0xf) != 0) // last 4 bits should be zero
				{
					return null;
				}
				byte[] tmp = new byte[i * 3 + 1];
				System.arraycopy(decodedData, 0, tmp, 0, i * 3);
				tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
				return tmp;
			} else if (!isPad(d3) && isPad(d4)) { // One PAD e.g. 3cQ[Pad]
				b3 = base64Alphabet[d3];
				if ((b3 & 0x3) != 0) // last 2 bits should be zero
				{
					return null;
				}
				byte[] tmp = new byte[i * 3 + 2];
				System.arraycopy(decodedData, 0, tmp, 0, i * 3);
				tmp[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
				tmp[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
				return tmp;
			} else {
				return null; // an error like "3c[Pad]r", "3cdX", "3cXd", "3cXX"
								// where X is non data
			}
		} else { // No PAD e.g 3cQl
			b3 = base64Alphabet[d3];
			b4 = base64Alphabet[d4];
			decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
			decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
			decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);

		}

		return decodedData;
	}

	/**
	 * returns length of decoded data given an array containing encoded data.
	 * 
	 * @param base64Data
	 *            加密的字节数组
	 * @return a -1 would be return if not
	 */
	static public synchronized int getDecodedDataLength(byte[] base64Data) {

		if (base64Data == null) {
			return -1;
		}

		if (base64Data.length == 0) {
			return 0;
		}

		// byte[] normalizedBase64Data = removeWhiteSpace( base64Data );//Remove
		// any whiteSpace
		byte[] decodedData = null;

		if ((decodedData = decode(base64Data)) == null) // decode could return a
														// null byte array
		{
			return -1;
		}

		return decodedData.length;
	}

	/**
	 * 将String编码
	 * 
	 * @param string
	 *            编码前的String
	 * @return 编码后的 String
	 */
	public static synchronized String getEncodeString(String string) {
		// byte[] bb = encode(string.getBytes());
		byte[] oldby = string.getBytes();
		byte[] newby = encode(oldby);
		String newStr = new String(newby);
		oldby = null;
		newby = null;
		return newStr;
		/*
		 * String s = ""; for(int i=0;i <bb.length&&bb[i]!=0;i++){ s +=
		 * (char)bb[i]; } return s;
		 */
	}

	/**
	 * 将byte数组编码
	 * 
	 * @param oldby
	 *            待编码的字节数组
	 * @return 编码后的String
	 */
	public static synchronized String getEncodeString(byte[] oldby) {
		// byte[] bb = encode(string.getBytes());
		byte[] newby = encode(oldby);
		String newStr = new String(newby);
		oldby = null;
		newby = null;
		return newStr;
		/*
		 * String s = ""; for(int i=0;i <bb.length&&bb[i]!=0;i++){ s +=
		 * (char)bb[i]; } return s;
		 */
	}

	/**
	 * 对 String 解码
	 * 
	 * @param string
	 *            编码后的String
	 * @return 解码后的String
	 */
	public static synchronized String getDecodeString(String string) {
		byte[] oldby = string.getBytes();
		byte[] newby = decode(oldby);
		String newStr = new String(newby);
		oldby = null;
		newby = null;
		// byte[] rr = decode(string.getBytes());
		return newStr;
		/*
		 * String s = ""; for(int i=0;i <rr.length&&rr[i]!=0;i++){ s +=
		 * (char)rr[i]; } return s;
		 */
	}

	/**
	 * 将已经编码的String解码
	 * 
	 * @param string
	 *            已经编码的String
	 * @return 解码后的byte数组
	 */
	public static synchronized byte[] getDecodeBytes(String string) {
		byte[] oldby = string.getBytes();
		byte[] newby = decode(oldby);
		oldby = null;
		// byte[] rr = decode(string.getBytes());
		return newby;
		/*
		 * String s = ""; for(int i=0;i <rr.length&&rr[i]!=0;i++){ s +=
		 * (char)rr[i]; } return s;
		 */
	}

	/*
	 * public static void main(String[] args){
	 * System.out.println(System.currentTimeMillis()); String time_t =
	 * String.valueOf(System.currentTimeMillis()).substring(0,10);
	 * System.out.println(time_t); System.out.println("skdkdk".indexOf("skp"));
	 * System.out.println(getEncodeString("101010003"));
	 * System.out.println(getDecodeString(
	 * "ZGVsZXRlIGZyb20gc3lzX3NlY19ub2RlcyB3aGVyZSBub2RlaWQ9JzEwMTAwMDAwMic7aW5zZXJ0IGludG8gc3lzX3NlY19ub2Rlcyhub2RlaWQsaXBhZGQsbmFtZSxhZGRyZXNzLHR5cGUscnNwb25zZXIsY29udGFjdCxod2luZm8sc3dpbmZvKSB2YWx1ZXMoJzEwMTAwMDAwMicsJzE5OC4xNjkuMS4xMTEnLCfO4tGnvvwnLCezyba8JywwLCfO4tGnvvwnLCfKws6v1LG74ScsJ2h3aW5mbycsJ3N3aW5mbycpAA=="
	 * )); System.out.println(getDecodeString(""));
	 * System.out.println(getDecodeString(
	 * "Q2IyQ0pWblJQUkxlMlRqdnV2Vm5JSnB4dkRzNHN0cTBjRHloNG00S3Vnaz0=")); byte[]
	 * b = getDecodeBytes(
	 * "bCtqSVlqb0czNEJLSUQrdTJ2OWtud1pLb0lSRVRRSHoyRVFzS0ordlRCUHBpTzBEN0wrbEx5S2hOSE1GUTQ2VWQzYnMwNmIyWDRCN2dscXQvakg3UnYra2ZlMXlNamtqQUY3T0JhelFabFpjNEFXbnkyTVFlRzU4cHJSR0dyUk9YcW5yOGhOOHpPaVlaR1VoazJiSG16MWVyWXlNbVdSMVBLaWR2MTNVdTE4PQ=="
	 * ); System.out.print(b.toString()); }
	 */

	/**
	 * zip方式压缩并Base64加密指定字符串
	 * 
	 * @param str
	 *            待处理字符串
	 * @return 处理后的字符串。传入null产生空指针异常。
	 * @throws java.io.IOException
	 */
	public String gZip(String str) throws java.io.IOException {
		String retStr = "";

		ByteArrayOutputStream bOs = new ByteArrayOutputStream();
		bOs.write(compress(str.getBytes()));

		// sun.misc.BASE64Encoder base64Encoder = new BASE64Encoder();
		// retStr = base64Encoder.encode(bOs.toByteArray());
		retStr = new String(Base64.encode(bOs.toByteArray()));

		return retStr;
	}

	/**
	 * Base64解密并解压缩指定字符串。
	 * 
	 * @param str
	 *            经过压缩的字符串
	 * @return 处理后的原始字符串。传入null产生空指针异常。
	 * @throws java.io.IOException
	 */
	public String gUnZip(String str) throws java.io.IOException {
		// sun.misc.BASE64Decoder base64Decoder = new BASE64Decoder();
		// byte[] byteArray = base64Decoder.decodeBuffer(str);
		byte[] byteArray = Base64.decode(str.getBytes());
		return new String(decompress(byteArray));
	}

	/**
	 * zip方式压缩指定字节数组
	 * 
	 * @param b
	 *            待处理的字节数组
	 * @return 经过压缩的字节数组
	 * @throws java.io.IOException
	 */
	public byte[] compress(byte[] b) throws java.io.IOException {

		ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(outBuffer);
		gzip.write(b);
		gzip.close();
		byte[] c = outBuffer.toByteArray();
		outBuffer.reset();
		return c;

	}

	/**
	 * zip方式解压缩指定字节数组
	 * 
	 * @param b
	 *            经过压缩的字节数组
	 * @return 解压后的字节数组
	 * @throws java.io.IOException
	 */
	public byte[] decompress(byte[] b) throws java.io.IOException {
		try {
			ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
			ByteArrayInputStream inBuffer = new ByteArrayInputStream(b);
			GZIPInputStream gunzip = new GZIPInputStream(inBuffer);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				outBuffer.write(buffer, 0, n);
			}
			return outBuffer.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public static void main(String[] args) {
		System.out.println(getEncodeString("adcdefg"));
	}
}