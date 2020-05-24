package com.igroupes.rtadmin.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

public class FileTypeUtils {
    private FileTypeUtils() {
    }

    private static final FileType[] BASE_IMAGE_FILE_TYPE = {FileType.JPEG, FileType.PNG, FileType.GIF};
    private static final int FLAG_LENGTH = 30;

    private static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    private static String getFileHead(InputStream in) {
        byte[] b = new byte[FLAG_LENGTH];
        try {
            in.read(b, 0, FLAG_LENGTH);
        } catch (IOException io) {
            IOUtils.closeQuietly(in);
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     *
     * @return 文件类型
     */
    public static FileType getType(InputStream in) {

        String fileHead = getFileHead(in);
        if (StringUtils.isBlank(fileHead)) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileType[] fileTypes = FileType.values();
        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    public static boolean isImage(InputStream in) throws IOException {
        if (!in.markSupported()) {
            throw new IOException("mark/reset not supported");
        }
        in.mark(FLAG_LENGTH);
        boolean contains = ArrayUtils.contains(BASE_IMAGE_FILE_TYPE, getType(in));
        in.reset();

        return contains;
    }


    enum FileType {
        JPEG("FFD8FF"),
        PNG("89504E47"),
        GIF("47494638"),
        TIFF("49492A00"),
        BMP("424D"),
        DWG("41433130"),
        PSD("38425053"),
        RTF("7B5C727466"),
        XML("3C3F786D6C"),
        HTML("68746D6C3E"),
        EML("44656C69766572792D646174653A"),
        DBX("CFAD12FEC5FD746F"),
        PST("2142444E"),
        XLS_DOC("D0CF11E0"),
        MDB("5374616E64617264204A"),
        WPD("FF575043"),
        EPS("252150532D41646F6265"),
        PDF("255044462D312E"),
        QDF("AC9EBD8F"),
        PWL("E3828596"),
        ZIP("504B0304"),
        RAR("52617221"),
        WAV("57415645"),
        AVI("41564920"),
        RAM("2E7261FD"),
        RM("2E524D46"),
        MPG("000001BA"),
        MOV("6D6F6F76"),
        ASF("3026B2758E66CF11"),
        MID("4D546864");
        private String value;

        FileType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}
