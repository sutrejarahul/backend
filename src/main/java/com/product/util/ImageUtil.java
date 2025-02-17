package com.product.util;

import java.util.Base64;

public class ImageUtil {

    public static String encodeImage(byte[] imageData) {
        return (imageData != null) ? Base64.getEncoder().encodeToString(imageData) : null;
    }
}
