package top.gtb520.plugin.playernamecheck;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import top.gtb520.plugin.playernamecheck.commands.fly_Command;
import top.gtb520.plugin.playernamecheck.controller.command_redirect;
import top.gtb520.plugin.playernamecheck.controller.mute;
import top.gtb520.plugin.playernamecheck.event.*;
import top.gtb520.plugin.playernamecheck.unity.PlaceholderAPI;
import top.gtb520.plugin.playernamecheck.unity.YamlFile_Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static top.gtb520.plugin.playernamecheck.unity.unity.GetLoggerPlus;

public final class main extends JavaPlugin implements Listener {
    public static YamlFile_Utils Yaml;

    public static main instance;
    private static PluginDescriptionFile descriptionFile;

    public static PluginDescriptionFile getDescriptionFile() {
        return descriptionFile;
    }

    public static main getInstance() {
        return instance;
    }

    private File logDir;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onEnable() {
        // 插件开始启用

        // 初始化默认插件内容
        instance = this;
        descriptionFile = getDescription();
        GetLoggerPlus("&e&l\n" +
                " _                     _____          ______ _             _       \n" +
                "| |                   |_   _|         | ___ \\ |           (_)      \n" +
                "| |     ___  _ __   __ _| | __ _  ___ | |_/ / |_   _  __ _ _ _ __  \n" +
                "| |    / _ \\| '_ \\ / _` | |/ _` |/ _ \\|  __/| | | | |/ _` | | '_ \\ \n" +
                "| |___| (_) | | | | (_| | | (_| | (_) | |   | | |_| | (_| | | | | |\n" +
                "\\_____/\\___/|_| |_|\\__, \\_/\\__,_|\\___/\\_|   |_|\\__,_|\\__, |_|_| |_|\n" +
                "                    __/ |                             __/ |        \n" +
                "                   |___/                             |___/         \n");
        GetLoggerPlus("&e插件名称: PlayerNameCheck");
        GetLoggerPlus("&4&l插件加载中，感谢您的使用！\n" +
                "定制版插件，此插件仅供“小龙套们”服务器使用！感谢理解！" +
                "作者：database-mysql Github: https://github.com/database-mysql\n" +
                "为爱发电！！！");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            GetLoggerPlus("&d找不到PlaceholderAPI,请安装PlaceholderAPI后才能使用本插件");
            Bukkit.getPluginManager().disablePlugins();
            try {
                Thread.sleep(2300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        } else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            GetLoggerPlus("&2已检测到PlaceholderAPI前置");
        }

        Bukkit.getPluginManager().registerEvents(this, this);
        new PlaceholderAPI(this).register();

        File Folder = new File(String.valueOf(getDataFolder()));
        File Config_File = new File(getDataFolder(), "config.yml");
        Yaml = new YamlFile_Utils();
        if (!Folder.exists() || !Config_File.exists()) {
            Folder.mkdirs();
            Yaml.saveYamlFile(getDataFolder().getPath(), "config.yml", "config.yml",true);
        }

        List<File> Folders = new ArrayList<>();
        Folders.add(new File(getDataFolder().getPath()));
        Makedirs(Folders);
        // 默认部分初始化完成

        // 初始化日志部分
        // 创建或获取日志目录
        logDir = new File(getDataFolder(), "logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        GetLoggerPlus("&2日志框架初始化加载完成");

        // 加载配置文件中的违禁词和敏感词
        this.reloadConfig();
        saveDefaultConfig();

        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new Players(),this);
        Bukkit.getPluginManager().registerEvents(this,this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoin_Event(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit_Event(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerChat_Event(),this);
        Bukkit.getPluginManager().registerEvents(new SignChange_Event(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath_Event(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerReSpawn_Event(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangedWorld_Event(),this);

        // 注册命令管理器
        Objects.requireNonNull(getCommand("xiaolongtaoserver")).setExecutor(new command_redirect());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new fly_Command());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new mute());
    }

    @Override
    public void onLoad(){
        // 插件开始加载
    }

    @Override
    public void onDisable() {
        // 插件开始关闭
        GetLoggerPlus("&e&l\n" +
                " _                     _____          ______ _             _       \n" +
                "| |                   |_   _|         | ___ \\ |           (_)      \n" +
                "| |     ___  _ __   __ _| | __ _  ___ | |_/ / |_   _  __ _ _ _ __  \n" +
                "| |    / _ \\| '_ \\ / _` | |/ _` |/ _ \\|  __/| | | | |/ _` | | '_ \\ \n" +
                "| |___| (_) | | | | (_| | | (_| | (_) | |   | | |_| | (_| | | | | |\n" +
                "\\_____/\\___/|_| |_|\\__, \\_/\\__,_|\\___/\\_|   |_|\\__,_|\\__, |_|_| |_|\n" +
                "                    __/ |                             __/ |        \n" +
                "                   |___/                             |___/         \n");
        GetLoggerPlus("&e插件名称: PlayerNameCheck");
        instance = null;
        GetLoggerPlus("&4&l插件已卸载，感谢使用！\n" +
                "定制版插件，此插件仅供“小龙套们”服务器使用！感谢理解！" +
                "作者：database-mysql Github: https://github.com/database-mysql\n" +
                "为爱发电！！！");
    }

    // 局部方法
    private static void Makedirs(List<File> Folders) {
        for (File EachFolder : Folders) {
            if (!EachFolder.exists()) {
                EachFolder.mkdirs();
            }
        }
    }

    public void onPlayerKick(PlayerKickEvent event) {
        String playerName = event.getPlayer().getName();
        String playerIp = event.getPlayer().getAddress().getAddress().getHostAddress();
        Date now = new Date();
        String logFileName = String.format("%s/%s.log", logDir.getAbsolutePath(), dateFormat.format(now));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            // 写入日志条目
            writer.write(String.format("Player: %s, IP: %s, Time: %s, Reason: %s%n",
                    playerName,
                    playerIp,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now),
                    event.getReason()));
        } catch (IOException e) {
            getLogger().severe("Failed to write kick log for player " + playerName);
            e.printStackTrace();
        }
    }
}
