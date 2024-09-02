package info.ata4.minecraft.mineshot.client.command;

import info.ata4.minecraft.mineshot.Mineshot;
import info.ata4.minecraft.mineshot.client.config.MineshotConfig;
import info.ata4.minecraft.mineshot.client.util.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class MinimapCommand extends CommandBase {
    private static final float BLOCKS = 2f; // 2 blocks per screen (vertically) at zoom = 1

    private final MineshotConfig config;

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
        return "commands.mineshot.minimap.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 4) {
            throw new WrongUsageException("commands.mineshot.minimap.usage", new Object[0]);
        } else {
            if (!Minecraft.getMinecraft().isSingleplayer()) {
                return;
            }

            double x = parseDouble(sender.getPosition().getX(), args[0], false);
            double z = parseDouble(sender.getPosition().getZ(), args[1], false);
            double x1 = parseDouble(sender.getPosition().getX(), args[2], false);
            double z1 = parseDouble(sender.getPosition().getZ(), args[3], false);

            float zoom = (float) (Math.abs(z - z1) / BLOCKS);
            int pixelsPerBlock = args.length >= 6 ? parseInt(args[5]) : 16;
            int captureWidth = (int) Math.ceil(Math.abs(x - x1) * pixelsPerBlock);
            int captureHeight = (int) Math.ceil(zoom * 2 * pixelsPerBlock);

            double xPos = x + ((x1 - x) * 0.5);
            double zPos = z + ((z1 - z) * 0.5);
            double yPos = args.length >= 5 ? parseDouble(sender.getPosition().getY(), args[4], false) : sender.getPosition().getY();

            sender.getCommandSenderEntity().setLocationAndAngles(xPos, yPos, zPos, 0, 90);

            Minecraft.getMinecraft().gameSettings.hideGUI = true;
            Mineshot.ovh.enable();
            Mineshot.ovh.setZoomAndRotation(zoom, 90, 0);

            config.captureWidth.set(captureWidth);
            config.captureHeight.set(captureHeight);
            Mineshot.sch.capture();

            ChatUtils.print("commands.mineshot.minimap.info", new Object[] { xPos, yPos, zPos, zoom, captureWidth, captureHeight });
        }
    }
}