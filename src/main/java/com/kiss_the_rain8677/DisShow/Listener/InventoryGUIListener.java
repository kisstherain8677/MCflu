package com.kiss_the_rain8677.DisShow.Listener;

import com.kiss_the_rain8677.DisShow.MainPlugin;
import com.kiss_the_rain8677.DisShow.GUI.InventoryGUI;
import com.kiss_the_rain8677.DisShow.Util.VaultUtil;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryGUIListener implements Listener {

    MainPlugin plugin;
    //获取plugin，注册事件
    public InventoryGUIListener(MainPlugin plugin){
        this.plugin=plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void InventoryOpen(InventoryClickEvent event){
        Inventory inventory=event.getInventory();
        String title=inventory.getTitle();
        if(title.equalsIgnoreCase(InventoryGUI.PlayerrenqiGui)){
            HumanEntity whoClicked = event.getWhoClicked();
            Player player=(Player)whoClicked;
            int rawSlot=event.getRawSlot();
            plugin.getLogger().info(String.valueOf(rawSlot));
            if(rawSlot==49){
                //进行收购
                event.setCancelled(true);

                //遍历10-43
                double money=0;
                for(int i=10;i<=43;i++){
                    ItemStack itemStack=inventory.getItem(i);
                    if(itemStack!=null && itemStack.getType()== Material.RAW_BEEF){
                        int amount=itemStack.getAmount();
                        money+=amount*10;
                    }
                }
                VaultUtil.give(player.getUniqueId(),money);
                player.sendMessage("收购成功,你现在的现金有:"+VaultUtil.seemoney(player.getUniqueId()));
                //inventory.getItem()
            }else if(rawSlot>=0&&rawSlot<=8||rawSlot<=53 && rawSlot>=45 ||rawSlot==9 ||rawSlot==18||rawSlot==27||rawSlot==36
                    ||rawSlot==45||rawSlot==17||rawSlot==26||rawSlot==35||rawSlot==44){
                event.setCancelled(true);
                player.sendMessage("不能点击");
            }

        }
    }
}
