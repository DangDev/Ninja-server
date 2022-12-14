package com.hoiuc.server;

import com.hoiuc.assembly.*;
import com.hoiuc.io.Message;
import com.hoiuc.io.SQLManager;
import com.hoiuc.io.Util;
import com.hoiuc.stream.*;
import com.hoiuc.stream.thiendiabang.ThienDiaData;
import com.hoiuc.template.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    public void sendMessMenuNhiemVu(Player p, byte npcid, byte menuId, String str) throws IOException {
        NpcTemplate npc = (NpcTemplate)Manager.npcs.get(npcid);
        Message mss = new Message(39);
        DataOutputStream ds = mss.writer();
        ds.writeShort(npcid);
        ds.writeUTF(str);
        ds.writeByte(npc.menu[menuId].length);

        for(int i = 1; i < npc.menu[menuId].length; ++i) {
            ds.writeUTF(npc.menu[menuId][i]);
        }

        ds.flush();
        p.conn.sendMessage(mss);
        mss.cleanup();
    }

    public static void doMenuArray(Player p, String[] menu) {
        Message m = null;
        try {
            m = new Message(63);
            for(byte i = 0; i < menu.length; ++i) {
                m.writer().writeUTF(menu[i]);
            }
            m.writer().flush();
            p.conn.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }

    public static void sendWrite(Player p, short type, String title) {
        Message m = null;
        try {
            m = new Message(92);
            m.writer().writeUTF(title);
            m.writer().writeShort(type);
            m.writer().flush();
            p.conn.sendMessage(m);
            m.cleanup();
        } catch (IOException var5) {
            var5.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }

    public static void menuId(Player p, Message ms) {
        try {
            short npcId = ms.reader().readShort();
            ms.cleanup();
            p.c.typemenu = 0;
            p.typemenu = npcId;

            // tuy chinh cho custom NPC
            if (npcId == 33) {
                switch(Server.manager.event) {
                    case 1: {
                        Menu.doMenuArray(p, new String[]{"Di???u gi???y", "Di???u v???i"});
                        break;
                    }
                    case 2: {
                        Menu.doMenuArray(p, new String[]{"H???p b??nh th?????ng", "H???p b??nh th?????ng h???ng", "B??nh th???p c???m", "B??nh d???o", "B??nh ?????u xanh", "B??nh p??a"});
                        break;
                    }
                    case 3: {
                        Menu.doMenuArray(p, new String[]{"B??nh Chocolate", "B??nh d??u t??y", "?????i m???t n???", "?????i pet","BXH Di???t Boss TL", "H?????ng d???n"});
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            else if(npcId == 40) {
                switch (p.c.mapid) {
                    case 117: {
                        Menu.doMenuArray(p, new String[]{"R???i kh???i n??i n??y", "?????t c?????c", "H?????ng d???n"});
                        break;
                    }
                    case 118:
                    case 119: {
                        Menu.doMenuArray(p, new String[]{"R???i kh???i n??i n??y", "Th??ng tin"});
                        break;
                    }
                }
            }

             ms = new Message((byte)40);
            ms.writer().flush();
            p.conn.sendMessage(ms);
            ms.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ms != null) {
                ms.cleanup();
            }
        }
    }

    public static void menu(Player p, Message ms) {
        try {
            byte npcId = ms.reader().readByte();
            byte menuId = ms.reader().readByte();
            byte b3 = ms.reader().readByte();
            if (ms.reader().available() > 0) {
                byte var6 = ms.reader().readByte();
            }
            ms.cleanup();
            if ((p.typemenu == -1 || p.typemenu == 0) && p.typemenu != npcId) {
                switch(npcId) {
                    case 0:
                        Menu.npcKanata(p, npcId, menuId, b3);
                        break;
                    case 1:
                        Menu.npcFuroya(p, npcId, menuId, b3);
                        break;
                    case 2:
                        Menu.npcAmeji(p, npcId, menuId, b3);
                        break;
                    case 3:
                        Menu.npcKiriko(p, npcId, menuId, b3);
                        break;
                    case 4:
                        Menu.npcTabemono(p, npcId, menuId, b3);
                        break;
                    case 5:
                        Menu.npcKamakura(p, npcId, menuId, b3);
                        break;
                    case 6:
                        Menu.npcKenshinto(p, npcId, menuId, b3);
                        break;
                    case 7:
                        Menu.npcUmayaki_Lang(p, npcId, menuId, b3);
                        break;
                    case 8:
                        Menu.npcUmayaki_Truong(p, npcId, menuId, b3);
                        break;
                    case 9:
                        Menu.npcToyotomi(p, npcId, menuId, b3);
                        break;
                    case 10:
                        Menu.npcOokamesama(p, npcId, menuId, b3);
                        break;
                    case 11:
                        Menu.npcKazeto(p, npcId, menuId, b3);
                        break;
                    case 12:
                        Menu.npcTajima(p, npcId, menuId, b3);
                        break;
                    case 18:
                        Menu.npcRei(p, npcId, menuId, b3);
                        break;
                    case 19:
                        Menu.npcKirin(p, npcId, menuId, b3);
                        break;
                    case 20:
                        Menu.npcSoba(p, npcId, menuId, b3);
                        break;
                    case 21:
                        Menu.npcSunoo(p, npcId, menuId, b3);
                        break;
                    case 22:
                        Menu.npcGuriin(p, npcId, menuId, b3);
                        break;
                    case 23:
                        Menu.npcMatsurugi(p, npcId, menuId, b3);
                        break;
                    case 24:
                        Menu.npcOkanechan(p, npcId, menuId, b3);
                        break;
                    case 25:
                        Menu.npcRikudou(p, npcId, menuId, b3);
                        break;
                    case 26:
                        Menu.npcGoosho(p, npcId, menuId, b3);
                        break;
                    case 27:
                        Menu.npcTruCoQuan(p, npcId, menuId, b3);
                        break;
                    case 28:
                        Menu.npcShinwa(p, npcId, menuId, b3);
                        break;
                    case 29:
                        Menu.npcChiHang(p, npcId, menuId, b3);
                        break;
                    case 30:
                        Menu.npcRakkii(p, npcId, menuId, b3);
                        break;
                    case 31:
                        Menu.npcLongDen(p, npcId, menuId, b3);
                        break;
                    case 32:
                        Menu.npcKagai(p, npcId, menuId, b3);
                        break;
                    case 33:
                        Menu.npcTienNu(p, npcId, menuId, b3);
                        break;
                    case 34:
                        Menu.npcCayThong(p, npcId, menuId, b3);
                        break;
                    case 35:
                        Menu.npcOngGiaNoen(p, npcId, menuId, b3);
                        break;
                    case 36:
                        Menu.npcVuaHung(p, npcId, menuId, b3);
                        break;
                    case 37:
                        Menu.npcKanata_LoiDai(p, npcId, menuId, b3);
                        break;
                    case 38:
                        Menu.npcAdmin(p, npcId, menuId, b3);
                        break;
                    case 39: {
                        Menu.npcRikudou_ChienTruong(p, npcId, menuId, b3);
                        break;
                    }
                    case 40: {
                        Menu.npcKagai_GTC(p, npcId, menuId, b3);
                        break;
                    }
                    case 92:
                        p.typemenu = menuId == 0 ? 93 : 94;
                        Menu.doMenuArray(p, new String[]{"Th??ng tin", "Lu???t ch??i"});
                        break;
                    case 93:
                        if (menuId == 0) {
                            Server.manager.rotationluck[0].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "V??ng xoay vip", "Tham gia ??i, xem lu???t l??m g??");
                        }
                        break;
                    case 94:
                        if (menuId == 0) {
                            Server.manager.rotationluck[1].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "V??ng xoay th?????ng", "Tham gia ??i xem lu???t lm g??");
                        }
                    case 95:
                        break;
                    case 120: {
                        if (menuId > 0 && menuId < 7) {
                            Admission.Admission(p,menuId);
                        }
                    }
                    default: {
                        Service.chatNPC(p, (short) npcId, "Ta kh??ng c?? g?? ????? n??i v???i ng????i");
                        break;
                    }
                }
            }
            else if (p.typemenu == npcId) {
                switch(p.typemenu) {
                    case 0:
                        Menu.npcKanata(p, npcId, menuId, b3);
                        break;
                    case 1:
                        Menu.npcFuroya(p, npcId, menuId, b3);
                        break;
                    case 2:
                        Menu.npcAmeji(p, npcId, menuId, b3);
                        break;
                    case 3:
                        Menu.npcKiriko(p, npcId, menuId, b3);
                        break;
                    case 4:
                        Menu.npcTabemono(p, npcId, menuId, b3);
                        break;
                    case 5:
                        Menu.npcKamakura(p, npcId, menuId, b3);
                        break;
                    case 6:
                        Menu.npcKenshinto(p, npcId, menuId, b3);
                        break;
                    case 7:
                        Menu.npcUmayaki_Lang(p, npcId, menuId, b3);
                        break;
                    case 8:
                        Menu.npcUmayaki_Truong(p, npcId, menuId, b3);
                        break;
                    case 9:
                        Menu.npcToyotomi(p, npcId, menuId, b3);
                        break;
                    case 10:
                        Menu.npcOokamesama(p, npcId, menuId, b3);
                        break;
                    case 11:
                        Menu.npcKazeto(p, npcId, menuId, b3);
                        break;
                    case 12:
                        Menu.npcTajima(p, npcId, menuId, b3);
                        break;
                    case 18:
                        Menu.npcRei(p, npcId, menuId, b3);
                        break;
                    case 19:
                        Menu.npcKirin(p, npcId, menuId, b3);
                        break;
                    case 20:
                        Menu.npcSoba(p, npcId, menuId, b3);
                        break;
                    case 21:
                        Menu.npcSunoo(p, npcId, menuId, b3);
                        break;
                    case 22:
                        Menu.npcGuriin(p, npcId, menuId, b3);
                        break;
                    case 23:
                        Menu.npcMatsurugi(p, npcId, menuId, b3);
                        break;
                    case 24:
                        Menu.npcOkanechan(p, npcId, menuId, b3);
                        break;
                    case 25:
                        Menu.npcRikudou(p, npcId, menuId, b3);
                        break;
                    case 26:
                        Menu.npcGoosho(p, npcId, menuId, b3);
                        break;
                    case 27:
                        Menu.npcTruCoQuan(p, npcId, menuId, b3);
                        break;
                    case 28:
                        Menu.npcShinwa(p, npcId, menuId, b3);
                        break;
                    case 29:
                        Menu.npcChiHang(p, npcId, menuId, b3);
                        break;
                    case 30:
                        Menu.npcRakkii(p, npcId, menuId, b3);
                        break;
                    case 31:
                        Menu.npcLongDen(p, npcId, menuId, b3);
                        break;
                    case 32:
                        Menu.npcKagai(p, npcId, menuId, b3);
                        break;
                    case 33:
                        Menu.npcTienNu(p, npcId, menuId, b3);
                        break;
                    case 34:
                        Menu.npcCayThong(p, npcId, menuId, b3);
                        break;
                    case 35:
                        Menu.npcOngGiaNoen(p, npcId, menuId, b3);
                        break;
                    case 36:
                        Menu.npcVuaHung(p, npcId, menuId, b3);
                        break;
                    case 37:
                        Menu.npcKanata_LoiDai(p, npcId, menuId, b3);
                        break;
                    case 38:
                        Menu.npcAdmin(p, npcId, menuId, b3);
                        break;
                    case 39: {
                        Menu.npcRikudou_ChienTruong(p, npcId, menuId, b3);
                        break;
                    }
                    case 40: {
                        Menu.npcKagai_GTC(p, npcId, menuId, b3);
                        break;
                    }
                    case 92:
                        p.typemenu = menuId == 0 ? 93 : 94;
                        doMenuArray(p, new String[]{"Th??ng tin", "Lu???t ch??i"});
                        break;
                    case 93:
                        if (menuId == 0) {

                            Server.manager.rotationluck[0].luckMessage(p);
                        } else if (menuId == 1) {

                            Server.manager.sendTB(p, "V??ng xoay vip", "Tham gia ??i, xem lu???t l??m g??");
                        }
                        break;
                    case 94:
                        if (menuId == 0) {
                            Server.manager.rotationluck[1].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "V??ng xoay th?????ng", "Tham gia ??i xem lu???t lm g??");
                        }
                    case 95:
                        break;
                    case 120: {
                        if (menuId > 0 && menuId < 7) {
                            Admission.Admission(p,(byte)menuId);
                        }
                    }
                    default: {
                        Service.chatNPC(p, (short) npcId, "Ta kh??ng c?? g?? ????? n??i v???i ng????i");
                        break;
                    }
                }
            }
            else {
                switch(p.typemenu) {
                    case -125: {
                        Menu.doiGiayVun(p, npcId, menuId, b3);
                        break;
                    }
                    case 92: {
                        switch (menuId) {
                            case 0: {
                                Server.manager.rotationluck[0].luckMessage(p);
                                break;
                            }
                            case 1: {
                                Server.manager.rotationluck[1].luckMessage(p);
                                break;
                            }
                        }
                        break;
                    }
                    case 9999: {
                        Menu.menuAdmin(p, npcId, menuId, b3);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            p.typemenu = 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ms != null) {
                ms.cleanup();
            }
        }
    }

    public static void menuAdmin(Player p, byte npcid, byte menuId, byte b3) {
        Player player;
        int i;
        switch(menuId) {
            case 0: {
                Service.sendInputDialog(p, (short) 9998, "Nh???p s??? ph??t mu???n b???o tr?? 0->10 (0: ngay l???p t???c)");
                break;
            }
            case 1: {
                Service.KhoaTaiKhoan(p);
                break;
            }
            case 2: {
                Service.AutoSaveData();
                p.sendAddchatYellow("Update th??nh c??ng");
                break;
            }
            case 3: {
                String chat = "MapID: " + p.c.mapid + " - X: " + p.c.get().x + " - Y: " + p.c.get().y;
                p.conn.sendMessageLog(chat);
                break;
            }
            case 4: {
                Service.sendInputDialog(p, (short) 9996, "Nh???p s??? xu mu???n c???ng (c?? th??? nh???p s??? ??m)");
                break;
            }
            case 5: {
                Service.sendInputDialog(p, (short) 9995, "Nh???p s??? l?????ng mu???n c???ng (c?? th??? nh???p s??? ??m)");
                break;
            }
            case 6: {
                Service.sendInputDialog(p, (short) 9997, "Nh???p s??? y??n mu???n c???ng (c?? th??? nh???p s??? ??m)");
                break;
            }
            case 7: {
                Service.sendInputDialog(p, (short) 9994, "Nh???p s??? level mu???n t??ng (c?? th??? nh???p s??? ??m)");
                break;
            }
            case 8: {
                Service.sendInputDialog(p, (short) 9993, "Nh???p s??? ti???m n??ng mu???n t??ng (c?? th??? nh???p s??? ??m)");
                break;
            }
            case 9: {
                Service.sendInputDialog(p, (short) 9992, "Nh???p s??? k??? n??ng mu???n t??ng (c?? th??? nh???p s??? ??m)");
                break;
            }
            case 10: {
                SaveData saveData = new SaveData();
                saveData.player = p;
                Thread t1 = new Thread(saveData);
                t1.start();
                if (!Manager.isSaveData) {
                    player = null;
                    t1 = null;
                    saveData = null;
                }
                break;
            }
            case 11: {
                Service.sendInputDialog(p, (short) 9991, "Nh???p n???i dung");
                break;
            }
            case 12: {
                try {
                    Server.manager.sendTB(p, "Ki???m tra", "- T???ng s??? k???t n???i: " + Client.gI().conns_size() + "\n\n- S??? ng?????i ????ng nh???p: " + Client.gI().players_size() + "\n\n- T???NG S??? NG?????I CH??I TH???C T???: " + Client.gI().ninja_size());
                } catch (Exception var11) {
                    var11.printStackTrace();
                }
                break;
            }
            case 13: {
                synchronized (Client.gI().conns) {
                    for (i = 0; i < Client.gI().conns.size(); ++i) {
                        Session conn = (Session) Client.gI().conns.get(i);
                        if (conn != null) {
                            player = conn.player;
                            if (player != null) {
                                if (player.c == null) {
                                    Client.gI().kickSession(conn);
                                }
                            } else if (player == null) {
                                Client.gI().kickSession(conn);
                            }
                        }
                    }
                }

                p.conn.sendMessageLog("D???n clone th??nh c??ng!");
                break;
            }
            case 14: {
                synchronized (Client.gI().conns) {
                    for (i = 0; i < Client.gI().conns.size(); ++i) {
                        player = ((Session) Client.gI().conns.get(i)).player;
                        if (player != null && player != p) {
                            Client.gI().kickSession(player.conn);
                        }
                    }
                }

                p.conn.sendMessageLog("D???n Session th??nh c??ng!");
                break;
            }
            case 15: {
                Service.sendInputDialog(p, (short) 9990, "Nh???p gi?? tr??? c???n thay ?????i");
                break;
            }
            case 16: {
                try {
                    String a = "";
                    int i2 = 1;
                    for (CheckRHB check: CheckRHB.checkRHBArrayList) {
                        a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                        i2++;
                    }
                    Server.manager.sendTB(p, "Check RHB", a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 17: {
               try {
                   ResultSet red = SQLManager.stat.executeQuery("SELECT * FROM `alert` WHERE `id` = 1;");
                   if (red != null && red.first()) {
                       String alert = red.getString("content");
                       Manager.alert.setAlert(alert);
                       red.close();
                   }
                   p.sendAddchatYellow("C???p nh???t th??ng b??o th??nh c??ng");
                   Manager.alert.sendAlert(p);
               } catch (Exception e) {
                   p.conn.sendMessageLog("L???i c???p nh???t!");
               }
               break;
            }
            case 18: {
                try {
                    Manager.chatKTG("Ng?????i ch??i " + p.c.name + " s??? d???ng B??nh kh??c c??y d??u t??y ???? nh???n ???????c " + ItemTemplate.ItemTemplateId(385).name);
                } catch (Exception e) {
                    p.conn.sendMessageLog("L???i c???p nh???t!");
                }
                break;
            }
            case 19: {
                try {
                    Manager.chatKTG("Ng?????i ch??i " + p.c.name + " s??? d???ng B??nh kh??c c??y d??u t??y ???? nh???n ???????c " + ItemTemplate.ItemTemplateId(384).name);
                } catch (Exception e) {
                    p.conn.sendMessageLog("L???i c???p nh???t!");
                }
                break;
            }
        }

    }

    public static void doiGiayVun(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 250);
                p.c.addItemBag(false, ItemTemplate.itemDefault(252, false));
                break;
            }
            case 1: {
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 300);
                p.c.addItemBag(false, ItemTemplate.itemDefault(253, false));
                break;
            }
        }

    }

    public static void npcKanata(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.requestItem(2);
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (!p.c.clan.clanName.isEmpty()) {
                            Service.chatNPC(p, (short) npcid, "Hi???n t???i con ???? c?? gia t???c, kh??ng th??? th??nh l???p gia t???c ???????c n???a.");
                            return;
                        }

                        if (p.luong < 100000) {
                            Service.chatNPC(p, (short) npcid, "????? th??nh l???p gia t???c, con ph???i c?? ??t nh???t 100.000 l?????ng trong ng?????i.");
                            return;
                        }
                        Menu.sendWrite(p, (short) 50, "T??n gia t???c");
                        return;
                    }
                    case 1: {
                        if (p.c.clan.clanName.isEmpty()) {
                            Service.chatNPC(p, (short) npcid, "Hi???n t???i con ch??a c?? gia t???c, kh??ng th??? m??? L??nh ?????a gia t???c.");
                            return;
                        }

                        LanhDiaGiaToc lanhDiaGiaToc = null;
                        if (p.c.ldgtID != -1) {
                            if (LanhDiaGiaToc.ldgts.containsKey(p.c.ldgtID)) {
                                lanhDiaGiaToc = LanhDiaGiaToc.ldgts.get(p.c.ldgtID);
                                if (lanhDiaGiaToc != null && lanhDiaGiaToc.map[0] != null && lanhDiaGiaToc.map[0].area[0] != null) {
                                    if(lanhDiaGiaToc.ninjas.size() <= 24) {
                                        p.c.mapKanata = p.c.mapid;
                                        p.c.tileMap.leave(p);
                                        lanhDiaGiaToc.map[0].area[0].EnterMap0(p.c);
                                        return;
                                    } else {
                                        p.sendAddchatYellow("S??? th??nh vi??n tham gia L??nh ?????a Gia T???c ???? ?????t t???i ??a.");
                                    }
                                }
                            }
                        }
                        if(lanhDiaGiaToc == null){
                            if(p.c.clan.typeclan < 3) {
                                Service.chatNPC(p, (short) npcid, "Con kh??ng ph???i t???c tr?????ng ho???c t???c ph??, kh??ng th??? m??? L??nh ?????a gia t???c.");
                                return;
                            }
                            if(p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng ????? ch??? tr???ng ????? nh???n Ch??a kho?? LDGT");
                                return;
                            }
                            ClanManager clan = ClanManager.getClanName(p.c.clan.clanName);
                            if (clan != null && p.c.clan.typeclan >= 3) {
                                if(clan.openDun <= 0) {
                                    Service.chatNPC(p, (short) npcid, "S??? l???n v??o LDGT tu???n n??y ???? h???t.");
                                    return;
                                }
                                if(clan.ldgtID != -1) {
                                    Service.chatNPC(p, (short) npcid, "L??nh ?????a gia t???c c???a con ??ang ???????c m??? r???i.");
                                    return;
                                }
                                clan.openDun--;
                                clan.flush();
                                lanhDiaGiaToc = new LanhDiaGiaToc();

                                Item itemup = ItemTemplate.itemDefault(260);
                                itemup.quantity = 1;
                                itemup.expires = System.currentTimeMillis() + 600000L;
                                itemup.isExpires = true;
                                itemup.isLock = true;
                                if(p.c.quantityItemyTotal(260) > 0) {
                                    p.c.removeItemBags(260, p.c.quantityItemyTotal(260));
                                }
                                p.c.addItemBag(false, itemup);
                                p.c.ldgtID = lanhDiaGiaToc.ldgtID;
                                clan.ldgtID = lanhDiaGiaToc.ldgtID;
                                lanhDiaGiaToc.clanManager = clan;
                                p.c.mapKanata = p.c.mapid;
                                p.c.tileMap.leave(p);
                                lanhDiaGiaToc.map[0].area[0].EnterMap0(p.c);
                                return;
                            }

                        }
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog("Ch???c n??ng n??y kh??ng d??nh cho ph??n th??n");
                            return;
                        }
                        if(p.c.quantityItemyTotal(262) < 500) {
                            Service.chatNPC(p, (short) npcid, "Con c???n c?? 500 ?????ng ti???n gia t???c ????? ?????i l???y T??i qu?? gia t???c.");
                            return;
                        }
                        if(p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        p.c.removeItemBags(262, 500);
                        Item itemup = ItemTemplate.itemDefault(263);
                        itemup.quantity = 1;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3:
                    default: {
                        Service.chatNPC(p, (short) npcid, "Ra ch??? kh??c ch??i");
                        break;
                    }
                }
                break;
            }
            case 2: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog("Ch???c n??ng n??y kh??ng d??nh cho ph??n th??n");
                    return;
                }

                if (b3 == 0) {
                    Service.evaluateCave(p.c);
                    return;
                }

                Cave cave = null;
                if (p.c.caveID != -1) {
                    if (Cave.caves.containsKey(p.c.caveID)) {
                        cave = Cave.caves.get(p.c.caveID);
                        if (cave != null && cave.map[0] != null && cave.map[0].area[0] != null) {
                            p.c.mapKanata = p.c.mapid;
                            p.c.tileMap.leave(p);
                            cave.map[0].area[0].EnterMap0(p.c);
                        }
                    }
                } else if (p.c.party != null && p.c.party.cave == null && p.c.party.charID != p.c.id) {
                    p.conn.sendMessageLog("Ch??? c?? nh??m tr?????ng m???i ???????c ph??p m??? c???a hang ?????ng");
                    return;
                }

                if (cave == null) {
                    if (p.c.nCave <= 0) {
                        Service.chatNPC(p, (short) npcid, "S??? l???n v??o hang ?????ng c???a con h??m nay ???? h???t, h??y quay l???i v??o ng??y mai.");
                        return;
                    }
                    if (b3 == 1) {
                        if (p.c.level < 30 || p.c.level > 39) {
                            p.conn.sendMessageLog("Tr??nh ????? kh??ng ph?? h???p");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 30 || p.c.party.aChar.get(i).level > 39) {
                                        p.conn.sendMessageLog("Th??nh vi??n trong nh??m c?? tr??nh ????? kh??ng ph?? h???p");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(3);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(3);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 1;
                    }
                    if (b3 == 2) {
                        if (p.c.level < 40 || p.c.level > 49) {
                            p.conn.sendMessageLog("Tr??nh ????? kh??ng ph?? h???p");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 40 || p.c.party.aChar.get(i).level > 49) {
                                        p.conn.sendMessageLog("Th??nh vi??n trong nh??m c?? tr??nh ????? kh??ng ph?? h???p");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(4);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(4);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 3) {
                        if (p.c.level < 50 || p.c.level > 59) {
                            p.conn.sendMessageLog("Tr??nh ????? kh??ng ph?? h???p");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 50 || p.c.party.aChar.get(i).level > 59) {
                                        p.conn.sendMessageLog("Th??nh vi??n trong nh??m c?? tr??nh ????? kh??ng ph?? h???p");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(5);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(5);
                        }
                        p.c.caveID = cave.caveID;
                    }
                    if (b3 == 4) {
                        if (p.c.level < 60 || p.c.level > 69) {
                            p.conn.sendMessageLog("Tr??nh ????? kh??ng ph?? h???p");
                            return;
                        }
                        if (p.c.party != null && p.c.party.aChar.size() > 1) {
                            p.conn.sendMessageLog("Ho???t ?????ng n??y ch??? ???????c ph??p 1 m??nh.");
                            return;
                        }
                        cave = new Cave(6);
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 1;
                    }
                    if (b3 == 5) {
                        if (p.c.level < 70 || p.c.level > 89) {
                            p.conn.sendMessageLog("Tr??nh ????? kh??ng ph?? h???p");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 70 || p.c.party.aChar.get(i).level > 89) {
                                        p.conn.sendMessageLog("Th??nh vi??n trong nh??m c?? tr??nh ????? kh??ng ph?? h???p");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(7);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(7);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 6) {
                        if (p.c.level < 90 || p.c.level > 130) {
                            p.conn.sendMessageLog("Tr??nh ????? kh??ng ph?? h???p");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 90 || p.c.party.aChar.get(i).level > 131) {
                                        p.conn.sendMessageLog("Th??nh vi??n trong nh??m c?? tr??nh ????? kh??ng ph?? h???p");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(9);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(9);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }

                    if (cave != null) {
                        p.c.nCave--;
                        p.c.pointCave = 0;

                        if (p.c.party != null && p.c.party.charID == p.c.id) {
                            if(p.c.party.aChar != null && p.c.party.aChar.size() > 0) {
                                synchronized (p.c.party.aChar) {
                                    Char _char;
                                    for (int i = 0; i < p.c.party.aChar.size(); i++) {
                                        if(p.c.party.aChar.get(i) != null) {
                                            _char = p.c.party.aChar.get(i);
                                            if (_char.id != p.c.id && p.c.tileMap.getNinja(_char.id) != null && _char.nCave > 0 && _char.caveID == -1 && _char.level >= 30 && (int) _char.level / 10 == cave.x) {
                                                _char.nCave--;
                                                _char.pointCave = 0;
                                                _char.caveID = p.c.caveID;
                                                _char.isHangDong6x = p.c.isHangDong6x;
                                                _char.mapKanata = _char.mapid;
                                                _char.countHangDong++;
                                                if (_char.pointUydanh < 5000) {
                                                    _char.pointUydanh += 5;
                                                }
                                                _char.tileMap.leave(_char.p);
                                                cave.map[0].area[0].EnterMap0(_char);
                                                _char.p.setPointPB(_char.pointCave);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        p.c.mapKanata = p.c.mapid;
                        p.c.countHangDong++;
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 5;
                        }
                        p.c.tileMap.leave(p);
                        cave.map[0].area[0].EnterMap0(p.c);
                    }
                }
                p.setPointPB(p.c.pointCave);
                break;
            }
            case 3: {
//                Service.chatNPC(p, (short) npcid, "Ch???c n??ng ??ang b???o tr??, vui l??ng quay l???i sau!");
//                return;
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.party != null && p.c.party.charID != p.c.id) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng ph???i tr?????ng nh??m, kh??ng th??? th???c hi???n g???i l???i m???i l??i ????i cho ng?????i/nh??m kh??c");
                            return;
                        }

                        Service.sendInputDialog(p, (short) 2, "Nh???p t??n ?????i th??? c???a con");
                        return;
                    }
                    case 1: {
                        Service.sendLoiDaiList(p.c);
                        return;
                    }
                    case 2: {
                        String alert = "";

                        for (int i = 0; i < DunListWin.dunList.size(); ++i) {
                            int temp = i + 1;
                            alert = alert + temp + ". Phe " + ((DunListWin) DunListWin.dunList.get(i)).win + " th???ng Phe " + ((DunListWin) DunListWin.dunList.get(i)).lose + ".\n";
                        }
                        Server.manager.sendTB(p, "K???t qu???", alert);
                        return;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                Service.chatNPC(p, (short) npcid, "V?? kh?? c???a ta c???c s???c b??n. N???u mu???n t??? th?? th?? c??? ?????n ch??? ta!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ra ch??? kh??c ch??i con! Ta ??ang xem Anime");
                break;
            }
        }
    }

    public static void npcFuroya(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                switch(b3) {
                    case 0:
                        p.requestItem(21 - p.c.gender);
                        return;
                    case 1:
                        p.requestItem(23 - p.c.gender);
                        return;
                    case 2:
                        p.requestItem(25 - p.c.gender);
                        return;
                    case 3:
                        p.requestItem(27 - p.c.gender);
                        return;
                    case 4:
                        p.requestItem(29 - p.c.gender);
                        return;
                    default:
                        Service.chatNPC(p, (short)npcid, "Su???t! N??i chuy???n phi???m th?? ?????ng t???i t??m ta");
                        return;
                }
            case 1:
                Service.chatNPC(p, (short)npcid, "Tan b??n qu???n ??o, m?? n??n, g??ng tay v?? gi??y si??u b???n, si??u r???!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Su???t! N??i chuy???n phi???m th?? ?????ng t???i t??m ta");
                break;
            }
        }

    }

    public static void npcAmeji(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        p.requestItem(16);
                        break;
                    }
                    case 1: {
                        p.requestItem(17);
                        break;
                    }
                    case 2: {
                        p.requestItem(18);
                        break;
                    }
                    case 3: {
                        p.requestItem(19);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 1: {
                ItemTemplate data;
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level < 50) {
                            Service.chatNPC(p, (short) npcid, "C???p ????? c???a con kh??ng ????? ????? nh???n nhi???m v??? n??y");
                            return;
                        }

                        if (p.c.countTaskDanhVong < 1) {
                            Service.chatNPC(p, (short) npcid, "S??? l???n nh???n nhi???m v??? c???a con h??m nay ???? h???t! Quay l???i ng??y mai nha!");
                            return;
                        }

                        if (p.c.isTaskDanhVong == 1) {
                            Service.chatNPC(p, (short) npcid, "Tr?????c ???? con ???? nh???n nhi???m v??? r???i, h??y ho??n th??nh ???? nha");
                            return;
                        }

                        int type = DanhVongTemplate.randomNVDV();
                        p.c.taskDanhVong[0] = type;
                        p.c.taskDanhVong[1] = 0;
                        p.c.taskDanhVong[2] = DanhVongTemplate.targetTask(type);
                        p.c.isTaskDanhVong = 1;
                        p.c.countTaskDanhVong--;
                        if (p.c.isTaskDanhVong == 1) {
                            String nv = "NHI???M V??? L???N N??Y: \n" +
                                    String.format(DanhVongTemplate.nameNV[p.c.taskDanhVong[0]],
                                            p.c.taskDanhVong[1],
                                            p.c.taskDanhVong[2])
                                    + "\n\n- S??? l???n nh???n nhi???m v??? c??n l???i l??: " + p.c.countTaskDanhVong;
                            Server.manager.sendTB(p, "Nhi???m v???", nv);
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskDanhVong == 0) {
                            Service.chatNPC(p, (short) npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                            return;
                        }

                        if (p.c.taskDanhVong[1] < p.c.taskDanhVong[2]) {
                            Service.chatNPC(p, (short) npcid, "Con ch??a ho??n th??nh nhi???m v??? ta giao!");
                            return;
                        }

                        if (p.c.getBagNull() < 2) {
                            Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng ????? ch??? tr???ng ????? nh???n th?????ng");
                            return;
                        }

                        int point = 3;
                        if (p.c.taskDanhVong[0] == 9) {
                            point = 5;
                        }

                        p.c.isTaskDanhVong = 0;
                        p.c.taskDanhVong = new int[]{-1, -1, -1, 0, p.c.countTaskDanhVong};
                        Item item = ItemTemplate.itemDefault(DanhVongTemplate.randomDaDanhVong(), false);
                        item.quantity = 1;
                        item.isLock = false;
                        if (p.c.pointUydanh < 5000) {
                            ++p.c.pointUydanh;
                        }

                        p.c.addItemBag(true, item);
                        int type = Util.nextInt(10);

                        if (p.c.avgPointDanhVong(p.c.getPointDanhVong(type))) {
                            for (int i = 0; i < 10; i++) {
                                type = i;
                                if (!p.c.avgPointDanhVong(p.c.getPointDanhVong(type))) {
                                    break;
                                }
                            }
                        }
                        p.c.plusPointDanhVong(type, point);

                        if(p.c.countTaskDanhVong % 2 == 0) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 739 : 766, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(1,2);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 740 : 767, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(1,2);
                            p.c.addItemBag(true, itemUp);
                        }

                        Service.chatNPC(p, (short) npcid, "Con h??y nh???n l???y ph???n th?????ng c???a m??nh.");
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskDanhVong == 0) {
                            Service.chatNPC(p, (short) npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                            return;
                        }

                        Service.startYesNoDlg(p, (byte) 2, "Con c?? ch???c ch???n mu???n hu??? nhi???m v??? l???n n??y kh??ng?");
                        break;
                    }
                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.checkPointDanhVong(1)) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng ????? ch??? tr???ng ????? nh???n th?????ng");
                                return;
                            }

                            Item item = ItemTemplate.itemDefault(685, true);
                            item.quantity = 1;
                            item.upgrade = 1;
                            item.isLock = true;
                            Option op = new Option(6, 1000);
                            item.options.add(op);
                            op = new Option(87, 500);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                        } else {
                            Service.chatNPC(p, (short) npcid, "Con ch??a ????? ??i???m ????? nh???n m???t");
                        }

                        break;
                    }
                    case 4: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.ItemBody[14] == null) {
                            Service.chatNPC(p, (short) npcid, "H??y ??eo m???t v??o ng?????i tr?????c r???i n??ng c???p nh??.");
                            return;
                        }

                        if (p.c.ItemBody[14] == null) {
                            return;
                        }

                        if (p.c.ItemBody[14].upgrade >= 10) {
                            Service.chatNPC(p, (short) npcid, "M???t c???a con ???? ?????t c???p t???i ??a");
                            return;
                        }

                        if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                            Service.chatNPC(p, (short) npcid, "Con ch??a ????? ??i???m danh v???ng ????? th???c hi???n n??ng c???p");
                            return;
                        }

                        data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                        Service.startYesNoDlg(p, (byte) 0, "B???n c?? mu???n n??ng c???p " + data.name + " v???i " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " y??n ho???c xu v???i t??? l??? th??nh c??ng l?? " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] + "% kh??ng?");
                        break;
                    }
                    case 5: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.ItemBody[14] == null) {
                            Service.chatNPC(p, (short) npcid, "H??y ??eo m???t v??o ng?????i tr?????c r???i n??ng c???p nh??.");
                            return;
                        }

                        if (p.c.ItemBody[14].upgrade >= 10) {
                            Service.chatNPC(p, (short) npcid, "M???t c???a con ???? ?????t c???p t???i ??a");
                            return;
                        }

                        if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                            Service.chatNPC(p, (short) npcid, "Con ch??a ????? ??i???m danh v???ng ????? th???c hi???n n??ng c???p");
                            return;
                        }

                        data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                        Service.startYesNoDlg(p, (byte) 1, "B???n c?? mu???n n??ng c???p " + data.name + " v???i " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " y??n ho???c xu v?? " + GameSrc.goldUpMat[p.c.ItemBody[14].upgrade] + " l?????ng v???i t??? l??? th??nh c??ng l?? " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] * 2 + "% kh??ng?");
                        break;
                    }
                    case 6: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        String nv = "- Ho??n th??nh nhi???m v???. H??y g???p Ameji ????? tr??? nhi???m v???.\n- H??m nay c?? th??? nh???n th??m " + p.c.countTaskDanhVong + " nhi???m v??? trong ng??y.\n- H??m nay c?? th??? s??? d???ng th??m " + p.c.useDanhVongPhu + " Danh V???ng Ph?? ????? nh???n th??m 5 l???n l??m nhi???m v???.\n- Ho??n th??nh nhi???m v??? s??? nh???n ng???u nhi??n 1 vi??n ???? danh v???ng c???p 1-10.\n- Khi ????? m???c 100 ??i???m m???i lo???i c?? th??? nh???n m???t v?? n??ng c???p m???t.";
                        if (p.c.isTaskDanhVong == 1) {
                            nv = "NHI???M V??? L???N N??Y: \n" + String.format(DanhVongTemplate.nameNV[p.c.taskDanhVong[0]], p.c.taskDanhVong[1], p.c.taskDanhVong[2]) + "\n\n" + nv;
                        }

                        Server.manager.sendTB(p, "Nhi???m v???", nv);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 2: {
                Service.chatNPC(p, (short) npcid, "Tan b??n c??c lo???i trang s???c l???p l??nh!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Kh??ng gi??n kh??ng gi??n! H???? Con h???i g?? ta?");
                break;
            }
        }

    }

    public static void npcKiriko(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(7);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(6);
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch???a b???nh c???u ng?????i l?? tr??ch nhi???m c???a ta! Mong con c??ng c?? t???m l??ng nh?? ta!");
                break;
            }
        }

    }

    public static void npcTabemono(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                p.requestItem(9);
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(8);
                break;
            case 2: {
                Service.chatNPC(p, (short) npcid, "3 ?????i nh?? ta b??n th???c ??n ch??a ai b??? ??au b???ng c???! C??n con ta kh??ng bi???t :<");
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                switch (b3) {
                    case 0: {
                        if (!ThienDiaBangManager.register) {
                            Service.chatNPC(p, (short) npcid, "??ang trong th???i gian t???ng k???t. Hi???n t???i kh??ng th??? ????ng k??.");
                            return;
                        }
                        if (ThienDiaBangManager.diaBangList.containsKey(p.c.name) || ThienDiaBangManager.thienBangList.containsKey(p.c.name)) {
                            Service.chatNPC(p, (short) npcid, "Con ???? ????ng k?? tr?????c ???? r???i");
                            return;
                        }
                        if (p.c.get().level >= 50 && p.c.get().level < 70) {
                            ThienDiaBangManager.diaBangList.put(p.c.name, new ThienDiaData(p.c.name, ThienDiaBangManager.rankDiaBang++, 1));
                            Service.chatNPC(p, (short) npcid, "Con ???? ????ng k?? tham gia trang t??i ?????a b???ng th??nh c??ng.");
                        } else if (p.c.get().level >= 70) {
                            ThienDiaBangManager.thienBangList.put(p.c.name, new ThienDiaData(p.c.name, ThienDiaBangManager.rankThienBang++, 1));
                            Service.chatNPC(p, (short) npcid, "Con ???? ????ng k?? tham gia tranh t??i Thi??n b???ng th??nh c??ng.");
                        } else {
                            Service.chatNPC(p, (short) npcid, "Tr??nh ????? c???a con kh??ng ph?? h???p ????? ????ng k?? tham gia tranh t??i.");
                        }
                        break;
                    }
                    case 1: {
                        if (!ThienDiaBangManager.register) {
                            Service.chatNPC(p, (short) npcid, "??ang trong th???i gian t???ng k???t. Hi???n t???i kh??ng th??? thi ?????u.");
                            return;
                        }
                        ArrayList<ThienDiaData> list = new ArrayList<>();
                        if (ThienDiaBangManager.diaBangList.containsKey(p.c.name)) {
                            ThienDiaData rank = ThienDiaBangManager.diaBangList.get(p.c.name);
                            for (ThienDiaData data : ThienDiaBangManager.getListDiaBang()) {
                                if (data != null) {
                                    if (rank.getRank() < 10 && (data.getRank() - rank.getRank()) < 20) {
                                        list.add(data);
                                    } else if (data.getRank() < rank.getRank() & (rank.getRank() - data.getRank()) < 10) {
                                        list.add(data);
                                    }
                                }
                            }
                        } else if (ThienDiaBangManager.thienBangList.containsKey(p.c.name)) {
                            ThienDiaData rank = ThienDiaBangManager.thienBangList.get(p.c.name);
                            for (ThienDiaData data : ThienDiaBangManager.getListThienBang()) {
                                if (data != null) {
                                    if (rank.getRank() < 10 && (data.getRank() - rank.getRank()) < 20) {
                                        list.add(data);
                                    } else if (data.getRank() <= rank.getRank() & (rank.getRank() - data.getRank()) < 10) {
                                        list.add(data);
                                    }
                                }
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Con ch??a ????ng k?? tham gia thi ?????u.");
                            return;
                        }
                        Service.SendChinhPhuc(p, list);
                        return;
                    }
                    case 2: {
                        String res = "";
                        int count = 1;
                        for (ThienDiaData data : ThienDiaBangManager.getListSortAsc(new ArrayList<ThienDiaData>(ThienDiaBangManager.thienBangList.values()))) {
                            if (count < 11) {
                                res += "H???ng " + count + ": " + data.getName() + ".\n";
                                count++;
                            }
                        }
                        Server.manager.sendTB(p, "Thi??n b???ng", res);
                        return;
                    }
                    case 3: {
                        String res = "";
                        int count = 1;
                        for (ThienDiaData data : ThienDiaBangManager.getListSortAsc(new ArrayList<ThienDiaData>(ThienDiaBangManager.diaBangList.values()))) {
                            if (count < 11) {
                                res += "H???ng " + count + ": " + data.getName() + ".\n";
                                count++;
                            }
                        }
                        Server.manager.sendTB(p, "?????a b???ng", res);
                        return;
                    }
                    case 4: {
                        ResultSet res = null;
                        try {
                            if(p.c.rankTDB > 0) {
                                if(p.c.isGiftTDB == 1) {
                                    if(p.c.rankTDB > 20) {
                                        p.upluongMessage(500);
                                        p.c.upxuMessage(500000);
                                    } else {
                                        switch (p.c.rankTDB) {
                                            case 1: {
                                                if(p.c.getBagNull() < 10) {
                                                    Service.chatNPC(p, (short) npcid, "Con c???n ??t nh???t 10 ch??? tr???ng trong h??nh trang ????? nh???n th?????ng.");
                                                    return;
                                                }
                                                Item pl = ItemTemplate.itemDefault(308,false);
                                                pl.quantity = 2;
                                                p.c.addItemBag(true,pl);

                                                pl = ItemTemplate.itemDefault(309,false);
                                                pl.quantity = 2;
                                                p.c.addItemBag(true,pl);

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(384,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));

                                                p.upluongMessage(20000);
                                                p.c.upxuMessage(20000000);
                                                break;
                                            }
                                            case 2: {
                                                if(p.c.getBagNull() < 7) {
                                                    Service.chatNPC(p, (short) npcid, "Con c???n ??t nh???t 7 ch??? tr???ng trong h??nh trang ????? nh???n th?????ng.");
                                                    return;
                                                }
                                                Item pl = ItemTemplate.itemDefault(308,false);
                                                pl.quantity = 1;
                                                p.c.addItemBag(true,pl);

                                                pl = ItemTemplate.itemDefault(309,false);
                                                pl.quantity = 1;
                                                p.c.addItemBag(true,pl);

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(384,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));

                                                p.upluongMessage(10000);
                                                p.c.upxuMessage(10000000);
                                                break;
                                            }
                                            case 3: {
                                                if(p.c.getBagNull() < 4) {
                                                    Service.chatNPC(p, (short) npcid, "Con c???n ??t nh???t 4 ch??? tr???ng trong h??nh trang ????? nh???n th?????ng.");
                                                    return;
                                                }
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.upluongMessage(5000);
                                                p.c.upxuMessage(5000000);
                                                break;
                                            }
                                            case 4:
                                            case 5:
                                            case 6:
                                            case 7:
                                            case 8:
                                            case 9:
                                            case 10: {
                                                if(p.c.getBagNull() < 4) {
                                                    Service.chatNPC(p, (short) npcid, "Con c???n ??t nh???t 2 ch??? tr???ng trong h??nh trang ????? nh???n th?????ng.");
                                                    return;
                                                }
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.upluongMessage(3000);
                                                p.c.upxuMessage(3000000);
                                                break;
                                            }
                                            case 11:
                                            case 12:
                                            case 13:
                                            case 14:
                                            case 15:
                                            case 16:
                                            case 17:
                                            case 18:
                                            case 19:
                                            case 20: {
                                                p.upluongMessage(1000);
                                                p.c.upxuMessage(1000000);
                                                break;
                                            }

                                        }
                                    }
                                    p.c.isGiftTDB = 0;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Con ???? nh???n th?????ng tu???n r???i.");
                                    return;
                                }
                            } else {
                                Service.chatNPC(p, (short) npcid, "Tu???n tr?????c con ch??a tham gia Thi??n ?????a b???ng v?? ch??a c?? rank, con ch??a th??? nh???n th?????ng.");
                                return;
                            }
                        } catch (Exception e) {
                            p.conn.sendMessageLog("L???i nh???n th?????ng, vui l??ng th??? l???i sau!");
                            return;
                        } finally {
                            if(res != null) {
                                try {
                                    res.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    }
                    case 5: {
                        Server.manager.sendTB(p, "H?????ng d???n", "- Thi??n ?????a B???ng s??? ???????c m??? h??ng tu???n. B???t ?????u t??? th??? 2 v?? t???ng k???t v??o ch??? nh???t.\n" +
                                "- Thi??n ?????a B???ng s??? ???????c m??? ????ng k?? v?? ch??nh ph???c t??? 00h05' ?????n 23h45' h??ng ng??y. M???i ng??y s??? c?? 20p ????? t???ng k???t ng??y, trong th???i gian n??y s??? kh??ng th??? ????ng k?? v?? chinh ph???c\n" +
                                "- Trong th???i gian t???ng k???t n???u chi???n th???ng trong Chinh ph???c s??? kh??ng ???????c t??nh rank." +
                                "- V??o ng??y th?????ng s??? kh??ng gi???i h???n l?????t th??ch ?????u.\n" +
                                "- V??o Th??? 7 v?? Ch??? Nh???t m???i Ninja s??? c?? 5 l?????t th??ch ?????u, Th???ng s??? kh??ng b??? m???t l?????t, thua s??? b??? tr??? 1 l???n th??ch ?????u." +
                                "- ?????a B???ng d??nh cho ninja t??? c???p ????? 50-69.\n" +
                                "- Thi??n B???ng d??nh cho ninja t??? c???p ????? tr??n 70\n" +
                                "- Sau khi ????ng k?? th??nh c??ng, h??y Chinh Ph???c ngay ????? gi??nh l???y v??? tr?? top ?????u.\n" +
                                "- M???i l???n chi???n th???ng, n???u v??? tr?? c???a ?????i th??? tr?????c b???n, b???n s??? ?????i v??? tr?? c???a m??nh cho ?????i th???, c??n kh??ng v??? tr?? c???a b???n s??? ???????c gi??? nguy??n.\n" +
                                "- Ph???n th?????ng s??? ???????c tr??? th?????ng v??o m???i tu???n m???i (L??u ??: H??y nh???n th?????ng ngay trong tu???n m???i ????, n???u sang tu???n sau ph???n th?????ng s??? b??? reset).\n\n" +
                                "- PH???N TH?????NG: \n" +
                                "Top 1: H??o quang Rank 1 + 2 B??nh Phong L??i, 2 B??nh B??ng Ho???, 2 N???m x4, 3 N???m x3, 1 R????ng b???ch ng??n, 2 B??t b???o, 20,000 L?????ng, 20,000,000 xu.\n\n" +
                                "Top 2: H??o quang Rank 2 + 1 B??nh Phong L??i, 1 B??nh B??ng Ho???, 1 N???m x4, 2 N???m x3, 1 R????ng b???ch ng??n, 1 B??t b???o, 10,000 L?????ng, 10,000,000 xu.\n\n" +
                                "Top 3: H??o quang Rank 3 + 1 N???m x4, 1 N???m x3, 2 B??t b???o, 5,000 L?????ng, 5,000,000 xu.\n\n" +
                                "Top 4-10: 1 N???m x3, 1 B??t b???o, 3,000 L?????ng, 3,000,000 xu.\n\n" +
                                "Top 11-20: 1,000 L?????ng, 1,000,000 xu.\n\n" +
                                "C??n l???i: 500 L?????ng, 500,000 xu.");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "M???t 5s cu???c ?????i c???a con r???i ????! ??i l??m vi???c c???a m??nh ??i!");
                break;
            }
        }

    }

    public static void npcKamakura(Player p, byte npcid, byte menuId, byte b3) {
        try {
            if (p.c.isNhanban) {
                p.conn.sendMessageLog("Ch???c n??ng n??y kh??ng d??nh cho ph??n th??n.");
                return;
            }
            switch(menuId) {
                case 0:
                    //p.requestItem(4);
                    switch (b3) {
                        case 0: {
                            Service.openMenuBox(p);
                            break;
                        }
                        case 1: {
                            Service.openMenuBST(p);
                            break;
                        }
                        case 2: {
                            Service.openMenuCaiTrang(p);
                            break;
                        }
                        case 3: {
                            //Th??o c???i trang
                            p.c.caiTrang = -1;
                            Message m = new Message(11);
                            m.writer().writeByte(-1);
                            m.writer().writeByte(p.c.get().speed());
                            m.writer().writeInt(p.c.get().getMaxHP());
                            m.writer().writeInt(p.c.get().getMaxMP());
                            m.writer().writeShort(p.c.get().eff5buffHP());
                            m.writer().writeShort(p.c.get().eff5buffMP());
                            m.writer().flush();
                            p.conn.sendMessage(m);
                            m.cleanup();
                            Service.CharViewInfo(p, false);
                            p.endLoad(true);
                            break;
                        }
                    }
                    break;
                case 1:
                    if(p.c.tileMap.map.getXHD() != -1 || p.c.tileMap.map.LangCo() || p.c.tileMap.map.mapBossTuanLoc() || p.c.tileMap.map.mapLDGT() || p.c.tileMap.map.mapGTC() || p.c.tileMap.map.id == 111 || p.c.tileMap.map.id == 113) {
                        p.c.mapLTD = 22;
                    } else {
                        p.c.mapLTD = p.c.tileMap.map.id;
                    }
                    Service.chatNPC(p, (short)npcid, "L??u to??? ????? th??nh c??ng! Khi ch???t con s??? ???????c v??c x??c v??? ????y.");
                    break;
                case 2:
                    switch(b3) {
                        case 0:
                            if (p.c.level < 60) {
                                p.conn.sendMessageLog("Ch???c n??ng n??y y??n c???u tr??nh ????? 60");
                                return;
                            }

                            Map ma = Manager.getMapid(139);
                            TileMap area;
                            int var8;
                            for(var8 = 0; var8 < ma.area.length; ++var8) {
                                area = ma.area[var8];
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                            return;
                        case 1:
                            Server.manager.sendTB(p, "H?????ng d???n", "- H?????ng d???n v??ng ?????t ma qu???");
                            return;
                        default:
                            return;
                    }
                case 3:
                    Service.chatNPC(p, (short)npcid, "Ta gi??? ????? ch??a bao gi??? b??? m???t th??? g?? c???!");
                    break;
                default: {
                    Service.chatNPC(p, (short) npcid, "Tr??nh ra! Ta ??ang ??n M??");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void npcKenshinto(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        if(p.c.isNhanban) {
            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
            return;
        }
        switch(menuId) {
            case 0: {
                if (b3 == 0) {
                    p.requestItem(10);
                } else if (b3 == 1) {
                    p.requestItem(31);
                } else if (b3 == 2) {
                    Server.manager.sendTB(p, "H?????ng d???n", "- H?????ng d???n n??ng c???p trang b???");
                }
                break;
            }
            case 1: {
                if (b3 == 0) {
                    p.requestItem(12);
                } else if (b3 == 1) {
                    p.requestItem(11);
                }
                break;
            }
            case 2: {
                p.requestItem(13);
                break;
            }
            case 3: {
                p.requestItem(33);
                break;
            }
            case 4: {
                p.requestItem(46);
                break;
            }
            case 5: {
                p.requestItem(47);
                break;
            }
            case 6: {
                p.requestItem(49);
                break;
            }
            case 7: {
                p.requestItem(50);
                break;
            }
            case 8: {
                Service.chatNPC(p, (short) npcid, "C???n n??ng c???p trang b???, h??y ?????n qu??n c???a ta! Hahaha!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Nh???c l???i l???i ta n??i: MU v?? ?????ch!");
                break;
            }
        }

    }

    public static void npcUmayaki_Lang(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ta k??o xe qua c??c l??ng v???i t???c ????? m???ng c???a con!");
                return;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                TileMap[] var5 = Manager.getMapid(Map.arrLang[menuId - 1]).area;
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    TileMap area = var5[var7];
                    if (area.numplayers < Manager.getMapid(Map.arrLang[menuId - 1]).template.maxplayers) {
                        p.c.tileMap.leave(p);
                        area.EnterMap0(p.c);
                        return;
                    }
                }
            default: {
                Service.chatNPC(p, (short) npcid, "C?? ??i hay kh??ng c??n b???o?");
                break;
            }
        }
    }

    public static void npcUmayaki_Truong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
            case 1:
            case 2:
                TileMap[] var5 = Manager.getMapid(Map.arrTruong[menuId]).area;
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    TileMap area = var5[var7];
                    if (area.numplayers < Manager.getMapid(Map.arrTruong[menuId]).template.maxplayers) {
                        p.c.tileMap.leave(p);
                        area.EnterMap0(p.c);
                        return;
                    }
                }

                return;
            case 3:
                Service.chatNPC(p, (short)npcid, "Ta k??o xe qua c??c tr?????ng, kh??ng qua qu??n net ????u!");
                return;
            default: {
                Service.chatNPC(p, (short) npcid, "??i kh??ng n??i mau?");
                break;
            }
        }
    }

    public static void npcToyotomi(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                switch(b3) {
                    case 0:
                        Server.manager.sendTB(p, "Top ?????i gia y??n", Rank.getStringBXH(0));
                        return;
                    case 1:
                        Server.manager.sendTB(p, "Top cao th???", Rank.getStringBXH(1));
                        return;
                    case 2:
                        Server.manager.sendTB(p, "Top gia t???c", Rank.getStringBXH(2));
                        return;
                    case 3:
                        Server.manager.sendTB(p, "Top hang ?????ng", Rank.getStringBXH(3));
                        return;
                    default:
                        return;
                }
            case 1:
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, (short)npcid, "H???c ??t th??i con! Con ???? v??o l???p t??? tr?????c r???i m??.");
                } else if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, (short)npcid, "Con c???n c?? 1 t??m h???n trong tr???ng m???i c?? th??? nh???p h???c, h??y th??o v?? kh?? tr??n ng?????i ra!");
                } else if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, (short)npcid, "H??nh trang c???n ph???i c?? ??t nh???t 2 ?? tr???ng m???i c?? th??? nh???p h???c!");
                } else {
                    if (b3 == 0) {
                        Admission.Admission(p,(byte)1);
                    } else {
                        if (b3 != 1) {
                            Service.chatNPC(p, (short)npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                            break;
                        }
                        Admission.Admission(p,(byte)2);
                    }

                    Service.chatNPC(p, (short)npcid, "H??y ch??m ch??? luy???n t???p, c?? l??m th?? m???i c?? ??n con nh??.");
                }
                break;
            case 2:
                if (p.c.get().nclass != 1 && p.c.get().nclass != 2) {
                    Service.chatNPC(p, (short)npcid, "Con kh??ng ph???i h???c sinh c???a tr?????ng n??y, ta kh??ng th??? gi??p con t???y ??i???m d?????c r???i.");
                } else if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, (short)npcid, "S??? l???n t???y ??i???m k??? n??ng c???a con ???? h???t.");
                        return;
                    }

                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, (short)npcid, "Ta ???? gi??p con t???y ??i???m ti???m n??ng, h??y n??ng ??i???m th???t h???p l?? nha.");
                    p.sendAddchatYellow("T???y ??i???m ti???m n??ng th??nh c??ng");
                } else if (b3 == 1) {
                    if (p.c.get().countTayKyNang < 1) {
                        Service.chatNPC(p, (short)npcid, "S??? l???n t???y ??i???m k??? n??ng c???a con ???? h???t.");
                        return;
                    }
                    p.restSpoint();
                    --p.c.get().countTayKyNang;
                    Service.chatNPC(p, (short)npcid, "Ta ???? gi??p con t???y ??i???m k??? n??ng, h??y n??ng ??i???m th???t h???p l?? nha.");
                    p.sendAddchatYellow("T???y ??i???m k??? n??ng th??nh c??ng");
                }
                break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Tr?????ng ta l?? 1 ng??i tr?????ng danh gi??, ch??? gi??nh cho nh??ng ninja t??nh n??ng nh?? kem m?? th??i.");
                break;
            case 4:
                Service.chatNPC(p, (short)npcid, "Ta ??ang h??i m???t x??u, ta s??? giao chi???n v???i con sau nha! Bye bye...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcOokamesama(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:

                switch(b3) {
                    case 0:

                        Server.manager.sendTB(p, "Top ?????i gia y??n", Rank.getStringBXH(0));
                        return;
                    case 1:

                        Server.manager.sendTB(p, "Top cao th???", Rank.getStringBXH(1));
                        return;
                    case 2:

                        Server.manager.sendTB(p, "Top gia t???c", Rank.getStringBXH(2));
                        return;
                    case 3:

                        Server.manager.sendTB(p, "Top hang ?????ng", Rank.getStringBXH(3));
                        return;
                    default:
                        return;
                }
            case 1:
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, (short)npcid, "Con ???? v??o l???p t??? tr?????c r???i m??.");
                } else if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, (short)npcid, "Con c???n c?? 1 t??m h???n trong tr???ng m???i c?? th??? nh???p h???c, h??y th??o v?? kh?? tr??n ng?????i ra!");
                } else if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, (short)npcid, "H??nh trang c???n ph???i c?? ??t nh???t 2 ?? tr???ng m???i c?? th??? nh???p h???c!");
                } else {
                    if (b3 == 0) {
                        Admission.Admission(p,(byte)3);
                    } else {
                        if (b3 != 1) {
                            Service.chatNPC(p, (short)npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                            break;
                        }

                        Admission.Admission(p,(byte)4);
                    }

                    Service.chatNPC(p, (short)npcid, "H??y ch??m ch??? luy???n t???p, c?? l??m th?? m???i c?? ??n con nh??.");
                }
                break;
            case 2:
                if (p.c.get().nclass != 3 && p.c.get().nclass != 4) {
                    Service.chatNPC(p, (short)npcid, "Con kh??ng ph???i h???c sinh c???a tr?????ng n??y, ta kh??ng th??? gi??p con t???y ??i???m d?????c r???i.");
                } else if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, (short)npcid, "S??? l???n t???y ??i???m k??? n??ng c???a con ???? h???t.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, (short)npcid, "Ta ???? gi??p con t???y ??i???m ti???m n??ng, h??y n??ng ??i???m th???t h???p l?? nha.");
                    p.sendAddchatYellow("T???y ??i???m ti???m n??ng th??nh c??ng");
                } else if (b3 == 1) {
                    if (p.c.get().countTayKyNang < 1) {
                        Service.chatNPC(p, (short)npcid, "S??? l???n t???y ??i???m k??? n??ng c???a con ???? h???t.");
                        return;
                    }

                    p.restSpoint();
                    --p.c.get().countTayKyNang;
                    Service.chatNPC(p, (short)npcid, "Ta ???? gi??p con t???y ??i???m k??? n??ng, h??y n??ng ??i???m th???t h???p l?? nha.");
                    p.sendAddchatYellow("T???y ??i???m k??? n??ng th??nh c??ng");
                }
                break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Sao h??m nay tr???i n??ng th??? nh???, h??nh nh?? bi???n ?????i kh?? h???u l??m tan h???t b??ng tr?????ng ta r???i!");
                break;
            case 4:
                Service.chatNPC(p, (short)npcid, "Ta ??ang h??i m???t x??u, ta s??? giao chi???n v???i con sau nha! Bye bye...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcKazeto(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                switch(b3) {
                    case 0:

                        Server.manager.sendTB(p, "Top ?????i gia y??n", Rank.getStringBXH(0));
                        return;
                    case 1:

                        Server.manager.sendTB(p, "Top cao th???", Rank.getStringBXH(1));
                        return;
                    case 2:

                        Server.manager.sendTB(p, "Top gia t???c", Rank.getStringBXH(2));
                        return;
                    case 3:

                        Server.manager.sendTB(p, "Top hang ?????ng", Rank.getStringBXH(3));
                        return;
                    default:
                        return;
                }
            case 1:
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, (short)npcid, "Con ???? v??o l???p t??? tr?????c r???i m??.");
                } else if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, (short)npcid, "Con c???n c?? 1 t??m h???n trong tr???ng m???i c?? th??? nh???p h???c, h??y th??o v?? kh?? tr??n ng?????i ra!");
                } else if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, (short)npcid, "H??nh trang c???n ph???i c?? ??t nh???t 2 ?? tr???ng m???i c?? th??? nh???p h???c!");
                } else {
                    if (b3 == 0) {
                        Admission.Admission(p,(byte)5);
                    } else if (b3 == 1) {
                        Admission.Admission(p,(byte)6);
                    }

                    Service.chatNPC(p, (short)npcid, "H??y ch??m ch??? luy???n t???p, c?? l??m th?? m???i c?? ??n con nh??.");
                }
                break;
            case 2:
                if (p.c.get().nclass != 5 && p.c.get().nclass != 6) {
                    Service.chatNPC(p, (short)npcid, "Con kh??ng ph???i h???c sinh c???a tr?????ng n??y, ta kh??ng th??? gi??p con t???y ??i???m d?????c r???i.");
                } else if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, (short)npcid, "S??? l???n t???y ??i???m ti???m n??ng c???a con ???? h???t.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, (short)npcid, "Ta ???? gi??p con t???y ??i???m ti???m n??ng, h??y n??ng ??i???m th???t h???p l?? nha.");
                    p.sendAddchatYellow("T???y ??i???m ti???m n??ng th??nh c??ng");
                } else if (b3 == 1) {
                    if (p.c.get().countTayKyNang < 1) {
                        Service.chatNPC(p, (short)npcid, "S??? l???n t???y ??i???m k??? n??ng c???a con ???? h???t.");
                        return;
                    }
                    p.restSpoint();
                    --p.c.get().countTayKyNang;
                    Service.chatNPC(p, (short)npcid, "Ta ???? gi??p con t???y ??i???m k??? n??ng, h??y n??ng ??i???m th???t h???p l?? nha.");
                    p.sendAddchatYellow("T???y ??i???m k??? n??ng th??nh c??ng");
                }
                break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Ng????i l?? ng?????i th???i tan b??ng c???a tr?????ng Ookaza v?? mang kem v??? cho tr?????ng Hirosaki ????ng kh??ng?");
                break;
            case 4:
                Service.chatNPC(p, (short)npcid, "Ta ??ang h??i m???t x??u, ta s??? giao chi???n v???i con sau nha! Bye bye...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcTajima(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ch??o m???ng con ?????n v???i ng??i l??ng ??i ????u c??ng ph???i nh??? v???!");
                break;
            case 1:
                Service.chatNPC(p, (short)npcid, "Ch???c n??ng Hu??? v???t ph???m v?? nhi???m v??? ??ang c???p nh???t!");
                break;
            case 2:
                if (p.c.timeRemoveClone > System.currentTimeMillis()) {
                    p.toNhanBan();
                } else {
                    Service.chatNPC(p, (short)npcid, "Con kh??ng c?? ph??n th??n ????? s??? d???ng ch???c n??ng n??y!");
                }
                break;
            case 3:
                if (!p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, "Con kh??ng ph???i ph??n th??n ????? s??? d???ng ch???c n??ng n??y!");
                    return;
                }
                if (!p.c.clone.isDie && p.c.timeRemoveClone > System.currentTimeMillis()) {
                    p.exitNhanBan(true);
                }
                break;
            case 4:
            case 5:
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcRei(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ng????i ?????n ????y l??m g??, kh??ng c?? nhi???m v??? cho ng????i ????u!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcKirin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ng????i ?????n ????y l??m g??, kh??ng c?? nhi???m v??? cho ng????i ????u!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcSoba(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ta s??? s???m c?? nhi???m v??? cho con!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcSunoo(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Kh??? kh???...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcGuriin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcMatsurugi(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcOkanechan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Server.manager.sendTB(p, "H?????ng d???n", "- ????? n???p ti???n ho???c mua ?????, con h??y l??n Website ho???c THAM GIA BOX ZALO c???a game ????? n???p nh??!");
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                switch(b3) {
                    case 0:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 10 && p.c.checkLevel[0] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(223, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }

                            p.c.checkLevel[0] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 1:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 20 && p.c.checkLevel[1] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(224, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[1] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 2:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 30 && p.c.checkLevel[2] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(225, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[2] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 3:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 40 && p.c.checkLevel[3] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(226, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[3] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 4:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 50 && p.c.checkLevel[4] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(227, true));
                            if(p.status == 1) {
                                p.upluongMessage(1500L);
                                p.c.luongTN += 1500;
                            } else {
                                p.upluongMessage(3000L);
                            }
                            p.c.checkLevel[4] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 5:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 70 && p.c.checkLevel[5] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(228, true));
                            if(p.status == 1) {
                                p.upluongMessage(1500L);
                                p.c.luongTN += 1500;
                            } else {
                                p.upluongMessage(3000L);
                            }
                            p.c.checkLevel[5] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 6:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level >= 90 && p.c.checkLevel[6] == 0) {
                            if(p.status == 1) {
                                p.upluongMessage(2500L);
                                p.c.luongTN += 2500;
                            } else {
                                p.upluongMessage(5000L);
                            }
                            p.c.checkLevel[6] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 7:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, "Ch???c n??ng n??y kh??ng d??nh cho ph??n th??n!");
                            return;
                        }

                        if (p.c.level >= 110 && p.c.checkLevel[7] == 0) {
                            if(p.status == 1) {
                                p.upluongMessage(2500L);
                                p.c.luongTN += 2500;
                            } else {
                                p.upluongMessage(5000L);
                            }
                            p.c.checkLevel[7] = 1;
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    case 8:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level >= 130 && p.c.checkLevel[8] == 0) {
                            if(p.status == 1) {
                                p.upluongMessage(3500L);
                                p.c.luongTN += 3500;
                            } else {
                                p.upluongMessage(7000L);
                            }
                            p.c.checkLevel[8] = 1;
                            Service.chatNPC(p, (short)npcid, "Ch??c m???ng con ???? ?????t ?????n c???p ????? m???i!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Tr??nh ????? c???a con kh??ng ????? ho???c con ???? nh???n th?????ng r???i!");
                        }

                        return;
                    default: {
                        break;
                    }
                }
                break;
            case 2:
                Service.chatNPC(p, (short)npcid, "H??y r??n luy???n th???t ch??m ch??? r???i quay l???i ch??? ta nh???n th?????ng nha!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcRikudou(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        MapTemplate map;
        MobTemplate mob;
        switch(menuId) {
            case 0: {
                Service.chatNPC(p, (short)npcid, "H??y ch??m ch??? l??n nha.");
                break;
            }
            case 1: {
                switch(b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level < 10) {
                            Service.chatNPC(p, (short)npcid, "Con c???n ?????t c???p ????? 10 ????? c?? th??? nh???n nhi???m v???.");
                            return;
                        }

                        if (p.c.isTaskHangNgay != 0) {
                            Service.chatNPC(p, (short)npcid, "Ta ???? giao nhi???m v??? cho con tr?????c ???? r???i");
                            return;
                        }

                        if (p.c.countTaskHangNgay >= 20) {
                            Service.chatNPC(p, (short)npcid, "Con ???? ho??n th??nh h???t nhi???m v??? ng??y h??m nay r???i, ng??y mai h??y quay l???i nha.");
                            return;
                        }

                        mob = Service.getMobIdByLevel(p.c.level);
                        if (mob != null) {
                            map = Service.getMobMapId(mob.id);
                            if (map != null) {
                                p.c.taskHangNgay[0] = 0;
                                p.c.taskHangNgay[1] = 0;
                                p.c.taskHangNgay[2] = Util.nextInt(10, 25);
                                p.c.taskHangNgay[3] = mob.id;
                                p.c.taskHangNgay[4] = map.id;
                                p.c.isTaskHangNgay = 1;
                                p.c.countTaskHangNgay++;
                                Service.getTaskOrder(p.c, (byte)0);
                                Service.chatNPC(p, (short)npcid, "????y l?? nhi???m v??? th??? " + p.c.countTaskHangNgay + "/20 trong ng??y c???a con.");
                            }
                        }
                        break;
                    }

                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskHangNgay == 0) {
                            Service.chatNPC(p, (short)npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                            return;
                        }

                        p.c.isTaskHangNgay = 0;
                        p.c.countTaskHangNgay--;
                        p.c.taskHangNgay = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskHangNgay};
                        Service.clearTaskOrder(p.c, (byte)0);
                        Service.chatNPC(p, (short)npcid, "Con ???? hu??? nhi???m v??? l???n n??y.");
                        break;
                    }

                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskHangNgay == 0) {
                            Service.chatNPC(p, (short)npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                            return;
                        }

                        if (p.c.getBagNull() == 0) {
                            p.conn.sendMessageLog(Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.taskHangNgay[1] < p.c.taskHangNgay[2]) {
                            Service.chatNPC(p, (short)npcid, "Con ch??a ho??n th??nh nhi???m v??? ta giao!");
                            return;
                        }

                        p.c.isTaskHangNgay = 0;
                        p.c.taskHangNgay = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskHangNgay};
                        Service.clearTaskOrder(p.c, (byte)0);
                        long luongUp = Util.nextInt(3000, 5000);
                        if(p.status == 1) {
                            luongUp /= 2;
                            p.c.upxuMessage(15000L);
                            p.upluongMessage(luongUp);
                            p.c.xuTN += 150000;
                            p.c.luongTN += luongUp;
                        } else {
                            p.c.upxuMessage(35000L);
                            p.upluongMessage(luongUp);
                        }

                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 2;
                        }

                        if(p.c.countTaskHangNgay % 2 == 0) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 733 : 760, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(2,3);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 734 : 761, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(2,3);
                            p.c.addItemBag(true, itemUp);
                        }

                        Service.chatNPC(p, (short)npcid, "Con h??y nh???n l???y ph???n th?????ng c???a m??nh.");
                        break;
                    }

                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.taskHangNgay[4] != -1) {
                            Map ma = Manager.getMapid(p.c.taskHangNgay[4]);
                            int var8;
                            TileMap area;
                            for(var8 = 0; var8 < ma.area.length; ++var8) {
                                area = ma.area[var8];
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                        }
                        Service.chatNPC(p, (short)npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch(b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level < 30) {
                            Service.chatNPC(p, (short)npcid, "Con c???n ?????t c???p ????? 30 ????? c?? th??? nh???n nhi???m v??? t?? th??.");
                            return;
                        }

                        if (p.c.isTaskTaThu != 0) {
                            Service.chatNPC(p, (short)npcid, "Ta ???? giao nhi???m v??? cho con tr?????c ???? r???i");
                            return;
                        }

                        if (p.c.countTaskTaThu >= 2) {
                            Service.chatNPC(p, (short)npcid, "Con ???? ho??n th??nh h???t nhi???m v??? ng??y h??m nay r???i, ng??y mai h??y quay l???i nha.");
                            return;
                        }
                        mob = Service.getMobIdTaThu(p.c.level);
                        if (mob != null) {
                            map = Service.getMobMapIdTaThu(mob.id);
                            if (map != null) {
                                p.c.taskTaThu[0] = 1;
                                p.c.taskTaThu[1] = 0;
                                p.c.taskTaThu[2] = 1;
                                p.c.taskTaThu[3] = mob.id;
                                p.c.taskTaThu[4] = map.id;
                                p.c.isTaskTaThu = 1;
                                ++p.c.countTaskTaThu;
                                Service.getTaskOrder(p.c, (byte)1);
                                Service.chatNPC(p, (short)npcid, "H??y ho??n th??nh nhi???m v??? v?? tr??? v??? ????y nh???n th?????ng.");
                            }
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskTaThu == 0) {
                            Service.chatNPC(p, (short)npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                            return;
                        }

                        Service.clearTaskOrder(p.c, (byte)1);
                        p.c.isTaskTaThu = 0;
                        --p.c.countTaskTaThu;
                        p.c.taskTaThu = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskTaThu};
                        Service.chatNPC(p, (short)npcid, "Con ???? hu??? nhi???m v??? l???n n??y.");
                        break;
                    }

                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskTaThu == 0) {
                            Service.chatNPC(p, (short)npcid, "Con ch??a nh???n nhi???m v??? n??o c???!");
                            return;
                        }

                        if (p.c.taskTaThu[1] < p.c.taskTaThu[2]) {
                            Service.chatNPC(p, (short)npcid, "Con ch??a ho??n th??nh nhi???m v??? ta giao!");
                            return;
                        }

                        if (p.c.getBagNull() < 2) {
                            Service.chatNPC(p, (short)npcid, "H??nh trang c???a con kh??ng ????? ch??? tr???ng ????? nh???n th?????ng");
                            return;
                        }

                        p.c.isTaskTaThu = 0;
                        p.c.taskTaThu = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskTaThu};
                        Service.clearTaskOrder(p.c, (byte)1);
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 3;
                        }
                        Item item = ItemTemplate.itemDefault(251, false);
                        item.quantity = Util.nextInt(10, 20);
                        item.isLock = false;
                        p.c.addItemBag(true, item);

                        if(p.c.countTaskTaThu == 1) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 737 : 764, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(20,50);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 738 : 765, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(50,100);
                            p.c.addItemBag(true, itemUp);
                        }

                        Service.chatNPC(p, (short)npcid, "Con h??y nh???n l???y ph???n th?????ng c???a m??nh.");
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(ChienTruong.chienTruong == null) {
                            Service.chatNPC(p, (short)npcid, "Chi???n tr?????ng ch??a ???????c t??? ch???c.");
                            return;
                        }
                        if(ChienTruong.chienTruong != null) {
                            if(ChienTruong.chienTruong30 && (p.c.level < 30 || p.c.level >= 50)) {
                                Service.chatNPC(p, (short)npcid, "B??y gi??? l?? th???i gian chi???n tr?????ng cho c???p ????? t??? 30 ?????n 49. Tr??nh ????? c???a con kh??ng ph?? h???p ????? tham gia.");
                                return;
                            }else if(ChienTruong.chienTruong50 && p.c.level < 50) {
                                Service.chatNPC(p, (short)npcid, "B??y gi??? l?? th???i gian chi???n tr?????ng cho c???p ????? l???n h??n ho???c b???ng 50. Tr??nh ????? c???a con kh??ng ph?? h???p ????? tham gia.");
                                return;
                            }
                            if((ChienTruong.chienTruong30 ||ChienTruong.chienTruong50) && p.c.pheCT == 1) {
                                Service.chatNPC(p, (short)npcid, "Con ???? ??i???m danh phe H???c gi??? tr?????c ???? r???i.");
                                return;
                            }
                            if(ChienTruong.start && p.c.pheCT==-1) {
                                Service.chatNPC(p, (short)npcid, "Chi???n tr?????ng ???? b???t ?????u, kh??ng th??? b??o danh.");
                                return;
                            }
                            if((ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == -1 ) {
                                if (p.c.pointUydanh < 5000) {
                                    p.c.pointUydanh += 10;
                                }
                                p.c.pheCT = 0;
                                p.c.pointCT = 0;
                                p.c.isTakePoint = 0;
                                p.c.typepk = 4;
                                Service.ChangTypePkId(p.c, (byte)4);
                                Service.updatePointCT(p.c, 0);
                                if(p.c.party != null) {
                                    p.c.party.removePlayer(p.c.id);
                                }
                                if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                    ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                                } else {
                                    ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                                }
                                Map ma = Manager.getMapid(ChienTruong.chienTruong.map[0].id);
                                for (TileMap area : ma.area) {
                                    if (area.numplayers < ma.template.maxplayers) {
                                        p.c.tileMap.leave(p);
                                        area.EnterMap0(p.c);
                                        return;
                                    }
                                }
                                return;
                            }
                            p.c.typepk = 4;
                            Service.ChangTypePkId(p.c, (byte)4);
                            Service.updatePointCT(p.c, 0);
                            if(p.c.party != null) {
                                p.c.party.removePlayer(p.c.id);
                            }
                            if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                            } else {
                                ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                            }
                            Map ma = Manager.getMapid(ChienTruong.chienTruong.map[0].id);
                            for (TileMap area : ma.area) {
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(ChienTruong.chienTruong == null) {
                            Service.chatNPC(p, (short)npcid, "Chi???n tr?????ng ch??a ???????c t??? ch???c.");
                            return;
                        }
                        if(ChienTruong.chienTruong != null) {
                            if( ChienTruong.chienTruong30 && (p.c.level < 30 || p.c.level >= 50)) {
                                Service.chatNPC(p, (short)npcid, "B??y gi??? l?? th???i gian chi???n tr?????ng cho c???p ????? t??? 30 ?????n 49. Tr??nh ????? c???a con kh??ng ph?? h???p ????? tham gia.");
                                return;
                            }else if(ChienTruong.chienTruong50 && p.c.level < 50) {
                                Service.chatNPC(p, (short)npcid, "B??y gi??? l?? th???i gian chi???n tr?????ng cho c???p ????? l???n h??n ho???c b???ng 50. Tr??nh ????? c???a con kh??ng ph?? h???p ????? tham gia.");
                                return;
                            }
                            if(ChienTruong.start && p.c.pheCT==-1) {
                                Service.chatNPC(p, (short)npcid, "Chi???n tr?????ng ???? b???t ?????u, kh??ng th??? b??o danh.");
                                return;
                            }
                            if((ChienTruong.chienTruong30 ||ChienTruong.chienTruong50) && p.c.pheCT == 0) {
                                Service.chatNPC(p, (short)npcid, "Con ???? ??i???m danh phe B???ch gi??? tr?????c ???? r???i.");
                                return;
                            }
                            if( (ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == -1 ) {
                                if (p.c.pointUydanh < 5000) {
                                    p.c.pointUydanh += 10;
                                }
                                p.c.pheCT = 1;
                                p.c.pointCT = 0;
                                p.c.typepk = 5;
                                p.c.isTakePoint = 0;
                                Service.ChangTypePkId(p.c, (byte)5);
                                Service.updatePointCT(p.c, 0);
                                if(p.c.party != null) {
                                    p.c.party.removePlayer(p.c.id);
                                }
                                if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                    ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                                } else {
                                    ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                                }
                                Map ma = Manager.getMapid(ChienTruong.chienTruong.map[6].id);
                                for (TileMap area : ma.area) {
                                    if (area.numplayers < ma.template.maxplayers) {
                                        p.c.tileMap.leave(p);
                                        area.EnterMap0(p.c);
                                        return;
                                    }
                                }
                                return;
                            }
                            p.c.typepk = 5;
                            Service.ChangTypePkId(p.c, (byte)5);
                            Service.updatePointCT(p.c, 0);
                            if(p.c.party != null) {
                                p.c.party.removePlayer(p.c.id);
                            }
                            if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                            } else {
                                ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                            }
                            Map ma = Manager.getMapid(ChienTruong.chienTruong.map[6].id);
                            for (TileMap area : ma.area) {
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    case 2: {
                        if(ChienTruong.finish) {
                            Service.evaluateCT(p.c);
                        } else {
                            Server.manager.sendTB(p, "K???t qu???", "Ch??a c?? th??ng tin.");
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "H?????ng d???n", "Chi???n tr?????ng ???????c m??? 2 l???n m???i ng??y.\n" +
                        "- Chi???n tr?????ng lv30: gi??nh cho nh??n v???t level t??? 30 ?????n 45, ??i???m danh v??o l??c 19h v?? b???t ?????u t??? 19h30' ?????n 20h30'.\n" +
                        "- Chi???n tr?????ng lv50: gi??nh cho nh??n v???t level t??? 50 tr??? l??n, ??i???m danh v??o l??c 21h v?? b???t ?????u t??? 21h30' ?????n 22h30'.\n\n" +
                        "+ Top1: 10v ??an m???i lo???i + 3tr xu.\n" +
                        "+ Top 2: 7v ??an m???i lo???i + 2tr xu.\n" +
                        "+ Top 3: 5v ??an m???i lo???i + 1tr xu.\n" +
                        "+ Phe th???ng: 1v ??an m???i lo???i + 500k xu.");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcGoosho(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                p.requestItem(14);
                break;
            case 1:
                p.requestItem(15);
                break;
            case 2:
                p.requestItem(32);
                break;
            case 3:
                p.requestItem(34);
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcTruCoQuan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if(p.c.quantityItemyTotal(260) < 1) {
                    p.sendAddchatYellow("Kh??ng c?? ch??a kho?? ????? m??? c???a n??y.");
                    return;
                }
                if(p.c.tileMap.map.lanhDiaGiaToc != null && p.c.tileMap.map.mapLDGT()) {
                    switch (p.c.tileMap.map.id) {
                        case 80: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(1, p);
                            break;
                        }
                        case 81: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(2, p);
                            break;
                        }
                        case 82: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(3, p);
                            break;
                        }
                        case 83: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(4, p);
                            break;
                        }
                        case 84: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(5, p);
                            break;
                        }
                        case 85: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(6, p);
                            break;
                        }
                        case 86: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(7, p);
                            break;
                        }
                        case 87: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(8, p);
                            Server.manager.sendTB(p, "Ghi ch??", "Con ???????ng n??y s??? d???n ?????n c??nh c???a n??i ??? c???a m???t nh??n v???t huy???n b?? ???? b??? l???i nguy???n c??? " +
                                    "x??a y???m b??a r???ng s??? kh??ng ai c?? th??? ????nh b???i ???????c nh??n v???t huy???n b?? n??y. B???n h??y mau t??m c??ch ho?? gi???i l???i nguy???n.");
                            break;
                        }
                        case 88: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(9, p);
                            Server.manager.sendTB(p, "Ghi ch??", "Con ???????ng n??y s??? d???n ?????n c??nh c???a n??i ??? c???a m???t nh??n v???t huy???n b?? ???? b??? l???i nguy???n c??? " +
                                    "x??a y???m b??a r???ng s??? kh??ng ai c?? th??? ????nh b???i ???????c nh??n v???t huy???n b?? n??y. B???n h??y mau t??m c??ch ho?? gi???i l???i nguy???n.");
                            break;
                        }
                        case 89: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(10, p);
                            Server.manager.sendTB(p, "Ghi ch??", "Con ???????ng n??y s??? d???n ?????n c??nh c???a n??i ??? c???a m???t nh??n v???t huy???n b?? ???? b??? l???i nguy???n c??? " +
                                    "x??a y???m b??a r???ng s??? kh??ng ai c?? th??? ????nh b???i ???????c nh??n v???t huy???n b?? n??y. B???n h??y mau t??m c??ch ho?? gi???i l???i nguy???n.");
                            break;
                        }
                        default: {
                            break;
                        }

                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcShinwa(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.menuIdAuction = b3;
                final List<ShinwaTemplate> itemShinwas = ShinwaManager.entrys.get((int)b3);
                final Message mess = new Message(103);
                mess.writer().writeByte(b3);
                if(itemShinwas != null) {
                    mess.writer().writeInt(itemShinwas.size());
                    ShinwaTemplate item;
                    for(int i = 0; i < itemShinwas.size(); i++) {
                        item = itemShinwas.get(i);
                        if(item != null) {
                            mess.writer().writeInt(i);
                            mess.writer().writeInt(item.getRemainTime());
                            mess.writer().writeShort(item.getItem().quantity);
                            mess.writer().writeUTF(item.getSeller());
                            mess.writer().writeInt((int)item.getPrice());
                            mess.writer().writeShort(item.getItem().id);
                        } else {
                            mess.writer().writeInt(i);
                            mess.writer().writeInt(-1);
                            mess.writer().writeShort(0);
                            mess.writer().writeUTF("");
                            mess.writer().writeInt(999999999);
                            mess.writer().writeShort(12);
                        }
                    }
                } else {
                    mess.writer().writeInt(0);
                }
                mess.writer().flush();
                p.conn.sendMessage(mess);
                mess.cleanup();
                break;
            }
            case 1: {
                p.menuIdAuction = -2;
                p.requestItem(36);
                break;
            }
            case 2: {
                try {
                    synchronized (ShinwaManager.entrys.get((int)-1)) {
                        List<ShinwaTemplate> itemShinwas = ShinwaManager.entrys.get((int)-1);
                        List<Integer> ind = new ArrayList<>();
                        ShinwaTemplate item;
                        for(int i = itemShinwas.size() - 1; i>=0; i--) {
                            item = itemShinwas.get(i);
                            if(item != null && item.getSeller().equals(p.c.name)) {
                                if(p.c.getBagNull() == 0) {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang kh??ng ????? ch??? tr???ng ????? nh???n th??m v???t ph???m!");
                                    return;
                                }
                                p.c.addItemBag(true, item.getItem());
                                ind.add(i);
                            }
                        }
                        if(ind.size() < 1) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng c?? ????? ????? nh???n l???i!");
                            return;
                        }
                        for(int i : ind) {
                            itemShinwas.remove(i);
                        }
                    }
                } catch (Exception e) {
                    p.conn.sendMessageLog("C?? l???i, vui l??ng th??? l???i sau!");
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcChiHang(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcRakkii(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.requestItem(38);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                Service.sendInputDialog(p, (short) 4, "Nh???p Gift Code t???i ????y");
                break;
            }
            case 2: {
                switch (b3) {
                    case 0:
                    case 1: {
                        Server.manager.rotationluck[0].luckMessage(p);
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "V??ng xoay vip", "H??y ?????t c?????c xu v?? th??? v???n may c???a m??nh trong 2 ph??t nha.");
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            case 3: {
                switch (b3) {
                    case 0:
                    case 1: {
                        Server.manager.rotationluck[1].luckMessage(p);
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "V??ng xoay th?????ng", "H??y ?????t c?????c xu v?? th??? v???n may c???a m??nh trong 2 ph??t nha.");
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcLongDen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcKagai(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.clan == null) {
                            Service.chatNPC(p, (short) npcid, "Con ch??a c?? Gia t???c.");
                            return;
                        }
                        if (p.c.clan != null && p.c.clan.typeclan != 4) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng ph???i t???c tr?????ng, kh??ng th??? m???i gia t???c chi???n.");
                            return;
                        }
                        //Service.sendInputDialog(p, (short) 5, "Nh???p t??n gia t???c ?????i ph????ng");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                } else {
                    Item it;
                    Char var6;
                    switch (b3) {
                        case 0:
                            if (p.c.pointUydanh < 300) {
                                Service.chatNPC(p, (short) npcid, "Con c???n 300 ??i???m ho???t ?????ng ????? ????? l???y b?? k??p 3 ng??y.");
                                return;
                            } else {
                                if (p.c.getBagNull() < 1) {
                                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                } else {
                                    var6 = p.c;
                                    var6.pointUydanh -= 300;
                                    it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                    it.isLock = false;
                                    it.quantity = 1;
                                    it.isExpires = true;
                                    it.expires = System.currentTimeMillis() + 259200000L;
                                    p.c.addItemBag(false, it);
                                    p.c.upxuMessage(3000000);
                                }

                                return;
                            }
                        case 1: {
                            if (p.c.pointUydanh < 700) {
                                Service.chatNPC(p, (short) npcid, "Con c???n 700 ??i???m ho???t ?????ng ????? ????? l???y b?? k??p 7 ng??y.");
                                return;
                            } else {
                                if (p.c.getBagNull() < 1) {
                                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                } else {
                                    var6 = p.c;
                                    var6.pointUydanh -= 700;
                                    it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                    it.isLock = false;
                                    it.quantity = 1;
                                    it.isExpires = true;
                                    it.expires = System.currentTimeMillis() + 432000000L;
                                    p.c.addItemBag(false, it);
                                    p.c.upxuMessage(5000000);
                                }
                                return;
                            }

                        }
                        case 2: {
                            if (p.c.pointUydanh < 2000) {
                                Service.chatNPC(p, (short) npcid, "Con c???n 2000 ??i???m ho???t ?????ng ????? ????? l???y b?? k??p 15 ng??y.");
                            } else if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                            } else {
                                var6 = p.c;
                                var6.pointUydanh -= 2000;
                                it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                it.isLock = false;
                                it.quantity = 1;
                                it.isExpires = true;
                                it.expires = System.currentTimeMillis() + 1296000000L;
                                p.c.addItemBag(false, it);
                                p.c.upxuMessage(10000000);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    break;
                } else {
                    switch (b3) {
                        case 0: {
                            p.requestItem(43);
                            break;
                        }
                        case 1: {
                            p.requestItem(44);
                            break;
                        }
                        case 2: {
                            p.requestItem(45);
                            break;
                        }
                        case 3: {
                            Server.manager.sendTB(p, "H?????ng d???n", "- Tinh luy???n...");
                            break;
                        }
                        default: {
                            Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                            break;
                        }
                    }
                }
                break;
            }
            case 0:
            case 2:
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcTienNu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        if (p.typemenu == 33) {
            Item it;
            switch(Server.manager.event) {
                //H??
                case 1: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    } else {
                        switch (menuId) {
                            case 0: {
                                if (p.c.quantityItemyTotal(432) >= 1 && p.c.quantityItemyTotal(428) >= 3 && p.c.quantityItemyTotal(429) >= 2 && p.c.quantityItemyTotal(430) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(434);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(432, 1);
                                        p.c.removeItemBags(428, 3);
                                        p.c.removeItemBags(429, 2);
                                        p.c.removeItemBags(430, 3);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                            case 1: {
                                if (p.c.quantityItemyTotal(433) >= 1 && p.c.quantityItemyTotal(428) >= 2 && p.c.quantityItemyTotal(429) >= 3 && p.c.quantityItemyTotal(431) >= 2) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(435);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(433, 1);
                                        p.c.removeItemBags(428, 2);
                                        p.c.removeItemBags(429, 3);
                                        p.c.removeItemBags(431, 2);
                                    }
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                //Trung thu
                case 2: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    } else {
                        switch (menuId) {
                            case 0: {
                                if (p.c.quantityItemyTotal(304) >= 1 && p.c.quantityItemyTotal(298) >= 1 && p.c.quantityItemyTotal(299) >= 1 && p.c.quantityItemyTotal(300) >= 1 && p.c.quantityItemyTotal(301) >= 1) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(302);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(304, 1);
                                        p.c.removeItemBags(298, 1);
                                        p.c.removeItemBags(299, 1);
                                        p.c.removeItemBags(300, 1);
                                        p.c.removeItemBags(301, 1);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                            case 1: {
                                if (p.c.quantityItemyTotal(305) >= 1 && p.c.quantityItemyTotal(298) >= 1 && p.c.quantityItemyTotal(299) >= 1 && p.c.quantityItemyTotal(300) >= 1 && p.c.quantityItemyTotal(301) >= 1) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(303);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(305, 1);
                                        p.c.removeItemBags(298, 1);
                                        p.c.removeItemBags(299, 1);
                                        p.c.removeItemBags(300, 1);
                                        p.c.removeItemBags(301, 1);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                            case 2: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 3 && p.c.quantityItemyTotal(293) >= 2 && p.c.quantityItemyTotal(294) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(298);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 3);
                                        p.c.removeItemBags(293, 2);
                                        p.c.removeItemBags(294, 3);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                            case 3: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(295) >= 3 && p.c.quantityItemyTotal(294) >= 2) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(299);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 2);
                                        p.c.removeItemBags(295, 3);
                                        p.c.removeItemBags(294, 2);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                            case 4: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(295) >= 3 && p.c.quantityItemyTotal(297) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(300);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 2);
                                        p.c.removeItemBags(295, 3);
                                        p.c.removeItemBags(297, 3);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                            case 5: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(296) >= 2 && p.c.quantityItemyTotal(297) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng");
                                    } else {
                                        it = ItemTemplate.itemDefault(301);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 2);
                                        p.c.removeItemBags(296, 2);
                                        p.c.removeItemBags(297, 3);
                                    }
                                } else {
                                    Service.chatNPC(p, (short) npcid, "H??nh trang c???a con kh??ng c?? ????? nguy??n li???u");
                                }
                                break;
                            }
                        }
                    }
                    break;
                }

                //Noel
                case 3: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 6, "Nh???p s??? l?????ng b??nh Chocolate mu???n l??m.");
                            break;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 7, "Nh???p s??? l?????ng b??nh  D??u t??y mu???n l??m.");
                            break;
                        }
                        case 2: {
                            if(p.c.pointNoel < 3500) {
                                Service.chatNPC(p, (short) npcid, "Con c???n ??t nh???t 3500 ??i???m ????? ?????i m???t n??? 7 ng??y.");
                                return;
                            }
                            p.c.pointNoel -= 3500;
                            it = ItemTemplate.itemDefault(p.c.gender == 1 ? 407 : 408);
                            it.isLock = false;
                            it.quantity = 1;
                            it.isExpires = true;
                            it.expires = System.currentTimeMillis() + 604800000L;
                            p.c.addItemBag(false, it);
                            break;
                        }
                        case 3: {
                            if(p.c.pointNoel < 5000) {
                                Service.chatNPC(p, (short) npcid, "Con c???n ??t nh???t 5000 ??i???m ????? ?????i pet Ho??? long 7 ng??y.");
                                return;
                            }
                            p.c.pointNoel -= 5000;
                            it = ItemTemplate.itemDefault(583);
                            it.isLock = false;
                            it.quantity = 1;
                            it.isExpires = true;
                            it.expires = System.currentTimeMillis() + 604800000L;
                            p.c.addItemBag(false, it);
                            break;
                        }
                        case 4: {
                            String a = "";
                            if(Rank.bxhBossTuanLoc.isEmpty()) {
                                a = "Ch??a c?? th??ng tin.";
                            }
                            for(Rank.Entry3 item : Rank.bxhBossTuanLoc) {
                                a += item.index +". "+item.name+": "+item.point+" ??i???m\n";
                            }
                            Server.manager.sendTB(p, "BXH Di???t Boss", a);
                            break;
                        }
                        case 5: {
                            Server.manager.sendTB(p, "H?????ng d???n", "- S??? ??i???m hi???n t???i c???a b???n l??: "+p.c.pointNoel+"\n" +
                                    "- Ki???m ??i???m s??? ki???n b???ng c??ch nh???n qu?? h??ng ng??y t???i C??y th??ng (+1 ??i???m), trang tr?? c??y th??ng (+10 ??i???m), gi???t boss Tu???n l???c (+1 ??i???m).\n" +
                                    "- D??ng ??i???m ????? d???i l???y v???t ph???m qu?? gi??: M???t n??? Super Broly/Onna Bugeisha 7 ng??y (3500 ??i???m), Pet Ho??? long 7 ng??y (5000 ??i???m).\n" +
                                    "- B??nh Chocolate: 2 B?? + 2 Kem + 3 ???????ng + 1 Chocolate + 5000 y??n.\n" +
                                    "- B??nh D??u t??y: 3 B?? + 3 Kem + 4 ???????ng + 1 D??u t??y + 10000 y??n.\n");
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                default: {
                    Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                    break;
                }
            }

        }
    }

    public static void npcCayThong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.level < 40) {
                    p.conn.sendMessageLog("Nh??n v???t ph???i tr??n level 40 m???i c?? th??? nh???n qu?? v?? trang tr??.");
                    return;
                }
                if(p.c.isNhanQuaNoel < 1) {
                    p.conn.sendMessageLog("H??m nay b???n ???? nh???n qu?? r???i.");
                    return;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng ????? nh???n qu??");
                    return;
                }
                p.c.isNhanQuaNoel = 0;
                p.c.pointNoel++;
                int random = Util.nextInt(0,2);
                switch (random) {
                    case 0: {
                        int xu = Util.nextInt(0,100000);
                        if(p.status == 1) {
                            xu /= 2;
                            p.c.xuTN += xu;
                        }
                        p.c.upxuMessage(xu);
                        p.sendAddchatYellow("H??i ??en! B???n nh???n ???????c " + xu + " xu.");
                        break;
                    }
                    case 1: {
                        int xu = Util.nextInt(100000,500000);
                        if(p.status == 1) {
                            xu /= 2;
                            p.c.xuTN += xu;
                        }
                        p.c.upxuMessage(xu);
                        p.sendAddchatYellow("Th???t may m???n! B???n nh???n ???????c " + xu + " xu.");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog( Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.level < 40) {
                    p.conn.sendMessageLog("Nh??n v???t ph???i tr??n level 40 m???i c?? th??? nh???n qu?? v?? trang tr??.");
                    return;
                }
                if(p.c.quantityItemyTotal(673) < 1) {
                    p.conn.sendMessageLog("B???n kh??ng c?? ????? Qu?? trang tr?? ????? trang tr?? c??y th??ng Noel.");
                    return;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("H??nh trang kh??ng ????? ch??? tr???ng ????? nh???n qu??");
                    return;
                }
                p.c.pointNoel += 10;
                p.c.removeItemBag(p.c.getIndexBagid(673, false), 1);
                Item it;
                int per = Util.nextInt(300);
                if(per<1) {
                    it = ItemTemplate.itemDefault(383);
                } else if (per >= 1 && per <= 3) {
                    it = ItemTemplate.itemDefault(775);
                } else {
                    per = Util.nextInt(UseItem.idItemCayThong.length);
                    it = ItemTemplate.itemDefault(UseItem.idItemCayThong[per]);
                }
                it.isLock = false;
                it.quantity = 1;
                p.c.addItemBag(true, it);
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "M???i ng??y t??m t???i t??i ????? nh???n Xu nh??!");
                break;
            }
        }
    }

    public static void npcOngGiaNoen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if(Server.manager.event != 3) {
                    Service.chatNPC(p, (short) npcid, "Hi???n t???i kh??ng trong th???i gian s??? ki???n Noel");
                    return;
                }
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.quantityItemyTotal(775) < 1000) {
                    Service.chatNPC(p, (short) npcid, "B???n kh??ng c?? ????? 1000 hoa tuy???t ????? ?????i m???t n???.");
                    return;
                }
                if(p.c.getBagNull() < 1) {
                    Service.chatNPC(p, (short) npcid, "H??nh trang kh??ng ????? ch??? tr???ng ????? nh???n qu??");
                    return;
                }
                p.c.removeItemBag( p.c.getIndexBagid(775, false), 1000);
                Item it = ItemTemplate.itemDefault(774);
                it.isLock = false;
                it.quantity = 1;
                it.isExpires = true;
                it.expires = System.currentTimeMillis() + 2592000000L;
                p.c.addItemBag(false, it);
                break;
            }
            case 1: {
                if(Server.manager.event != 3) {
                    Service.chatNPC(p, (short) npcid, "Hi???n t???i kh??ng trong th???i gian di???n ra s??? ki???n Noel");
                    return;
                }
                Server.manager.sendTB(p, "H?????ng d???n", "- Ki???m hoa tuy???t b???ng c??ch s??? d???ng B??nh kh??c c??y chocolate, B??nh kh??c c??y d??u t??y ho???c trang tr?? c??y th??ng.\n- D??ng 1000 b??ng hoa tuy???t ????? ?????i l???y m???t n??? Satan v???i ch??? s??? kh???ng.");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcVuaHung(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                Service.sendInputDialog(p, (short) 10, "Nh???p s??? y??n mu???n ?????i (100.000 y??n = 1000 xu):");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcKanata_LoiDai(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.party != null && p.c.party.charID != p.c.id) {
                    p.c.party.removePlayer(p.c.id);
                }

                p.c.dunId = -1;
                p.c.isInDun = false;
                p.c.tileMap.leave(p);
                p.restCave();
                p.changeMap(p.c.mapKanata);
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.party != null && p.c.party.charID != p.c.id) {
                    Service.chatNPC(p, (short)npcid, "Con kh??ng ph???i nh??m tr?????ng, kh??ng th??? ?????t c?????c");
                    return;
                }

                Service.sendInputDialog(p, (short)3, "?????t ti???n c?????c (l???n h??n 1000 xu v?? chia h???t cho 50)");
                break;
            case 2:
                Server.manager.sendTB(p, "H?????ng d???n", "- M???i ?????i th??? v??o l??i ????i\n\n- ?????t ti???n c?????c (L???n h??n 1000 xu v?? chia h???t cho 50)\n\n- Khi c??? 2 ???? ?????t ti???n c?????c, v?? s??? ti???n ph???i th???ng nh???t b???ng nhau th?? tr???n so t??i m???i c?? th??? b???t ?????u.\n\n- Khi ???? ?????t ti???n c?????c, nh??ng tho??t, m???t k???t n???i ho???c thua cu???c, th?? ng?????i ch??i c??n l???i s??? gi??nh chi???n th???ng\n\n- S??? ti???n th???ng s??? nh???n ???????c s??? b??? tr??? ph?? 5%\n\n- N???u h???t th???i gian m?? ch??a c?? ai gi??nh chi???n th???ng th?? cu???c so t??i s??? t??nh ho??, v?? m???i ng?????i s??? nh???n l???i s??? ti???n c???a m??nh v???i m???c ph?? b??? tr??? 1%");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }

    }

    public static void npcAdmin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.isDiemDanh == 0) {
                    if(p.status == 1) {
                        p.upluongMessage(250L);
                        p.c.luongTN += 250;
                    } else {
                        p.upluongMessage(5000L);
                    }
                    p.c.isDiemDanh = 1;
                    Service.chatNPC(p, (short) npcid, "??i???m danh th??nh c??ng, con nh???n ???????c 5000 l?????ng.");
                } else {
                    Service.chatNPC(p, (short) npcid, "H??m nay con ???? ??i???m danh r???i, h??y quay l???i v??o ng??y h??m sau nha!");
                }
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.isQuaHangDong == 1) {
                    Service.chatNPC(p, (short) npcid, "Con ???? nh???n th?????ng h??m nay r???i!");
                    return;
                }

                if (p.c.countHangDong >= 2) {
                    if(p.status == 1) {
                        p.upluongMessage(750L);
                        p.c.luongTN += 750;
                    } else {
                        p.upluongMessage(15000L);
                    }
                    p.c.isQuaHangDong = 1;
                    Service.chatNPC(p, (short) npcid, "Nh???n qu?? ho??n th??nh hang ?????ng th??nh c??ng, con nh???n ???????c 15000 l?????ng.");
                } else if (p.c.countHangDong < 2) {
                    Service.chatNPC(p, (short) npcid, "Con ch??a ho??n th??nh ????? 2 l???n ??i hang ?????ng, h??y ho??n th??nh ????? 2 l???n v?? quay l???i g???p ta ???? nh???n th?????ng");
                }
                break;
            }
            case 2: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.getBagNull() < 6) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                    return;
                }

                if (p.c.level == 1) {
                    p.updateExp(Level.getMaxExp(10));
                    if(p.status == 1) {
                        p.upluongMessage(10000L);
                        p.c.upxuMessage(25000000L);
                        p.c.upyenMessage(25000000L);
                        p.c.luongTN +=  10000;
                        p.c.yenTN += 50000000;
                        p.c.xuTN += 50000000;
                        p.c.addItemBag(false, ItemTemplate.itemDefault(222, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                    } else {
                        p.upluongMessage(20000L);
                        p.c.upxuMessage(100000000L);
                        p.c.upyenMessage(100000000L);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(222, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                    }
                    Service.chatNPC(p, (short) npcid, "Con ???? nh???n qu?? t??n th??? th??nh c??ng, ch??c con tr???i nghi???m game vui v???.");
                } else {
                    Service.chatNPC(p, (short) npcid, "Con ???? nh???n qu?? t??n th??? tr?????c ???? r???i, kh??ng th??? nh???n l???i l???n n???a!");
                }
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.level == 1) {
                    p.conn.sendMessageLog("Kh??ng th??? th???c hi???n thao t??c n??y..");
                    return;
                }
                if(p.c.get().exptype == 1) {
                    p.c.get().exptype = 0;
                    p.sendAddchatYellow("???? t???t nh???n exp.");
                } else {
                    p.c.get().exptype = 1;
                    p.sendAddchatYellow("???? b???t nh???n exp.");
                }
                break;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.status == 1) {
                    Service.chatNPC(p, (short) npcid, "T??i kho???n c???a con ch??a ???????c n??ng c???p l??n CH??NH TH???C, kh??ng th??? nh???n l???i ph???n th?????ng.");
                    return;
                }
                switch (b3) {
                    case 0: {
                        if(p.c.yenTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng c?? y??n l??u tr??? ????? nh???n l???i.");
                            return;
                        }
                        p.c.upyenMessage(p.c.yenTN);
                        p.c.yenTN = 0;
                        break;
                    }
                    case 1: {
                        if(p.c.xuTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng c?? xu l??u tr??? ????? nh???n l???i.");
                            return;
                        }
                        p.c.upxuMessage(p.c.xuTN);
                        p.c.xuTN = 0;
                        break;
                    }
                    case 2: {
                        if(p.c.luongTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng c?? l?????ng l??u tr??? ????? nh???n l???i.");
                            return;
                        }
                        p.upluongMessage(p.c.luongTN);
                        p.c.luongTN = 0;
                        break;
                    }
                    case 3: {
                        if(p.c.expTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con kh??ng c?? kinh nghi???m l??u tr??? ????? nh???n l???i.");
                            return;
                        }
                        p.updateExp(p.c.expTN);
                        p.c.expTN = 0;
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 5: {
                if(p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.clone == null) {
                    Service.chatNPC(p, (short) npcid, "Con kh??ng c?? ph??n th??n ????? s??? d???ng ch???c n??ng n??y.");
                    return;
                }
                Service.startYesNoDlg(p, (byte) 5, "Sau khi l???a ch???n, t???t c??? d??? li???u nh?? trang b???, th?? c?????i, ??i???m,... c???a ph??n th??n s??? b??? reset v??? ban ?????u. H??y ch???c ch???n r???ng b???n ???? th??o to??n b??? trang b??? c???a ph??n th??n v?? x??c nh???n mu???n reset.");
                break;
            }
            case 6: {
                Server.manager.sendTB(p, "H?????ng d???n", "- V???a v??o ch??i, h??y ?????n ch??? ta nh???n qu?? t??n th??? bao g???m: 100tr xu, 20k l?????ng, 100tr y??n \n- M???i ng??y con ???????c ??i???m danh h??ng ng??y 1 l???n v?? nh???n 5000 l?????ng \n- N???u m???i ng??y ho??n th??nh hang ?????ng ????? 2 l???n con h??y ?????n ch??? ta v?? Nh???n qu?? hang ?????ng ????? nh???n 15000 l?????ng\n\n** L??u ??, n???u l?? t??i kho???n tr???i nghi???m, con ch??? c?? th??? nh???n ???????c 1 n???a ph???n th?????ng t??? ta.");
                break;
            }

            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcRikudou_ChienTruong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.c.typepk = 0;
                Service.ChangTypePkId(p.c, (byte)0);
                p.c.tileMap.leave(p);
                p.restCave();
                p.changeMap(p.c.mapLTD);
                break;
            }
            case 1: {
                Service.evaluateCT(p.c);
                break;
            }

            default: {
                Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                break;
            }
        }
    }

    public static void npcKagai_GTC(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (p.c.mapid) {
            case 117: {
                switch(menuId) {
                    case 0: {
                        p.c.typepk = 0;
                        Service.ChangTypePkId(p.c, (byte)0);
                        p.c.tileMap.leave(p);
                        p.restCave();
                        p.changeMap(p.c.mapLTD);
                        break;
                    }
                    case 1: {
                        Service.chatNPC(p, (short) npcid, "?????t c?????c");
                        Service.sendInputDialog(p, (short)8, "?????t ti???n c?????c (l???n h??n 1000 xu v?? chia h???t cho 50)");
                        break;
                    }

                    default: {
                        Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                        break;
                    }
                }
                break;
            }
            case 118:
            case 119: {
                switch(menuId) {
                    case 0: {
                        p.c.typepk = 0;
                        Service.ChangTypePkId(p.c, (byte)0);
                        p.c.tileMap.leave(p);
                        p.restCave();
                        p.changeMap(p.c.mapLTD);
                        break;
                    }
                    case 1: {
                        Service.evaluateCT(p.c);
                        break;
                    }
                    default: {
                        Service.chatNPC(p, (short) npcid, "Ch??i game vui v??? nh??! From DangDev with Love <3");
                        break;
                    }
                }
                break;
            }
        }
    }
}
