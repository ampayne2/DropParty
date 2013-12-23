/*
 * This file is part of DropParty.
 *
 * Copyright (c) 2013-2013 <http://dev.bukkit.org/server-mods/dropparty//>
 *
 * DropParty is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DropParty is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DropParty.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ampayne2.dropparty.command.commands.remove;

import me.ampayne2.dropparty.DropParty;
import me.ampayne2.dropparty.command.DPCommand;
import me.ampayne2.dropparty.modes.PlayerMode;
import me.ampayne2.dropparty.parties.ChestParty;
import me.ampayne2.dropparty.parties.Party;
import me.ampayne2.dropparty.parties.PartyType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * A command that removes a chest of a certain id or sets the sender to chest removal mode.
 */
public class RemoveChest extends DPCommand {
    private final DropParty dropParty;

    public RemoveChest(DropParty dropParty) {
        super(dropParty, "chest", "Removes a chest or sets you to chest removal mode.", "/dp remove chest <party> [id]", new Permission("dropparty.remove.chest", PermissionDefault.OP), 1, 2, true);
        this.dropParty = dropParty;
    }

    @Override
    public void execute(String command, CommandSender sender, String[] args) {
        String partyName = args[0];
        if (dropParty.getPartyManager().hasParty(partyName)) {
            Party party = dropParty.getPartyManager().getParty(partyName);
            if (party.isType(PartyType.CHEST_PARTY)) {
                if (args.length == 1) {
                    dropParty.getPlayerModeController().setPlayerMode((Player) sender, PlayerMode.REMOVING_CHESTS, party);
                } else {
                    try {
                        int id = Integer.parseInt(args[1]);
                        if (((ChestParty) party).getChests().size() > id) {
                            // TODO: Remove chest of id
                        } else {
                            dropParty.getMessage().sendMessage(sender, "error.chest.iddoesntexist", args[1], partyName);
                        }
                    } catch (NumberFormatException e) {
                        dropParty.getMessage().sendMessage(sender, "error.numberformat");
                    }
                }
            } else {
                dropParty.getMessage().sendMessage(sender, "error.chest.notachestparty", partyName);
            }
        } else {
            dropParty.getMessage().sendMessage(sender, "error.party.doesntexist", partyName);
        }
    }

    @Override
    public List<String> getTabCompleteList(String[] args) {
        return args.length == 0 ? dropParty.getPartyManager().getPartyList(PartyType.CHEST_PARTY) : new ArrayList<String>();
    }
}
