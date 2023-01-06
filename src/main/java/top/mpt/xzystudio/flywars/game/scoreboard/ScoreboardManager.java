package top.mpt.xzystudio.flywars.game.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;

import java.util.HashMap;
import java.util.UUID;

/**
 * 计分板类
 */
public class ScoreboardManager {
    // 因为要给不同玩♂家展示不同的计分板，所以建立一个HashMap存放计分板
    private final HashMap<UUID, FastBoard> boards = new HashMap<>();

    // 建立HashMap，给每个团队都设置一个TeamInfo
    private final HashMap<GameTeam, TeamInfo> teamInfos = new HashMap<>();

    // 向玩家展示的计分板的标题
    private final String title = ChatUtils.translateColor("#BLUE#FlyWars 飞行战争");

    public ScoreboardManager() {
    }


}
