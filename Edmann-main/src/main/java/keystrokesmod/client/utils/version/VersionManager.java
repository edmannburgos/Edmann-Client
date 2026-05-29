package keystrokesmod.client.utils.version;

import java.io.InputStream;
import java.util.Scanner;

public class VersionManager {
    private final String versionFilePath = "/assets/keystrokesmod/version";
    private final String branchFilePath = "/assets/keystrokesmod/branch";

    private Version latestVersion;
    private Version clientVersion;

    public VersionManager() {
        createClientVersion();
        // Sets the latest version to your local version so it never checks the internet
        this.latestVersion = this.clientVersion;
    }

    private void createClientVersion() {
        String version = "1.3.0";
        String branch = "";
        int branchCommit = 0;
        InputStream input;
        Scanner scanner;

        input = VersionManager.class.getResourceAsStream(versionFilePath);
        assert input != null;
        scanner = new Scanner(input);
        version = scanner.nextLine();

        input = VersionManager.class.getResourceAsStream(branchFilePath);
        scanner = new Scanner(input);
        String[] line = scanner.nextLine().split("-");
        branch = line[0];
        try {
            branchCommit = Integer.parseInt(line[1]);
        } catch (NumberFormatException ignored) {
        }

        this.clientVersion = new Version(version, branch, branchCommit);
    }

    public Version getClientVersion() {
        return clientVersion;
    }

    public Version getLatestVersion() {
        return latestVersion;
    }
}