package io.github.c0urante.joplin;

import io.github.c0urante.joplin.HueEntertainmentClient;

import java.awt.*;
import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {

        HueEntertainmentClient client = createClient();
//        HueColor color1 = new Xyb(Color.GREEN, 255);
//        HueColor color2 = new Xyb(Color.RED, 255);
//        HueColor color1 = new Rgb(Color.GREEN);
        HueColor color2 = new Rgb(Color.RED);

        // Merry Christmas!
//        for (int i = 0; i < 100; i++) {
////            HueColor color1 = new Xyb(Color.GREEN, (int) (255 * Math.random()));
//            HueColor color2 = new Xyb(Color.RED, (int) (255 * Math.random()));
//            if (i % 2 == 0) {
//                client.sendColors(color1, color2, color1, color2, color1, color2);
//            } else {
//                client.sendColors(color2, color1, color2, color1, color2, color1);
//            }
//            Thread.sleep(100);
//        }

        for (int i = 0; i < 10000; i++) {
//            color1 = new Xyb(Color.GREEN, 255);
//            color2 = new Xyb(Color.RED, (int) (255 * ((i % 10) / 10.0)));
            color2 = new Xyb(Color.BLUE, (int) (255 * ((i % 20) / 20.0)));
            client.sendColors(color2, color2, color2, color2, color2, color2);
//            Thread.sleep(50);
            Thread.sleep(1000 / 25);
        }


        // Alternatively, if you just want to set N lights to a single fixed color
//        HueColor color3 = new Rgb(Color.BLUE);
//        client.sendColors(8, color3);

        // Or, if you want to set specific lights to specific colors
//        Light light1 = new Light(0, color1);
//        Light light3 = new Light(2, color2);
////        Light light5 = new Light(4, color3);
//        client.sendLights(light1, light3, light5);

        // Don't forget to clean up once you're finished
        client.close();
    }

    public static HueEntertainmentClient createClient() throws IOException, InterruptedException {
        // To get hue entertainment ID,
        // curl https://192.168.111.222/clip/v2/resource/entertainment_configuration --header "hue-application-key: 4K5R6S5WTtgtHb0dulx3ipnAJ5AryCxoB8P0dBtH" --insecure
        // Instantiate the client
        HueEntertainmentClient client = HueEntertainmentClient.builder()
                .host("192.168.111.222")
                .username("4K5R6S5WTtgtHb0dulx3ipnAJ5AryCxoB8P0dBtH ")
                .clientKey("CD240537CC422BF10CC613A3263BB92B")
                .entertainmentArea("9b76a94e-0af5-4ef8-8cd7-790502153231")
                .colorSpace(HueColor.COLOR_SPACE_XYB)
                .build();

        // Use the bridge REST API to turn on streaming
        // This method must be called before light colors can be set
        client.initializeStream();

        return client;
    }
}
