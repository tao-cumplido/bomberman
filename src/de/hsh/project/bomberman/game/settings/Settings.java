package de.hsh.project.bomberman.game.settings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by taocu on 13.11.2015.
 */


public class Settings {


    private static Map<SettingsTyp, Integer> basicSetting;
    private static Map<SettingsTyp, Integer> player1;
    private static Map<SettingsTyp, Integer> player2;
    private static Map<SettingsTyp, Integer> player3;
    private static Map<SettingsTyp, Integer> player4;
    private static String tempText;


    public Settings() {
        player1 = new HashMap<>();
        player2 = new HashMap<>();
        player3 = new HashMap<>();
        player4 = new HashMap<>();
        basicSetting = new HashMap<>();
        tempText = "";
        read("/settings.txt");


    }

    private static void read(String filePath) {

        try {
            InputStream read = Settings.class.getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(read));
            String lineTxt;
            String[] mark;
            while ((lineTxt = bufferedReader.readLine())!=null) {
                if (!lineTxt.equals("")) {
                    mark = lineTxt.trim().split("=");
                    tempText += mark[0] + "=";
                    if (mark[0].contains("1")) {
                        Settings.basicSetting.put(SettingsTyp.PLAYER1, Integer.valueOf(mark[1]));
                        for (int i = 0; i < 6; i++) {
                            lineTxt = bufferedReader.readLine();
                            addPlayerSetting(player1, lineTxt);
                        }
                    } else if (mark[0].contains("2")) {
                        basicSetting.put(SettingsTyp.PLAYER2, Integer.valueOf(mark[1]));
                        for (int i = 0; i < 6; i++) {
                            lineTxt = bufferedReader.readLine();
                            addPlayerSetting(player2, lineTxt);
                        }
                    } else if (mark[0].contains("3")) {
                        basicSetting.put(SettingsTyp.PLAYER3, Integer.valueOf(mark[1]));
                        for (int i = 0; i < 6; i++) {
                            lineTxt = bufferedReader.readLine();
                            addPlayerSetting(player3, lineTxt);
                        }
                    } else if (mark[0].contains("4")) {
                        basicSetting.put(SettingsTyp.PLAYER4, Integer.valueOf(mark[1]));
                        for (int i = 0; i < 6; i++) {
                            lineTxt = bufferedReader.readLine();
                            addPlayerSetting(player4, lineTxt);
                        }
                    } else if (mark[0].equals("life")) {
                        basicSetting.put(SettingsTyp.LIFE, Integer.valueOf(mark[1]));
                    } else if (mark[0].equals("time")) {
                        basicSetting.put(SettingsTyp.TIME, Integer.valueOf(mark[1]));
                    } else if (mark[0].equals("level")) {
                        basicSetting.put(SettingsTyp.LEVEL, Integer.valueOf(mark[1]));
                    } else if (mark[0].equals("board")) {
                        basicSetting.put(SettingsTyp.BOARD, Integer.valueOf(mark[1]));
                    }
                }
            }
            bufferedReader.close();
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void addPlayerSetting(Map<SettingsTyp, Integer> map, String lineText) {
        String[] mark;
        mark = lineText.trim().split("=");
        tempText += mark[0] + "=";
        if (mark[0].contains("keyup")) {
            map.put(SettingsTyp.DIRECTION_UP, Integer.valueOf(mark[1]));
        } else if (mark[0].contains("keydown")) {
            map.put(SettingsTyp.DIRECTION_DOWN, Integer.valueOf(mark[1]));
        } else if (mark[0].contains("keyleft")) {
            map.put(SettingsTyp.DIRECTION_LEFT, Integer.valueOf(mark[1]));
        } else if (mark[0].contains("keyright")) {
            map.put(SettingsTyp.DIRECTION_RIGHT, Integer.valueOf(mark[1]));
        } else if (mark[0].contains("bomb")) {
            map.put(SettingsTyp.SETTINGS_BOMB, Integer.valueOf(mark[1]));
        } else if (mark[0].contains("control")) {
            map.put(SettingsTyp.SETTING_REMOTECONTROL, Integer.valueOf(mark[1]));
        }


    }


    private static void writePlayerSetting(Map<SettingsTyp, Integer> map, String text) {
        if (text.contains("keyup")) {
            tempText = text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_UP).intValue());
        } else if (text.contains("keydown")) {
            tempText = text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_DOWN).intValue());
        } else if (text.contains("keyleft")) {
            tempText = text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_LEFT).intValue());
        } else if (text.contains("keyright")) {
            tempText = text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_RIGHT).intValue());
        } else if (text.contains("keybomb")) {
            tempText = text + "=" + String.valueOf(map.get(SettingsTyp.SETTINGS_BOMB).intValue());
        } else if (text.contains("keycontrol")) {
            tempText = text + "=" + String.valueOf(map.get(SettingsTyp.SETTING_REMOTECONTROL).intValue());
        }

    }

    protected static void write() {
        try {
            FileOutputStream f = new FileOutputStream(Settings.class.getResource("/settings.txt").getPath());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(f));
            String[] mark = tempText.trim().split("=");
            for (int i = 0; i < mark.length; i++) {
                if (mark[i].contains("1")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER1).intValue());
                    bufferedWriter.write(tempText);
                    bufferedWriter.newLine();
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player1, mark[j]);
                        bufferedWriter.write(tempText);
                        bufferedWriter.newLine();
                    }
                    i = i + 6;
                } else if (mark[i].contains("2")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER2).intValue());
                    bufferedWriter.write(tempText);
                    bufferedWriter.newLine();
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player2, mark[j]);
                        bufferedWriter.write(tempText);
                        bufferedWriter.newLine();
                    }
                    i = i + 6;
                } else if (mark[i].contains("3")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER3).intValue());
                    bufferedWriter.write(tempText);
                    bufferedWriter.newLine();
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player3, mark[j]);
                        bufferedWriter.write(tempText);
                        bufferedWriter.newLine();
                    }
                    i = i + 6;
                } else if (mark[i].contains("4")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER4).intValue());
                    bufferedWriter.write(tempText);
                    bufferedWriter.newLine();
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player4, mark[j]);
                        bufferedWriter.write(tempText);
                        bufferedWriter.newLine();
                    }
                    i = i + 6;
                } else if (mark[i].equals("life")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.LIFE).intValue());
                    bufferedWriter.write(tempText);
                } else if (mark[i].equals("time")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.TIME).intValue());
                    bufferedWriter.write(tempText);
                } else if (mark[i].equals("level")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.LEVEL).intValue());
                    bufferedWriter.write(tempText);
                } else if (mark[i].equals("board")) {
                    tempText = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.BOARD).intValue());
                    bufferedWriter.write(tempText);
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<SettingsTyp, Integer> getBasicSetting() {
        return basicSetting;
    }

    public static void setBasicSetting(Map<SettingsTyp, Integer> basicSetting) {
        Settings.basicSetting = basicSetting;
    }

    public static Map<SettingsTyp, Integer> getPlayer4() {
        return player4;
    }

    public static void setPlayer4(Map<SettingsTyp, Integer> player4) {
        Settings.player4 = player4;
    }

    public static Map<SettingsTyp, Integer> getPlayer3() {
        return player3;
    }

    public static void setPlayer3(Map<SettingsTyp, Integer> player3) {
        Settings.player3 = player3;
    }

    public static Map<SettingsTyp, Integer> getPlayer2() {
        return player2;
    }

    public static void setPlayer2(Map<SettingsTyp, Integer> player2) {
        Settings.player2 = player2;
    }

    public static Map<SettingsTyp, Integer> getPlayer1() {
        return player1;
    }

    public static void setPlayer1(Map<SettingsTyp, Integer> player1) {
        Settings.player1 = player1;
    }

}
