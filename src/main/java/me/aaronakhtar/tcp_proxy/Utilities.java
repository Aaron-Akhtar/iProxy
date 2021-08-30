package me.aaronakhtar.tcp_proxy;

public class Utilities {

    public enum Colour {
        RESET("\u001b[0m"),
        BRIGHT_BLACK("\u001b[30;1m"),
        BRIGHT_RED("\u001b[31;1m"),
        BRIGHT_YELLOW("\u001b[33;1m"),
        BRIGHT_BLUE("\u001b[34;1m"),
        BRIGHT_MAGENTA("\u001b[35;1m"),
        BRIGHT_CYAN("\u001b[36;1m"),
        BRIGHT_WHITE("\u001b[37;1m"),
        BLACK("\u001b[30m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        MAGENTA("\u001b[35m"),
        GREEN_BG("\u001b[42m"),
        RED_BG("\u001b[41;1m"),
        WHITE_BG("\u001b[47;1m"),
        MAGENTA_BG("\u001b[45;1m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[37m"),
        CLEAR("\033[H\033[2J"),
        NEW_LINE("\r\n");

        private String code;
        Colour(String code){
            this.code = code;
        }

        public String get(){
            return code;
        }

    }

}
