package com.android.pm;

import androidx.annotation.NonNull;

final class StubInterfaceImpl extends StubInterface {
    private static StubInterface si;

    static StubInterface getInstance() {
        if (si == null)
            si = new StubInterfaceImpl();
        return si;
    }

    private final PackageManager pm = new PmImpl();
    private final DevicePolicyManager dpm = new DpmImpl();

    private StubInterfaceImpl() {
    }

    private static final class PmImpl extends PackageManager {
        @Override
        public void install(@NonNull String path) throws Exception {
            CommandUtils.exec("pm install " + path);
        }

        @Override
        public void install(@NonNull String path, int user) throws Exception {
            CommandUtils.exec("pm install --user " + user + " " + path);
        }

        @Override
        public void uninstall(@NonNull String packageName) throws Exception {
            CommandUtils.exec("pm uninstall " + packageName);
        }

        @Override
        public void uninstall(@NonNull String packageName, int user) throws Exception {
            CommandUtils.exec("pm uninstall --user " + user + " " + packageName);
        }

        @Override
        public void installExisting(@NonNull String packageName) throws Exception {
            CommandUtils.exec("pm install-existing " + packageName);
        }

        @Override
        public void installExisting(@NonNull String packageName, int user) throws Exception {
            CommandUtils.exec("pm install-existing --user " + user + " " + packageName);
        }

        @Override
        public void enable(@NonNull String packageName) throws Exception {
            CommandUtils.exec("pm enable " + packageName);
        }

        @Override
        public void enable(@NonNull String packageName, int user) throws Exception {
            CommandUtils.exec("pm enable --user " + user + " " + packageName);
        }

        @Override
        public void disable(@NonNull String packageName) throws Exception {
            CommandUtils.exec("pm disable " + packageName);
        }

        @Override
        public void disable(@NonNull String packageName, int user) throws Exception {
            CommandUtils.exec("pm disable --user " + user + " " + packageName);
        }

        @Override
        public void grant(@NonNull String packageName, @NonNull String permission) throws Exception {
            CommandUtils.exec("pm grant " + packageName + " " + permission);
        }

        @Override
        public void grant(@NonNull String packageName, @NonNull String permission, int user) throws Exception {
            CommandUtils.exec("pm grant --user " + user + " " + packageName + " " + permission);
        }

        @Override
        public void revoke(@NonNull String packageName, @NonNull String permission) throws Exception {
            CommandUtils.exec("pm revoke " + packageName + " " + permission);
        }

        @Override
        public void revoke(@NonNull String packageName, @NonNull String permission, int user) throws Exception {
            CommandUtils.exec("pm revoke --user " + user + " " + packageName + " " + permission);
        }

        @Override
        public String[] listPackages() throws Exception {
            final CommandUtils.Result r = CommandUtils.exec("pm list packages");
            final String[] str = r.getOutput();
            for (int i = 0; i < str.length; i++)
                str[i] = str[i].replace("package:", "");
            return str;
        }

        @Override
        public String[] listPackages(int user) throws Exception {
            final CommandUtils.Result r = CommandUtils.exec("pm list packages --user " + user);
            final String[] str = r.getOutput();
            for (int i = 0; i < str.length; i++)
                str[i] = str[i].replace("package:", "");
            return str;
        }
    }

    private static final class DpmImpl extends DevicePolicyManager {
        @Override
        public void setActiveAdmin(@NonNull String componentName, int user) throws Exception {
            CommandUtils.exec("dpm set-active-admin --user " + user + " " + componentName);
        }

        @Override
        public void setProfileOwner(@NonNull String componentName, int user) throws Exception {
            CommandUtils.exec("dpm set-profile-owner --user " + user + " " + componentName);
        }

        @Override
        public void setDeviceOwner(@NonNull String componentName) throws Exception {
            CommandUtils.exec("dpm set-device-owner " + componentName);
        }

        @Override
        public void removeActiveAdmin(@NonNull String componentName, int user) throws Exception {
            CommandUtils.exec("dpm remove-active-admin --user " + user + componentName);
        }
    }

    @NonNull
    @Override
    public final PackageManager pm() {
        return pm;
    }

    @NonNull
    @Override
    public final DevicePolicyManager dpm() {
        return dpm;
    }
}