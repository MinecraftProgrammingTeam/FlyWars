package top.mpt.xzystudio.flywars.scheduler;

import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.utils.GameUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

public class PickUpTimer extends BukkitRunnable {

    // 这里设置成public是不是懒得写getTeam了(((
    private GameTeam team = null;

    public GameTeam getTeam() {
        return team;
    }

    /**
     * 设置Team
     * @param team 要设置的Team
     */
    public void setTeam(GameTeam team) {
        this.team = team;
    }

    @Override
    public void run() {
        // 如果没setTeam，直接return（防止NPE）
        if (this.team == null) return;
        // 获取p1
        Player p = (Player) GameUtils.find(this.team.getP1().getPassengers(), passenger -> passenger == this.team.getP1());
        if (p == null){  // 还没捡起来，干死♂他们 - 6
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.HARM, Integer.MAX_VALUE, 1);
            // 遍历此Team的全部玩家，并增加药水效果（瞬间伤害）
            this.team.players.keySet().forEach(potionEffect::apply);
            PlayerUtils.showTitle(this.team.getP1(), "#RED#请及时捡起您的队友！", "否则您将持续受到伤害");
            PlayerUtils.showTitle(this.team.getP2(), "#RED#您的猪队友没能及时捡起你", "qswl");
        }
    }
}