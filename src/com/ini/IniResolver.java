package com.ini;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

//
// Ini解析类
//
//@REMARK
// 1.INI文件不允许包含BOM头
// 2.保存时会自动添加自校验码，加载时会自动校验
//
public class IniResolver {
	public boolean load(byte[] content) {
		try {
			int off = 0;
			int len = content.length;

			md5Check = null;
			if (content[0] == '#') {
				MessageDigest md5Digest = MessageDigest.getInstance("MD5");
				md5Digest.update(content, 35, content.length - 35);

				md5Check = new String(content, 1, 32, ENCODING);
				String md5String = Miscellaneous.encodeHex(md5Digest.digest());
				if (!md5Check.equalsIgnoreCase(md5String)) {
					return false;
				}

				off += 35;
				len -= off;
			}

			ByteArrayInputStream is = new ByteArrayInputStream(content, off, len);
			return load(new InputStreamReader(is, ENCODING));
		} catch (Exception e) {
			return false;
		}
	}

	public boolean load(File file) {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);

			int len = is.available();
			byte[] content = new byte[len];
			is.read(content);

			return load(content);
		} catch (Exception e) {
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean load(Reader reader) {
		try {
			clear();

			String line = null;
			Section section = null;
			BufferedReader is = new BufferedReader(reader);

			while ((line = is.readLine()) != null) {
				if (line.length() == 0 || line.matches("\\s")) {
					continue;
				}

				switch (line.charAt(0)) {
				// comments
				case ';':
				case '#':
					break;

				// section
				case '[':
					if (line.length() <= 2 || line.charAt(line.length() - 1) != ']') {
						throw new Exception("invalid section name");
					}

					String sectionName = line.substring(1, line.length() - 1);
					section = new Section();

					sectionList.add(sectionName);
					sectionMap.put(sectionName, section);
					break;

				// key-value
				default:
					if (section == null) {
						throw new Exception("not found section name");
					}

					int index = line.indexOf('=');
					if (index == -1) {
						throw new Exception("invalid key-value format");
					}

					String key = line.substring(0, index);
					String value = line.substring(index + 1, line.length());

					section.keyList.add(key);
					section.keyValueMap.put(key, value);
					break;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(File file, boolean check) {
		FileOutputStream fos = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(bos, ENCODING);

			if (!save(osw)) {
				return false;
			}

			byte[] content = bos.toByteArray();
			fos = new FileOutputStream(file);

			if (check) {
				MessageDigest md5Digest = MessageDigest.getInstance("MD5");
				md5Digest.update(content);
				md5Check = Miscellaneous.encodeHex(md5Digest.digest());

				fos.write('#');
				fos.write(md5Check.getBytes(ENCODING));
				fos.write('\r');
				fos.write('\n');
			}

			fos.write(content);
			fos.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
			}
		}
	}

	private boolean save(Writer writer) {
		try {
			BufferedWriter os = new BufferedWriter(writer);

			Iterator<String> itSection = sectionList.iterator();
			while (itSection.hasNext()) {
				String sectionName = itSection.next();
				os.write("[" + sectionName + "]");
				os.newLine();

				Section section = sectionMap.get(sectionName);
				Iterator<String> itKey = section.keyList.iterator();
				while (itKey.hasNext()) {
					String key = itKey.next();
					String value = section.keyValueMap.get(key);

					os.write(key + "=" + value);
					os.newLine();
				}

				os.newLine();
			}

			os.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEqual(IniResolver other) {
		if (other != null && !Miscellaneous.isEmpty(other.md5Check) && !Miscellaneous.isEmpty(md5Check)
				&& md5Check.equalsIgnoreCase(other.md5Check)) {
			return true;
		}
		return false;
	}

	public String getValue(String sectionName, String keyName) {
		Section section = sectionMap.get(sectionName);
		if (section != null) {
			return section.keyValueMap.get(keyName);
		}
		return null;
	}

	public void setValue(String sectionName, String keyName, String value) {
		Section section = sectionMap.get(sectionName);
		if (section == null) {
			sectionList.add(sectionName);

			section = new Section();
			sectionMap.put(sectionName, section);
		}

		if (section.keyValueMap.get(keyName) == null) {
			section.keyList.add(keyName);
		}

		section.keyValueMap.put(keyName, value);
	}

	public final Collection<String> getAllSection() {
		return sectionList;
	}

	public final Collection<String> getAllKey(String sectionName) {
		Section section = sectionMap.get(sectionName);
		if (section != null) {
			return section.keyList;
		}
		return null;
	}

	public void clear() {
		sectionList.clear();
		sectionMap.clear();
	}

	public static class Section {
		public Collection<String> keyList = new LinkedList<String>();
		public Map<String, String> keyValueMap = new HashMap<String, String>();

		public Map<String, String> getKeyValueMap() {
			return keyValueMap;
		}

	}

	public Map<String, Section> getSectionMap() {
		return sectionMap;
	}

	private String md5Check;
	private Collection<String> sectionList = new LinkedList<String>();
	private Map<String, Section> sectionMap = new HashMap<String, Section>();

	private static final String ENCODING = "utf-8";
}
