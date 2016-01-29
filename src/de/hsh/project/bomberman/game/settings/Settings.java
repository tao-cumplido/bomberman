package de.hsh.project.bomberman.game.settings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by taocu on 13.11.2015.
 */


public class Settings {


    private static Map<SettingsTyp, Integer> basicSetting=new HashMap<>();
    private static Map<SettingsTyp, Integer> player1= new HashMap<>();
    private static Map<SettingsTyp, Integer> player2=new HashMap<>();
    private static Map<SettingsTyp, Integer> player3=new HashMap<>();
    private static Map<SettingsTyp, Integer> player4=new HashMap<>();
    private static String tempText="";
    private static String text;
    private static boolean temp = false;

    private static void read() {
        String path = System.getProperty("user.dir");
        File file = new File(path + "/settings.txt");
        BufferedReader bufferedReader;

        try {
            if(file.exists()){
                bufferedReader = new BufferedReader(new FileReader(file));
            }else{
                InputStream read = Settings.class.getResourceAsStream("/settings.txt");
                bufferedReader = new BufferedReader(new InputStreamReader(read));
            }
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


    private static void writePlayerSetting(Map<SettingsTyp, Integer> map, String s) {
        if (s.contains("keyup")) {
            text = s + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_UP).intValue());
        } else if (s.contains("keydown")) {
            text = s + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_DOWN).intValue());
        } else if (s.contains("keyleft")) {
            text = s + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_LEFT).intValue());
        } else if (s.contains("keyright")) {
            text = s + "=" + String.valueOf(map.get(SettingsTyp.DIRECTION_RIGHT).intValue());
        } else if (s.contains("keybomb")) {
            text = s+ "=" + String.valueOf(map.get(SettingsTyp.SETTINGS_BOMB).intValue());
        } else if (s.contains("keycontrol")) {
            text = s + "=" + String.valueOf(map.get(SettingsTyp.SETTING_REMOTECONTROL).intValue());
        }

    }

    protected static void write() {
        try {
            String path = System.getProperty("user.dir");
            File file = new File(path + "/settings.txt");
          //  FileOutputStream f = new FileOutputStream(Settings.class.getResource("/settings.txt").getPath());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            String[] mark = tempText.trim().split("=");
            for (int i = 0; i < mark.length; i++) {
                if (mark[i].contains("1")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER1).intValue());
                   bufferedWriter.write(text);
                   bufferedWriter.newLine();
                    //text ="\n";
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player1, mark[j]);
                        bufferedWriter.write(text);
                        bufferedWriter.newLine();
                        //text +="\n";
                    }
                    i = i + 6;
                } else if (mark[i].contains("2")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER2).intValue());
                    bufferedWriter.write(text);
                    bufferedWriter.newLine();
                    //text +="\n";
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player2, mark[j]);
                        bufferedWriter.write(text);
                        bufferedWriter.newLine();
                        //text +="\n";
                    }
                    i = i + 6;
                } else if (mark[i].contains("3")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER3).intValue());
                    bufferedWriter.write(text);
                    bufferedWriter.newLine();
                    //text +="\n";
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player3, mark[j]);
                        bufferedWriter.write(text);
                        bufferedWriter.newLine();
                        //text +="\n";
                    }
                    i = i + 6;
                } else if (mark[i].contains("4")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.PLAYER4).intValue());
                    bufferedWriter.write(text);
                    bufferedWriter.newLine();
                    //text +="\n";
                    for (int j = i + 1; j <= i + 6; j++) {
                        writePlayerSetting(player4, mark[j]);
                        bufferedWriter.write(text);
                        bufferedWriter.newLine();
                       // text +="\n";
                    }
                    i = i + 6;
                } else if (mark[i].equals("life")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.LIFE).intValue());
                    bufferedWriter.write(text);
                } else if (mark[i].equals("time")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.TIME).intValue());
                    bufferedWriter.write(text);
                } else if (mark[i].equals("level")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.LEVEL).intValue());
                    bufferedWriter.write(text);
                } else if (mark[i].equals("board")) {
                    text = mark[i] + "=" + String.valueOf(basicSetting.get(SettingsTyp.BOARD).intValue());
                    bufferedWriter.write(text);
                }
                bufferedWriter.newLine();
               // text +="\n";
            }
           // bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<SettingsTyp, Integer> getBasicSetting() {
        checkRead();
        return basicSetting;
    }

    public static void setBasicSetting(Map<SettingsTyp, Integer> basicSetting) {
        checkRead();
        Settings.basicSetting = basicSetting;
    }

    public static Map<SettingsTyp, Integer> getPlayer4() {
        checkRead();
        return player4;
    }

    public static void setPlayer4(Map<SettingsTyp, Integer> player4) {
        checkRead();
        Settings.player4 = player4;
    }

    public static Map<SettingsTyp, Integer> getPlayer3() {
        checkRead();
        return player3;
    }

    public static void setPlayer3(Map<SettingsTyp, Integer> player3) {
        checkRead();
        Settings.player3 = player3;
    }

    public static Map<SettingsTyp, Integer> getPlayer2() {
        checkRead();
        return player2;
    }

    public static void setPlayer2(Map<SettingsTyp, Integer> player2) {
        checkRead();
        Settings.player2 = player2;
    }

    public static Map<SettingsTyp, Integer> getPlayer1() {
        checkRead();
        return player1;
    }

    public static void setPlayer1(Map<SettingsTyp, Integer> player1) {
        checkRead();
        Settings.player1 = player1;
    }

    private static void checkRead(){
        if(!temp) {
            read();
            temp = true;
        }
    }

}
