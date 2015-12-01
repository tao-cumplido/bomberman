package de.hsh.project.bomberman.game.settings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taocu on 13.11.2015.
 */


public abstract class Settings {


    Map<SettingsTyp, Integer> basicSetting;
    Map<SettingsTyp, Integer> player1;
    Map<SettingsTyp, Integer> player2;
    Map<SettingsTyp, Integer> player3;
    Map<SettingsTyp, Integer> player4;

    String tempText;


    public Settings() {
        player1 = new HashMap<>();
        player2 = new HashMap<>();
        player3 = new HashMap<>();
        player4 = new HashMap<>();
        basicSetting = new HashMap<>();
        tempText = "";
        read("/settings.txt");


    }

    private void read(String filePath) {

        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String[] mark;
                while (!(lineTxt = bufferedReader.readLine()).equals("settings.conf")) {
                    if (!lineTxt.equals("")) {
                        mark = lineTxt.trim().split("=");
                        tempText += mark[0] + "=";
                        if (mark[0].equals("pa")) {
                            basicSetting.put(SettingsTyp.PLAYER1, Integer.valueOf(mark[1]));
                            for (int i = 0; i < 6; i++) {
                                lineTxt = bufferedReader.readLine();
                                addPlayerSetting(player1, lineTxt);
                            }
                        } else if (mark[0].equals("pb")) {
                            basicSetting.put(SettingsTyp.PLAYER2, Integer.valueOf(mark[1]));
                            for (int i = 0; i < 6; i++) {
                                lineTxt = bufferedReader.readLine();
                                addPlayerSetting(player2, lineTxt);
                            }
                        } else if (mark[0].equals("pc")) {
                            basicSetting.put(SettingsTyp.PLAYER3, Integer.valueOf(mark[1]));
                            for (int i = 0; i < 6; i++) {
                                lineTxt = bufferedReader.readLine();
                                addPlayerSetting(player3, lineTxt);
                            }
                        } else if (mark[0].equals("pd")) {
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
            } else {
                System.out.println("not found");
            }
        } catch (Exception e) {
            System.out.println("read error");
            e.printStackTrace();
        }
    }

    private void addPlayerSetting(Map<SettingsTyp, Integer> map, String lineText) {
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

    private void writePlayerSetting(Map<SettingsTyp, Integer> map, String text) {

        if (text.contains("keyup")) {
            tempText += text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_UP).intValue());
        } else if (text.contains("keydown")) {
            tempText += text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_DOWN).intValue());
        } else if (text.contains("keyleft")) {
            tempText += text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_LEFT).intValue());
        } else if (text.contains("keyright")) {
            tempText += text + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_RIGHT).intValue());
        } else if (text.contains("bomb")) {
            tempText += text + "=" + String.valueOf(map.get(SettingsTyp.SETTINGS_BOMB).intValue());
        } else if (text.contains("control")) {
            tempText += text + "=" + String.valueOf(map.get(SettingsTyp.SETTING_REMOTECONTROL).intValue());
        }
        tempText += "\r\n";

    }

    public void write() {

        try {
            String encoding = "GBK";
            File file = new File("/settings.txt");
            if (file.isFile() && file.exists()) {
                OutputStreamWriter writer = new OutputStreamWriter(
                        new FileOutputStream(file), encoding);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                tempText += "settings.conf";
                String[] mark = tempText.trim().split("=");
                tempText = "";
                for (int i = 0; i < mark.length; i++) {
                    if (mark[i].equals("pa")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER1).intValue());
                        tempText += "\r\n";
                        for (int j = i + 1; j <= i + 6; j++) {
                            writePlayerSetting(player1, mark[j]);
                        }
                        i = i + 6;
                    } else if (mark[i].equals("pb")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER2).intValue());
                        tempText += "\r\n";
                        for (int j = i + 1; j <= i + 6; j++) {
                            writePlayerSetting(player2, mark[j]);
                        }
                        i = i + 6;
                    } else if (mark[i].equals("pc")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER3).intValue());
                        tempText += "\r\n";
                        for (int j = i + 1; j <= i + 6; j++) {
                            writePlayerSetting(player3, mark[j]);
                        }
                        i = i + 6;
                    } else if (mark[i].equals("pd")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER4).intValue());
                        tempText += "\r\n";
                        for (int j = i + 1; j <= i + 6; j++) {
                            writePlayerSetting(player4, mark[j]);
                        }
                        i = i + 6;
                    } else if (mark[i].equals("life")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.LIFE).intValue());
                    } else if (mark[i].equals("time")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.TIME).intValue());
                    } else if (mark[i].equals("level")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.LEVEL).intValue());
                    } else if (mark[i].equals("board")) {
                        tempText += mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.BOARD).intValue());
                    } else {
                        tempText += "\r\n";
                        tempText += mark[i];
                    }
                    tempText += "\r\n";
                }
                bufferedWriter.write(tempText);
                bufferedWriter.close();
                writer.close();
            } else {
                System.out.println("not found");
            }
        } catch (Exception e) {
            System.out.println("write error");
            e.printStackTrace();
        }

    }

    public Map<SettingsTyp, Integer> getBasicSetting() {
        return basicSetting;
    }

    public void setBasicSetting(Map<SettingsTyp, Integer> basicSetting) {
        this.basicSetting = basicSetting;
    }

    public Map<SettingsTyp, Integer> getPlayer4() {
        return player4;
    }

    public void setPlayer4(Map<SettingsTyp, Integer> player4) {
        this.player4 = player4;
    }

    public Map<SettingsTyp, Integer> getPlayer3() {
        return player3;
    }

    public void setPlayer3(Map<SettingsTyp, Integer> player3) {
        this.player3 = player3;
    }

    public Map<SettingsTyp, Integer> getPlayer2() {
        return player2;
    }

    public void setPlayer2(Map<SettingsTyp, Integer> player2) {
        this.player2 = player2;
    }

    public Map<SettingsTyp, Integer> getPlayer1() {
        return player1;
    }

    public void setPlayer1(Map<SettingsTyp, Integer> player1) {
        this.player1 = player1;
    }


}
