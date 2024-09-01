package info.ata4.minecraft.mineshot.client.command;

import info.ata4.minecraft.mineshot.Mineshot;
import info.ata4.minecraft.mineshot.MineshotCore;
import info.ata4.minecraft.mineshot.client.config.MineshotConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class HugeScreenshotCommand extends CommandBase {
    private MineshotConfig config;

    public HugeScreenshotCommand(MineshotConfig config) {
        this.config = config;
    }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public String getCommandName() {
        return "hugescreenshot";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/hugescreenshot [width] [height]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 0 && args.length != 2) {
            throw new WrongUsageException("", new Object[0]);
        } else {
            if (args.length == 2) {
                config.captureWidth.set(parseInt(args[0]));
                config.captureHeight.set(parseInt(args[1]));
            }
            Mineshot.ssh.capture();
        }
    }
}
