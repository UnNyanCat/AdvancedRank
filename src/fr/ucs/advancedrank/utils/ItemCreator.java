package fr.ucs.advancedrank.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemCreator {
    private ItemStack item;
    private Player possesseur;
    private String creator_name;
    private ArrayList<String> tag;
    private int slot;
    private ArrayList<Pattern> patterns;

    public ItemCreator(final Material material) {
        this.item = new ItemStack(material);
    }

    public ItemCreator(final ItemStack item) {
        this.setMaterial(item.getType());
        this.setAmount(item.getAmount());
        this.setDurability(item.getDurability());
        this.setName(item.getItemMeta().getDisplayName());
        this.setEnchantments(item.getItemMeta().getEnchants());
        this.setLores(item.getItemMeta().getLore());
    }

    public ItemCreator(final ItemCreator itemcreator) {
        this.item = itemcreator.getItem();
        this.possesseur = itemcreator.getPossesseur();
        this.creator_name = itemcreator.getCreator_name();
        this.tag = new ArrayList<String>(itemcreator.getTag());
    }

    public ItemCreator(final String itemcreatorstring) {
        this.item = new ItemStack(Material.STONE);
        this.fromString(itemcreatorstring);
    }

    @Override
    public String toString() {
        final StringBuilder itemcreator = new StringBuilder();
        itemcreator.append("ItemCreator:{");
        itemcreator.append("Slot:{" + this.slot + "}");
        itemcreator.append(",Amount:{" + this.getAmount() + "}");
        itemcreator.append(",Durability:{" + this.getDurability() + "}");
        if (this.getName() != null) {
            itemcreator.append(",Name:{" + this.getName() + "}");
        }
        if (this.getLores() != null) {
            itemcreator.append(",Lores:{");
            for (final String lore : this.getLores()) {
                itemcreator.append("['" + lore + "'],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        if (this.getEnchantments() != null) {
            itemcreator.append(",Enchantments:{");
            for (final Map.Entry<Enchantment, Integer> e : this.getEnchantments().entrySet()) {
                itemcreator.append("[" + e.getKey().toString() + "," + e.getValue() + "],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        if (this.getItemFlags() != null) {
            itemcreator.append(",ItemFlags:{");
            for (final ItemFlag itemflag : this.getItemFlags()) {
                itemcreator.append("[" + itemflag.toString() + "],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        if (this.getPossesseur() != null) {
            itemcreator.append(",Possesseur:{" + this.getPossesseur().getUniqueId().toString() + "}");
        }
        if (this.getCreator_name() != null) {
            itemcreator.append(",Creator_name:{" + this.getCreator_name() + "}");
        }
        if (this.getTag() != null) {
            itemcreator.append(",Tag:{");
            for (final String tag : this.getTag()) {
                itemcreator.append("[" + tag + "],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        itemcreator.append("}");
        return itemcreator.toString();
    }

    public ItemCreator fromString(String itemcreatorstring) {
        if (itemcreatorstring.substring(0, 10).equals("ItemCreator")) {
            itemcreatorstring = itemcreatorstring.substring(12, itemcreatorstring.length() - 2);
            while (itemcreatorstring != "") {
                int i;
                for (i = 0; itemcreatorstring.charAt(i) != ':'; ++i) {
                }
                final String currentname = itemcreatorstring.substring(0, i - 1);
                itemcreatorstring = itemcreatorstring.substring(i);
                Integer f = 0;
                for (boolean instring = false; itemcreatorstring.charAt(f) != '}' && !instring; ++f) {
                    if (itemcreatorstring.substring(f, f).equals("'")) {
                        instring = !instring;
                    }
                    final Integer integer1 = f;
                }
                String currentpacket = itemcreatorstring.substring(0, f);
                itemcreatorstring = itemcreatorstring.substring(f + 1);
                System.out.println("  ");
                System.out.println("  ");
                System.out.println("  ");
                System.out.println("  ITEM CREATOR   ");
                System.out.println(currentname);
                System.out.println("  ");
                final String s = currentname;
                switch (s) {
                    case "Type": {
                        System.out.println("  TYPE: " + Material.valueOf(currentpacket.substring(0, currentpacket.length() - 1)).toString());
                        this.setMaterial(Material.valueOf(currentpacket.substring(0, currentpacket.length() - 1)));
                    }
                    case "Slot": {
                        this.setSlot(Integer.valueOf(currentpacket.substring(1, currentpacket.length() - 2)));
                    }
                    case "Amount": {
                        this.setAmount(Integer.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Durability": {
                        this.setDurability(Short.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Name": {
                        this.setName(currentpacket.substring(1, currentpacket.length() - 1));
                    }
                    case "Lores": {
                        final ArrayList<String> lores = new ArrayList<String>();
                        Integer c;
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            Boolean inlore = false;
                            c = 0;
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            while (currentpacket.charAt(c) != ']' && !inlore) {
                                if (currentpacket.substring(c, c).equals("'")) {
                                    inlore = !inlore;
                                }
                                final Integer integer2 = c;
                                ++c;
                            }
                            lores.add(currentpacket.substring(1, c - 1));
                        }
                        this.setLores(lores);
                    }
                    case "Glow": {
                        this.setGlow(Boolean.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Enchantments": {
                        final HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
                        Integer c2;
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c2)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            for (c2 = 0; currentpacket.charAt(c2) != ']'; ++c2) {
                                final Integer integer3 = c2;
                            }
                            final String current = currentpacket.substring(1, c2 - 1);
                            enchantments.put(Enchantment.getByName(current.split(",")[0]), Integer.valueOf(current.split(",")[1]));
                        }
                        this.setEnchantments(enchantments);
                    }
                    case "ItemFlags": {
                        final ArrayList<ItemFlag> itemflags = new ArrayList<ItemFlag>();
                        Integer c2;
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c2)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            for (c2 = 0; currentpacket.charAt(c2) != ']'; ++c2) {
                                final Integer integer3 = c2;
                            }
                            final String current = currentpacket.substring(1, c2 - 1);
                            itemflags.add(ItemFlag.valueOf(current));
                        }
                        this.setItemFlags(itemflags);
                    }
                    case "Owner": {
                        this.setOwner(currentpacket.substring(1, currentpacket.length() - 1));
                    }
                    case "BaseColor": {
                        this.setBasecolor(DyeColor.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Patterns": {
                        final ArrayList<Pattern> patterns = new ArrayList<Pattern>();
                        Integer c2;
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c2)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            for (c2 = 0; currentpacket.charAt(c2) != ']'; ++c2) {
                                final Integer integer3 = c2;
                            }
                            final String current = currentpacket.substring(1, c2 - 1);
                            patterns.add(new Pattern(DyeColor.valueOf(current.split(",")[0]), PatternType.valueOf(current.split(",")[1])));
                        }
                        this.setPatterns(patterns);
                    }
                    case "StoredEnchantments": {
                        final HashMap<Enchantment, Integer> storedenchantments = new HashMap<Enchantment, Integer>();
                        Integer c2;
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c2)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            for (c2 = 0; currentpacket.charAt(c2) != ']'; ++c2) {
                                final Integer integer3 = c2;
                            }
                            final String current = currentpacket.substring(1, c2 - 1);
                            storedenchantments.put(Enchantment.getByName(current.split(",")[0]), Integer.valueOf(current.split(",")[1]));
                        }
                        this.setStoredEnchantments(storedenchantments);
                    }
                    case "Possesseur": {
                        this.setPossesseur(Bukkit.getPlayer(UUID.fromString(currentpacket.substring(1, currentpacket.length() - 1))));
                    }
                    case "Creator_name": {
                        this.setCreator_name(currentpacket.substring(1, currentpacket.length() - 1));
                    }
                    case "Tag": {
                        final ArrayList<String> taglist = new ArrayList<String>();
                        Integer c;
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            Boolean intag = false;
                            c = 0;
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            while (currentpacket.charAt(c) != ']' && !intag) {
                                if (currentpacket.substring(c, c).equals("'")) {
                                    intag = !intag;
                                }
                                final Integer integer2 = c;
                                ++c;
                            }
                            taglist.add(currentpacket.substring(1, c - 1));
                        }
                        this.setTag(taglist);
                        continue;
                    }
                }
            }
        }
        return this;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ItemCreator setMaterial(final Material material) {
        if (this.item == null) {
            this.item = new ItemStack(material);
        } else {
            this.item.setType(material);
        }
        return this;
    }

    public ItemCreator setUnbreakable(final Boolean unbreakable) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        this.item.setItemMeta(meta);
        return this;
    }

    public Integer getAmount() {
        return this.item.getAmount();
    }

    public ItemCreator setAmount(final Integer amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemCreator setSkull(final String paramString1) {
        final SkullMeta localSkullMeta = (SkullMeta) this.item.getItemMeta();
        final GameProfile localGameProfile = new GameProfile(UUID.randomUUID(), "domei_heads");
        final PropertyMap localPropertyMap = localGameProfile.getProperties();
        localPropertyMap.put("textures", new Property("textures", paramString1));
        try {
            final Field localField = localSkullMeta.getClass().getDeclaredField("profile");
            localField.setAccessible(true);
            localField.set(localSkullMeta, localGameProfile);
        } catch (NoSuchFieldException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex = new ReflectiveOperationException();
            final ReflectiveOperationException localNoSuchFieldException = ex;
            localNoSuchFieldException.printStackTrace();
        }
        this.item.setItemMeta(localSkullMeta);
        return this;
    }

    public Short getDurability() {
        return this.item.getDurability();
    }

    public ItemCreator setDurability(final Short durability) {
        this.item.setDurability(durability);
        return this;
    }

    public ItemCreator setDurability(final Integer durability) {
        final Short shortdurability = (short) (int) durability;
        this.item.setDurability(shortdurability);
        return this;
    }

    public Integer getDurabilityInteger() {
        return (int) this.item.getDurability();
    }

    public ItemMeta getMeta() {
        return this.item.getItemMeta();
    }

    public ItemCreator setMeta(final ItemMeta meta) {
        this.item.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return this.item.getItemMeta().getDisplayName();
    }

    public ItemCreator setName(final String name) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public ArrayList<String> getLores() {
        return (ArrayList<String>) this.item.getItemMeta().getLore();
    }

    public ItemCreator setLores(final List<String> list) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setLore(list);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearLores() {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setLore(new ArrayList());
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator setSkullURL(final String url) {
        if (url.isEmpty()) {
            return this;
        }
        final SkullMeta headMeta = (SkullMeta) this.item.getItemMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "domei_heads");
        final byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex2) {
            final Exception ex = new Exception();
            final Exception e1 = ex;
            e1.printStackTrace();
        }
        this.item.setItemMeta(headMeta);
        return this;
    }

    public ItemCreator insertLores(final String lore, final Integer position) {
        final ItemMeta meta = this.item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null) {
            lores = new ArrayList<String>();
        }
        lores.add(position, lore);
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addLore(final String lore) {
        final ItemMeta meta = this.item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null) {
            lores = new ArrayList<String>();
        }
        if (lore != null) {
            lores.add(lore);
        } else {
            lores.add(" ");
        }
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeLore(final String lore) {
        final ItemMeta meta = this.item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null && lores.contains(lore)) {
            lores.remove(lore);
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public String[] getTableauLores() {
        final String[] tableaulores = new String[0];
        if (this.item.getItemMeta().getLore() != null) {
            Integer i = 0;
            for (final String lore : this.item.getItemMeta().getLore()) {
                tableaulores[i] = lore;
                final Integer integer1 = i;
                ++i;
            }
        }
        return tableaulores;
    }

    public ItemCreator setTableauLores(final String[] lores) {
        final ArrayList<String> tableaulores = new ArrayList<String>();
        Collections.addAll(tableaulores, lores);
        final ItemMeta meta = this.item.getItemMeta();
        meta.setLore(tableaulores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator replaceallLores(final String replacelore, final String newlore) {
        final ItemMeta meta = this.item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null && lores.contains(replacelore)) {
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator replaceoneLore(final Integer ligne, final String newlore) {
        final ItemMeta meta = this.item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null && lores.get(ligne) != null) {
            lores.remove(ligne);
            lores.add(ligne, newlore);
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator replacefirstLores(final String replacelore, final String newlore, final Integer nombre) {
        final ItemMeta meta = this.item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null && lores.contains(replacelore)) {
            final Integer replaced = 0;
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator replacelastLores(final String replacelore, final String newlore, final Integer nombre) {
        final ItemMeta meta = this.item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null && lores.contains(replacelore)) {
            final Integer replaced = 0;
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setGlow(final Boolean glow) {
        if (glow) {
            final net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(this.item);
            NBTTagCompound tag = null;
            if (!minecraftitemstack.hasTag()) {
                tag = new NBTTagCompound();
                minecraftitemstack.setTag(new NBTTagCompound());
            } else {
                tag = minecraftitemstack.getTag();
            }
            final NBTTagList ench = new NBTTagList();
            tag.set("ench", ench);
            minecraftitemstack.setTag(tag);
            this.item = CraftItemStack.asCraftMirror(minecraftitemstack);
        } else {
            final net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(this.item);
            NBTTagCompound tag = null;
            if (!minecraftitemstack.hasTag()) {
                tag = minecraftitemstack.getTag();
                if (tag.hasKey("ench")) {
                    tag.remove("ench");
                    minecraftitemstack.setTag(tag);
                    this.item = CraftItemStack.asCraftMirror(minecraftitemstack);
                }
            }
        }
        return this;
    }

    public HashMap<Enchantment, Integer> getEnchantments() {
        return new HashMap<Enchantment, Integer>(this.item.getItemMeta().getEnchants());
    }

    public ItemCreator setEnchantments(final Map<Enchantment, Integer> map) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<Enchantment>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
        }
        for (final Map.Entry<Enchantment, Integer> e : map.entrySet()) {
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearEnchantments() {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<Enchantment>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addEnchantment(final Enchantment enchantment, final Integer lvl) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(enchantment, lvl, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeEnchantment(final Enchantment enchantment) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null && meta.getEnchants().containsKey(enchantment)) {
            meta.removeEnchant(enchantment);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public Enchantment[] getTableauEnchantments() {
        final Enchantment[] enchantments = new Enchantment[0];
        if (this.item.getItemMeta().getEnchants() != null) {
            Integer i = 0;
            for (final Enchantment enchantment : this.item.getItemMeta().getEnchants().keySet()) {
                enchantments[i] = enchantment;
                final Integer integer1 = i;
                ++i;
            }
        }
        return enchantments;
    }

    public Integer[] getTableauEnchantmentslvl() {
        final Integer[] enchantmentslvl = new Integer[0];
        if (this.item.getItemMeta().getEnchants() != null) {
            Integer i = 0;
            for (final Integer enchantmentlvl : this.item.getItemMeta().getEnchants().values()) {
                enchantmentslvl[i] = enchantmentlvl;
                final Integer integer1 = i;
                ++i;
            }
        }
        return enchantmentslvl;
    }

    public ItemCreator setTableauEnchantments(final Enchantment[] enchantments, final Integer[] enchantmentslvl) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<Enchantment>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ArrayList<ItemFlag> getItemFlags() {
        final ArrayList<ItemFlag> itemflags = new ArrayList<ItemFlag>();
        if (this.item.getItemMeta().getItemFlags() != null) {
            for (final ItemFlag itemflag : this.item.getItemMeta().getItemFlags()) {
                itemflags.add(itemflag);
            }
        }
        return itemflags;
    }

    public ItemCreator setItemFlags(final ArrayList<ItemFlag> itemflags) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<ItemFlag>();
            for (final ItemFlag itemflag : meta.getItemFlags()) {
                cloneitemflags.add(itemflag);
            }
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
        }
        for (final ItemFlag itemflag2 : itemflags) {
            meta.addItemFlags(itemflag2);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addFlag(final ItemFlag... flag) {
        final ItemMeta im = this.item.getItemMeta();
        im.addItemFlags(flag);
        this.item.setItemMeta(im);
        return this;
    }

    public ItemCreator clearItemFlags() {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<ItemFlag>();
            for (final ItemFlag itemflag : meta.getItemFlags()) {
                cloneitemflags.add(itemflag);
            }
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addItemFlags(final ItemFlag itemflag) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(itemflag);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeItemFlags(final ItemFlag itemflag) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null && meta.getItemFlags().contains(itemflag)) {
            meta.removeItemFlags(itemflag);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemFlag[] getTableauItemFlags() {
        final ItemMeta meta = this.item.getItemMeta();
        final ItemFlag[] itemflags = new ItemFlag[0];
        Integer i = 0;
        if (meta.getItemFlags() != null) {
            for (final ItemFlag itemflag : meta.getItemFlags()) {
                itemflags[i] = itemflag;
                final Integer integer1 = i;
                ++i;
            }
        }
        return itemflags;
    }

    public ItemCreator setTableauItemFlags(final ItemFlag[] itemflags) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<ItemFlag>();
            for (final ItemFlag itemflag : meta.getItemFlags()) {
                cloneitemflags.add(itemflag);
            }
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
        }
        for (final ItemFlag itemflag2 : itemflags) {
            meta.addItemFlags(itemflag2);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public SkullMeta getSkullMeta() {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            return (SkullMeta) this.item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setSkullMeta(final SkullMeta skullmeta) {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            this.item.setItemMeta(skullmeta);
        }
        return this;
    }

    public String getOwner() {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            return ((SkullMeta) this.item.getItemMeta()).getOwner();
        }
        return null;
    }

    public ItemCreator setOwner(final String owner) {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
            meta.setOwner(owner);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public BannerMeta getBannerMeta() {
        if (this.item.getType().equals(Material.BANNER)) {
            return (BannerMeta) this.item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setBannerMeta(final BannerMeta bannermeta) {
        if (this.item.getType().equals(Material.BANNER)) {
            this.item.setItemMeta(bannermeta);
        }
        return this;
    }

    public DyeColor getBasecolor() {
        if (this.item.getType().equals(Material.BANNER)) {
            return ((BannerMeta) this.item.getItemMeta()).getBaseColor();
        }
        return null;
    }

    public ItemCreator setBasecolor(final DyeColor basecolor) {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            meta.setBaseColor(basecolor);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ArrayList<Pattern> getPatterns() {
        if (this.item.getType().equals(Material.BANNER)) {
            return (ArrayList<Pattern>) ((BannerMeta) this.item.getItemMeta()).getPatterns();
        }
        return null;
    }

    public ItemCreator setPatterns(final ArrayList<Pattern> petterns) {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            meta.setPatterns(petterns);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearPatterns() {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            meta.setPatterns(new ArrayList());
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addPattern(final Pattern pattern) {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            meta.addPattern(pattern);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator removePattern(final Pattern pattern) {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            final ArrayList<Pattern> patterns = (ArrayList<Pattern>) meta.getPatterns();
            if (patterns != null && patterns.contains(pattern)) {
                patterns.remove(pattern);
                meta.setPatterns(patterns);
                this.item.setItemMeta(meta);
            }
        }
        return this;
    }

    public Pattern[] getTableauPatterns() {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            final Pattern[] tableaupatterns = new Pattern[0];
            if (meta.getPatterns() != null) {
                Integer i = 0;
                for (final Pattern pattern : meta.getPatterns()) {
                    tableaupatterns[i] = pattern;
                    final Integer integer1 = i;
                    ++i;
                }
            }
            return tableaupatterns;
        }
        return null;
    }

    public ItemCreator setTableauPatterns(final Pattern[] patterns) {
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            if (meta.getPatterns() != null) {
                meta.setPatterns(new ArrayList());
            }
            for (final Pattern pattern : patterns) {
                meta.addPattern(pattern);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public EnchantmentStorageMeta getEnchantmentStorageMeta() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            return (EnchantmentStorageMeta) this.item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setEnchantmentStorageMeta(final EnchantmentStorageMeta enchantmentstoragemeta) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            this.item.setItemMeta(enchantmentstoragemeta);
        }
        return this;
    }

    public HashMap<Enchantment, Integer> getStoredEnchantments() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            return (HashMap<Enchantment, Integer>) this.item.getItemMeta().getEnchants();
        }
        return null;
    }

    public ItemCreator setStoredEnchantments(final HashMap<Enchantment, Integer> storedenchantments) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            if (meta.getStoredEnchants() != null) {
                final ArrayList<Enchantment> clonestoredenchantments = new ArrayList<Enchantment>(meta.getStoredEnchants().keySet());
                for (final Enchantment storedenchantment : clonestoredenchantments) {
                    meta.removeStoredEnchant(storedenchantment);
                }
            }
            for (final Map.Entry<Enchantment, Integer> e : storedenchantments.entrySet()) {
                meta.addEnchant(e.getKey(), e.getValue(), true);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearStoredEnchantments() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            if (meta.getStoredEnchants() != null) {
                final ArrayList<Enchantment> clonestoredenchantments = new ArrayList<Enchantment>(meta.getStoredEnchants().keySet());
                for (final Enchantment storedenchantment : clonestoredenchantments) {
                    meta.removeStoredEnchant(storedenchantment);
                }
                this.item.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemCreator addStoredEnchantment(final Enchantment storedenchantment, final Integer lvl) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            meta.addStoredEnchant(storedenchantment, lvl, true);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator removeStoredEnchantment(final Enchantment enchantment) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            if (meta.getStoredEnchants() != null && meta.getStoredEnchants().containsKey(enchantment)) {
                meta.removeEnchant(enchantment);
                this.item.setItemMeta(meta);
            }
        }
        return this;
    }

    public Enchantment[] getTableauStoredEnchantments() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            final Enchantment[] storedenchantments = new Enchantment[0];
            if (meta.getStoredEnchants() != null) {
                Integer i = 0;
                for (final Enchantment storedenchantment : meta.getStoredEnchants().keySet()) {
                    storedenchantments[i] = storedenchantment;
                    final Integer integer1 = i;
                    ++i;
                }
            }
            return storedenchantments;
        }
        return null;
    }

    public Integer[] getTableauStoredEnchantmentslvl() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            final Integer[] storedenchantmentslvl = new Integer[0];
            if (meta.getStoredEnchants() != null) {
                Integer i = 0;
                for (final Integer storedenchantmentlvl : meta.getStoredEnchants().values()) {
                    storedenchantmentslvl[i] = storedenchantmentlvl;
                    final Integer integer1 = i;
                    ++i;
                }
            }
            return storedenchantmentslvl;
        }
        return null;
    }

    public ItemCreator setTableauStoredEnchantments(final Enchantment[] storedenchantments, final Integer[] storedenchantmentslvl) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.item.getItemMeta();
            if (meta.getStoredEnchants() != null) {
                final ArrayList<Enchantment> clonestoredenchantments = new ArrayList<Enchantment>(meta.getStoredEnchants().keySet());
                for (final Enchantment storedenchantment : clonestoredenchantments) {
                    meta.removeStoredEnchant(storedenchantment);
                }
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addallItemsflags() {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addBannerPreset(final Integer ID, final DyeColor patterncolor) {
        switch (ID) {
            case 1: {
                this.addBannerPreset(BannerPreset.Bar, patterncolor);
                break;
            }
            case 2: {
                this.addBannerPreset(BannerPreset.Previous, patterncolor);
                break;
            }
            case 3: {
                this.addBannerPreset(BannerPreset.Next, patterncolor);
                break;
            }
            case 4: {
                this.addBannerPreset(BannerPreset.Heart, patterncolor);
                break;
            }
            case 5: {
                this.addBannerPreset(BannerPreset.Star, patterncolor);
                break;
            }
            case 6: {
                this.addBannerPreset(BannerPreset.Cross, patterncolor);
                break;
            }
            case 7: {
                this.addBannerPreset(BannerPreset.YinYang, patterncolor);
                break;
            }
            case 8: {
                this.addBannerPreset(BannerPreset.Losange, patterncolor);
                break;
            }
            case 9: {
                this.addBannerPreset(BannerPreset.Minus, patterncolor);
                break;
            }
            case 10: {
                this.addBannerPreset(BannerPreset.Plus, patterncolor);
                break;
            }
        }
        return this;
    }

    public ItemCreator addBannerPreset(final BannerPreset type, final DyeColor patterncolor) {
        if (type == null) {
            return this;
        }
        if (this.item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            meta.getBaseColor();
        }
        return this;
    }

    private void addasyncronePattern(final Pattern pattern, final Boolean calcul) {
        if (calcul) {
            this.patterns.add(pattern);
            final BannerMeta meta = (BannerMeta) this.item.getItemMeta();
            for (final Pattern currentpattern : this.patterns) {
                meta.addPattern(currentpattern);
            }
            this.patterns.clear();
            this.item.setItemMeta(meta);
        } else {
            if (this.patterns == null) {
                this.patterns = new ArrayList<Pattern>();
            }
            this.patterns.add(pattern);
        }
    }

    public Player getPossesseur() {
        return this.possesseur;
    }

    public ItemCreator setPossesseur(final Player possesseur) {
        this.possesseur = possesseur;
        return this;
    }

    public String getCreator_name() {
        return this.creator_name;
    }

    public ItemCreator setCreator_name(final String creator_name) {
        this.creator_name = creator_name;
        return this;
    }

    public ArrayList<String> getTag() {
        return this.tag;
    }

    public ItemCreator setTag(final ArrayList<String> tag) {
        this.tag = tag;
        return this;
    }

    public ItemCreator clearTag() {
        if (this.tag != null) {
            this.tag.clear();
        }
        return this;
    }

    public ItemCreator addTag(final String tag) {
        if (this.tag == null) {
            this.tag = new ArrayList<String>();
        }
        this.tag.add(tag);
        return this;
    }

    public ItemCreator removeTag(final String tag) {
        if (this.tag != null) {
            this.tag.remove(tag);
        }
        return this;
    }

    public String[] getTableauTag() {
        final String[] taglist = new String[0];
        Integer i = 0;
        for (final String currenttag : this.tag) {
            taglist[i] = currenttag;
            final Integer integer1 = i;
            ++i;
        }
        return taglist;
    }

    public ItemCreator setTableaTag(final String[] tag) {
        if (this.tag == null) {
            this.tag = new ArrayList<String>();
        } else {
            this.tag.clear();
        }
        Collections.addAll(this.tag, tag);
        return this;
    }

    public Boolean comparate(final ItemCreator item, final ComparatorType type) {
        switch (type) {
            case All: {
                return this.comparate(item, ComparatorType.Material) && this.comparate(item, ComparatorType.Amount) && this.comparate(item, ComparatorType.Durability) && this.comparate(item, ComparatorType.Name) && this.comparate(item, ComparatorType.Lores) && this.comparate(item, ComparatorType.Enchantements) && this.comparate(item, ComparatorType.ItemsFlags) && this.comparate(item, ComparatorType.Owner) && this.comparate(item, ComparatorType.BaseColor) && this.comparate(item, ComparatorType.Patterns) && this.comparate(item, ComparatorType.StoredEnchantements) && this.comparate(item, ComparatorType.Creator_Name) && this.comparate(item, ComparatorType.Possesseur) && this.comparate(item, ComparatorType.TAG);
            }
            case Similar: {
                return this.comparate(item, ComparatorType.Material) && this.comparate(item, ComparatorType.Durability) && this.comparate(item, ComparatorType.Name) && this.comparate(item, ComparatorType.Lores) && this.comparate(item, ComparatorType.Enchantements) && this.comparate(item, ComparatorType.ItemsFlags) && this.comparate(item, ComparatorType.Owner) && this.comparate(item, ComparatorType.BaseColor) && this.comparate(item, ComparatorType.Patterns) && this.comparate(item, ComparatorType.StoredEnchantements);
            }
            case ItemStack: {
                return this.comparate(item, ComparatorType.Material) && this.comparate(item, ComparatorType.Amount) && this.comparate(item, ComparatorType.Durability) && this.comparate(item, ComparatorType.Name) && this.comparate(item, ComparatorType.Lores) && this.comparate(item, ComparatorType.Enchantements) && this.comparate(item, ComparatorType.ItemsFlags) && this.comparate(item, ComparatorType.Owner) && this.comparate(item, ComparatorType.BaseColor) && this.comparate(item, ComparatorType.Patterns) && this.comparate(item, ComparatorType.StoredEnchantements);
            }
            case Amount: {
                return this.getAmount() == item.getAmount();
            }
            case Durability: {
                return this.getDurability() == item.getDurability();
            }
            case Name: {
                return this.getName() == item.getName();
            }
            default: {
                return false;
            }
        }
    }

    public int getSlot() {
        return this.slot;
    }

    public ItemCreator setSlot(final int slot) {
        this.slot = slot;
        return this;
    }

    public enum BannerPreset {
        Bar,
        Previous,
        Next,
        Heart,
        Star,
        Cross,
        YinYang,
        Losange,
        Minus,
        Plus
    }

    public enum ComparatorType {
        All,
        ItemStack,
        Similar,
        Material,
        Amount,
        Durability,
        Name,
        Lores,
        Enchantements,
        ItemsFlags,
        Owner,
        BaseColor,
        Patterns,
        StoredEnchantements,
        Possesseur,
        Creator_Name,
        TAG
    }

    private class comparaison<type1, type2> {
        public Boolean islistequal(final List<type1> list1, final List<type1> list2) {
            if (list1 == null && list2 == null) {
                return true;
            }
            if (list1 == null || list2 == null) {
                return false;
            }
            return list1.size() == list2.size();
        }

        public Boolean ismapequal(final Map<type1, type2> map1, final Map<type1, type2> map2) {
            if (map1 == null && map2 == null) {
                return true;
            }
            if (map1 == null || map2 == null) {
                return false;
            }
            if (map1.size() == map2.size()) {
                for (final Map.Entry<type1, type2> e : map1.entrySet()) {
                    if (map2.get(e.getKey()) == null) {
                        return false;
                    }
                    if (map2.get(e.getKey()) != e.getValue()) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
}
