/*
 * Project Name: xinyunlian-ecom
 * File Name: RSAUtils.java
 * Class Name: RSAUtils
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rujianbin.common.web.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;

/**
 * RSA加解密
 */
public final class RSAUtils {

	private static final Logger		LOGGER		= LoggerFactory.getLogger(RSAUtils.class);

	/** 安全服务提供者 */
	private static final Provider	PROVIDER	= new BouncyCastleProvider();

	/** 密钥大小 */
	private static final int		KEY_SIZE	= 1024;

	private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

	public static final String PRIVATE_KEY_SESSION_ATTRIBUTE_NAME = "session_privateKey";

	/**
	 * 不可实例化
	 */
	private RSAUtils() {
	}

	/**
	 * 生成密钥对
	 * 
	 * @return 密钥对
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", PROVIDER);
			keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param publicKey
	 *            公钥
	 * @param data
	 *            数据
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 加密 (明文过长时分片加密)
	 * 
	 * @param publicKey
	 *            公钥
	 * @param text
	 *            字符串
	 * 
	 * @return Base64编码字符串
	 */
	public static String encrypt(PublicKey publicKey, String text) {
		byte[] data = crypt( text.getBytes(),true,publicKey);
		return data != null ? Base64.encodeBase64String(data) : null;
	}

	private static byte[] crypt(byte[] data,boolean encode,Key key){
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
			if(encode) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			}else{
				cipher.init(Cipher.DECRYPT_MODE, key);
			}
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;
			blockSize = data.length < blockSize ? data.length : blockSize;
			while (data.length - j * blockSize > 0) {
				if (j * blockSize + blockSize > data.length) {
					bout.write(cipher.doFinal(data, j * blockSize, data.length - j * blockSize));
				} else {
					bout.write(cipher.doFinal(data, j * blockSize, blockSize));
				}
				j++;
			}
			return bout.toByteArray();
		}catch (Exception e){
			LOGGER.error("",e);
		}
		return new byte[]{} ;
	}

	/**
	 * 解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            数据
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解密（分片解密）
	 * 
	 * @param privateKey
	 *            私钥
	 * @param text
	 *            Base64编码字符串
	 * @return 解密后的数据
	 */
	public static String decrypt(PrivateKey privateKey, String text) {
		byte[] data = crypt(Base64.decodeBase64(text),false,privateKey);
		return data != null ? new String(data) : null;
	}

}
