/*
 * Copyright © 2023 Chris Egerton (fearthecellos@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.c0urante.joplin;

import io.github.c0urante.joplin.internal.Validation;

import java.awt.Color;
import java.nio.ByteBuffer;

public class Xyb implements HueColor {
    public static final int SIXTEEN_ONES = 65535;

    private final float x;
    private final float y;
    private final int b;

    public Xyb(Color color, int brightness) {
        this(
                color.getRed() << 8,
                color.getGreen() << 8,
                color.getBlue() << 8,
                brightness
        );
    }

    // Brightness is between 0 and 254.
    public Xyb(int red, int green, int blue, int brightness) {
        // TODO(todd)
//        Validation.x(red);
//        Validation.green(green);
//        Validation.blue(blue);
        float[] xy = rgbToXy(red, green, blue);

        this.x = xy[0];
        this.y = xy[1];
        this.b = brightness;
    }

    @Override
    public void serializeTo(ByteBuffer byteBuffer) {
        int x = (int) (this.x * SIXTEEN_ONES);
        int y = (int) (this.y * SIXTEEN_ONES);
        int brightness = (int) (1.0 * this.b * SIXTEEN_ONES / 256);

        byteBuffer.put((byte) ((x >> 8) & 0xFF));
        byteBuffer.put((byte) (x & 0xFF));
        byteBuffer.put((byte) ((y >> 8) & 0xFF));
        byteBuffer.put((byte) (y & 0xFF));
        byteBuffer.put((byte) ((brightness >> 8) & 0xFF));
        byteBuffer.put((byte) (brightness & 0xFF));
    }

    // TODO: How to do this? This is generated from Deepseek AI
    public static float[] rgbToXy(int red, int green, int blue) {
        // Normalize RGB values to 0..1
        float r = red / 255.0f;
        float g = green / 255.0f;
        float b = blue / 255.0f;

        // Apply gamma correction
        r = (r > 0.04045f) ? (float) Math.pow((r + 0.055f) / (1.0f + 0.055f), 2.4f) : (r / 12.92f);
        g = (g > 0.04045f) ? (float) Math.pow((g + 0.055f) / (1.0f + 0.055f), 2.4f) : (g / 12.92f);
        b = (b > 0.04045f) ? (float) Math.pow((b + 0.055f) / (1.0f + 0.055f), 2.4f) : (b / 12.92f);

        // Convert to XYZ using Wide RGB D65 conversion
        float X = r * 0.664511f + g * 0.154324f + b * 0.162028f;
        float Y = r * 0.283881f + g * 0.668433f + b * 0.047685f;
        float Z = r * 0.000088f + g * 0.072310f + b * 0.986039f;

        // Calculate xy from XYZ
        float x = X / (X + Y + Z);
        float y = Y / (X + Y + Z);

        // TODO(todd) remove?
//        // Calculate brightness (Philips Hue uses 0-254)
//        float brightness = Y * 254f;
//        brightness = Math.min(254, Math.max(0, brightness));

        return new float[]{x, y};
    }

}
