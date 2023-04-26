package fr.ucs.advancedrank.config;

import fr.ucs.advancedrank.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

public enum ConfigFile {
    PLAYERS("players.yml");

    private final String fileName;
    private final File dataFolder;

    ConfigFile(String fileName) {
        this.fileName = fileName;
        this.dataFolder = Main.getInstance().getDataFolder();
    }

    public void create(Logger logger) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Resource Path can't be Null or Empty");
        }

        InputStream in = Main.getInstance().getResource(fileName);
        if (in == null) {
            throw new IllegalArgumentException("The resource '" + fileName + "' can't be found in plugin .jar");
        }

        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            logger.severe("Failed to make directory");
        }

        File outputFile = getFile();

        try {
            if (!outputFile.exists()) {
                logger.info("The '" + fileName + "' file not found, creating it.");

                OutputStream out = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int n;

                while ((n = in.read(buffer)) > 0) {
                    out.write(buffer, 0, n);
                }

                out.close();
                in.close();

                if (!outputFile.exists()) {
                    logger.severe("Unable to make the file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return new File(dataFolder, fileName);
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration config) throws IOException {
        config.save(getFile());
    }

    public String getFileName() {
        return fileName;
    }
}
