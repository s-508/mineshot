package info.ata4.minecraft.mineshot.client.command;

import info.ata4.minecraft.mineshot.Mineshot;
import info.ata4.minecraft.mineshot.client.util.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class ZoomCommand extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public String getCommandName() {
        return "zoom";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.mineshot.zoom.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1 && args.length != 0) {
            throw new WrongUsageException("commands.mineshot.zoom.usage", new Object[0]);
        } else {
            if (!Mineshot.ovh.isEnabled()) {
                return;
            }

            if (args.length == 0) {
                ChatUtils.print("commands.mineshot.zoom.result", new Object[] { Float.toString(Mineshot.ovh.getZoom()) });
            }
            if (args.length == 1) {
                Mineshot.ovh.setZoom((float) parseDouble(args[0]));
            }
        }
    }
}
