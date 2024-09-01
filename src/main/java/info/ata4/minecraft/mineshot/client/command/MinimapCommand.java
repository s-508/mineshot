package info.ata4.minecraft.mineshot.client.command;

import info.ata4.minecraft.mineshot.Mineshot;
import info.ata4.minecraft.mineshot.client.config.MineshotConfig;
import info.ata4.minecraft.mineshot.client.util.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.*;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.player.EntityPlayerMP;

public class MinimapCommand extends CommandBase {
    private static final int BLOCKS = 2; // 2 blocks per screen (vertically) at zoom = 1

    private MineshotConfig config;

    public MinimapCommand(MineshotConfig config) {
        this.config = config;
    }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public String getCommandName() {
        return "minimap";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/minimap <x1> <z1> <x2> <z2> [y] [pixels]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 4) {
            throw new WrongUsageException("", new Object[0]);
        } else {
            if (!Minecraft.getMinecraft().isSingleplayer()) {
                ChatUtils.print("");
                return;
            }

            double x = parseDouble(sender.getPosition().getX(), args[0], false);
            double z = parseDouble(sender.getPosition().getZ(), args[1], false);
            double x1 = parseDouble(sender.getPosition().getX(), args[2], false);
            double z1 = parseDouble(sender.getPosition().getZ(), args[3], false);

            double xDiff = Math.abs(x - x1);
            double zDiff = Math.abs(z - z1);

            double yPos = args.length >= 5 ? parseDouble(sender.getPosition().getY(), args[4], false) : sender.getPosition().getY();
            int pixelSize = args.length >= 6 ? parseInt(args[5]) : 16;

            sender.getCommandSenderEntity().setLocationAndAngles(x + ((x1 - x) * 0.5), yPos, z + ((z1 - z) * 0.5), 0, 90);

            Minecraft.getMinecraft().gameSettings.hideGUI = true;

            Mineshot.ovh.enable();
            Mineshot.ovh.setZoomAndRotation((float) (zDiff / BLOCKS), 90, 0);

            config.captureWidth.set((int) Math.ceil(xDiff * pixelSize));
            config.captureHeight.set((int) Math.ceil(zDiff * pixelSize));

            Mineshot.ssh.capture();
        }
    }
}