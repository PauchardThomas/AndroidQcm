package com.iia.cdsm.qcm.Security;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Created by Thom' on 11/05/2016.
 */
public class Hash {
    /**
     * Hash SHA 256 text.
     * @param content text to hash
     * @return text hashed
     */
    public static String hashContent(String content) {

        final String hashed = Hashing.sha256()
                .hashString(content, Charsets.UTF_8)
                .toString();

        return hashed;
    }
}
